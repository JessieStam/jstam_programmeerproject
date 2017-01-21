package jstam.programmeerproject_scubascan.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import jstam.programmeerproject_scubascan.Activities.NewDiveActivity;
import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 21/01/2017.
 */

public class FifthNewDiveFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    String notes;
    EditText notes_input;

    Button save_button;

    ImageView saved_image;

    //ThingsAdapter adapter;
    FragmentActivity listener;

    FifthNewDiveFragment.FifthNewDiveFragmentListener activityCommander;

    public static FifthNewDiveFragment newInstance(int page) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FifthNewDiveFragment fragment = new FifthNewDiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface FifthNewDiveFragmentListener {
        public void saveExtraData(String notes);
    }

    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewDiveActivity){
            this.listener = (FragmentActivity) context;
        }

        try{
            activityCommander = (FifthNewDiveFragment.FifthNewDiveFragmentListener) context;
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
        mPage = getArguments().getInt(ARG_PAGE);

    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fifthnewdive, parent, false);

        notes_input = (EditText) view.findViewById(R.id.newdive_notes_input);
        notes = "";

        save_button = (Button) view.findViewById(R.id.frag_fifthnewdive_button);

        saved_image = (ImageView) view.findViewById(R.id.data_saved_image5);

        save_button.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        buttonClicked(v);
                    }
                }
        );

        return view;
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {



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

    private void buttonClicked(View v) {

        Log.d("test", "fifth button was clicked");

        notes = notes_input.getText().toString();

        if (!notes.equals("")) {

            if (saved_image.getVisibility() == View.INVISIBLE) {
                saved_image.setVisibility(View.VISIBLE);
                save_button.setText("Edit");
            }
            else {
                saved_image.setVisibility(View.INVISIBLE);
                save_button.setText("Save");
            }

            activityCommander.saveExtraData(notes);
        }
        else {
            Log.d("Test", "Parameters incomplete...");
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d("test", "fifth fragment was stopped");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // save data
        Log.d("test", "fifth fragments view was destroyed");

    }

}
