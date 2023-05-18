package com.example.works;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    UserDAO userdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText userName = findViewById(R.id.user_name);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        Button register = findViewById(R.id.register);

        userdb = new UserDAO(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName.setError(null);
                password.setError(null);

                if (Validation.isUserNameValid(userName.getText().toString())) {
                    if (Validation.isPasswordValid(password.getText().toString())) {

                        String user = userName.getText().toString();
                        String pass = password.getText().toString();

                        User users = new User(user, pass);


                        Boolean checkuserpass = userdb.checkUserNamePassword(users);
                        Boolean checkUserNamePasswordAdmin = userdb.checkUserNamePasswordAdmin(users);
                        if (checkuserpass == true) {
                            if (checkUserNamePasswordAdmin == true) {
                                Toast.makeText(LoginActivity.this, "Prisijungėte kaip administratorius!", Toast.LENGTH_SHORT).show();
                                Intent goToAdminActivity = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(goToAdminActivity);
                            } else {
                                Toast.makeText(LoginActivity.this, "Prisijungimas sekmingas!", Toast.LENGTH_SHORT).show();
                                Intent goToMainActivity = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(goToMainActivity);
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Neteisingi prisijungimo duomenys!", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        password.setError(getResources().getString(R.string.login_p_error));
                        password.requestFocus();
                    }
                } else {
                    userName.setError(getResources().getString(R.string.login_u_error));
                    userName.requestFocus();
                }
            }

        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegisterActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(goToRegisterActivity);
            }
        });

    }

}