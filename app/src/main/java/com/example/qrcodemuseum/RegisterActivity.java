package com.example.qrcodemuseum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.qrcodemuseum.model.User;

public class RegisterActivity extends AppCompatActivity {

    //Components
    private EditText userName;
    private EditText email;
    private EditText phone;
    private EditText password;
    private EditText confirmPassword;
    private RadioGroup userType;

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
            //Todo:Create new user on database, get the last id on database and +1 save

            //Check radioButton
            RadioButton selectedRadioButton = findViewById(userType.getCheckedRadioButtonId());

            //        else
            //        {
            //            armazenarDadosUsuario();
            //
            //            Intent intent = new Intent(getApplicationContext(), Cadastro3Activity.class);
            //
            //            intent.putExtra("usuario", usuario);
            //
            //            startActivity(intent);
            //            finish();
            //        }

            Integer userTypeNumber = (selectedRadioButton.getText().toString().equals("Student")) ? 2 : 1;

            User user = new User(2, userName.getText().toString(), email.getText().toString(),
                    phone.getText().toString(), password.getText().toString(), userTypeNumber);

            Toast.makeText(
                    getApplicationContext(),
                    "Registered: " + user.getUser() + " " + user.getEmail() + " " + user.getPhone() + " "
                            + user.getPassword() + " " + user.getId() + " " + user.getUserType(),
                    Toast.LENGTH_LONG
            ).show();

            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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
}
