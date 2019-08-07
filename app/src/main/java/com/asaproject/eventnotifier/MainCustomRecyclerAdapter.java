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

import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MainCustomRecyclerAdapter extends RecyclerView.Adapter<MainCustomRecyclerAdapter.ViewHolder>
{

    private List<KeyForEvents> eventsList;
    private Context context;

    public MainCustomRecyclerAdapter(List<KeyForEvents> eventsList,Context context) {
        this.context=context;
        this.eventsList=eventsList;
    }








    @NonNull
    @Override
    public MainCustomRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_row,parent,false);
        return new MainCustomRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainCustomRecyclerAdapter.ViewHolder holder, final int position)
    {
        KeyForEvents ewk=eventsList.get(position);
        holder.textTitle.setText(ewk.ei.getTitle());
        holder.textDescription.setText(ewk.ei.getDesc());
        holder.textUsername.setText(ewk.ei.getUsername());
        Picasso.get().load(ewk.ei.getImageUrl()).transform(new RoundedCornersTransformation(43,0)).into(holder.post_image);
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){

                Intent s = new Intent(context,SingleEvent.class);
                s.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                s.putExtra("PostId",eventsList.get(position).key);
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
        TextView textTitle,textDescription,textUsername;
        ImageView post_image;

        public ViewHolder(View itemView)
        {
            super(itemView);
            textTitle=itemView.findViewById(R.id.textTitle);
            textDescription=itemView.findViewById(R.id.textDescription);
            textUsername=itemView.findViewById(R.id.textUsername);
            post_image=itemView.findViewById(R.id.post_image);
        }
    }

}
