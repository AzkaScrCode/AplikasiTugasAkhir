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

public class InputManualActivity2019 extends AppCompatActivity {

    EditText h19, nl19;
    Button t19;

    GridLabelRenderer gridLabelRenderer;
    GraphView graphView;
    LineGraphSeries series;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_manual2019);

        h19 = findViewById(R.id.et_hari19);
        nl19 = findViewById(R.id.et_netlaju19);
        t19 = findViewById(R.id.bt_tambah19);
        
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Net Laju Tahun 2019");

        graphView = findViewById(R.id.graphview19);
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
        t19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = reference.push().getKey();

                int hari19 = Integer.parseInt(h19.getText().toString());
                float nlaju19 = Float.parseFloat(nl19.getText().toString());

                NetLaju netLaju = new NetLaju(hari19,nlaju19);
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

                for (DataSnapshot myDataSnapshot : snapshot.getChildren()) {
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