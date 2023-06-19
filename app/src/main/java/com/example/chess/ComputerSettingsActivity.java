package com.example.chess;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ComputerSettingsActivity extends AppCompatActivity {

    SeekBar seekBar;
    ImageView engineIcon;
    TextView name;
    int[] engineIcons;
    Button play, statistics;
    RadioButton white, black, random;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_settings);

        // Definitions
        seekBar = findViewById(R.id.level);
        name = findViewById(R.id.name);
        engineIcon = findViewById(R.id.engineIcon);
        engineIcons = new int[]{R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine, R.drawable.ten, R.drawable.eleven, R.drawable.twelve, R.drawable.thirteen, R.drawable.fourteen, R.drawable.fifteen, R.drawable.sixteen, R.drawable.seventeen, R.drawable.eighteen, R.drawable.nineteen, R.drawable.twenty};
        play = findViewById(R.id.play);
        white = findViewById(R.id.white);
        black = findViewById(R.id.black);
        random = findViewById(R.id.random);
        statistics = findViewById(R.id.statistics);


        // Initialization
        getWindow().getDecorView().setSystemUiVisibility(5894);
        random.setChecked(true);
        name.setText("Stockfish 10");
        engineIcon.setImageResource(engineIcons[9]);
        engineIcon.getLayoutParams().height = 500;
        engineIcon.getLayoutParams().width = 500;
        engineIcon.requestLayout();
        seekBar.setMax(20);
        seekBar.setMin(1);
        seekBar.setProgress(10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ComputerActivity.level = progress;
                name.setText("Stockfish " + progress);
                engineIcon.setImageResource(engineIcons[progress - 1]);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        // Radio Buttons
        white.setOnClickListener(v -> {
            white.setChecked(true);
            black.setChecked(false);
            random.setChecked(false);
        });
        black.setOnClickListener(v -> {
            white.setChecked(false);
            black.setChecked(true);
            random.setChecked(false);
        });
        random.setOnClickListener(v -> {
            white.setChecked(false);
            black.setChecked(false);
            random.setChecked(true);
        });

        play.setOnClickListener(v -> {
            ComputerActivity.level = seekBar.getProgress();
            ComputerActivity.fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
            ComputerActivity.isBlack = black.isChecked() || (!white.isChecked() && Math.random() < 0.5);
            startActivity(new Intent(this, ComputerActivity.class));
        });
        statistics.setOnClickListener(v -> startActivity(new Intent(this, StatisticsActivity.class)));
    }

}