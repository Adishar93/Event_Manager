package com.asaproject.eventnotifier;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder>
{
    private List<KeyForEvents> eventsList;
    private Context context;

    //DatabaseReference databaseEvent;

    public CustomRecyclerAdapter(List<KeyForEvents> eventsList,Context context) {
        this.context=context;
        this.eventsList=eventsList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cevent_row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        EventItems ei=eventsList.get(position).ei;
        holder.textTitle.setText(ei.getTitle());
        Picasso.get().load(ei.getImageUrl()).transform(new RoundedCornersTransformation(43,0)).into(holder.post_image);
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){

                Intent s = new Intent(context,SingleEvent.class);
                s.putExtra("PostId",eventsList.get(position).key);
                s.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(s);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textTitle;
        ImageView post_image;

        public ViewHolder(View itemView)
        {
            super(itemView);
            textTitle=itemView.findViewById(R.id.textTitle);
            post_image=itemView.findViewById(R.id.post_image);
        }
    }
}
