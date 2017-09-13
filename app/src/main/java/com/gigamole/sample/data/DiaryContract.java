package com.gigamole.sample.data;

import android.provider.BaseColumns;

/**
 * Created by youssef on 06/07/17.
 */

public class DiaryContract {

    public static final class DiaryEntry implements BaseColumns {

        public static final String TABLE_NAME  ="Entries";

        public static final String COLUMN_ENTRY_TITLE = "title";

        public static final String COLUMN_ENTRY_DATE  = " date";

        public static final String COLUMN_ENTRY_DESCRIPTION = "description";

        public static final String COLUMN_ENTRY_IMAGE = "image";



    }
}
