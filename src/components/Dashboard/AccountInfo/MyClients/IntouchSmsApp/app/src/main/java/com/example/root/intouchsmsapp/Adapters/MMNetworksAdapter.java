package com.example.root.intouchsmsapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.root.intouchsmsapp.Models.MMNetworks;
import com.example.root.intouchsmsapp.Models.senderNames;
import com.example.root.intouchsmsapp.R;

import java.util.ArrayList;

public class MMNetworksAdapter extends ArrayAdapter<MMNetworks> {

    public MMNetworksAdapter(Context context, ArrayList<MMNetworks> mmNetworksArrayList){
        super(context, 0, mmNetworksArrayList);
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
                    R.layout.mmnetworks_spinner, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.tv_mmnetworks);

        MMNetworks currentItem = getItem(position);

        if(currentItem != null){

            textViewName.setText(currentItem.getNetworkname());
//            textViewName.setGravity(Gravity.END);
        }


        return convertView;

    }
}
