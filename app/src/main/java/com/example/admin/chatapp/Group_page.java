package com.example.admin.chatapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Group_page extends AppCompatActivity {

    ListView list;
    ArrayAdapter adapter;
    DatabaseReference databaseReference;
    String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ArrayList<String> listItems = new ArrayList<String>();
        list = (ListView) findViewById(R.id.group_list);
        listItems.add("No Groups");


        databaseReference= FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = databaseReference.child("messages");
        databaseReference.child("map").child("u2n").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uname =dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                Intent i = new Intent(Group_page.this, Chat.class);
                i.putExtra("u1",uname );
                i.putExtra("u2", listItems.get(position));

                startActivity(i);

            }
        });

        usersdRef.child(uname).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listItems.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    listItems.add(ds.getKey().split("_")[1]);

                }
                ArrayAdapter<String> adapter = new ArrayAdapter(Group_page.this, android.R.layout.simple_list_item_1, listItems);

                list.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
                        HashMap<String,String> obj=new HashMap<>();
                        obj.put("text","created");
                        obj.put("name",uname);
                        databaseReference.child("messages").child(yourTextView.getText().toString()).push().setValue(obj);
                        //databaseReference.child("messages").child(databaseReference.child("map").child("u2n").child(new FirebaseAuth().getCurrentUser().getUid())+"_"+yourTextView.getText().toString()).push(text);
                        //adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                dialog.setCancelable(true);
                dialog.show();

            }
        });
    }








}



