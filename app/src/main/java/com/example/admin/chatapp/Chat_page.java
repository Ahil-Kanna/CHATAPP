package com.example.admin.chatapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.provider.ContactsContract;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Chat_page extends AppCompatActivity {

    static String uname;
    ListView list;
    ArrayAdapter adapter;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ArrayList<String> listItems = new ArrayList<String>();
        list = (ListView) findViewById(R.id.listview);
        listItems.add("No Contacts");
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

                Intent i = new Intent(Chat_page.this, Chat.class);
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
                ArrayAdapter<String> adapter = new ArrayAdapter(Chat_page.this, android.R.layout.simple_list_item_1, listItems);

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
               // Toast.makeText(Chat_page.this,uname, Toast.LENGTH_SHORT).show();
                final Dialog dialog=new Dialog(Chat_page.this);
                dialog.setContentView(R.layout.group_details);
                dialog.setTitle("ADD NEW CONTACT");
                final TextView yourTextView = (TextView)dialog.findViewById(R.id.editText);
                yourTextView.setHint("NEW CONTACT");
                Button button=(Button) dialog.findViewById(R.id.button7);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*listItems.add(yourTextView.getText().toString());
                        list.setAdapter(adapter);*/
                        HashMap<String,String> obj=new HashMap<>();
                        obj.put("text","created");
                        obj.put("name",uname);
                        databaseReference.child("messages").child(uname+"_"+yourTextView.getText().toString()).push().setValue(obj);
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



