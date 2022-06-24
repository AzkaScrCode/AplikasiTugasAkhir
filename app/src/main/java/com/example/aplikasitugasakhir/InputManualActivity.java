package com.example.aplikasitugasakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InputManualActivity extends AppCompatActivity {

    Button masuk1,masuk2,masuk3,masuk4, masuk5, masuk6, masuk7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_manual);

        masuk5 = findViewById(R.id.bt_inputmn2016);
        masuk1 = findViewById(R.id.bt_inputmn2017);
        masuk2 = findViewById(R.id.bt_inputmn2018);
        masuk3 = findViewById(R.id.bt_inputmn2019);
        masuk4 = findViewById(R.id.bt_inputmn2020);
        masuk6 = findViewById(R.id.bt_inputmn2021);
        masuk7 = findViewById(R.id.bt_inputmn2022);

        masuk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InputManualActivity2017.class);
                startActivity(intent);
            }
        });

        masuk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InputManualActivity2018.class);
                startActivity(intent);
            }
        });

        masuk3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InputManualActivity2019.class);
                startActivity(intent);
            }
        });

        masuk4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InputManualActivity2020.class);
                startActivity(intent);
            }
        });

        masuk5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InputManualActivity2016.class);
                startActivity(intent);
            }
        });

        masuk6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InputManual2021Activity.class);
                startActivity(intent);
            }
        });

        masuk7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InputManual2022Activity.class);
                startActivity(intent);
            }
        });
    }
}