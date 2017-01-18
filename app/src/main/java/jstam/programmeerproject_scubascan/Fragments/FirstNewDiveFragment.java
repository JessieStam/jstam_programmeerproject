package jstam.programmeerproject_scubascan.Fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import jstam.programmeerproject_scubascan.Activities.NewDiveActivity;
import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 16/01/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class FirstNewDiveFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    String date;
    String country;
    String dive_spot;
    String buddy;

    EditText date_input;
    EditText country_input;
    EditText dive_spot_input;
    EditText buddy_input;

    Button next_button;

    ImageView saved_image;

    //ThingsAdapter adapter;
    FragmentActivity listener;

    FirstNewDiveFragmentListener activityCommander;

    public static FirstNewDiveFragment newInstance(int page) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FirstNewDiveFragment fragment = new FirstNewDiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface FirstNewDiveFragmentListener {
        public void saveGeneralData(String date, String country, String dive_spot, String buddy);
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
            activityCommander = (FirstNewDiveFragmentListener) context;
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
        View view =  inflater.inflate(R.layout.fragment_firstnewdive, parent, false);

        date_input = (EditText) view.findViewById(R.id.newdive_date_input);
        country_input = (EditText) view.findViewById(R.id.newdive_country_input);
        dive_spot_input = (EditText) view.findViewById(R.id.newdive_divespot_input);
        buddy_input = (EditText) view.findViewById(R.id.newdive_buddy_input);

        next_button = (Button) view.findViewById(R.id.frag_firstnewdive_button);

        saved_image = (ImageView) view.findViewById(R.id.data_saved_image1);

        //view.findViewById(R.id.frag_firstnewdive_button).setOnClickListener(NewDiveActivity);
        next_button.setOnClickListener(
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

        date = date_input.getText().toString();
        country = country_input.getText().toString();
        dive_spot = dive_spot_input.getText().toString();
        buddy = buddy_input.getText().toString();

        if (!date.equals("") && !country.equals("") && !dive_spot.equals("") && !buddy.equals("")) {

            if (saved_image.getVisibility() == View.INVISIBLE) {
                saved_image.setVisibility(View.VISIBLE);
                next_button.setText("Edit");
            }
            else {
                saved_image.setVisibility(View.INVISIBLE);
                next_button.setText("Save");
            }

            activityCommander.saveGeneralData(date, country, dive_spot, buddy);
        }
        else {
            Log.d("Test", "Parameters incomplete...");
        }

    }

}