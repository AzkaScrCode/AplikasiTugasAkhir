package com.example.aplikasitugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

    private Button mBtnHitunRequirement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_requirement);

        database = FirebaseDatabase.getInstance();

        mBtnHitunRequirement = findViewById(R.id.button_hitung_requirement);
        mBtnHitunRequirement.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Map<Integer, Double> map = new HashMap<>();
        int year = 2022;

        map.put(1,1.22);
        map.put(2,2.22);
        map.put(3,3.22);
        map.put(4,4.22);
        map.put(5,5.22);
        map.put(6,6.22);
        map.put(7,7.22);
        map.put(8,8.22);
        map.put(9,9.22);
        map.put(10,10.22);
        map.put(11,11.22);
        map.put(12,12.22);

        DatabaseReference reference = database.getReference("air wet normal " + year);

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
                    wetNormalDry.setWet(normalD);
                    wetNormalDry.setWaterReq(req);
                    wetNormalDry.setDryBalance(dryBalance);
                    wetNormalDry.setNormalBalance(normalBalance);
                    wetNormalDry.setWetBalance(wetBalance);
                    wetNormalDry.setDryStatus(dryStatus);
                    wetNormalDry.setNormalStatus(normalStatus);
                    wetNormalDry.setWetStatus(wetStatus);

                    wetNormalDries.add(wetNormalDry);

                }

                DatabaseReference ref = database.getReference();
                ref.child("air wet normal " + year).setValue(wetNormalDries);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}