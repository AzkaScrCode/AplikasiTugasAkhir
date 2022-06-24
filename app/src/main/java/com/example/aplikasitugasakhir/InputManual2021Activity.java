package com.example.aplikasitugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class InputManual2021Activity extends AppCompatActivity {

    EditText h21, nl21;
    Button t21;

    FirebaseDatabase database;
    DatabaseReference reference;

    GridLabelRenderer gridLabelRenderer;
    GraphView graphView;
    LineGraphSeries series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_manual2021);
        
        h21 = findViewById(R.id.et_hari21);
        nl21 = findViewById(R.id.et_netlaju21);
        t21 = findViewById(R.id.bt_tambah21);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Net Laju Tahun 2021");

        graphView = findViewById(R.id.graphview21);
        series = new LineGraphSeries();
        graphView.addSeries(series);

        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(true);

        gridLabelRenderer = graphView.getGridLabelRenderer();
        gridLabelRenderer.setHorizontalAxisTitle("Hari");
        gridLabelRenderer.setVerticalAxisTitle("Net Laju (mm/hari)");
        
        setListeners();
    }

    private void setListeners() {
        t21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = reference.push().getKey();

                int hari21 = Integer.parseInt(h21.getText().toString());
                float nlaju21 = Float.parseFloat(nl21.getText().toString());

                NetLaju netLaju = new NetLaju(hari21,nlaju21);

                reference.child(id).setValue(netLaju);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataPoint[] dp = new DataPoint[(int) snapshot.getChildrenCount()];
                int index = 0;

                for (DataSnapshot myDataSnapshot : snapshot.getChildren()){
                    NetLaju netLaju = myDataSnapshot.getValue(NetLaju.class);
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