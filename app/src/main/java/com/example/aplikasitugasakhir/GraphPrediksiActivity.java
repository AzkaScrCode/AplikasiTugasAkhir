package com.example.aplikasitugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.aplikasitugasakhir.model.NetLaju;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphPrediksiActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner mSpinner;
    private Button mBtnShowPrediksi;
    private GraphView mGraphView;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private LineGraphSeries series;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_prediksi);

        database = FirebaseDatabase.getInstance();

        mSpinner = findViewById(R.id.spinner_graph_prediksi_year);
        mBtnShowPrediksi = findViewById(R.id.button_prediksi_result);
        mGraphView = findViewById(R.id.graph_prediksi);

        mBtnShowPrediksi.setOnClickListener(this);

        series = new LineGraphSeries();
        mGraphView.addSeries(series);

        mGraphView.getViewport().setScalable(true);
        mGraphView.getViewport().setScalableY(true);


        GridLabelRenderer gridLabelRenderer = mGraphView.getGridLabelRenderer();
        gridLabelRenderer.setHorizontalAxisTitle("Hari");
        gridLabelRenderer.setVerticalAxisTitle("Net Laju (mm/hari)");
        
    }

    @Override
    public void onClick(View view) {

        String year = (String) mSpinner.getSelectedItem();
        reference = database.getReference("Data Net Laju").child(year);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataPoint[] dp = new DataPoint[(int) snapshot.getChildrenCount()];
                int index = 0;

                for (DataSnapshot myDataSnapshot : snapshot.getChildren()){
                    String hari = myDataSnapshot.getKey();

                    Object o = myDataSnapshot.getValue();
                    String netLajuStr = String.valueOf(o);
                    Float laju = Float.valueOf(netLajuStr);

                    NetLaju netLaju = new NetLaju(Integer.valueOf(hari), laju);

                    dp[index] = new DataPoint(netLaju.getHari(), netLaju.getNetLaju());
                    index++;
                }
                series.resetData(dp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}