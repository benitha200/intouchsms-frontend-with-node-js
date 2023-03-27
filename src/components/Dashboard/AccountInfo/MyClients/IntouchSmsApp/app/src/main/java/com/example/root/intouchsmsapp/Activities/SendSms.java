package com.example.root.intouchsmsapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.intouchsmsapp.CheckConnection.ConnectivityReceiver;
import com.example.root.intouchsmsapp.CheckConnection.MyApplication;
import com.example.root.intouchsmsapp.Dialogs.TopUpPostPaid;
import com.example.root.intouchsmsapp.FireBaseClasses.User;
import com.example.root.intouchsmsapp.Fragments.MessagingFragment;
import com.example.root.intouchsmsapp.Models.TransactionIds;
import com.example.root.intouchsmsapp.R;
import com.example.root.intouchsmsapp.network.ApiClient;
import com.example.root.intouchsmsapp.network.ApiInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.root.intouchsmsapp.Activities.LoginActivity.ACCOUNT_BALANCE;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.ACCOUNT_PAYMODE;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.ACCOUNT_ROLE;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.FULL_NAMES;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.SHARED_PREFS;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.TOKEN;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.UID;

public class SendSms extends AppCompatActivity implements TopUpPostPaid.DialogClassListener, ConnectivityReceiver.ConnectivityReceiverListener{

    private String uid;
    private TextView main_title, toolbar_bl;
    private String token;
    Boolean in = Boolean.TRUE;
    private ProgressDialog progressDialog;
    private TextInputLayout msg;
    private NavigationView navigationView;
    private String accounttype;
    private String paymode;
    private Button global_topup;
    private String bl;

