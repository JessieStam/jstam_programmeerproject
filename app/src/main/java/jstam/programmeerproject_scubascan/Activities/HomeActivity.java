package jstam.programmeerproject_scubascan.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;

import jstam.programmeerproject_scubascan.Helpers.NitrogenCalculator;
import jstam.programmeerproject_scubascan.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    public void moveToMenu() {
        Intent goToMenu = new Intent(this, MenuActivity.class);
        startActivity(goToMenu);

        finish();
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
}
