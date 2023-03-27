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

import static com.example.root.intouchsmsapp.Activities.LoginActivity.SHARED_PREFS;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.TOKEN;

public class EditMyCustomer extends AppCompatActivity {

    private Button saveclient;
    private TextInputLayout customerno, names, username,status,
            mobilephone, email, officephone, password1, password2,
            address, bal, commission,desc,created, lastlogin;

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    private String token;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_customer);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("View and Modify");
        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        token = prefs.getString(TOKEN, "None");

        customerno = findViewById(R.id.client_number);
        names = findViewById(R.id.client_names);
        username = findViewById(R.id.client_username);
        status = findViewById(R.id.client_status);
        mobilephone = findViewById(R.id.client_mobilephone);
        email = findViewById(R.id.client_email);
        officephone = findViewById(R.id.office_phone);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        address = findViewById(R.id.address);
        bal = findViewById(R.id.client_accountbalance);
        commission = findViewById(R.id.client_commission);
        desc = findViewById(R.id.description);
        created = findViewById(R.id.client_created);
        lastlogin = findViewById(R.id.client_lastlogin);
        saveclient = findViewById(R.id.btn_saveclient);

        customerno.getEditText().setText(getIntent().getStringExtra("customerno"));
        customerno.setEnabled(false);
        names.getEditText().setText(getIntent().getStringExtra("customername"));
        username.getEditText().setText(getIntent().getStringExtra("customerusername"));
        status.getEditText().setText(getIntent().getStringExtra("customerstatus"));
        status.setEnabled(false);
        mobilephone.getEditText().setText(getIntent().getStringExtra("phone"));
        email.getEditText().setText(getIntent().getStringExtra("email"));
        officephone.getEditText().setText(getIntent().getStringExtra("officephone"));
        password1.getEditText().setText(getIntent().getStringExtra("password"));
        password2.getEditText().setText(getIntent().getStringExtra("password"));
        address.getEditText().setText(getIntent().getStringExtra("address"));
        //NumberFormat.getInstance().format(Math.round(max_credits * 100.0) / 100.0).toString()
        bal.getEditText().setText(NumberFormat.getInstance().format(Math.round(Float.parseFloat(getIntent().getStringExtra("customerbalance")) * 100.0) / 100.0).toString() + " " + "Cr.");
        bal.setEnabled(false);
        commission.getEditText().setText(getIntent().getStringExtra("clientcommission") + " RWF");
        commission.setEnabled(false);
        created.getEditText().setText(getIntent().getStringExtra("created"));
        created.setEnabled(false);
        lastlogin.getEditText().setText(getIntent().getStringExtra("lastlogin"));
        lastlogin.setEnabled(false);

        if (!getIntent().getStringExtra("clientaddr").isEmpty()){
            address.getEditText().setText(getIntent().getStringExtra("clientaddr"));
        }

        if (!getIntent().getStringExtra("clientdesc").isEmpty()){
            desc.getEditText().setText(getIntent().getStringExtra("clientdesc"));
        }

        saveclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (names.getEditText().getText().toString().isEmpty() || username.getEditText().getText().toString().isEmpty() ||
                        mobilephone.getEditText().getText().toString().isEmpty() || password1.getEditText().getText().toString().isEmpty() ||
                        password2.getEditText().getText().toString().isEmpty()){

                    if (names.getEditText().getText().toString().isEmpty()){
                        names.setErrorEnabled(true);
                        names.setError("Please provide names");
                        return;
                    }

                    if (username.getEditText().getText().toString().isEmpty()){
                        username.setErrorEnabled(true);
                        username.setError("Please provide username");
                        return;
                    }

                    if (mobilephone.getEditText().getText().toString().isEmpty()){
                        mobilephone.setErrorEnabled(true);
                        mobilephone.setError("Please provide mobilephone");
                        return;
                    }

                    if (password1.getEditText().getText().toString().isEmpty()){
                        password1.setErrorEnabled(true);
                        password1.setError("Required");
                        return;
                    }

                    if (password2.getEditText().getText().toString().isEmpty()){
                        password2.setErrorEnabled(true);
                        password2.setError("Required");
                        return;
                    }
                } else {
                    if (!(password1.getEditText().getText().toString().equals(password2.getEditText().getText().toString()))){
                        password1.setErrorEnabled(true);
                        password1.setError("Password Should Match");
                        password2.setErrorEnabled(true);
                        password2.setError("Password Should Match");
                        return;
                    } else {
                        appeditmyclient(token, getIntent().getIntExtra("pk", 0), names.getEditText().getText().toString(), username.getEditText().getText().toString(),
                                mobilephone.getEditText().getText().toString(), email.getEditText().getText().toString(),
                                officephone.getEditText().getText().toString(), password1.getEditText().getText().toString(),
                                address.getEditText().getText().toString(), desc.getEditText().getText().toString());

                    }
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
        resultIntent.putExtra("result", "from_myclient_form");
        setResult(RESULT_OK, resultIntent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void appeditmyclient(final String token, int pk, String names, String username, String mobilephone, String email, String officephone,
                               String password, String address, String desc){
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait...", true);
        Call<ArrayList> call = apiService.editmyclient("Token "+token, pk, username, names, email, password, mobilephone,
                officephone, address, desc);

        call.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


                ArrayList<String> resp = response.body();
                //String resp = response.body();

                if (!response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(EditMyCustomer.this, "Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                try{

                    final JSONObject parentObject = new JSONObject(resp.get(0));

                    if (parentObject.getBoolean("success")){

                        progressDialog.dismiss();
                        onBackPressed();

                    } else {

                        progressDialog.dismiss();
                        Toast.makeText(EditMyCustomer.this, parentObject.getString("msg"), Toast.LENGTH_SHORT).show();

                    }

                }catch (JSONException e){
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(EditMyCustomer.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
