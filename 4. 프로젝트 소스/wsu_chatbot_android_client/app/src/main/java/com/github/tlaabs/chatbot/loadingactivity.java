package com.github.tlaabs.chatbot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;


public class loadingactivity extends AppCompatActivity {
    int SPLASH_TIME_OUT = 5000;
    ProgressBar progressBar;

    int[] images = new int[] {R.drawable.wsu, R.drawable.wsu6, R.drawable.wsu5, R.drawable.wsu4, R.drawable.wsu7,R.drawable.wsu8};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading); //들어갈대 로딩 화면

        ImageView mImageView = (ImageView)findViewById(R.id.SYSDOCU);
        int imageId = (int)(Math.random() * images.length);
        mImageView.setBackgroundResource(images[imageId]);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home = new Intent(getApplicationContext(), ChattingActivity.class);
                startActivity(home);
                finish();
            }
        },SPLASH_TIME_OUT);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = findViewById(R.id.progressBar);

        progressAnimation();
    }

    public void progressAnimation(){
        loading anim = new loading(this, progressBar, 0f, 100f);
        anim.setDuration(3000);
        progressBar.setAnimation(anim);
    }
}

