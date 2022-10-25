package com.example.zrenie4;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class BlinkActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blink);
        imageView = findViewById(R.id.dra);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createOneShotVibrationUsingVibrationEffect() {
        // 1000 : Vibrate for 1 sec
        // VibrationEffect.DEFAULT_AMPLITUDE - would perform vibration at full strength
        VibrationEffect effect = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);
        Vibrator vibrator = null;
        vibrator.vibrate(effect);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Drawable drawable = imageView.getDrawable();

        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }

    }

}