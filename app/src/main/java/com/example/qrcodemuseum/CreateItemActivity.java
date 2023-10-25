package com.example.qrcodemuseum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qrcodemuseum.model.DatabaseHelper;
import com.example.qrcodemuseum.model.Item;

public class CreateItemActivity extends AppCompatActivity {

    //Componenets
    private EditText title;
    private EditText year;
    private EditText description;

    private Long userId;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        title = findViewById(R.id.CreateItemEditTextTitle);
        year = findViewById(R.id.CreateItemEditTextYear);
        description = findViewById(R.id.CreateItemEditTextDescription);

        //Get data from the previous activity
        Intent intent = getIntent();
        userId = intent.getLongExtra("userId", 1);

        dbHelper = new DatabaseHelper(getApplicationContext());

    }

    public void save(View view) {

        if (title.getText().length() < 1 || year.getText().length() < 1 || description.getText().length() < 1) {
            Toast.makeText(
                    getApplicationContext(),
                    "Please fill all the fields.",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            createItemDatabase();

            Toast.makeText(
                    getApplicationContext(),
                    "Item created!",
                    Toast.LENGTH_LONG
            ).show();

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }

    public void createItemDatabase() {
        db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title.getText().toString());
        contentValues.put("year", year.getText().toString());
        contentValues.put("description", description.getText().toString());
        contentValues.put("user_id", userId.toString());

        db.insert("Item", null, contentValues);

        db.close();
        dbHelper.close();
    }
}
