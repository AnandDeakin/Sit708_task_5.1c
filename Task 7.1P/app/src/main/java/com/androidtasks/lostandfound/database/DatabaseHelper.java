package com.androidtasks.lostandfound.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.androidtasks.lostandfound.model.Item;

import java.util.ArrayList;
import java.util.List;
/**
 * DatabaseHelper manages all database operations for the Lost and Found application.
 * It handles the creation of db and CRUD operations for items in the SQLite database.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version - increment this when schema changes
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "lost_and_found_db";

    // Table name for storing lost and found items
    private static final String TABLE_ITEMS = "items";

    // Column names for the items table
    private static final String COLUMN_ID = "id";                     // Primary key
    private static final String COLUMN_POST_TYPE = "post_type";       // Type of post (lost/found)
    private static final String COLUMN_NAME = "name";                 // Name of the person posting
    private static final String COLUMN_PHONE = "phone";              // Contact phone number
    private static final String COLUMN_DESCRIPTION = "description";   // Item description
    private static final String COLUMN_DATE = "date";                // Date of lost/found
    private static final String COLUMN_LOCATION = "location";        // Location where item was lost/found

    /**
     * Constructor for DatabaseHelper
     * @param context The application context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time.
     * Creates the items table with all necessary columns.
     * @param db The database instance
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_POST_TYPE + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_LOCATION + " TEXT"
                + ")";
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    /**
     * Called when the database needs to be upgraded.
     * Drops the existing table and recreates it.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        // Create tables again
        onCreate(db);
    }

    /**
     * Adds a new item to the database
     * @param item The Item object to be inserted
     * @return The row ID of the newly inserted item, or -1 if an error occurred
     */
    public long addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Prepare values for insertion
        values.put(COLUMN_POST_TYPE, item.getPostType()); // Lost or found
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_PHONE, item.getPhone());
        values.put(COLUMN_DESCRIPTION, item.getDescription());
        values.put(COLUMN_DATE, item.getDate());
        values.put(COLUMN_LOCATION, item.getLocation());

        // Insert row and get the ID
        long id = db.insert(TABLE_ITEMS, null, values);
        db.close();
        return id;
    }

    /**
     * Retrieves all items from the database
     * @return List of all Item objects in the database
     */
    @SuppressLint("Range")
    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Iterate through all rows and create Item objects
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                item.setPostType(cursor.getString(cursor.getColumnIndex(COLUMN_POST_TYPE)));
                item.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                item.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
                item.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                item.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                item.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));

                itemList.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return itemList;
    }

    /**
     * Retrieves a single item from the database by ID
     * @param id The ID of the item to retrieve
     * @return The Item object if found, null if not founds
     */
    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query for single item by ID
        Cursor cursor = db.query(TABLE_ITEMS, null, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // Create and populate Item object from cursor
        @SuppressLint("Range") Item item = new Item(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_POST_TYPE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));

        cursor.close();
        db.close();
        return item;
    }

    /**
     * Deletes an item from the database
     * @param id The ID of the item to delete
     */
    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, COLUMN_ID + "=?", new String[] { String.valueOf(id) });
        db.close();
    }
}
