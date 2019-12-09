package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import Database.DBHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_username, et_password;
    private CheckBox cb_teacher, cb_student;
    private Button btn_register;

    private DBHelper dbHelper;

    private String username, password;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_username = findViewById(R.id.et_uname);
        et_password = findViewById(R.id.et_pwd);

        btn_register = findViewById(R.id.btn_reg);

        cb_student = findViewById(R.id.cb_student);
        cb_teacher = findViewById(R.id.cb_teacher);

        dbHelper = new DBHelper(this);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_username.getText().toString().trim()))
                    Toast.makeText(RegisterActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(et_password.getText().toString().trim()))
                    Toast.makeText(RegisterActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                else if (!cb_student.isChecked() && !cb_teacher.isChecked())
                    Toast.makeText(RegisterActivity.this, "Please enter a user type", Toast.LENGTH_SHORT).show();
                else if (cb_student.isChecked() && cb_teacher.isChecked())
                    Toast.makeText(RegisterActivity.this, "Please enter only one user type", Toast.LENGTH_SHORT).show();
                else {
                    username = et_username.getText().toString().trim();
                    password = et_password.getText().toString().trim();

                    if (cb_teacher.isChecked())
                        type = "Teacher";

                    if (cb_student.isChecked())
                        type = "Student";
                }

                long id = dbHelper.insert(username, password, type);

                if (id == -1)
                    Toast.makeText(RegisterActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                else {
                    clearData();
                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void clearData() {
        et_username.setText("");
        et_password.setText("");

        cb_teacher.setChecked(false);
        cb_student.setChecked(false);
    }
}
