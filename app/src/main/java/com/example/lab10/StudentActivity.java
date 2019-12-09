package com.example.lab10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Database.DBHelper;

public class StudentActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "CHANNEL ID";

    private TextView tv_title;
    private ListView listView;

    private String username;

    private DBHelper dbHelper;

    private ArrayList messages, subjects, users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        username = getIntent().getStringExtra("username");

        tv_title = findViewById(R.id.title4);

        listView = findViewById(R.id.list);

        tv_title.setText("Welcome " + username);

        dbHelper = new DBHelper(this);

        createNotificationChannel();

        users = new ArrayList();
        messages = new ArrayList();
        subjects = new ArrayList();

        Cursor cursor;

        cursor = dbHelper.getLastMessage();

        if (cursor.moveToFirst()) {
            users.add(cursor.getString(1));
            subjects.add(cursor.getString(2));
            messages.add(cursor.getString(3));

            while (cursor.moveToNext()) {
                users.add(cursor.getString(1));
                subjects.add(cursor.getString(2));
                messages.add(cursor.getString(3));
            }
        }

        cursor.close();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, subjects);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                long id = listView.getItemIdAtPosition(i);
                int x = (int) id;

                Intent intent = new Intent(StudentActivity.this, MessageActivity.class);

                intent.putExtra("user", (String) users.get(x));
                intent.putExtra("message", (String) messages.get(x));
                intent.putExtra("subject", (String) subjects.get(x));

                startActivity(intent);
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            notification();
        }
    }

    private void notification() {
        Cursor cursor;

        String user;
        String message;
        String subject;

        int notificationId = 1;

        Intent intent = new Intent(this, MessageActivity.class);

        cursor = dbHelper.getLastMessage();

        cursor.moveToLast();

        user = cursor.getString(1);
        subject = cursor.getString(2);
        message = cursor.getString(3);

        cursor.close();

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        intent.putExtra("user", user);
        intent.putExtra("message", message);
        intent.putExtra("subject", subject);

        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("New Message")
                .setContentText("You have a new Message")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, builder.build());
    }
}
