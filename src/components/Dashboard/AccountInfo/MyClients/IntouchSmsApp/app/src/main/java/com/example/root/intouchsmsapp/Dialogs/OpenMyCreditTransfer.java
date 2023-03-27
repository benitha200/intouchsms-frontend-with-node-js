package com.example.root.intouchsmsapp.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.intouchsmsapp.Adapters.MMNetworksAdapter;
import com.example.root.intouchsmsapp.Models.MMNetworks;
import com.example.root.intouchsmsapp.R;
import com.example.root.intouchsmsapp.network.ApiClient;
import com.example.root.intouchsmsapp.network.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OpenMyCreditTransfer extends AppCompatDialogFragment {


    private View view;

    private DialogClassListener listener;
    private static final String ARG_TRANSFERNO = "argTransferNo";
    private static final String ARG_DESC = "argDesc";
    private static final String ARG_TRANSFERAMOUNT = "argTransferAmount";
    private static final String ARG_CUSTOMERNO = "argCustomerNo";
    private static final String ARG_NAMES = "argNames";
    private static final String ARG_PHONE = "argPhone";
    private static final String ARG_EMAIL = "argEmail";
    private static final String ARG_ADDRESS = "argAddress";
    private static final String ARG_CREATED = "argCreated";

    private TextInputLayout address;

    private TextView transferno_tv, transferamount_tv, customerno_tv, customername_tv, customerphone_tv, customeremail_tv, createdon_tv,
    desc;

    public static OpenMyCreditTransfer newInstance(String transferno, String desc, String transferamount, String customerno,
                                                   String names, String phone, String email, String address, String created) {
        OpenMyCreditTransfer dialog = new OpenMyCreditTransfer();
        Bundle args = new Bundle();
        args.putString(ARG_TRANSFERNO, transferno);
        args.putString(ARG_DESC, desc);
        args.putString(ARG_TRANSFERAMOUNT, transferamount);
        args.putString(ARG_CUSTOMERNO, customerno);
        args.putString(ARG_NAMES, names);
        args.putString(ARG_PHONE, phone);
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_ADDRESS, address);
        args.putString(ARG_CREATED, created);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        //Build a view with the above inflater



        view = inflater.inflate(R.layout.openmycredittransfer, null);

        TextView textView = new TextView(getActivity());
        textView.setText("My Credit Transfer");
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(18F);
        textView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        textView.setTextColor(Color.WHITE);

        transferno_tv = view.findViewById(R.id.credittransfer_transferno_tv);
        transferno_tv.setText(getArguments().getString(ARG_TRANSFERNO));

        transferamount_tv = view.findViewById(R.id.credittransfer_transferamounttv);
        transferamount_tv.setText(getArguments().getString(ARG_TRANSFERAMOUNT));

        customerno_tv = view.findViewById(R.id.credittransfer_customerno_tv);
        customerno_tv.setText(getArguments().getString(ARG_CUSTOMERNO));

        customername_tv = view.findViewById(R.id.credittransfer_customernametv);
        customername_tv.setText(getArguments().getString(ARG_NAMES));

        customerphone_tv = view.findViewById(R.id.credittransfer_customerphone_tv);
        customerphone_tv.setText(getArguments().getString(ARG_PHONE));

        customeremail_tv = view.findViewById(R.id.credittransfer_customeremailtv);
        customeremail_tv.setText(getArguments().getString(ARG_EMAIL));

        createdon_tv = view.findViewById(R.id.credittransfer_createdon_tv);
        createdon_tv.setText(getArguments().getString(ARG_CREATED));

        desc = view.findViewById(R.id.credittransfer_desc_tv);
        desc.setText(getArguments().getString(ARG_DESC));

        address = view.findViewById(R.id.credit_transfer_customeraddress);
        address.getEditText().setText(getArguments().getString(ARG_ADDRESS));
        address.setEnabled(false);

        builder.setView(view)
                .setCustomTitle(textView)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.show();

    }


    // an overwrite for onAttach method

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogClassListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
            "must implement Dialog Class Listener");
        }
    }


    // so to pass data from our dialog to our activity
    // we need to create an interface

    public interface DialogClassListener{
        void openmycredittransfer();
    }



}
