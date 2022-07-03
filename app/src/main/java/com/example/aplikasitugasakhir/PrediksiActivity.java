package com.example.aplikasitugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class PrediksiActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnPrediksi;
    private Spinner mSpinnerYear, mSpinnerPeriode;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private static final String TAG = "prediksi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediksi);

        mSpinnerYear = findViewById(R.id.spinner);
        mSpinnerPeriode = findViewById(R.id.spinner2);

        mBtnPrediksi = findViewById(R.id.button_prediksi);
        mBtnPrediksi.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

    }

    @Override
    public void onClick(View view) {

        String yearSpinner = (String) mSpinnerYear.getSelectedItem();
        String periodeSpinner = (String) mSpinnerPeriode.getSelectedItem();

        int periode = Integer.valueOf(periodeSpinner) - 1;
        int year = Integer.valueOf(yearSpinner) ;
        int startYear = (year - 1) - periode;

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> tahuns = new ArrayList<>();
                Map<Long, List<Float>> nets = new HashMap<>();

                for (int i = startYear; i <= year-1; i++) {
                    tahuns.add("Net Laju Tahun " + i);
                }

                for (String tahun : tahuns) {
                    for (DataSnapshot item : snapshot.child(tahun).getChildren()) {

                        Long hari = item.child("hari").getValue(Long.class);
                        Float netLaju = item.child("netLaju").getValue(Float.class);

                        if (nets.get(hari) == null) {
                            List<Float> floats = new ArrayList<>();
                            floats.add(netLaju);
                            nets.put(hari, floats);
                        } else {
                            nets.get(hari).add(netLaju);
                        }
                    }
                }

                List<NetLaju> netLajus = new ArrayList<>();

                for (Map.Entry<Long, List<Float>> entry : nets.entrySet()) {

                    List<Float> floats = entry.getValue();
                    float sum = 0f;

                    for (Float netLaju : floats) {
                        sum = sum + netLaju;
                    }

                    float avg = sum / floats.size();

                    NetLaju netLaju = new NetLaju(Integer.valueOf(entry.getKey().toString()), avg);
                    netLajus.add(netLaju);
                }


                DatabaseReference ref = database.getReference("Net Laju Tahun " + yearSpinner);
                ref.setValue(netLajus);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ERROX", "onCancelled: " + error.getMessage());
            }
        });


    }


}