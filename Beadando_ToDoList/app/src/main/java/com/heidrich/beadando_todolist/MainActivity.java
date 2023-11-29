package com.heidrich.beadando_todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button, help_button;

    MyDatabaseHelper dbHelper;
    ArrayList<String> task_id_list, task_name_list, task_importance_list, task_status_list;

    CustomAdapter customAdapter;
    ImageView empty_imageView;
    TextView no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("TO DO LIST");
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        help_button = findViewById(R.id.help_button);
        empty_imageView = findViewById(R.id.empty_imageView);
        no_data = findViewById(R.id.no_data);
//        add_button.setOnClickListener(v -> {
//            Intent intent  = new Intent(MainActivity.this, AddActivity.class);
//            startActivity(intent);
//        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        help_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelpUrl();
            }
        });

        dbHelper = new MyDatabaseHelper(MainActivity.this);
        task_id_list = new ArrayList<>();
        task_name_list = new ArrayList<>();
        task_importance_list = new ArrayList<>();
        task_status_list = new ArrayList<>();

        readAllAndSetVisibility();

        customAdapter = new CustomAdapter(MainActivity.this,
                this, task_id_list, task_name_list,
                task_importance_list, task_status_list);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }//end onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            recreate();

            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void readAllAndSetVisibility(){
        Cursor c = dbHelper.readAll();

        if (c.getCount() == 0){
            empty_imageView.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }
        else {
            while (c.moveToNext()){
                task_id_list.add(c.getString(0));
                task_name_list.add(c.getString(1));
                task_importance_list.add(c.getString(2));
                task_status_list.add(c.getString(3));

                empty_imageView.setVisibility(View.GONE);
                no_data.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ALERT!");
        builder.setMessage("Are you sure you want to delete all table data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(MainActivity.this);
                dbHelper.deleteAllRows();

                //should autoupdate this fucking table**************************************************
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
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

    private void openHelpUrl() {
        String url = "https://www.google.com/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
//        else {
//            Toast.makeText(this, "Can't open url help.", Toast.LENGTH_SHORT).show();
//        }
    }



}