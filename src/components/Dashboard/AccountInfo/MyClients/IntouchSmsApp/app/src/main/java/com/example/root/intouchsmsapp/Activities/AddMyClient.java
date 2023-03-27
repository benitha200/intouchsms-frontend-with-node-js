package com.example.root.intouchsmsapp.Activities;

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
import com.example.root.intouchsmsapp.StartActivity;
import com.example.root.intouchsmsapp.network.ApiClient;
import com.example.root.intouchsmsapp.network.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.root.intouchsmsapp.Activities.LoginActivity.SHARED_PREFS;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.TOKEN;

public class AddMyClient extends AppCompatActivity {

    private Button saveclient;
    private TextInputLayout names, username, mobilephone, email, officephone, password1, password2,
        address, desc;

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_client);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Add My Client");
        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        token = prefs.getString(TOKEN, "None");

        names = findViewById(R.id.client_names);
        username = findViewById(R.id.client_username);
        mobilephone = findViewById(R.id.client_mobilephone);
        email = findViewById(R.id.client_email);
        officephone = findViewById(R.id.office_phone);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        address = findViewById(R.id.address);
        desc = findViewById(R.id.description);

        saveclient = findViewById(R.id.btn_saveclient);
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
                        appaddmyclient(token, names.getEditText().getText().toString(), username.getEditText().getText().toString(),
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

    public void appaddmyclient(final String token, String names, String username, String mobilephone, String email, String officephone,
                               String password, String address, String desc){
        StartActivity.showProgressDialog("Saving, Please wait", AddMyClient.this);
        Call<ArrayList> call = apiService.addmyclient("Token "+token, username, names, email, password, mobilephone,
                officephone, address, desc);

        call.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


                ArrayList<String> resp = response.body();
                //String resp = response.body();

                if (!response.isSuccessful()){
                    StartActivity.hideProgressDialog();
                    Toast.makeText(AddMyClient.this, "Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                try{

                    final JSONObject parentObject = new JSONObject(resp.get(0));

                    if (parentObject.getBoolean("success")){
                        StartActivity.hideProgressDialog();
                        onBackPressed();

                    } else {

                        StartActivity.hideProgressDialog();
                        Toast.makeText(AddMyClient.this, parentObject.getString("msg"), Toast.LENGTH_SHORT).show();

                    }

                }catch (JSONException e){
                    StartActivity.hideProgressDialog();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {

                StartActivity.hideProgressDialog();
                Toast.makeText(AddMyClient.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
