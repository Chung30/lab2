package com.example.lab2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {
    public static final String TableName = "ContactTable";
    public static final String Id = "Id";
    public static final String Name = "FullName";
    public static final String Phone = "Phonenumber";
    public static final String Image = "Image";
    public static final String Status = "Status";

    public MyDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "Create table if not exists " + TableName + "("
                + Id + " Integer primary key, "
                + Name + " Text, "
                + Phone + " Text, "
                + Image + " Blob, " // cho ảnh dạng byte
                + Status + " Integer)";

        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + TableName);
        onCreate(db);
    }

    public ArrayList<Contact> getAllContact(){
        ArrayList<Contact> list = new ArrayList<>();
        String sql = "Select * from " + TableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor != null){
            while (cursor.moveToNext()){
                Contact c = new Contact(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getBlob(3),
                        cursor.getInt(4) == 1 ? true : false);

                list.add(c);
            }
        }
        System.out.println(list.size() + "1");
        return list;
    }

    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Id, contact.getId());
        values.put(Name, contact.getName());
        values.put(Phone, contact.getPhone());
        values.put(Image, contact.getImg());
        values.put(Status, contact.isStatus() == true ? 1 : 0);

        try {
            db.insert(TableName, null, values);
        }
        catch (Exception e){
            System.out.println(e);
        }
        db.close();
    }

    public void EditContact(Contact contact, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Id, contact.getId());
        values.put(Name, contact.getName());
        values.put(Phone, contact.getPhone());
        values.put(Image, contact.getImg());
        values.put(Status, contact.isStatus() ? 1 : 0);

        db.update(TableName, values, Id + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteContact(int id){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Delete from " + TableName + " Where Id = " + id;
        db.execSQL(sql);
        db.close();
    }
}