package com.example.chess;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    // Variables
    public static String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    GridLayout chessboard;
    ConstraintLayout mainLayout;
    ArrayList<ChessPiece> blackPieces = new ArrayList<>();
    ArrayList<ChessPiece> whitePieces = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Variables
        chessboard = findViewById(R.id.chessboard);
        mainLayout = findViewById(R.id.mainLayout);


        // Initialize
        setChessboardDimensions();
        createSquares();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        // Initialize and place pieces on the board
        ChessPiece blackRook = new ChessPiece(0, 0, createChessPiece(R.drawable.br, 0, 0), "br");
        ChessPiece blackRook2 = new ChessPiece(0, 7, createChessPiece(R.drawable.br, 0, 7), "br");
        ChessPiece blackKnight = new ChessPiece(0, 1, createChessPiece(R.drawable.bn, 0, 1), "bn");
        ChessPiece blackKnight2 = new ChessPiece(0, 6, createChessPiece(R.drawable.bn, 0, 6), "bn");
        ChessPiece blackBishop = new ChessPiece(0, 2, createChessPiece(R.drawable.bb, 0, 2), "bb");
        ChessPiece blackBishop2 = new ChessPiece(0, 5, createChessPiece(R.drawable.bb, 0, 5), "bb");
        ChessPiece blackQueen = new ChessPiece(0, 3, createChessPiece(R.drawable.bq, 0, 3), "bq");
        ChessPiece blackKing = new ChessPiece(0, 4, createChessPiece(R.drawable.bk, 0, 4), "bk");
        ChessPiece blackPawn1 = new ChessPiece(1, 0, createChessPiece(R.drawable.bp, 1, 0), "bp");
        ChessPiece blackPawn2 = new ChessPiece(1, 1, createChessPiece(R.drawable.bp, 1, 1), "bp");
        ChessPiece blackPawn3 = new ChessPiece(1, 2, createChessPiece(R.drawable.bp, 1, 2), "bp");
        ChessPiece blackPawn4 = new ChessPiece(1, 3, createChessPiece(R.drawable.bp, 1, 3), "bp");
        ChessPiece blackPawn5 = new ChessPiece(1, 4, createChessPiece(R.drawable.bp, 1, 4), "bp");
        ChessPiece blackPawn6 = new ChessPiece(1, 5, createChessPiece(R.drawable.bp, 1, 5), "bp");
        ChessPiece blackPawn7 = new ChessPiece(1, 6, createChessPiece(R.drawable.bp, 1, 6), "bp");
        ChessPiece blackPawn8 = new ChessPiece(1, 7, createChessPiece(R.drawable.bp, 1, 7), "bp");

        ChessPiece whiteRook = new ChessPiece(7, 0, createChessPiece(R.drawable.wr, 7, 0), "wr");
        ChessPiece whiteRook2 = new ChessPiece(7, 7, createChessPiece(R.drawable.wr, 7, 7), "wr");
        ChessPiece whiteKnight = new ChessPiece(7, 1, createChessPiece(R.drawable.wn, 7, 1), "wn");
        ChessPiece whiteKnight2 = new ChessPiece(7, 6, createChessPiece(R.drawable.wn, 7, 6), "wn");
        ChessPiece whiteBishop = new ChessPiece(7, 2, createChessPiece(R.drawable.wb, 7, 2), "wb");
        ChessPiece whiteBishop2 = new ChessPiece(7, 5, createChessPiece(R.drawable.wb, 7, 5), "wb");
        ChessPiece whiteQueen = new ChessPiece(7, 3, createChessPiece(R.drawable.wq, 7, 3), "wq");
        ChessPiece whiteKing = new ChessPiece(7, 4, createChessPiece(R.drawable.wk, 7, 4), "wk");
        ChessPiece whitePawn1 = new ChessPiece(6, 0, createChessPiece(R.drawable.wp, 6, 0), "wp");
        ChessPiece whitePawn2 = new ChessPiece(6, 1, createChessPiece(R.drawable.wp, 6, 1), "wp");
        ChessPiece whitePawn3 = new ChessPiece(6, 2, createChessPiece(R.drawable.wp, 6, 2), "wp");
        ChessPiece whitePawn4 = new ChessPiece(6, 3, createChessPiece(R.drawable.wp, 6, 3), "wp");
        ChessPiece whitePawn5 = new ChessPiece(6, 4, createChessPiece(R.drawable.wp, 6, 4), "wp");
        ChessPiece whitePawn6 = new ChessPiece(6, 5, createChessPiece(R.drawable.wp, 6, 5), "wp");
        ChessPiece whitePawn7 = new ChessPiece(6, 6, createChessPiece(R.drawable.wp, 6, 6), "wp");
        ChessPiece whitePawn8 = new ChessPiece(6, 7, createChessPiece(R.drawable.wp, 6, 7), "wp");
        blackPieces.add(blackRook);
        blackPieces.add(blackRook2);
        blackPieces.add(blackKnight);
        blackPieces.add(blackKnight2);
        blackPieces.add(blackBishop);
        blackPieces.add(blackBishop2);
        blackPieces.add(blackQueen);
        blackPieces.add(blackKing);
        blackPieces.add(blackPawn1);
        blackPieces.add(blackPawn2);
        blackPieces.add(blackPawn3);
        blackPieces.add(blackPawn4);
        blackPieces.add(blackPawn5);
        blackPieces.add(blackPawn6);
        blackPieces.add(blackPawn7);
        blackPieces.add(blackPawn8);

        whitePieces.add(whiteRook);
        whitePieces.add(whiteRook2);
        whitePieces.add(whiteKnight);
        whitePieces.add(whiteKnight2);
        whitePieces.add(whiteBishop);
        whitePieces.add(whiteBishop2);
        whitePieces.add(whiteQueen);
        whitePieces.add(whiteKing);
        whitePieces.add(whitePawn1);
        whitePieces.add(whitePawn2);
        whitePieces.add(whitePawn3);
        whitePieces.add(whitePawn4);
        whitePieces.add(whitePawn5);
        whitePieces.add(whitePawn6);
        whitePieces.add(whitePawn7);
        whitePieces.add(whitePawn8);


        // add a textview at the top


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

    // Create a method to update the position of a piece on the board
    private void updatePosition(ImageView piece, int x, int y) {
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = 0;
        layoutParams.height = 0;
        layoutParams.rowSpec = GridLayout.spec(x, 1);
        layoutParams.columnSpec = GridLayout.spec(y, 1);
        layoutParams.setGravity(Gravity.FILL);
        piece.setLayoutParams(layoutParams);
    }


}

//! TODO IN COMPUTER SCREEN
// TODO: class for each square or piece
// TODO: update moves based on clicks
// TODO: only allowed to make legal moves
// TODO: only allowed to make moves during your turn
// TODO: time
// TODO: check checkmate
// TODO: check stalemate
// TODO: check draw
// TODO: AI responses
// TODO: Update statistics
// TODO: Update game history

//! TODO IN SPLASH SCREEN
//! TODO IN LOGIC SCREEN
//! TODO IN HOME SCREEN
//! TODO IN MULTIPLAYER SCREEN


// background
//        AnimationDrawable animationDrawable = (AnimationDrawable) mainLayout.getBackground();
//        animationDrawable.setEnterFadeDuration(5000);
//        animationDrawable.setExitFadeDuration(5000);
//        animationDrawable.start();