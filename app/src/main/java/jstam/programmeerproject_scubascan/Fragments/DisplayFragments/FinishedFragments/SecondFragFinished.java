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

import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.SecondFragUnfinished;
import jstam.programmeerproject_scubascan.Helpers.Managers.TextDisplayManager;
import jstam.programmeerproject_scubascan.Items.DiveItem;
import jstam.programmeerproject_scubascan.R;

/**
 * Scuba Scan - SecondFragFinished
 *
 * Jessie Stam
 * 10560599
 *
 * This fragment displays circumstances data.
 */
public class SecondFragFinished extends Fragment {

    String air_temp, surface_temp, bottom_temp, water_type, visibility, dive_type, dive_number;
    ArrayList<String> circumstances_data;
    TextView text;
    InputStream displaytext;
    TextDisplayManager display_manager;
    DiveItem dive_item;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.frag_second_finished, container, false);

        // get text file for displaying
        displaytext = getResources().openRawResource(R.raw.finisheddive_page2);

        display_manager = new TextDisplayManager(displaytext);
        display_manager.read(displaytext);

        circumstances_data = new ArrayList<>();

        // get arguments to fill in placeholders in display text
        if (getArguments() != null) {

            // get arguments from unfinished fragment
            if (getArguments().getString("dive_number") == null){
                air_temp = getArguments().getString("air_temp");
                surface_temp = getArguments().getString("surface_temp");
                bottom_temp = getArguments().getString("bottom_temp");
                visibility = getArguments().getString("visibility");
                water_type = getArguments().getString("water_type");
                dive_type = getArguments().getString("dive_type");
            }
            // get arguments from LogDetailsActivity
            else {
                dive_number = getArguments().getString("dive_number");
                dive_item = getArguments().getParcelable("dive_item");

                air_temp = dive_item.getAirTemp();
                surface_temp = dive_item.getSurfaceTemp();
                bottom_temp = dive_item.getBottomTemp();
                visibility = dive_item.getVisibility();
                water_type = dive_item.getWaterType();
                dive_type = dive_item.getDiveType();
            }
        }

        circumstances_data.add(air_temp);
        circumstances_data.add(surface_temp);
        circumstances_data.add(bottom_temp);
        circumstances_data.add(water_type);
        circumstances_data.add(dive_type);
        circumstances_data.add(visibility);

        text = (TextView) view.findViewById(R.id.finished_text_second);
        Button btn = (Button) view.findViewById(R.id.edit_secondnewdive_button);

        // when edit button is clicked, replace fragment with unfinished fragment
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getFragmentManager()
                        .beginTransaction();
                trans.replace(R.id.root_frame_second, new SecondFragUnfinished());
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
        if (circumstances_data != null) {
            for (String detail : circumstances_data) {
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
