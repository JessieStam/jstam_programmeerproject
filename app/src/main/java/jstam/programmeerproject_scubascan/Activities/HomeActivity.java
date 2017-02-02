package jstam.programmeerproject_scubascan.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;

import java.io.IOException;
import java.io.InputStream;

import jstam.programmeerproject_scubascan.Helpers.NitrogenCalculator;
import jstam.programmeerproject_scubascan.R;

public class HomeActivity extends AppCompatActivity {

    private boolean exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        exit = false;

    }

    public void signUpUser(View view) {

        String title = "Signing up";
        String instr = "Welcome! Enter your e-mail adress and password to sign up.";
        String pass_confirm = "visible";

        Intent signUpUser = new Intent(this, LoginActivity.class);

        signUpUser.putExtra("log_sign", title);
        signUpUser.putExtra("instr", instr);
        signUpUser.putExtra("pass_confirm", pass_confirm);

        startActivity(signUpUser);
    }

    public void logInUser(View view) {

        String title = "Logging in";
        String instr = "Welcome back! You know the drill: e-mail and password to continue.";
        String pass_confirm = "invisible";

        Intent logInUser = new Intent(this, LoginActivity.class);

        logInUser.putExtra("log_sign", title);
        logInUser.putExtra("instr", instr);
        logInUser.putExtra("pass_confirm", pass_confirm);

        startActivity(logInUser);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }
}
