package jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments;

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

import java.util.ArrayList;

import jstam.programmeerproject_scubascan.Activities.NewDiveActivity;
import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 18/01/2017.
 */

public class ThirdNewDiveFragment extends Fragment implements View.OnClickListener {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    ArrayList<String> clothes;

    Boolean bool_swimsuit, bool_shorty, bool_wetsuit, bool_drysuit, bool_hood, bool_gloves,
            bool_fins, bool_shoes, bool_flashlight, bool_mask;

    CheckBox swimsuit, shorty, wetsuit, drysuit, hood, gloves, fins, shoes, flashlight, mask;

    String lead = "";
    EditText lead_input;

    Button save_button;

    ImageView saved_image;

    //ThingsAdapter adapter;
    FragmentActivity listener;

    ThirdNewDiveFragment.ThirdNewDiveFragmentListener activityCommander;

    public static ThirdNewDiveFragment newInstance(int page) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ThirdNewDiveFragment fragment = new ThirdNewDiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface ThirdNewDiveFragmentListener {
        public void saveEquipmentData(String lead, ArrayList<String> clothes);
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
            activityCommander = (ThirdNewDiveFragment.ThirdNewDiveFragmentListener) context;
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
        clothes = new ArrayList<>();

    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_thirdnewdive, parent, false);

        lead_input = (EditText) view.findViewById(R.id.newdive_lead_input);
        save_button = (Button) view.findViewById(R.id.frag_thirdnewdive_button);
        saved_image = (ImageView) view.findViewById(R.id.data_saved_image3);

        return view;
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //ListView lv = (ListView) view.findViewById(R.id.lvSome);
        //lv.setAdapter(adapter);

        save_button.setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_swimsuit).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_shorty).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_wetsuit).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_drysuit).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_hood).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_gloves).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_fins).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_shoes).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_flash).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_mask).setOnClickListener(this);

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

        Log.d("test", "something in third is clicked");

        int id = view.getId();

        if (id == R.id.frag_thirdnewdive_checkbox_swimsuit || id == R.id.frag_thirdnewdive_checkbox_shorty
                || id == R.id.frag_thirdnewdive_checkbox_wetsuit || id == R.id.frag_thirdnewdive_checkbox_drysuit
                || id == R.id.frag_thirdnewdive_checkbox_hood || id == R.id.frag_thirdnewdive_checkbox_gloves
                || id == R.id.frag_thirdnewdive_checkbox_fins || id == R.id.frag_thirdnewdive_checkbox_shoes
                || id == R.id.frag_thirdnewdive_checkbox_flash || id == R.id.frag_thirdnewdive_checkbox_mask) {

            String item_name = getResources().getResourceEntryName(id)
                    .replace("frag_thirdnewdive_checkbox_", "");

            Log.d("test", "name of clicked item is " + item_name);

            checkIfInList(item_name);

        } else if (id == R.id.frag_thirdnewdive_button) {

            Log.d("test", "save button 3 clicked");

            lead = lead_input.getText().toString();

            if (!lead.equals("")) {

                if (saved_image.getVisibility() == View.INVISIBLE) {
                    saved_image.setVisibility(View.VISIBLE);
                    save_button.setText("Edit");
                }
                else {
                    saved_image.setVisibility(View.INVISIBLE);
                    save_button.setText("Save");
                }

                activityCommander.saveEquipmentData(lead, clothes);
            }
            else {
                Log.d("Test", "Parameters incomplete...");
            }
        }
    }

    public void checkIfInList(String item_name) {

        for (String item : clothes) {
            if (item.equals(item_name)) {
                clothes.remove(item);
                break;
            }
        }
        Log.d("test", "it still adds item_name to list");
        clothes.add(item_name);
    }
}
