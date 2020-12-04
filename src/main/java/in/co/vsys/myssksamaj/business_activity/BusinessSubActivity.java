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
import in.co.vsys.myssksamaj.activities.BusinessDashboardActivity;
import in.co.vsys.myssksamaj.businessAdapter.BusinessSubCategoryAdapter1;
import in.co.vsys.myssksamaj.businesservices.BusinessAPIServices;
import in.co.vsys.myssksamaj.businessmodels.SellectAllSubCategoryType;
import in.co.vsys.myssksamaj.network1.BusinessAPIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessSubActivity extends AppCompatActivity {

    private String businessTypeId;
    RecyclerView recycler_sub_cat_business;
    ProgressBar progress_sub_business;
    private Toolbar mToolbar;
    private ArrayList<SellectAllSubCategoryType.SubCateList> subCateLists_arraylist;
    BusinessSubCategoryAdapter1 businessSubCategoryAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_sub);

        mToolbar=(Toolbar)findViewById(R.id.business_sub_cat_toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            businessTypeId = bundle.getString("mainBusinessid");
        }
        init();
        loadSubcategoryUsingIds(businessTypeId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(BusinessSubActivity.this, BusinessDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void init() {

        subCateLists_arraylist=new ArrayList<>();
        progress_sub_business=(ProgressBar) findViewById(R.id.progress_sub_business);
        recycler_sub_cat_business=(RecyclerView)findViewById(R.id.recycler_sub_cat_business);
        recycler_sub_cat_business.setHasFixedSize(true);
        recycler_sub_cat_business.setLayoutManager(new LinearLayoutManager(this));
    }


    private void loadSubcategoryUsingIds(String maincategoryid) {
        progress_sub_business.setVisibility(View.VISIBLE);
        BusinessAPIServices service = BusinessAPIClient.getInstance().create(BusinessAPIServices.class);
        Call<SellectAllSubCategoryType> call = service.getAllSubCategory(maincategoryid);
        call.enqueue(new Callback<SellectAllSubCategoryType>() {
            @Override
            public void onResponse(Call<SellectAllSubCategoryType> call, Response<SellectAllSubCategoryType> response) {
                progress_sub_business.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    subCateLists_arraylist = response.body().getSubCateLists();
                    if (subCateLists_arraylist.size() > 0) {
                        businessSubCategoryAdapter1 = new BusinessSubCategoryAdapter1(BusinessSubActivity.this, subCateLists_arraylist);
                        Log.d("mytag", "onResponse: "+subCateLists_arraylist.size());
                        recycler_sub_cat_business.setAdapter(businessSubCategoryAdapter1);
                    } else {
                        Toast.makeText(BusinessSubActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BusinessSubActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SellectAllSubCategoryType> call, Throwable t) {
                progress_sub_business.setVisibility(View.GONE);
                Log.d("mytag", "onFailure: " + t.getMessage());
                Toast.makeText(BusinessSubActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
