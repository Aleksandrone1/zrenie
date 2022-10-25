package com.example.zrenie4;


import static com.example.zrenie4.R.id.root_element;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    EditText email, name, pass, phone;
    Button buttonsignin, buttonregin, vnes;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    RelativeLayout relativeLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        buttonsignin = findViewById(R.id.buttonsignin);
        buttonregin = findViewById(R.id.buttonregin);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
        email = (EditText) findViewById(R.id.email_field);
        pass = (EditText) findViewById(R.id.pass_field);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.numbertelephone);
        relativeLayout = findViewById(root_element);
        progressBar = findViewById(R.id.progress);

        buttonregin.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Registration2.class);
            startActivity(intent);
            finish();
        });
        buttonsignin.setOnClickListener(view -> showSiginWindow());

    }


    private void showSiginWindow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //создание диалога
        builder.setTitle("Войти");
        builder.setMessage("Введите данные для входа");
        LayoutInflater inflater = LayoutInflater.from(this);
        View registor = inflater.inflate(R.layout.sigin_in_window, null);
        builder.setView(registor);
        MaterialEditText email = registor.findViewById(R.id.email_field); //даные с которых будет считывается данные
        MaterialEditText pass = registor.findViewById(R.id.pass_field);
        //создание кнопки отмена с помощью негатива
        builder.setNegativeButton("Отменить", (dialogInterface, i) -> {
            dialogInterface.dismiss(); // данное вспылвающее окно
        });
        builder.setPositiveButton("Войти", (dialogInterface, i) -> {
            if (TextUtils.isEmpty(email.getText().toString())) {
                Snackbar.make(relativeLayout, "Введите вашу почту", Snackbar.LENGTH_LONG).show();// позволяет выводить ошибки make cоздает всплыв окно
                return; //если вызывается ошибка то сразу могли выйти
                //проверяют не пустые ли введенные данные
            }
            // авторизирует пользователя
            //срабатывает при не успешном срабатывании добавлении пользователя
            auth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                    .addOnSuccessListener(authResult -> {
// перекидывает на новую сцену
                        startActivity(new Intent(MainActivity.this, MapActivity.class));
                        finish();
                    }).addOnFailureListener(e -> Snackbar.make(relativeLayout, "Ошибка авторизации." + e.getMessage(), Snackbar.LENGTH_LONG).show());
        }); // которые будут получать все данные
        builder.show();
    }


}