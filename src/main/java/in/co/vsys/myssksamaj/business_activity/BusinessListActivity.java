package in.co.vsys.myssksamaj.business_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.businessAdapter.BusinessCustomListAdapter;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.BusinessModel1;
import in.co.vsys.myssksamaj.businessmodels.BusinessModelList;
import in.co.vsys.myssksamaj.network1.BusinessAPIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessListActivity extends AppCompatActivity {

    Toolbar business_list_toolbar;
    RecyclerView business_listing_recycler;
    ProgressBar business_list_progress;
    BusinessCustomListAdapter businessCustomListAdapter;
    ArrayList<BusinessModelList> businessModelLists;
    String serviceid;
    String parentactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_list);


        business_list_toolbar=(Toolbar)findViewById(R.id.business_list_toolbar);
        setSupportActionBar(business_list_toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        init();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            parentactivity=bundle.getString("fromactivityname");
            Log.d("mytag", "onCreate: "+parentactivity);

            if(parentactivity.equals("dashboard")){
                serviceid=bundle.getString("prodname");
                Log.d("mytag", "dash ");
            }else {
                serviceid = bundle.getString("serviceid");
                Log.d("mytag", "service ");
            }
            loadBusinessUsingids(serviceid);
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
        businessModelLists=new ArrayList<>();
        business_listing_recycler=(RecyclerView)findViewById(R.id.business_listing_recycler);
        business_listing_recycler.setHasFixedSize(true);
        business_listing_recycler.setLayoutManager(new LinearLayoutManager(this));
        business_list_progress=(ProgressBar)findViewById(R.id.business_list_progress);
    }


    private void loadBusinessUsingids(String str) {
        business_list_progress.setVisibility(View.VISIBLE);
        BusinessAPIServices service = BusinessAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<BusinessModel1> call =null;
        if(parentactivity.equals("dashboard")){
            call=service.getAllBusinessUsngKeyWord(str);
        }else if(parentactivity.equals("serviceactivity")) {
            call=service.getAllBusinessUsngIds(str);
        }

        call.enqueue(new Callback<BusinessModel1>() {
            @Override
            public void onResponse(Call<BusinessModel1> call, Response<BusinessModel1> response) {
                business_list_progress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    businessModelLists = response.body().getBusinessModelLists();
                    if (businessModelLists.size() > 0) {
                        businessCustomListAdapter = new BusinessCustomListAdapter(BusinessListActivity.this, businessModelLists);
                        Log.d("mytag", "onResponse: "+businessModelLists.size());
                        business_listing_recycler.setAdapter(businessCustomListAdapter);
                    } else {
                        Toast.makeText(BusinessListActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BusinessListActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<BusinessModel1> call, Throwable t) {
                business_list_progress.setVisibility(View.GONE);
                Log.d("mytag", "onFailure: " + t.getMessage());
                Toast.makeText(BusinessListActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
