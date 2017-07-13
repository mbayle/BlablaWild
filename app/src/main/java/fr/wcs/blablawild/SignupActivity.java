package fr.wcs.blablawild;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText editTextPassword;
    private EditText editTextMail;
    private Button buttonCreateAccount;
    private TextView textViewSignIn;
    private ProgressDialog progressDialog;

    private final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        editTextMail = (EditText) findViewById(R.id.editTextMail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonCreateAccount = (Button) findViewById(R.id.buttonCreateAccount);
        textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);
        progressDialog = new ProgressDialog(this);

        buttonCreateAccount.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
    }

    private void registerUser(){
        String email = editTextMail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Veuillez indiquer votre E-Mail", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Veuillez indiquer votre mot de passe", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Enregistrement en cours");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent createSuccess = new Intent(SignupActivity.this, AccountActivity.class);
                            startActivity(createSuccess);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication impossible.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view){
        if (view == buttonCreateAccount){
            registerUser();
        }
        if (view == textViewSignIn){
            Intent signIn = new Intent(SignupActivity.this, SigninActivity.class);
            startActivity(signIn);
        }
    }
}
