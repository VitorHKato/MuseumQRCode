package com.example.qrcodemuseum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qrcodemuseum.model.Item;

public class CreateItemActivity extends AppCompatActivity {

    //Componenets
    private EditText title;
    private EditText year;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        title = findViewById(R.id.CreateItemEditTextTitle);
        year = findViewById(R.id.CreateItemEditTextYear);
        description = findViewById(R.id.CreateItemEditTextDescription);
    }

    public void save(View view) {

        if (title.getText().length() < 1 || year.getText().length() < 1 || description.getText().length() < 1) {
            Toast.makeText(
                    getApplicationContext(),
                    "Please fill all the fields.",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            //TODO: Pegar ultimo id do banco e salvar +1, salvar no banco
            Item item = new Item(10, title.getText().toString(),
                    Integer.valueOf(year.getText().toString()), description.getText().toString());

            Toast.makeText(
                    getApplicationContext(),
                    "Saved item: " + item.getId() + " " + item.getTitle() + " " + item.getYear().toString() + " " + item.getDescription(),
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
        startActivity(intent);
        finish();
    }
}
