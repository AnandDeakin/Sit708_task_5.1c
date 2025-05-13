package com.androidtasks.media.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "iTubeDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_PLAYLIST = "playlist";

    // User Table Columns
    private static final String COLUMN_USER_ID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    // Playlist Table Columns
    private static final String COLUMN_PLAYLIST_ID = "_id";
    public static final String COLUMN_VIDEO_ID = "video_id";
    public static final String COLUMN_VIDEO_URL = "video_url";
    private static final String COLUMN_USER_USERNAME = "username";

    // Create User Table Query
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS +
            "(" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERNAME + " TEXT UNIQUE, " +
            COLUMN_PASSWORD + " TEXT" +
            ")";

    // Create Playlist Table Query
    private static final String CREATE_TABLE_PLAYLIST = "CREATE TABLE " + TABLE_PLAYLIST +
            "(" +
            COLUMN_PLAYLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_USERNAME + " TEXT, " +
            COLUMN_VIDEO_ID + " TEXT, " +
            COLUMN_VIDEO_URL + " TEXT, " +
            "FOREIGN KEY(" + COLUMN_USER_USERNAME + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USERNAME + ")" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_PLAYLIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    // Add a new user to the database
    public long addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        // Insert row
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    // Check if user exists with given username and password
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(
                TABLE_USERS,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    // Check if user exists with given username
    public boolean checkUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                TABLE_USERS,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    // Add a video to user's playlist
    public long addVideoToPlaylist(String username, String videoId, String videoUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, username);
        values.put(COLUMN_VIDEO_ID, videoId);
        values.put(COLUMN_VIDEO_URL, videoUrl);

        // Insert row
        long id = db.insert(TABLE_PLAYLIST, null, values);
        db.close();
        return id;
    }

    // Get all videos in user's playlist
    public Cursor getPlaylistVideos(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_USERNAME + " = ?";
        String[] selectionArgs = {username};

        return db.query(
                TABLE_PLAYLIST,
                null,
                selection,
                selectionArgs,
                null,
                null,
                COLUMN_PLAYLIST_ID + " DESC"
        );
    }
}