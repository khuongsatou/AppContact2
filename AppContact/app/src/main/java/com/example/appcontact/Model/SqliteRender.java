package com.example.appcontact.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.appcontact.Contact.Contact;

import java.util.ArrayList;
import java.util.List;

public class SqliteRender extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="dbcontact.sqlite";

    private static final String TABLE_NAME = "contact";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NUMBER = "number";
    private static final String COLUMN_STATUS_DELETE = "status_delete";

    private final static  String  CREATE_TABLE  = "CREATE TABLE "+TABLE_NAME+"( "+ COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_NAME +" VARCHAR ,"+ COLUMN_NUMBER +" VARCHAR, " + COLUMN_STATUS_DELETE + " INTEGER )";

    public SqliteRender(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void Insert(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,contact.getName());
        values.put(COLUMN_NUMBER,contact.getNumber());
        values.put(COLUMN_STATUS_DELETE,0);
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public void Update(int position,Contact contact){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,contact.getName());
        values.put(COLUMN_NUMBER,contact.getNumber());
        db.update(TABLE_NAME,values,COLUMN_ID + "= ?",new String[]{(position)+""});
        db.close();
    }

    public void Delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS_DELETE,1);
        db.update(TABLE_NAME,values,COLUMN_ID + "= ?",new String[]{(id)+""});
        db.close();
    }

    public List<Contact> SelectListContact(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Contact> contactList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME +" WHERE "+ COLUMN_STATUS_DELETE +" = 0",null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                Log.d("idContact",id+"");
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String number = cursor.getString(cursor.getColumnIndex(COLUMN_NUMBER));
                Contact contact = new Contact(name,number);
                contactList.add(contact);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return contactList;
    }
    public int SelectIDContact(String name,String number){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ COLUMN_ID +" FROM "+TABLE_NAME +" WHERE "+ COLUMN_NAME + " = '"+ name +"' AND " + COLUMN_NUMBER + " = '"+number+ "' ",null);
        int id = 0 ;
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            cursor.close();
        }
        return id;
    }



}
