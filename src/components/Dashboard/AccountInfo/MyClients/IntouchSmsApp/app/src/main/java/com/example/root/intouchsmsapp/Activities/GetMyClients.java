package com.example.root.intouchsmsapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.intouchsmsapp.Adapters.MyClientsAdapter;
import com.example.root.intouchsmsapp.Adapters.MyCustomersAdapter;
import com.example.root.intouchsmsapp.Models.MyClientsItems;
import com.example.root.intouchsmsapp.Models.MyCustomersItems;
import com.example.root.intouchsmsapp.R;
import com.example.root.intouchsmsapp.helper.DividerItemDecoration;
import com.example.root.intouchsmsapp.network.ApiClient;
import com.example.root.intouchsmsapp.network.ApiInterface;
import com.nshmura.snappysmoothscroller.LinearLayoutScrollVectorDetector;
import com.nshmura.snappysmoothscroller.SnapType;
import com.nshmura.snappysmoothscroller.SnappySmoothScroller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.root.intouchsmsapp.Activities.LoginActivity.PAGE_SIZE;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.SHARED_PREFS;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.TOKEN;

public class GetMyClients extends AppCompatActivity implements MyClientsAdapter.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,MyClientsAdapter.CustomersAdapterListener{

    private final List<MyClientsItems> customersItemsList = new ArrayList<>();

    private MyClientsAdapter mAdapter;

    private FloatingActionButton float_add_client;

    RelativeLayout progressbarview;
    TextView loadtext;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private String token;

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    private LinearLayoutManager mLayoutManager;

    private int clientpk = 0;
    private String customerno, customername, customerusername, customerstatus, phone,
            email,customerofficephone, customerpassword, address, customerbalance, clientcommission,
            customercreated, customerlastlogin, desc, clientaddr;

    private String querystring = "";

