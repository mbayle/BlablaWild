package fr.wcs.blablawild;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;
    private final String TAG = "ACCOUNT ACTIVITY";
    private TextView textViewDisplayName;
    private TextView textViewEmail;
    private Button buttonDisconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        textViewDisplayName = (TextView) findViewById(R.id.textViewDisplayName);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        buttonDisconnect = (Button) findViewById(R.id.buttonDisconnect);

        buttonDisconnect.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, SignupActivity.class));
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // Name, email address, and profile photo Url
                textViewDisplayName.setText(user.getDisplayName());
                textViewEmail.setText(user.getEmail());

                // The user's ID, unique to the Firebase project. Do NOT use this value to
                // authenticate with your backend server, if you have one. Use
                // FirebaseUser.getToken() instead.
                String uid = user.getUid();
            }
        }
    }

    @Override
    public void onClick(View view) {

        if(view == buttonDisconnect){

            mAuth.signOut();
            finish();
            startActivity(new Intent(this, SigninActivity.class));
        }
    }
}