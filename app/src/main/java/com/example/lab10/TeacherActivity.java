package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Database.DBHelper;

public class TeacherActivity extends AppCompatActivity {

    private TextView tv_title;
    private EditText et_subject, et_message;
    private Button btn_send;

    private DBHelper dbHelper;

    private String subject, message;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        username = getIntent().getStringExtra("username");

        tv_title = findViewById(R.id.title3);

        et_subject = findViewById(R.id.et_subject);
        et_message = findViewById(R.id.et_msg);

        btn_send = findViewById(R.id.btn_send);

        dbHelper = new DBHelper(this);

        tv_title.setText("Welcome " + username);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_subject.getText().toString().trim()))
                    Toast.makeText(TeacherActivity.this, "Please enter a subject", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(et_message.getText().toString().trim()))
                    Toast.makeText(TeacherActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                else {
                    subject = et_subject.getText().toString().trim();
                    message = et_message.getText().toString().trim();

                    long id = dbHelper.save(username, subject, message);

                    if (id == -1)
                        Toast.makeText(TeacherActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    else {
                        clearData();
                        Toast.makeText(TeacherActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void clearData() {
        et_subject.setText("");
        et_message.setText("");
    }
}
