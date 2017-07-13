package fr.wcs.blablawild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static fr.wcs.blablawild.R.id.buttonCreateAccount;
import static fr.wcs.blablawild.R.id.textViewSignIn;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView textViewDisplayName;
    private TextView textViewEmail;
    private Button buttonDisconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mAuth = FirebaseAuth.getInstance();
        textViewDisplayName = (TextView) findViewById(R.id.textViewDisplayName);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        buttonDisconnect = (Button) findViewById(R.id.buttonDisconnect);

        buttonDisconnect.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        textViewEmail.setText(currentUser.getEmail());
    }

    @Override
    public void onClick(View view){
        if (view == buttonDisconnect){
        }
    }
}
