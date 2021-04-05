package com.hilde.schnitze;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "weih.db";
    public static final String TABLE_NAME = "food";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FOODNAME = "foodname";
    SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    public boolean checkOrCreateDefaultData(Context context, List<String> my_default_data) {
        boolean has_db_data = false;
        if (!hasDatabaseData(context)){
            this.onCreate(db);
            this.addDefaultData(my_default_data);
        } else {
            //this.deleteTable();
            Log.i("DATA", "Database already have data entries: "+ this.numberOfRows());
            has_db_data = true;
        }
        Log.i("DB", "Number of entries: "+numberOfRows());
        return has_db_data;
    }

    public static boolean doesDatabaseExist(Context context) {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }

    public boolean hasDatabaseData(Context context) {
        db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT COUNT(*) FROM "+TABLE_NAME, null );
        res.moveToFirst();

        if (res.getInt(0) > 0){
            Log.i("DB","DB has DB entries");
            return true;

        } else {
            Log.i("DB","DB has non DB entries");
            return false;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(
                "create table IF NOT EXISTS " + TABLE_NAME + " " +
                        "(id integer primary key, " + COLUMN_FOODNAME + ")"
        );
        Log.i("DB", "DB created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addDefaultData(List<String> my_default_data) {
        int i = 0;
        for ( String foodname : my_default_data) {
            this.insertFood(i,foodname);
            i++;
        }
    }

    public boolean insertFood (int id, String foodname) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("foodname", foodname);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public void addFood(String foodname) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("foodname", foodname);
        db.insert(TABLE_NAME, null, contentValues);
    }

    // find foodname and return true if exists already
    public boolean findFood(String foodname) {
        db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+TABLE_NAME+" where "+COLUMN_FOODNAME+" LIKE '%"+foodname+"'", null );
        boolean is_already_in_database = false;
        if (res.getCount() > 0) {
            is_already_in_database = true;
        }

        return is_already_in_database;
    }

    public void findFoodAndPrint(String foodname) {
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_FOODNAME+" LIKE '%" + foodname +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorC = db.rawQuery(query, null);
        while (cursorC.moveToNext()) {
            Log.i("DB",cursorC.getString(0) + " : " + cursorC.getString(1));
        }
    }

    public Cursor getData(int id) {
        db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where id="+id+"", null );

        return res;
    }

    public String getDataString(int id) {
        Log.i("foobar",""+id);
        db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where id="+id+"", null );
        res.moveToFirst();
        String name = res.getString(res.getColumnIndex(DBHelper.COLUMN_FOODNAME));
        return name;
    }

    public int numberOfRows(){
        db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    // return value is the amount of entry numbers are changed
    public int updateFoodname(Integer id, String foodname) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("foodname", foodname);

        return db.update("food", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
    }

    public String findFoodname(String foodname){
        String query = "SELECT * FROM food WHERE foodname LIKE '%" + foodname +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorC = db.rawQuery(query, null);
        return cursorC.getString(1);
    }

    // return value is the amount of entry numbers are deleted
    public int deleteFoodID(int id) {
        db = this.getWritableDatabase();
        return db.delete("food",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    // sec. rel
    public void deleteFoodname(String foodname){
        String query = "SELECT * FROM food WHERE foodname LIKE '%" + foodname +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorC = db.rawQuery(query, null);
        while (cursorC.moveToNext()) {
            Log.i("DELETE",cursorC.getString(0) + " : " + cursorC.getString(1));
            int toBeDeleted = cursorC.getInt(0);
            deleteFoodID(toBeDeleted);
        }
    }

    public void deleteTable() {
        db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS food");
        Log.i("DB", "DB deleted.");
    }

    public ArrayList<String> getAllData() {
        ArrayList<String> array_list = new ArrayList<String>();
        db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COLUMN_FOODNAME)));
            res.moveToNext();
        }
        Log.i("DATA", "Length of Array: "+array_list.size());
        return array_list;
    }

    public void closeDatabaseConnection() {
        db.close();
    }
}
