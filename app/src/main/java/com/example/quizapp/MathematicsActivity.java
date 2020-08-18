package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MathematicsActivity extends AppCompatActivity {
    private TextView mathe_que1;
    private CheckBox mathe_checkbox1;
    private CheckBox mathe_checkbox2;
    private CheckBox mathe_checkbox3;
    private Button mathe_btn_nex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathematics);

        mathe_que1 = findViewById(R.id.mathe_que1_textview);
        mathe_checkbox1 = findViewById(R.id.mathe_checkbox1);
        mathe_checkbox1 = findViewById(R.id.mathe_checkbox2);
        mathe_checkbox1 = findViewById(R.id.mathe_checkbox3);
        mathe_btn_nex   = findViewById(R.id.mathe_btn_nex);

        mathe_btn_nex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MathematicsActivity.this,"mathe_btn_nex", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
