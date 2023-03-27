package com.example.root.intouchsmsapp.Adapters;

import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.root.intouchsmsapp.Models.TransactionsItems;
import com.example.root.intouchsmsapp.R;

import java.util.ArrayList;
import java.util.List;



public class TransactionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    //private Context mContext;
    private List<TransactionsItems> transactions;
    private TransactionsAdapterListener listener;
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
        public CardView transactionContainer;

        TextView transaction, smsquantity, amount, created, opening_bl, closing_bl;

        public MyViewHolder(View itemView) {
            super(itemView);

            transaction = itemView.findViewById(R.id.transactionName);
            amount = itemView.findViewById(R.id.transactionAmount);
            smsquantity = itemView.findViewById(R.id.transactionSMSQuantity);
            created = itemView.findViewById(R.id.transactionCreatedOn);
            opening_bl = itemView.findViewById(R.id.transactionOpeningBl);
            closing_bl = itemView.findViewById(R.id.transactionClosingBl);
            transactionContainer = (CardView) itemView.findViewById(R.id.transactionContainer);

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

    public TransactionsAdapter(OnLoadMoreListener onLoadMoreListener, TransactionsAdapterListener listener) {
        this.listener = listener;
        this.onLoadMoreListener=onLoadMoreListener;
        transactions =new ArrayList<>();
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
        return transactions.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.transactions_rows, parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressbar_loadmore, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            TransactionsItems transactionsItems = (TransactionsItems) transactions.get(position);
            ((MyViewHolder) holder).transaction.setText(transactionsItems.getTransaction());
            ((MyViewHolder) holder).smsquantity.setText(transactionsItems.getSmsquantity());
            ((MyViewHolder) holder).amount.setText("Amount: "+transactionsItems.getAmount() + " RWF");
            ((MyViewHolder) holder).created.setText(transactionsItems.getCreated());
            ((MyViewHolder) holder).opening_bl.setText("Opening Bal "+transactionsItems.getOpeningbalance());
            ((MyViewHolder) holder).closing_bl.setText("Closing Bal "+transactionsItems.getClosingbalance());

            // apply click events
            applyClickEvents((MyViewHolder) holder, position);
        }
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {


       holder.transactionContainer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               listener.onTransactionRowClicked(position);
           }
       });

    }


    @Override
    public long getItemId(int position) {
        return transactions.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public List<TransactionsItems> getItems() {
        return transactions;
    }


    public interface TransactionsAdapterListener {
        //void onIconClicked(int position);

        //void onIconImportantClicked(int position);

        void onTransactionRowClicked(int position);

        //void onRowLongClicked(int position);
    }

    public void showLoading() {
        if (isMoreLoading && transactions != null && onLoadMoreListener != null) {
            isMoreLoading = false;
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    transactions.add(null);
                    notifyItemInserted(transactions.size() - 1);
                    onLoadMoreListener.onLoadMore();
                }
            });
        }
    }

    public void setMore(boolean isMore) {
        this.isMoreLoading = isMore;
    }

    public void dismissLoading() {
        if (transactions != null && transactions.size() > 0) {
            transactions.remove(transactions.size() - 1);
            notifyItemRemoved(transactions.size());
        }
    }

    public void addAll(List<TransactionsItems> lst){
        transactions.clear();
        transactions.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<TransactionsItems> lst){
        int sizeInit = transactions.size();
        transactions.addAll(lst);
        notifyItemRangeChanged(sizeInit, transactions.size());
    }

    public void clear(){
        transactions.clear();
        notifyDataSetChanged();
    }
}