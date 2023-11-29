package com.heidrich.beadando_todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText task_name_input, task_importance_input, task_status_input;
    Button save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Task");
        setContentView(R.layout.activity_add);

        task_name_input = findViewById(R.id.task_name_input);
        task_importance_input = findViewById(R.id.task_importance_input);
        task_status_input = findViewById(R.id.task_status_input);
        save_button = findViewById(R.id.save_button);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(AddActivity.this);
                dbHelper.addTask(task_name_input.getText().toString().trim(),
                        task_importance_input.getText().toString().trim(),
                        Integer.parseInt(task_status_input.getText().toString().trim())
                        );

                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        });

    }
}