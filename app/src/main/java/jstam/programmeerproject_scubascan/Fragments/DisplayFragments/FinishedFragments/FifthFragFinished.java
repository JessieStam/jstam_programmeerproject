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

import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.FifthFragUnfinished;
import jstam.programmeerproject_scubascan.Helpers.Managers.TextDisplayManager;
import jstam.programmeerproject_scubascan.Items.DiveItem;
import jstam.programmeerproject_scubascan.R;

/**
 * Scuba Scan - FifthFragFinished
 *
 * Jessie Stam
 * 10560599
 *
 * This fragment displays notes.
 */
public class FifthFragFinished extends android.support.v4.app.Fragment {

    String notes, dive_number;
    TextView text;
    InputStream displaytext;
    TextDisplayManager display_manager;
    DiveItem dive_item;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.frag_fifth_finished, container, false);

        // get text file for displaying
        displaytext = getResources().openRawResource(R.raw.finisheddive_page5);

        // read display text
        display_manager = new TextDisplayManager(displaytext);
        display_manager.read(displaytext);

        // get arguments to fill in placeholders in display text
        if (getArguments() != null) {

            // get arguments from unfinished fragment
            if (getArguments().getString("dive_number") == null){
                Log.d("test", "finished fifthnewdive getarguments are NOT null");
                notes = getArguments().getString("notes");
            }
            // get arguments from LogDetailsActivity
            else {
                dive_number = getArguments().getString("dive_number");
                dive_item = getArguments().getParcelable("dive_item");

                notes = dive_item.getNotes();
            }
        }

        text = (TextView) view.findViewById(R.id.finished_text_fifth);
        Button btn = (Button) view.findViewById(R.id.edit_fifthnewdive_button);

        // when edit button is clicked, replace fragment with unfinished fragment
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getFragmentManager()
                        .beginTransaction();
                trans.replace(R.id.root_frame_fifth, new FifthFragUnfinished());
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
        if (notes != null) {
            display_manager.fillInPlaceholder(notes);

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