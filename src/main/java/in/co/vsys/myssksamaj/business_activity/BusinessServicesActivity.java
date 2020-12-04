package in.co.vsys.myssksamaj.business_activity;

import android.content.Intent;
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
import in.co.vsys.myssksamaj.businessAdapter.BusinessServiceAdapter1;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.SelectServicesUsingSubCategoryId;
import in.co.vsys.myssksamaj.network1.BusinessAPIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessServicesActivity extends AppCompatActivity {

    Toolbar business_service_toolbar;
    RecyclerView recycler_service_business;
    ProgressBar progress_service_business;
    ArrayList<SelectServicesUsingSubCategoryId.SellectAllServices> sellectAllServicesArrayList;
    BusinessServiceAdapter1 businessServiceAdapter1;
    private String serviceid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_services);

        business_service_toolbar=(Toolbar)findViewById(R.id.business_service_toolbar);
        setSupportActionBar(business_service_toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            serviceid = bundle.getString("serviceid");
        }

        init();
        loadServicesUsingSubCatId(serviceid);
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
        sellectAllServicesArrayList=new ArrayList<>();
        recycler_service_business=(RecyclerView)findViewById(R.id.recycler_service_business);
        recycler_service_business.setHasFixedSize(true);
        recycler_service_business.setLayoutManager(new LinearLayoutManager(this));
        progress_service_business=(ProgressBar) findViewById(R.id.progress_service_business);
    }
private void loadServicesUsingSubCatId(String string) {
        progress_service_business.setVisibility(View.VISIBLE);
        BusinessAPIServices service = BusinessAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<SelectServicesUsingSubCategoryId> call = service.getAllServiceUsingSubCategoryIds(string);
        call.enqueue(new Callback<SelectServicesUsingSubCategoryId>() {
            @Override
            public void onResponse(Call<SelectServicesUsingSubCategoryId> call, Response<SelectServicesUsingSubCategoryId> response) {
                progress_service_business.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    sellectAllServicesArrayList = response.body().getAllServicesArrayList();
                    if (sellectAllServicesArrayList.size() > 0) {
                        businessServiceAdapter1 = new BusinessServiceAdapter1(BusinessServicesActivity.this, sellectAllServicesArrayList);
                        recycler_service_business.setAdapter(businessServiceAdapter1);


                    } else {
                        Toast.makeText(BusinessServicesActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BusinessServicesActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SelectServicesUsingSubCategoryId> call, Throwable t) {
                progress_service_business.setVisibility(View.GONE);
                Log.d("mytag", "onFailure: " + t.getMessage());
                Toast.makeText(BusinessServicesActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
