package com.example.admin.chatapp;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Change_pass extends AppCompatActivity {


    TextInputEditText oldpass,newpass,newpass1;
    TextInputLayout userl,passl;
    String newPass=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        oldpass=(TextInputEditText) findViewById(R.id.password1);
        newpass=(TextInputEditText) findViewById(R.id.password2);
        newpass1=(TextInputEditText) findViewById(R.id.password3);
    }


    public void change(View view){
        if(newpass.getText().toString().equals(newpass1.getText().toString())) {
            newPass=newpass.getText().toString();
        }

        final FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email,oldpass.getText().toString());

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                Snackbar snackbar_fail = Snackbar
                                        .make(findViewById(R.id.llayout1), "Something went wrong. Please try again later", Snackbar.LENGTH_LONG);
                                snackbar_fail.show();
                            }else {
                                Snackbar snackbar_su = Snackbar
                                        .make(findViewById(R.id.llayout1), "Password Successfully Modified", Snackbar.LENGTH_LONG);
                                snackbar_su.show();
                            }
                        }
                    });
                }else {
                    Snackbar snackbar_su = Snackbar
                            .make(findViewById(R.id.llayout1), "Authentication Failed", Snackbar.LENGTH_LONG);
                    snackbar_su.show();
                }
            }
        });
    }
}
