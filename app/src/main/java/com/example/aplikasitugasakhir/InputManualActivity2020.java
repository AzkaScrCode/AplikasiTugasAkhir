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

public class InputManualActivity2020 extends AppCompatActivity {

    EditText h20, nl20;
    Button t20;

    FirebaseDatabase database;
    DatabaseReference reference;

    GridLabelRenderer gridLabelRenderer;
    GraphView graphView;
    LineGraphSeries series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_manual2020);

        h20 = findViewById(R.id.et_hari20);
        nl20 = findViewById(R.id.et_netlaju20);
        t20 = findViewById(R.id.bt_tambah20);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Net Laju Tahun 2020");

        graphView = findViewById(R.id.graphview20);
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
        t20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = reference.push().getKey();

                int hari20 = Integer.parseInt(h20.getText().toString());
                float nlaju20 = Float.parseFloat(nl20.getText().toString());

                NetLaju netLaju = new NetLaju(hari20,nlaju20);
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