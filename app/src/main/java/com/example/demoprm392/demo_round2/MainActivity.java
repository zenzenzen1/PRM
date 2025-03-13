package com.example.demoprm392.demo_round2;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.demoprm392.R;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.textView);
        var isPermissionGrantedToReadContacts = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
        
        if (!isPermissionGrantedToReadContacts) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
            return;
        }
        TextView textView2 = findViewById(R.id.textView2);
        readContacts();

        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.COLUMN_NAME, "Name 1");
//        values.put(MyDatabaseHelper.COLUMN_NAME, "Name 2");
//        values.put("Demo", "Name demo");
        

        // insert
//        Uri newUri = getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
//        if (newUri != null) {
//            Toast.makeText(this, "Inserted: " + newUri.toString(), Toast.LENGTH_SHORT).show();
//        }

        values = new ContentValues();
        values.put(MyDatabaseHelper.COLUMN_NAME, "Student Name updated");
        Uri updateUri = Uri.withAppendedPath(MyContentProvider.CONTENT_URI, "1");
        int rowsUpdated = getContentResolver().update(updateUri, values, null, null);
        if (rowsUpdated > 0) {
            Toast.makeText(this, "Updated successfully!", Toast.LENGTH_SHORT).show();
        }

        // Delete item with ID 1
        Uri deleteUri = Uri.withAppendedPath(MyContentProvider.CONTENT_URI, "3"); 
        int rowsDeleted = getContentResolver().delete(deleteUri, null, null);

        if (rowsDeleted > 0) {
            Toast.makeText(this, "Deleted successfully!", Toast.LENGTH_SHORT).show();
        }
        
        
        Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, 
                new String[]{MyDatabaseHelper.COLUMN_ID, MyDatabaseHelper.COLUMN_NAME}, null, null, null);
        if (cursor != null) {
            StringBuilder result = new StringBuilder();
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_ID));
                result.append("Id: ").append(id).append(", ");
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_NAME));
                result.append("Name: ").append(name).append("\n");
            }
            cursor.close();
            textView2.setText(result);
        }

        
        
    }
    
    private void readContacts() {
        StringBuilder result = new StringBuilder();
        
        var cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, 
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                var name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                var id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                result.append("Id: ").append(id).append(",name: ").append(name);
                var hasPhoneNumber = cursor.getInt(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (hasPhoneNumber > 0) {
                    var phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    if (phoneCursor != null && phoneCursor.moveToFirst()) {
                        do {
                            try {
                                var phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                result.append(", phone: ").append(phoneNumber);
                            }
                            catch (Exception ignored) {
                            }
                        } while (phoneCursor.moveToNext());
                        phoneCursor.close();
                    }
                }
                result.append("\n");
            } while (cursor.moveToNext());
            cursor.close();
        }
        textView.setText(result.toString());
    }
    
    
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            readContacts();
        }
    }
}