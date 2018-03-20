package com.example.admin.chatapp;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Update_details extends AppCompatActivity {

    TextInputEditText email,name,phno,age;
    TextInputLayout emaill,namel,phnol,agel;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        email=(TextInputEditText) findViewById(R.id.email);
        name=(TextInputEditText) findViewById(R.id.name);
        phno=(TextInputEditText) findViewById(R.id.phone);
        age=(TextInputEditText) findViewById(R.id.age);
        email.setText(firebaseAuth.getCurrentUser().getEmail());
    }

    public void update1(View view){
        //Details details=new Details(email.getText().toString(),name.getText().toString(),Integer.parseInt(age.getText().toString()),phno.getText().toString(),100);
        Map<String,Object> values=new HashMap<String, Object>();
        values.put("Email",email.getText().toString());
        values.put("Name",name.getText().toString());
        values.put("Age",age.getText().toString());
        values.put("Phone",phno.getText().toString());
        values.put("balance","100");
        databaseReference.child("Details").child(firebaseAuth.getCurrentUser().getUid()).setValue(values);

    }
}
