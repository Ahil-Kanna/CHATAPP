package com.example.admin.chatapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Chat_page extends AppCompatActivity {

    ListView list;
    ListAdapter adapter;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ArrayList<String> listItems=new ArrayList<String>();
        list=(ListView) findViewById(R.id.listview);
        listItems.add("Contacts");
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        list.setAdapter(adapter);

        databaseReference= FirebaseDatabase.getInstance().getReference("chat");



    }


}
