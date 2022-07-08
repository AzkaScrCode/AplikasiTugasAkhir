package com.example.aplikasitugasakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button tbmanual, tbprediksi, btWetDryNormal, mBtnLiatPrediksi, mBtnWaterReq;

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Apakah anda ingin keluar dari menu pengolahan data?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbmanual = findViewById(R.id.bt_inputmn);
        tbprediksi = findViewById(R.id.bt_tambahprediksi);
        btWetDryNormal = findViewById(R.id.bt_wet_dry_normal);
        mBtnLiatPrediksi = findViewById(R.id.bt_liat_prediksi);
        mBtnWaterReq = findViewById(R.id.bt_input_water_req);

        tbmanual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InputManualActivity.class);
                startActivity(intent);
            }
        });

        tbprediksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PrediksiActivity.class);
                startActivity(intent);
            }
        });

        btWetDryNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WaterAvailabilityActivity.class);
                startActivity(intent);
            }
        });

        mBtnLiatPrediksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GraphPrediksiActivity.class);
                startActivity(intent);
            }
        });

        mBtnWaterReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WaterRequirementActivity.class);
                startActivity(intent);
            }
        });

    }

}