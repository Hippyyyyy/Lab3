package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.CursorLoader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);
        requestPermission();
        getContacts();

    }
    public void getContacts(){
        List<String> arr = new ArrayList<>();
        Uri uri = Uri.parse("content://contacts/people");

        CursorLoader cursorLoader = new CursorLoader(this, uri, null, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            String s = "";

            String id = ContactsContract.Contacts._ID;
            int indexID = cursor.getColumnIndex(id);
            s+=cursor.getString(indexID) + " - ";

            String name = ContactsContract.Contacts.DISPLAY_NAME;
            int indexNAME = cursor.getColumnIndex(name);
            s+=cursor.getString(indexNAME) + " - ";

            arr.add(s);

            cursor.moveToNext();

        }
        cursor.close();

        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, arr);
        lv.setAdapter(arrayAdapter);
    }
    public boolean requestPermission(){
        if (Build.VERSION.SDK_INT >= 23){
            if (checkSelfPermission(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
                return true;
            }else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS
                },1);
                return false;
            }
        }else {
            return false;
        }
    }
}