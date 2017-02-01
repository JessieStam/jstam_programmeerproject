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
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
////                if (user != null) {
////                    // User is signed in
////                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
////                } else {
////                    // User is signed out
////                    Log.d(TAG, "onAuthStateChanged:signed_out");
////                }
//            }
//        };

        //my_database = FirebaseDatabase.getInstance().getReference();

        mAuth.signOut();

//        Intent backToHome = new Intent (current_activity, HomeActivity.class);
//        startActivity(backToHome);


    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }


}
