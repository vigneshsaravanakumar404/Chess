package com.example.chess;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    // Variables
    GridLayout chessboard;
    ConstraintLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Variables
        chessboard = findViewById(R.id.chessboard);
        mainLayout = findViewById(R.id.mainLayout);

        // Initialize
        AnimationDrawable animationDrawable = (AnimationDrawable) mainLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        //animationDrawable.start();
        setChessboardDimensions();
        createSquares();

    }

    // Methods
    private void setChessboardDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        ViewGroup.LayoutParams layoutParams = chessboard.getLayoutParams();
        layoutParams.width = screenWidth;
        layoutParams.height = screenWidth;
        chessboard.setLayoutParams(layoutParams);
    }

    private void createSquares() {
        boolean isWhite = true;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ImageView square = new ImageView(this);

                if (isWhite) {
                    square.setBackgroundColor(Color.parseColor("#6ac3bd"));
                } else {
                    square.setBackgroundColor(Color.TRANSPARENT);
                }

                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                layoutParams.rowSpec = GridLayout.spec(row, 1f);
                layoutParams.columnSpec = GridLayout.spec(col, 1f);
                square.setLayoutParams(layoutParams);

                chessboard.addView(square);

                isWhite = !isWhite;
            }
            isWhite = !isWhite;
        }
    }
}
