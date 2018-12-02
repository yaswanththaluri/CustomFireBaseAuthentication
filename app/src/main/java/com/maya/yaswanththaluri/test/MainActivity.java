package com.maya.yaswanththaluri.test;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private EditText email, password;
    private Button button;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView i =(ImageView)findViewById(R.id.logo);
        Glide.with(this).load(R.drawable.internshala).apply(RequestOptions.circleCropTransform()).into(i);

        firebaseAuth = FirebaseAuth.getInstance();

        email = (EditText)findViewById(R.id.mail);
        password = (EditText)findViewById(R.id.password);
        button = (Button)findViewById(R.id.login);
        signup = (TextView)findViewById(R.id.signup);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail;
                String pswd;

                mail = email.getText().toString();
                pswd = password.getText().toString();

                if (!(mail.equals("")&&pswd.equals("")))
                {
                    firebaseAuth.signInWithEmailAndPassword(mail, pswd)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                 if (!task.isSuccessful())
                                 {
                                     Toast.makeText(MainActivity.this, "User Doesnt Exist / Incorrect credentials",
                                             Toast.LENGTH_SHORT).show();
                                 }
                                 else
                                 {
                                     FirebaseUser user = firebaseAuth.getCurrentUser();
                                     if(user.isEmailVerified())
                                     {
                                         Toast.makeText(MainActivity.this, "Successful Login",
                                                 Toast.LENGTH_SHORT).show();
                                         startActivity(new Intent(MainActivity.this, Profile.class));
                                     }
                                     else
                                     {
                                         Toast.makeText(MainActivity.this, "Email not verified",
                                                 Toast.LENGTH_SHORT).show();
                                     }
                                 }
                                }
                            });
                }
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, Signup.class);
                startActivity(i);
            }
        });


    }
}
