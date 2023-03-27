package com.example.root.intouchsmsapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.root.intouchsmsapp.Models.senderNames;
import com.example.root.intouchsmsapp.R;

import java.util.ArrayList;

public class SenderNamesAdapter extends ArrayAdapter<senderNames> {

    public SenderNamesAdapter(Context context, ArrayList<senderNames> senderNamesArrayList){
        super(context, 0, senderNamesArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initview(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initview(position, convertView, parent);
    }


    private View initview(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.sendername_spinner, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.tv_sendername);

        senderNames currentItem = getItem(position);

        if(currentItem != null){

            textViewName.setText(currentItem.getSendernames());
//            textViewName.setGravity(Gravity.END);
        }


        return convertView;

    }
}
