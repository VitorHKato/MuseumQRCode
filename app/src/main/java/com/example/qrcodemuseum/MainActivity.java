package com.example.qrcodemuseum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qrcodemuseum.model.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    //Components
    private EditText editTextUser;
    private EditText editTextPassword;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUser = findViewById(R.id.MainEditTextUser);
        editTextPassword = findViewById(R.id.MainEditTextPassword);

        dbHelper = new DatabaseHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();
    }

    public void login(View view) {

        validateLogin();
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void validateLogin() {
        boolean valid = false;

        String[] projection = {"id", "username", "password"};
        Cursor cursor = db.query("User", projection, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            Long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));

            if (username.equals(editTextUser.getText().toString()) && password.equals(editTextPassword.getText().toString())) {
                Toast.makeText(
                        getApplicationContext(),
                        "Logged!",
                        Toast.LENGTH_LONG
                ).show();

                valid = true;

                db.close();
                dbHelper.close();

                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("userId", id);
                startActivity(intent);
                finish();
            }
        }
        cursor.close();

        if (!valid) {
            Toast.makeText(
                    getApplicationContext(),
                    "Incorrect credentials",
                    Toast.LENGTH_LONG
            ).show();
        }

    }
}
