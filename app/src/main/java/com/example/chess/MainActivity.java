package com.example.chess;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Variables
    ImageView board;
    ImageView br;
    ImageView bn;
    ImageView bb;
    ImageView bq;
    ImageView bk;
    ImageView bp;
    ImageView wr;
    ImageView wn;
    ImageView wb;
    ImageView wq;
    ImageView wk;
    ImageView wp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialization
        board = findViewById(R.id.board);


    }
}