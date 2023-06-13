package com.example.chess;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

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

        // background
        AnimationDrawable animationDrawable = (AnimationDrawable) mainLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        //animationDrawable.start();

        // Initialize
        setChessboardDimensions();
        createSquares();

        // Place pieces on the board
        ImageView blackRook = createChessPiece(R.drawable.br, 0, 0);
        ImageView blackRook2 = createChessPiece(R.drawable.br, 0, 7);
        ImageView blackKnight = createChessPiece(R.drawable.bn, 0, 1);
        ImageView blackKnight2 = createChessPiece(R.drawable.bn, 0, 6);
        ImageView blackBishop = createChessPiece(R.drawable.bb, 0, 2);
        ImageView blackBishop2 = createChessPiece(R.drawable.bb, 0, 5);
        ImageView blackQueen = createChessPiece(R.drawable.bq, 0, 3);
        ImageView blackKing = createChessPiece(R.drawable.bk, 0, 4);
        ImageView blackPawn1 = createChessPiece(R.drawable.bp, 1, 0);
        ImageView blackPawn2 = createChessPiece(R.drawable.bp, 1, 1);
        ImageView blackPawn3 = createChessPiece(R.drawable.bp, 1, 2);
        ImageView blackPawn4 = createChessPiece(R.drawable.bp, 1, 3);
        ImageView blackPawn5 = createChessPiece(R.drawable.bp, 1, 4);
        ImageView blackPawn6 = createChessPiece(R.drawable.bp, 1, 5);
        ImageView blackPawn7 = createChessPiece(R.drawable.bp, 1, 6);
        ImageView blackPawn8 = createChessPiece(R.drawable.bp, 1, 7);

        ImageView whiteRook = createChessPiece(R.drawable.wr, 7, 0);
        ImageView whiteRook2 = createChessPiece(R.drawable.wr, 7, 7);
        ImageView whiteKnight = createChessPiece(R.drawable.wn, 7, 1);
        ImageView whiteKnight2 = createChessPiece(R.drawable.wn, 7, 6);
        ImageView whiteBishop = createChessPiece(R.drawable.wb, 7, 2);
        ImageView whiteBishop2 = createChessPiece(R.drawable.wb, 7, 5);
        ImageView whiteQueen = createChessPiece(R.drawable.wq, 7, 3);
        ImageView whiteKing = createChessPiece(R.drawable.wk, 7, 4);
        ImageView whitePawn1 = createChessPiece(R.drawable.wp, 6, 0);
        ImageView whitePawn2 = createChessPiece(R.drawable.wp, 6, 1);
        ImageView whitePawn3 = createChessPiece(R.drawable.wp, 6, 2);
        ImageView whitePawn4 = createChessPiece(R.drawable.wp, 6, 3);
        ImageView whitePawn5 = createChessPiece(R.drawable.wp, 6, 4);
        ImageView whitePawn6 = createChessPiece(R.drawable.wp, 6, 5);
        ImageView whitePawn7 = createChessPiece(R.drawable.wp, 6, 6);
        ImageView whitePawn8 = createChessPiece(R.drawable.wp, 6, 7);

        chessboard.addView(blackRook);
        chessboard.addView(blackRook2);
        chessboard.addView(blackKnight);
        chessboard.addView(blackKnight2);
        chessboard.addView(blackBishop);
        chessboard.addView(blackBishop2);
        chessboard.addView(blackQueen);
        chessboard.addView(blackKing);
        chessboard.addView(blackPawn1);
        chessboard.addView(blackPawn2);
        chessboard.addView(blackPawn3);
        chessboard.addView(blackPawn4);
        chessboard.addView(blackPawn5);
        chessboard.addView(blackPawn6);
        chessboard.addView(blackPawn7);
        chessboard.addView(blackPawn8);

        chessboard.addView(whiteRook);
        chessboard.addView(whiteRook2);
        chessboard.addView(whiteKnight);
        chessboard.addView(whiteKnight2);
        chessboard.addView(whiteBishop);
        chessboard.addView(whiteBishop2);
        chessboard.addView(whiteQueen);
        chessboard.addView(whiteKing);
        chessboard.addView(whitePawn1);
        chessboard.addView(whitePawn2);
        chessboard.addView(whitePawn3);
        chessboard.addView(whitePawn4);
        chessboard.addView(whitePawn5);
        chessboard.addView(whitePawn6);
        chessboard.addView(whitePawn7);
        chessboard.addView(whitePawn8);

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

                final String chessCoordinate = getChessCoordinate(row, col);
                square.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast(chessCoordinate);
                    }
                });

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

    private String getChessCoordinate(int row, int col) {
        char file = (char) ('a' + col);
        char rank = (char) ('8' - row);
        return String.valueOf(file) + rank;
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, "Clicked Square: " + message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private ImageView createChessPiece(int imageResourceId, int x, int y) {
        ImageView piece = new ImageView(this);
        piece.setImageResource(imageResourceId);
        piece.setScaleType(ImageView.ScaleType.FIT_CENTER);
        piece.setAdjustViewBounds(true);

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = 0;
        layoutParams.height = 0;
        layoutParams.rowSpec = GridLayout.spec(x, 1);
        layoutParams.columnSpec = GridLayout.spec(y, 1);
        layoutParams.setGravity(Gravity.FILL);
        piece.setLayoutParams(layoutParams);

        return piece;
    }

}
