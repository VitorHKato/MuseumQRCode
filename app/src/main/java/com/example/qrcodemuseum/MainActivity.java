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

        //TODO: Consultar no banco se o user e a senha existem no banco
        if(true) {
            Toast.makeText(
                    getApplicationContext(),
                    editTextUser.getText().toString() + " " + editTextPassword.getText().toString(),
                    Toast.LENGTH_LONG
            ).show();

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Incorrect credentials",
                    Toast.LENGTH_LONG
            ).show();
        }

    }

    public void signUp(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
