package fr.wcs.blablawild;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.google.firebase.auth.UserProfileChangeRequest;

import static fr.wcs.blablawild.R.id.textViewSignin;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText inputEmail;
    private EditText inputPassword;
    private Button btnSignUp;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private TextView textViewSignIn;
    private EditText userName;
    static String displayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //On instancie ici l'authentification avec FB
        auth = FirebaseAuth.getInstance();


        btnSignUp = (Button) findViewById(R.id.buttonRegister);
        inputEmail = (EditText) findViewById(R.id.editTextEmailUser);
        inputPassword = (EditText) findViewById(R.id.editTextPasswordUser);
        textViewSignIn = (TextView) findViewById(textViewSignin);
        userName=(EditText) findViewById(R.id.editTextDisplayName);

        //progress dialog

        progressDialog = new ProgressDialog(this);

        // On rattache le listener au boutton
        btnSignUp.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);

    }


    //On récupère les mots de passes etemails des edittexts
    private void registerUser() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        //emails et mots de passes sont renseignes
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Renseigne ton email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Renseigne ton mot de passe", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Inscription en cours...");
        progressDialog.show();


        //Creation d'un user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = auth.getCurrentUser();
                        displayName = userName.getText().toString().trim();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(displayName)
                                .build();
                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(SignupActivity.this,displayName,Toast.LENGTH_SHORT);
                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                finish();
                                startActivity(intent);
                            }
                        });
                    }
                });


    }


    public void onClick(View view) {

        if (view == btnSignUp) {
            registerUser();
        }

        if (view == textViewSignIn) {
            //On ira à Login Activity si l'utilisateur est déjà enregistré
            startActivity(new Intent(SignupActivity.this, SigninActivity.class));
        }
    }
}