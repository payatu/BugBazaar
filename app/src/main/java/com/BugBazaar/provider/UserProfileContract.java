package com.BugBazaar.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class UserProfileContract {

    public static final String CONTENT_AUTHORITY = "com.BugBazaar.UserProfile";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class UserProfileEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath("user_profiles").build();

        // Table name
        public static final String TABLE_NAME = "user_profiles";

        // Column names
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_MOBILE = "mobile";
        public static final String COLUMN_ADDRESS = "address";
    }
}
