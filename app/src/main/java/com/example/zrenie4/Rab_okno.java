package com.example.zrenie4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Rab_okno extends AppCompatActivity {
ImageButton imageButton;
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rab_okno);
        imageButton=findViewById(R.id.imageButton2);
        textView=findViewById(R.id.textView3);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Rab_okno.this, Instruction.class));
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Rab_okno.this, Inst_vlevo_vpravo.class));
            }
        });

    }
    }
