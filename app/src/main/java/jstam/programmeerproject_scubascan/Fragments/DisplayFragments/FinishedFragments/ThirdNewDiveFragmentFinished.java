package jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.ThirdNewDiveFragment;
import jstam.programmeerproject_scubascan.Helpers.FinishedDiveDisplayManager;
import jstam.programmeerproject_scubascan.Items.DiveItem;
import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 22/01/2017.
 */

public class ThirdNewDiveFragmentFinished extends android.support.v4.app.Fragment {

    FragmentActivity listener;
    String lead, dive_number;
    ArrayList<String> clothes;
    TextView text;
    InputStream displaytext;
    FinishedDiveDisplayManager display_manager;

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
                .inflate(R.layout.fragment_thirdnewdivefinished, container, false);

        displaytext = getResources().openRawResource(R.raw.finisheddive_page3);

        // if savedInstanceState is empty, create new manager object
        if (savedInstanceState == null) {

            display_manager = new FinishedDiveDisplayManager(displaytext);
            display_manager.read(displaytext);

            clothes = new ArrayList<>();

        } else {

//            date = savedInstanceState.getString("date");
//            country = savedInstanceState.getString("country");
//            dive_spot = savedInstanceState.getString("dive_spot");
//            buddy = savedInstanceState.getString("buddy");

            display_manager = (FinishedDiveDisplayManager) savedInstanceState.getSerializable("manager");

        }

        if (getArguments() != null) {

            Log.d("test", "finished thirdnewdive getarguments are NOT null");

            if (getArguments().getString("dive_number") == null) {

                clothes = getArguments().getStringArrayList("clothes_strings");
                lead = getArguments().getString("lead");

            } else {

                dive_number = getArguments().getString("dive_number");
                dive_item = getArguments().getParcelable("dive_item");

                clothes = dive_item.getClothingList();
                lead = dive_item.getLead();

            }


            for (String item : clothes) {
                Log.d("test", "clothes item: " + item);
            }

        } else {
            Log.d("test", "finished thirdnewdive getarguments are null");
        }

        text = (TextView) view.findViewById(R.id.finished_text_third);
        Button btn = (Button) view.findViewById(R.id.edit_thirdnewdive_button);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getFragmentManager()
                        .beginTransaction();
                trans.replace(R.id.root_frame_third, new ThirdNewDiveFragment());
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        if (lead != null && clothes != null) {

            display_manager.fillInPlaceholder(lead);
            String placeholder_string = "";

            for (String item : clothes) {

                if (clothes.indexOf(item) != (clothes.size() -1)) {
                    placeholder_string += "</b>a <b>" + item + ", ";
                } else {
                    placeholder_string += "</b>and a <b>" + item + ".";
                }
            }

            display_manager.fillInPlaceholder(placeholder_string);

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
