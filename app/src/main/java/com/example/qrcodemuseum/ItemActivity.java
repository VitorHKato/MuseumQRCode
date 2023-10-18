package com.example.qrcodemuseum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qrcodemuseum.model.DatabaseHelper;
import com.example.qrcodemuseum.model.Item;

public class ItemActivity extends AppCompatActivity {

    //Components
    private TextView title;
    private TextView year;
    private TextView description;

    private Item item;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        title = findViewById(R.id.ItemTextViewTitle);
        year = findViewById(R.id.ItemTextViewYear);
        description = findViewById(R.id.ItemTextViewDescription);

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");

        dbHelper = new DatabaseHelper(getApplicationContext());

        title.setText(item.getTitle());
        year.setText(item.getYear().toString());
        description.setText(item.getDescription());
    }

    public void delete(View view) {
        db = dbHelper.getWritableDatabase();

        String[] selectionArgs = {item.getId().toString()};

        db.delete("Item", "id = ?", selectionArgs);

        Toast.makeText(
                getApplicationContext(),
                "Item deleted.",
                Toast.LENGTH_LONG).show();

        db.close();
        dbHelper.close();

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
