package com.example.sqlite_recyclerview.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import com.example.sqlite_recyclerview.Model.Data;
import com.example.sqlite_recyclerview.Model.Utils;

public class DataBase_Helper extends SQLiteOpenHelper {
    @RequiresApi(api = Build.VERSION_CODES.P)
    public DataBase_Helper(@Nullable Context context) {
        super(context,Utils.DB_NAME,null,Utils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
           String CREATE_TABLE ="CREATE TABLE " + Utils.TABLE_NAME +" ( "
                +Utils.COLoUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Utils.COLoUM_NAME + " TEXT,"
                +Utils.COLoUM_LNAME + " TEXT," +Utils.COLoUM_DESCRIPTION + " TEXT,"
                +Utils.COLoUM_AGE + " TEXT," +Utils.COLoUM_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP" +" )";
           sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_NAME );
        onCreate(sqLiteDatabase);
    }

    public long insertData(Data data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Utils.COLoUM_NAME, data.getName());
        cv.put(Utils.COLoUM_LNAME , data.getLname());
        cv.put(Utils.COLoUM_DESCRIPTION , data.getDescription());
        cv.put(Utils.COLoUM_AGE , data.getAge());

        long id = db.insert(Utils.TABLE_NAME, null, cv);
        db.close();
       return id ;
    }

    public int updateData(Data data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Utils.COLoUM_NAME , data.getName());
        cv.put(Utils.COLoUM_LNAME , data.getLname());
        cv.put(Utils.COLoUM_DESCRIPTION , data.getDescription());
        cv.put(Utils.COLoUM_AGE , data.getAge());

         return db.update(Utils.TABLE_NAME,cv,"id" + " =?",new String[]{String.valueOf(data.getId())});
    }
    public void deleteData(Data data){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Utils.TABLE_NAME,Utils.COLoUM_ID + " =?",new String[]{String.valueOf(data.getId())});
        db.close();
    }

    public Data getData(int id ) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Utils.TABLE_NAME, new String[]{Utils.COLoUM_ID, Utils.COLoUM_NAME,
                        Utils.COLoUM_LNAME, Utils.COLoUM_DESCRIPTION, Utils.COLoUM_AGE, Utils.COLoUM_TIMESTAMP},
                Utils.COLoUM_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
   //     Data data = new Data();
//        cursor.getInt(cursor.getColumnIndex(Utils.COLoUM_ID));
//        cursor.getString(cursor.getColumnIndex(Utils.COLoUM_NAME));
//        cursor.getString(cursor.getColumnIndex(Utils.COLoUM_LNAME));
//        cursor.getString(cursor.getColumnIndex(Utils.COLoUM_DESCRIPTION));
//        cursor.getString(cursor.getColumnIndex(Utils.COLoUM_AGE));
//        cursor.getString(cursor.getColumnIndex(Utils.COLoUM_TIMESTAMP));

        Data data = new Data(
                Integer.parseInt(cursor.getString(0)),
                                 cursor.getString(1),
                                 cursor.getString(2),
                                 cursor.getString(3),
                                 cursor.getString(4));
        return data;
    }

    public List<Data> getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Data> allData = new ArrayList<>();
        String query = "SELECT * FROM "+ Utils.TABLE_NAME ;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
        do {
            Data data = new Data();
            data.setId(Integer.parseInt(cursor.getString(0)));
            data.setName(cursor.getString(1));
            data.setLname(cursor.getString(2));
            data.setDescription(cursor.getString(3));
            data.setAge(cursor.getString(4));
            data.setTimeStamp(cursor.getString(5));
            allData.add(data);
        }while (cursor.moveToNext());
            return allData;

//            data.setId(cursor.getInt(cursor.getColumnIndex(Utils.COLoUM_ID)));
//            data.setName(cursor.getString(cursor.getColumnIndex(Utils.COLoUM_NAME)));
//            data.setLname( cursor.getString(cursor.getColumnIndex(Utils.COLoUM_LNAME)));
//            data.setDescription(cursor.getString(cursor.getColumnIndex(Utils.COLoUM_DESCRIPTION)));
//            data.setAge(cursor.getString(cursor.getColumnIndex(Utils.COLoUM_AGE)));
//            data.setTimeStamp(cursor.getString(cursor.getColumnIndex(Utils.COLoUM_TIMESTAMP)));
        }

        public int getCountData(){

        String query = " SELECT * FROM "+ Utils.TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            int count = cursor.getCount();
            cursor.close();
            return count;
        }



}
