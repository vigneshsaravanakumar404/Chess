package com.example.chess;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ComputerSettingsActivity extends AppCompatActivity {


    SeekBar seekBar;
    ImageView engineIcon;
    TextView name;
    int[] engineIcons = new int[20];
    Button play;


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

        name.setText("Stockfish 10");
        engineIcon.setImageResource(engineIcons[9]);
        engineIcon.getLayoutParams().height = 500;
        engineIcon.getLayoutParams().width = 500;
        engineIcon.requestLayout();
        seekBar.setMax(20);
        seekBar.setMin(1);
        seekBar.setProgress(10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 10;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                ComputerActivity.level = progressChangedValue;
                name.setText("Stockfish " + progressChangedValue);
                engineIcon.setImageResource(engineIcons[progressChangedValue - 1]);

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        play.setOnClickListener(v -> {
            ComputerActivity.level = seekBar.getProgress();
            ComputerActivity.fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
            startActivity(new Intent(this, ComputerActivity.class));
        });


    }
}