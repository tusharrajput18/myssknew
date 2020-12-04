package in.co.vsys.myssksamaj.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.BusinessListAdapter;
import in.co.vsys.myssksamaj.model.data_models.BusinessModel;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

public class BusinessTypeListActivity extends AppCompatActivity {

    private int businessTypeId;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private static final String TAG = BusinessTypeListActivity.class.getSimpleName();
    private List<BusinessModel> businessList;
    private static final String BUSINESS_TYPE_ID = "BusinessTypeId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_type_list);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_businessTypeLIst);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Business List");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_BusinessLIst);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_BusinessList);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            businessTypeId = bundle.getInt("busi_type_Id");
        }

        businessList = new ArrayList<>();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        loadBusinessListUsingBusiId(businessTypeId);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadBusinessListUsingBusiId(final int bId) {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest busiListRequest = new StringRequest(Request.Method.POST, Config.business_list_busiType_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "business type list " + response);
                        mProgressBar.setVisibility(View.INVISIBLE);

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = null;
                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    int bId = object.getInt("businessid");
                                    String bName = object.getString("businessname");
                                    String bCity = object.getString("address");
                                    String bNo = object.getString("phone2");
                                    String bWebsite = object.getString("website");
                                    String bType = object.getString("businessType");
                                    String bUrl = object.getString("Image1");

                                    BusinessModel model = new BusinessModel(bId, bName, bWebsite, bCity, bNo, bUrl, bType);
                                    businessList.add(model);

                                }

                                BusinessListAdapter adapter = new BusinessListAdapter(BusinessTypeListActivity.this, businessList);
                                mRecyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            } else {

                                Toast.makeText(BusinessTypeListActivity.this, "Business list not found...", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "business type error " + error);
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> busiListParam = new HashMap<>();
                busiListParam.put(BUSINESS_TYPE_ID, String.valueOf(bId));
                return busiListParam;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(busiListRequest);
    }
}
