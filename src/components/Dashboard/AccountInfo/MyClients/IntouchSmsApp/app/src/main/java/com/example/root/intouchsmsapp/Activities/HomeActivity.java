package com.example.root.intouchsmsapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.intouchsmsapp.Adapters.GridViewAdapter;
import com.example.root.intouchsmsapp.CheckConnection.ConnectivityReceiver;
import com.example.root.intouchsmsapp.CheckConnection.MyApplication;
import com.example.root.intouchsmsapp.Dialogs.TopUpPostPaid;
import com.example.root.intouchsmsapp.Fragments.MessagingFragment;
import com.example.root.intouchsmsapp.Models.GridViewItems;
import com.example.root.intouchsmsapp.R;
import com.example.root.intouchsmsapp.StartActivity;
import com.example.root.intouchsmsapp.network.ApiClient;
import com.example.root.intouchsmsapp.network.ApiInterface;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.scrounger.countrycurrencypicker.library.Country;
import com.scrounger.countrycurrencypicker.library.CountryCurrencyPicker;
import com.scrounger.countrycurrencypicker.library.Currency;
import com.scrounger.countrycurrencypicker.library.Listener.CountryCurrencyPickerListener;
import com.scrounger.countrycurrencypicker.library.PickerType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.root.intouchsmsapp.Activities.LoginActivity.ACCOUNT_BALANCE;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.ACCOUNT_PAYMODE;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.ACCOUNT_ROLE;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.FULL_NAMES;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.SHARED_PREFS;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.TOKEN;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.UID;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TopUpPostPaid.DialogClassListener{

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    Boolean in = Boolean.TRUE;
    private Button logout;
    private SharedPreferences prefs;
    private String token;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListenerRegistration listenerRegistration, listenerRegistration_balance;

    private String accounttype;
    private String paymode;

    private String uid;
    private TextView balance_tv;
    private boolean balance_from_firebase = false;

    private static final long START_TIME_IN_MILLIS = 300000; //360000
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TextView user_full_names;
    private TextView navUserName;
    private String bl;
    private String user_full_name;

    //for recycler grid layout

    private RecyclerView recyclerView;
    private ArrayList<GridViewItems> mRecyclerItems;
    GridViewAdapter gridViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.hometoolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }



        prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        token = prefs.getString(TOKEN, "None");
        accounttype = prefs.getString(ACCOUNT_ROLE, "None");
        paymode = prefs.getString(ACCOUNT_PAYMODE, "None");
        bl = prefs.getString(ACCOUNT_BALANCE, "None");
        bl = bl.replace("Balance: ", "");

        user_full_names = findViewById(R.id.nav_full_names);
        // navigation view
        navigationView = findViewById(R.id.nav_view);
        user_full_name = prefs.getString(FULL_NAMES, "None");
        if (!prefs.getString(ACCOUNT_ROLE, "").equals("Reseller")){
            Menu menuNav = navigationView.getMenu();
            MenuItem credittransferitem = menuNav.findItem(R.id.nav_creditransfers);
            MenuItem myclientsitem = menuNav.findItem(R.id.nav_myclients);
            myclientsitem.setVisible(false);
            credittransferitem.setVisible(false);

        }

        drawer = findViewById(R.id.drawer_layout);

        logout = findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutCall(token);
            }
        });

        View headerView = navigationView.getHeaderView(0);
        navUserName = (TextView) headerView.findViewById(R.id.nav_full_names);

        navUserName.setText("Welcome, "+user_full_name+"\n"+bl);


        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null) {

            navigationView.setCheckedItem(R.id.nav_home);
        }


        // set events

        //setSingleEvent(gridLayout);

        createDashboard(prefs.getString(ACCOUNT_ROLE, ""));
    }

    public void createDashboard(String accountrole){


        mRecyclerItems = new ArrayList<>();

        mRecyclerItems.add(new GridViewItems(1, "My Profile", R.drawable.ic_profile));
        mRecyclerItems.add(new GridViewItems(2, "Top Up", R.drawable.ic_balanceicon));
        mRecyclerItems.add(new GridViewItems(3, "Send Sms", R.drawable.ic_message));
        //mRecyclerItems.add(new GridViewItems(8, "Message Scheduler", R.drawable.ic_schedule));
        mRecyclerItems.add(new GridViewItems(4, "Message Logs", R.drawable.ic_sent_sms));
        mRecyclerItems.add(new GridViewItems(5, "My Transactions", R.drawable.ic_topup));

        if (accountrole.equals("Reseller")){

            mRecyclerItems.add(new GridViewItems(6, "My Credit Transfers", R.drawable.ic_credit_transfer));
            mRecyclerItems.add(new GridViewItems(7, "My Clients", R.drawable.ic_myclients));
            //mRecyclerItems.add(new GridViewItems(8, "Choose Currency", R.drawable.ic_money));

        }



        buildRecyclerView();
    }

    public void buildRecyclerView() {

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        /*recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));*/

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        gridViewAdapter = new GridViewAdapter(mRecyclerItems);

        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(gridViewAdapter);

        gridViewAdapter.setOnItemClickListener(new GridViewAdapter.OnItemClickListener() {
            @Override
            public void oncellclick(int position) {
                GridViewItems gridViewItems = mRecyclerItems.get(position);
                if (gridViewItems.getId()==1){
                    viewProfile();
                } else if(gridViewItems.getId()==2){
                    openTopUpForm(accounttype);
                } else if (gridViewItems.getId()==3){
                    openSendSmsActivty();
                } else if (gridViewItems.getId()==4){
                    openMessageLogSummaryActivty();
                } else if (gridViewItems.getId()==5){
                    viewMyTransactions();
                } else if (gridViewItems.getId()==6){
                    openCreditTransfersActivity();
                } else if (gridViewItems.getId()==7){
                    openMyClientsActivity();
                } else if (gridViewItems.getId()==8){
                    openMessageSchedulerActivity();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        checkConnection();

        prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        token = prefs.getString(TOKEN, "None");

        checkauthorization(token);

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_profile:
                viewProfile();
                break;

            case R.id.nav_messaging:
                openSendSmsActivty();
                break;

            case R.id.nav_sentsms:
                openMessageLogSummaryActivty();
                break;

            case R.id.nav_transactions:
                openTransactionsActivity();
                break;

            case R.id.nav_creditransfers:
                openCreditTransfersActivity();
                break;

            case R.id.nav_myclients:
                openMyClientsActivity();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void choosecurrencyactivity(){
        CountryCurrencyPicker pickerDialog = CountryCurrencyPicker.newInstance(PickerType.COUNTRYandCURRENCY, new CountryCurrencyPickerListener() {
            @Override
            public void onSelectCountry(Country country) {

            }

            @Override
            public void onSelectCurrency(Currency currency) {
                if (currency.getCountries() == null) {
                    Toast.makeText(HomeActivity.this,
                            String.format("name: %s\nsymbol: %s", currency.getName(), currency.getSymbol())
                            , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this,
                            String.format("name: %s\ncurrencySymbol: %s\ncountries: %s", currency.getName(), currency.getSymbol(), TextUtils.join(", ", currency.getCountriesNames()))
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });

        pickerDialog.setDialogTitle("Choose Currency");

        pickerDialog.show(getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);

    }

    public void openTransactionsActivity(){
        Intent intent = new Intent(this, Transactions.class);
        startActivityForResult(intent, 2);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    private void changeBal(String paymode, String bal){

        accounttype = prefs.getString(ACCOUNT_ROLE, "None");
        balance_tv = findViewById(R.id.balance);

        if (!paymode.equals("PostPaid")){


            if (accounttype.equals("Reseller") || accounttype.equals("Agent")){
                navUserName.setText("Welcome, "+user_full_name+"\n"+"Bal: "+bal +" Cr.");
                balance_tv.setText("Bal: "+bal+" Cr.");
            } else {
                navUserName.setText("Welcome, "+user_full_name+"\n"+"Bal: "+bal +" RWF");
                balance_tv.setText("Bal: "+bal+" RWF");
            }

        } else{

            if (accounttype.equals("Reseller") || accounttype.equals("Agent")){
                navUserName.setText("Welcome, "+user_full_name);
                balance_tv.setText("");
            } else {
                navUserName.setText("Welcome, "+user_full_name);
                balance_tv.setText("");
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

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
    }


    /*private void setSingleEvent(android.support.v7.widget.GridLayout gridLayout){
        // loop through all child items of a grid

        for (int i = 0; i < gridLayout.getChildCount(); i++){
            // since all childrens imbedded are cardviews we cast the object to the cardview

            CardView cardView = (CardView)gridLayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalI==0){
                        //viewProfile();
                        viewGridRecyclerView();
                    } else if(finalI==1){
                        openTopUpForm(accounttype);

                    } else if (finalI==2){
                        openSendSmsActivty();
                    } else if (finalI==3){
                        openMessageLogSummaryActivty();
                    } else if (finalI==4){
                        viewMyTransactions();
                    } else if (finalI==5){
                        openCreditTransfersActivity();
                    } else if (finalI==6){
                        openMyClientsActivity();
                    }


                }
            });
        }
    }*/

    public void openMessageLogSummaryActivty(){
        Intent intent = new Intent(this, MessageLogSummary.class);
        startActivityForResult(intent, 6);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openMessageSchedulerActivity(){
        Intent intent = new Intent(this, MessageScheduler.class);
        startActivityForResult(intent, 6);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void viewProfile(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);

    }

    private void viewMyTransactions() {
        Intent intent = new Intent(this, Transactions.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void openSendSmsActivty(){
        Intent intent = new Intent(this, SendSms.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openMyClientsActivity(){
        Intent intent = new Intent(this, GetMyClients.class);
        startActivityForResult(intent, 15);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openCreditTransfersActivity(){
        Intent intent = new Intent(this, MyCreditTransfers.class);
        startActivityForResult(intent, 4);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    private void logoutCall(String token){
        StartActivity.showProgressDialog("Logging out please wait",HomeActivity.this);
        Call<String> call = apiService.logout("Token "+token);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String resp = response.body();

                if (!response.isSuccessful()){
                    StartActivity.hideProgressDialog();
                    Toast.makeText(HomeActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    final JSONObject parentObject = new JSONObject(resp);

                    if (parentObject.getString("success").equals("true")){

                        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(TOKEN, "None");
                        editor.putString(FULL_NAMES, "None");
                        editor.apply();

                        StartActivity.hideProgressDialog();

                        in = Boolean.FALSE;
                        //onBackPressed();
                        finish();
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    } else {

                        StartActivity.hideProgressDialog();
                        Toast.makeText(HomeActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e){
                    StartActivity.hideProgressDialog();
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                StartActivity.hideProgressDialog();
                Toast.makeText(HomeActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
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
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            if (in){
                this.moveTaskToBack(true);
            }
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
        StartActivity.showProgressDialog("Processing Transaction", HomeActivity.this);
        Call<ArrayList> call = apiService.ecashtopup("Token "+token, amount, network, phone);

        call.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


                ArrayList<String> resp = response.body();
                //String resp = response.body();

                if (!response.isSuccessful()){
                    StartActivity.hideProgressDialog();
                    Toast.makeText(HomeActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                try{

                    final JSONObject parentObject = new JSONObject(resp.get(0));

                    if (parentObject.getBoolean("success")){

                        //Toast.makeText(CustomerTopUpForm.this, parentObject.toString(), Toast.LENGTH_SHORT).show();
                        StartActivity.hideProgressDialog();
                        //paymentrequestsimulation(token);
                        gettransactiontopupstatus(parentObject.getString("transactionid"));
                        countDown();

                    } else {

                        StartActivity.hideProgressDialog();
                        Toast.makeText(HomeActivity.this, parentObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException e){
                    StartActivity.hideProgressDialog();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {
                StartActivity.hideProgressDialog();
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

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
                                Toast.makeText(HomeActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                                mCountDownTimer.cancel();
                                StartActivity.hideProgressDialog();
                                listenerRegistration.remove();
                            }
                        } else {

                        }
                    }
                });


    }

    private void countDown(){
        StartActivity.showProgressDialog("Please approve on your handset", HomeActivity.this);
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDown();

            }

            @Override
            public void onFinish() {
                mTimeLeftInMillis = START_TIME_IN_MILLIS;
                StartActivity.hideProgressDialog();
                listenerRegistration.remove();
                Toast.makeText(HomeActivity.this, "Time out", Toast.LENGTH_SHORT).show();
            }
        }.start();



    }

    private void updateCountDown(){
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timerLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        StartActivity.showProgressDialog("Time Left: "+timerLeftFormatted, HomeActivity.this);

    }

    private void checkauthorization(final String token){
        Call<ArrayList> call = apiService.getmybalance("Token "+token);
        call.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {

                if (!response.isSuccessful()){

                    if(response.code()==401 || response.code()==403 ){

                        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(TOKEN, "None");
                        editor.putString(FULL_NAMES, "None");
                        editor.apply();

                        in = Boolean.FALSE;
                        //onBackPressed();
                        finish();
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

                    }
                    return;
                }



            }

            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        navigationView.setCheckedItem(R.id.nav_home);

    }

}
