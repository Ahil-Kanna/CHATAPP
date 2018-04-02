package com.example.admin.chatapp;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Update_details extends AppCompatActivity {

    TextInputEditText email1,name1,phno1,age1;
    TextInputLayout emaill,namel,phnol,agel;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        email1=(TextInputEditText) findViewById(R.id.email);
        name1=(TextInputEditText) findViewById(R.id.name);
        phno1=(TextInputEditText) findViewById(R.id.phone);
        age1=(TextInputEditText) findViewById(R.id.age);
        email1.setText(firebaseAuth.getCurrentUser().getEmail());
    }

    public void update1(View view){

        if(validate()){



            Map<String,Object> values=new HashMap<String, Object>();
        values.put("Email",email1.getText().toString());
        values.put("Name",name1.getText().toString());
        values.put("Age",age1.getText().toString());
        values.put("Phone",phno1.getText().toString());
        values.put("balance","100");
        databaseReference.child("Details").child(firebaseAuth.getCurrentUser().getUid()).setValue(values);
            Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
            finish();
        }}

        public Boolean validate(){
           /* if(String.phno1.getText().toString())
            {
                phnol.setError("Enter valid phone number");
                Toast.makeText(Update_details.this, "Enter field properly", Toast.LENGTH_SHORT).show();
                return false;

            }
            else  {
                phnol.setError(null);
            }*/
            return true;
        }

}
