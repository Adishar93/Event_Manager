package com.asaproject.eventnotifier;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText un, ps;
    ProgressBar login_pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        auth = FirebaseAuth.getInstance();
        un = findViewById(R.id.un);
        ps = findViewById(R.id.ps);
        login_pb=findViewById(R.id.login_progress);

    }

    public void tryLogin(View v) {
        String u = un.getText().toString();
        String p = ps.getText().toString();
        if(TextUtils.isEmpty(u) || TextUtils.isEmpty(p))
        {

            Toast.makeText(this,"Enter Both Values!",Toast.LENGTH_LONG).show();
        }
        else {
            login_pb.setVisibility(View.VISIBLE);
            auth.signInWithEmailAndPassword(u, p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    // If sign in fails, display a message to the user. If sign in succeeds

                    if (task.isSuccessful()) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        login_pb.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                    } else {
                        login_pb.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Failed !Please check Username and Password", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
    }


    public void logout(View v)
    {
        if(auth.getCurrentUser()==null)
            Toast.makeText(getApplicationContext(),"Not Logged in",Toast.LENGTH_SHORT).show();
        else {
            auth.signOut();
            if (auth.getCurrentUser() == null)
                Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "Logout unsuccessful", Toast.LENGTH_SHORT).show();
        }

    }
    }




