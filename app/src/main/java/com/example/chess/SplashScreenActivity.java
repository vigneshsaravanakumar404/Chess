package com.example.chess;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {


    TextView textView;
    ProgressBar progressBar;
    int duration = 500;
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


        // Make the activity full screen
        getWindow().getDecorView().setSystemUiVisibility(5894);
        handler.postDelayed(runnable, delay);
        textView.setText("Chess\nBy: Vignesh");
        textView.setGravity(1);
        getWindow().getDecorView().setBackgroundColor(0xFF00FF00);


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
                } else {
                    // Progress bar has finished, start the new activity
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }
        };
        handler.postDelayed(runnable, delay);

// ...


    }
}