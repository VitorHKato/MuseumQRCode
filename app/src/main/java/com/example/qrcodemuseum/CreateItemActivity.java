package com.example.qrcodemuseum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CreateItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);
    }

    public void save(View view) {
        Toast.makeText(
                getApplicationContext(),
                "Saved",
                Toast.LENGTH_LONG
        ).show();
    }
}
