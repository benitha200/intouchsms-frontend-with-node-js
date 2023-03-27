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

import com.example.root.intouchsmsapp.Models.CreditTransfersItems;
import com.example.root.intouchsmsapp.Models.MessageLogDetailsItems;
import com.example.root.intouchsmsapp.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;



public class CreditTransfersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    //private Context mContext;
    private List<CreditTransfersItems> creditTransfersItemsList;
    private CreditTransfersAdapterListener listener;

    private boolean isMoreLoading = true;
    private OnLoadMoreListener onLoadMoreListener;

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView creditTransferContainer;

        TextView name, phone, amount, created;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.credittransfer_textViewCustomerName);
            phone = itemView.findViewById(R.id.credittransfer_textViweCustomerPhone);
            amount = itemView.findViewById(R.id.credittransfer__textViewCustomerAmount);
            created = itemView.findViewById(R.id.credittransfer_textViewCustomerCreated);
            creditTransferContainer = (CardView) itemView.findViewById(R.id.creditTransferContainer);

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

    public CreditTransfersAdapter(OnLoadMoreListener onLoadMoreListener, CreditTransfersAdapterListener listener) {
        this.listener = listener;
        this.onLoadMoreListener=onLoadMoreListener;
        creditTransfersItemsList =new ArrayList<>();
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
        return creditTransfersItemsList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.credittransfers_rows, parent, false));
        } else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressbar_loadmore, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            CreditTransfersItems creditTransfersItems = (CreditTransfersItems) creditTransfersItemsList.get(position);
            Double amounttransfered = (Double.parseDouble(creditTransfersItems.getAmountransfered()) * 100.0) / 100.0;
            ((MyViewHolder) holder).name.setText("Names: "+creditTransfersItems.getCustomername());
            ((MyViewHolder) holder).phone.setText("Phone "+creditTransfersItems.getPhone());
            ((MyViewHolder) holder).amount.setText("Amount "+ NumberFormat.getInstance().format(Math.round(amounttransfered * 100.0) / 100.0).toString() + " Credits");
            ((MyViewHolder) holder).created.setText("Created "+creditTransfersItems.getCreated());


            // apply click events
            applyClickEvents((MyViewHolder) holder, position);
        }
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {


        holder.creditTransferContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCreditTransferRowClicked(position);
            }
        });

    }


    @Override
    public long getItemId(int position) {
        return creditTransfersItemsList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return creditTransfersItemsList.size();
    }

    public List<CreditTransfersItems> getItems() {
        return creditTransfersItemsList;
    }


    public interface CreditTransfersAdapterListener {
        //void onIconClicked(int position);

        //void onIconImportantClicked(int position);

        void onCreditTransferRowClicked(int position);

        //void onRowLongClicked(int position);
    }

    public void showLoading() {
        if (isMoreLoading && creditTransfersItemsList != null && onLoadMoreListener != null) {
            isMoreLoading = false;
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    creditTransfersItemsList.add(null);
                    notifyItemInserted(creditTransfersItemsList.size() - 1);
                    onLoadMoreListener.onLoadMore();
                }
            });
        }
    }

    public void setMore(boolean isMore) {
        this.isMoreLoading = isMore;
    }

    public void dismissLoading() {
        if (creditTransfersItemsList != null && creditTransfersItemsList.size() > 0) {
            creditTransfersItemsList.remove(creditTransfersItemsList.size() - 1);
            notifyItemRemoved(creditTransfersItemsList.size());
        }
    }

    public void addAll(List<CreditTransfersItems> lst){
        creditTransfersItemsList.clear();
        creditTransfersItemsList.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<CreditTransfersItems> lst){
        int sizeInit = creditTransfersItemsList.size();
        creditTransfersItemsList.addAll(lst);
        notifyItemRangeChanged(sizeInit, creditTransfersItemsList.size());
    }

    public void clear(){
        creditTransfersItemsList.clear();
        notifyDataSetChanged();
    }
}