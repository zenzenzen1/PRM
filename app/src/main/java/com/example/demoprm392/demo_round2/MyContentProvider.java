package com.example.demoprm392.demo_round2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import androidx.annotation.Nullable;

import androidx.annotation.NonNull;

public class MyContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.example.demoprm392";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + MyDatabaseHelper.TABLE_NAME);

    private static final int ITEMS = 1;
    private static final int ITEM_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, MyDatabaseHelper.TABLE_NAME, ITEMS);
        uriMatcher.addURI(AUTHORITY, MyDatabaseHelper.TABLE_NAME + "/#", ITEM_ID);
    }

    private MyDatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new MyDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case ITEMS:
                cursor = db.query(MyDatabaseHelper.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case ITEM_ID:
                selection = MyDatabaseHelper.COLUMN_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = db.query(MyDatabaseHelper.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ITEMS:
                return "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + MyDatabaseHelper.TABLE_NAME;
            case ITEM_ID:
                return "vnd.android.cursor.item/vnd." + AUTHORITY + "." + MyDatabaseHelper.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id;
        if (uriMatcher.match(uri) == ITEMS) {
            id = db.insert(MyDatabaseHelper.TABLE_NAME, null, values);
            if (id > 0) {
                Uri insertedUri = ContentUris.withAppendedId(CONTENT_URI, id);
                return insertedUri;
            }
        } else {
            throw new IllegalArgumentException("Unsupported URI for insertion: " + uri);
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (uriMatcher.match(uri)) {
            case ITEMS:
                count = db.delete(MyDatabaseHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case ITEM_ID:
                selection = MyDatabaseHelper.COLUMN_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                count = db.delete(MyDatabaseHelper.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI for deletion: " + uri);
        }
        if (count > 0) {
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (uriMatcher.match(uri)) {
            case ITEMS:
                count = db.update(MyDatabaseHelper.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ITEM_ID:
                selection = MyDatabaseHelper.COLUMN_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                count = db.update(MyDatabaseHelper.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI for update: " + uri);
        }
        if (count > 0) {
        }
        return count;
    }
}
class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "student";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT NOT NULL);";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

