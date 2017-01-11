package jstam.programmeerproject_scubascan;

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

public class LoginActivity extends HomeActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";

    String title;
    String instr;
    String confirm_pass;

    private TextView title_text;
    private TextView instr_text;
    private TextView mStatusTextView;
    private TextView mDetailTextView;

    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mPasswordConfirmField;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Views
        mStatusTextView = (TextView) findViewById(R.id.status);
        mDetailTextView = (TextView) findViewById(R.id.detail);

        mEmailField = (EditText) findViewById(R.id.email_input);
        mPasswordField = (EditText) findViewById(R.id.password_input);
        mPasswordConfirmField = (EditText) findViewById(R.id.password_confirm_input);

        // get extras from MainActivity
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            title = extras.getString("log_sign");
            instr = extras.getString("instr");
            confirm_pass = extras.getString("pass_confirm");
        }

        Button signup_button = (Button) findViewById(R.id.sign_up_button);

        // Buttons
        findViewById(R.id.sign_up_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);

        mEmailField = (EditText) findViewById(R.id.email_input);
        mPasswordField = (EditText) findViewById(R.id.password_input);
        mPasswordConfirmField = (EditText) findViewById(R.id.password_confirm_input);

        // display edittext for password confirmation in case of signing up
        if (confirm_pass != null) {
            if (confirm_pass.equals("visible")) {
                mPasswordConfirmField.setVisibility(View.VISIBLE);
                signup_button.setText("Sign me up!");
            }
            else {
                signup_button.setText("Log me in!");
            }
        }

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                updateUI(user);
                // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]
    }

    // [START on_start_add_listener]
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    // [END on_stop_remove_listener]

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        // showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            mStatusTextView.setText(R.string.auth_failed);
                        }
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        //hideProgressDialog();
        if (user != null) {
            Button singup_login = (Button) findViewById(R.id.sign_up_button);

            if (singup_login != null) {
                findViewById(R.id.sign_up_button).setVisibility(View.GONE);
                findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
                mEmailField.setVisibility(View.GONE);
                mPasswordField.setVisibility(View.GONE);

                if (mPasswordConfirmField.getVisibility() != View.INVISIBLE) {
                    mPasswordConfirmField.setVisibility(View.GONE);
                }
            } else {
                mStatusTextView.setText(R.string.signed_out);
                mDetailTextView.setText(null);

                findViewById(R.id.sign_up_button).setVisibility(View.VISIBLE);
                findViewById(R.id.sign_out_button).setVisibility(View.GONE);

                mEmailField.setVisibility(View.VISIBLE);
                mPasswordField.setVisibility(View.VISIBLE);

                if (confirm_pass != null) {
                    if (confirm_pass.equals("invisible")) {
                        mPasswordConfirmField.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
    }

    @Override
    public void onClick (View v){
        int i = v.getId();
        String new_instr = "Logged in succesfully! Click the search button to look for " +
                "books. Click the list button to access your lists.";

        if (i == R.id.sign_up_button) {

            String email = mEmailField.getText().toString();
            String password = mPasswordField.getText().toString();
            String password_confirm = mPasswordConfirmField.getText().toString();

            if (title.equals("Signing up")) {

                if (!password.equals(password_confirm)) {
                    Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
                } else if (email.equals("")) {
                    Toast.makeText(this, "Fill in your e-mail!", Toast.LENGTH_SHORT).show();
                } else {
                    createAccount(email, password);
                    instr_text.setText(new_instr);
                    //manager.create_user(email);
                }

            } else if (title.equals("Logging in")) {
                //manager.create_user(email);
                signIn(email, password);
                instr_text.setText(new_instr);
            }

        } else if (i == R.id.sign_out_button) {
            //manager.logout_user();
            signOut();
            instr_text.setText(instr);
        }
    }
}