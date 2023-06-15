package com.example.chess;

import android.widget.ImageView;

public class ChessPiece {

    int x;
    int y;
    String piece;
    ImageView image;

    public ChessPiece(int x, int y, ImageView image, String piece) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.piece = piece;

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
