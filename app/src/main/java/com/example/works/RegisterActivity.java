package com.example.works;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    UserDAO userdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText userName = findViewById(R.id.register_username);
        EditText password = findViewById(R.id.register_password);
        EditText remail = findViewById(R.id.register_email);
        RadioButton adminRadioButton = findViewById(R.id.admin_radio_button);
        RadioButton userRadioButton = findViewById(R.id.user_radio_button);
        Button register = findViewById(R.id.register);

        userdb = new UserDAO(this);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName.setError(null);
                remail.setError(null);
                password.setError(null);

                if (Validation.isUserNameValid(userName.getText().toString())) {
                    if (Validation.isEmailValid(remail.getText().toString())) {
                        if (Validation.isPasswordValid(password.getText().toString())) {

                            String user = userName.getText().toString();
                            String pass = password.getText().toString();
                            String email = remail.getText().toString();
                            boolean isAdmin = adminRadioButton.isChecked();
                            boolean isUser = userRadioButton.isChecked();

                            User users = new User(user, pass, email, isAdmin, isUser);

                            Boolean checkuser = userdb.checkUserName(users);
                            if (checkuser == false) {
                                Boolean insert = userdb.insertData(users);
                                if (insert == true) {
                                    Toast.makeText(RegisterActivity.this, "Registracija sėkminga!", Toast.LENGTH_SHORT).show();
                                    Intent goToLoginActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(goToLoginActivity);
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Registracija nepavyko!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Toks vartotojas yra registruotas! Prašome prisijungti!", Toast.LENGTH_SHORT).show();
                                Intent goToLoginActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(goToLoginActivity);
                            }


                        } else {
                            password.setError(getResources().getString(R.string.login_p_error));
                            password.requestFocus();
                        }

                    } else {
                        remail.setError(getResources().getString(R.string.register_email_error));
                        remail.requestFocus();
                    }
                } else {
                    userName.setError(getResources().getString(R.string.login_u_error));
                    userName.requestFocus();
                }


            }
        });

    }
}