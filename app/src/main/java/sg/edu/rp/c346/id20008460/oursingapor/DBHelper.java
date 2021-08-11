package sg.edu.rp.c346.id20008460.oursingapor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "song.db";
    private static final int DATABASE_VERSION = 4;
    private static final String TABLE_ISLAND = "song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_DES = "Description";
    private static final String COLUMN_KM = "Km";
    private static final String COLUMN_STAR = "Star";

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTable = "CREATE TABLE " + TABLE_ISLAND + "( "
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT , "
                + COLUMN_DES + " TEXT , "
                + COLUMN_STAR + " FLOAT , "
                + COLUMN_KM + " INTEGER )";
        db.execSQL(createSongTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ISLAND); // every data in it will be gone
        onCreate(db);

    }

    public long insertIsland(String title , String singers , int year , float star) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME , title);
        values.put(COLUMN_DES , singers);
        values.put(COLUMN_KM, year);
        values.put(COLUMN_STAR, star);
        long result = db.insert (TABLE_ISLAND, null , values);
        db.close();
        return result;
    }

    public ArrayList<Island> getAllIsland() {
        ArrayList<Island> songs = new ArrayList<Island>();

        String selectQuery =
                "SELECT " + COLUMN_ID + ", "
                        + COLUMN_NAME + ", "
                        + COLUMN_DES + ", "
                        + COLUMN_KM + ", "
                        + COLUMN_STAR + " "
                        + "FROM " + TABLE_ISLAND;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                float star = cursor.getFloat(4);

                Island song= new Island(id, title, singers, year, star);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public int updateIsland(Island data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_DES, data.getDes());
        values.put(COLUMN_KM, data.getKm());
        values.put(COLUMN_STAR, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.get_id())};
        int result = db.update(TABLE_ISLAND, values, condition, args);

        db.close();
        return result;
    }

    public int deleteIsland(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_ISLAND, condition, args);
        db.close();
        return result;
    }

    public ArrayList<Island> getAllIsland(int keyword) {
        ArrayList<Island> songs = new ArrayList<Island>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_DES , COLUMN_KM, COLUMN_STAR};
        String condition = COLUMN_STAR + " Like ?";
        String[] args = { "%" +  keyword + "%"};
        Cursor cursor = db.query(TABLE_ISLAND, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                float star = cursor.getFloat(4);

                Island song= new Island(id, title, singers, year, star);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }


    public ArrayList<Island> filterYear(int keyword) {
        ArrayList<Island> songs = new ArrayList<Island>();

        SQLiteDatabase db = this.getReadableDatabase();


        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_DES, COLUMN_KM, COLUMN_STAR};
        String condition = COLUMN_KM + " Like ?";
        String[] args = { "%" +  keyword + "%"};


        Cursor cursor = db.query(TABLE_ISLAND, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                float star = cursor.getFloat(4);

                Island song= new Island(id, title, singers, year, star);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }


}
