package com.example.root.intouchsmsapp.Dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.intouchsmsapp.Activities.AgentOrResellerTopUpForm;
import com.example.root.intouchsmsapp.Adapters.MMNetworksAdapter;
import com.example.root.intouchsmsapp.Models.MMNetworks;
import com.example.root.intouchsmsapp.R;
import com.example.root.intouchsmsapp.network.ApiClient;
import com.example.root.intouchsmsapp.network.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class TopUpPostPaid extends AppCompatDialogFragment {


    private View view;

    private DialogClassListener listener;
    private static final String ARG_TOKEN = "argToken";
    private TextInputLayout phone;
    private TextInputLayout amount;
    private String selectedNetwork;
    private ArrayList<MMNetworks> mmNetworksItems;
    private Spinner spinnner_mmnetworks;
    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    public static TopUpPostPaid newInstance(String token) {
        TopUpPostPaid dialog = new TopUpPostPaid();
        Bundle args = new Bundle();
        args.putString(ARG_TOKEN, token);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        //Build a view with the above inflater



        view = inflater.inflate(R.layout.top_up_postpaid, null);

        TextView textView = new TextView(getActivity());
        textView.setText("TopUp Cash");
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(18F);
        textView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        textView.setTextColor(Color.WHITE);

        phone = view.findViewById(R.id.topup_postpaid_phone);
        amount = view.findViewById(R.id.topup_postpaid_amount);

        spinnner_mmnetworks = view.findViewById(R.id.spn_topuppostpaid_payment_methods);

        getMMNetworks(getArguments().getString(ARG_TOKEN));
        builder.setView(view);
        builder.setCustomTitle(textView);
        builder.setNegativeButton("cancel",null);
        builder.setPositiveButton("submit", null);

        final AlertDialog mAlertDialog = builder.create();

        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        //we need to create variables to pass values
                        if (phone.getEditText().getText().toString().isEmpty() || amount.getEditText().getText().toString().isEmpty()){
                            if (phone.getEditText().getText().toString().isEmpty()){
                                phone.setErrorEnabled(true);
                                phone.setError("Please provide phone number");
                                return;
                            }

                            if (amount.getEditText().getText().toString().isEmpty()){
                                amount.setErrorEnabled(true);
                                amount.setError("Please provide amount");
                                return;
                            }
                        } else {
                            mAlertDialog.dismiss();
                            listener.topuppostpaid(selectedNetwork,phone.getEditText().getText().toString(),
                                    Integer.parseInt(amount.getEditText().getText().toString()));
                        }
                    }
                });
            }
        });


        mAlertDialog.show();
        return mAlertDialog;

    }

    private void getMMNetworks(final String token){

        mmNetworksItems = new ArrayList<>();
        Call<ArrayList> call = apiService.getMMNetworks("Token "+token);
        call.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {

                if (!response.isSuccessful()){
                    Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<String> resp = response.body();
                String actualresp = resp.get(0).replace("(", "");
                actualresp = actualresp.replace(")", "");

                try{
                    final JSONObject parentObject = new JSONObject(actualresp);
                    final JSONArray mmnetworks = parentObject.getJSONArray("response");
                    int total= mmnetworks.length();
                    if (total > 0){
                        for(int x = 0; x < total; x++){
                            mmNetworksItems.add(new MMNetworks(mmnetworks.getJSONObject(x).getJSONObject("fields").getString("name")));
                        }

                        spinnner_mmnetworks.setAdapter(new MMNetworksAdapter(getContext(), mmNetworksItems));
                        spinnner_mmnetworks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                MMNetworks mmNetworks = (MMNetworks) adapterView.getItemAtPosition(i);
                                selectedNetwork = mmNetworks.getNetworkname();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }


                } catch (JSONException e){

                }

            }

            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {
                Toast.makeText(getContext(), "Please try again", Toast.LENGTH_SHORT).show();
            }
        });


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
        void topuppostpaid(String selected_network, String phone, int amount);
    }



}
