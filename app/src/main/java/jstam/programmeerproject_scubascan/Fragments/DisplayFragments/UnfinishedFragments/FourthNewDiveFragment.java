package jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import jstam.programmeerproject_scubascan.Activities.DiveLogDetailsActivity;
import jstam.programmeerproject_scubascan.Activities.NewDiveActivity;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments.FourthNewDiveFragmentFinished;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments.ThirdNewDiveFragmentFinished;
import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 19/01/2017.
 */

public class FourthNewDiveFragment extends Fragment implements View.OnClickListener {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    String time_in, time_out, pressure_in, pressure_out, depth, safetystop;
    ArrayList<String> technicaldata_list;

    EditText time_in_input, time_out_input, pressure_in_input, pressure_out_input, depth_input;

    CheckBox yes, no;

    Button save_button;

    ImageView saved_image;

    //ThingsAdapter adapter;
    FragmentActivity listener;

    FourthNewDiveFragment.FourthNewDiveFragmentListener activityCommander;

//    public static FourthNewDiveFragment newInstance(int page) {
//
//        Bundle args = new Bundle();
//        args.putInt(ARG_PAGE, page);
//        FourthNewDiveFragment fragment = new FourthNewDiveFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    public interface FourthNewDiveFragmentListener {
        public void saveTechnicalData(String time_in, String time_out, String pressure_in,
                                      String pressure_out, String depth, String safetystop);
    }

    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewDiveActivity){
            this.listener = (FragmentActivity) context;
        } else if (context instanceof DiveLogDetailsActivity) {
            this.listener = (FragmentActivity) context;
        }

        try{
            activityCommander = (FourthNewDiveFragment.FourthNewDiveFragmentListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }

    }

    // This event fires 2nd, before views are created for the fragment
    // The onCreate method is called when the Fragment instance is being created, or re-created.
    // Use onCreate for any standard setup that does not require the activity to be fully created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ArrayList<Thing> things = new ArrayList<Thing>();
        //adapter = new ThingsAdapter(getActivity(), things);
        //mPage = getArguments().getInt(ARG_PAGE);

    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fourthnewdive, parent, false);

        time_in_input = (EditText) view.findViewById(R.id.newdive_timein_input);
        time_out_input = (EditText) view.findViewById(R.id.newdive_timeout_input);
        pressure_in_input = (EditText) view.findViewById(R.id.newdive_pressurein_input);
        pressure_out_input = (EditText) view.findViewById(R.id.newdive_pressureout_input);
        depth_input = (EditText) view.findViewById(R.id.newdive_depth_input);

        technicaldata_list = new ArrayList<>();

        safetystop = "";

        save_button = (Button) view.findViewById(R.id.frag_fourthnewdive_button);

        saved_image = (ImageView) view.findViewById(R.id.data_saved_image4);

        return view;
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        save_button.setOnClickListener(this);
        yes = (CheckBox) view.findViewById(R.id.frag_fourthnewdive_checkbox_yes);
        no = (CheckBox) view.findViewById(R.id.frag_fourthnewdive_checkbox_no);

        view.findViewById(R.id.frag_fourthnewdive_checkbox_yes).setOnClickListener(this);
        view.findViewById(R.id.frag_fourthnewdive_checkbox_no).setOnClickListener(this);

    }

    // This method is called when the fragment is no longer connected to the Activity
    // Any references saved in onAttach should be nulled out here to prevent memory leaks.
    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    // This method is called after the parent Activity's onCreate() method has completed.
    // Accessing the view hierarchy of the parent activity must be done in the onActivityCreated.
    // At this point, it is safe to search for activity View objects by their ID, for example.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {

        Log.d("test", "something in fourth is clicked");

        if (view == no) {
            yes.setChecked(false);
            safetystop = "no safetystop.";
        } else if (view == yes) {
            no.setChecked(false);
            safetystop = "</b>a<b> safetystop.";
        }
        else if (view == save_button) {

            Log.d("test", "save button 4 clicked");

            time_in = time_in_input.getText().toString();
            time_out = time_out_input.getText().toString();
            pressure_in = pressure_in_input.getText().toString();
            pressure_out = pressure_out_input.getText().toString();
            depth = depth_input.getText().toString();

            technicaldata_list.add(time_in);
            technicaldata_list.add(time_out);
            technicaldata_list.add(pressure_in);
            technicaldata_list.add(pressure_out);
            technicaldata_list.add(depth);

            if (time_in != null && time_out != null && pressure_in != null && pressure_out != null
                    && depth != null && !safetystop.equals("")) {

                technicaldata_list.add(safetystop);

                if (saved_image.getVisibility() == View.INVISIBLE) {
                    saved_image.setVisibility(View.VISIBLE);
                    save_button.setText("Edit");
                }
                else {
                    saved_image.setVisibility(View.INVISIBLE);
                    save_button.setText("Save");
                }

                activityCommander.saveTechnicalData(time_in, time_out, pressure_in, pressure_out,
                        depth, safetystop);

                // fragment transaction
                Fragment new_frag = new FourthNewDiveFragmentFinished();
                Bundle data_input = new Bundle();
                FragmentTransaction trans = getFragmentManager().beginTransaction();

                data_input.putStringArrayList("technicaldata", technicaldata_list);

                new_frag.setArguments(data_input);

                trans.replace(R.id.root_frame_fourth, new_frag);
                trans.commit();

            }
            else {
                Log.d("Test", "Parameters incomplete...");
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d("test", "fourth fragment was stopped");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // save data
        Log.d("test", "fourth fragments view was destroyed");

    }
}
