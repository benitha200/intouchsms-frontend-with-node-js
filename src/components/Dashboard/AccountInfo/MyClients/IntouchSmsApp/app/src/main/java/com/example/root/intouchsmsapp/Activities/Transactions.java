package com.example.root.intouchsmsapp.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.intouchsmsapp.Adapters.TransactionsAdapter;
import com.example.root.intouchsmsapp.Models.TransactionsItems;
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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.root.intouchsmsapp.Activities.LoginActivity.ACCOUNT_ROLE;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.PAGE_SIZE;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.SHARED_PREFS;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.TOKEN;

public class Transactions extends AppCompatActivity implements TransactionsAdapter.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,TransactionsAdapter.TransactionsAdapterListener{

    Toolbar searchtollbar;
    Menu search_menu;
    MenuItem item_search;

    private final List<TransactionsItems> mytransactions = new ArrayList<>();
    private String account_role;

    private double opening_bl;
    private double closing_bl;

    private TransactionsAdapter mAdapter;

    RelativeLayout progressbarview;
    TextView loadtext;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private SharedPreferences settings;

    private String token;

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    private LinearLayoutManager mLayoutManager;

    private int total_transactions;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        token = prefs.getString(TOKEN, "None");
        account_role = prefs.getString(ACCOUNT_ROLE, "None");

        //loadData(token);

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

        mAdapter = new TransactionsAdapter(this,this);
        mRecyclerView.setAdapter(mAdapter);

