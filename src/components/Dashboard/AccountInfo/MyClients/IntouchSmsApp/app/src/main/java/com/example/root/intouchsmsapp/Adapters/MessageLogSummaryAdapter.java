package com.example.root.intouchsmsapp.Adapters;

import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.root.intouchsmsapp.Models.MessageLogSummaryItems;
import com.example.root.intouchsmsapp.R;

import java.util.ArrayList;
import java.util.List;



public class MessageLogSummaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    //private Context mContext;
    private List<MessageLogSummaryItems> messageLogSummaryItemsList;
    private MessageLogSummaryAdapterListener listener;
    //private SparseBooleanArray selectedItems;

    // array used to perform multiple animation at once
    //private SparseBooleanArray animationItemsIndex;
    //private boolean reverseAllAnimations = false;

    // index is used to animate only the selected row
    // dirty fix, find a better solution
    //private static int currentSelectedIndex = -1;

    private boolean isMoreLoading = true;
    private OnLoadMoreListener onLoadMoreListener;

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView messagelogSummaryContainer;

        TextView from, queued, delivered, undelivered, unsent, credits, messsage;

        ImageButton copycontent;

        public MyViewHolder(View itemView) {
            super(itemView);

            from = itemView.findViewById(R.id.messagelogsummary_from);
            queued = itemView.findViewById(R.id.messagelogsummary_queued);
            delivered = itemView.findViewById(R.id.messagelogsummaryDelivered);
            undelivered = itemView.findViewById(R.id.messagelogsummaryUndelivered);
            unsent = itemView.findViewById(R.id.messagelogsummary_unsent);
            credits = itemView.findViewById(R.id.messagelogsummaryCredits);
            messsage = itemView.findViewById(R.id.messagelogsummary_message);
            copycontent = itemView.findViewById(R.id.copycontent);
            messagelogSummaryContainer = (CardView) itemView.findViewById(R.id.messageLogSummaryContainer);

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

    public MessageLogSummaryAdapter(OnLoadMoreListener onLoadMoreListener, MessageLogSummaryAdapterListener listener) {
        this.listener = listener;
        this.onLoadMoreListener=onLoadMoreListener;
        messageLogSummaryItemsList =new ArrayList<>();
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
        return messageLogSummaryItemsList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.messagelogsummary_rows, parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressbar_loadmore, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MessageLogSummaryItems messageLogSummaryItems = (MessageLogSummaryItems) messageLogSummaryItemsList.get(position);
            ((MyViewHolder) holder).from.setText("From "+messageLogSummaryItems.getFrom());
            ((MyViewHolder) holder).queued.setText("Queued "+messageLogSummaryItems.getQueued());
            ((MyViewHolder) holder).delivered.setText("Delivered "+messageLogSummaryItems.getDelivered());
            ((MyViewHolder) holder).undelivered.setText("Undelivered "+messageLogSummaryItems.getUndelivered());
            ((MyViewHolder) holder).unsent.setText("Total "+messageLogSummaryItems.getTotal());
            ((MyViewHolder) holder).credits.setText("Credits "+messageLogSummaryItems.getCredits());
            ((MyViewHolder) holder).messsage.setText("Message: "+messageLogSummaryItems.getMessage());

            // apply click events
            applyClickEvents((MyViewHolder) holder, position);
        }
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {


        holder.messagelogSummaryContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMesssageLogSummaryRowClicked(position);
            }
        });

        holder.copycontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCopyContentClicked(position);
            }
        });

    }


    @Override
    public long getItemId(int position) {
        return messageLogSummaryItemsList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return messageLogSummaryItemsList.size();
    }

    public List<MessageLogSummaryItems> getItems() {
        return messageLogSummaryItemsList;
    }


    public interface MessageLogSummaryAdapterListener {
        //void onIconClicked(int position);

        //void onIconImportantClicked(int position);

        void onMesssageLogSummaryRowClicked(int position);
        void onCopyContentClicked(int position);
        //void onRowLongClicked(int position);
    }

    public void showLoading() {
        if (isMoreLoading && messageLogSummaryItemsList != null && onLoadMoreListener != null) {
            isMoreLoading = false;
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    messageLogSummaryItemsList.add(null);
                    notifyItemInserted(messageLogSummaryItemsList.size() - 1);
                    onLoadMoreListener.onLoadMore();
                }
            });
        }
    }

    public void setMore(boolean isMore) {
        this.isMoreLoading = isMore;
    }

    public void dismissLoading() {
        if (messageLogSummaryItemsList != null && messageLogSummaryItemsList.size() > 0) {
            messageLogSummaryItemsList.remove(messageLogSummaryItemsList.size() - 1);
            notifyItemRemoved(messageLogSummaryItemsList.size());
        }
    }

    public void addAll(List<MessageLogSummaryItems> lst){
        messageLogSummaryItemsList.clear();
        messageLogSummaryItemsList.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<MessageLogSummaryItems> lst){
        int sizeInit = messageLogSummaryItemsList.size();
        messageLogSummaryItemsList.addAll(lst);
        notifyItemRangeChanged(sizeInit, messageLogSummaryItemsList.size());
    }

    public void clear(){
        messageLogSummaryItemsList.clear();
        notifyDataSetChanged();
    }
}