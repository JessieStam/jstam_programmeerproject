package jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.ThirdFragUnfinished;
import jstam.programmeerproject_scubascan.Helpers.Managers.TextDisplayManager;
import jstam.programmeerproject_scubascan.Items.DiveItem;
import jstam.programmeerproject_scubascan.R;

/**
 * Scuba Scan - ThirdFragFinished
 *
 * Jessie Stam
 * 10560599
 *
 * This fragment displays equipment data.
 */
public class ThirdFragFinished extends android.support.v4.app.Fragment {

    String lead, dive_number;
    ArrayList<String> clothes;
    TextView text;
    InputStream displaytext;
    TextDisplayManager display_manager;
    DiveItem dive_item;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.frag_third_finished, container, false);

        // get text file for displaying
        displaytext = getResources().openRawResource(R.raw.finisheddive_page3);


        display_manager = new TextDisplayManager(displaytext);
        display_manager.read(displaytext);

        clothes = new ArrayList<>();

        // get arguments to fill in placeholders in display text
        if (getArguments() != null) {

            // get arguments from unfinished fragment
            if (getArguments().getString("dive_number") == null) {
                clothes = getArguments().getStringArrayList("clothes_strings");
                lead = getArguments().getString("lead");
            }
            // get arguments from LogDetailsActivity
            else {
                dive_number = getArguments().getString("dive_number");
                dive_item = getArguments().getParcelable("dive_item");

                clothes = dive_item.getClothingList();
                lead = dive_item.getLead();
            }
        }

        text = (TextView) view.findViewById(R.id.finished_text_third);
        Button btn = (Button) view.findViewById(R.id.edit_thirdnewdive_button);

        // when edit button is clicked, replace fragment with unfinished fragment
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getFragmentManager()
                        .beginTransaction();
                trans.replace(R.id.root_frame_third, new ThirdFragUnfinished());
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
        if (lead != null && clothes != null) {

            display_manager.fillInPlaceholder(lead);
            String placeholder_string = "";

            for (String item : clothes) {
                if (clothes.size() == 1) {
                    placeholder_string += "</b>a <b>" + item + ".";
                } else if (clothes.indexOf(item) != (clothes.size() -1)) {
                    placeholder_string += "</b>a <b>" + item + ", ";
                } else {
                    placeholder_string += "</b>and a <b>" + item + ".";
                }
            }

            display_manager.fillInPlaceholder(placeholder_string);

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
