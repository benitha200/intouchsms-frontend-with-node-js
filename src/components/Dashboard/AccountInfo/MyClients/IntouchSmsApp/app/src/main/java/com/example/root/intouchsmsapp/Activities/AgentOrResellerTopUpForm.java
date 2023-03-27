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
import com.example.root.intouchsmsapp.GlobalVariables.Global;
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

public class AgentOrResellerTopUpForm extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private String token;
    private Spinner spinnner_mmnetworks;
    private ArrayList<MMNetworks> mmNetworksItems;
    private String selectedNetwork;
    private Button submit;
    private static final long START_TIME_IN_MILLIS = 300000; //360000
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private Button selectPublishedPackages;
    private Double minimumprice, maximumprice, minimumcr, maximumcr;
    private double unit_price;
    private TextInputLayout packagename, mincredits, maxcredits, minprice, maxprice, unitprice, quantity, total_price_tax_excl, vat, total_price_tax_incl, amount, phone;

    private int pk = 0;
    private static String STATUS_CODE = "0000";

    private ListenerRegistration listenerRegistration;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_or_reseller_top_up_form);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("TopUp Credit");
        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        spinnner_mmnetworks = findViewById(R.id.agent_reseller_spn_topup_payment_methods);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        token = prefs.getString(TOKEN, "None");

        getMMNetworks(token);

        selectPublishedPackages = findViewById(R.id.agent_reseller_select_package);
        selectPublishedPackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPublishedPackagesActivity();
            }
        });

        packagename = findViewById(R.id.agent_reseller_package);
        packagename.setEnabled(false);
        mincredits = findViewById(R.id.agent_reseller_creditrange_from);
        mincredits.getEditText().setText("0.00");
        mincredits.setEnabled(false);
        maxcredits = findViewById(R.id.agent_reseller_creditrange_to);
        maxcredits.setEnabled(false);
        minprice = findViewById(R.id.agent_reseller_pricerange_from);
        minprice.getEditText().setText("0.00 RWF");
        minprice.setEnabled(false);
        maxprice = findViewById(R.id.agent_reseller_pricerange_to);
        maxprice.setEnabled(false);
        unitprice = findViewById(R.id.agent_reseller_top_up_unitprice);
        unitprice.getEditText().setText("0.00 RWF");
        unitprice.setEnabled(false);
        quantity = findViewById(R.id.agent_reseller_top_up_quantity);
        quantity.getEditText().setText("0.00");
        quantity.setEnabled(false);
        total_price_tax_excl = findViewById(R.id.agent_reseller_top_up_price_tax_excl);
        total_price_tax_excl.getEditText().setText("0.00 RWF");
        total_price_tax_excl.setEnabled(false);
        vat = findViewById(R.id.agent_reseller_top_up_vat);
        vat.getEditText().setText("0.00 RWF");
        vat.setEnabled(false);
        total_price_tax_incl = findViewById(R.id.agent_reseller_top_up_price_tax_incl);
        total_price_tax_incl.getEditText().setText("0 RWF");
        total_price_tax_incl.setEnabled(false);

        amount = findViewById(R.id.agent_reseller_top_up_amount);
        amount.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (pk != 0){

                    if (charSequence.length() == 0){
                        quantity.getEditText().setText("0.00");
                        total_price_tax_incl.getEditText().setText("0 RWF");
                        total_price_tax_excl.getEditText().setText("0.00 RWF");
                        vat.getEditText().setText("0.00 RWF");
                    } else if (charSequence.length() != 0){

                        if (maximumprice.isNaN()){
                            if (Double.parseDouble(charSequence.toString()) < minimumprice){
                                amount.setError("The Minimum value for this field is " + Integer.toString(minimumprice.intValue()));

                                double quantit = Double.parseDouble(charSequence.toString()) / unit_price;
                                double totalpricetax_excl = Double.parseDouble(charSequence.toString()) / 1.18;
                                double taxamount = totalpricetax_excl * 0.18;
                                double totalpricetax_incl = totalpricetax_excl + taxamount;
                                double vat_ = Double.parseDouble(charSequence.toString()) - totalpricetax_excl;
                                quantity.getEditText().setText(NumberFormat.getInstance().format(Math.round(quantit * 100.0) / 100.0));
                                quantity.setError("The Minimum value for this field is "+ Integer.toString(minimumcr.intValue()));
                                total_price_tax_excl.getEditText().setText(NumberFormat.getInstance().format(Math.round(totalpricetax_excl * 100.0) / 100.0)+" RWF");
                                vat.getEditText().setText(NumberFormat.getInstance().format(Math.round(vat_ * 100.0) / 100.0)+" RWF");
                                total_price_tax_incl.getEditText().setText(NumberFormat.getInstance().format(Math.round(totalpricetax_incl * 100.0) / 100.0)+" RWF");

                            } else if (Double.parseDouble(charSequence.toString()) >= minimumprice){
                                double quantit = Double.parseDouble(charSequence.toString()) / unit_price;
                                double totalpricetax_excl = Double.parseDouble(charSequence.toString()) / 1.18;
                                double taxamount = totalpricetax_excl * 0.18;
                                double totalpricetax_incl = totalpricetax_excl + taxamount;
                                double vat_ = Double.parseDouble(charSequence.toString()) - totalpricetax_excl;
                                quantity.getEditText().setText(NumberFormat.getInstance().format(Math.round(quantit * 100.0) / 100.0));

                                quantity.setErrorEnabled(false);
                                quantity.setError("");

                                total_price_tax_excl.getEditText().setText(NumberFormat.getInstance().format(Math.round(totalpricetax_excl * 100.0) / 100.0)+" RWF");
                                vat.getEditText().setText(NumberFormat.getInstance().format(Math.round(vat_ * 100.0) / 100.0)+" RWF");
                                total_price_tax_incl.getEditText().setText(NumberFormat.getInstance().format(Math.round(totalpricetax_incl * 100.0) / 100.0)+" RWF");

                                amount.setErrorEnabled(false);
                                amount.setError("");
                            }
                        } else {

                            if (Double.parseDouble(charSequence.toString()) < minimumprice){
                                amount.setError("The Minimum value for this field is " + Integer.toString(minimumprice.intValue()));

                                double quantit = Double.parseDouble(charSequence.toString()) / unit_price;
                                double totalpricetax_excl = Double.parseDouble(charSequence.toString()) / 1.18;
                                double taxamount = totalpricetax_excl * 0.18;
                                double totalpricetax_incl = totalpricetax_excl + taxamount;
                                double vat_ = Double.parseDouble(charSequence.toString()) - totalpricetax_excl;
                                quantity.getEditText().setText(NumberFormat.getInstance().format(Math.round(quantit * 100.0) / 100.0));
                                quantity.setError("The Minimum value for this field is "+ Integer.toString(minimumcr.intValue()));
                                total_price_tax_excl.getEditText().setText(NumberFormat.getInstance().format(Math.round(totalpricetax_excl * 100.0) / 100.0)+" RWF");
                                vat.getEditText().setText(NumberFormat.getInstance().format(Math.round(vat_ * 100.0) / 100.0)+" RWF");
                                total_price_tax_incl.getEditText().setText(NumberFormat.getInstance().format(Math.round(totalpricetax_incl * 100.0) / 100.0)+" RWF");

                            } else if (Double.parseDouble(charSequence.toString()) > maximumprice){

                                double quantit = Double.parseDouble(charSequence.toString()) / unit_price;
                                double totalpricetax_excl = Double.parseDouble(charSequence.toString()) / 1.18;
                                double taxamount = totalpricetax_excl * 0.18;
                                double totalpricetax_incl = totalpricetax_excl + taxamount;
                                double vat_ = Double.parseDouble(charSequence.toString()) - totalpricetax_excl;
                                quantity.getEditText().setText(NumberFormat.getInstance().format(Math.round(quantit * 100.0) / 100.0));
                                quantity.setError("The Maximum value for this field is "+Integer.toString(maximumcr.intValue()));
                                total_price_tax_excl.getEditText().setText(NumberFormat.getInstance().format(Math.round(totalpricetax_excl * 100.0) / 100.0)+" RWF");
                                vat.getEditText().setText(NumberFormat.getInstance().format(Math.round(vat_ * 100.0) / 100.0)+" RWF");
                                total_price_tax_incl.getEditText().setText(NumberFormat.getInstance().format(Math.round(totalpricetax_incl * 100.0) / 100.0)+" RWF");

                                amount.setError("The Maximum value for this field is " + Integer.toString(maximumprice.intValue()));

                            } else if (Double.parseDouble(charSequence.toString()) >= minimumprice && Double.parseDouble(charSequence.toString()) <= maximumprice){
                                amount.setError("");
                                amount.setErrorEnabled(false);
                                double quantit = Double.parseDouble(charSequence.toString()) / unit_price;
                                double totalpricetax_excl = Double.parseDouble(charSequence.toString()) / 1.18;
                                double taxamount = totalpricetax_excl * 0.18;
                                double totalpricetax_incl = totalpricetax_excl + taxamount;
                                double vat_ = Double.parseDouble(charSequence.toString()) - totalpricetax_excl;
                                quantity.getEditText().setText(NumberFormat.getInstance().format(Math.round(quantit * 100.0) / 100.0));
                                quantity.setError("");
                                quantity.setErrorEnabled(false);
                                total_price_tax_excl.getEditText().setText(NumberFormat.getInstance().format(Math.round(totalpricetax_excl * 100.0) / 100.0)+" RWF");
                                vat.getEditText().setText(NumberFormat.getInstance().format(Math.round(vat_ * 100.0) / 100.0)+" RWF");
                                total_price_tax_incl.getEditText().setText(NumberFormat.getInstance().format(Math.round(totalpricetax_incl * 100.0) / 100.0)+" RWF");

                            }

                        }



                    }

                } else {
                    amount.setError("Please select package first");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        phone = findViewById(R.id.agent_reseller_top_up_phone);
        phone.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0){
                    phone.setError("Please provide a mobile money phone number");
                }else if ((charSequence.length() > 0) && (charSequence.length() <10)){
                    phone.setError("Phone number should be 10 digits");
                } else if (charSequence.length() == 10){
                    phone.setError("");
                    phone.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        submit = findViewById(R.id.agent_reseller_btn_submit_topup);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String topup_phone = phone.getEditText().getText().toString();
                String topup_amount = amount.getEditText().getText().toString();
                if (pk == 0){
                    Toast.makeText(AgentOrResellerTopUpForm.this, "Please select a package", Toast.LENGTH_SHORT).show();
                    return;
                } else if (topup_phone.length() == 0 || topup_amount.length()==0){

                    if (topup_phone.length()==0){
                        phone.setError("Please provide a mobile money phone number");
                    }

                    if (topup_amount.length() == 0){
                        amount.setError("This field is required");
                    }

                    return;

                }else if ((topup_phone.length() > 0) && (topup_phone.length() <10)){
                    phone.setError("Phone number should be 10 digits");
                    return;

                } else if (maximumprice.isNaN()){

                    if (Double.parseDouble(topup_amount) < minimumprice ){
                        if (Double.parseDouble(topup_amount) < minimumprice){
                            amount.setError("The Minimum value for this field is " + Integer.toString(minimumprice.intValue()));
                        }

                        return;

                    } else if( (topup_phone.length() == 10) && (Double.parseDouble(topup_amount) >= minimumprice )){

                        apptopup(token, pk, amount.getEditText().getText().toString(), Double.toString(unit_price), selectedNetwork, phone.getEditText().getText().toString(),
                                vat.getEditText().getText().toString());

                    }


                } else if (!(maximumprice.isNaN())){

                     if (Double.parseDouble(topup_amount) < minimumprice || Double.parseDouble(topup_amount) > maximumprice){
                        if (Double.parseDouble(topup_amount) < minimumprice){
                            amount.setError("The Minimum value for this field is " + Integer.toString(minimumprice.intValue()));
                        } else if (Double.parseDouble(topup_amount) > maximumprice){
                            amount.setError("The Maximum value for this field is " + Integer.toString(maximumprice.intValue()));
                        }

                        return;

                    } else if( (topup_phone.length() == 10) && (Double.parseDouble(topup_amount) >= minimumprice && Double.parseDouble(topup_amount) <= maximumprice)){

                        apptopup(token, pk, amount.getEditText().getText().toString(), Double.toString(unit_price), selectedNetwork, phone.getEditText().getText().toString(),
                                vat.getEditText().getText().toString());

                    }
                }
            }
        });

    }

    private void apptopup(final String token, int packagepk, String amount, String unitpricetaxincl, String network, String phone, String tax){
        progressDialog = ProgressDialog.show(this, "Processing Transaction", "Please wait...", true);
        Call<ArrayList> call = apiService.topup("Token "+token,packagepk, amount, unitpricetaxincl, network, phone, tax);

        call.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


                ArrayList<String> resp = response.body();
                //String resp = response.body();

                if (!response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(AgentOrResellerTopUpForm.this, "Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                try{

                    final JSONObject parentObject = new JSONObject(resp.get(0));

                    if (parentObject.getBoolean("success")){

                        progressDialog.dismiss();

                        gettransactiontopupstatus(parentObject.getString("transactionid"));
                        countDown();

                    } else {

                        progressDialog.dismiss();
                        Toast.makeText(AgentOrResellerTopUpForm.this, parentObject.getString("msg"), Toast.LENGTH_SHORT).show();

                    }

                }catch (JSONException e){
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(AgentOrResellerTopUpForm.this, t.getMessage(), Toast.LENGTH_SHORT).show();

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
                                if (documentSnapshot.getString("responseCode").equals("01")){
                                    mTimeLeftInMillis = START_TIME_IN_MILLIS;
                                    Toast.makeText(AgentOrResellerTopUpForm.this, "Successfull", Toast.LENGTH_SHORT).show();
                                    mCountDownTimer.cancel();
                                    progressDialog.dismiss();
                                    listenerRegistration.remove();
                                    onBackPressed();
                                }else {
                                    mCountDownTimer.cancel();
                                    progressDialog.dismiss();
                                    listenerRegistration.remove();
                                    Toast.makeText(AgentOrResellerTopUpForm.this, "Failed", Toast.LENGTH_SHORT).show();
                                }

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
                Toast.makeText(AgentOrResellerTopUpForm.this, "Time out", Toast.LENGTH_SHORT).show();
            }
        }.start();



    }

    private void updateCountDown(){
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timerLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        progressDialog.setMessage("Time Left: "+timerLeftFormatted);

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
        resultIntent.putExtra("result", "from_agent_reseller_form");
        setResult(RESULT_OK, resultIntent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
                    Toast.makeText(AgentOrResellerTopUpForm.this, "Please try again", Toast.LENGTH_SHORT).show();
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

                        spinnner_mmnetworks.setAdapter(new MMNetworksAdapter(AgentOrResellerTopUpForm.this, mmNetworksItems));
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
                Toast.makeText(AgentOrResellerTopUpForm.this, "Please try again", Toast.LENGTH_SHORT).show();

            }
        });

        progressDialog.dismiss();

    }

    public void openPublishedPackagesActivity(){
        Intent intent = new Intent(this, PublishedPackages.class);
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {

                String result = data.getStringExtra("result");

                if (result.equals("from_publishedpackages")){

                    if (Integer.parseInt(data.getStringExtra("pk"))!=0){

                        pk = Integer.parseInt(data.getStringExtra("pk"));
                        packagename.getEditText().setText(data.getStringExtra("packagename"));

                        minimumcr = Double.parseDouble(data.getStringExtra("mincredits"));
                        mincredits.getEditText().setText(NumberFormat.getInstance().format(Math.round(Double.parseDouble(data.getStringExtra("mincredits")) * 100.0) / 100.0));

                        minimumprice = Double.parseDouble(data.getStringExtra("minprice"));
                        minprice.getEditText().setText(NumberFormat.getInstance().format(Math.round(Double.parseDouble(data.getStringExtra("minprice")) * 100.0) / 100.0)+" RWF");

                        unit_price = Double.parseDouble(data.getStringExtra("unitprice"));
                        unitprice.getEditText().setText(NumberFormat.getInstance().format(Math.round(Double.parseDouble(data.getStringExtra("unitprice")) * 100.0) / 100.0)+" RWF");

                        amount.getEditText().setText("");
                        if (data.getBooleanExtra("maximumvalues_null", false)){
                            maximumcr = Double.NaN;
                            maximumprice = Double.NaN;
                            maxcredits.getEditText().setText("");
                            maxprice.getEditText().setText("");
                        } else {
                            maximumcr = Double.parseDouble(data.getStringExtra("maxcredits"));
                            maxcredits.getEditText().setText(NumberFormat.getInstance().format(Math.round(Double.parseDouble(data.getStringExtra("maxcredits")) * 100.0) / 100.0));
                            maximumprice = Double.parseDouble(data.getStringExtra("maxprice"));
                            maxprice.getEditText().setText(NumberFormat.getInstance().format(Math.round(Double.parseDouble(data.getStringExtra("maxprice")) * 100.0) / 100.0)+" RWF");
                        }


                    }

                }
            }
            if (resultCode == RESULT_CANCELED) {
                /*Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();*/
            }
        }
    }


}
