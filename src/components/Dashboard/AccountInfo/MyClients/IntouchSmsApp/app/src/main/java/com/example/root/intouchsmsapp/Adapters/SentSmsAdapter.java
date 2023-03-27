package com.example.root.intouchsmsapp.Adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.intouchsmsapp.Models.SentMessages;
import com.example.root.intouchsmsapp.R;


import java.util.ArrayList;
import java.util.List;

public class SentSmsAdapter extends RecyclerView.Adapter<SentSmsAdapter.RecyclerViewHolder>{

    private TextView network, cost, from, to, /*external_message_id,*/ created_on, sent_on, message, message_details, message_status;

    private static final String TAG = "SentMessagesAdapter";
    List<SentMessages> sentMessagesList;

    public SentSmsAdapter(List<SentMessages> sentMessagesList) {
        this.sentMessagesList = sentMessagesList;
    }





    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent_messages_row, parent, false);
        RecyclerViewHolder rvh = new RecyclerViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        SentMessages sentMessages = sentMessagesList.get(position);
        holder.network.setText(sentMessages.getNetwork());
        holder.cost.setText(sentMessages.getCost()+" RWF");
        holder.from.setText(sentMessages.getFrom());
        holder.to.setText(sentMessages.getTo());
        /*holder.external_message_id.setText("External Message id: \n"+(sentMessages.getExternal_message_id()));*/
        holder.created_on.setText((sentMessages.getCreated_on()));
        holder.sent_on.setText(sentMessages.getSent_on());
        holder.message.setText(sentMessages.getMessage());
        holder.message_status.setText(sentMessages.getStatus());

        if (sentMessages.getStatus().equals("Errored")){
            holder.message_status.setTextColor(Color.RED);
        }

        if (sentMessages.getStatus().equals("Queued")){
            holder.message_status.setTextColor(Color.BLUE);
        }

        if (sentMessages.getStatus().equals("Delivered")){
            holder.message_status.setTextColor(Color.GREEN);
        }

        if (sentMessages.getStatus().equals("Unsent")){
            holder.message_status.setTextColor(Color.BLACK);
        }

        boolean isExpanded = sentMessagesList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return sentMessagesList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private static final String TAG = "SentMessagesVH";

        ConstraintLayout expandableLayout;
        TextView network, cost, from, to, /*external_message_id,*/ created_on, sent_on, message, message_details, message_status;

        public RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);

            network = itemView.findViewById(R.id.networkTextView);
            cost = itemView.findViewById(R.id.costTextView);
            from = itemView.findViewById(R.id.fromTextView);
            to = itemView.findViewById(R.id.toTextView);
            /*external_message_id= itemView.findViewById(R.id.external_message_id_textview);*/
            created_on = itemView.findViewById(R.id.createdonTextView);
            sent_on = itemView.findViewById(R.id.sentonTextView);
            message = itemView.findViewById(R.id.messageTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            message_details = itemView.findViewById(R.id.message_details);
            message_status = itemView.findViewById(R.id.messagestatus);

            message_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SentMessages sentMessages = sentMessagesList.get(getAdapterPosition());
                    sentMessages.setExpanded(!sentMessages.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });




        }
    }




}
