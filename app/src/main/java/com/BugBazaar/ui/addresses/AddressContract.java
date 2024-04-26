package com.BugBazaar.ui.addresses;

import android.net.Uri;
import android.provider.BaseColumns;

public class AddressContract {

    public static final class AddressEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.bugbazaar.provider.addresses/addresses");
        //public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ADDRESSES).build();
        public static final String TABLE_NAME = "addresses";
        public static final String COLUMN_NICKNAME = "nickname";
        public static final String COLUMN_ADDRESS = "address";
    }
}
