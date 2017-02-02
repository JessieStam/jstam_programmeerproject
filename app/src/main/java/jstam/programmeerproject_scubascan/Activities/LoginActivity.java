package jstam.programmeerproject_scubascan.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import jstam.programmeerproject_scubascan.Items.UserItem;
import jstam.programmeerproject_scubascan.R;

/**
 * Scuba Scan - LoginActivity
 *
 * Jessie Stam
 * 10560599
 *
 * The second activity the user sees. This activity uses Google Firebase to let users create
 * accounts and log in on those accounts. Parameters from HomeActiviy, depending on logging in or
 * signing up, determine the user interface. Logging in is required for moving on to the next
 * activity.
 *
 * This code partially originates from Firebase and is added by me to fit the specific requirements.
 */
public class LoginActivity extends HomeActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    String title, instr, confirm_pass;
    TextView title_text, instr_text;
    EditText username_field, email_field, password_field, pass_confirm_field;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference my_database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize views
        username_field = (EditText) findViewById(R.id.username_input);
        email_field = (EditText) findViewById(R.id.email_input);
        password_field = (EditText) findViewById(R.id.password_input);
        pass_confirm_field = (EditText) findViewById(R.id.password_confirm_input);

        title_text = (TextView) findViewById(R.id.signup_title);
        instr_text = (TextView) findViewById(R.id.signup_instr);

        Button signup_button = (Button) findViewById(R.id.sign_up_button);

        // set listeners for buttons
        findViewById(R.id.sign_up_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.go_menu_button).setOnClickListener(this);

        // get extras from HomeActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("log_sign");
            instr = extras.getString("instr");
            confirm_pass = extras.getString("pass_confirm");
        }

        // edit initial UI according to logging in or signing up
        title_text.setText(title);
        instr_text.setText(instr);

        // display EditText for password confirmation in case of signing up
        if (confirm_pass != null) {
            if (confirm_pass.equals(getString(R.string.home_passconfirm_visible))) {
                username_field.setVisibility(View.VISIBLE);
                pass_confirm_field.setVisibility(View.VISIBLE);
                signup_button.setText(R.string.login_signupbutton_text);
            }
            else {
                signup_button.setText(R.string.login_loginbutton_text);
            }
        }

        // set Firebase listener for auth state
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    instr_text.setText(R.string.login_stilllogedin_instr);

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                updateUI(user);
            }
        };

        // initialize Firebase reference
        my_database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /* Create new user account. */
    private void createAccount(String email, String password) {

        final String new_instr = getString(R.string.login_loggedin_newinstr);

        // validate user input and create user with e-mail and password
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // if sign in fails, display a message to the user
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                        // if sign in succeeds, auth state listener will be notified
                        else {
                            instr_text.setText(new_instr);
                            saveUserInformation();
                        }
                    }
                });
    }

    /* Log in user with existing account. */
    private void signIn(String email, String password) {

        final String new_instr = getString(R.string.login_signedup_newinstr);

        // validate user input and sign in user with e-mail and password
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // if sign in fails, display a message to the user.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                        // if sign in succeeds, auth state listener will be notified
                        else {
                            instr_text.setText(new_instr);
                        }

                    }
                });
    }

    /* Sign user out. */
    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    /* Validate user's data. */
    private boolean validateForm() {

        boolean valid = true;

        // validate e-mail
        String email = email_field.getText().toString();
        if (TextUtils.isEmpty(email)) {
            email_field.setError(getString(R.string.required));
            valid = false;
        } else {
            email_field.setError(null);

            if (!email.contains("@") || !email.contains(".")) {
                Toast.makeText(this, R.string.login_email_requirement, Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        // validate password
        int min_char = 6;
        String password = password_field.getText().toString();
        if (TextUtils.isEmpty(password)) {
            password_field.setError(getString(R.string.required));
            valid = false;
        } else {
            password_field.setError(null);

            if (password.length() < min_char) {
                Toast.makeText(this, R.string.login_passrequirement, Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }

        // if user is signing up, validate username and password confirmation
        if (pass_confirm_field.getVisibility() == View.VISIBLE ) {

            // validate username
            int max_char = 15;
            String username = username_field.getText().toString();
            if (TextUtils.isEmpty(username)) {
                username_field.setError(getString(R.string.required));
                valid = false;
            } else {
                username_field.setError(null);

                if (username.length() > max_char) {
                    Toast.makeText(this, R.string.login_maxchar_requirement,
                            Toast.LENGTH_SHORT).show();
                    valid = false;
                }
            }

            // validate password confirmation
            String password_confirm = pass_confirm_field.getText().toString();
            if (TextUtils.isEmpty(password_confirm)) {
                pass_confirm_field.setError(getString(R.string.required));
                valid = false;
            } else if (!password.equals(password_confirm)) {
                Toast.makeText(this, R.string.login_no_pass_match, Toast.LENGTH_SHORT).show();
                valid = false;
            } else {
                password_field.setError(null);
            }
        }

        return valid;
    }

    /* Update interface after logging in or signing up. */
    private void updateUI(FirebaseUser user) {

        if (user != null) {
            Button singup_login = (Button) findViewById(R.id.sign_up_button);

            if (singup_login != null) {
                findViewById(R.id.sign_up_button).setVisibility(View.GONE);
                findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
                findViewById(R.id.go_menu_button).setVisibility(View.VISIBLE);
                username_field.setVisibility(View.GONE);
                email_field.setVisibility(View.GONE);
                password_field.setVisibility(View.GONE);

                if (pass_confirm_field.getVisibility() != View.INVISIBLE) {
                    pass_confirm_field.setVisibility(View.GONE);
                }
            } else {

                username_field.setVisibility(View.VISIBLE);
                email_field.setVisibility(View.VISIBLE);
                password_field.setVisibility(View.VISIBLE);

                if (confirm_pass != null) {
                    if (confirm_pass.equals(getString(R.string.home_passconfirm_invisible))) {
                        pass_confirm_field.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
    }

    /* Save user information to Firebase upon signing up. */
    private void saveUserInformation() {

        String username = username_field.getText().toString().trim();
        String email = email_field.getText().toString().trim();

        FirebaseUser firebase_user = mAuth.getCurrentUser();
        String user_id = firebase_user.getUid();

        UserItem user = new UserItem(username, email);

        my_database.child("users").child(user_id).child("user_info").setValue(user);

    }

    /* Listen for button click to determine what to do. */
    @Override
    public void onClick (View v){

        int i = v.getId();

        // upon pressing sign up button, create new account or log in
        if (i == R.id.sign_up_button) {

            String email = email_field.getText().toString();
            String password = password_field.getText().toString();

            if (title.equals(getString(R.string.home_signup_title))) {

                if (validateForm()) {
                    createAccount(email, password);
                    signIn(email, password);
                }

            } else if (title.equals(getString(R.string.home_login_title))) {

                if (validateForm()) {
                    signIn(email, password);
                }
            }

        }
        // upon pressing sign out button, sign user out and go back to HomeActivity
        else if (i == R.id.sign_out_button) {
            signOut();
            finish();
        }
        // upon pressing continue button, move on to MenuActivity
        else if (i == R.id.go_menu_button) {

            Intent goToMenu = new Intent(this, MenuActivity.class);
            startActivity(goToMenu);

            finish();
        }
    }
}