package jstam.programmeerproject_scubascan.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import jstam.programmeerproject_scubascan.R;

/**
 * Scuba Scan - HomeActivity
 *
 * Jessie Stam
 * 10560599
 *
 * The first screen that is shown to the user when the application is started. It simply displays
 * the name of the application and gives the user the option to log in or sign up. Upon pressing
 * the back navigation, the activity will tell the user that by pressing back again the app will be
 * closed.
 */
public class HomeActivity extends AppCompatActivity {

    private boolean exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        exit = false;
    }


    /* When sign up button is clicked, move to LoginActivity with the corresponding params. */
    public void signUpUser(View view) {

        String title = getString(R.string.home_signup_title);
        String instr = getString(R.string.home_signup_instr);
        String pass_confirm = getString(R.string.home_passconfirm_visible);

        Intent signUpUser = new Intent(this, LoginActivity.class);

        signUpUser.putExtra("log_sign", title);
        signUpUser.putExtra("instr", instr);
        signUpUser.putExtra("pass_confirm", pass_confirm);

        startActivity(signUpUser);
    }

    /* When log in button is clicked, move to LoginActivity with the corresponding params. */
    public void logInUser(View view) {

        String title = getString(R.string.home_login_title);
        String instr = getString(R.string.home_login_instr);
        String pass_confirm = getString(R.string.home_passconfirm_invisible);

        Intent logInUser = new Intent(this, LoginActivity.class);

        logInUser.putExtra("log_sign", title);
        logInUser.putExtra("instr", instr);
        logInUser.putExtra("pass_confirm", pass_confirm);

        startActivity(logInUser);
    }

    /* When back navigation is pressed twice, close the app. */
    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
        } else {
            Toast.makeText(this, R.string.backnav_exit_warning,
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
