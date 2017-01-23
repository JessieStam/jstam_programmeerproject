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

import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.FifthNewDiveFragment;
import jstam.programmeerproject_scubascan.Helpers.FinishedDiveDisplayManager;
import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 23/01/2017.
 */

public class FifthNewDiveFragmentFinished extends android.support.v4.app.Fragment {

    FragmentActivity listener;
    String notes;
    TextView text;
    InputStream displaytext;
    FinishedDiveDisplayManager display_manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (savedInstanceState != null) {
//
////            date = savedInstanceState.getString("date");
////            country = savedInstanceState.getString("country");
////            dive_spot = savedInstanceState.getString("dive_spot");
////            buddy = savedInstanceState.getString("buddy");
//
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater
                .inflate(R.layout.fragment_fifthnewdivefinished, container, false);

        displaytext = getResources().openRawResource(R.raw.finisheddive_page5);

        // if savedInstanceState is empty, create new manager object
        if (savedInstanceState == null) {

            display_manager = new FinishedDiveDisplayManager(displaytext);
            display_manager.read(displaytext);

        } else {

//            date = savedInstanceState.getString("date");
//            country = savedInstanceState.getString("country");
//            dive_spot = savedInstanceState.getString("dive_spot");
//            buddy = savedInstanceState.getString("buddy");

            display_manager = (FinishedDiveDisplayManager) savedInstanceState.getSerializable("manager");

        }

        if (getArguments() != null) {

            Log.d("test", "finished fifthnewdive getarguments are NOT null");
            notes = getArguments().getString("notes");

        } else {
            Log.d("test", "finished fifthnewdive getarguments are null");
        }

        text = (TextView) view.findViewById(R.id.finished_text_fifth);
        Button btn = (Button) view.findViewById(R.id.edit_fifthnewdive_button);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getFragmentManager()
                        .beginTransaction();
                trans.replace(R.id.root_frame_fifth, new FifthNewDiveFragment());
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        if (notes != null) {

            display_manager.fillInPlaceholder(notes);


            // create boolean to check if isFilledIn function returns true
            boolean filledIn = display_manager.isFilledIn();

            // when everything is filled in, move on to fifth Activity to print story
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

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;

        Log.d("test", "finished fragment is detached");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//        outState.putString("date", date);
//        outState.putString("country", country);
//        outState.putString("dive_spot", dive_spot);
//        outState.putString("buddy", buddy);
//        outState.putSerializable("manager", display_manager);

    }
}