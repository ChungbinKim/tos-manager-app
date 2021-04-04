package com.example.tosmanager.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class dbhelper extends SQLiteOpenHelper {

    static final String TABLE_NAME1 = "user";
    static final String TABLE_NAME2 = "policy";
    Context context;

    public dbhelper(Context context){
        super(context, "caucpas.db", null, 1 );
        this.context = context;
        Log.d(TAG, "DataBaseHedlper 생성자 호출");
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        try{
            Log.d(TAG, "Table Create");
            String createuser = "CREATE TABLE " + TABLE_NAME1 + "( email varchar(40) not null PRIMARY KEY, "+
                    "password varchar(20) not null);";
            String createpolicy = "CREATE TABLE " + TABLE_NAME2 + "( listnum INTEGER not null PRIMARY KEY, "+
                    "original TEXT);";
            db.execSQL(createuser);
            db.execSQL(createpolicy);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Table onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS USER");
        db.execSQL("DROP TABLE IF EXISTS POLICY");
    }

}