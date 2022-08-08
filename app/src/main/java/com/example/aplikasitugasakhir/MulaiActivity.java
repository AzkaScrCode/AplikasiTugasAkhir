package com.example.aplikasitugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
    private TextView tvmad, tvmse, tvmape, tvakurasi, tvperamalan; /*tverror, tvad, tvse, tvape;*/ /*tvhperamalan*/;
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

                int year = Integer.valueOf(yearSpinner) ;
                int startYear = (year - 1) - periode;

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        List<String> tahuns = new ArrayList<>();
                        Map<String, List<Float>> nets = new HashMap<>();

                        for (int i = startYear; i <= year-1; i++) {
                            tahuns.add(String.valueOf(i));
                        }

                        for (String tahun : tahuns) {
                            for (DataSnapshot item : snapshot.child(tahun).getChildren()) {

                                //Long hari = item.child("hari").getValue(Long.class);
                                String hari = item.getKey();
                                Object o = item.getValue();
                                String netLajuStr = String.valueOf(o);
                                Float netLaju = Float.valueOf(netLajuStr);

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

                        for (Map.Entry<String , List<Float>> entry : nets.entrySet()) {

                            List<Float> floats = entry.getValue();
                            float sum = 0f;

                            for (Float netLaju : floats) {
                                sum = sum + netLaju;
                            }

                            float avg = sum / floats.size();

                            NetLaju netLaju = new NetLaju(Integer.valueOf(entry.getKey().toString()), avg);
                            netLajus.add(netLaju);
                        }

                        double[] angka = new double[netLajus.size()];
                        double[] angka2 = new double[netLajus.size()];

                       for(int i = 0; i < netLajus.size();i++){
                           angka[i] = netLajus.get(i).getNetLaju();
                           angka[2] = netLajus.get(i).getNetLaju();
                       }


                        //Menghitung peramalan
                        float[] peramalan = new float[angka.length];
                        for (int i = 0; i + periode <= angka2.length; i++) {
                            float sum = 0;
                            for (int j = i; j < i + periode; j++) {
                                sum += angka2[j];
                            }
                            peramalan[i] = sum / periode;
                            tvperamalan.setText(String.valueOf(peramalan[i]));
                        }

                        //Array Baru
                        int L = -1, R = periode;
                        int n = angka.length;
                        int res_size = deleteElement(angka, L, R, n);
                        for (int i = 0; i < res_size; i++) {
                        }

                        //Error Predict
                        float[] errorpredict = new float[angka.length];
                        for (int i = 0; i < res_size; i++) {
                            errorpredict[i] = (float) (angka[i] - peramalan[i]);
                        }

                        //Absolute Deviation
                        float[] mad = new float[errorpredict.length];
                        for (int i = 0; i < errorpredict.length; i++) {
                            mad[i] = errorpredict[i];
                            if (errorpredict[i] > 0) {
                                mad[i] = errorpredict[i] * 1;

                            } else if (errorpredict[i] < 0) {
                                float mad2 = errorpredict[i] * -1;
                                mad[i] = mad2;
                            }
                        }

                        //Mean Absolute Deviation
                        float sum1 = 0;
                        for (int i = 0; i < mad.length; i++) {
                            sum1 += mad[i] / (mad.length - periode);
                        }
                        //hasil1 = sum1;
                        tvmad.setText(String.valueOf(sum1));

                        //Square Error
                        float[] mse = new float[mad.length];
                        for (int i = 0; i < mad.length; i++) {
                            mse[i] = (mad[i] * mad[i]);
                        }

                        //Mean Square Error
                        float sum2 = 0;
                        for (int i = 0; i < mse.length; i++) {
                            sum2 += mse[i] / (mse.length - periode);
                        }
                        //hasil2 = sum2;
                        tvmse.setText(String.valueOf(sum2));

                        //Absolute Percentage Error
                        float[] mape = new float[angka.length];
                        for (int i = 0; i < res_size; i++) {
                            mape[i] = (float) ((angka[i] - peramalan[i]) / angka[i]);
                            if (mape[i] > 0) {
                                mape[i] = mape[i] * 1;
                            } else if (mape[i] < 0) {
                                mape[i] = mape[i] * -1;
                            }
                        }

                        //Mean Absolute Percentage Error
                        float sum3 = 0;
                        for (int i = 0; i < mape.length; i++) {
                            sum3 += mape[i] / (mape.length - periode);
                        }
                        //hasil3 = sum3;
                        tvmape.setText(String.valueOf(sum3));

                        float akurasi = 100 - sum3;
                        //hasil4 = akurasi;
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