package fr.wcs.blablawild;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.UploadTask;

import static android.R.attr.data;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth firebaseAuth;
    private StorageReference myStorage;
    private TextView DisplayName;
    private Button buttonLogout;
    private Button buttonMenu;
    private Button buttonModification;
    private ProgressDialog progressDialog;
    private ImageView imageViewProfile;
    private EditText editTextDisplayName;
    private String username;
    private String uid;
    private String uEmail;
    private static int RESULT_LOAD_IMAGE = 1;
    private Uri selectedImage;
    private String picturePath;
    private static final String TAG = "AccountActivity";

    private static final int PICK_PHOTO =256;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        selectedImage=null;

        //On initialise l'authentification et le storage de firebase
        firebaseAuth = FirebaseAuth.getInstance();
        myStorage = FirebaseStorage.getInstance().getReference("avatars");

        //si l'utilisateur n'est pas loggé
        //l'utilisateur en cours sera null
        if (firebaseAuth.getCurrentUser() == null) {
            //ferme cette activity
            finish();
            //renvoie à la login activity
            startActivity(new Intent(this, SigninActivity.class));
        } else {

            //récupère utilisateur en cours
            FirebaseUser user = firebaseAuth.getCurrentUser();

            uid = user.getUid();


            imageViewProfile = (ImageView) findViewById(R.id.imageViewProfile);
            buttonLogout = (Button) findViewById(R.id.buttonDisconnect);
            buttonMenu = (Button) findViewById(R.id.buttonMenu);
            DisplayName = (TextView) findViewById(R.id.textViewDisplayName);

            //On relie le listener au bouton
            buttonLogout.setOnClickListener(this);
            buttonMenu.setOnClickListener(this);
            imageViewProfile.setOnClickListener(this);

            downloadAvatar();

            // le textview récupère le display name

            DisplayName.setText(user.getDisplayName());
        }
    }

    private void openGallery() {
        Intent gallery = new Intent();

        gallery.setType("image/*");

        gallery.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(gallery, getString(R.string.SelectPicture)), PICK_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode,data);
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTO) {
            selectedImage = data.getData();

            uploadAvatar(selectedImage);
            downloadAvatar();

        }
    }

    private void uploadAvatar(Uri uri){

        StorageReference usersPic = myStorage.child(firebaseAuth.getCurrentUser().getDisplayName()+"_avatar.jpg");
        usersPic.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // URL pour upluoder la photo
                        @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(downloadUrl)
                                .build();
                        firebaseAuth.getCurrentUser().updateProfile(profileUpdates);
                        downloadAvatar();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AccountActivity.this,R.string.imageError,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void downloadAvatar(){
        StorageReference usersPic = myStorage.child(firebaseAuth.getCurrentUser().getDisplayName()+"_avatar.jpg");
        usersPic.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(AccountActivity.this)
                        .load(uri)
                        .into(imageViewProfile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(AccountActivity.this,R.string.imageError,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {

        if(view == buttonLogout){

            firebaseAuth.signOut();
            //l'activité est fermée
            finish();
            //On est renvoyé à login activity
            startActivity(new Intent(this, SigninActivity.class));
        }

        if(view == buttonMenu){
            startActivity(new Intent(this, MainActivity.class));
        }

        if(view == imageViewProfile){
            openGallery();
        }
    }
}