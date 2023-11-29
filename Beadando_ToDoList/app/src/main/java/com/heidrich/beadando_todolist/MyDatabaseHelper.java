package com.heidrich.beadando_todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "ToDoList_db.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "ToDoList_table";
    private static final String TASK_ID = "id";
    private static final String TASK_NAME = "task_name";
    private static final String TASK_IMPORTANCE = "task_importance";
    private static final String TASK_STATUS = "task_status";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TASK_NAME + " TEXT, " +
                TASK_IMPORTANCE + " TEXT, " +
                TASK_STATUS + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addTask(String t_name, String t_importance, int t_status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();

        c.put(TASK_NAME, t_name);
        c.put(TASK_IMPORTANCE, t_importance);
        c.put(TASK_STATUS, t_status);

        long result = db.insert(TABLE_NAME, null, c);
        if (result == -1){
            Toast.makeText(context, "Failed to add task.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Task added successfully.", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAll(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    void updateData(String row_id, String tn, String ti, String ts){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(TASK_NAME, tn);
        c.put(TASK_IMPORTANCE, ti);
        c.put(TASK_STATUS, ts);

        long result = db.update(TABLE_NAME, c, "id=?", new String[] {row_id});

        if (result == -1){
            Toast.makeText(context, "Update failed.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Successfully updated table.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id=?", new String[] {row_id});

        if (result == -1){
            Toast.makeText(context, "Content deletion failed.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Successfully deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllRows(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
