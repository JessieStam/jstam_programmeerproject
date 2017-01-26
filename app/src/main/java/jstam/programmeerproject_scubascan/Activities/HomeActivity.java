package jstam.programmeerproject_scubascan.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

import jstam.programmeerproject_scubascan.Helpers.NitrogenCalculator;
import jstam.programmeerproject_scubascan.R;

public class HomeActivity extends AppCompatActivity {

    //NitrogenCalculator nitrogen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Log.d("test6", "HomeActivity calls nitrogen calculator");
//
//        InputStream input_stream_first = getResources().openRawResource(R.raw.nitrogen_first);
//        InputStream input_stream_second = getResources().openRawResource(R.raw.nitrogen_second);
//        InputStream input_stream_third = getResources().openRawResource(R.raw.nitrogen_third);
//
//        nitrogen = new NitrogenCalculator();
//
//        try {
//            nitrogen.readToHashMap("first", input_stream_first);
//            nitrogen.readToHashMap("second", input_stream_second);
//            nitrogen.readToHashMap("third", input_stream_third);
//        } catch (IOException e) {
//            Log.d("test6", "HomeActivity throws exception");
//
//            e.printStackTrace();
//        }
//
//        nitrogen.calculateBottomTime("15:43", "16:30");
//        nitrogen.calculateSurfaceInterval("16:30", "26-01-2017");
//        nitrogen.calculateNitrogen("17", "47");

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
