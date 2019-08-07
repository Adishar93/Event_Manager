package com.asaproject.eventnotifier;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListRegistrants extends AppCompatActivity {

    private ListView lv;
    private TextView totalR;
    private DatabaseReference reference;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private int registrantsCount=0;
    private String post_key;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_registrants);
        setTitle("Registrants");
        lv=findViewById(R.id.lv);
        totalR=findViewById(R.id.totalR);
        pb=findViewById(R.id.pb);

        list=new ArrayList<>();

    }

    public void onStart()
    {
        pb.setVisibility(View.VISIBLE);
        super.onStart();
        post_key=getIntent().getStringExtra("PostId");

        reference=FirebaseDatabase.getInstance().getReference("registrants").child(post_key);

        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                list.clear();
                                                registrantsCount=0;
                                                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {

                                                    RegistrantClass r = eventSnapshot.getValue(RegistrantClass.class);
                                                    list.add("\nName: "+r.getName()+"\nEmail: "+r.getEmail()+"\nContact no: "+r.getContact()+"\n");
                                                    registrantsCount++;


                                                }
                                                pb.setVisibility(View.GONE);
                                                adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list);
                                                lv.setAdapter(adapter);
                                                totalR.setText("Total Registrants: "+registrantsCount);


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        }
        );
    }
}
