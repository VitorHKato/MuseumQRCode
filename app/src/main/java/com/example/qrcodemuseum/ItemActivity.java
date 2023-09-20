package com.example.qrcodemuseum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qrcodemuseum.model.Item;

public class ItemActivity extends AppCompatActivity {

    //Components
    private TextView title;
    private TextView year;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        title = findViewById(R.id.ItemTextViewTitle);
        year = findViewById(R.id.ItemTextViewYear);
        description = findViewById(R.id.ItemTextViewDescription);

        Intent intent = getIntent();
        Item item = (Item) intent.getSerializableExtra("item");

        title.setText(item.getTitle());
        year.setText(item.getYear().toString());
        description.setText(item.getDescription());
    }

    public void delete(View view) {
        Toast.makeText(
                getApplicationContext(),
                "Delete",
                Toast.LENGTH_LONG
        ).show();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
