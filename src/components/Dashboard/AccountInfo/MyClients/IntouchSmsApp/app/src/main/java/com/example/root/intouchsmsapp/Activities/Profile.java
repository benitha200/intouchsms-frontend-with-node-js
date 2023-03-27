package com.example.root.intouchsmsapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
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

import static com.example.root.intouchsmsapp.Activities.LoginActivity.ACCOUNT_ROLE;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.SHARED_PREFS;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.TOKEN;

public class Profile extends AppCompatActivity {


    private String token, accounttype;
    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    private TextView username, accountbalance, phonenumber, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.profiletoolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        username = findViewById(R.id.names);
        accountbalance = findViewById(R.id.balance);
        phonenumber = findViewById(R.id.phone);
        email = findViewById(R.id.email);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        token = prefs.getString(TOKEN, "None");
        accounttype = prefs.getString(ACCOUNT_ROLE, "None");
        getmyprofile(token);
    }


    private void getmyprofile(final String token){
        Call<ArrayList> call = apiService.getmyprofile("Token "+token);
        call.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {

                if (!response.isSuccessful()){
                    Toast.makeText(Profile.this, "Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<String> resp = response.body();
                try{
                    final JSONObject parentObject = new JSONObject(resp.get(0));

                    if (parentObject.getBoolean("success")){
                        
                        String phonenumber_ = parentObject.getJSONObject("response").getString("mobilephone");

                        username.setText(parentObject.getJSONObject("response").getString("username"));

                        phonenumber.setText(phonenumber_.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3"));

                        email.setText(parentObject.getJSONObject("response").getString("email"));

                        Double creditsbalance_ = parentObject.getJSONObject("response").getDouble("creditsbalance");
                        Double accountbalance_ = parentObject.getJSONObject("response").getDouble("accountbalance");

                        if (accounttype.equals("Agent") || accounttype.equals("Reseller")){
                            accountbalance.setText(NumberFormat.getInstance().format(Math.round(creditsbalance_ * 100.0) / 100.0) +" Credits");
                        } else {
                            accountbalance.setText(NumberFormat.getInstance().format(Math.round(accountbalance_ * 100.0) / 100.0) +" RWF");
                        }

                    }

                } catch (JSONException e){
                    Toast.makeText(Profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {
                Toast.makeText(Profile.this, t.toString(), Toast.LENGTH_SHORT).show();

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
        resultIntent.putExtra("result", "from_profile");
        setResult(RESULT_OK, resultIntent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
