package com.example.root.intouchsmsapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.root.intouchsmsapp.R;
import com.example.root.intouchsmsapp.ApiData.loginData;
import com.example.root.intouchsmsapp.StartActivity;
import com.example.root.intouchsmsapp.network.ApiClient;
import com.example.root.intouchsmsapp.network.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    public static final int PAGE_SIZE = 25;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TOKEN = "token";
    public static final String FULL_NAMES = "full_names";
    public static final String ACCOUNT_BALANCE = "0.0 credits";
    public static final String ACCOUNT_ROLE = "ROLE";
    public static final String ACCOUNT_PAYMODE = "PAYMODE";
    public static final String UID = "uid";

    private TextInputLayout username_login;
    private TextInputLayout password_login;
    private Button button_login;

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        if (prefs.getString(TOKEN, "None").equals("None")){
            return;
        } else {
            openHomeActivity();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        username_login = findViewById(R.id.login_username);
        password_login = findViewById(R.id.login_password);
        button_login = findViewById(R.id.login_button);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if ((username_login.getEditText().getText().length() == 0) || (password_login.getEditText().getText().length()==0)){
                    if (username_login.getEditText().getText().length()==0){
                        username_login.setErrorEnabled(true);
                        username_login.getEditText().setError("Username*");
                    } else if(password_login.getEditText().getText().length()==0){
                        password_login.setErrorEnabled(true);
                        password_login.getEditText().setError("Password*");
                    }
                } else {
                    username_login.setErrorEnabled(false);
                    password_login.setErrorEnabled(false);
                    login_user(username_login.getEditText().getText().toString(), password_login.getEditText().getText().toString());
                }

            }
        });


    }

    private void login_user(String username, String password){
        StartActivity.showProgressDialog("Logging in Please wait...", LoginActivity.this);
        loginData loginData = new loginData(username, password);
        Call<String> call = apiService.getLoginDetails(loginData);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                username_login.getEditText().setText("");
                password_login.getEditText().setText("");
                String resp = response.body();

                if (!response.isSuccessful()){
                    StartActivity.hideProgressDialog();
                    Toast.makeText(LoginActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {

                    final JSONObject parentObject = new JSONObject(resp);

                    if (parentObject.getString("success").toString().equals("true")){

                        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();

                        if (parentObject.getString("role").equals("Reseller") || parentObject.getString("role").equals("Agent")){

                            editor.putString(TOKEN, parentObject.getString("token"));
                            editor.putString(FULL_NAMES, parentObject.getString("user"));
                            editor.putString(ACCOUNT_ROLE, parentObject.getString("role"));
                            editor.putString(ACCOUNT_PAYMODE, parentObject.getString("paymode"));
                            editor.putString(ACCOUNT_BALANCE, parentObject.getString("balance"));
                            editor.putString(UID, parentObject.getString("uid"));

                            if (parentObject.getString("balance").length()==0){
                                editor.putString(ACCOUNT_BALANCE, "Balance: 0.0 credits");
                            }

                            editor.apply();

                            StartActivity.hideProgressDialog();
                            openHomeActivity();

                        } else {
                            StartActivity.hideProgressDialog();
                            Toast.makeText(LoginActivity.this, "Sorry you need to be an Agent or a Reseller", Toast.LENGTH_SHORT).show();
                        }



                    } else if (parentObject.getString("success").toString().equals("false")){
                        StartActivity.hideProgressDialog();
                        Toast.makeText(LoginActivity.this, parentObject.getJSONObject("errors").getString("username"), Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                StartActivity.hideProgressDialog();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void openSendSmsActivty(){
        Intent intent = new Intent(this, SendSms.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


}
