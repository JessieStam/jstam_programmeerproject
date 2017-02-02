package jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.FirstFragUnfinished;
import jstam.programmeerproject_scubascan.Helpers.Managers.DiveManager;
import jstam.programmeerproject_scubascan.Helpers.Managers.TextDisplayManager;
import jstam.programmeerproject_scubascan.Items.DiveItem;
import jstam.programmeerproject_scubascan.R;

/**
 * Scuba Scan - FirstFragFinished
 *
 * Jessie Stam
 * 10560599
 *
 * This fragment displays general data.
 */
public class FirstFragFinished extends Fragment {

    String date, country, dive_spot, buddy, dive_number;
    ArrayList<String> general_data;
    TextView text;
    InputStream displaytext;
    TextDisplayManager display_manager;
    DiveManager dive_manager;
    DiveItem dive_item;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.frag_first_finished, container, false);

        // get text file for displaying
        displaytext = getResources().openRawResource(R.raw.finisheddive_page1);
        dive_manager = DiveManager.getOurInstance();

        text = (TextView) view.findViewById(R.id.finished_text);
        Button btn = (Button) view.findViewById(R.id.edit_firstnewdive_button);

        // when edit button is clicked, replace fragment with unfinished fragment
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getFragmentManager()
                        .beginTransaction();
                trans.replace(R.id.root_frame, new FirstFragUnfinished());
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        // read display text
        display_manager = new TextDisplayManager(displaytext);
        display_manager.read(displaytext);

        general_data = new ArrayList<>();

        // get arguments to fill in placeholders in display text
        if (getArguments() != null) {

            // get arguments from unfinished fragment
            if (getArguments().getString("dive_number") == null){
                date = getArguments().getString("date");
                country = getArguments().getString("country");
                dive_spot = getArguments().getString("dive_spot");
                buddy = getArguments().getString("buddy");
            }
            // get arguments from LogDetailsActivity
            else {
                dive_number = getArguments().getString("dive_number");
                dive_item = getArguments().getParcelable("dive_item");

                date = dive_item.getDate();
                buddy = dive_item.getBuddy();
                country = dive_item.getCountry();
                dive_spot = dive_item.getDiveSpot();
            }
        }

        general_data.add(date);
        general_data.add(buddy);
        general_data.add(country);
        general_data.add(dive_spot);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // fill in display text
        if (general_data != null) {
            for (String detail : general_data) {
                display_manager.fillInPlaceholder(detail);
            }

            // create boolean to check if isFilledIn function returns true
            boolean filledIn = display_manager.isFilledIn();

            // when everything is filled in, display text
            if (filledIn) {

                String final_display = display_manager.toString();

                if (Build.VERSION.SDK_INT >= 24) {
                    text.setText(Html.fromHtml(final_display, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    text.setText(Html.fromHtml(final_display));
                }
            }
        }
    }
}
