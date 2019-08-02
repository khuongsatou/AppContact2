package com.example.appcontact.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appcontact.Contact.Contact;

import java.util.ArrayList;
import java.util.List;

public class SqliteRender extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="dbcontact.sqlite";

    private static final String TABLE_NAME = "contact";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NUMBER = "number";

    private final static  String  CREATE_TABLE  = "CREATE TABLE "+TABLE_NAME+"( "+ COLUMN_ID +" INTEGER PRIMARY KEY ,"+ COLUMN_NAME +" VARCHAR ,"+ COLUMN_NUMBER +" VARCHAR )";

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

    public long Insert(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,contact.getName());
        values.put(COLUMN_NUMBER,contact.getNumber());
        long result = db.insert(TABLE_NAME,null,values);
        return result;
    }
    public long Update(Contact contact,int position){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,contact.getName());
        values.put(COLUMN_NUMBER,contact.getNumber());
        long result = db.update(TABLE_NAME,values,COLUMN_ID + "= ?",new String[]{position+""});
        return result;
    }

    public long Delete(int position){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,COLUMN_ID +"= ?",new String[]{ position+""});
        return result;
    }

    public List<Contact> SelectListContact(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Contact> contactList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
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


}
