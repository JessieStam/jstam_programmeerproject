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
        getLogFromFirebase();

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

    public void getLogFromFirebase() {

        database_ref.child("users").child(user_id).child("dive_log").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> dive_log = dataSnapshot.getChildren();

                for (DataSnapshot dive : dive_log) {

                    Log.d("test5", "in dive");

                    DiveItem dive_item = dive.getValue(DiveItem.class);
                    String dive_number = "Dive " + String.valueOf(dive_item.getDiveNumber());

                    dive_number_list.add(dive_number);
                    location_list.add(dive_item.getCountry());
                    date_list.add(dive_item.getDate());

                    Log.d("test5", "dive_number_list = " + String.valueOf(dive_number_list.size()));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // cancelled
            }
        });

    }
}
