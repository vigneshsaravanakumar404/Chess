package com.example.chess;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ComputerSettingsActivity extends AppCompatActivity {


    SeekBar seekBar;
    ImageView engineIcon;
    TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_settings);

        SeekBar seekBar = findViewById(R.id.level);
        TextView name = findViewById(R.id.name);
        ImageView engineIcon = findViewById(R.id.engineIcon);

        seekBar.setMax(20);
        seekBar.setMin(1);
        seekBar.setProgress(10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 10;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                ComputerActivity.level = progressChangedValue;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}