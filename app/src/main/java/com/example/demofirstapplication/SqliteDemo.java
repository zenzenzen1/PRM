package com.example.demofirstapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demofirstapplication.dao.StudentDao;
import com.example.demofirstapplication.dao.StudentRoomDatabase;
import com.example.demofirstapplication.entity.Student;
import com.example.demofirstapplication.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

public class SqliteDemo extends AppCompatActivity {
    EditText nameEditText, ageEditText;
    Button insertButton, loadButton, updateButton, deleteButton;
    SQLiteDatabase database;
    ListView listView;
    ArrayAdapter<String> adapters;
    List<String> data;
//    StudentRepository studentRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sqlite_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        studentRepository = new StudentRepository(this);
        
        nameEditText = findViewById(R.id.editTextName);
        ageEditText = findViewById(R.id.editTextAge);
        insertButton = findViewById(R.id.InsertButton);
        loadButton = findViewById(R.id.LoadButton);
        updateButton = findViewById(R.id.UpdateButton);
        deleteButton = findViewById(R.id.DeleteButton);
        
        listView = findViewById(R.id.listView);
        data = new ArrayList<>();
        adapters = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapters);
        
        
//        database = openOrCreateDatabase("student.sqlite", MODE_PRIVATE, null);
//        try {
//            database.execSQL("CREATE TABLE student(name TEXT, age INTEGER)");
//        } catch (Exception e) {
//            Log.d("SQLiteDemo", "Table already exists");
//        }
        
        insertButton.setOnClickListener((view) -> {
            String name = nameEditText.getText().toString();
            int age = Integer.parseInt(ageEditText.getText().toString());

//            ContentValues values = new ContentValues();
//            values.put("name", name);
//            values.put("age", age);
//            if(database.insert("student", null, values) != -1){
//                Toast.makeText(this, "Insert successfully", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Insert failed", Toast.LENGTH_SHORT).show();
//            }
//            studentRepository.addStudent(new Student(name, age));
            clearText();
        });
        
        deleteButton.setOnClickListener((view) -> {
            String name = nameEditText.getText().toString();
            int n;
//            if((n = database.delete("student", "name = ?", new String[]{name})) != 0){
//                Toast.makeText(this, "Delete successfully " + n + " row(s)", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
//            }
            clearText();
        });
        
        updateButton.setOnClickListener((view) -> {
            String name = nameEditText.getText().toString();
            int age = Integer.parseInt(ageEditText.getText().toString());
//            ContentValues values = new ContentValues();
//            values.put("age", age);
//            int n;
//            if((n = database.update("student", values, "name = ?", new String[]{name})) != 0){
//                Toast.makeText(this, "Update successfully " + n + " row(s)", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
//            }
            clearText();
        });
        
        loadButton.setOnClickListener((view) -> loadData());
    }
    
    private void loadData() {
//        data.clear();
//        var cursor = database.query("student", null, null, null, null, null, null);
//        while (cursor.moveToNext()){
//            String name = cursor.getString(0);
//            int age = cursor.getInt(1);
//            data.add(name + " - " + age);
//        }
//        cursor.close();
        adapters.notifyDataSetChanged();
    }
    private void clearText() {
        nameEditText.setText("");
        ageEditText.setText("");
    }
}



























