package com.example.admin.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth=FirebaseAuth.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Main2Activity.this,Chat_page.class);
                startActivity(i);
            }
        });
    }

    public void validatemail(View view){
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Main2Activity.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(Main2Activity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void updatedet(View view){
        Intent i=new Intent(Main2Activity.this,Update_details.class);
        startActivity(i);
    }

    public void changepass(View view){

        Intent i=new Intent(Main2Activity.this,Change_pass.class);
        startActivity(i);
    }

    public void exitop(View view){
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.signOut();
        finishAffinity();
    }

    public void creategroup(View v){
        Intent i=new Intent(Main2Activity.this,Group_page.class);
        startActivity(i);
    }

}
