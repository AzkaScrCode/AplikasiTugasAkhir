package com.example.aplikasitugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;

public class WaterReqGraphActivity extends AppCompatActivity implements View.OnClickListener {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private Spinner mSpinnerYear;
    private Button mBtnTampilkanGraph;
    private GraphView mGraphView;

    private LineGraphSeries series;
    private LineGraphSeries series2;
    private LineGraphSeries series3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_req_graph);
        
        database = FirebaseDatabase.getInstance();

        mSpinnerYear = findViewById(R.id.spinner_tahun_water_graph);
        mBtnTampilkanGraph = findViewById(R.id.button_tampilkan_req_graph);
        mGraphView = findViewById(R.id.graph_wat_req);

        mBtnTampilkanGraph.setOnClickListener(this);

        series = new LineGraphSeries();
        series2 = new LineGraphSeries();
        series3 = new LineGraphSeries();
        
        mGraphView.addSeries(series);
        mGraphView.addSeries(series2);
        mGraphView.addSeries(series3);

        mGraphView.getViewport().setScalable(true);
        mGraphView.getViewport().setScalableY(true);


        GridLabelRenderer gridLabelRenderer = mGraphView.getGridLabelRenderer();
        gridLabelRenderer.setHorizontalAxisTitle("Hari");
        gridLabelRenderer.setVerticalAxisTitle("Net Laju (mm/hari)");

    }

    @Override
    public void onClick(View view) {

        String year = (String) mSpinnerYear.getSelectedItem();

        reference = database.getReference("water-requirements " + year);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!snapshot.hasChildren()){
                    Toast.makeText(view.getContext(), "data di tahun tsb tidak ditemukan",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                DataPoint[] dp = new DataPoint[(int) snapshot.getChildrenCount()];
                DataPoint[] dp2 = new DataPoint[(int) snapshot.getChildrenCount()];
                DataPoint[] dp3 = new DataPoint[(int) snapshot.getChildrenCount()];

                int index = 0;

                for(DataSnapshot item : snapshot.getChildren()){

                    Long hari = (Long) item.child("hari").getValue();
                    
                    Object objDry = item.child("dryBalance").getValue();
                    Object objNormal = item.child("normalBalance").getValue();
                    Object objWet = item.child("wetBalance").getValue();

                    String dryStr = String.valueOf(objDry);
                    String normalStr = String.valueOf(objNormal);
                    String wetStr = String.valueOf(objWet);

                    double dryBalance = Double.valueOf(dryStr);
                    double normalBalance = Double.valueOf(normalStr);
                    double wetBalance = Double.valueOf(wetStr);

                    dp[index] = new DataPoint(hari,dryBalance);
                    dp2[index] = new DataPoint(hari, normalBalance);
                    dp3[index] = new DataPoint(hari, wetBalance);

                    index++;
                }

                series.resetData(dp);
                series2.resetData(dp2);
                series3.resetData(dp3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}