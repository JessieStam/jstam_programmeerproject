package jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import jstam.programmeerproject_scubascan.Activities.DiveLogDetailsActivity;
import jstam.programmeerproject_scubascan.Activities.NewDiveActivity;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments.SecondNewDiveFragmentFinished;
import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 17/01/2017.
 */

public class SecondNewDiveFragment extends Fragment implements View.OnClickListener {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    String air_temp, surface_temp, bottom_temp, water_type, visibility, dive_type;
    EditText air_temp_input, surface_temp_input, bottom_temp_input, visibility_input;
    CheckBox salty, sweet, shore, boat;

    Button save_button;

    ImageView saved_image;

    //ThingsAdapter adapter;
    FragmentActivity listener;

    SecondNewDiveFragment.SecondNewDiveFragmentListener activityCommander;

//    public static SecondNewDiveFragment newInstance(int page) {
//
//        Log.d("Test", "In newInstance of SecondNewDiveFragment");
//
//        Bundle args = new Bundle();
//        args.putInt(ARG_PAGE, page);
//        SecondNewDiveFragment fragment = new SecondNewDiveFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    public interface SecondNewDiveFragmentListener {
        public void saveCircumstancesData(String air_temp, String surface_temp, String bottom_temp,
                                         String visibility, String water_type, String dive_type);
        public void showFragmentToast(String toast);
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
            activityCommander = (SecondNewDiveFragment.SecondNewDiveFragmentListener) context;
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
//        mPage = getArguments().getInt(ARG_PAGE);

    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_secondnewdive, parent, false);

        air_temp_input = (EditText) view.findViewById(R.id.newdive_temp_air_input);
        surface_temp_input = (EditText) view.findViewById(R.id.newdive_temp_surface_input);
        bottom_temp_input = (EditText) view.findViewById(R.id.newdive_temp_bottom_input);
        visibility_input = (EditText) view.findViewById(R.id.newdive_visibility_input);

        water_type = "";
        dive_type = "";

        salty = (CheckBox) view.findViewById(R.id.frag_secondnewdive_checkbox_salty);
        sweet = (CheckBox) view.findViewById(R.id.frag_secondnewdive_checkbox_sweet);
        shore = (CheckBox) view.findViewById(R.id.frag_secondnewdive_checkbox_shore);
        boat = (CheckBox) view.findViewById(R.id.frag_secondnewdive_checkbox_boat);

        salty.setOnClickListener(this);
        sweet.setOnClickListener(this);
        shore.setOnClickListener(this);
        boat.setOnClickListener(this);

        save_button = (Button) view.findViewById(R.id.frag_secondnewdive_button);

        save_button.setOnClickListener(this);

        saved_image = (ImageView) view.findViewById(R.id.data_saved_image2);

        return view;
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //ListView lv = (ListView) view.findViewById(R.id.lvSome);
        //lv.setAdapter(adapter);
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

        if (view == salty) {
            water_type = "salty";
            sweet.setChecked(false);
        } else if (view == sweet) {
            water_type = "sweet";
            salty.setChecked(false);
        } else if (view == shore) {
            dive_type = "boat";
            boat.setChecked(false);
        } else if (view == boat) {
            dive_type = "boat";
            shore.setChecked(false);
        } else if (view == save_button) {

            air_temp = air_temp_input.getText().toString();
            surface_temp = surface_temp_input.getText().toString();
            bottom_temp = bottom_temp_input.getText().toString();
            visibility = visibility_input.getText().toString();

            if (validateForm()) {

                if (saved_image.getVisibility() == View.INVISIBLE) {
                    saved_image.setVisibility(View.VISIBLE);
                    save_button.setText("Edit");
                }
                else {
                    saved_image.setVisibility(View.INVISIBLE);
                    save_button.setText("Save");
                }

                activityCommander.saveCircumstancesData(air_temp, surface_temp, bottom_temp,
                        visibility, water_type, dive_type);

                Fragment new_frag = new SecondNewDiveFragmentFinished();

                Bundle data_input = new Bundle();

                FragmentTransaction trans = getFragmentManager().beginTransaction();

                data_input.putString("air_temp", air_temp);
                data_input.putString("surface_temp", surface_temp);
                data_input.putString("bottom_temp", bottom_temp);
                data_input.putString("visibility", visibility);
                data_input.putString("water_type", water_type);
                data_input.putString("dive_type", dive_type);

                new_frag.setArguments(data_input);

                trans.replace(R.id.root_frame_second, new_frag);
                trans.commit();
            }
            else {
                Log.d("Test", "Parameters incomplete...");
            }
        }
    }

    /* Validate user's data. */
    private boolean validateForm() {
        boolean valid = true;

        if (TextUtils.isEmpty(air_temp)) {
            air_temp_input.setError("Required.");
            valid = false;
        } else {
            air_temp_input.setError(null);
        }

        if (TextUtils.isEmpty(surface_temp)) {
            surface_temp_input.setError("Required.");
            valid = false;
        } else {
            surface_temp_input.setError(null);
        }

        if (TextUtils.isEmpty(bottom_temp)) {
            bottom_temp_input.setError("Required.");
            valid = false;
        } else {
            bottom_temp_input.setError(null);
        }

        if (TextUtils.isEmpty(visibility)) {
            visibility_input.setError("Required.");
            valid = false;
        } else {
            visibility_input.setError(null);
        }

        if (water_type.equals("") || dive_type.equals("")) {
            activityCommander.showFragmentToast("Don't forget the checkboxes!.");
            valid = false;
        }

        return valid;
    }
}
