package jstam.programmeerproject_scubascan.Helpers;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import jstam.programmeerproject_scubascan.Activities.HomeActivity;

/**
 * Created by Jessie on 01/02/2017.
 */

public class LogoutHelper {

    private static final String TAG = "LogoutHelper";

    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    /* Sign user out. */
    public void signOut() {

        DatabaseReference my_database;

//        // listener for auth state
        mAuth = FirebaseAuth.getInstance();


        mAuth.signOut();

    }

}
