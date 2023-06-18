package com.example.chess;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {


    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";
    TextView textView;
    ProgressBar progressBar;
    int duration = 500;
    int incrementStep = 1;
    long delay = (long) (duration / 100.0);
    Handler handler;
    Runnable runnable;
    GoogleSignInClient mGoogleSignInClient;
    ImageButton signIn;
    private FirebaseAuth mAuth;

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


        // make the bar's color #3EB489
        int color = Color.parseColor("#E06666");
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        progressBar.setProgressTintList(colorStateList);

        // Make the Image Button invisible
        signIn = findViewById(R.id.signIn);
        signIn.setVisibility(ImageButton.INVISIBLE);


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
                    signIn.setVisibility(ImageButton.VISIBLE);
                    int color = Color.parseColor("#00FFFFFF"); // Transparent color
                    ColorStateList colorStateList = ColorStateList.valueOf(color);
                    signIn.setBackgroundTintList(colorStateList);
                    signIn.setElevation(0f);


                    // Increase the size
                    ScaleAnimation enlargeAnimation = new ScaleAnimation(
                            1f, 1.2f, 1f, 1.2f,
                            Animation.RELATIVE_TO_SELF, 0.5f,
                            Animation.RELATIVE_TO_SELF, 0.5f
                    );
                    enlargeAnimation.setRepeatCount(Animation.INFINITE);
                    enlargeAnimation.setRepeatMode(Animation.REVERSE);
                    enlargeAnimation.setDuration(1000);

                    // Apply the animation to the button
                    signIn.startAnimation(enlargeAnimation);
                    signIn.setBackground(null);

                    // Make the activity full screen
                    getWindow().getDecorView().setSystemUiVisibility(5894);
                    signIn.setBackgroundResource(0);

                    // Google Authentication
                    mAuth = FirebaseAuth.getInstance();
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
                    mGoogleSignInClient = GoogleSignIn.getClient(SplashScreenActivity.this, gso);
                    signIn.setOnClickListener(v -> {
                        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                        startActivityForResult(signInIntent, RC_SIGN_IN);
                    });

                    // Change the sign in button to #rgb(49,175,147)
                    signIn.setBackgroundColor(0xFF31AF93);


                    // round the corners of the sign in button
                    signIn.setClipToOutline(true);
                }
            }
        };
        handler.postDelayed(runnable, delay);

// ...


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());

            } catch (Exception e) {
                e.printStackTrace();
                //TODO: CREATE LOGIN FAILED DIALOG
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {

        AuthCredential credential = com.google.firebase.auth.GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "signInWithCredential: success");
                FirebaseUser user = mAuth.getCurrentUser();
                Intent mainActivity = new Intent(SplashScreenActivity.this, ComputerSettingsActivity.class);
                startActivity(mainActivity);
                Log.d(TAG, "onComplete: " + user.getEmail());
            } else {
                Log.d(TAG, "signInWit   hCredential: failure", task.getException());

                //TODO: CREATE LOGIN FAILED DIALOG
            }
        });
    }
}