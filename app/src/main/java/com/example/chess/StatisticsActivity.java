package com.example.chess;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    TextView textView2;
    int loss, win, draw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        getWindow().getDecorView().setSystemUiVisibility(5894);

        linearLayout = findViewById(R.id.linearLayout);
        textView2 = findViewById(R.id.textView2);

        win = 0;
        loss = 0;
        draw = 0;

        // get data from firebase storage and display data
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        String userId = auth.getCurrentUser().getUid();
        String filename = userId + "_data.txt"; // Using the user's UID as the filename

        // Get a reference to the file in Firebase Storage
        StorageReference fileRef = storageRef.child(filename);

        fileRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
            // Convert the data bytes to a string
            String dataString = new String(bytes);

            // Log the retrieved data
            Log.d("TAG", "Retrieved data: " + dataString);

            // Process the data and split it into lines
            List<String> games = new ArrayList<>();
            for (String line : dataString.split("\n")) {
                if (line.startsWith("0") || line.startsWith("1") || line.startsWith("0.5")) {
                    games.add(line);


                    String[] gameInfo = line.split(" \\+ ");
                    if (gameInfo.length >= 2) {
                        String result = gameInfo[0];
                        String outcome = gameInfo[1];
                        String level = gameInfo[2];

                        // Create a TextView to display the game information
                        TextView textView = new TextView(this);
                        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                        // Set the text color based on the game result
                        if (result.equals("0")) {
                            textView.setTextColor(Color.RED); // Loss
                            loss++;
                        } else if (result.equals("1")) {
                            textView.setTextColor(Color.GREEN); // Win
                            win++;
                        } else {
                            textView.setTextColor(Color.GRAY); // Draw
                            draw++;
                        }

                        textView.setText(outcome + " with Stockfish Level: " + level);
                        textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                        textView.setTextSize(15);


                        // Add the TextView to the LinearLayout
                        linearLayout.addView(textView);
                    }
                }

            }
            textView2.setText("\n\nOverall Statistics: \n" +
                    "Wins: " + win + "\n" +
                    "Losses: " + loss + "\n" +
                    "Draws: " + draw);

            textView2.setGravity(Gravity.CENTER);
            textView2.setTextSize(20);
            textView2.setBackgroundColor(Color.GRAY);
            textView2.setTextColor(Color.WHITE);


            // Log the processed games
            Log.d("TAG", "Games: " + games);
        }).addOnFailureListener(e -> {
            // Handle any errors retrieving the data
            Log.e("TAG", "Error retrieving data: " + e.getMessage(), e);
        });
    }
}
