package com.asaproject.eventnotifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationPage extends AppCompatActivity {

    String post_key,reg_key;
    DatabaseReference rDatabase;
    EditText name,email,contact;
    RegistrantClass r;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        setTitle("Register");
        rDatabase=FirebaseDatabase.getInstance().getReference("registrants");
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        contact=findViewById(R.id.contact);
        pb=findViewById(R.id.pb);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        post_key=getIntent().getStringExtra("PostId");
    }
    public void addRegistrant(View v)
    {
        pb.setVisibility(View.VISIBLE);
        if(!((name.getText().toString().equals(""))||(email.getText().toString().equals(""))||(contact.getText().toString().equals("")))) {
            r = new RegistrantClass(name.getText().toString(), email.getText().toString(), contact.getText().toString());
            reg_key=rDatabase.child(post_key).push().getKey();
            rDatabase.child(post_key).child(reg_key).setValue(r).addOnSuccessListener(new OnSuccessListener<Void>() {

                @Override
                public void onSuccess(Void v) {
                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                    pb.setVisibility(View.GONE);
                    Intent i = new Intent(getApplicationContext(), SingleEvent.class);
                    i.putExtra("PostId", post_key);
                    startActivity(i);
                }
            });
        }
        else
            Toast.makeText(getApplicationContext(), "Please fill everything", Toast.LENGTH_SHORT).show();




    }
}