    private static final long START_TIME_IN_MILLIS = 300000; //360000
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);





    private static final String KEY_TOKEN = "token";
    private static final String KEY_TRANSACTIONID= "transactionId";
    private String total;


    private SharedPreferences prefs;

    //firebase

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListenerRegistration listenerRegistration, listenerRegistration_balance;

    private boolean balance_from_firebase = false;

    @Override
    protected void onStart() {
        super.onStart();

        checkConnection();

        uid = prefs.getString(UID, "None");
        listenerRegistration_balance = db.collection("Balances").document(uid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                        if (documentSnapshot.exists()){
                            balance_from_firebase = true;
                            paymode = prefs.getString(ACCOUNT_PAYMODE, "None");
                            changeBal(paymode, documentSnapshot.getString("balance"));
                        }
                    }
                });


    }

    @Override
    protected void onStop() {
        super.onStop();
        listenerRegistration_balance.remove();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        global_topup = findViewById(R.id.btn_global_topup);

        prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        token = prefs.getString(TOKEN, "None");

        accounttype = prefs.getString(ACCOUNT_ROLE, "None");
        paymode = prefs.getString(ACCOUNT_PAYMODE, "None");
        bl = prefs.getString(ACCOUNT_BALANCE, "None");
        bl = bl.replace("Balance: ", "");

        uid = prefs.getString(UID, "None");

        main_title = findViewById(R.id.main_title);
        main_title.setText("SEND SMS");

        toolbar_bl = findViewById(R.id.toolbar_bl);

        global_topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTopUpForm(accounttype);
            }
        });

        if (paymode.equals("PostPaid")){
            global_topup.setVisibility(View.GONE);
        }

        if(savedInstanceState==null) {
            // setting the one selected by default
            main_title = findViewById(R.id.main_title);
            main_title.setText("SEND SMS");

            if (paymode.equals("PostPaid")){
                global_topup.setVisibility(View.GONE);

            }


            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MessagingFragment()).commit();
        }

        
    }


    private void changeBal(String paymode, String bal){

        toolbar_bl = findViewById(R.id.toolbar_bl);
        accounttype = prefs.getString(ACCOUNT_ROLE, "None");

        if (!paymode.equals("PostPaid")){


            if (accounttype.equals("Reseller") || accounttype.equals("Agent")){
                toolbar_bl.setText("Bal: "+bal+" Cr.");
            } else {
                toolbar_bl.setText("Bal: "+bal+" RWF");
            }




        } else{

            if (accounttype.equals("Reseller") || accounttype.equals("Agent")){
                toolbar_bl.setText("");
            } else {
                toolbar_bl.setText("");
            }

        }
    }


    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Please check your internet connection!";
            color = Color.RED;
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }



        /*Snackbar snackbar = Snackbar.make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }


    @Override
    public void onBackPressed() {

        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", "from_profile");
        setResult(RESULT_OK, resultIntent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

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


    public void openprofileActivity(){
        Intent intent = new Intent(this, Profile.class);
        startActivityForResult(intent, 9);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    public void openMessageLogSummaryActivty(){
        Intent intent = new Intent(this, MessageLogSummary.class);
        startActivityForResult(intent, 6);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openTransactionsActivity(){
        Intent intent = new Intent(this, Transactions.class);
        startActivityForResult(intent, 2);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public void openMyClientsActivity(){
        Intent intent = new Intent(this, MyCustomers.class);
        intent.putExtra("from", "sendmessage");
        startActivityForResult(intent, 15);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openCreditTransfersActivity(){
        Intent intent = new Intent(this, MyCreditTransfers.class);
        startActivityForResult(intent, 4);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {

                String result = data.getStringExtra("result");

                if (result.equals("from_sent_sms")){

                    navigationView.setCheckedItem(R.id.nav_messaging);
                }
            }
            if (resultCode == RESULT_CANCELED) {
                /*Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();*/
            }
        }

        if (requestCode == 2) {

            if (resultCode == RESULT_OK) {

                String result = data.getStringExtra("result");

                if (result.equals("from_topups")){

                    navigationView.setCheckedItem(R.id.nav_messaging);
                }
            }
            if (resultCode == RESULT_CANCELED) {
                /*Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();*/
            }
        }

        if (requestCode == 3) {

            if (resultCode == RESULT_OK) {

                String result = data.getStringExtra("result");

                if (result.equals("from_customer_topup_form")){

                    //Toast.makeText(this, "From customer top up form", Toast.LENGTH_SHORT).show();
                }
            }
            if (resultCode == RESULT_CANCELED) {
                /*Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();*/
            }
        }

        if (requestCode == 5) {

            if (resultCode == RESULT_OK) {

                String result = data.getStringExtra("result");

                if (result.equals("from_agent_reseller_form")){

                    //Toast.makeText(this, "From Agent or Reseller top up form", Toast.LENGTH_SHORT).show();
                }
            }
            if (resultCode == RESULT_CANCELED) {
                /*Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();*/
            }
        }

        if (requestCode == 4) {

            if (resultCode == RESULT_OK) {

                String result = data.getStringExtra("result");

                if (result.equals("from_credittransfers")){

                    navigationView.setCheckedItem(R.id.nav_messaging);
                }
            }
            if (resultCode == RESULT_CANCELED) {
                /*Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();*/
            }
        }

        if (requestCode == 15) {

            if (resultCode == RESULT_OK) {

                String result = data.getStringExtra("result");

                if (result.equals("from_clients")){

                    navigationView.setCheckedItem(R.id.nav_messaging);
                }
            }
            if (resultCode == RESULT_CANCELED) {
                /*Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();*/
            }
        }

        if (requestCode == 6) {

            if (resultCode == RESULT_OK) {

                String result = data.getStringExtra("result");

                if (result.equals("from_message_log_summary")){

                    navigationView.setCheckedItem(R.id.nav_messaging);
                }
            }
            if (resultCode == RESULT_CANCELED) {
                /*Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();*/
            }
        }

        if (requestCode == 9) {

            if (resultCode == RESULT_OK) {

                String result = data.getStringExtra("result");

                if (result.equals("from_profile")){

                    navigationView.setCheckedItem(R.id.nav_messaging);
                }
            }
            if (resultCode == RESULT_CANCELED) {
                /*Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();*/
            }
        }

    }

    public void openTopUpForm(String role){

        if (role.equals("Customer")){

            Intent intent = new Intent(this, CustomerTopUpForm.class);
            startActivityForResult(intent, 3);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        } else if (role.equals("Agent") || role.equals("Reseller")){

            if (paymode.equals("PrePaid")){
                Intent intent = new Intent(this, AgentOrResellerTopUpForm.class);
                startActivityForResult(intent, 5);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            } else if (paymode.equals("PostPaid")){
                //for post paid
                TopUpPostPaid topUpPostPaid = TopUpPostPaid.newInstance(token);
                topUpPostPaid.setCancelable(false);
                topUpPostPaid.show(getSupportFragmentManager(), "dialog topuppostpaid");
            }


        } else {
            Toast.makeText(this, "To Topup you have to be a Customer,Agent or Reseller.", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void topuppostpaid(String selected_network, String phone, int amount) {
        if (phone.isEmpty()){
            Toast.makeText(this, "Please provide phone number", Toast.LENGTH_SHORT).show();
            //return;
        } else if (Integer.toString(amount).length() == 0){
            Toast.makeText(this, "Please provide amount", Toast.LENGTH_SHORT).show();
            //return;
        } else {
            appecashtopup(token, Integer.toString(amount), selected_network, phone);
        }
    }


    private void appecashtopup(final String token, String amount, String network, String phone){
        progressDialog = ProgressDialog.show(this, "Processing Transaction", "Please wait...", true);
        Call<ArrayList> call = apiService.ecashtopup("Token "+token, amount, network, phone);

        call.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


                ArrayList<String> resp = response.body();
                //String resp = response.body();

                if (!response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(SendSms.this, "Please try again", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(SendSms.this, parentObject.toString(), Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException e){
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(SendSms.this, t.getMessage(), Toast.LENGTH_SHORT).show();

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
                                Toast.makeText(SendSms.this, "Successfull", Toast.LENGTH_SHORT).show();
                                mCountDownTimer.cancel();
                                progressDialog.dismiss();
                                listenerRegistration.remove();
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
                Toast.makeText(SendSms.this, "Time out", Toast.LENGTH_SHORT).show();
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
