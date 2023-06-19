package com.example.chess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class SquareView extends View {
    private Paint paint;
    private int squareColor;

    public SquareView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        squareColor = Color.TRANSPARENT; // Default color is transparent
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height);

        int left = (width - size) / 2;
        int top = (height - size) / 2;
        int right = left + size;
        int bottom = top + size;

        paint.setColor(squareColor);
        canvas.drawRect(left, top, right, bottom, paint);
    }
}
