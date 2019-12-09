package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Database.DBHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username, et_password;
    private Button btn_login, btn_register;

    private DBHelper dbHelper;

    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        dbHelper = new DBHelper(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_username.getText().toString().trim()))
                    Toast.makeText(LoginActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(et_password.getText().toString().trim()))
                    Toast.makeText(LoginActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                else {
                    username = et_password.getText().toString().trim();
                    password = et_password.getText().toString().trim();

                    String str = dbHelper.login(username, password);

                    if (str.equals("error")) {
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    } else if (str.equals("Student")) {
                        Intent intent = new Intent(LoginActivity.this, StudentActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    } else if (str.equals("Teacher")) {
                        Intent intent = new Intent(LoginActivity.this, TeacherActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void clearData() {
        et_username.setText("");
        et_password.setText("");
    }
}
