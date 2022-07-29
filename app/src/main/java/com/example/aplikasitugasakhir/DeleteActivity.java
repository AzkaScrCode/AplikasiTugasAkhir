package com.example.aplikasitugasakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteActivity extends AppCompatActivity {

    private Button mBtnPrediksi;
    private Spinner mSpinnerYear;

    private FirebaseDatabase database;
    private DatabaseReference netLajuRef;
    private DatabaseReference waterBalanceRef;
    private DatabaseReference waterRequirementRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        mSpinnerYear = findViewById(R.id.spinner_year_delete);
        mBtnPrediksi = findViewById(R.id.button_delete);

        database = FirebaseDatabase.getInstance();

        mBtnPrediksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String year = (String) mSpinnerYear.getSelectedItem();

                netLajuRef = database.getReference("Data Net Laju").child(year);
                waterBalanceRef = database.getReference("Water Balance").child(year);
                waterRequirementRef = database.getReference("Water Requirements").child(year);

                netLajuRef.removeValue();
                waterBalanceRef.removeValue();
                waterRequirementRef.removeValue();

                Toast.makeText(view.getContext(),"Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
            }
        });
    }
}