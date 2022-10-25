package com.example.zrenie4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;
public class Profileactivity extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 101;
    Button button1;
    ImageView imageView;
    EditText editText;
    Uri uri;
    ProgressBar progressBar;
    String profileImageUrl;
    FirebaseAuth firebaseAuth;
    TextView v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileactivity);
        firebaseAuth = FirebaseAuth.getInstance();
        editText = (EditText) findViewById(R.id.textView5);
        imageView = findViewById(R.id.imageView);
        button1 = findViewById(R.id.ter);
        progressBar = findViewById(R.id.progress);
        v = findViewById(R.id.textView7);
        loadUserInform();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });
        //получаем информацию о текущем пользователе
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserinform();
            }
        });
    }

    private void loadUserInform() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            if (user.getPhotoUrl() != null) {
                Glide.with(this).load(user.getPhotoUrl().toString()).into(imageView);
            }
            if (user.getDisplayName() != null) {
                editText.setText(user.getDisplayName());
            }

            if (user.isEmailVerified()) {
                v.setText("Пользователь прошел верификацию");
            } else {
                v.setText("Пользователь не прошел верификацию (необходимо нажать на это сообщение)");

            }
        }
    }

    private void saveUserinform() {
        String displayname = editText.getText().toString();
        if (displayname.isEmpty()) {
            editText.setError("Имя не получено");
            editText.requestFocus();
        }
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null && uri != null) {
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayname).setPhotoUri(Uri.parse(String.valueOf(uri))).build();
            firebaseUser.updateProfile(profileChangeRequest)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Profileactivity.this, "Имя и длинна обновлена", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
                uploadImageToFirebase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebase() {
        StorageReference profileimage = FirebaseStorage.getInstance().getReference("profileimage/" + System.currentTimeMillis() + " .jpg");
        if (uri != null) {
            progressBar.setVisibility(View.VISIBLE);
            profileimage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    profileImageUrl = Objects.requireNonNull(taskSnapshot.getMetadata()).toString();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Profileactivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Выберите необходимое фото"), CHOOSE_IMAGE);
    }
}