package com.example.admin.chatapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Group_page extends AppCompatActivity {

    ListView list;
    ListAdapter adapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(Group_page.this);
                dialog.setContentView(R.layout.group_details);
                dialog.setTitle("CREATE NEW GROUP");
                dialog.setCancelable(true);
                dialog.show();
            }
        });


        ArrayList<String> listItems = new ArrayList<String>();
        list = (ListView) findViewById(R.id.group_list);
        listItems.add("Contacts");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        list.setAdapter(adapter);

        databaseReference= FirebaseDatabase.getInstance().getReference("group");
    }


    }
