package com.maya.yaswanththaluri.test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Signup extends AppCompatActivity {

    private EditText mail, password, cnf, name;
    private Button create;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ImageView i =(ImageView)findViewById(R.id.logoup);
        Glide.with(this).load(R.drawable.internshala).apply(RequestOptions.circleCropTransform()).into(i);

        firebaseAuth = FirebaseAuth.getInstance();

        mail = (EditText)findViewById(R.id.mailup);
        password = (EditText)findViewById(R.id.passwordup);
        cnf = (EditText)findViewById(R.id.cnfup);
        name = (EditText)findViewById(R.id.nameup);

        create = (Button)findViewById(R.id.upsignup);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email, pswd, cnfpswd, na;

                email = mail.getText().toString();
                pswd = password.getText().toString();
                cnfpswd = cnf.getText().toString();
                na = name.getText().toString();

                if(pswd.equals(cnfpswd))
                {
                    firebaseAuth.createUserWithEmailAndPassword(email, pswd)
                            .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful())
                                    {
                                        Toast.makeText(Signup.this, "Failed in creating account"
                                        , Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        UserProfileChangeRequest update = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(na).build();
                                        user.updateProfile(update);

                                        user.sendEmailVerification()
                                                .addOnCompleteListener(Signup.this, new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (!task.isSuccessful())
                                                        {
                                                            Toast.makeText(Signup.this, "Account created successfully but failed to sent " +
                                                                            "Verification mail"
                                                                    , Toast.LENGTH_SHORT).show();
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(Signup.this, "Account created successfully and verification sent to MailId"
                                                                    , Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                        Toast.makeText(Signup.this, "Account created successfully"
                                                , Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(Signup.this, MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(Signup.this, "Both password and confirm pasword should match"
                            , Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
