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
import com.example.root.intouchsmsapp.R;

import java.util.ArrayList;
import java.util.List;



public class MessageLogDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    //private Context mContext;
    private List<MessageLogDetailsItems> messageLogDetailsItemsList;
    private MessageLogDetailsAdapterListener listener;

    private boolean isMoreLoading = true;
    private OnLoadMoreListener onLoadMoreListener;

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView messagelogDetailContainer;

        TextView /*from,*/ tocontact, to_phoneno, created, status/*, messsage*/;

        public MyViewHolder(View itemView) {
            super(itemView);

            /*from = itemView.findViewById(R.id.messagelogdetails_from);*/
            tocontact = itemView.findViewById(R.id.messagelogdetails_tocontact);
            to_phoneno = itemView.findViewById(R.id.messagelogdetails_to_phoneno);
            created = itemView.findViewById(R.id.messagelogdetails_created);
            status = itemView.findViewById(R.id.messagelogdetailstatus);
            /*messsage = itemView.findViewById(R.id.messagelogdetails_message);*/
            messagelogDetailContainer = (CardView) itemView.findViewById(R.id.messagelogdetailscontainer);

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

    public MessageLogDetailsAdapter(OnLoadMoreListener onLoadMoreListener, MessageLogDetailsAdapterListener listener) {
        this.listener = listener;
        this.onLoadMoreListener=onLoadMoreListener;
        messageLogDetailsItemsList =new ArrayList<>();
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
        return messageLogDetailsItemsList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.messagelogdetails_rows, parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressbar_loadmore, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MessageLogDetailsItems messageLogDetailsItems = (MessageLogDetailsItems) messageLogDetailsItemsList.get(position);
            /*((MyViewHolder) holder).from.setVisibility(View.GONE);*/
            //((MyViewHolder) holder).from.setText("From: "+messageLogDetailsItems.getFrom());

            if (messageLogDetailsItems.getTocontact().isEmpty()){
                ((MyViewHolder) holder).tocontact.setVisibility(View.GONE);
            } else {
                ((MyViewHolder) holder).tocontact.setText("To Contact: "+messageLogDetailsItems.getTocontact());
            }

            ((MyViewHolder) holder).to_phoneno.setText("To Phone No "+messageLogDetailsItems.getTo_phone());
            ((MyViewHolder) holder).created.setText(messageLogDetailsItems.getCreated());
            ((MyViewHolder) holder).status.setText(messageLogDetailsItems.getStatus());

            if (messageLogDetailsItems.getStatus().equals("Errored")){
                ((MyViewHolder) holder).status.setTextColor(Color.RED);
            }

            if (messageLogDetailsItems.getStatus().equals("Queued")){
                ((MyViewHolder) holder).status.setTextColor(Color.BLUE);
            }

            if (messageLogDetailsItems.getStatus().equals("Delivered")){
                ((MyViewHolder) holder).status.setTextColor(Color.parseColor("#4CAF50"));
            }

            if (messageLogDetailsItems.getStatus().equals("Unsent")){
                ((MyViewHolder) holder).status.setTextColor(Color.BLACK);
            }
            /*((MyViewHolder) holder).messsage.setVisibility(View.GONE);*/
            //((MyViewHolder) holder).messsage.setText("Message: "+messageLogDetailsItems.getMessage());

            // apply click events
            applyClickEvents((MyViewHolder) holder, position);
        }
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {


        holder.messagelogDetailContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMesssageLogDetailRowClicked(position);
            }
        });

    }


    @Override
    public long getItemId(int position) {
        return messageLogDetailsItemsList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return messageLogDetailsItemsList.size();
    }

    public List<MessageLogDetailsItems> getItems() {
        return messageLogDetailsItemsList;
    }


    public interface MessageLogDetailsAdapterListener {
        //void onIconClicked(int position);

        //void onIconImportantClicked(int position);

        void onMesssageLogDetailRowClicked(int position);

        //void onRowLongClicked(int position);
    }

    public void showLoading() {
        if (isMoreLoading && messageLogDetailsItemsList != null && onLoadMoreListener != null) {
            isMoreLoading = false;
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    messageLogDetailsItemsList.add(null);
                    notifyItemInserted(messageLogDetailsItemsList.size() - 1);
                    onLoadMoreListener.onLoadMore();
                }
            });
        }
    }

    public void setMore(boolean isMore) {
        this.isMoreLoading = isMore;
    }

    public void dismissLoading() {
        if (messageLogDetailsItemsList != null && messageLogDetailsItemsList.size() > 0) {
            messageLogDetailsItemsList.remove(messageLogDetailsItemsList.size() - 1);
            notifyItemRemoved(messageLogDetailsItemsList.size());
        }
    }

    public void addAll(List<MessageLogDetailsItems> lst){
        messageLogDetailsItemsList.clear();
        messageLogDetailsItemsList.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<MessageLogDetailsItems> lst){
        int sizeInit = messageLogDetailsItemsList.size();
        messageLogDetailsItemsList.addAll(lst);
        notifyItemRangeChanged(sizeInit, messageLogDetailsItemsList.size());
    }

    public void clear(){
        messageLogDetailsItemsList.clear();
        notifyDataSetChanged();
    }
}