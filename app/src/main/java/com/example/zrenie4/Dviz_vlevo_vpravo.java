package com.example.zrenie4;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class Dviz_vlevo_vpravo extends AppCompatActivity {
    ImageView imageView,i1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dviz_vlevo_vpravo);
        i1 = findViewById(R.id.draw);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Drawable drawable1 = i1.getDrawable();
        if (drawable1 instanceof Animatable) {
            ((Animatable) drawable1).start();
        }
    }
}