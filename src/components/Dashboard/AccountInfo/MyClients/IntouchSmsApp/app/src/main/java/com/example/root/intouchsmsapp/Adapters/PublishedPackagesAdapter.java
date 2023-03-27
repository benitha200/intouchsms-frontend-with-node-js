package com.example.root.intouchsmsapp.Adapters;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.root.intouchsmsapp.Models.MessageLogDetailsItems;
import com.example.root.intouchsmsapp.Models.MyCustomersItems;
import com.example.root.intouchsmsapp.Models.PublishedPackagesItems;
import com.example.root.intouchsmsapp.R;

import java.util.ArrayList;
import java.util.List;



public class PublishedPackagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    //private Context mContext;
    private List<PublishedPackagesItems> publishedPackagesItemsList;
    private PublishedPackageAdapterListener listener;

    private boolean isMoreLoading = true;
    private OnLoadMoreListener onLoadMoreListener;

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView publishedPackageContainer;

        TextView packagename, min_credits, max_credits, unitprice, pricerange;

        public MyViewHolder(View itemView) {
            super(itemView);

            packagename = itemView.findViewById(R.id.textViewPackageName);
            min_credits = itemView.findViewById(R.id.textViewMinCredits);
            max_credits = itemView.findViewById(R.id.textViewMaxCredits);
            unitprice = itemView.findViewById(R.id.publishedpackage_textViewUnitPrice);
            pricerange = itemView.findViewById(R.id.publishedpackagerange);

            publishedPackageContainer = (CardView) itemView.findViewById(R.id.publishedPackageContainer);

            /*service = (TextView) view.findViewById(R.id.service);
            amount = (TextView) view.findViewById(R.id.amountcollected);
            collectionContainer = (LinearLayout) view.findViewById(R.id.collection_container);*/

            //view.setOnLongClickListener(this);
        }

        /*@Override
        public boolean onLongClick(View view) {
            listener.onRowLongClicked(getAdapterPosition());
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }*/
    }


    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pBar;
        public ProgressViewHolder(View v) {
            super(v);
            pBar = (ProgressBar) v.findViewById(R.id.itemProgressBar);
        }
    }

    public PublishedPackagesAdapter(OnLoadMoreListener onLoadMoreListener, PublishedPackageAdapterListener listener) {
        this.listener = listener;
        this.onLoadMoreListener=onLoadMoreListener;
        publishedPackagesItemsList =new ArrayList<>();
    }

    /*public MyCollectionsAdapter(Context mContext, List<Booking> bookings) {
        this.mContext = mContext;
        this.bookings = bookings;
        //this.listener = listener;
        //selectedItems = new SparseBooleanArray();
        //animationItemsIndex = new SparseBooleanArray();
        this.onLoadMoreListener=onLoadMoreListener;
    }*/

    @Override
    public int getItemViewType(int position) {
        return publishedPackagesItemsList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.publishedpackages_rows, parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressbar_loadmore, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            PublishedPackagesItems publishedPackagesItems = (PublishedPackagesItems) publishedPackagesItemsList.get(position);
            ((MyViewHolder) holder).packagename.setText("Name: "+publishedPackagesItems.getName());
            ((MyViewHolder) holder).min_credits.setText("Min Cr: "+publishedPackagesItems.getMincredits());
            ((MyViewHolder) holder).unitprice.setText("Unit Price: "+publishedPackagesItems.getUnitprice() + " RWF ");

            if (publishedPackagesItems.getMaximum_null()){
                ((MyViewHolder) holder).max_credits.setText("Max Cr: "+publishedPackagesItems.getMincredits() +" + ");
                ((MyViewHolder) holder).pricerange.setText("Price Range: "+publishedPackagesItems.getMin_price()+" RWF - "+publishedPackagesItems.getMin_price() +" RWF +");
            } else {
                ((MyViewHolder) holder).max_credits.setText("Max Cr: "+publishedPackagesItems.getMaxcredits());
                ((MyViewHolder) holder).pricerange.setText("Price Range: "+publishedPackagesItems.getMin_price()+" RWF - "+publishedPackagesItems.getMax_price() + " RWF ");
            }

            // apply click events
            applyClickEvents((MyViewHolder) holder, position);
        }
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {


        holder.publishedPackageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPublishedPackageRowClicked(position);
            }
        });

    }


    @Override
    public long getItemId(int position) {
        return publishedPackagesItemsList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return publishedPackagesItemsList.size();
    }

    public List<PublishedPackagesItems> getItems() {
        return publishedPackagesItemsList;
    }


    public interface PublishedPackageAdapterListener {
        //void onIconClicked(int position);

        //void onIconImportantClicked(int position);

        void onPublishedPackageRowClicked(int position);

        //void onRowLongClicked(int position);
    }

    public void showLoading() {
        if (isMoreLoading && publishedPackagesItemsList != null && onLoadMoreListener != null) {
            isMoreLoading = false;
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    publishedPackagesItemsList.add(null);
                    notifyItemInserted(publishedPackagesItemsList.size() - 1);
                    onLoadMoreListener.onLoadMore();
                }
            });
        }
    }

    public void setMore(boolean isMore) {
        this.isMoreLoading = isMore;
    }

    public void dismissLoading() {
        if (publishedPackagesItemsList != null && publishedPackagesItemsList.size() > 0) {
            publishedPackagesItemsList.remove(publishedPackagesItemsList.size() - 1);
            notifyItemRemoved(publishedPackagesItemsList.size());
        }
    }

    public void addAll(List<PublishedPackagesItems> lst){
        publishedPackagesItemsList.clear();
        publishedPackagesItemsList.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<PublishedPackagesItems> lst){
        int sizeInit = publishedPackagesItemsList.size();
        publishedPackagesItemsList.addAll(lst);
        notifyItemRangeChanged(sizeInit, publishedPackagesItemsList.size());
    }

    public void clear(){
        publishedPackagesItemsList.clear();
        notifyDataSetChanged();
    }
}