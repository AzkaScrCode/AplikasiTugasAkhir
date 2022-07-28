package com.example.aplikasitugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.aplikasitugasakhir.model.WetNormalDry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaterRequirementActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private EditText et1,et2,et3,et4,et5,et6,et7,et8,et9,et10,et11,et12;

    private Button mBtnHitunRequirement;
    private Spinner mSpinnerYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_requirement);

        database = FirebaseDatabase.getInstance();

        et1 = findViewById(R.id.et_watreq_1);
        et2 = findViewById(R.id.et_watreq_2);
        et3 = findViewById(R.id.et_watreq_3);
        et4 = findViewById(R.id.et_watreq_4);
        et5 = findViewById(R.id.et_watreq_5);
        et6 = findViewById(R.id.et_watreq_6);
        et7 = findViewById(R.id.et_watreq_7);
        et8 = findViewById(R.id.et_watreq_8);
        et9 = findViewById(R.id.et_watreq_9);
        et10 = findViewById(R.id.et_watreq_10);
        et11 = findViewById(R.id.et_watreq_11);
        et12 = findViewById(R.id.et_watreq_12);

        mSpinnerYear = findViewById(R.id.spinner_tahun_wat_req);
        mBtnHitunRequirement = findViewById(R.id.bt_calculate_req);
        mBtnHitunRequirement.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Map<Integer, Double> map = new HashMap<>();
        String yearSpinner = (String) mSpinnerYear.getSelectedItem();
        int year = Integer.valueOf(yearSpinner);

        map.put(1,Double.valueOf(et1.getText().toString()));
        map.put(2,Double.valueOf(et2.getText().toString()));
        map.put(3,Double.valueOf(et3.getText().toString()));
        map.put(4,Double.valueOf(et4.getText().toString()));
        map.put(5,Double.valueOf(et5.getText().toString()));
        map.put(6,Double.valueOf(et6.getText().toString()));
        map.put(7,Double.valueOf(et7.getText().toString()));
        map.put(8,Double.valueOf(et8.getText().toString()));
        map.put(9,Double.valueOf(et9.getText().toString()));
        map.put(10,Double.valueOf(et10.getText().toString()));
        map.put(11,Double.valueOf(et11.getText().toString()));
        map.put(12,Double.valueOf(et12.getText().toString()));

        DatabaseReference reference = database.getReference("Water Balance/"+yearSpinner);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<WetNormalDry> wetNormalDries = new ArrayList<>();

                for(DataSnapshot item : snapshot.getChildren()){
                    Log.d("KUDAX", "onDataChange: " + item.child("dry").getValue());

                    long hari = item.child("hari").getValue(Long.class);
                    String dry = item.child("dry").getValue().toString();
                    String normal = item.child("normal").getValue().toString();
                    String wet = item.child("wet").getValue().toString();

                    double dryD = Double.valueOf(dry);
                    double normalD = Double.valueOf(normal);
                    double wetD = Double.valueOf(wet);

                    double req = 0d;

                    if(hari >= 0 && hari <= 10){
                        req = map.get(1);
                    }else if (hari >= 11 && hari <= 20){
                        req = map.get(2);
                    }else if (hari >= 21 && hari <= 30){
                        req = map.get(3);
                    }else if (hari >= 31 && hari <= 40){
                        req = map.get(4);
                    }else if (hari >= 41 && hari <= 50){
                        req = map.get(5);
                    }else if (hari >= 51 && hari <= 60){
                        req = map.get(6);
                    }else if (hari >= 61 && hari <= 70){
                        req = map.get(7);
                    }else if (hari >= 71 && hari <= 80){
                        req = map.get(8);
                    }else if (hari >= 81 && hari <= 90){
                        req = map.get(9);
                    }else if (hari >= 91 && hari <= 100){
                        req = map.get(10);
                    }else if (hari >= 101 && hari <= 110){
                        req = map.get(11);
                    }else if (hari >= 111 && hari <= 120){
                        req = map.get(12);
                    }

                    double dryBalance = dryD - req;
                    double normalBalance = normalD - req;
                    double wetBalance = wetD - req;

                    String dryStatus = dryBalance < 0 ? "irigasi":"drain";
                    String normalStatus = normalBalance < 0 ? "irigasi":"drain";
                    String wetStatus = wetBalance < 0 ? "irigasi":"drain";

                    WetNormalDry wetNormalDry = new WetNormalDry();
                    wetNormalDry.setHari(hari);
                    wetNormalDry.setDry(dryD);
                    wetNormalDry.setNormal(normalD);
                    wetNormalDry.setWet(wetD);
                    wetNormalDry.setWaterReq(req);
                    wetNormalDry.setDryBalance(dryBalance);
                    wetNormalDry.setNormalBalance(normalBalance);
                    wetNormalDry.setWetBalance(wetBalance);
                    wetNormalDry.setDryStatus(dryStatus);
                    wetNormalDry.setNormalStatus(normalStatus);
                    wetNormalDry.setWetStatus(wetStatus);

                    wetNormalDries.add(wetNormalDry);

                }

                DatabaseReference ref = database.getReference("Water Requirements");
                ref.child(String.valueOf(year)).setValue(wetNormalDries);
                //ref.child("water-requirements " + year).setValue(wetNormalDries);
//                ref.setValue(wetNormalDries);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}