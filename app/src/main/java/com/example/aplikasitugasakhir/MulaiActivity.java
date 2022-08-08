package com.example.aplikasitugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasitugasakhir.model.NetLaju;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MulaiActivity extends AppCompatActivity {

    //1. Buat Tabel
    //2. Buat Hasil peramalan
    //3. Buat destroy activity menggunakan tombol back
    //4. N/A

    static int deleteElement(double[] angka, int L, int R, int N) {
        int i, j = 0;
        for (i = 0; i < N; i++) {
            if (i <= L || i >= R) {
                angka[j] = angka[i];
                j++;
            }
        }
        return j;
    }

//    double angka2[] = {13.27, 1.77, 10.46, 11.6, -3.87, 14.63, 13.32, 19.2, 14.1, 14.97, 6.42, 23.56, 9.56, 11.63, 5.15, 0.45, 29.16, 5.51, 18.18, -0.23, 0.03, 13.36, 7.77, 13.39, 4.28, 12.68, 13.52, 17.04, 10.13, 20.28, -6.74, 8.44, 12.48};
//    double angka[] = new double[]{13.27, 1.77, 10.46, 11.6, -3.87, 14.63, 13.32, 19.2, 14.1, 14.97, 6.42, 23.56, 9.56, 11.63, 5.15, 0.45, 29.16, 5.51, 18.18, -0.23, 0.03, 13.36, 7.77, 13.39, 4.28, 12.68, 13.52, 17.04, 10.13, 20.28, -6.74, 8.44, 12.48};

    private EditText input;
    private Button proses;
    private TextView tvmad, tvmse, tvmape, tvakurasi, tvperamalan; /*tverror, tvad, tvse, tvape;*/ /*tvhperamalan*/
    ;
    private Spinner mSpinnerYear;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulai);

        input = findViewById(R.id.input);
        proses = findViewById(R.id.proses);
        /*tvhperamalan = findViewById(R.id.tvhperamalan);*/
        tvmad = findViewById(R.id.tvmad);
        /*tvad = findViewById(R.id.tvad);*/
        tvperamalan = findViewById(R.id.tvperamalan);
        /*tverror = findViewById(R.id.tverror);*/
        tvmse = findViewById(R.id.tvmse);
        tvmape = findViewById(R.id.tvmape);
        tvakurasi = findViewById(R.id.tvakurasi);
        mSpinnerYear = findViewById(R.id.spinner_year_moving_avg);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Data Net Laju");

        proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //int periode;
                float hasil1, hasil2, hasil3, hasil4, hperamalan, had, hse, hape;
                /*String hasilperamalan = tvhperamalan.getText().toString();*/
                int periode = Integer.valueOf(input.getText().toString().trim()) - 1;


                String yearSpinner = (String) mSpinnerYear.getSelectedItem();
                //String periodeSpinner = (String) mSpinnerPeriode.getSelectedItem();

                int year = Integer.valueOf(yearSpinner);
                int startYear = (year - 1) - periode;

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        List<String> tahuns = new ArrayList<>();
                        Map<String, List<Float>> nets = new HashMap<>();

                        for (int i = startYear; i <= year - 1; i++) {
                            tahuns.add(String.valueOf(i));
                        }


                        List<Float> netLajus = new ArrayList<>();
                        for (DataSnapshot item : snapshot.getChildren()) {


                            if (item.hasChild("1")) {
                                Object o = item.child("1").getValue();
                                String netLajuStr = String.valueOf(o);
                                Float netLaju = Float.valueOf(netLajuStr);
                                netLajus.add(netLaju);

                                continue;
                            }

                        }

                        //peramalan
                        int pos = year - 1988;
                        int posAkhir = (year - 1) - 1988;
                        int posAwal = (year - 1 - periode) - 1988;

                        float sumRamal = 0f;
                        for (int i = posAwal; i <= posAkhir; i++) {
                            sumRamal += netLajus.get(i);
                        }

                        sumRamal /= (periode + 1);

                        tvperamalan.setText(String.valueOf(sumRamal));

                        List<Float>avgNoZero = new ArrayList<>();

                        //error prediction
                        List<Float> movingAvg = new ArrayList<>();
                        for (int i = 0; i < netLajus.size(); i++) {

                            if ((i - periode) < 0) {
                                movingAvg.add(0f);
                                continue;
                            }

                            int awal = i - periode;
                            float sumX = 0f;

                            for (int j = awal; j <= i; j++) {
                                sumX += netLajus.get(j);
                            }

                            sumX /= (periode + 1);
                            movingAvg.add(sumX);
                            avgNoZero.add(netLajus.get(i));
                        }

                        float yearmoving = movingAvg.get(pos-1);

                        //MAD
                        List<Float> mad = new ArrayList<>();
                        for(int i =0; i < (pos-1); i++){

                            if(movingAvg.get(i) == 0f){
                                continue;
                            }

                            float netLaju = netLajus.get(i+1);
                            float avg = movingAvg.get(i);

                            mad.add(Math.abs(netLaju-avg));
                        }

                        float sumMad = 0f;
                        for(Float item : mad) {
                            sumMad += item;
                        }

                        sumMad /= mad.size();

                        tvmad.setText(String.valueOf(sumMad));

                        //MSE
                        float mse = 0f;
                        for(Float item : mad) {
                            mse += (item * item);
                        }

                        mse /= mad.size();
                        tvmse.setText(String.valueOf(mse));


                        //MAPE
                        float mape = 0f;
                        for(int i = 0; i < mad.size(); i++){

                            float m = mad.get(i);
                            float avg = avgNoZero.get(i+1);

                            float val = (m/avg);

                            mape += Math.abs(val);

                        }

                        mape /= mad.size();

                        tvmape.setText(String.valueOf(mape));


                        float akurasi = 100f - mape;
                        tvakurasi.setText(String.valueOf(akurasi));

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }
}