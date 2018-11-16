package com.maya.yaswanththaluri.test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {


    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        final TextView t = (TextView)findViewById(R.id.textname);
        final TextView t2 = (TextView)findViewById(R.id.email);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = auth.getCurrentUser();

                if (user == null)
                {
                    startActivity(new Intent(Profile.this, MainActivity.class));
                    finish();
                }
                else
                {
                    String name = user.getDisplayName();
                    t.setText(name);
                    String mail = user.getEmail();
                    t2.setText(mail);
                }
            }
        };

        Button b =(Button)findViewById(R.id.signout);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }
}
