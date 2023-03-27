package com.example.root.intouchsmsapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.root.intouchsmsapp.Adapters.MMNetworksAdapter;
import com.example.root.intouchsmsapp.Models.MMNetworks;
import com.example.root.intouchsmsapp.R;
import com.example.root.intouchsmsapp.network.ApiClient;
import com.example.root.intouchsmsapp.network.ApiInterface;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.root.intouchsmsapp.Activities.LoginActivity.SHARED_PREFS;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.TOKEN;

public class CustomerTopUpForm extends AppCompatActivity {

    private TextInputLayout topup_phone, topup_amount, unit_price, quantity, tax_excl, vat, tax_incl;
    private ProgressDialog progressDialog;
    private String token;
    private Spinner spinnner_mmnetworks;
    private ArrayList<MMNetworks> mmNetworksItems;
    private String selectedNetwork;
    private Double originalunitprice, taxamount, taxpercent, discount, discountpercent, unitprice;
    private String tax;
    private Button confirmTopup;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final long START_TIME_IN_MILLIS = 300000; //360000
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    private ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_form);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("TopUp Credit");
        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



        spinnner_mmnetworks = findViewById(R.id.spn_topup_payment_methods);
        confirmTopup = findViewById(R.id.btn_submit_topup);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        token = prefs.getString(TOKEN, "None");

        getUserUnitPrice(token);
        getMMNetworks(token);

        topup_phone = findViewById(R.id.top_up_phone);
        topup_phone.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0){
                    topup_phone.setError("Please provide a mobile money phone number");
                }else if ((charSequence.length() > 0) && (charSequence.length() <10)){
                    topup_phone.setError("Phone number should be 10 digits");
                } else if (charSequence.length() == 10){
                    topup_phone.setError("");
                    topup_phone.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        topup_amount = findViewById(R.id.top_up_amount);
        //topup_amount.getEditText().setFilters(new InputFilter[]{ new MinMax("100", "1000000000")});
        topup_amount.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0){
                    quantity.getEditText().setText("0.00");
                    tax_incl.getEditText().setText("0 RWF");
                    tax_excl.getEditText().setText("0.00 RWF");
                    vat.getEditText().setText(Double.toString(Math.round(taxamount * 100.0) / 100.0)+" RWF");
                } else if (charSequence.length() != 0){

                    if (Double.parseDouble(charSequence.toString()) < 100){
                        topup_amount.setError("The Minimum value for this field is 100");
                    } else if (Double.parseDouble(charSequence.toString()) >= 100){
                        topup_amount.setError("");
                        topup_amount.setErrorEnabled(false);
                    }

                    double quantit = Double.parseDouble(charSequence.toString()) / unitprice;
                    double totalpricetax_excl = Double.parseDouble(charSequence.toString()) / 1.18;
                    double taxamount = totalpricetax_excl * 0.18;
                    double totalpricetax_incl = totalpricetax_excl + taxamount;
                    quantity.getEditText().setText(NumberFormat.getInstance().format(Math.round(quantit * 100.0) / 100.0));
                    tax_excl.getEditText().setText(NumberFormat.getInstance().format(Math.round(totalpricetax_excl * 100.0) / 100.0)+" RWF");
                    vat.getEditText().setText(NumberFormat.getInstance().format(Math.round(taxamount * 100.0) / 100.0)+" RWF");
                    tax_incl.getEditText().setText(NumberFormat.getInstance().format(Math.round(totalpricetax_incl * 100.0) / 100.0)+" RWF");


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        unit_price = findViewById(R.id.top_up_unitprice);
        unit_price.setEnabled(false);
        quantity = findViewById(R.id.top_up_quantity);
        quantity.setEnabled(false);
        quantity.getEditText().setText("0.00");
        tax_excl = findViewById(R.id.top_up_price_tax_excl);
        tax_excl.setEnabled(false);
        tax_excl.getEditText().setText("0.00 RWF");
        vat = findViewById(R.id.top_up_vat);
        vat.setEnabled(false);
        tax_incl = findViewById(R.id.top_up_price_tax_incl);
        tax_incl.setEnabled(false);
        tax_incl.getEditText().setText("0 RWF");

        confirmTopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = topup_phone.getEditText().getText().toString();
                String amount = topup_amount.getEditText().getText().toString();

                if (phone.length() == 0 || amount.length()==0){
                    if (phone.length()==0){
                        topup_phone.setError("Please provide a mobile money phone number");
                    }

                    if (amount.length() == 0){
                        topup_amount.setError("This field is required");
                    }

                    return;

                }else if ((phone.length() > 0) && (phone.length() <10)){
                    topup_phone.setError("Phone number should be 10 digits");
                    return;
                }else if (Double.parseDouble(amount) < 100){
                    topup_amount.setError("The Minimum value for this field is 100");
                    return;
                }else if (phone.length() == 10 && Double.parseDouble(amount) >= 100){
                    //phone
                    topup_phone.setError("");
                    topup_phone.setErrorEnabled(false);
                    //amount
                    topup_amount.setError("");
                    topup_amount.setErrorEnabled(false);

                    apptopup(token, topup_amount.getEditText().getText().toString(), Double.toString(unitprice), selectedNetwork, topup_phone.getEditText().getText().toString(), tax);

                }


            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", "from_customer_topup_form");
        setResult(RESULT_OK, resultIntent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void getUserUnitPrice(final String token){
        progressDialog = ProgressDialog.show(this, "Getting User unitprice", "Please wait...", true);
        Call<ArrayList> call = apiService.getUserUnitPrice("Token "+token);
        call.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {

                if (!response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(CustomerTopUpForm.this, "Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.dismiss();
                ArrayList<String> resp = response.body();

                try{
                    final JSONObject parentObject = new JSONObject(resp.get(0));
                    if (parentObject.getBoolean("success")){

                        originalunitprice = parentObject.getJSONObject("response").getDouble("originalunitprice");
                        taxamount = parentObject.getJSONObject("response").getDouble("taxamount");
                        tax = parentObject.getJSONObject("response").getString("tax");
                        taxpercent =parentObject.getJSONObject("response").getDouble("taxpercent");
                        discount = parentObject.getJSONObject("response").getDouble("discount");
                        discountpercent = parentObject.getJSONObject("response").getDouble("discountpercent");
                        unitprice = parentObject.getJSONObject("response").getDouble("unitprice");
                        unit_price.getEditText().setText(Double.toString(parentObject.getJSONObject("response").getDouble("unitprice")) + " RWF");
                        vat.getEditText().setText(Double.toString(Math.round(taxamount * 100.0) / 100.0)+" RWF");

                    }

                } catch (JSONException e){

                }



            }

            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(CustomerTopUpForm.this, t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        progressDialog.dismiss();

    }


    private void getMMNetworks(final String token){
        mmNetworksItems = new ArrayList<>();
        progressDialog = ProgressDialog.show(this, "Getting Mobile Money Networks", "Please wait...", true);
        Call<ArrayList> call = apiService.getMMNetworks("Token "+token);
        call.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {

                if (!response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(CustomerTopUpForm.this, "Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.dismiss();
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

                        spinnner_mmnetworks.setAdapter(new MMNetworksAdapter(CustomerTopUpForm.this, mmNetworksItems));
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

                progressDialog.dismiss();
                Toast.makeText(CustomerTopUpForm.this, "Please try again", Toast.LENGTH_SHORT).show();

            }
        });

        progressDialog.dismiss();

    }

    private void apptopup(final String token, String amount, String unitpricetaxincl, String network, String phone, String tax){
        progressDialog = ProgressDialog.show(this, "Processing Transaction", "Please wait...", true);
        Call<ArrayList> call = apiService.topup("Token "+token,0, amount, unitpricetaxincl, network, phone, tax);

        call.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


                ArrayList<String> resp = response.body();
                //String resp = response.body();

                if (!response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(CustomerTopUpForm.this, "Please try again", Toast.LENGTH_SHORT).show();
                    return;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
                }

                try{

                    final JSONObject parentObject = new JSONObject(resp.get(0));

                    if (parentObject.getBoolean("success")){

                        //Toast.makeText(CustomerTopUpForm.this, parentObject.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        //paymentrequestsimulation(token);
                        gettransactiontopupstatus(parentObject.getString("transactionid"));
                        countDown();

                    } else {

                        progressDialog.dismiss();
                        Toast.makeText(CustomerTopUpForm.this, parentObject.toString(), Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException e){
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(CustomerTopUpForm.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void gettransactiontopupstatus(final String transactionid){

        listenerRegistration = db.collection("transactions").document(transactionid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot.exists()){
                            if (documentSnapshot.getString("transactionId").equals(transactionid)){
                                mTimeLeftInMillis = START_TIME_IN_MILLIS;
                                Toast.makeText(CustomerTopUpForm.this, "Successfull", Toast.LENGTH_SHORT).show();
                                mCountDownTimer.cancel();
                                progressDialog.dismiss();
                                listenerRegistration.remove();
                                onBackPressed();
                            }
                        } else {

                        }
                    }
                });


    }

    private void countDown(){
        progressDialog = ProgressDialog.show(this, "Please approve on your handset", "waiting...", true);
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDown();

            }

            @Override
            public void onFinish() {
                mTimeLeftInMillis = START_TIME_IN_MILLIS;
                progressDialog.dismiss();
                listenerRegistration.remove();
                Toast.makeText(CustomerTopUpForm.this, "Time out", Toast.LENGTH_SHORT).show();
            }
        }.start();



    }

    private void updateCountDown(){
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timerLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        progressDialog.setMessage("Time Left: "+timerLeftFormatted);

    }



                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                

}
