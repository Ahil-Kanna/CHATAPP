package com.example.admin.chatapp;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Chat extends AppCompatActivity {
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    DatabaseReference reference1, reference2;

    String username,chatWith;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        username = getIntent().getStringExtra("u1");
        chatWith = getIntent().getStringExtra("u2");
        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout) findViewById(R.id.layout2);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        reference1 = FirebaseDatabase.getInstance().getReference().child("messages");//.child("messages").child(username + "_" + chatWith);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if (!messageText.equals("")) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("text", messageText);
                    map.put("name", username);
                    reference1.child(username).child(username + "_" + chatWith).push().setValue(map);
                    reference1.child(chatWith).child(chatWith + "_" + username).push().setValue(map);
                    messageArea.setText("");
                    addMessageBox(messageText,1);
                }
            }
        });

        //Toast.makeText(this, username+chatWith, Toast.LENGTH_SHORT).show();

        /*reference1.child(username).addValueEventListener(new ValueEventListener() {
            @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> ds = dataSnapshot.child(username + "_" + chatWith).getChildren();
                for (DataSnapshot chatt : ds) {
                    HashMap<String, String> map = chatt.getValue(HashMap.class);
                    String message = map.get("text").toString();
                    String userName = map.get("name").toString();

                    if (userName.equals(username)) {
                        addMessageBox("You:-\n" + message, 1);
                    } else {
                        addMessageBox(chatWith + ":-\n" + message, 2);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }





    public void addMessageBox(String message, int type){
        TextView textView = new TextView(Chat.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 2) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_in);
        }
        else{
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_out);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}