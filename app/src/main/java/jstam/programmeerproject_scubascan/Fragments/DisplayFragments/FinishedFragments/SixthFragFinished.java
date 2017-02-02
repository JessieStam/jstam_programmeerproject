package jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import jstam.programmeerproject_scubascan.Helpers.Managers.TextDisplayManager;
import jstam.programmeerproject_scubascan.Items.DiveItem;
import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 31/01/2017.
 */

public class SixthFragFinished extends android.support.v4.app.Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_NUMBER = "ARG_NUMBER";
    public static final String ARG_DIVEITEM = "ARG_DIVEITEM";

    private int mPage;

    FragmentActivity listener;
    ArrayList<String> nitrogen_data;
    TextView text;
    InputStream displaytext;
    TextDisplayManager display_manager;

    String previous_level, nitrogen_level, interval_level, dive_number;

    DiveItem dive_item;

    public static SixthFragFinished newInstance(int page, String dive_number, DiveItem dive_item) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_NUMBER, dive_number);
        args.putParcelable(ARG_DIVEITEM, dive_item);
        SixthFragFinished fragment = new SixthFragFinished();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ArrayList<Thing> things = new ArrayList<Thing>();
        //adapter = new ThingsAdapter(getActivity(), things);
        mPage = getArguments().getInt(ARG_PAGE);
        dive_number = getArguments().getString(ARG_NUMBER);
        dive_item = getArguments().getParcelable(ARG_DIVEITEM);

        nitrogen_data = new ArrayList<>();

    }


    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater
                .inflate(R.layout.frag_sixth_finished, container, false);

        displaytext = getResources().openRawResource(R.raw.finisheddive_page6);

        text = (TextView) view.findViewById(R.id.finished_text_sixth);

        // if savedInstanceState is empty, create new manager object
        if (savedInstanceState == null) {

            display_manager = new TextDisplayManager(displaytext);
            display_manager.read(displaytext);


        } else {

//            date = savedInstanceState.getString("date");
//            country = savedInstanceState.getString("country");
//            dive_spot = savedInstanceState.getString("dive_spot");
//            buddy = savedInstanceState.getString("buddy");

            display_manager = (TextDisplayManager) savedInstanceState.getSerializable("manager");

        }

        if (getArguments() != null) {

            previous_level = dive_item.getPreviousLevel();
            nitrogen_level = dive_item.getNitrogenLevel();
            interval_level = dive_item.getIntervalLevel();


            if (previous_level == null) {
                previous_level = "none";
            }

            if (interval_level == null) {
                interval_level = "none";
            }

        } else {
            Log.d("test", "finished fourthnewdive getarguments are null");
        }

        nitrogen_data.add(previous_level);
        nitrogen_data.add(nitrogen_level);
        nitrogen_data.add(interval_level);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Log.d("test2", "onviewcreated");

        if (nitrogen_data != null) {

            for (String detail : nitrogen_data) {

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

//        outState.putString("date", date);
//        outState.putString("country", country);
//        outState.putString("dive_spot", dive_spot);
//        outState.putString("buddy", buddy);
//        outState.putSerializable("manager", display_manager);

    }
}