package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    private TextView tv_title;
    private MultiAutoCompleteTextView mtv_message;

    private String user, message, subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        tv_title = findViewById(R.id.title5);

        mtv_message = findViewById(R.id.tv_msg);

        user = getIntent().getStringExtra("user");
        subject = getIntent().getStringExtra("subject");
        message = getIntent().getStringExtra("message");

        tv_title.setText(subject);
        mtv_message.setText(message);
    }
}
