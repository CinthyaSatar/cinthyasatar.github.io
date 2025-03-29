package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "news.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NEWS = "news";
    private static final String TABLE_FAVORITES = "favorites";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_LINK = "link";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNewsTableQuery = "CREATE TABLE " + TABLE_NEWS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_LINK + " TEXT);";

        String createFavoritesTableQuery = "CREATE TABLE " + TABLE_FAVORITES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_LINK + " TEXT);";

        db.execSQL(createNewsTableQuery);
        db.execSQL(createFavoritesTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    public long addNewsItem(NewsItem newsItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, newsItem.getTitle());
        values.put(COLUMN_DESCRIPTION, newsItem.getDescription());
        values.put(COLUMN_DATE, newsItem.getDate());
        values.put(COLUMN_LINK, newsItem.getLink());
        long id = db.insert(TABLE_NEWS, null, values);
        db.close();
        return id;
    }

    public void deleteNewsItem(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NEWS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<NewsItem> getAllNewsItems() {
        ArrayList<NewsItem> newsItems = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NEWS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                NewsItem newsItem = new NewsItem();
                newsItem.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                newsItem.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                newsItem.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                newsItem.setLink(cursor.getString(cursor.getColumnIndex(COLUMN_LINK)));
                newsItems.add(newsItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return newsItems;
    }

    public long addFavoriteItem(NewsItem newsItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, newsItem.getTitle());
        values.put(COLUMN_DESCRIPTION, newsItem.getDescription());
        values.put(COLUMN_DATE, newsItem.getDate());
        values.put(COLUMN_LINK, newsItem.getLink());
        long id = db.insert(TABLE_FAVORITES, null, values);
        db.close();
        return id;
    }

    public void deleteFavoriteItem(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<NewsItem> getAllFavoriteItems() {
        ArrayList<NewsItem> favoriteItems = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FAVORITES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                NewsItem favoriteItem = new NewsItem();
                favoriteItem.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                favoriteItem.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                favoriteItem.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                favoriteItem.setLink(cursor.getString(cursor.getColumnIndex(COLUMN_LINK)));
                favoriteItems.add(favoriteItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return favoriteItems;
    }
}
