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

import com.example.root.intouchsmsapp.Adapters.CreditTransfersAdapter;
import com.example.root.intouchsmsapp.Adapters.MessageLogSummaryAdapter;
import com.example.root.intouchsmsapp.Dialogs.OpenMyCreditTransfer;
import com.example.root.intouchsmsapp.Dialogs.TopUpPostPaid;
import com.example.root.intouchsmsapp.Models.CreditTransfersItems;
import com.example.root.intouchsmsapp.Models.MessageLogSummaryItems;
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

import static com.example.root.intouchsmsapp.Activities.LoginActivity.PAGE_SIZE;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.SHARED_PREFS;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.TOKEN;

public class MyCreditTransfers extends AppCompatActivity implements OpenMyCreditTransfer.DialogClassListener,
        CreditTransfersAdapter.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,CreditTransfersAdapter.CreditTransfersAdapterListener{

    private final List<CreditTransfersItems> creditTransfersItemsList = new ArrayList<>();

    private CreditTransfersAdapter mAdapter;

    RelativeLayout progressbarview;
    TextView loadtext;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private String token;

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    private LinearLayoutManager mLayoutManager;

    private int total_credittransfers;

    private FloatingActionButton float_add_credittransfer;

    private String querystring = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_credit_transfers);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("My Credit Transfers");
        }

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        token = prefs.getString(TOKEN, "None");

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

        mAdapter = new CreditTransfersAdapter(this,this);
        mRecyclerView.setAdapter(mAdapter);

        progressbarview.setVisibility(View.VISIBLE);
        loadtext.setText("Loading...");

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        // Pagination
        mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);

        float_add_credittransfer = findViewById(R.id.float_add_credittransfer);
        float_add_credittransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreditTransferFormActivity();
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

        Call getMyCreditTransfersCall = apiService.getmycredittransfers(
                "Token "+token,
                0,
                25,
                querystring
        );
        getMyCreditTransfersCall.enqueue(getMyCreditFirstFetchCallback);
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



        if (creditTransfersItemsList.size() == total_credittransfers){
            mAdapter.setMore(false);
            mAdapter.dismissLoading();
            return;
        } else {
            mAdapter.showLoading();

            final int start = mAdapter.getItemCount() - 1;
            final int end = start + PAGE_SIZE;

            Call getMyCreditTransfersCall = apiService.getmycredittransfers(
                    "Token "+token,
                    start,
                    end,
                    querystring
            );

            getMyCreditTransfersCall.enqueue(getMyCreditTransferNextFetchCallback);
        }
        /*Toast.makeText(this, Integer.toString(total_transactions), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, Integer.toString(collectionsummaries.size()), Toast.LENGTH_SHORT).show();*/


    }

    protected Callback<ArrayList> getMyCreditFirstFetchCallback = new Callback<ArrayList>() {
        @Override
        public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


            if (response.isSuccessful()) {

                progressbarview.setVisibility(View.GONE);

                ArrayList<String> getMyCreditTransfers = response.body();

                try {
                    final JSONObject parentObject = new JSONObject(getMyCreditTransfers.get(0));
                    final JSONArray credittransfers = parentObject.getJSONArray("response");
                    total_credittransfers = parentObject.getInt("total");

                    if (total_credittransfers > 0){

                        if (MyCreditTransfers.this != null && !MyCreditTransfers.this.isFinishing()) {


                            //clear the collections
                            creditTransfersItemsList.clear();

                            for (int i = 0; i < credittransfers.length(); i++) {

                                creditTransfersItemsList.add(

                                        new CreditTransfersItems(
                                                credittransfers.getJSONObject(i).getInt("pk"),
                                                credittransfers.getJSONObject(i).getString("transferno"),
                                                credittransfers.getJSONObject(i).getString("receipientcustomerno"),
                                                credittransfers.getJSONObject(i).getString("receipientcustomer"),
                                                credittransfers.getJSONObject(i).getString("receipientmobilephone"),
                                                credittransfers.getJSONObject(i).getString("receipientemail"),
                                                Double.toString(credittransfers.getJSONObject(i).getDouble("amount")),
                                                credittransfers.getJSONObject(i).getString("created"),
                                                credittransfers.getJSONObject(i).getString("description"),
                                                credittransfers.getJSONObject(i).getString("receipientaddress")
                                        )

                                );
                            }


                            mAdapter.addAll(creditTransfersItemsList);
                            mSwipeRefreshLayout.setRefreshing(false);

                        }



                    } else{

                        Toast.makeText(MyCreditTransfers.this, "No Data Found", Toast.LENGTH_SHORT).show();

                    }





                } catch (JSONException e){
                    progressbarview.setVisibility(View.GONE);

                    Toast.makeText(MyCreditTransfers.this, e.getMessage(), Toast.LENGTH_LONG).show();

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

            Toast.makeText(MyCreditTransfers.this, "Can't load data. Check your network connection." , Toast.LENGTH_LONG).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };


    protected Callback<ArrayList> getMyCreditTransferNextFetchCallback = new Callback<ArrayList>() {
        @Override
        public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


            if (response.isSuccessful()) {

                progressbarview.setVisibility(View.GONE);

                ArrayList<String> getMyCreditTransfers = response.body();

                try {



                    final JSONObject parentObject = new JSONObject(getMyCreditTransfers.get(0));
                    final JSONArray credittransfers = parentObject.getJSONArray("response");

                    if (total_credittransfers > 0){

                        if (MyCreditTransfers.this != null && !MyCreditTransfers.this.isFinishing()) {


                            //clear the collections
                            creditTransfersItemsList.clear();

                            for (int i = 0; i < credittransfers.length(); i++) {

                                creditTransfersItemsList.add(

                                        new CreditTransfersItems(
                                                credittransfers.getJSONObject(i).getInt("pk"),
                                                credittransfers.getJSONObject(i).getString("transferno"),
                                                credittransfers.getJSONObject(i).getString("receipientcustomerno"),
                                                credittransfers.getJSONObject(i).getString("receipientcustomer"),
                                                credittransfers.getJSONObject(i).getString("receipientmobilephone"),
                                                credittransfers.getJSONObject(i).getString("receipientemail"),
                                                Double.toString(credittransfers.getJSONObject(i).getDouble("amount")),
                                                credittransfers.getJSONObject(i).getString("created"),
                                                credittransfers.getJSONObject(i).getString("description"),
                                                credittransfers.getJSONObject(i).getString("receipientaddress")
                                        )

                                );
                            }

                            mAdapter.dismissLoading();
                            mAdapter.addItemMore(creditTransfersItemsList);
                            mAdapter.setMore(true);

                        }

                    } else{

                        Toast.makeText(MyCreditTransfers.this, "No Data Found", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    //progressbarview.setVisibility(View.GONE);

                    Toast.makeText(MyCreditTransfers.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else{
                Toast.makeText(MyCreditTransfers.this, "Failed load data", Toast.LENGTH_LONG).show();
                //Keep trying
                //loadData();
            }
        }

        @Override
        public void onFailure(Call<ArrayList> call, Throwable t) {

            //isLoading = false;

            //progressbarview.setVisibility(View.GONE);

            Toast.makeText(MyCreditTransfers.this, "Can't load data. Check your network connection." , Toast.LENGTH_LONG).show();
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
    public void onCreditTransferRowClicked(int position) {

        CreditTransfersItems creditTransfersItems =mAdapter.getItems().get(position);
        Double amounttransfered = (Double.parseDouble(creditTransfersItems.getAmountransfered()) * 100.0) / 100.0;
        OpenMyCreditTransfer openMyCreditTransfer = OpenMyCreditTransfer.newInstance(creditTransfersItems.getTransferno(),
                creditTransfersItems.getDesc(), NumberFormat.getInstance().format(Math.round(amounttransfered * 100.0) / 100.0).toString() +" Credits", creditTransfersItems.getCustomerno(),
                creditTransfersItems.getCustomername(), creditTransfersItems.getPhone(),creditTransfersItems.getEmail(),
                creditTransfersItems.getAddress(), creditTransfersItems.getCreated());
        openMyCreditTransfer.setCancelable(false);
        openMyCreditTransfer.show(getSupportFragmentManager(), "dialog openmycredittransfer");

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

                if (total_credittransfers > 0){
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
    public void openmycredittransfer() {

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
        resultIntent.putExtra("result", "from_credittransfers");
        setResult(RESULT_OK, resultIntent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public void openCreditTransferFormActivity(){
        Intent intent = new Intent(this, CreditTransferForm.class);
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {

                String result = data.getStringExtra("result");

                if (result.equals("from_creditransfer_form")){
                    loadData(token);
                }
            }
            if (resultCode == RESULT_CANCELED) {
                /*Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();*/
            }
        }
    }


}
