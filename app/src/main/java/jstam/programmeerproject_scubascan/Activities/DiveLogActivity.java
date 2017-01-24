package jstam.programmeerproject_scubascan.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jstam.programmeerproject_scubascan.Helpers.DiveListAdapter;
import jstam.programmeerproject_scubascan.Items.DiveItem;
import jstam.programmeerproject_scubascan.R;
import jstam.programmeerproject_scubascan.Helpers.ToolbarHelper;

/**
 * Created by Jessie on 12/01/2017.
 */

public class DiveLogActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ToolbarHelper toolbar_helper;

    RecyclerView dive_list;
    LinearLayoutManager layout_manager;
    DiveListAdapter adapter;

    ArrayList<String> dive_number_list;
    ArrayList<String> location_list;
    ArrayList<String> date_list;

    ArrayList<DiveItem> firebase_dive_list;

    FirebaseDatabase my_database;
    DatabaseReference database_ref;

    FirebaseAuth mAuth;
    String user_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dive_log);

        // construct toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_helper = new ToolbarHelper();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebase_user = mAuth.getCurrentUser();
        user_id = firebase_user.getUid();

        my_database = FirebaseDatabase.getInstance();
        database_ref = my_database.getReference();

        dive_list = (RecyclerView) findViewById(R.id.dive_log_list);

        // use a linear layout manager on RecyclerView
        layout_manager = new LinearLayoutManager(this);
        dive_list.setLayoutManager(layout_manager);

        dive_number_list = new ArrayList<>();
        location_list = new ArrayList<>();
        date_list = new ArrayList<>();

        // haal shit van firebase
        firebase_dive_list = getLogFromFirebase();

        // fill the list
        fillDiveLog(firebase_dive_list);

        // create new BooksAdapter object and set to RecyclerView
        adapter = new DiveListAdapter(this, dive_number_list, location_list, date_list);
        dive_list.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu toolbar) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.toolbar_main, toolbar);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem toolbar) {

        // display toast for clicked toolbar item
        String clicked_item = toolbar_helper.getClickedMenuItem(toolbar, this);
        Toast.makeText(this, clicked_item, Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(toolbar);
    }

    public ArrayList<DiveItem> getLogFromFirebase() {

        final ArrayList<DiveItem> list = new ArrayList<>();

        database_ref.child(user_id).child("dive_log").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> dive_log = dataSnapshot.getChildren();

                for (DataSnapshot dive : dive_log) {

                    DiveItem dive_item = dive.getValue(DiveItem.class);
                    list.add(dive_item);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("test4", "in onCancelled");
            }
        });

        return list;
    }

    public void fillDiveLog(ArrayList<DiveItem> dives) {

        // remove old data
        dive_number_list.clear();
        location_list.clear();
        date_list.clear();

        if (dives.size() != 0) {
            for (DiveItem dive : dives){
                dive_number_list.add(String.valueOf(dive.getDiveNumber()));
                location_list.add(dive.getCountry());
                date_list.add(dive.getDate());
            }
        }
    }

}
