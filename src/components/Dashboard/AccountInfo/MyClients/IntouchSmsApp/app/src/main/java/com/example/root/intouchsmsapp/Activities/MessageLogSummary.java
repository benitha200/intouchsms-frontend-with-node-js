package com.example.root.intouchsmsapp.Activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.root.intouchsmsapp.Adapters.MessageLogSummaryAdapter;
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
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.root.intouchsmsapp.Activities.LoginActivity.PAGE_SIZE;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.SHARED_PREFS;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.TOKEN;

public class MessageLogSummary extends AppCompatActivity implements MessageLogSummaryAdapter.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,MessageLogSummaryAdapter.MessageLogSummaryAdapterListener{

    private final List<MessageLogSummaryItems> messageLogSummaryItemsList = new ArrayList<>();

    private MessageLogSummaryAdapter mAdapter;

    RelativeLayout progressbarview;
    TextView loadtext;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private String token;

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    private LinearLayoutManager mLayoutManager;

    private int total_messagelogsummaries;
    private String querystring = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_log_summary);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Message Log Summary");
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

        mAdapter = new MessageLogSummaryAdapter(this,this);
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

        Call getMyMessageLogSummaryCall = apiService.getmessagelogsummary(
                "Token "+token,
                0,
                25,
                querystring
        );
        getMyMessageLogSummaryCall.enqueue(getMyMessageLogSummaryFirstFetchCallback);
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



        if (messageLogSummaryItemsList.size() == total_messagelogsummaries){
            mAdapter.setMore(false);
            mAdapter.dismissLoading();
            return;
        } else {
            mAdapter.showLoading();

            final int start = mAdapter.getItemCount() - 1;
            final int end = start + PAGE_SIZE;

            Call getMyMessageLogSummaryCall = apiService.getmessagelogsummary(
                    "Token "+token,
                    start,
                    end,
                    querystring
            );

            getMyMessageLogSummaryCall.enqueue(getMyMessageLogSummaryNextFetchCallback);
        }
        /*Toast.makeText(this, Integer.toString(total_transactions), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, Integer.toString(collectionsummaries.size()), Toast.LENGTH_SHORT).show();*/


    }

    protected Callback<ArrayList> getMyMessageLogSummaryFirstFetchCallback = new Callback<ArrayList>() {
        @Override
        public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


            if (response.isSuccessful()) {

                progressbarview.setVisibility(View.GONE);

                ArrayList<String> getMessageLogSummaries = response.body();

                try {
                    final JSONObject parentObject = new JSONObject(getMessageLogSummaries.get(0));
                    final JSONArray messagelogsummaries = parentObject.getJSONArray("response");
                    total_messagelogsummaries = parentObject.getInt("total");

                    if (total_messagelogsummaries > 0){

                        if (MessageLogSummary.this != null && !MessageLogSummary.this.isFinishing()) {


                            //clear the collections
                            messageLogSummaryItemsList.clear();

                            for (int i = 0; i < messagelogsummaries.length(); i++) {

                                MessageLogSummaryItems messageLogSummaryItems = new MessageLogSummaryItems();

                                messageLogSummaryItems.setId(messagelogsummaries.getJSONObject(i).getInt("pk"));
                                messageLogSummaryItems.setFrom(messagelogsummaries.getJSONObject(i).getString("sender"));
                                messageLogSummaryItems.setQueued(Double.toString(messagelogsummaries.getJSONObject(i).getDouble("queued")));
                                messageLogSummaryItems.setDelivered(Double.toString(messagelogsummaries.getJSONObject(i).getDouble("delivered")));
                                messageLogSummaryItems.setUndelivered(Integer.toString(messagelogsummaries.getJSONObject(i).getInt("undelivered")));
                                messageLogSummaryItems.setTotal(Double.toString(messagelogsummaries.getJSONObject(i).getDouble("total")));
                                messageLogSummaryItems.setCredits(Double.toString(messagelogsummaries.getJSONObject(i).getDouble("smscredits")));
                                messageLogSummaryItems.setMessage(messagelogsummaries.getJSONObject(i).getString("text"));

                                messageLogSummaryItemsList.add(messageLogSummaryItems);
                            }


                            mAdapter.addAll(messageLogSummaryItemsList);
                            mSwipeRefreshLayout.setRefreshing(false);

                        }



                    } else{

                        Toast.makeText(MessageLogSummary.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }





                } catch (JSONException e){
                    progressbarview.setVisibility(View.GONE);

                    Toast.makeText(MessageLogSummary.this, e.getMessage(), Toast.LENGTH_LONG).show();

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

            Toast.makeText(MessageLogSummary.this, "Can't load data. Check your network connection." , Toast.LENGTH_LONG).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };


    protected Callback<ArrayList> getMyMessageLogSummaryNextFetchCallback = new Callback<ArrayList>() {
        @Override
        public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


            if (response.isSuccessful()) {

                progressbarview.setVisibility(View.GONE);

                ArrayList<String> getMessageLogSummaries = response.body();

                try {



                    final JSONObject parentObject = new JSONObject(getMessageLogSummaries.get(0));
                    final JSONArray messagelogsummaries = parentObject.getJSONArray("response");

                    if (total_messagelogsummaries > 0){

                        if (MessageLogSummary.this != null && !MessageLogSummary.this.isFinishing()) {


                            //clear the collections
                            messageLogSummaryItemsList.clear();

                            for (int i = 0; i < messagelogsummaries.length(); i++) {

                                MessageLogSummaryItems messageLogSummaryItems = new MessageLogSummaryItems();

                                messageLogSummaryItems.setId(messagelogsummaries.getJSONObject(i).getInt("pk"));
                                messageLogSummaryItems.setFrom(messagelogsummaries.getJSONObject(i).getString("sender"));
                                messageLogSummaryItems.setQueued(Double.toString(messagelogsummaries.getJSONObject(i).getDouble("queued")));
                                messageLogSummaryItems.setDelivered(Double.toString(messagelogsummaries.getJSONObject(i).getDouble("delivered")));
                                messageLogSummaryItems.setUndelivered(Integer.toString(messagelogsummaries.getJSONObject(i).getInt("undelivered")));
                                messageLogSummaryItems.setTotal(Double.toString(messagelogsummaries.getJSONObject(i).getDouble("total")));
                                messageLogSummaryItems.setCredits(Double.toString(messagelogsummaries.getJSONObject(i).getDouble("smscredits")));
                                messageLogSummaryItems.setMessage(messagelogsummaries.getJSONObject(i).getString("text"));

                                messageLogSummaryItemsList.add(messageLogSummaryItems);
                            }

                            mAdapter.dismissLoading();
                            mAdapter.addItemMore(messageLogSummaryItemsList);
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

                        Toast.makeText(MessageLogSummary.this, "No Data Found", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    //progressbarview.setVisibility(View.GONE);

                    Toast.makeText(MessageLogSummary.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else{
                Toast.makeText(MessageLogSummary.this, "Failed load data", Toast.LENGTH_LONG).show();
                //Keep trying
                //loadData();
            }
        }

        @Override
        public void onFailure(Call<ArrayList> call, Throwable t) {

            //isLoading = false;

            //progressbarview.setVisibility(View.GONE);

            Toast.makeText(MessageLogSummary.this, "Can't load data. Check your network connection." , Toast.LENGTH_LONG).show();
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
    public void onMesssageLogSummaryRowClicked(int position) {

        MessageLogSummaryItems messageLogSummaryItems =mAdapter.getItems().get(position);
        openMessageLogDetailsActivity(messageLogSummaryItems.getId(),
                messageLogSummaryItems.getMessage(), messageLogSummaryItems.getFrom());

    }

    @Override
    public void onCopyContentClicked(int position) {

        MessageLogSummaryItems messageLogSummaryItems =mAdapter.getItems().get(position);

        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Message", messageLogSummaryItems.getMessage());
        clipboardManager.setPrimaryClip(clipData);

        Toast.makeText(this, "Message Copied", Toast.LENGTH_SHORT).show();

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

                if (total_messagelogsummaries > 0){
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

    public void openMessageLogDetailsActivity(int pk, String message, String from){
        Intent intent = new Intent(this, MessageLogDetails.class);
        intent.putExtra("pk", pk);
        intent.putExtra("message", message);
        intent.putExtra("from", from);
        startActivity(intent);
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


    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", "from_message_log_summary");
        setResult(RESULT_OK, resultIntent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
