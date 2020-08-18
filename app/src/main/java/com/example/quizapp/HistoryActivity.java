package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryActivity extends AppCompatActivity {

    private TextView hist_que1;
    private CheckBox hist_checkbox1;
    private CheckBox hist_checkbox2;
    private CheckBox hist_checkbox3;
    private Button   hist_btn_nex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        hist_que1 = findViewById(R.id.hist_que1_textview);
        hist_checkbox1 = findViewById(R.id.hist_checkbox1);
        hist_checkbox1 = findViewById(R.id.hist_checkbox2);
        hist_checkbox1 = findViewById(R.id.hist_checkbox3);
        hist_btn_nex   = findViewById(R.id.hist_btn_nex);

        hist_btn_nex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HistoryActivity.this,"hist_btn_nex", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
