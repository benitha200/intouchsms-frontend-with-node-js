package com.example.root.intouchsmsapp.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.intouchsmsapp.Models.GridViewItems;
import com.example.root.intouchsmsapp.R;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.RecyclerViewHolder> implements Filterable{

    private ArrayList<GridViewItems> mRecyclerList;
    private ArrayList<GridViewItems> mRecycleListFull;


    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void oncellclick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout itemclickhandler;
        private TextView itemname;
        private ImageView imageView;

        public RecyclerViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            itemclickhandler = itemView.findViewById(R.id.itemclickhandler);
            itemname = itemView.findViewById(R.id.textViewGrid);
            imageView = itemView.findViewById(R.id.imageView);


            itemclickhandler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.oncellclick(position);
                        }
                    }
                }
            });



        }
    }

    public GridViewAdapter(ArrayList<GridViewItems> recyclerList){
        this.mRecyclerList = recyclerList;
        mRecycleListFull = new ArrayList<>(mRecyclerList);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view, parent, false);
        RecyclerViewHolder rvh = new RecyclerViewHolder(v, mListener);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        GridViewItems currentItem = mRecyclerList.get(position);
        holder.itemname.setText(currentItem.getItemname());
        holder.imageView.setImageResource(currentItem.getImageView());

    }

    @Override
    public int getItemCount() {
        return mRecyclerList.size();
    }

    @Override
    public Filter getFilter() {
        return recyclerFilter;
    }

    private Filter recyclerFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<GridViewItems> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(mRecycleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (GridViewItems item : mRecycleListFull){
                    if (item.getItemname().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }

            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mRecyclerList.clear();
            mRecyclerList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
