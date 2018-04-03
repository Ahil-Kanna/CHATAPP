package com.example.admin.chatapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Group_page extends AppCompatActivity {

    ListView list;
    ArrayAdapter adapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ArrayList<String> listItems = new ArrayList<String>();
        list = (ListView) findViewById(R.id.group_list);
        listItems.add("Groups");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        list.setAdapter(adapter);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(Group_page.this);
                dialog.setContentView(R.layout.group_details);
                dialog.setTitle("CREATE NEW GROUP");
                final TextView yourTextView = (TextView)dialog.findViewById(R.id.editText);
                Button button=(Button) dialog.findViewById(R.id.button7);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listItems.add(yourTextView.getText().toString());
                        list.setAdapter(adapter);
                        HashMap<String,String> obj=new HashMap<>();
                        obj.put("Admin",FirebaseAuth.getInstance().getCurrentUser().getUid());
                        databaseReference.child("Group").push().child(yourTextView.getText().toString()).setValue(obj);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                });
                dialog.setCancelable(true);
                dialog.show();

            }
        });



    }

    }
