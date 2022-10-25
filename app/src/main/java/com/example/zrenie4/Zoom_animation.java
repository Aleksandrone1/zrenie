package com.example.zrenie4;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class Zoom_animation extends AppCompatActivity {
Button button;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_animation);
        imageView = findViewById(R.id.imageview);
        button=findViewById(R.id.zoomOutButton);
        button.setOnClickListener() {

            // loading the animation of
            // zoom_in.xml file into a variable
            val animZoomIn = AnimationUtils.loadAnimation(this,
                    R.anim.zoom_in)
            // assigning that animation to
            // the image and start animation
            image.startAnimation(animZoomIn)
    }

}