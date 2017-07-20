package fr.wcs.blablawild;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private StorageReference Photoref;
    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseAuth.getInstance();
        }
    };
    private final String TAG = "ACCOUNT ACTIVITY";
    private TextView textViewDisplayName;
    private EditText editTextPseudo;
    private ImageView imageViewProfile;
    private TextView textViewEmail;
    private Button buttonDisconnect, refreshButton, uploadPhoto;
    static String displayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        StorageReference uselessref = FirebaseStorage.getInstance().getReference();
        Photoref = FirebaseStorage.getInstance().getReference("usersPics");
        refreshButton = (Button) findViewById(R.id.buttonrefresh);
        uploadPhoto = (Button) findViewById(R.id.buttonUpload);
        uploadPhoto.setOnClickListener(this);
        refreshButton.setOnClickListener(this);
        editTextPseudo = (EditText) findViewById(R.id.editTextpseudo);
        textViewDisplayName = (TextView) findViewById(R.id.textViewDisplayName);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        buttonDisconnect = (Button) findViewById(R.id.buttonDisconnect);
        imageViewProfile = (ImageView) findViewById(R.id.imageViewProfile);

        buttonDisconnect.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, SignupActivity.class));
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // Name, email address, and profile photo Url
                if (user.getDisplayName() != null) {
                    textViewDisplayName.setText(user.getDisplayName());
                } else {
                    Toast.makeText(this, "Set your pseudo", Toast.LENGTH_SHORT).show();
                }

                // The user's ID, unique to the Firebase project. Do NOT use this value to
                // authenticate with your backend server, if you have one. Use
                // FirebaseUser.getToken() instead.
                String uid = user.getUid();
            }
        }
    }


    private void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);//
    }

    private void uploadPhoto(Uri imageUri) {


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();


        Photoref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful uploads on complete
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();


                StorageReference photoRef = Photoref.child("userPics/" + userId);


                imageViewProfile.setImageDrawable(null);

                Glide.with(AccountActivity.this).using(new FirebaseImageLoader()).load(photoRef).asBitmap().centerCrop()
                        .into(new BitmapImageViewTarget(imageViewProfile) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(AccountActivity.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                imageViewProfile.setImageDrawable(circularBitmapDrawable);

                            }
                        });
            }
        });

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

    private void signOut() {
        mAuth.signOut();
    }


    @Override
    public void onClick(View view) {

        if (view == refreshButton) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            displayName = editTextPseudo.getText().toString().trim();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build();

            textViewDisplayName.setText(user.getDisplayName());
            textViewEmail.setText(user.getEmail());

        }


        if (view == buttonDisconnect) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(this, SigninActivity.class));
        }

        if (view == uploadPhoto) {


            AlertDialog.Builder a1 = new AlertDialog.Builder(AccountActivity.this);

            a1.setTitle("Choose an option");
            // Setting Dialog Message
            a1.setMessage("Choose whether your prefer upload a photo by taking a picture with the camera , or uploading an existing from the gallery");

            a1.setPositiveButton("Take a new one",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int button1) {
                            // if this button is clicked, close
                            // current activity
                            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, 0);//zero can be replaced with any action code

                            dialog.cancel();
                        }


                    });
            a1.setNeutralButton("From Gallery",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int button2) {
                            openGallery();
                            dialog.cancel();

                        }
                    });

            // Showing Alert Message
            AlertDialog alertDialog = a1.create();
            a1.show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageViewProfile.setImageURI(selectedImage);
                    if (selectedImage != null) {
                        Picasso.with(getApplicationContext())
                                .load(selectedImage)
                                .resize(400, 400)
                                .centerCrop()
                                .into(imageViewProfile);


                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(selectedImage)
                                .build();


                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "User profile updated.");
                                        }
                                    }
                                });


                        uploadPhoto(selectedImage);
                    }
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageViewProfile.setImageURI(selectedImage);

                    if (selectedImage != null) {
                        Picasso.with(getApplicationContext())
                                .load(selectedImage)
                                .resize(400, 400)
                                .centerCrop()
                                .into(imageViewProfile);


                        uploadPhoto(selectedImage);
                    }
                    // Get and resize profile image
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                }
        }
    }
}