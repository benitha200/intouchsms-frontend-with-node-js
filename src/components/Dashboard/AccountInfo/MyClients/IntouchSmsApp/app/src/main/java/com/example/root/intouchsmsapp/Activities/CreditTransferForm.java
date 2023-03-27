package com.example.root.intouchsmsapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.root.intouchsmsapp.R;
import com.example.root.intouchsmsapp.network.ApiClient;
import com.example.root.intouchsmsapp.network.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.root.intouchsmsapp.Activities.LoginActivity.SHARED_PREFS;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.TOKEN;

public class CreditTransferForm extends AppCompatActivity {

    private int receipientpk = 0;

    private TextInputLayout transferno, description, accountbalance, transferamount, customerno, customername, phone, email, address,
            customerbalance;

    private ProgressDialog progressDialog;
    private String token;
    private Button select_customer,  submit_credittransfer;
    private Double customer_balance;

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_transfer_form);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Add My Credit Transfer");
        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        transferno = findViewById(R.id.credit_transfer_no);
        transferno.getEditText().setText("(New)...");
        transferno.setEnabled(false);
        description = findViewById(R.id.credit_transfer_desc);
        accountbalance = findViewById(R.id.credit_transfer_accountbalance);
        accountbalance.setEnabled(false);
        transferamount = findViewById(R.id.credit_transfer_amount);
        customerno = findViewById(R.id.credit_transfer_customerno);
        customerno.setEnabled(false);
        customername = findViewById(R.id.credit_transfer_customername);
        customername.setEnabled(false);
        phone = findViewById(R.id.credit_transfer_customerphone);
        phone.setEnabled(false);
        email = findViewById(R.id.credit_transfer_customeremail);
        email.setEnabled(false);
        address = findViewById(R.id.credit_transfer_customeraddress);
        address.setEnabled(false);
        customerbalance = findViewById(R.id.credit_transfer_customerbalance);
        customerbalance.setEnabled(false);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        token = prefs.getString(TOKEN, "None");
        getmybalance(token);

        select_customer = findViewById(R.id.button_select_customer);
        select_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMyCustomersActivity();
            }
        });
        submit_credittransfer = findViewById(R.id.btn_submit_creditransfer);
        submit_credittransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (receipientpk==0){

                    Toast.makeText(CreditTransferForm.this, "Please select a customer", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (description.getEditText().getText().toString().length()==0){

                    description.setErrorEnabled(true);
                    description.setError("This field is required");
                    return;

                } else{

                    description.setErrorEnabled(false);
                    description.setError("");

                }

                if (transferamount.getEditText().getText().toString().length()==0){

                    transferamount.setErrorEnabled(true);
                    transferamount.setError("This field is required");
                    return;

                } else if(Double.parseDouble(transferamount.getEditText().getText().toString()) > customer_balance){
                    transferamount.setErrorEnabled(true);
                    transferamount.setError("This Maximum value for this field is "+Double.toString(customer_balance));
                } else{

                    transferamount.setErrorEnabled(false);
                    transferamount.setError("");

                }

                appaddcredittransfer(token,receipientpk, Integer.parseInt(transferamount.getEditText().getText().toString().trim()),
                        description.getEditText().getText().toString());
            }
        });
    }


    private void getmybalance(final String token){
        progressDialog = ProgressDialog.show(this, "Getting Balance", "Please wait...", true);
        Call<ArrayList> call = apiService.getmybalance("Token "+token);
        call.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {

                if (!response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(CreditTransferForm.this, "Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.dismiss();
                ArrayList<String> resp = response.body();
                try{
                    final JSONObject parentObject = new JSONObject(resp.get(0));
                    if (parentObject.getBoolean("success")){
                        Double mybalance = (Double.parseDouble(parentObject.getString("balance")) * 100.0) / 100.0;
                        accountbalance.getEditText().setText(NumberFormat.getInstance().format(Math.round(mybalance * 100.0) / 100.0).toString()+" "+parentObject.getString("symbol"));
                    }

                } catch (JSONException e){
                    Toast.makeText(CreditTransferForm.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CreditTransferForm.this, t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        progressDialog.dismiss();

    }

    public void appaddcredittransfer(final String token, int receipientpk, int transferamount, String desc){
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait...", true);
        Call<ArrayList> call = apiService.addmycredittransfer("Token "+token, receipientpk,
                transferamount,desc);

        call.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


                ArrayList<String> resp = response.body();
                //String resp = response.body();

                if (!response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(CreditTransferForm.this, "Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                try{

                    final JSONObject parentObject = new JSONObject(resp.get(0));

                    if (parentObject.getBoolean("success")){

                        progressDialog.dismiss();
                        onBackPressed();

                    } else {

                        progressDialog.dismiss();
                        Toast.makeText(CreditTransferForm.this, parentObject.getString("msg"), Toast.LENGTH_SHORT).show();

                    }

                }catch (JSONException e){
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(CreditTransferForm.this, t.getMessage(), Toast.LENGTH_SHORT).show();

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
        resultIntent.putExtra("result", "from_creditransfer_form");
        setResult(RESULT_OK, resultIntent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void openMyCustomersActivity(){
        Intent intent = new Intent(this, MyCustomers.class);
        intent.putExtra("from", "credittransferform");
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {

                String result = data.getStringExtra("result");

                if (result.equals("from_mycustomers")){

                    if (Integer.parseInt(data.getStringExtra("recepientpk"))!=0){

                        receipientpk = Integer.parseInt(data.getStringExtra("recepientpk"));
                        customerno.getEditText().setText(data.getStringExtra("customerno"));
                        customername.getEditText().setText(data.getStringExtra("customername"));
                        phone.getEditText().setText(data.getStringExtra("phone"));
                        email.getEditText().setText(data.getStringExtra("email"));
                        address.getEditText().setText(data.getStringExtra("address"));
                        customerbalance.getEditText().setText((NumberFormat.getInstance().format(Math.round(Double.parseDouble(data.getStringExtra("customerbalance")) * 100.0) / 100.0))+" Credits");
                        customer_balance = Double.parseDouble(data.getStringExtra("customerbalance"));
                    }

                }
            }
            if (resultCode == RESULT_CANCELED) {
                /*Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();*/
            }
        }
    }
}
