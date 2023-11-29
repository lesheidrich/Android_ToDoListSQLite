package com.heidrich.beadando_todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText input_task_name_update, input_task_importance_update, input_task_status_update;
    Button save_button_update, delete_button;
    String id, task_name, task_importance, task_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        input_task_name_update = findViewById(R.id.input_task_name_update);
        input_task_importance_update = findViewById(R.id.input_task_importance_update);
        input_task_status_update = findViewById(R.id.input_task_status_update);
        save_button_update = findViewById(R.id.save_button_update);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.show();
            actionBar.setTitle("Update Task");
        }

        save_button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(UpdateActivity.this);
                dbHelper.updateData(id,
                        input_task_name_update.getText().toString().trim(),
                        input_task_importance_update.getText().toString().trim(),
                        input_task_status_update.getText().toString().trim());

                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData(){
        if (getIntent().hasExtra("id") &&
                getIntent().hasExtra("task_name") &&
                getIntent().hasExtra("task_importance") &&
                getIntent().hasExtra("task_status")){

            id = getIntent().getStringExtra("id");
            task_name = getIntent().getStringExtra("task_name");
            task_importance = getIntent().getStringExtra("task_importance");
            task_status = getIntent().getStringExtra("task_status");

            input_task_name_update.setText(task_name);
            input_task_importance_update.setText(task_importance);
            input_task_status_update.setText(task_status);
        }
        else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ALERT!");
        builder.setMessage("Are you sure you want to delete " + task_name + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(UpdateActivity.this);
                dbHelper.deleteRow(id);

                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        builder.create().show();
    }


}