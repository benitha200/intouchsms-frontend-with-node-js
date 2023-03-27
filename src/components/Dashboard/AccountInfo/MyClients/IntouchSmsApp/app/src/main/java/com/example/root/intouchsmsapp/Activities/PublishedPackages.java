package com.example.root.intouchsmsapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.intouchsmsapp.Adapters.MessageLogDetailsAdapter;
import com.example.root.intouchsmsapp.Adapters.MyCustomersAdapter;
import com.example.root.intouchsmsapp.Adapters.PublishedPackagesAdapter;
import com.example.root.intouchsmsapp.Models.MessageLogDetailsItems;
import com.example.root.intouchsmsapp.Models.MyCustomersItems;
import com.example.root.intouchsmsapp.Models.PublishedPackagesItems;
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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.root.intouchsmsapp.Activities.LoginActivity.PAGE_SIZE;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.SHARED_PREFS;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.TOKEN;

public class PublishedPackages extends AppCompatActivity implements PublishedPackagesAdapter.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,PublishedPackagesAdapter.PublishedPackageAdapterListener{

    private final List<PublishedPackagesItems> publishedPackagesItemsList = new ArrayList<>();

    private PublishedPackagesAdapter mAdapter;

    RelativeLayout progressbarview;
    TextView loadtext;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private String token;

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    private LinearLayoutManager mLayoutManager;

    private int total_packages;

