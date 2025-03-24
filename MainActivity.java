package com.example.ex7;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    EditText editTextName, editTextAge, editTextId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextId = findViewById(R.id.editTextId);
    }

    public void addUser(View view) {
        String name = editTextName.getText().toString();
        int age = Integer.parseInt(editTextAge.getText().toString());
        boolean result = dbHelper.insertUser(name, age);
        if (result) {
            Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error adding user", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewUsers(View view) {
        Cursor cursor = dbHelper.getAllUsers();
        if (cursor != null) {
            // Get the column indices first
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int ageIndex = cursor.getColumnIndex("age");

            // Check if any column index is -1 (indicating an issue)
            if (idIndex == -1 || nameIndex == -1 || ageIndex == -1) {
                // If any column is not found, print the column names to help with debugging
                String[] columnNames = cursor.getColumnNames();
                Log.e("Database", "Column names: " + Arrays.toString(columnNames));
            } else {
                // If all columns are valid, continue with the cursor traversal
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    int age = cursor.getInt(ageIndex);
                    Toast.makeText(this, "ID: " + id + " Name: " + name + " Age: " + age, Toast.LENGTH_SHORT).show();
                }
            }
            cursor.close();
        }
    }

    public void updateUser(View view) {
        int id = Integer.parseInt(editTextId.getText().toString());
        String name = editTextName.getText().toString();
        int age = Integer.parseInt(editTextAge.getText().toString());
        boolean result = dbHelper.updateUser(id, name, age);
        if (result) {
            Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error updating user", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteUser(View view) {
        int id = Integer.parseInt(editTextId.getText().toString());
        boolean result = dbHelper.deleteUser(id);
        if (result) {
            Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error deleting user", Toast.LENGTH_SHORT).show();
        }
    }
}
