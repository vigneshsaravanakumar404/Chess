package com.example.chess;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // Variables
    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";
    GoogleSignInClient mGoogleSignInClient;
    ImageButton signIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        signIn = findViewById(R.id.signIn);

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
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signIn.setOnClickListener(v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });

        // Change the sign in button to #rgb(49,175,147)
        signIn.setBackgroundColor(0xFF31AF93);


        // round the corners of the sign in button
        signIn.setClipToOutline(true);


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
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithCredential: success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent mainActivity = new Intent(LoginActivity.this, ComputerSettingsActivity.class);
                    startActivity(mainActivity);
                    Log.d(TAG, "onComplete: " + user.getEmail());
                } else {
                    Log.d(TAG, "signInWit   hCredential: failure", task.getException());

                    //TODO: CREATE LOGIN FAILED DIALOG
                }
            }
        });
    }
}

// Log out code: C:\Users\Vigne\OneDrive\Pictures\Screenshots\Screenshot 2023-06-10 223027.png