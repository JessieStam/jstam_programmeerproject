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
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.FourthNewDiveFragment;
import jstam.programmeerproject_scubascan.Helpers.FinishedDiveDisplayManager;
import jstam.programmeerproject_scubascan.Items.DiveItem;
import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 23/01/2017.
 */

public class FourthNewDiveFragmentFinished extends android.support.v4.app.Fragment {

    FragmentActivity listener;
    ArrayList<String> technicaldata_list;
    TextView text;
    InputStream displaytext;
    FinishedDiveDisplayManager display_manager;

    String time_in, time_out, pressure_in, pressure_out, depth, safetystop, dive_number;

    DiveItem dive_item;

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
                .inflate(R.layout.fragment_fourthnewdivefinished, container, false);

        displaytext = getResources().openRawResource(R.raw.finisheddive_page4);

        // if savedInstanceState is empty, create new manager object
        if (savedInstanceState == null) {

            display_manager = new FinishedDiveDisplayManager(displaytext);
            display_manager.read(displaytext);

            technicaldata_list = new ArrayList<>();

        } else {

//            date = savedInstanceState.getString("date");
//            country = savedInstanceState.getString("country");
//            dive_spot = savedInstanceState.getString("dive_spot");
//            buddy = savedInstanceState.getString("buddy");

            display_manager = (FinishedDiveDisplayManager) savedInstanceState.getSerializable("manager");

        }

        if (getArguments() != null) {

            if (getArguments().getString("dive_number") == null){

                Log.d("test", "finished fourthnewdive getarguments are NOT null");
                technicaldata_list = getArguments().getStringArrayList("technicaldata");

            } else {

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



        } else {
            Log.d("test", "finished fourthnewdive getarguments are null");
        }

        text = (TextView) view.findViewById(R.id.finished_text_fourth);
        Button btn = (Button) view.findViewById(R.id.edit_fourthnewdive_button);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getFragmentManager()
                        .beginTransaction();
                trans.replace(R.id.root_frame_fourth, new FourthNewDiveFragment());
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Log.d("test2", "onviewcreated");

        if (technicaldata_list != null) {

            for (String item : technicaldata_list) {
                Log.d("test2", "item in technical_data list = " + item);

                display_manager.fillInPlaceholder(item);
            }

            // create boolean to check if isFilledIn function returns true
            boolean filledIn = display_manager.isFilledIn();

            // when everything is filled in, move on to fourth Activity to print story
            if (filledIn) {

                Log.d("test2", "is filled in");

                String final_display = display_manager.toString();

                if (Build.VERSION.SDK_INT >= 24) {
                    text.setText(Html.fromHtml(final_display, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    text.setText(Html.fromHtml(final_display));
                }
            }

        } else {
            Log.d("test2", "onviewcreated: data list is null");
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