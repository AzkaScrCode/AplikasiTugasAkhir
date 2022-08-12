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
    private EditText et13,et14,et15,et16,et17,et18,et19,et20,et21,et22,et23,et24;
    private EditText et25,et26,et27,et28,et29,et30,et31,et32,et33,et34,et35,et36;

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
        et13 = findViewById(R.id.et_watreq_13);
        et14 = findViewById(R.id.et_watreq_14);
        et15 = findViewById(R.id.et_watreq_15);
        et16 = findViewById(R.id.et_watreq_16);
        et17 = findViewById(R.id.et_watreq_17);
        et18 = findViewById(R.id.et_watreq_18);
        et19 = findViewById(R.id.et_watreq_19);
        et20 = findViewById(R.id.et_watreq_20);
        et21 = findViewById(R.id.et_watreq_21);
        et22 = findViewById(R.id.et_watreq_22);
        et23 = findViewById(R.id.et_watreq_23);
        et24 = findViewById(R.id.et_watreq_24);
        et25 = findViewById(R.id.et_watreq_25);
        et26 = findViewById(R.id.et_watreq_26);
        et27 = findViewById(R.id.et_watreq_27);
        et28 = findViewById(R.id.et_watreq_28);
        et29 = findViewById(R.id.et_watreq_29);
        et30 = findViewById(R.id.et_watreq_30);
        et31 = findViewById(R.id.et_watreq_31);
        et32 = findViewById(R.id.et_watreq_32);
        et33 = findViewById(R.id.et_watreq_33);
        et34 = findViewById(R.id.et_watreq_34);
        et35 = findViewById(R.id.et_watreq_35);
        et36 = findViewById(R.id.et_watreq_36);


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
        map.put(13,Double.valueOf(et13.getText().toString()));
        map.put(14,Double.valueOf(et14.getText().toString()));
        map.put(15,Double.valueOf(et15.getText().toString()));
        map.put(16,Double.valueOf(et16.getText().toString()));
        map.put(17,Double.valueOf(et17.getText().toString()));
        map.put(18,Double.valueOf(et18.getText().toString()));
        map.put(19,Double.valueOf(et19.getText().toString()));
        map.put(20,Double.valueOf(et20.getText().toString()));
        map.put(21,Double.valueOf(et21.getText().toString()));
        map.put(22,Double.valueOf(et22.getText().toString()));
        map.put(23,Double.valueOf(et23.getText().toString()));
        map.put(24,Double.valueOf(et24.getText().toString()));
        map.put(25,Double.valueOf(et25.getText().toString()));
        map.put(26,Double.valueOf(et26.getText().toString()));
        map.put(27,Double.valueOf(et27.getText().toString()));
        map.put(28,Double.valueOf(et28.getText().toString()));
        map.put(29,Double.valueOf(et29.getText().toString()));
        map.put(30,Double.valueOf(et30.getText().toString()));
        map.put(31,Double.valueOf(et31.getText().toString()));
        map.put(30,Double.valueOf(et30.getText().toString()));
        map.put(31,Double.valueOf(et31.getText().toString()));
        map.put(32,Double.valueOf(et32.getText().toString()));
        map.put(33,Double.valueOf(et33.getText().toString()));
        map.put(34,Double.valueOf(et34.getText().toString()));
        map.put(35,Double.valueOf(et35.getText().toString()));
        map.put(36,Double.valueOf(et36.getText().toString()));


        DatabaseReference reference = database.getReference("Water Balance/"+yearSpinner);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    }else if(hari >= 121 && hari <= 130){
                        req = map.get(13);
                    }else if(hari >= 131 && hari <= 140){
                        req = map.get(14);
                    }else if(hari >= 141 && hari <= 150){
                        req = map.get(15);
                    }else if(hari >= 151 && hari <= 160){
                        req = map.get(16);
                    }else if(hari >= 161 && hari <= 170){
                        req = map.get(17);
                    }else if(hari >= 171 && hari <= 180){
                        req = map.get(18);
                    }else if(hari >= 181 && hari <= 190){
                        req = map.get(19);
                    }else if(hari >= 191 && hari <= 200){
                        req = map.get(20);
                    }else if(hari >= 201 && hari <= 210){
                        req = map.get(21);
                    }else if(hari >= 211 && hari <= 220){
                        req = map.get(22);
                    }else if(hari >= 221 && hari <= 230){
                        req = map.get(23);
                    }else if(hari >= 231 && hari <= 240){
                        req = map.get(24);
                    }else if(hari >= 241 && hari <= 250){
                        req = map.get(25);
                    }else if(hari >= 251 && hari <= 260){
                        req = map.get(26);
                    }else if(hari >= 261 && hari <= 270){
                        req = map.get(27);
                    }else if(hari >= 281 && hari <= 290){
                        req = map.get(29);
                    }else if(hari >= 291 && hari <= 300){
                        req = map.get(30);
                    }else if(hari >= 301 && hari <= 310){
                        req = map.get(31);
                    } else if(hari >= 311 && hari <= 320){
                        req = map.get(32);
                    }else if(hari >= 321 && hari <= 330){
                        req = map.get(33);
                    }else if(hari >= 331 && hari <= 340){
                        req = map.get(34);
                    }else if(hari >= 341 && hari <= 350){
                        req = map.get(35);
                    } else if(hari >= 351 && hari <= 365){
                        req = map.get(36);
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