        progressbarview.setVisibility(View.VISIBLE);
        loadtext.setText("Loading...");

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        // Pagination
        mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);

        setSearchtollbar();
    }


    public void onStart() {
        super.onStart();



        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        token = prefs.getString(TOKEN, "None");

        loadData(token);
    }

    public void loadData(String token){

        Call getMyTransactionsCall = apiService.gettransactions(
                "Token "+token,
                0,
                25
        );
        getMyTransactionsCall.enqueue(getMyTransactionsFirstFetchCallback);
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



        if (mytransactions.size() == total_transactions){
            mAdapter.setMore(false);
            mAdapter.dismissLoading();
            return;
        } else {
            mAdapter.showLoading();

            final int start = mAdapter.getItemCount() - 1;
            final int end = start + PAGE_SIZE;

            Call getMyTransactionsCall = apiService.gettransactions(
                    "Token "+token,
                    start,
                    end
            );

            getMyTransactionsCall.enqueue(getMyCollectionsNextFetchCallback);
        }
        /*Toast.makeText(this, Integer.toString(total_transactions), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, Integer.toString(collectionsummaries.size()), Toast.LENGTH_SHORT).show();*/


    }

    protected Callback<ArrayList> getMyTransactionsFirstFetchCallback = new Callback<ArrayList>() {
        @Override
        public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


            if (response.isSuccessful()) {

                progressbarview.setVisibility(View.GONE);

                ArrayList<String> gettransactions = response.body();

                try {
                    final JSONObject parentObject = new JSONObject(gettransactions.get(0));
                    final JSONArray transactions = parentObject.getJSONArray("response");
                    total_transactions = parentObject.getInt("total");

                    if (total_transactions > 0){

                        if (Transactions.this != null && !Transactions.this.isFinishing()) {


                            //clear the collections
                            mytransactions.clear();

                            for (int i = 0; i < transactions.length(); i++) {

                                double smscredits = transactions.getJSONObject(i).getDouble("smscredits");
                                double amount = transactions.getJSONObject(i).getDouble("amount");

                                String symbol = "";
                                String symbol_cr = "";
                                String symbol_cr_title = "";
                                if (account_role.equals("Agent") || account_role.equals("Reseller")){
                                    symbol = "Cr.";
                                    symbol_cr_title = "SMS Credits ";
                                } else if (account_role.equals("Customer")){
                                    symbol = "RWF";
                                    symbol_cr_title = "SMS Quantity ";
                                }

                                if (account_role.equals("Reseller") || account_role.equals("Agent")){
                                    opening_bl = transactions.getJSONObject(i).getDouble("smscreditsopeningbalance");
                                    closing_bl = transactions.getJSONObject(i).getDouble("smscreditsclosingbalance");

                                } else if(account_role.equals("Customer")){
                                    opening_bl = transactions.getJSONObject(i).getDouble("openingbalance");
                                    closing_bl = transactions.getJSONObject(i).getDouble("closingbalance");
                                }

                                TransactionsItems transactionsItems = new TransactionsItems();

                                transactionsItems.setId(1);
                                transactionsItems.setTransaction(transactions.getJSONObject(i).getString("transaction"));
                                transactionsItems.setCustomerno(transactions.getJSONObject(i).getString("customerno"));
                                transactionsItems.setCustomername(transactions.getJSONObject(i).getString("customer"));
                                transactionsItems.setSmsquantity(symbol_cr_title+" "+NumberFormat.getInstance().format(Math.round(smscredits * 100.0) / 100.0) + " " + symbol_cr);
                                transactionsItems.setOpeningbalance(NumberFormat.getInstance().format(Math.round(opening_bl * 100.0) / 100.0) +" "+symbol);
                                transactionsItems.setAmount(NumberFormat.getInstance().format(Math.round(amount * 100.0) / 100.0));
                                transactionsItems.setClosingbalance(NumberFormat.getInstance().format(Math.round(closing_bl * 100.0) / 100.0)+ " "+symbol);
                                transactionsItems.setCreated(transactions.getJSONObject(i).getString("created"));
                                transactionsItems.setBeneficiary(transactions.getJSONObject(i).getString("beneficiary"));

                                mytransactions.add(transactionsItems);
                            }


                            mAdapter.addAll(mytransactions);
                            mSwipeRefreshLayout.setRefreshing(false);

                        }



                    } else{

                        Toast.makeText(Transactions.this, "No Data Found", Toast.LENGTH_SHORT).show();

                    }





                } catch (JSONException e){
                    progressbarview.setVisibility(View.GONE);

                    Toast.makeText(Transactions.this, e.getMessage(), Toast.LENGTH_LONG).show();

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

            Toast.makeText(Transactions.this, "Can't load data. Check your network connection." , Toast.LENGTH_LONG).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };


    protected Callback<ArrayList> getMyCollectionsNextFetchCallback = new Callback<ArrayList>() {
        @Override
        public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


            if (response.isSuccessful()) {

                progressbarview.setVisibility(View.GONE);

                ArrayList<String> gettransactions = response.body();

                try {



                    final JSONObject parentObject = new JSONObject(gettransactions.get(0));
                    final JSONArray transactions = parentObject.getJSONArray("response");

                    if (total_transactions > 0){

                        if (Transactions.this != null && !Transactions.this.isFinishing()) {


                            //clear the collections
                            mytransactions.clear();

                            for (int i = 0; i < transactions.length(); i++) {

                                double smscredits = transactions.getJSONObject(i).getDouble("smscredits");
                                double amount = transactions.getJSONObject(i).getDouble("amount");

                                String symbol = "";
                                String symbol_cr = "";
                                String symbol_cr_title = "";
                                if (account_role.equals("Agent") || account_role.equals("Reseller")){
                                    symbol = "Cr.";
                                    symbol_cr_title = "SMS Credits:";
                                } else if (account_role.equals("Customer")){
                                    symbol = "RWF";
                                    symbol_cr_title = "SMS Quantity:";
                                }

                                if (account_role.equals("Reseller") || account_role.equals("Agent")){
                                    opening_bl = transactions.getJSONObject(i).getDouble("smscreditsopeningbalance");
                                    closing_bl = transactions.getJSONObject(i).getDouble("smscreditsclosingbalance");

                                } else if(account_role.equals("Customer")){
                                    opening_bl = transactions.getJSONObject(i).getDouble("openingbalance");
                                    closing_bl = transactions.getJSONObject(i).getDouble("closingbalance");
                                }

                                TransactionsItems transactionsItems = new TransactionsItems();

                                transactionsItems.setId(1);
                                transactionsItems.setTransaction(transactions.getJSONObject(i).getString("transaction"));
                                transactionsItems.setCustomerno(transactions.getJSONObject(i).getString("customerno"));
                                transactionsItems.setCustomername(transactions.getJSONObject(i).getString("customer"));
                                transactionsItems.setSmsquantity(symbol_cr_title+" "+NumberFormat.getInstance().format(Math.round(smscredits * 100.0) / 100.0) + " " + symbol_cr);
                                transactionsItems.setOpeningbalance(NumberFormat.getInstance().format(Math.round(opening_bl * 100.0) / 100.0) +" "+symbol);
                                transactionsItems.setAmount(NumberFormat.getInstance().format(Math.round(amount * 100.0) / 100.0));
                                transactionsItems.setClosingbalance(NumberFormat.getInstance().format(Math.round(closing_bl * 100.0) / 100.0)+ " "+symbol);
                                transactionsItems.setCreated(transactions.getJSONObject(i).getString("created"));
                                transactionsItems.setBeneficiary(transactions.getJSONObject(i).getString("beneficiary"));

                                mytransactions.add(transactionsItems);
                            }

                            mAdapter.dismissLoading();
                            mAdapter.addItemMore(mytransactions);
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

                        Toast.makeText(Transactions.this, "No Data Found", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    //progressbarview.setVisibility(View.GONE);

                    Toast.makeText(Transactions.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else{
                Toast.makeText(Transactions.this, "Failed load data", Toast.LENGTH_LONG).show();
                //Keep trying
                //loadData();
            }
        }

        @Override
        public void onFailure(Call<ArrayList> call, Throwable t) {

            //isLoading = false;

            //progressbarview.setVisibility(View.GONE);

            Toast.makeText(Transactions.this, "Can't load data. Check your network connection." , Toast.LENGTH_LONG).show();
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
    public void onTransactionRowClicked(int position) {

        TransactionsItems transactionsItems =mAdapter.getItems().get(position);

    }

    public void setSearchtollbar()
    {
        searchtollbar = (Toolbar) findViewById(R.id.searchtoolbar);
        if (searchtollbar != null) {
            searchtollbar.inflateMenu(R.menu.menu_search);
            search_menu=searchtollbar.getMenu();

            searchtollbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        circleReveal(R.id.searchtoolbar,1,true,false);
                    else
                        searchtollbar.setVisibility(View.GONE);
                }
            });

            item_search = search_menu.findItem(R.id.action_filter_search);

            MenuItemCompat.setOnActionExpandListener(item_search, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    // Do something when collapsed
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        circleReveal(R.id.searchtoolbar,1,true,false);
                    }
                    else
                        searchtollbar.setVisibility(View.GONE);
                    return true;
                }

                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    // Do something when expanded
                    return true;
                }
            });

            initSearchView();


        } else
            Log.d("toolbar", "setSearchtollbar: NULL");
    }

    public void initSearchView()
    {
        final SearchView searchView = (SearchView) search_menu.findItem(R.id.action_filter_search).getActionView();

        // Enable/Disable Submit button in the keyboard

        searchView.setSubmitButtonEnabled(false);

        // Change search close button image

        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeButton.setImageResource(R.drawable.ic_close);


        // set hint and the text colors

        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHint("Search...");
        txtSearch.setHintTextColor(Color.GRAY);
        txtSearch.setTextColor(getResources().getColor(R.color.colorBlack));


        // set the cursor

        AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.search_cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                return true;
            }

            public void callSearch(String querytext) {



            }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow)
    {
        final View myView = findViewById(viewID);

        int width=myView.getWidth();

        if(posFromRight>0)
            width-=(posFromRight*getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material))-(getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)/ 2);
        if(containsOverflow)
            width-=getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);

        int cx=width;
        int cy=myView.getHeight()/2;

        Animator anim;
        if(isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0,(float)width);
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float)width, 0);

        anim.setDuration((long)220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(!isShow)
                {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // make the view visible and start the animation
        if(isShow)
            myView.setVisibility(View.VISIBLE);

        // start the animation
        anim.start();


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
        resultIntent.putExtra("result", "from_topups");
        setResult(RESULT_OK, resultIntent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
