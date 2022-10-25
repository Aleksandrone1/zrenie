package com.example.zrenie4;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zrenie4.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration2 extends AppCompatActivity {
    ProgressBar progressBar;
    Button vnes;
    EditText email, name, pass, phone;
    String email1, name1, pass1, phone1;
    TextView verification, vozvrat;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    RelativeLayout relativeLayout;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg);
        vnes = findViewById(R.id.button8);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
        email = findViewById(R.id.email_field);
        pass = findViewById(R.id.pass_field);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.numbertelephone);
        vozvrat = (TextView) findViewById(R.id.vozvrat1);
        verification = (TextView) findViewById(R.id.textView6);
        final FirebaseUser user1 = auth.getCurrentUser();
        user = new User();
        FirebaseUser user3 = FirebaseAuth.getInstance().getCurrentUser();
        vnes.setOnClickListener(view -> {
            Toast.makeText(Registration2.this, "Перед созданием учетной записи необходимо пройти подтвердить свою почту", Toast.LENGTH_LONG).show();
            FirebaseUser user9 = auth.getCurrentUser();
            if (user9 != null) {
                if (user9.isEmailVerified()) {
                    verification.setText("Пользователь прошел верификацию");
                    showRegisterWindow();
                }
                verification.setText("Для потверждения почты (необходимо нажать на эту ссылку и проверить почту)");

                verification.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        user9.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(Registration2.this, "Пользователь прошел верификацию", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }

        });
        vozvrat.setOnClickListener(view ->
                startActivity(new Intent(Registration2.this, MainActivity.class)));
    }

    private void showRegisterWindow() {
        email1 = email.getText().toString().trim(); //даные с которых будет считывается данные
        pass1 = pass.getText().toString().trim();
        name1 = name.getText().toString().trim();
        phone1 = phone.getText().toString().trim();

        if (TextUtils.isEmpty(email1)) {
            Toast.makeText(Registration2.this, "Введите почту", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(pass1)) {
            Toast.makeText(Registration2.this, "Введите пароль", Toast.LENGTH_LONG).show();
            email.requestFocus();
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
            email.setError("Введите действительную почту");
        }
        if (TextUtils.isEmpty(name1)) {
            Toast.makeText(Registration2.this, "Введите имя", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(phone1)) {
            Toast.makeText(Registration2.this, "Введите телефон", Toast.LENGTH_LONG).show();
        }
        try {
            User user = new User(email1, pass1, name1, phone1);
            auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnSuccessListener(authResult -> {
                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnSuccessListener(unused -> Toast.makeText(Registration2.this, "Пользователь добавлен", Toast.LENGTH_LONG).show());
            });


        } catch (Exception e) {
            Toast.makeText(Registration2.this, (CharSequence) e, Toast.LENGTH_LONG).show();
        }
    }

    private void loadUserInform() {
        FirebaseUser user9 = auth.getCurrentUser();

        if (user9 != null) {
            if (user9.isEmailVerified()) {
                verification.setText("Пользователь прошел верификацию");
            }
            verification.setText("Пользователь не прошел верификацию (необходимо нажать на это сообщение");

            verification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user9.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Registration2.this, "Пользователь прошел верификацию", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    }
}