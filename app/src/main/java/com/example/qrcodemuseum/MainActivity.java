package com.example.qrcodemuseum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Components
    private EditText editTextUser;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUser = findViewById(R.id.MainEditTextUser);
        editTextPassword = findViewById(R.id.MainEditTextPassword);

    }

    public void login(View view) {

        Toast.makeText(
                getApplicationContext(),
                editTextUser.getText().toString() + " " + editTextPassword.getText().toString(),
                Toast.LENGTH_LONG
        ).show();

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void signUp(View view) {
        Toast.makeText(
                getApplicationContext(),
                "Sign up view.",
                Toast.LENGTH_LONG
        ).show();

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
