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
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.SecondNewDiveFragment;
import jstam.programmeerproject_scubascan.Helpers.FinishedDiveDisplayManager;
import jstam.programmeerproject_scubascan.Items.DiveItem;
import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 22/01/2017.
 */

public class SecondNewDiveFragmentFinished extends Fragment {

    FragmentActivity listener;
    String air_temp, surface_temp, bottom_temp, water_type, visibility, dive_type, dive_number;
    ArrayList<String> circumstances_data;
    TextView text;
    InputStream displaytext;
    FinishedDiveDisplayManager display_manager;

    DiveItem dive_item;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {

//            date = savedInstanceState.getString("date");
//            country = savedInstanceState.getString("country");
//            dive_spot = savedInstanceState.getString("dive_spot");
//            buddy = savedInstanceState.getString("buddy");

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater
                .inflate(R.layout.fragment_secondnewdivefinished, container, false);

        displaytext = getResources().openRawResource(R.raw.finisheddive_page2);

        // if savedInstanceState is empty, create new manager object
        if (savedInstanceState == null) {

            display_manager = new FinishedDiveDisplayManager(displaytext);
            display_manager.read(displaytext);

            circumstances_data = new ArrayList<>();

        } else {

//            date = savedInstanceState.getString("date");
//            country = savedInstanceState.getString("country");
//            dive_spot = savedInstanceState.getString("dive_spot");
//            buddy = savedInstanceState.getString("buddy");

            display_manager = (FinishedDiveDisplayManager) savedInstanceState.getSerializable("manager");

        }

        if (getArguments() != null) {

            Log.d("test", "finished secondnewdive getarguments are NOT null");

            if (getArguments().getString("dive_number") == null){

                air_temp = getArguments().getString("air_temp");
                surface_temp = getArguments().getString("surface_temp");
                bottom_temp = getArguments().getString("bottom_temp");
                visibility = getArguments().getString("visibility");
                water_type = getArguments().getString("water_type");
                dive_type = getArguments().getString("dive_type");

            } else {

                dive_number = getArguments().getString("dive_number");
                dive_item = getArguments().getParcelable("dive_item");

                air_temp = dive_item.getAirTemp();
                surface_temp = dive_item.getSurfaceTemp();
                bottom_temp = dive_item.getBottomTemp();
                visibility = dive_item.getVisibility();
                water_type = dive_item.getWaterType();
                dive_type = dive_item.getDiveType();

            }


        } else {
            Log.d("test", "finished secondnewdive getarguments are null");
        }

        circumstances_data.add(air_temp);
        circumstances_data.add(surface_temp);
        circumstances_data.add(bottom_temp);
        circumstances_data.add(water_type);
        circumstances_data.add(dive_type);
        circumstances_data.add(visibility);

        text = (TextView) view.findViewById(R.id.finished_text_second);
        Button btn = (Button) view.findViewById(R.id.edit_secondnewdive_button);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getFragmentManager()
                        .beginTransaction();
                trans.replace(R.id.root_frame_second, new SecondNewDiveFragment());
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        if (circumstances_data != null) {

            for (String detail : circumstances_data) {

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
