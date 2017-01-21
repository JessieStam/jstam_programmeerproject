package jstam.programmeerproject_scubascan.Fragments;

import android.os.health.PackageHealthStats;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
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

    int current_fragment;

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

//    public static FirstNewDiveFragment newInstance(int page) {
//
//        Bundle args = new Bundle();
//        args.putInt(ARG_PAGE, page);
//        FirstNewDiveFragment fragment = new FirstNewDiveFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

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
//        mPage = getArguments().getInt(ARG_PAGE);

        if (savedInstanceState != null) {

            date = savedInstanceState.getString("date");
            country = savedInstanceState.getString("country");
            dive_spot = savedInstanceState.getString("dive_spot");
            buddy = savedInstanceState.getString("buddy");
            current_fragment = savedInstanceState.getInt("current_frag");

            date_input.setText(date);
            country_input.setText(country);
            dive_spot_input.setText(dive_spot);
            buddy_input.setText(buddy);

            Log.d("test", "saved instance state is NOT null in firstnewdivefragment");

        } else {

            current_fragment = 1;

            Log.d("test", "saved instance state is null in firstnewdivefragment");
        }

    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_firstnewdive, parent, false);

        if (current_fragment == 2) {

            FragmentTransaction trans = getFragmentManager().beginTransaction();
            trans.replace(R.id.root_frame, new FirstNewDiveFragmentFinished());

            trans.commit();

        }

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

        Log.d("test", "first new dive fragment is detached");
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

            current_fragment = 2;

            FragmentTransaction trans = getFragmentManager().beginTransaction();
            trans.replace(R.id.root_frame, new FirstNewDiveFragmentFinished());

            trans.commit();
        }
        else {
            Log.d("Test", "Parameters incomplete...");
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d("test", "first fragment was stopped");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // save data
        Log.d("test", "first fragments view was destroyed");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("date", date);
        outState.putString("country", country);
        outState.putString("dive_spot", dive_spot);
        outState.putString("buddy", buddy);
        outState.putInt("current_frag", current_fragment);

    }
//
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        // restore ArrayList of visible ImageViews
//        checked_images_list = savedInstanceState.getIntegerArrayList("savedList");
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        item_list.clear();
//        todo_list.clear();
//
//        // read SQLite database
//        db_list = db_helper.read_item();
//
//        // iterate over TodoItems in databases
//        for (HashMap<String, String> hashmap : db_list) {
//
//            //ave id, title and status
//            String retrieved_id = hashmap.get("_id");
//            String retrieved_title = hashmap.get("todo_text");
//            String retrieved_status = hashmap.get("current_status");
//
//            // recreate TodoItem and put in list
//            TodoItem new_item = todo_manager.create_item(retrieved_title);
//            new_item.setId(Integer.parseInt(retrieved_id));
//            new_item.setCurrentStatus(retrieved_status);
//
//            // put items back into the listview
//            item_list.add(new_item);
//            todo_list.add(retrieved_title);
//            todoAdapter.notifyDataSetChanged();
//        }
//    }

}
