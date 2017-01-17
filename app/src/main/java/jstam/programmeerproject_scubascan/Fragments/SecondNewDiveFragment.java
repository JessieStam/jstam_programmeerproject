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
import android.widget.Toast;

import jstam.programmeerproject_scubascan.Activities.NewDiveActivity;
import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 17/01/2017.
 */

public class SecondNewDiveFragment extends Fragment{

    String air_temp;
    String surface_temp;
    String bottom_temp;
    String water_type;
    String visibility;
    String dive_type;

    EditText air_temp_input;
    EditText surface_temp_input;
    EditText bottom_temp_input;
    EditText visibility_input;

    CheckBox salty;
    CheckBox sweet;
    CheckBox shore;
    CheckBox boat;

    Button save_button;


    //ThingsAdapter adapter;
    FragmentActivity listener;

    SecondNewDiveFragment.SecondNewDiveFragmentListener activityCommander;

    public interface SecondNewDiveFragmentListener {
        public void saveCircumstancesData(String air_temp, String surface_temp, String bottom_temp,
                                         String visibility, String water_type, String dive_type);
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

        save_button = (Button) view.findViewById(R.id.frag_secondnewdive_button);

        //view.findViewById(R.id.frag_firstnewdive_button).setOnClickListener(NewDiveActivity);
        save_button.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        buttonClicked(v);
                    }
                }
        );

        salty.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        checkboxClicked(v);
                    }
                }
        );

        sweet.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        checkboxClicked(v);
                    }
                }
        );

        shore.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        checkboxClicked(v);
                    }
                }
        );

        boat.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        checkboxClicked(v);
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

    public void buttonClicked(View view) {

        air_temp = air_temp_input.getText().toString();
        surface_temp = surface_temp_input.getText().toString();
        bottom_temp = bottom_temp_input.getText().toString();
        visibility = visibility_input.getText().toString();

        if (!air_temp.equals("") && !surface_temp.equals("") && !bottom_temp.equals("")
                && !visibility.equals("")) {
            activityCommander.saveCircumstancesData(air_temp, surface_temp, bottom_temp, visibility, water_type, dive_type);
        }
        else {
            Log.d("Test", "Parameters incomplete...");
        }

    }

    public void checkboxClicked(View checkbox) {

        if (checkbox == salty) {
            Log.d("test", "Salty is checked");
            water_type = "salty";
            sweet.setChecked(false);
        }
        else if (checkbox == sweet) {
            Log.d("test", "Sweet is checked");
            water_type = "sweet";
            salty.setChecked(false);
        }
        else if (checkbox == shore) {
            Log.d("test", "Shore is checked");
            dive_type = "shore";
            boat.setChecked(false);
        }
        else if (checkbox == boat) {
            Log.d("test", "Boat is checked");
            dive_type = "boat";
            shore.setChecked(false);
        }

    }
}
