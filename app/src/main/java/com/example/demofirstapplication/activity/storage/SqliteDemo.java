package com.example.demofirstapplication.activity.storage;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demofirstapplication.R;

import java.util.ArrayList;

public class SqliteDemo extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null)
            db.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sqlite_demo2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText classIdEdt = findViewById(R.id.editTextClassId);
        EditText classNameEdt = findViewById(R.id.editTextClassName);
        EditText noStudentsEdt = findViewById(R.id.editTextNoStudents);
        Button insertButton = findViewById(R.id.buttonInsert);
        Button updateButton = findViewById(R.id.buttonUpdate);
        Button deleteButton = findViewById(R.id.buttonDelete);
        Button selectButton = findViewById(R.id.buttonSelect);

        ListView listView = findViewById(R.id.listView);
        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        db = openOrCreateDatabase("student_management.db", MODE_PRIVATE, null);
        try {
            db.execSQL("CREATE TABLE student_class (class_id text PRIMARY KEY, class_name TEXT, no_students INTEGER)");
        } catch (Exception e) {
            Log.e("SQLiteDemo", "onCreate: ", e);
        }
        insertButton.setOnClickListener(v -> {
            String classId = classIdEdt.getText().toString();
            String className = classNameEdt.getText().toString();
            int noStudents = Integer.parseInt(noStudentsEdt.getText().toString());
            ContentValues values = new ContentValues();
            values.put("class_id", classId);
            values.put("class_name", className);
            values.put("no_students", noStudents);
            if (db.insert("student_class", null, values) != -1) {
                list.add(classId + " - " + className + " - " + noStudents);
                adapter.notifyDataSetChanged();
            } else {
                Log.e("SQLiteDemo", "onCreate: Insert failed");
            }
        });
        deleteButton.setOnClickListener(v -> {
            String classId = classIdEdt.getText().toString();
            if (db.delete("student_class", "class_id = ?", new String[]{classId}) > 0) {
                list.remove(classId);
                adapter.notifyDataSetChanged();
            } else {
                Log.e("SQLiteDemo", "onCreate: Delete failed");
            }
        });
        updateButton.setOnClickListener(v -> {
            String classId = classIdEdt.getText().toString();
            String className = classNameEdt.getText().toString();
            int noStudents = Integer.parseInt(noStudentsEdt.getText().toString());
            ContentValues values = new ContentValues();
            values.put("class_name", className);
            values.put("no_students", noStudents);
            if (db.update("student_class", values, "class_id = ?", new String[]{classId}) > 0) {
                list.clear();
                var cursor = db.query("student_class", null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    list.add(cursor.getString(0) + " - " + cursor.getString(1) + " - " + cursor.getInt(2));
                }
                cursor.close();
                adapter.notifyDataSetChanged();
            } else {
                Log.e("SQLiteDemo", "onCreate: Update failed");
            }
        });
        selectButton.setOnClickListener(v -> {
            list.clear();
            var cursor = db.query("student_class", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                list.add(cursor.getString(0) + " - " + cursor.getString(1) + " - " + cursor.getInt(2));
            }
            cursor.close();
            adapter.notifyDataSetChanged();
        });
    }


}

















