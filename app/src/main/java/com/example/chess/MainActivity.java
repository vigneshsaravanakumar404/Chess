package com.example.chess;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.github.bhlangonijr.chesslib.Board;


public class MainActivity extends AppCompatActivity {

    // Variables
    public static boolean isBlack = false;
    public static boolean isPlayerTurn;
    public static String fen = "r2qk2r/pp1n1ppp/2pbpn2/3p1b2/3P1B2/2PBPN2/PP1N1PPP/R2QK2R w KQkq - 0 1";
    GridLayout chessboard;
    ConstraintLayout mainLayout;
    String currentSelection = "";
    Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Variables
        chessboard = findViewById(R.id.chessboard);
        mainLayout = findViewById(R.id.mainLayout);
        board = new Board();
//
        // Initialize
        setChessboardDimensions();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        // Set this value based on the desired orientation of the chessboard
        createSquares(isBlack);
        setBoardPositionFromFEN(fen, isBlack);

        // Turn
        isPlayerTurn = !isBlack;

        // Set onClickListeners to each square
        for (int i = 0; i < chessboard.getChildCount(); i++) {
            ImageView square = (ImageView) chessboard.getChildAt(i);
            square.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the row and column of the clicked square
                    int row = 0;
                    int col = 0;
                    for (int i = 0; i < chessboard.getChildCount(); i++) {
                        if (chessboard.getChildAt(i) == v) {
                            row = i / 8;
                            col = i % 8;
                            break;
                        }
                    }

                    // Get the piece on the clicked square
                    String piece = getPieceFromBoardPosition(row, col);

                    if (piece.length() > 0 && isPlayerTurn) {
                        if (!isBlack && Character.isUpperCase(piece.charAt(0))) {
                            Log.d("LOG123", "You are playing black and clicked on a black piece ");
                            currentSelection = getBoardPositionFromRowCol(row, col);

                        } else if (isBlack && Character.isLowerCase(piece.charAt(0))) {
                            Log.d("LOG123", "You are playing white and clicked on a white piece");
                            currentSelection = getBoardPositionFromRowCol(row, col);
                        }
                    }


                }
            });
        }


    }


    // Board set up
    private void setChessboardDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        ViewGroup.LayoutParams layoutParams = chessboard.getLayoutParams();
        layoutParams.width = screenWidth;
        layoutParams.height = screenWidth;
        chessboard.setLayoutParams(layoutParams);
    }

    private void createSquares(boolean isWhiteAtBottom) {
        boolean isWhite = isWhiteAtBottom;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ImageView square = new ImageView(this);

                if (!isWhite) {
                    square.setBackgroundColor(Color.parseColor("#6ac3bd"));
                } else {
                    square.setBackgroundColor(Color.WHITE);
                }

                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                layoutParams.width = 0;
                layoutParams.height = 0;
                layoutParams.rowSpec = GridLayout.spec(row, 1f);
                layoutParams.columnSpec = GridLayout.spec(col, 1f);
                square.setLayoutParams(layoutParams);

                chessboard.addView(square);

                isWhite = !isWhite;
            }
            isWhite = !isWhite;
        }
    }


    // Game loop methods
    private void setBoardPositionFromFEN(String fen, boolean isWhiteAtBottom) {
        // Split the FEN string into separate parts
        String[] fenParts = fen.split(" ");

        String position = fenParts[0];
        String activeColor = fenParts[1];
        String castlingRights = fenParts[2];
        String enPassantSquare = fenParts[3];
        String halfMoveClock = fenParts[4];
        String fullMoveNumber = fenParts[5];

        // Clear the existing chessboard
        chessboard.removeAllViews();

        String[] positionRows = position.split("/");

        boolean isWhite = isWhiteAtBottom;

        for (int row = 0; row < 8; row++) {
            String fenRow;
            if (isWhiteAtBottom) {
                fenRow = positionRows[7 - row]; // Reverse the rows for white at the bottom
            } else {
                fenRow = positionRows[row]; // Keep the rows as is for black at the bottom
            }

            int col = 0;

            for (int i = 0; i < fenRow.length(); i++) {
                char c = fenRow.charAt(i);

                if (Character.isDigit(c)) {
                    // Empty squares indicated by a number in the FEN
                    int emptySquares = Character.getNumericValue(c);

                    for (int j = 0; j < emptySquares; j++) {
                        ImageView square = new ImageView(this);
                        if (!isWhite) {
                            square.setBackgroundColor(Color.parseColor("#6ac3bd"));
                        } else {
                            square.setBackgroundColor(Color.TRANSPARENT);
                        }

                        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                        layoutParams.width = 0;
                        layoutParams.height = 0;
                        layoutParams.rowSpec = GridLayout.spec(row, 1f);
                        layoutParams.columnSpec = GridLayout.spec(col, 1f);
                        square.setLayoutParams(layoutParams);

                        chessboard.addView(square);

                        col++;
                        isWhite = !isWhite; // Toggle color
                    }
                } else {
                    // Non-empty squares represent pieces
                    ImageView square = new ImageView(this);
                    if (!isWhite) {
                        square.setBackgroundColor(Color.parseColor("#6ac3bd"));
                    } else {
                        square.setBackgroundColor(Color.TRANSPARENT);
                    }
                    int pieceResourceId = getPieceResourceId(c);
                    square.setImageResource(pieceResourceId);
                    GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                    layoutParams.width = 0;
                    layoutParams.height = 0;
                    layoutParams.rowSpec = GridLayout.spec(row, 1f);
                    layoutParams.columnSpec = GridLayout.spec(col, 1f);
                    square.setLayoutParams(layoutParams);
                    chessboard.addView(square);
                    col++;
                    isWhite = !isWhite; // Toggle color
                }
            }
            isWhite = !isWhite; // Toggle color at the end of each row
        }
        // Additional processing based on other FEN information can be added here
        // For example, you can use the `activeColor`, `castlingRights`, `enPassantSquare`, `halfMoveClock`, and `fullMoveNumber` variables
    }

    private int getPieceResourceId(char piece) {
        // Map FEN characters to piece resource IDs
        switch (piece) {
            case 'r':
                return R.drawable.br;
            case 'n':
                return R.drawable.bn;
            case 'b':
                return R.drawable.bb;
            case 'q':
                return R.drawable.bq;
            case 'k':
                return R.drawable.bk;
            case 'p':
                return R.drawable.bp;
            case 'R':
                return R.drawable.wr;
            case 'N':
                return R.drawable.wn;
            case 'B':
                return R.drawable.wb;
            case 'Q':
                return R.drawable.wq;
            case 'K':
                return R.drawable.wk;

            case 'P':
                return R.drawable.wp;
            default:
                return 0; // Return 0 for an unknown piece
        }
    }

    private String getPieceFromBoardPosition(int row, int col) {
        ImageView square = (ImageView) chessboard.getChildAt(row * 8 + col);
        Drawable drawable = square.getDrawable();
        if (drawable == null) {
            return "";
        }
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap == null) {
                return "";
            }
            Resources resources = getResources();

            if (bitmap.sameAs(BitmapFactory.decodeResource(resources, R.drawable.br))) {
                return "r";
            }
            if (bitmap.sameAs(BitmapFactory.decodeResource(resources, R.drawable.bn))) {
                return "n";
            }
            if (bitmap.sameAs(BitmapFactory.decodeResource(resources, R.drawable.bb))) {
                return "b";
            }
            if (bitmap.sameAs(BitmapFactory.decodeResource(resources, R.drawable.bq))) {
                return "q";
            }
            if (bitmap.sameAs(BitmapFactory.decodeResource(resources, R.drawable.bk))) {
                return "k";
            }
            if (bitmap.sameAs(BitmapFactory.decodeResource(resources, R.drawable.bp))) {
                return "p";
            }
            if (bitmap.sameAs(BitmapFactory.decodeResource(resources, R.drawable.wr))) {
                return "R";
            }
            if (bitmap.sameAs(BitmapFactory.decodeResource(resources, R.drawable.wn))) {
                return "N";
            }
            if (bitmap.sameAs(BitmapFactory.decodeResource(resources, R.drawable.wb))) {
                return "B";
            }
            if (bitmap.sameAs(BitmapFactory.decodeResource(resources, R.drawable.wq))) {
                return "Q";
            }
            if (bitmap.sameAs(BitmapFactory.decodeResource(resources, R.drawable.wk))) {
                return "K";
            }
            if (bitmap.sameAs(BitmapFactory.decodeResource(resources, R.drawable.wp))) {
                return "P";
            }
        }
        return "";
    }

    private String getBoardPositionFromRowCol(int row, int col) {
        String[] cols = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] rows = {"8", "7", "6", "5", "4", "3", "2", "1"};
        return cols[col] + rows[row];
    }


    //!TODO: methods to make
    // TODO: getLegalMoves(String fen, String piece);
    // TODO: getLegalMovesIfKingIsInCheck(String fen, String piece);
    // TODO: isCheckMate(String fen);
    // TODO: isStaleMate(String fen);
    // TODO: isDraw(String fen);
    // TODO: isThreeFoldRepetition(String fen); #AMBITOUS AF
    // TODO: isFiftyMoveRule(String fen); #AMBITOUS AF
    // TODO: isInsufficientMaterial(String fen); #AMBITOUS AF


//    private List<String> getLegalMoves(String fen, int row, int col) {
//        List<String> legalMoves = new ArrayList<>();
//        String piece = getPieceFromBoardPosition(row, col);
//
//        switch (piece) {
//            case "r":
//            case "R":
//                return getRookMoves(fen, row, col);
//            case "n":
//            case "N":
//                return getKnightMoves(fen, row, col);
//            case "b":
//            case "B":
//                return getBishopMoves(fen, row, col);
//            case "q":
//            case "Q":
//                return getQueenMoves(fen, row, col);
//            case "k":
//            case "K":
//                return getKingMoves(fen, row, col);
//            case "p":
//            case "P":
//                return getPawnMoves(fen, row, col);
//            default:
//                return legalMoves;
//        }
//    }


}
//!TODO IN COMPUTER SCREEN
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