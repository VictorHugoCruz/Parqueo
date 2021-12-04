package com.victorcruz.parking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class PantallaBienvenida extends AppCompatActivity {

    private static int PANTALLA=5000;

    Animation topAnim, bottomAnim;

    ImageView imagen, logoapp;
    TextView logo, tecno, carrera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_bienvenida);

        //animaciones
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animcacion);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animacio);

        logoapp = findViewById(R.id.logoapp);
        imagen = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        tecno = findViewById(R.id.textView2);
        carrera = findViewById(R.id.textView3);

        logoapp.setAnimation(topAnim);
        imagen.setAnimation(bottomAnim);
        logo.setAnimation(topAnim);
        tecno.setAnimation(bottomAnim);
        carrera.setAnimation(bottomAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PantallaBienvenida.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, PANTALLA);


    }
}