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

import com.example.root.intouchsmsapp.Adapters.MessageLogDetailsAdapter;
import com.example.root.intouchsmsapp.Adapters.MyCustomersAdapter;
import com.example.root.intouchsmsapp.Models.MessageLogDetailsItems;
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

public class MyCustomers extends AppCompatActivity implements MyCustomersAdapter.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,MyCustomersAdapter.CustomersAdapterListener{

    private final List<MyCustomersItems> customersItemsList = new ArrayList<>();

    private MyCustomersAdapter mAdapter;

    RelativeLayout progressbarview;
    TextView loadtext;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private String token;

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    private LinearLayoutManager mLayoutManager;

    private int total_customers;

    private int recepientpk = 0;
    private String customerno, customername, customerusername, customerstatus, phone,
            email,customerofficephone, customerpassword, address, customerbalance, customercommission,
            customerdesc, customercreated, customerlastlogin, symbol;


    private String querystring = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customers);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Select Customer");

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

        mAdapter = new MyCustomersAdapter(this,this);
        mRecyclerView.setAdapter(mAdapter);

        progressbarview.setVisibility(View.VISIBLE);
        loadtext.setText("Loading...");

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        // Pagination
        mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);
    }


    public void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        token = prefs.getString(TOKEN, "None");
        loadData(token);

    }

    public void loadData(String token){

        Call getMyCustomersCall = apiService.getmycustomers(
                "Token "+token,
                0,
                25,
                querystring
        );
        getMyCustomersCall.enqueue(getMyCustomersFirstFetchCallback);
    }

    @Override
    public void onRefresh() {
        //Log.d("MyCollectionsActivity_","onRefresh");
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

            Call getMyCustomersCall = apiService.getmycustomers(
                    "Token "+token,
                    start,
                    end,
                    querystring
            );

            getMyCustomersCall.enqueue(getMyCustomersNextFetchCallback);

        }


    }

    //customers by role
    protected Callback<ArrayList> getMyCustomersFirstFetchCallback = new Callback<ArrayList>() {
        @Override
        public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


            if (response.isSuccessful()) {

                progressbarview.setVisibility(View.GONE);

                ArrayList<String> getmycustomers = response.body();

                try {
                    final JSONObject parentObject = new JSONObject(getmycustomers.get(0));
                    final JSONArray customers = parentObject.getJSONArray("response");
                    total_customers = parentObject.getInt("total");

                    if (total_customers > 0){

                        if (MyCustomers.this != null && !MyCustomers.this.isFinishing()) {


                            //clear the collections
                            customersItemsList.clear();

                            for (int i = 0; i < customers.length(); i++) {

                                Boolean status = customers.getJSONObject(i).getJSONObject("fields").getBoolean("is_active");
                                String active;
                                if (status){
                                    active = "yes";
                                } else {
                                    active = "no";
                                }

                                customersItemsList.add(

                                        new MyCustomersItems(
                                                customers.getJSONObject(i).getInt("pk"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("customerno"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("username"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("first_name"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("last_login"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("date_joined"),
                                                active,
                                                customers.getJSONObject(i).getJSONObject("fields").getString("rateplan"),
                                                Double.toString(customers.getJSONObject(i).getJSONObject("fields").getDouble("balance")),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("phone"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("email"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("address"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("symbol"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("officephone"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("password"),
                                                Double.toString(customers.getJSONObject(i).getJSONObject("fields").getDouble("commission")),
                                                ""
                                        )

                                );
                            }


                            mAdapter.addAll(customersItemsList);
                            mSwipeRefreshLayout.setRefreshing(false);

                        }



                    } else{

                        Toast.makeText(MyCustomers.this, "No Data Found", Toast.LENGTH_SHORT).show();

                    }





                } catch (JSONException e){
                    progressbarview.setVisibility(View.GONE);

                    Toast.makeText(MyCustomers.this, e.getMessage(), Toast.LENGTH_LONG).show();

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

            Toast.makeText(MyCustomers.this, "Can't load data. Check your network connection." , Toast.LENGTH_LONG).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    protected Callback<ArrayList> getMyCustomersNextFetchCallback = new Callback<ArrayList>() {
        @Override
        public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


            if (response.isSuccessful()) {

                progressbarview.setVisibility(View.GONE);

                ArrayList<String> getMessageLogDetails = response.body();

                try {



                    final JSONObject parentObject = new JSONObject(getMessageLogDetails.get(0));
                    final JSONArray customers = parentObject.getJSONArray("response");

                    if (total_customers > 0){

                        if (MyCustomers.this != null && !MyCustomers.this.isFinishing()) {


                            //clear the collections
                            customersItemsList.clear();

                            for (int i = 0; i < customers.length(); i++) {

                                Boolean status = customers.getJSONObject(i).getJSONObject("fields").getBoolean("is_active");
                                String active;
                                if (status){
                                    active = "yes";
                                } else {
                                    active = "no";
                                }

                                customersItemsList.add(

                                        new MyCustomersItems(
                                                customers.getJSONObject(i).getInt("pk"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("customerno"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("username"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("first_name"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("last_login"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("date_joined"),
                                                active,
                                                customers.getJSONObject(i).getJSONObject("fields").getString("rateplan"),
                                                Double.toString(customers.getJSONObject(i).getJSONObject("fields").getDouble("balance")),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("phone"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("email"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("address"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("symbol"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("officephone"),
                                                customers.getJSONObject(i).getJSONObject("fields").getString("password"),
                                                Double.toString(customers.getJSONObject(i).getJSONObject("fields").getDouble("commission")),
                                                ""
                                        )

                                );
                            }

                            mAdapter.dismissLoading();
                            mAdapter.addItemMore(customersItemsList);
                            mAdapter.setMore(true);

                            /*if (collectionsummaries.size() < total_transactions){
                                mAdapter.dismissLoading();
                                mAdapter.addItemMore(collectionsummaries);
                                mAdapter.setMore(true);
                            } else {
                                mAdapter.dismissLoading();
                                mAdapter.addItemMore(null);
                                mAdapter.setMore(false);
                            }*/

                        }

                    } else{

                        Toast.makeText(MyCustomers.this, "No Data Found", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    //progressbarview.setVisibility(View.GONE);

                    Toast.makeText(MyCustomers.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else{
                Toast.makeText(MyCustomers.this, "Failed load data", Toast.LENGTH_LONG).show();
                //Keep trying
                //loadData();
            }
        }

        @Override
        public void onFailure(Call<ArrayList> call, Throwable t) {

            //isLoading = false;

            //progressbarview.setVisibility(View.GONE);

            Toast.makeText(MyCustomers.this, "Can't load data. Check your network connection." , Toast.LENGTH_LONG).show();
            //mSwipeRefreshLayout.setRefreshing(false);
        }
    };

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

    @Override
    public void onCustomerRowClicked(int position) {
        MyCustomersItems myCustomersItems =mAdapter.getItems().get(position);
        recepientpk = myCustomersItems.getId();
        customerno = myCustomersItems.getCustomerno();
        customername = myCustomersItems.getNames();
        customerusername = myCustomersItems.getUsername();
        customerstatus = myCustomersItems.getStatus();
        phone = myCustomersItems.getPhone();
        customerofficephone = myCustomersItems.getCustomerofficephone();
        email = myCustomersItems.getEmail();
        customerpassword = myCustomersItems.getPassword();
        address = myCustomersItems.getAddress();
        customerbalance = myCustomersItems.getCustomerbalance();
        customercommission = myCustomersItems.getCustomercommission();
        customercreated = myCustomersItems.getDatejoined();
        customerlastlogin = myCustomersItems.getLastlogin();
        symbol = myCustomersItems.getSymbol();
        onBackPressed();


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
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", "from_mycustomers");
        resultIntent.putExtra("recepientpk", Integer.toString(recepientpk));
        resultIntent.putExtra("customerno", customerno);
        resultIntent.putExtra("customername", customername);
        resultIntent.putExtra("customerusername", customerusername);
        resultIntent.putExtra("customerstatus", customerstatus);
        resultIntent.putExtra("phone", phone);
        resultIntent.putExtra("email", email);
        resultIntent.putExtra("officephone", customerofficephone);
        resultIntent.putExtra("password", customerpassword);
        resultIntent.putExtra("address", address);
        resultIntent.putExtra("officephone", customerofficephone);
        resultIntent.putExtra("customerbalance", customerbalance);
        setResult(RESULT_OK, resultIntent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4) {

            if (resultCode == RESULT_OK) {

                String result = data.getStringExtra("result");

                if (result.equals("from_myclient_form")){
                    loadData(token);
                }
            }
            if (resultCode == RESULT_CANCELED) {
                /*Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();*/
            }
        }
    }


}