    private int total_customers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_my_clients);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("My Clients");

        }

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        token = prefs.getString(TOKEN, "None");

        progressbarview = (RelativeLayout) this.findViewById(R.id.progressbar_view);
        loadtext=(TextView) this.findViewById(R.id.loadText);

        mRecyclerView = (RecyclerView) this.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipe_refresh_layout);

        mLayoutManager = new LinearLayoutManager(this) {

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                SnappySmoothScroller scroller = new SnappySmoothScroller.Builder()
                        .setPosition(position)
                        .setSnapType(SnapType.END)
                        .setScrollVectorDetector(new LinearLayoutScrollVectorDetector(this))
                        .build(recyclerView.getContext());

                startSmoothScroll(scroller);
            }
        };

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mAdapter = new MyClientsAdapter(this,this);
        mRecyclerView.setAdapter(mAdapter);

        progressbarview.setVisibility(View.VISIBLE);
        loadtext.setText("Loading...");

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        // Pagination
        mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);

        //loadData(token);
        float_add_client = findViewById(R.id.float_add_myclient);
        float_add_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddMyClientFormActivity();
            }
        });
    }

    public void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        token = prefs.getString(TOKEN, "None");
        loadData(token);

    }

    public void loadData(String token){

        Call getMyClients = apiService.getmyclients(
                "Token "+token,
                0,
                25,
                querystring
        );
        getMyClients.enqueue(getMyClientsFirstFetchCallback);
    }

    @Override
    public void onRefresh() {
        mAdapter.clear();
        mAdapter.setMore(true);
        progressbarview.setVisibility(View.VISIBLE);
        loadData(token);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore() {



        if (customersItemsList.size() == total_customers){
            mAdapter.setMore(false);
            mAdapter.dismissLoading();
            return;
        } else {
            mAdapter.showLoading();

            final int start = mAdapter.getItemCount() - 1;
            final int end = start + PAGE_SIZE;

            Call getMyClientsCall = apiService.getmyclients(
                    "Token "+token,
                    start,
                    end,
                    querystring
            );

            getMyClientsCall.enqueue(getMyClientsNextFetchCallback);

        }


    }

    protected Callback<ArrayList> getMyClientsFirstFetchCallback = new Callback<ArrayList>() {
        @Override
        public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


            if (response.isSuccessful()) {

                progressbarview.setVisibility(View.GONE);

                ArrayList<String> getmycustomers = response.body();

                try {
                    final JSONObject parentObject = new JSONObject(getmycustomers.get(0));
                    final JSONArray clients = parentObject.getJSONArray("response");
                    total_customers = parentObject.getInt("total");

                    if (total_customers > 0){

                        if (GetMyClients.this != null && !GetMyClients.this.isFinishing()) {


                            //clear the collections
                            customersItemsList.clear();

                            for (int i = 0; i < clients.length(); i++) {

                                Boolean status = clients.getJSONObject(i).getJSONObject("fields").getBoolean("is_active");
                                String active;
                                if (status){
                                    active = "yes";
                                } else {
                                    active = "no";
                                }

                                customersItemsList.add(

                                        new MyClientsItems(
                                                clients.getJSONObject(i).getInt("pk"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("customerno"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("first_name"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("username"),
                                                active,
                                                clients.getJSONObject(i).getJSONObject("fields").getString("phone"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("email"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("officephone"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("password"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("address"),
                                                Double.toString(clients.getJSONObject(i).getJSONObject("fields").getDouble("smsbalance")),
                                                Double.toString(clients.getJSONObject(i).getJSONObject("fields").getDouble("commission")),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("desc"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("date_joined"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("last_login")
                                        )

                                );
                            }


                            mAdapter.addAll(customersItemsList);
                            mSwipeRefreshLayout.setRefreshing(false);

                        }



                    } else{

                        Toast.makeText(GetMyClients.this, "No Data Found", Toast.LENGTH_SHORT).show();

                    }





                } catch (JSONException e){
                    progressbarview.setVisibility(View.GONE);

                    Toast.makeText(GetMyClients.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            } else{
                //Toast.makeText(getContext(), "Failed load data", Toast.LENGTH_LONG).show();
                //Keep trying
                //loadData();
            }
        }

        @Override
        public void onFailure(Call<ArrayList> call, Throwable t) {

            //isLoading = false;

            progressbarview.setVisibility(View.GONE);

            Toast.makeText(GetMyClients.this, "Can't load data. Check your network connection." , Toast.LENGTH_LONG).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    protected Callback<ArrayList> getMyClientsNextFetchCallback = new Callback<ArrayList>() {
        @Override
        public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


            if (response.isSuccessful()) {

                progressbarview.setVisibility(View.GONE);

                ArrayList<String> getmyclients = response.body();

                try {



                    final JSONObject parentObject = new JSONObject(getmyclients.get(0));
                    final JSONArray clients = parentObject.getJSONArray("response");

                    if (total_customers > 0){

                        if (GetMyClients.this != null && !GetMyClients.this.isFinishing()) {


                            //clear the collections
                            customersItemsList.clear();

                            for (int i = 0; i < clients.length(); i++) {

                                Boolean status = clients.getJSONObject(i).getJSONObject("fields").getBoolean("is_active");
                                String active;
                                if (status){
                                    active = "yes";
                                } else {
                                    active = "no";
                                }

                                customersItemsList.add(

                                        new MyClientsItems(
                                                clients.getJSONObject(i).getInt("pk"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("customerno"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("first_name"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("username"),
                                                active,
                                                clients.getJSONObject(i).getJSONObject("fields").getString("phone"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("email"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("officephone"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("password"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("address"),
                                                Double.toString(clients.getJSONObject(i).getJSONObject("fields").getDouble("smsbalance")),
                                                Double.toString(clients.getJSONObject(i).getJSONObject("fields").getDouble("commission")),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("desc"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("date_joined"),
                                                clients.getJSONObject(i).getJSONObject("fields").getString("last_login")
                                        )

                                );
                            }

                            mAdapter.dismissLoading();
                            mAdapter.addItemMore(customersItemsList);
                            mAdapter.setMore(true);


                        }

                    } else{

                        Toast.makeText(GetMyClients.this, "No Data Found", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    //progressbarview.setVisibility(View.GONE);

                    Toast.makeText(GetMyClients.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else{
                Toast.makeText(GetMyClients.this, "Failed load data", Toast.LENGTH_LONG).show();
                //Keep trying
                //loadData();
            }
        }

        @Override
        public void onFailure(Call<ArrayList> call, Throwable t) {

            //isLoading = false;

            //progressbarview.setVisibility(View.GONE);

            Toast.makeText(GetMyClients.this, "Can't load data. Check your network connection." , Toast.LENGTH_LONG).show();
            //mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    public void openAddMyClientFormActivity(){
        Intent intent = new Intent(this, AddMyClient.class);
        startActivityForResult(intent, 4);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openEditMyClientFormActivity(){
        Intent intent = new Intent(this, EditMyCustomer.class);
        intent.putExtra("pk", clientpk);
        intent.putExtra("customerno", customerno);
        intent.putExtra("customername", customername);
        intent.putExtra("customerusername", customerusername);
        intent.putExtra("customerstatus", customerstatus);
        intent.putExtra("phone", phone);
        intent.putExtra("email", email);
        intent.putExtra("address", address);
        intent.putExtra("customerbalance", customerbalance);
        intent.putExtra("clientcommission", clientcommission);
        intent.putExtra("password", customerpassword);
        intent.putExtra("address", address);
        intent.putExtra("officephone", customerofficephone);
        intent.putExtra("created", customercreated);
        intent.putExtra("lastlogin", customerlastlogin);
        intent.putExtra("clientaddr", clientaddr);
        intent.putExtra("clientdesc", desc);

        startActivityForResult(intent, 4);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onCustomerRowClicked(int position) {
        MyClientsItems myClientsItems =mAdapter.getItems().get(position);
        clientpk = myClientsItems.getId();
        customerno = myClientsItems.getCustomerno();
        customername = myClientsItems.getNames();
        customerusername = myClientsItems.getUsername();
        customerstatus = myClientsItems.getStatus();
        phone = myClientsItems.getPhone();
        customerofficephone = myClientsItems.getOfficephone();
        email = myClientsItems.getEmail();
        customerpassword = myClientsItems.getPassword();
        address = myClientsItems.getAddress();
        customerbalance = myClientsItems.getAccountbal();
        clientcommission = myClientsItems.getCommission();
        customercreated = myClientsItems.getDatejoined();
        customerlastlogin = myClientsItems.getLastlogin();
        desc = myClientsItems.getDesc();
        clientaddr = myClientsItems.getAddress();

        openEditMyClientFormActivity();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchonly, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        // below for setting cursor color
        AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.loginsignup_color_cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
        }

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (total_customers > 0){
                    querystring = query;
                    mAdapter.clear();
                    mAdapter.setMore(true);
                    progressbarview.setVisibility(View.VISIBLE);
                    loadData(token);
                    mAdapter.notifyDataSetChanged();

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.isEmpty() || newText.length()==0){
                    querystring = "";
                    mAdapter.clear();
                    mAdapter.setMore(true);
                    progressbarview.setVisibility(View.VISIBLE);
                    loadData(token);
                    mAdapter.notifyDataSetChanged();
                }

                //Toast.makeText(Subjects.this, newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return true;
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager llManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (llManager.findLastCompletelyVisibleItemPosition() == (mAdapter.getItemCount() - 4)) {
                mAdapter.showLoading();
            }
        }
    };
}
