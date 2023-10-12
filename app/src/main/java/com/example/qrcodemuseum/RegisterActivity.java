package com.example.qrcodemuseum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.qrcodemuseum.model.DatabaseHelper;
import com.example.qrcodemuseum.model.User;

public class RegisterActivity extends AppCompatActivity {

    //Components
    private EditText userName;
    private EditText email;
    private EditText phone;
    private EditText password;
    private EditText confirmPassword;
    private RadioGroup userType;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.RegisterEditTextUser);
        email = findViewById(R.id.RegisterEditTextEmail);
        phone = findViewById(R.id.RegisterEditTextPhone);
        password = findViewById(R.id.RegisterEditTextPassword);
        confirmPassword = findViewById(R.id.RegisterEditTextConfirmPassword);
        userType = findViewById(R.id.RegisterRadioGroup);

        dbHelper = new DatabaseHelper(getApplicationContext());
    }

    public void register(View view) {

        if (userName.getText().length() < 1 || email.getText().length() < 1 || phone.getText().length() < 1 ||
                password.getText().length() < 1 || confirmPassword.getText().length() < 1 || userType.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(
                    getApplicationContext(),
                    "Please fill all the fields.",
                    Toast.LENGTH_LONG
            ).show();
        }
        else if (password.getText().toString().length() < 6 || confirmPassword.getText().toString().length() < 6)
        {
            Toast.makeText(
                    getApplicationContext(),
                    "Digite uma senha de pelo menos 6 dígitos!",
                    Toast.LENGTH_LONG
            ).show();
        }
        else if (!password.getText().toString().equals(confirmPassword.getText().toString()))
        {
            Toast.makeText(
                    getApplicationContext(),
                    "As senhas não correspondem!",
                    Toast.LENGTH_LONG
            ).show();
        }
        else {
            //Check radioButton
            RadioButton selectedRadioButton = findViewById(userType.getCheckedRadioButtonId());

            Integer userTypeNumber = (selectedRadioButton.getText().toString().equals("Student")) ? 2 : 1;

            long newRowId = createAccountDatabase(userTypeNumber);

            Toast.makeText(
                    getApplicationContext(),
                    "Registered: " + userName.getText().toString(),
                    Toast.LENGTH_LONG
            ).show();

            db.close();
            dbHelper.close();

            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("userId", newRowId);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private long createAccountDatabase(Integer userTypeNumber) {
        db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("username", userName.getText().toString());
        contentValues.put("email", email.getText().toString());
        contentValues.put("phone", phone.getText().toString());
        contentValues.put("password", password.getText().toString());
        contentValues.put("userType", userTypeNumber);

        return db.insert("User", null, contentValues);
    }
}
