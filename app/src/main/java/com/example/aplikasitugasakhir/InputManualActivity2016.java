package com.example.aplikasitugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class InputManualActivity2016 extends AppCompatActivity {
    
    EditText nl16, h16;
    Button t16;
    
    FirebaseDatabase database;
    DatabaseReference reference;

    GridLabelRenderer gridLabelRenderer;
    GraphView graphView;
    LineGraphSeries series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_manual2016);
        
        h16 = findViewById(R.id.et_hari16);
        nl16 = findViewById(R.id.et_netlaju16);
        t16 = findViewById(R.id.bt_tambah16);
        
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Net Laju Tahun 2016");

        graphView = findViewById(R.id.graphview16);
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
        t16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = reference.push().getKey();

                int hari16 = Integer.parseInt(h16.getText().toString());
                float nlaju16 = Float.parseFloat(nl16.getText().toString());

                NetLaju netLaju = new NetLaju(hari16,nlaju16);

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
                DataPoint [] dp = new DataPoint[(int) snapshot.getChildrenCount()];
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