    private int pk = 0;
    private String packagename, min_credits, max_credits, min_price, max_price, unit_price;
    private Boolean maximumvalues_null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_published_packages);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Package Select");
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

        mAdapter = new PublishedPackagesAdapter(this,this);
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

        Call getPublishedPackagesCall = apiService.getpublishedpackages(
                "Token "+token,
                0,
                25
        );
        getPublishedPackagesCall.enqueue(getPublishedPackagesFirstFetchCallback);
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



        if (publishedPackagesItemsList.size() == total_packages){
            mAdapter.setMore(false);
            mAdapter.dismissLoading();
            return;
        } else {
            mAdapter.showLoading();

            final int start = mAdapter.getItemCount() - 1;
            final int end = start + PAGE_SIZE;

            Call getPublishedPackagesCall = apiService.getpublishedpackages(
                    "Token "+token,
                    start,
                    end
            );

            getPublishedPackagesCall.enqueue(getPublishedPackagesNextFetchCallback);
        }
        /*Toast.makeText(this, Integer.toString(total_transactions), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, Integer.toString(collectionsummaries.size()), Toast.LENGTH_SHORT).show();*/


    }

    protected Callback<ArrayList> getPublishedPackagesFirstFetchCallback = new Callback<ArrayList>() {
        @Override
        public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


            if (response.isSuccessful()) {

                progressbarview.setVisibility(View.GONE);

                ArrayList<String> getPublishedPackages = response.body();

                try {
                    final JSONObject parentObject = new JSONObject(getPublishedPackages.get(0));
                    final JSONArray packages = parentObject.getJSONArray("response");
                    total_packages = parentObject.getInt("total");
                    if (total_packages > 0){

                        if (PublishedPackages.this != null && !PublishedPackages.this.isFinishing()) {


                            //clear the collections
                            publishedPackagesItemsList.clear();

                            for (int i = 0; i < packages.length(); i++) {

                                Double min_credits = packages.getJSONObject(i).getJSONObject("fields").getDouble("minimum");
                                Double max_credits, max_price;
                                String maximumcreditsvalue, maximumpricevalue;
                                Boolean maximum_null = false;

                                if (packages.getJSONObject(i).getJSONObject("fields").get("maximum").toString().equals("null")){
                                    maximum_null = true;
                                    maximumcreditsvalue = NumberFormat.getInstance().format(Math.round(min_credits * 100.0) / 100.0).toString();
                                } else {
                                    maximum_null = false;
                                    max_credits = packages.getJSONObject(i).getJSONObject("fields").getDouble("maximum");
                                    maximumcreditsvalue = NumberFormat.getInstance().format(Math.round(max_credits * 100.0) / 100.0).toString();
                                }

                                Double unit_price = packages.getJSONObject(i).getJSONObject("fields").getDouble("unitpricetaxincl");
                                Double min_price = packages.getJSONObject(i).getJSONObject("fields").getDouble("minimumprice");

                                if (packages.getJSONObject(i).getJSONObject("fields").get("maximumprice").toString().equals("null")){
                                    maximum_null = true;
                                    maximumpricevalue = NumberFormat.getInstance().format(Math.round(min_price * 100.0) / 100.0).toString();
                                } else {
                                    maximum_null = false;
                                    max_price = packages.getJSONObject(i).getJSONObject("fields").getDouble("maximumprice");
                                    maximumpricevalue = NumberFormat.getInstance().format(Math.round(max_price * 100.0) / 100.0).toString();
                                }





                                publishedPackagesItemsList.add(

                                        new PublishedPackagesItems(
                                                packages.getJSONObject(i).getInt("pk"),
                                                packages.getJSONObject(i).getJSONObject("fields").getString("name"),
                                                NumberFormat.getInstance().format(Math.round(min_credits * 100.0) / 100.0).toString(),
                                                maximumcreditsvalue,
                                                NumberFormat.getInstance().format(Math.round(unit_price * 100.0) / 100.0).toString(),
                                                NumberFormat.getInstance().format(Math.round(min_price * 100.0) / 100.0).toString(),
                                                maximumpricevalue,
                                                maximum_null

                                        )

                                );
                            }


                            mAdapter.addAll(publishedPackagesItemsList);
                            mSwipeRefreshLayout.setRefreshing(false);

                        }



                    } else{

                        Toast.makeText(PublishedPackages.this, "No Data Found", Toast.LENGTH_SHORT).show();

                    }





                } catch (JSONException e){
                    progressbarview.setVisibility(View.GONE);

                    Toast.makeText(PublishedPackages.this, e.getMessage(), Toast.LENGTH_LONG).show();

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

            Toast.makeText(PublishedPackages.this, "Can't load data. Check your network connection." , Toast.LENGTH_LONG).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };


    protected Callback<ArrayList> getPublishedPackagesNextFetchCallback = new Callback<ArrayList>() {
        @Override
        public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


            if (response.isSuccessful()) {

                progressbarview.setVisibility(View.GONE);

                ArrayList<String> getPublishedPackages = response.body();

                try {



                    final JSONObject parentObject = new JSONObject(getPublishedPackages.get(0));
                    final JSONArray packages = parentObject.getJSONArray("response");

                    if (total_packages > 0){

                        if (PublishedPackages.this != null && !PublishedPackages.this.isFinishing()) {


                            //clear the collections
                            publishedPackagesItemsList.clear();

                            for (int i = 0; i < packages.length(); i++) {

                                Double min_credits = packages.getJSONObject(i).getJSONObject("fields").getDouble("minimum");
                                Double max_credits, max_price;
                                String maximumcreditsvalue, maximumpricevalue;
                                Boolean maximum_null = false;

                                if (packages.getJSONObject(i).getJSONObject("fields").get("maximum").toString().equals("null")){
                                    maximum_null = true;
                                    maximumcreditsvalue = NumberFormat.getInstance().format(Math.round(min_credits * 100.0) / 100.0).toString();
                                } else {
                                    maximum_null = false;
                                    max_credits = packages.getJSONObject(i).getJSONObject("fields").getDouble("maximum");
                                    maximumcreditsvalue = NumberFormat.getInstance().format(Math.round(max_credits * 100.0) / 100.0).toString();
                                }

                                Double unit_price = packages.getJSONObject(i).getJSONObject("fields").getDouble("unitpricetaxincl");
                                Double min_price = packages.getJSONObject(i).getJSONObject("fields").getDouble("minimumprice");

                                if (packages.getJSONObject(i).getJSONObject("fields").get("maximumprice").toString().equals("null")){
                                    maximum_null = true;
                                    maximumpricevalue = NumberFormat.getInstance().format(Math.round(min_price * 100.0) / 100.0).toString() ;
                                } else {
                                    maximum_null = false;
                                    max_price = packages.getJSONObject(i).getJSONObject("fields").getDouble("maximumprice");
                                    maximumpricevalue = NumberFormat.getInstance().format(Math.round(max_price * 100.0) / 100.0).toString();
                                }





                                publishedPackagesItemsList.add(

                                        new PublishedPackagesItems(
                                                packages.getJSONObject(i).getInt("pk"),
                                                packages.getJSONObject(i).getJSONObject("fields").getString("name"),
                                                NumberFormat.getInstance().format(Math.round(min_credits * 100.0) / 100.0).toString(),
                                                maximumcreditsvalue,
                                                NumberFormat.getInstance().format(Math.round(unit_price * 100.0) / 100.0).toString(),
                                                NumberFormat.getInstance().format(Math.round(min_price * 100.0) / 100.0).toString(),
                                                maximumpricevalue,
                                                maximum_null

                                        )

                                );

                            }

                            mAdapter.dismissLoading();
                            mAdapter.addItemMore(publishedPackagesItemsList);
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

                        Toast.makeText(PublishedPackages.this, "No Data Found", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    //progressbarview.setVisibility(View.GONE);

                    Toast.makeText(PublishedPackages.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else{
                Toast.makeText(PublishedPackages.this, "Failed load data", Toast.LENGTH_LONG).show();
                //Keep trying
                //loadData();
            }
        }

        @Override
        public void onFailure(Call<ArrayList> call, Throwable t) {

            //isLoading = false;

            //progressbarview.setVisibility(View.GONE);

            Toast.makeText(PublishedPackages.this, "Can't load data. Check your network connection." , Toast.LENGTH_LONG).show();
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
    public void onPublishedPackageRowClicked(int position) {

        PublishedPackagesItems publishedPackagesItems =mAdapter.getItems().get(position);
        pk = publishedPackagesItems.getId();
        packagename = publishedPackagesItems.getName();
        unit_price = publishedPackagesItems.getUnitprice().toString().replace(",", "");
        min_credits = publishedPackagesItems.getMincredits().toString().replace(",", "");
        min_price = publishedPackagesItems.getMin_price().toString().replace(",", "");
        maximumvalues_null = publishedPackagesItems.getMaximum_null();

        if (maximumvalues_null){
            max_credits = "";
            max_price = "";
        } else {
            max_credits = publishedPackagesItems.getMaxcredits().toString().replace(",", "");
            max_price = publishedPackagesItems.getMax_price().toString().replace(",", "");
        }



        onBackPressed();

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
        resultIntent.putExtra("result", "from_publishedpackages");
        resultIntent.putExtra("pk", Integer.toString(pk));
        resultIntent.putExtra("maximumvalues_null", maximumvalues_null);
        resultIntent.putExtra("packagename", packagename);
        resultIntent.putExtra("maxcredits", max_credits);
        resultIntent.putExtra("mincredits", min_credits);
        resultIntent.putExtra("minprice", min_price);
        resultIntent.putExtra("maxprice", max_price);
        resultIntent.putExtra("unitprice", unit_price);
        setResult(RESULT_OK, resultIntent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
