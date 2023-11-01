package com.example.qrcodemuseum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qrcodemuseum.model.DatabaseHelper;

public class CreateItemActivity extends AppCompatActivity {

    //Componenets
    private EditText title;
    private EditText year;
    private EditText description;

    private Long userId;
    private Integer itemId;

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

        //Get data from edit item
        itemId = intent.getIntExtra("itemId", 0);

        dbHelper = new DatabaseHelper(getApplicationContext());

        loadItem();
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

        if (itemId == 0) {
            db.insert("Item", null, contentValues);
        } else {
            String[] whereArgs = { String.valueOf(itemId) };
            db.update("Item", contentValues, "id = ?", whereArgs);
        }

        db.close();
        dbHelper.close();
    }

    public void loadItem() {
        if (itemId != 0) {
            db = dbHelper.getReadableDatabase();

            String[] projection = {"id", "title", "year", "description"};
            Cursor cursor = db.query("Item", projection, null, null, null, null, null);

            while (cursor.moveToNext()) {
                String titleItem = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String yearItem = cursor.getString(cursor.getColumnIndexOrThrow("year"));
                String descriptionItem = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                Integer id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));

                if (id.equals(itemId)) {
                    title.setText(titleItem);
                    year.setText(yearItem);
                    description.setText(descriptionItem);
                }

            }
            cursor.close();
        }
    }
}
