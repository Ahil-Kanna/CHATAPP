package com.example.admin.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.widget.Toast;
import javax.crypto.Cipher.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    CheckBox checkBox;
    TextInputEditText user,pass;
    TextInputLayout userl,passl;
    private IntentIntegrator qrScan;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        user=(TextInputEditText) findViewById(R.id.usernamel);
        userl=(TextInputLayout) findViewById(R.id.textInputLayout);
        pass=(TextInputEditText) findViewById(R.id.passwordl);
        passl=(TextInputLayout) findViewById(R.id.passlay);
        checkBox=(CheckBox) findViewById(R.id.checkBox);
        qrScan = new IntentIntegrator(this);

        //
        mAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Details");

        //remember me
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            user.setText(loginPreferences.getString("username", ""));
            pass.setText(loginPreferences.getString("password", ""));
            checkBox.setChecked(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data

                    //converting the data to json
                    String obj = new String(result.getContents());
                    //setting values to textviews
                    user.setText(obj.split(",")[0]);
                    pass.setText(Decryptpass(obj.split(",")[1]));

                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private boolean validateForm(){
        if(TextUtils.isEmpty(user.getText().toString())) {
            userl.setError("This field can't be empty");
            Toast.makeText(this, "Enter fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!TextUtils.isEmpty(user.getText().toString())) {
            userl.setError(null);
        }

        if(!user.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")){
            userl.setError("Invalid Email");
            Toast.makeText(this, "Enter field properly", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(user.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))
            userl.setError(null);


        if(TextUtils.isEmpty(pass.getText().toString())){
            passl.setError("This field can't be empty");
            Toast.makeText(this, "Enter fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!TextUtils.isEmpty(pass.getText().toString())) {
            userl.setError(null);
        }
        if(pass.getText().toString().length()<8){
            passl.setError("Password too short");
            return false;
        }
        else if(!TextUtils.isEmpty(user.getText().toString())&& !TextUtils.isEmpty(pass.getText().toString())){
            passl.setError(null);
            return true;

        }
        else
            passl.setError(null);

        return false;

    }

    public void signin(View view){

        Log.d(TAG, "LoginAccount:" + user);
        if (!validateForm()) {
            return;
        }

        if (checkBox.isChecked()) {
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("username", user.getText().toString());
            loginPrefsEditor.putString("password", pass.getText().toString());
            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }

        // showProgressDialog();

        // [START create_user_with_email]
        mAuth.signInWithEmailAndPassword(user.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInUserWithEmail:success");
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            Intent i=new Intent(MainActivity.this,Main2Activity.class);
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        //  hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    public void createAccount(View view){
        Log.d(TAG, "createAccount:" + user);
        if (!validateForm()) {
            return;
        }

        // showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(user.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            Intent i=new Intent(MainActivity.this,Main2Activity.class);
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        //  hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    public void scanuser(View v){
        qrScan.initiateScan();
    }

    public String Decryptpass(String md5){

        passl.setPasswordVisibilityToggleDrawable(0);
        return md5;

    }

    public void forgotpass(View v){
        if(TextUtils.isEmpty(user.getText().toString())) {
            userl.setError("This field can't be empty");
            Toast.makeText(this, "Enter fields", Toast.LENGTH_SHORT).show();
        }
        else if(!TextUtils.isEmpty(user.getText().toString())) {
            userl.setError(null);
        }

        if(!user.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")){
            userl.setError("Invalid Email");
            Toast.makeText(this, "Enter field properly", Toast.LENGTH_SHORT).show();
        }
        else if(user.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))
            userl.setError(null);

        else{
        FirebaseAuth.getInstance().sendPasswordResetEmail(user.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Reset Email sent.");
                            Toast.makeText(MainActivity.this, "Reset Email sent.", Toast.LENGTH_SHORT).show();
                        }
                    else{
                            Toast.makeText(MainActivity.this, "Error sending Email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }}

}


