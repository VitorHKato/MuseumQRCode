package com.example.qrcodemuseum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
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
