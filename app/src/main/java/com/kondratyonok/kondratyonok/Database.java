package com.kondratyonok.kondratyonok;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.yandex.metrica.YandexMetrica;

/**
 * Created by NKondratyonok on 01.02.18.
 */

public class Database {
    interface DatabaseData {

        String TABLE_NAME = "launched";

        interface Columns extends BaseColumns {
            String FIELD_NUMBER = "number";
            String FIELD_TITLE = "package_name";
        }

        String CREATE_TABLE_SCRIPT =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                        "(" +
                        Columns.FIELD_NUMBER + " NUMBER, " +
                        Columns.FIELD_TITLE + " TEXT" +
                        ")";

        String DROP_TABLE_SCRIPT =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    static class MyDbHelper extends SQLiteOpenHelper {
        static final int VERSION = 1;
        static final String DB_NAME = "applications.db";


        public MyDbHelper(Context context) {
            super(context, DB_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DatabaseData.CREATE_TABLE_SCRIPT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DatabaseData.DROP_TABLE_SCRIPT);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

    static private MyDbHelper mDbHelper;

    static public void init(Activity activity) {
        mDbHelper = new MyDbHelper(activity);
    }

    public static void insertOrUpdate(Entry entry) {
        try {
            Log.i("DATABASE", "insert or update");
            YandexMetrica.reportEvent("Database", "{\"action\":\"insert or update\"}");
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            Cursor cursor = db.query(
                    DatabaseData.TABLE_NAME,
                    new String[]{DatabaseData.Columns.FIELD_NUMBER},
                    DatabaseData.Columns.FIELD_TITLE + " = ?",
                    new String[]{entry.packageName},
                    null,
                    null,
                    null
            );
            if (cursor.getCount() == 0) {
                insert(entry);
            } else {
                update(entry);
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e("DATABASE", "failed to insert or update");
        }
    }

    public static void insert(Entry entry) {
        YandexMetrica.reportEvent("Database", "{\"action\":\"insert\"}");
        Log.i("DATABASE", "insert");
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseData.Columns.FIELD_NUMBER, entry.launched);
        contentValues.put(DatabaseData.Columns.FIELD_TITLE, entry.packageName);
        try {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.insert(DatabaseData.TABLE_NAME, null, contentValues);
        } catch (SQLiteException e) {
            Log.e("DATABASE", "failed to insert");
        }
    }

    public static void update(Entry entry) {
        YandexMetrica.reportEvent("Database", "{\"action\":\"update\"}");
        Log.i("DATABASE", "update");
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseData.Columns.FIELD_NUMBER, entry.launched);
        contentValues.put(DatabaseData.Columns.FIELD_TITLE, entry.packageName);
        try {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.update(
                    DatabaseData.TABLE_NAME,
                    contentValues,
                    DatabaseData.Columns.FIELD_TITLE + " = ?",
                    new String[]{entry.packageName});
        } catch (SQLiteException e) {
            Log.e("DATABASE", "failed to update");
        }
    }

    public static int get(String title) {
        try {
            YandexMetrica.reportEvent("Database", "{\"action\":\"get\"}");
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            Cursor cursor = db.query(
                    DatabaseData.TABLE_NAME,
                    new String[]{DatabaseData.Columns.FIELD_NUMBER},
                    DatabaseData.Columns.FIELD_TITLE + " = ?",
                    new String[]{title},
                    null,
                    null,
                    null
            );

            int result = 0;

            while (cursor.moveToNext()) {
                result += cursor.getInt(cursor.getColumnIndex(DatabaseData.Columns.FIELD_NUMBER));
            }
            cursor.close();
            return result;
        } catch (SQLiteException e) {
            return 0;
        }
    }

    public static void remove(Entry entry) {
        try {
            YandexMetrica.reportEvent("Database", "{\"action\":\"remove\"}");
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.delete(DatabaseData.TABLE_NAME, DatabaseData.Columns.FIELD_TITLE + " = ?", new String[]{entry.packageName});
        } catch (SQLiteException e) {
        }
    }

    public static void clear() {
        try {
            YandexMetrica.reportEvent("Database", "{\"action\":\"clear\"}");

            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.delete(DatabaseData.TABLE_NAME, null, null);
            db.close();
        } catch (SQLiteException e) {
        }
    }


}
