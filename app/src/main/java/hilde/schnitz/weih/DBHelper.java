package hilde.schnitz.weih;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "weih.db";
    public static final String TABLE_NAME = "food";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FOODNAME = "foodname";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(
                "create table IF NOT EXISTS " + TABLE_NAME + " " +
                        "(id integer primary key, " + COLUMN_FOODNAME + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertFood (int id, String foodname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("foodname", foodname);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where id="+id+"", null );
        return res;
    }

    public String getDataString(int id) {
        Log.i("foobar",""+id);
        SQLiteDatabase db = this.getReadableDatabase();
        /*
        /*
        String[] columns = {"id", "foodname"};
        Cursor cur = db.query("food", columns,null,null,null,null,null,null);

        while(cur.moveToNext()){
            int index;
            index = cur.getColumnIndexOrThrow("id");
            int column_id = Integer.parseInt(cur.getString(index));

            index = cur.getColumnIndexOrThrow("foodname");
            String column_foodname = cur.getString(index);

            Log.i("foobar",""+column_id);
            Log.i("foobar",column_foodname);
        }
        */
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where id="+id+"", null );
        res.moveToFirst();
        String name = res.getString(res.getColumnIndex(DBHelper.COLUMN_FOODNAME));
        try {
            Log.i("foobar",name);
        } catch (Exception e){
            Log.e("foobar",e.getMessage());
        }
        return name;

        //return "blah";
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateFoodname (Integer id, String foodname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("foodname", foodname);
        //contentValues.put("phone", phone);
        //contentValues.put("email", email);
        //contentValues.put("street", street);
        //contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteFoodname (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllData() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COLUMN_FOODNAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
