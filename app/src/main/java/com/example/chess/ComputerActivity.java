package com.example.chess;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ComputerActivity extends AppCompatActivity {

    // Variables
    public static boolean isBlack = false;
    public static boolean isPlayerTurn;
    public static String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    public static int level = 1;
    GridLayout chessboard;
    ConstraintLayout mainLayout;
    String currentSelection = "";
    Board board;
    ImageView computerIcon;
    Bitmap bitmap;
    Drawable scaledComputerIcon;
    TextView computerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer);

        // Variables
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.computericon);
        scaledComputerIcon = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 350, 350, true));
        computerIcon = findViewById(R.id.computerIcon);
        chessboard = findViewById(R.id.chessboard);
        mainLayout = findViewById(R.id.mainLayout);
        computerName = findViewById(R.id.computerName);
        board = new Board();

        // Initialize
        setChessboardDimensions();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        createSquares(isBlack);
        computerIcon.setImageDrawable(scaledComputerIcon);
        computerName.setText("Stockfish Level " + level);
        setBoardPositionFromFEN(fen, isBlack);
        isPlayerTurn = !isBlack;


        // Set onClickListeners to each square
        setOnClickListeners();

    }


    // Board set up
    private void setChessboardDimensions() {
        int screenWidth = chessboard.getWidth();

        ViewGroup.LayoutParams layoutParams = chessboard.getLayoutParams();
        layoutParams.width = screenWidth;
        layoutParams.height = screenWidth;
        chessboard.setLayoutParams(layoutParams);
    }
    private void createSquares(boolean isWhiteAtBottom) {
        boolean isWhite = isWhiteAtBottom;

        for (int i = 0; i < 64; i++) {
            ImageView square = new ImageView(this);
            square.setBackgroundColor(isWhite ? Color.WHITE : Color.parseColor("#6ac3bd"));

            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width = 0;
            layoutParams.height = 0;
            layoutParams.rowSpec = GridLayout.spec(i / 8, 1f);
            layoutParams.columnSpec = GridLayout.spec(i % 8, 1f);
            square.setLayoutParams(layoutParams);

            chessboard.addView(square);

            isWhite = !isWhite;
        }
    }
    private void setOnClickListeners() {
        for (int i = 0; i < chessboard.getChildCount(); i++) {
            ImageView square = (ImageView) chessboard.getChildAt(i);
            square.setOnClickListener(v -> {
                int position = chessboard.indexOfChild(v);
                int row = position / 8;
                int col = position % 8;

                String piece = getPieceFromBoardPosition(row, col);

                if (!isPlayerTurn) {
                    disableOnClickListeners();
                    new ComputerMoveTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else if (piece.length() > 0 && currentSelection.isEmpty()) {
                    if ((!isBlack && Character.isUpperCase(piece.charAt(0))) || (isBlack && Character.isLowerCase(piece.charAt(0)) && piece.charAt(0) != '0')) {
                        currentSelection = getBoardPositionFromRowCol(row, col);
                    }
                } else if (!currentSelection.isEmpty()) {
                    String move = currentSelection + getBoardPositionFromRowCol(row, col);
                    boolean isLegal = isMoveLegal(move);

                    if (isLegal) {
                        makeMove(move);
                        disableOnClickListeners();

                        if (!isPlayerTurn) {
                            new ComputerMoveTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        }
                    } else {
                        Toast.makeText(ComputerActivity.this, "Illegal move!", Toast.LENGTH_SHORT).show();
                    }
                    currentSelection = "";
                }

                if (isGameOver()) {
                    Log.d("GAME OVER", "GAME OVER");
                    if (isCheckmate()) {
                        Toast.makeText(ComputerActivity.this, "Checkmate!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ComputerActivity.this, "Stalemate!", Toast.LENGTH_SHORT).show();
                    }
                    disableOnClickListeners();
                }
            });
        }
    }
    private boolean isMoveLegal(String move) {
        List<Move> moves = board.legalMoves();
        return moves.stream().anyMatch(m -> m.toString().equals(move));
    }
    private void makeMove(String move) {
        board.doMove(new Move(move, board.getSideToMove()));
        fen = board.getFen();
        setBoardPositionFromFEN(fen, isBlack);
        isPlayerTurn = !isPlayerTurn;
    }


    // Game loop methods
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
    private void setBoardPositionFromFEN(String fen, boolean isWhiteAtBottom) {
        // Split the FEN string into separate parts
        String[] fenParts = fen.split(" ");

        String position = fenParts[0];

        // Clear the existing chessboard
        chessboard.removeAllViews();

        String[] positionRows = position.split("/");

        boolean isWhite = isWhiteAtBottom;

        for (int row = 0; row < 8; row++) {
            String fenRow = isWhiteAtBottom ? positionRows[7 - row] : positionRows[row];


            for (char c : fenRow.toCharArray()) {
                if (Character.isDigit(c)) {
                    // Empty squares indicated by a number in the FEN
                    int emptySquares = Character.getNumericValue(c);

                    for (int j = 0; j < emptySquares; j++) {
                        ImageView square = createChessboardSquare(isWhite);
                        chessboard.addView(square);

                        isWhite = !isWhite; // Toggle color
                    }
                } else {
                    // Non-empty squares represent pieces
                    ImageView square = createChessboardSquare(isWhite);
                    int pieceResourceId = getPieceResourceId(c);
                    square.setImageResource(pieceResourceId);
                    chessboard.addView(square);

                    isWhite = !isWhite; // Toggle color
                }
            }
            isWhite = !isWhite; // Toggle color at the end of each row
        }
    }
    private ImageView createChessboardSquare(boolean isWhite) {
        ImageView square = new ImageView(this);
        square.setBackgroundColor(isWhite ? Color.TRANSPARENT : Color.parseColor("#6ac3bd"));

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        layoutParams.width = 0;
        layoutParams.height = 0;
        layoutParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        square.setLayoutParams(layoutParams);

        return square;
    }
    private int getPieceResourceId(char piece) {
        Map<Character, Integer> pieceResourceMap = new HashMap<>();
        pieceResourceMap.put('r', R.drawable.br);
        pieceResourceMap.put('n', R.drawable.bn);
        pieceResourceMap.put('b', R.drawable.bb);
        pieceResourceMap.put('q', R.drawable.bq);
        pieceResourceMap.put('k', R.drawable.bk);
        pieceResourceMap.put('p', R.drawable.bp);
        pieceResourceMap.put('R', R.drawable.wr);
        pieceResourceMap.put('N', R.drawable.wn);
        pieceResourceMap.put('B', R.drawable.wb);
        pieceResourceMap.put('Q', R.drawable.wq);
        pieceResourceMap.put('K', R.drawable.wk);
        pieceResourceMap.put('P', R.drawable.wp);

        return pieceResourceMap.getOrDefault(piece, 0);
    }
    private String getBoardPositionFromRowCol(int row, int col) {
        if (isBlack) {
            row = 7 - row; // Flip row if playing as black
        }

        char file = (char) ('a' + col);
        int rank = 8 - row;

        return Character.toString(file) + rank;
    }
    private void disableOnClickListeners() {
        for (View view : chessboard.getTouchables()) {
            view.setOnClickListener(null);
        }
    }


    // Game End
    private boolean isCheckmate() {
        return board.isMated();
    }
    private boolean isStalemate() {
        return board.isStaleMate();
    }
    private boolean isDraw() {
        return board.isDraw();
    }
    private boolean isGameOver() {
        return isCheckmate() || isStalemate() || isDraw();
    }

    // ComputerMoveTask
    private class ComputerMoveTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            String url = "https://731d-173-63-234-100.ngrok-free.app//engine/level/?fen=" + fen + "&skill-level=" + level;
            String headerKey = "ngrok-skip-browser-warning";
            String headerValue = "true";

            try {
                URL urlObject = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty(headerKey, headerValue);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder content = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        content.append(line);
                    }
                    reader.close();

                    final String moveContent = content.toString();

                    runOnUiThread(() -> {

                        board.doMove(moveContent);

                        // Update the FEN
                        fen = board.getFen();
                        setBoardPositionFromFEN(fen, isBlack);
                        isPlayerTurn = !isPlayerTurn;
                    });

                } else {
                    Log.d("LOG123", "Error: " + responseCode);
                }

                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void result) {

            setOnClickListeners();


        }

    }

    // TODO: make first move/game ending happen in the background
    // TODO: board mirrored on y axis for black



}

//!TODO IN COMPUTER SCREEN
// TODO: check checkmate
// TODO: check stalemate
// TODO: check draw
// TODO: Update statistics
// TODO: Update game history

//! TODO IN SPLASH SCREEN
//! TODO IN LOGIC SCREEN
//! TODO IN HOME SCREEN
//! TODO IN MULTIPLAYER SCREEN

