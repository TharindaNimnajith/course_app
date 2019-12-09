package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CourseApp.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ENTRIES_USER =
                "CREATE TABLE " + UserManager.User.TABLE_NAME + " (" +
                        UserManager.User._ID + " INTEGER PRIMARY KEY," +
                        UserManager.User.COLUMN_NAME_NAME + " TEXT," +
                        UserManager.User.COLUMN_NAME_PASSWORD + " TEXT," +
                        UserManager.User.COLUMN_NAME_TYPE + " TEXT)";

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_USER);

        String SQL_CREATE_ENTRIES_MESSAGE =
                "CREATE TABLE " + MessageManager.Message.TABLE_NAME + " (" +
                        MessageManager.Message._ID + " INTEGER PRIMARY KEY," +
                        MessageManager.Message.COLUMN_NAME_USER + " TEXT," +
                        MessageManager.Message.COLUMN_NAME_SUBJECT + " TEXT," +
                        MessageManager.Message.COLUMN_NAME_MESSAGE + " TEXT)";

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //
    }

    public long insert(String username, String password, String type) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UserManager.User.COLUMN_NAME_NAME, username);
        values.put(UserManager.User.COLUMN_NAME_PASSWORD, password);
        values.put(UserManager.User.COLUMN_NAME_TYPE, type);

        long newRowId = db.insert(UserManager.User.TABLE_NAME, null, values);

        return newRowId;
    }

    public String login(String uname, String pwd) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                UserManager.User.COLUMN_NAME_NAME,
                UserManager.User.COLUMN_NAME_PASSWORD,
                UserManager.User.COLUMN_NAME_TYPE
        };

        Cursor cursor = db.query(
                UserManager.User.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<String> usernames = new ArrayList();
        ArrayList<String> passwords = new ArrayList();
        ArrayList<String> types = new ArrayList();

        if (cursor.moveToFirst()) {
            usernames.add(cursor.getString(1));
            passwords.add(cursor.getString(2));
            types.add(cursor.getString(3));

            while (cursor.moveToNext()) {
                usernames.add(cursor.getString(1));
                passwords.add(cursor.getString(2));
                types.add(cursor.getString(3));
            }
        }

        cursor.close();

        for (int i = 0; i < usernames.size(); i++) {
            if (usernames.get(i).equals(uname) && passwords.get(i).equals(pwd)) {
                return types.get(i);
            }
        }

        return "error";
    }

    public long save(String username, String subject, String message) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(MessageManager.Message.COLUMN_NAME_USER, username);
        values.put(MessageManager.Message.COLUMN_NAME_SUBJECT, subject);
        values.put(MessageManager.Message.COLUMN_NAME_MESSAGE, message);

        long newRowId = db.insert(MessageManager.Message.TABLE_NAME, null, values);

        return newRowId;
    }

    public Cursor getLastMessage() {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                MessageManager.Message.COLUMN_NAME_USER,
                MessageManager.Message.COLUMN_NAME_SUBJECT,
                MessageManager.Message.COLUMN_NAME_MESSAGE
        };

        String sortOrder =
                BaseColumns._ID;

        Cursor cursor = db.query(
                MessageManager.Message.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        return cursor;
    }
}
