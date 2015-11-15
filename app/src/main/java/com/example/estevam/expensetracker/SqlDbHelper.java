package com.example.estevam.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Estevam on 10/5/2015.
 */
//Step 2
public class SqlDbHelper extends SQLiteOpenHelper {
    private static final String KEY_NAME = "name";
    private static final String KEY_NAME2 = "price";
    private static final String KEY_NAME3 = "date";
    private static final String DATABASE_NAME = "ExpenseTrackerDb";
    public static final String DATABASE_TABLE = "ITEM_TABLE";
    public static final String DATABASE_TABLE2 = "PRICE_TABLE";
    private static final int DATABASE_VERSION = 1;

    public static final String COLUMN1 = "slno";
    public static final String COLUMN2 = "name";

    public static final String COLUMN3 = "price";
    public static final String COLUMN4 = "date";

    String SCRIPT_CREATE_TABLE = "CREATE TABLE " + DATABASE_TABLE + "(" + COLUMN1 + " INTEGER PRIMARY KEY," + COLUMN2 + " TEXT" + ")";
    String SCRIPT_CREATE_DATABASE2 = "CREATE TABLE " + DATABASE_TABLE2 + "(" + COLUMN1 + " INTEGER PRIMARY KEY," + COLUMN2 + " TEXT," + COLUMN3 + " REAL," + COLUMN4 + " TEXT" + ")";
    public SqlDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SCRIPT_CREATE_TABLE);
        db.execSQL(SCRIPT_CREATE_DATABASE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<Item>();
// Select All Query
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setItem_No(Integer.parseInt(cursor.getString(0)));
                item.setItem_name(cursor.getString(1));
// Adding contact to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
// return contact list
        return itemList;
    }

    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getItem_name());
        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }

    public void addItemtoPricetable(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getItem_name());
        values.put(KEY_NAME2, item.getItem_price());
        values.put(KEY_NAME3, item.getItem_date());

        db.insert(DATABASE_TABLE2, null, values);
        db.close();
    }

    public List<Item> getDataForReport(String fromDate, String toDate) {
        List<Item> itemList2 = new ArrayList<Item>();
        String selectQuery1 = null;
        if ((fromDate.equals(toDate)) || (fromDate.compareTo(toDate) < 1))
        {
            selectQuery1 = "SELECT  " + COLUMN2 + "," + " SUM(" + COLUMN3 + ")"
                    + " FROM " + DATABASE_TABLE2 + " where " + COLUMN4
                    + " BETWEEN  " + "'" + fromDate + "'" + " AND " + "'"
                    + toDate + "'" + " GROUP BY " + COLUMN2;
        }
        if ((fromDate.compareTo(toDate) > 1))
        {
            selectQuery1 = "SELECT  " + COLUMN2 + "," + " SUM(" + COLUMN3 + ")"
                    + " FROM " + DATABASE_TABLE2 + " where " + COLUMN4
                    + " BETWEEN  " + "'" + toDate + "'" + " AND " + "'"
                    + fromDate + "'" + " GROUP BY " + COLUMN2;
        }
        SQLiteDatabase db2 = this.getWritableDatabase();
        Cursor cursor1 = db2.rawQuery(selectQuery1, null);

        if (cursor1.moveToFirst()) {
            do {
                Item item2 = new Item();
                item2.setItem_name(cursor1.getString(0));
                item2.setItem_price(Double.parseDouble(cursor1.getString(1)));
                itemList2.add(item2);
            } while (cursor1.moveToNext());
        }
        cursor1.close();
        db2.close();
        return itemList2;
    }

    public double getTotalForReport(String fromDate, String toDate) {
        double total;
        String selectQuery = "SELECT "+"SUM("+COLUMN3+") FROM "+DATABASE_TABLE2+" where "+COLUMN4+" BETWEEN  '"+fromDate+"' AND  '"+toDate+"'";
        SQLiteDatabase db2 = this.getWritableDatabase();
        Cursor c = db2.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            total = Double.valueOf(c.getString(0));
        }
        else{
            total = -1;
        }
        return total;
    }



}
