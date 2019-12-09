package Database;

import android.provider.BaseColumns;

public final class UserManager {
    private UserManager() {
        //
    }

    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "User";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_PASSWORD = "Password";
        public static final String COLUMN_NAME_TYPE = "Type";
    }
}
