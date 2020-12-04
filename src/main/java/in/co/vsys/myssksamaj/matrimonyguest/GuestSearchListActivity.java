package in.co.vsys.myssksamaj.matrimonyguest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
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
import in.co.vsys.myssksamaj.contracts.SearchContract;
import in.co.vsys.myssksamaj.model.RecentlyJointModel;
import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.presenters.SearchPresenter;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

public class GuestSearchListActivity extends AppCompatActivity implements SearchContract.SearchView {

    private static final String TAG = GuestSearchListActivity.class.getSimpleName();
    private static final String MEMBER_ID = "MemberId";
    private static final String MEMBER_GENDER = "Gender";
    private static final String MEMBER_AGE_FROM = "AgeFrom";
    private static final String MEMBER_AGE_TO = "AgeTo";
    private static final String MEMBER_HEIGHT_FROM = "HeightFrom";
    private static final String MEMBER_HEIGHT_TO = "HeightTo";
    private static final String MEMBER_MOTHER_TOUNGE = "MotherTongueId";
    private static final String MEMBER_MARRIED_STATUS = "MarriedStatus";
    private static final String MEMBER_COUNTRY_ID = "CountryId";
    private static final String MEMBER_STATE_ID = "StateId";
    private static final String MEMBER_CITY_ID = "CityId";

    private static final String MEMBER_ISONLINE_ID = "isOnline";
    private static final String MEMBER_INCOME_ID = "incomeId";
    private static final String MEMBER_OCCUPATION_ID = "occupationId";
    private static final String MEMBER_PHYSICALLY_CHALLENGED = "physicallyChallenged";
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    //private List<RecentlyJointModel> searchLIst;
    private List<UserProfileModel> searchLIst;
    private GuestFilterAdapter filterAdapter;
    private SharedPreferences mPreferences;
    private int memberId;
    private String minAge, maxAge, minHeight, maxHeight, gender, motherTounge, marriedStatus, countryId, stateId, cityId;
    private SearchContract.SearchOps searchPresenter;
    private String manglik,strphysical,strincome,stroccupation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_search_list);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.filter_listToolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.filter_recyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.filterList_progressBar);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Search Profile List");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        memberId = mPreferences.getInt("memberId", 0);
        Log.e("memeberIdSearch", String.valueOf(memberId));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        getData();
        searchPresenter = new SearchPresenter(this);
        searchPresenter.searchMember(gender, minAge, maxAge, minHeight, maxHeight, motherTounge, marriedStatus, countryId, stateId, cityId, "", strincome, stroccupation,strphysical,manglik,"10810");
        //loadFileterList();
    }





   /* private void loadFileterList() {

        searchLIst = new ArrayList<>();
        mProgressBar.setVisibility(View.VISIBLE);


        *//*Matrimony_Search*//*
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.searchByFilterUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "filter list response" + response);
                        mProgressBar.setVisibility(View.GONE);

                        JSONArray jsonArray = null;

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    RecentlyJointModel jointModel = new RecentlyJointModel();
                                    jointModel.setUserMemberId(jsonObject1.getInt("MemberId"));
                                    jointModel.setUniqueId(jsonObject1.getString("UniqueId"));
                                    jointModel.setDOB(jsonObject1.getString("DOB"));
                                    jointModel.setUserHeight(jsonObject1.getString("MemberHeight"));
                                    jointModel.setMotherTongue(jsonObject1.getString("MotherTongue"));
                                    jointModel.setUserProfileUrl(jsonObject1.getString("MainProfilePhoto"));
                                    jointModel.setUserCity(jsonObject1.getString("MemberCity"));
                                    jointModel.setUserCountry(jsonObject1.getString("MemberCountry"));
                                    jointModel.setUserIncome(jsonObject1.getString("MemberInCome"));
                                    jointModel.setMarriedStatus(jsonObject1.getString("MarriedStatus"));
                                    //   jointModel.setGender(jsonObject1.getString("Gender"));
                                    jointModel.setEductionIn(jsonObject1.getString("Education In"));
                                    *//* model.setShortlistedFlag(object.getString("Shorted"));
                                    model.setInvitedFlag(object.getString("Invited"));*//*


                                    searchLIst.add(jointModel);
                                }

                                filterAdapter = new GuestFilterAdapter(GuestSearchListActivity.this, searchLIst);
                                mRecyclerView.setAdapter(filterAdapter);
                                filterAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "recently error " + error);
                        mProgressBar.setVisibility(View.GONE);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(MEMBER_GENDER, gender);
                params.put(MEMBER_AGE_FROM, minAge);
                params.put(MEMBER_AGE_TO, maxAge);
                params.put(MEMBER_HEIGHT_FROM, minHeight);
                params.put(MEMBER_HEIGHT_TO, maxHeight);
                params.put(MEMBER_MOTHER_TOUNGE, motherTounge);
                params.put(MEMBER_MARRIED_STATUS, marriedStatus);
                params.put(MEMBER_COUNTRY_ID, countryId);
                params.put(MEMBER_STATE_ID, stateId);
                params.put(MEMBER_CITY_ID, cityId);
                params.put(MEMBER_ISONLINE_ID, "");
                params.put(MEMBER_INCOME_ID, "");
                params.put(MEMBER_OCCUPATION_ID, "");
                params.put(MEMBER_PHYSICALLY_CHALLENGED, "");

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }*/

    private void getData() {


        minAge = mPreferences.getString("S_minAge", "");
        maxAge = mPreferences.getString("S_maxAge", "");
        minHeight = mPreferences.getString("S_minHeight", "");
        maxHeight = mPreferences.getString("S_maxHeight", "");
        motherTounge = mPreferences.getString("S_motherTongue", "");
        marriedStatus = mPreferences.getString("S_marriedStatus", "");
        countryId = mPreferences.getString("S_countryId", "");
        stateId = mPreferences.getString("S_stateId", "");
        cityId = mPreferences.getString("S_cityId", "");
        gender = mPreferences.getString("S_gender", "");

        manglik = mPreferences.getString("S_manglik", "");
        strphysical = mPreferences.getString("S_physicaldisablity", "");
        strincome = mPreferences.getString("S_income", "");
        stroccupation = mPreferences.getString("S_occupation", "");

        Log.d(TAG, "getData: "+manglik);
        Log.d(TAG, "getData: "+strphysical);
        Log.d(TAG, "getData: "+strincome);
        Log.d(TAG, "getData: "+stroccupation);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                startActivity(new Intent(GuestSearchListActivity.this, GuestSearchActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void showSearchResults(List<UserProfileModel> userProfileModels) {
        searchLIst = new ArrayList<>();
        searchLIst.clear();
        searchLIst.addAll(userProfileModels);
        Log.d(TAG, "showSearchResults: serarch list size"+userProfileModels.size());

        filterAdapter = new GuestFilterAdapter(GuestSearchListActivity.this, searchLIst);
        mRecyclerView.setAdapter(filterAdapter);
        filterAdapter.notifyDataSetChanged();

    }

    @Override
    public void showSearchedResults(List<UserProfileModel> userProfileModels) {

    }


    @Override
    public void showLoading() {

        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
