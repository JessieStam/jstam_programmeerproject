package jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.FourthFragUnfinised;
import jstam.programmeerproject_scubascan.Helpers.Managers.TextDisplayManager;
import jstam.programmeerproject_scubascan.Items.DiveItem;
import jstam.programmeerproject_scubascan.R;

/**
 * Scuba Scan - FourthFragFinished
 *
 * Jessie Stam
 * 10560599
 *
 * This fragment displays technical data.
 */
public class FourthFragFinished extends android.support.v4.app.Fragment {

    ArrayList<String> technicaldata_list;
    TextView text;
    InputStream displaytext;
    TextDisplayManager display_manager;
    String time_in, time_out, pressure_in, pressure_out, depth, safetystop, dive_number;
    DiveItem dive_item;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.frag_fourth_finished, container, false);

        // get text file for displaying
        displaytext = getResources().openRawResource(R.raw.finisheddive_page4);

        display_manager = new TextDisplayManager(displaytext);
        display_manager.read(displaytext);

        technicaldata_list = new ArrayList<>();

        // get arguments to fill in placeholders in display text
        if (getArguments() != null) {

            // get arguments from unfinished fragment
            if (getArguments().getString("dive_number") == null) {
                technicaldata_list = getArguments().getStringArrayList("technicaldata");
            }
            // get arguments from LogDetailsActivity
            else {
                dive_number = getArguments().getString("dive_number");
                dive_item = getArguments().getParcelable("dive_item");

                time_in = dive_item.getTimeIn();
                time_out = dive_item.getTimeOut();
                pressure_in = dive_item.getPressureIn();
                pressure_out = dive_item.getPressureOut();
                depth = dive_item.getDepth();
                safetystop = dive_item.getSafetystop();

                technicaldata_list.add(time_in);
                technicaldata_list.add(time_out);
                technicaldata_list.add(pressure_in);
                technicaldata_list.add(pressure_out);
                technicaldata_list.add(depth);
                technicaldata_list.add(safetystop);
            }
        }

        text = (TextView) view.findViewById(R.id.finished_text_fourth);
        Button btn = (Button) view.findViewById(R.id.edit_fourthnewdive_button);

        // when edit button is clicked, replace fragment with unfinished fragment
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getFragmentManager()
                        .beginTransaction();
                trans.replace(R.id.root_frame_fourth, new FourthFragUnfinised());
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);
                trans.commit();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // fill in display text
        if (technicaldata_list != null) {
            for (String item : technicaldata_list) {
                display_manager.fillInPlaceholder(item);
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