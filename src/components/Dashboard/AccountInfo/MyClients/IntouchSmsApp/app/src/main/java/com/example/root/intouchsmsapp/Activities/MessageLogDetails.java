package com.example.root.intouchsmsapp.Activities;

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

import com.example.root.intouchsmsapp.Adapters.MessageLogDetailsAdapter;
import com.example.root.intouchsmsapp.Models.MessageLogDetailsItems;
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

public class MessageLogDetails extends AppCompatActivity implements MessageLogDetailsAdapter.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,MessageLogDetailsAdapter.MessageLogDetailsAdapterListener{

    private final List<MessageLogDetailsItems> messageLogDetailsItemsList = new ArrayList<>();

    private MessageLogDetailsAdapter mAdapter;

    RelativeLayout progressbarview;
    TextView loadtext;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private String token;

    private ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    private LinearLayoutManager mLayoutManager;

    private int total_messagelogdetails;
    private  int pksummary;

    private String querystring;
    private TextView title_message, title_from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_log_details);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Message Log Details");
        }

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        token = prefs.getString(TOKEN, "None");
        pksummary = getIntent().getIntExtra("pk", 0);
        title_message = findViewById(R.id.log_detail_message);
        title_from = findViewById(R.id.log_detail_from);
        title_message.setText("Message: "+getIntent().getStringExtra("message"));
        title_from.setText("From: "+getIntent().getStringExtra("from"));

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

        mAdapter = new MessageLogDetailsAdapter(this,this);
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
        pksummary = getIntent().getIntExtra("pk", 0);

        loadData(token, pksummary);
    }

    public void loadData(String token, int pk){

        Call getMyMessageLogDetailsCall = apiService.getmessagelogdetails(
                "Token "+token,
                pk,
                0,
                25
        );
        getMyMessageLogDetailsCall.enqueue(getMyMessageLogDetailsFirstFetchCallback);
    }

    @Override
    public void onRefresh() {
        //Log.d("MyCollectionsActivity_","onRefresh");
        mAdapter.clear();
        mAdapter.setMore(true);
        progressbarview.setVisibility(View.VISIBLE);
        loadData(token, pksummary);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore() {



        if (messageLogDetailsItemsList.size() == total_messagelogdetails){
            mAdapter.setMore(false);
            mAdapter.dismissLoading();
            return;
        } else {
            mAdapter.showLoading();

            final int start = mAdapter.getItemCount() - 1;
            final int end = start + PAGE_SIZE;

            Call getMyMessageLogDetailsCall = apiService.getmessagelogdetails(
                    "Token "+token,
                    pksummary,
                    start,
                    end
            );

            getMyMessageLogDetailsCall.enqueue(getMyMessageLogDetailNextFetchCallback);
        }
        /*Toast.makeText(this, Integer.toString(total_transactions), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, Integer.toString(collectionsummaries.size()), Toast.LENGTH_SHORT).show();*/


    }

    protected Callback<ArrayList> getMyMessageLogDetailsFirstFetchCallback = new Callback<ArrayList>() {
        @Override
        public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


            if (response.isSuccessful()) {

                progressbarview.setVisibility(View.GONE);

                ArrayList<String> getMessageLogDetails = response.body();

                try {
                    final JSONObject parentObject = new JSONObject(getMessageLogDetails.get(0));
                    final JSONArray messagelogdetails = parentObject.getJSONArray("response");
                    total_messagelogdetails = parentObject.getInt("total");

                    if (total_messagelogdetails > 0){

                        if (MessageLogDetails.this != null && !MessageLogDetails.this.isFinishing()) {


                            //clear the collections
                            messageLogDetailsItemsList.clear();

                            for (int i = 0; i < messagelogdetails.length(); i++) {

                                String msg_status = "";

                                if (messagelogdetails.getJSONObject(i).getString("status").equals("U")){
                                    msg_status = "Unsent";
                                }
                                if (messagelogdetails.getJSONObject(i).getString("status").equals("D")){
                                    msg_status = "Delivered";
                                }
                                if (messagelogdetails.getJSONObject(i).getString("status").equals("E")){
                                    msg_status = "Errored";
                                }
                                if (messagelogdetails.getJSONObject(i).getString("status").equals("Q")){
                                    msg_status = "Queued";
                                }

                                MessageLogDetailsItems messageLogDetailsItems = new MessageLogDetailsItems();

                                messageLogDetailsItems.setId(messagelogdetails.getJSONObject(i).getInt("pk"));
                                messageLogDetailsItems.setFrom(messagelogdetails.getJSONObject(i).getString("sender"));
                                messageLogDetailsItems.setTocontact(messagelogdetails.getJSONObject(i).getString("contact"));
                                messageLogDetailsItems.setTo_phone(messagelogdetails.getJSONObject(i).getString("phone"));
                                messageLogDetailsItems.setCreated(messagelogdetails.getJSONObject(i).getString("created"));
                                messageLogDetailsItems.setStatus(msg_status);
                                messageLogDetailsItems.setMessage(messagelogdetails.getJSONObject(i).getString("text"));

                                messageLogDetailsItemsList.add(messageLogDetailsItems);
                            }


                            mAdapter.addAll(messageLogDetailsItemsList);
                            mSwipeRefreshLayout.setRefreshing(false);

                        }



                    } else{

                        Toast.makeText(MessageLogDetails.this, "No Data Found", Toast.LENGTH_SHORT).show();

                    }





                } catch (JSONException e){
                    progressbarview.setVisibility(View.GONE);

                    Toast.makeText(MessageLogDetails.this, e.getMessage(), Toast.LENGTH_LONG).show();

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

            Toast.makeText(MessageLogDetails.this, "Can't load data. Check your network connection." , Toast.LENGTH_LONG).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };


    protected Callback<ArrayList> getMyMessageLogDetailNextFetchCallback = new Callback<ArrayList>() {
        @Override
        public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {


            if (response.isSuccessful()) {

                progressbarview.setVisibility(View.GONE);

                ArrayList<String> getMessageLogDetails = response.body();

                try {



                    final JSONObject parentObject = new JSONObject(getMessageLogDetails.get(0));
                    final JSONArray messagelogdetails = parentObject.getJSONArray("response");

                    if (total_messagelogdetails > 0){

                        if (MessageLogDetails.this != null && !MessageLogDetails.this.isFinishing()) {


                            //clear the collections
                            messageLogDetailsItemsList.clear();

                            for (int i = 0; i < messagelogdetails.length(); i++) {

                                String msg_status = "";

                                if (messagelogdetails.getJSONObject(i).getString("status").equals("U")){
                                    msg_status = "Unsent";
                                }
                                if (messagelogdetails.getJSONObject(i).getString("status").equals("D")){
                                    msg_status = "Delivered";
                                }
                                if (messagelogdetails.getJSONObject(i).getString("status").equals("E")){
                                    msg_status = "Errored";
                                }
                                if (messagelogdetails.getJSONObject(i).getString("status").equals("Q")){
                                    msg_status = "Queued";
                                }

                                MessageLogDetailsItems messageLogDetailsItems = new MessageLogDetailsItems();

                                messageLogDetailsItems.setId(messagelogdetails.getJSONObject(i).getInt("pk"));
                                messageLogDetailsItems.setFrom(messagelogdetails.getJSONObject(i).getString("sender"));
                                messageLogDetailsItems.setTocontact(messagelogdetails.getJSONObject(i).getString("contact"));
                                messageLogDetailsItems.setTo_phone(messagelogdetails.getJSONObject(i).getString("phone"));
                                messageLogDetailsItems.setCreated(messagelogdetails.getJSONObject(i).getString("created"));
                                messageLogDetailsItems.setStatus(msg_status);
                                messageLogDetailsItems.setMessage(messagelogdetails.getJSONObject(i).getString("text"));

                                messageLogDetailsItemsList.add(messageLogDetailsItems);
                            }

                            mAdapter.dismissLoading();
                            mAdapter.addItemMore(messageLogDetailsItemsList);
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

                        Toast.makeText(MessageLogDetails.this, "No Data Found", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {

                    //progressbarview.setVisibility(View.GONE);

                    Toast.makeText(MessageLogDetails.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else{
                Toast.makeText(MessageLogDetails.this, "Failed load data", Toast.LENGTH_LONG).show();
                //Keep trying
                //loadData();
            }
        }

        @Override
        public void onFailure(Call<ArrayList> call, Throwable t) {

            //isLoading = false;

            //progressbarview.setVisibility(View.GONE);

            Toast.makeText(MessageLogDetails.this, "Can't load data. Check your network connection." , Toast.LENGTH_LONG).show();
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
    public void onMesssageLogDetailRowClicked(int position) {

        MessageLogDetailsItems messageLogDetailsItems =mAdapter.getItems().get(position);

    }


    /*@Override
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

                querystring = query;
                mAdapter.clear();
                mAdapter.setMore(true);
                progressbarview.setVisibility(View.VISIBLE);
                loadData(token, pksummary);
                mAdapter.notifyDataSetChanged();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.isEmpty() || newText.length()==0){
                    querystring = "";
                    mAdapter.clear();
                    mAdapter.setMore(true);
                    progressbarview.setVisibility(View.VISIBLE);
                    loadData(token, pksummary);
                    mAdapter.notifyDataSetChanged();
                }

                //Toast.makeText(Subjects.this, newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return true;
    }*/



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
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
