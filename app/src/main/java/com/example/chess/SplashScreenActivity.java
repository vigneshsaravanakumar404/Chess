package com.example.chess;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {


    TextView textView;
    ProgressBar progressBar;
    int duration = 2000;
    int incrementStep = 1;
    long delay = (long) (duration / 100.0);
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Variables
        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);
        handler = new Handler();

        progressBar.setProgress(0);
        progressBar.setVisibility(ProgressBar.VISIBLE);


        runnable = new Runnable() {
            @Override
            public void run() {
                // Get the current progress
                int progress = progressBar.getProgress();

                // Increment the progress by the step value
                progress += incrementStep;

                // Update the progress bar
                progressBar.setProgress(progress);

                // Check if the progress reached 100%
                if (progress < 100) {
                    // Post the next update with the calculated delay
                    handler.postDelayed(this, delay);
                }
            }
        };
        handler.postDelayed(runnable, delay);
        textView.setText("Chess\nBy: Vignesh");
        textView.setGravity(1);
        getWindow().getDecorView().setBackgroundColor(0xFF00FF00);
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

    }
}