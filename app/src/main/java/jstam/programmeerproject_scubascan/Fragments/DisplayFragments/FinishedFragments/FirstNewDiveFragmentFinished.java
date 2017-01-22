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

import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.FirstNewDiveFragment;
import jstam.programmeerproject_scubascan.Helpers.FinishedDiveDisplayManager;
import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 21/01/2017.
 */

public class FirstNewDiveFragmentFinished extends Fragment {

    FragmentActivity listener;
    String date, country, dive_spot, buddy;
    ArrayList<String> general_data;
    TextView text;
    InputStream displaytext;
    FinishedDiveDisplayManager display_manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {

            date = savedInstanceState.getString("date");
            country = savedInstanceState.getString("country");
            dive_spot = savedInstanceState.getString("dive_spot");
            buddy = savedInstanceState.getString("buddy");

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater
                .inflate(R.layout.fragment_firstnewdivefinished, container, false);

        displaytext = getResources().openRawResource(R.raw.finisheddive_page1);

        // if savedInstanceState is empty, create new manager object
        if (savedInstanceState == null) {

            display_manager = new FinishedDiveDisplayManager(displaytext);
            display_manager.read(displaytext);

            general_data = new ArrayList<>();

        } else {

            date = savedInstanceState.getString("date");
            country = savedInstanceState.getString("country");
            dive_spot = savedInstanceState.getString("dive_spot");
            buddy = savedInstanceState.getString("buddy");

            display_manager = (FinishedDiveDisplayManager) savedInstanceState.getSerializable("manager");

        }

        if (getArguments() != null) {

            Log.d("test", "finished firstnewdive getarguments are null :(");

            date = getArguments().getString("date");
            country = getArguments().getString("country");
            dive_spot = getArguments().getString("dive_spot");
            buddy = getArguments().getString("buddy");
        }

        general_data.add(date);
        general_data.add(buddy);
        general_data.add(country);
        general_data.add(dive_spot);

        text = (TextView) view.findViewById(R.id.finished_text);
        Button btn = (Button) view.findViewById(R.id.edit_firstnewdive_button);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getFragmentManager()
                        .beginTransaction();
                trans.replace(R.id.root_frame, new FirstNewDiveFragment());
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        if (general_data != null) {

            for (String detail : general_data) {

                Log.d("test", "detail is " + detail);

                display_manager.fillInPlaceholder(detail);
            }

            // create boolean to check if isFilledIn function returns true
            boolean filledIn = display_manager.isFilledIn();

            // when everything is filled in, move on to Third Activity to print story
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

        outState.putString("date", date);
        outState.putString("country", country);
        outState.putString("dive_spot", dive_spot);
        outState.putString("buddy", buddy);
        outState.putSerializable("manager", display_manager);

    }
}
