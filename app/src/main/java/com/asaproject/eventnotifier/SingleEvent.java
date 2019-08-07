package com.asaproject.eventnotifier;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class SingleEvent extends AppCompatActivity {
    private String post_key =null;
    private DatabaseReference mDatabase,rDatabase;
    private StorageReference mStorage;
    private ImageView singlePostImage;
    private TextView singlePostTitle;
    private TextView singlePostDesc;
    private TextView singleDate;
    private Button deleteButton,viewRegistrants,registerButton;
    private FirebaseAuth mAuth;
    private EventItems ei;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);

        mDatabase = FirebaseDatabase.getInstance().getReference("events");
        rDatabase=FirebaseDatabase.getInstance().getReference("registrants");
        mStorage= FirebaseStorage.getInstance().getReference("eventimages");
        singlePostDesc = (TextView) findViewById(R.id.singleDesc);
        singlePostTitle = (TextView) findViewById(R.id.singleTitle);
        singleDate=findViewById(R.id.singleDate);
        singlePostImage = (ImageView) findViewById(R.id.singleImageView);


        mAuth = FirebaseAuth.getInstance();
        deleteButton = (Button) findViewById(R.id.singleDeleteButton);
        viewRegistrants=findViewById(R.id.viewRegistrants);
        registerButton=findViewById(R.id.register);
        deleteButton.setVisibility(View.GONE);
        viewRegistrants.setVisibility(View.GONE);
        registerButton.setVisibility(View.VISIBLE);

    }

    @Override
    public void onStart() {

        super.onStart();

        post_key = getIntent().getStringExtra("PostId");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ei = null;
                ei = dataSnapshot.child(post_key).getValue(EventItems.class);
                if (ei != null) {
                    String post_title = ei.getTitle();
                    String post_desc = ei.getDesc();
                    String post_image = ei.getImageUrl();
                    long eventDate=ei.getDateOfEvent();


                    // String post_username = (String) dataSnapshot.child("")
                    singlePostTitle.setText(post_title);
                    singlePostDesc.setText(post_desc);
                    Calendar cal=Calendar.getInstance();
                    cal.setTime(new Date(eventDate));
                    singleDate.setText("Date: "+cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR));
                    Picasso.get().load(post_image).transform(new RoundedCornersTransformation(40, 0)).into(singlePostImage);

                    if (mAuth.getCurrentUser() != null) {
                        if (mAuth.getCurrentUser().getEmail().equalsIgnoreCase(ei.getUsername())) {
                            deleteButton.setVisibility(View.VISIBLE);
                            viewRegistrants.setVisibility(View.VISIBLE);
                            registerButton.setVisibility(View.GONE);
                        }
                        else
                        {
                            deleteButton.setVisibility(View.GONE);
                            viewRegistrants.setVisibility(View.GONE);
                            registerButton.setVisibility(View.VISIBLE);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    public void deleteButtonClicked(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog;

        builder.setTitle("Delete Event");
        builder.setMessage("Are you Sure you want to Delete this Event?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();

            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mStorage.child(ei.getImageName()).delete();
                mDatabase.child(post_key).removeValue();
                rDatabase.child(post_key).removeValue();
                Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainIntent);
            }
        });
        dialog=builder.create();
        dialog.show();



    }

    public void openRegistrationPage(View v)
    {
        Intent i=new Intent(getApplicationContext(),RegistrationPage.class);
        i.putExtra("PostId",post_key);
        startActivity(i);
    }

//    @Override
//    public void onBackPressed() {
//        startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
//    }

    public void openListRegistrants(View v)
    {
        Intent i=new Intent(getApplicationContext(),ListRegistrants.class);
        i.putExtra("PostId",post_key);
        startActivity(i);
    }
}