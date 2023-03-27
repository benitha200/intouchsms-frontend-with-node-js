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
import com.example.root.intouchsmsapp.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;



public class MyCustomersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    //private Context mContext;
    private List<MyCustomersItems> customersItemsList;
    private CustomersAdapterListener listener;

    private boolean isMoreLoading = true;
    private OnLoadMoreListener onLoadMoreListener;

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView customerContainer;

        TextView username, mobilephone,  lastlogin, status;

        public MyViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.myCustomerUserNameValue);
            mobilephone = itemView.findViewById(R.id.myCustomerMobilePhone);
            lastlogin = itemView.findViewById(R.id.mycustomerLastLogin);
            status = itemView.findViewById(R.id.myCustomerStatusValue);
            customerContainer = (CardView) itemView.findViewById(R.id.customersContainer);

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

    public MyCustomersAdapter(OnLoadMoreListener onLoadMoreListener, CustomersAdapterListener listener) {
        this.listener = listener;
        this.onLoadMoreListener=onLoadMoreListener;
        customersItemsList =new ArrayList<>();
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
        return customersItemsList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mycustomers_rows, parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressbar_loadmore, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyCustomersItems myCustomersItems = (MyCustomersItems) customersItemsList.get(position);
            Double customerbal = (Double.parseDouble(myCustomersItems.getCustomerbalance()) * 100.0) / 100.0;
            ((MyViewHolder) holder).username.setText("Username "+myCustomersItems.getUsername());
            ((MyViewHolder) holder).mobilephone.setText("Mobile Phone "+myCustomersItems.getPhone());
            ((MyViewHolder) holder).lastlogin.setText("Account Bal "+ NumberFormat.getInstance().format(Math.round(customerbal * 100.0) / 100.0).toString() +
                    " " + myCustomersItems.getSymbol()+" Last Login "+myCustomersItems.getLastlogin());
            ((MyViewHolder) holder).status.setText("Active: "+myCustomersItems.getStatus().toUpperCase());

            // apply click events
            applyClickEvents((MyViewHolder) holder, position);
        }
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {


        holder.customerContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCustomerRowClicked(position);
            }
        });

    }


    @Override
    public long getItemId(int position) {
        return customersItemsList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return customersItemsList.size();
    }

    public List<MyCustomersItems> getItems() {
        return customersItemsList;
    }


    public interface CustomersAdapterListener {
        //void onIconClicked(int position);

        //void onIconImportantClicked(int position);

        void onCustomerRowClicked(int position);

        //void onRowLongClicked(int position);
    }

    public void showLoading() {
        if (isMoreLoading && customersItemsList != null && onLoadMoreListener != null) {
            isMoreLoading = false;
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    customersItemsList.add(null);
                    notifyItemInserted(customersItemsList.size() - 1);
                    onLoadMoreListener.onLoadMore();
                }
            });
        }
    }

    public void setMore(boolean isMore) {
        this.isMoreLoading = isMore;
    }

    public void dismissLoading() {
        if (customersItemsList != null && customersItemsList.size() > 0) {
            customersItemsList.remove(customersItemsList.size() - 1);
            notifyItemRemoved(customersItemsList.size());
        }
    }

    public void addAll(List<MyCustomersItems> lst){
        customersItemsList.clear();
        customersItemsList.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<MyCustomersItems> lst){
        int sizeInit = customersItemsList.size();
        customersItemsList.addAll(lst);
        notifyItemRangeChanged(sizeInit, customersItemsList.size());
    }

    public void clear(){
        customersItemsList.clear();
        notifyDataSetChanged();
    }
}