package com.example.aplikasitugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aplikasitugasakhir.model.WetNormalDry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaterAvailabilityActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseDatabase database;
    private DatabaseReference reference;

    private Button btnHitungWet;
    private Spinner mSpinnerWaterAvailYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_availability);

        mSpinnerWaterAvailYear = findViewById(R.id.spinner_tahun_water_avail);
        btnHitungWet = findViewById(R.id.button_hitung_wet);
        btnHitungWet.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

    }

    @Override
    public void onClick(View view) {

        String yearSpinner = (String) mSpinnerWaterAvailYear.getSelectedItem();

        int initYear = 1988;
        int year = Integer.valueOf(yearSpinner);

        database.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<Long, List<Double>> nets = new HashMap<>();

                double kering = 0.8d;
                double normal = 0.5d;
                double basah = 0.2d;

                List<WetNormalDry> wets = new ArrayList<>();

                for(int i = initYear; i<= year;i++){
                    String keyToFind = "Net Laju Tahun " + i;

                    if(!snapshot.hasChild(keyToFind)) continue;

                    for(DataSnapshot leaf : snapshot.child(keyToFind).getChildren()){
                            Long hari = leaf.child("hari").getValue(Long.class);
                            Object o = leaf.child("netLaju").getValue();
                            String netLajuStr = String.valueOf(o);
                            Double netLaju = Double.valueOf(netLajuStr);

                            if (nets.get(hari) == null) {
                                List<Double> floats = new ArrayList<>();
                                floats.add(netLaju);
                                nets.put(hari, floats);
                            } else {
                                nets.get(hari).add(netLaju);
                            }
                    }
                }

                for (Map.Entry<Long, List<Double>> entry : nets.entrySet()) {
                    List<Double> floats = entry.getValue();

                    Collections.sort(floats);

                    double countKering = floats.size() * kering;
                    double countNormal = floats.size() * normal;
                    double countBasah = floats.size() * basah;

                    int pickKering  = (int) Math.ceil(countKering);
                    int pickNormal = (int) Math.ceil(countNormal);
                    int pickBasah = (int) Math.ceil(countBasah);

                    double keringVal = floats.get(pickKering-1);
                    double normalVal = floats.get(pickNormal-1);
                    double basahVal = floats.get(pickBasah-1);

                    WetNormalDry wetNormalDry = new WetNormalDry(entry.getKey(), basahVal,
                            normalVal, keringVal);
                    wets.add(wetNormalDry);
                }


                DatabaseReference ref = database.getReference("air wet normal " + year);
                ref.setValue(wets);

                Toast.makeText(view.getContext(),"Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}