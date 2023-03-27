package com.example.root.intouchsmsapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.intouchsmsapp.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    List<String> titles;
    List<Integer> images;
    Context context;
    LayoutInflater inflater;

    //constructor for the titles and images
    public Adapter(Context ctx, List<String> titles, List<Integer> images){
        this.titles = titles;
        this.images = images;
        this.inflater = LayoutInflater.from(ctx);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemname.setText(titles.get(position));
        holder.imageView.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemname;
        ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);

            itemname = itemView.findViewById(R.id.textViewGrid);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
