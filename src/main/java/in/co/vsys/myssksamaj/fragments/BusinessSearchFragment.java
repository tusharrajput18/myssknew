package in.co.vsys.myssksamaj.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import in.co.vsys.myssksamaj.adapter.BusinessTypeAdapter;
import in.co.vsys.myssksamaj.model.data_models.BusinessModel;
import in.co.vsys.myssksamaj.model.data_models.BusinessTypeModel;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessSearchFragment extends Fragment {

    public static final String TAG = BusinessSearchFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private AppCompatEditText search_businessName, search_city;
    private AppCompatSpinner spinner_businessType;
    private AppCompatImageButton btn_name, btn_city, btn_clear, btn_clear1;
    private ProgressBar mProgressBar;
    private List<BusinessModel> businessNameList, businessList;
    private List<BusinessTypeModel> businessTypeList;

    private static final String KEY_WORDS = "BusinessSearchKeyword";
    private static final String BUSINESS_TYPE_ID = "BusinessTypeId";
    private int appLoginId;
    private String keyword, cityName;

    public BusinessSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

      //  ((BusinessDashboardActivity) getActivity()).setActionBarTitle("Business Search");

        View view = inflater.inflate(R.layout.fragment_business_search, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_search_businessList);
        mProgressBar = view.findViewById(R.id.progressBar_search);
        btn_name = view.findViewById(R.id.btn_search_name);
        search_businessName = view.findViewById(R.id.edt_busi_search_name);
        btn_clear = view.findViewById(R.id.btn_busi_search_clear);
        spinner_businessType = view.findViewById(R.id.spinner_search_business_type);


        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        appLoginId = mSharedPreferences.getInt("appLoginId", 0);

        businessNameList = new ArrayList<>();
        businessTypeList = new ArrayList<>();
        businessList = new ArrayList<>();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        search_businessName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (search_businessName.getText().toString().trim().length() > 0) {

                    btn_clear.setVisibility(View.VISIBLE);
                } else {

                    btn_clear.setVisibility(View.INVISIBLE);
                }
            }
        });


        btn_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                keyword = search_businessName.getText().toString().trim();

                if (TextUtils.isEmpty(keyword) || keyword.length() <= 2) {

                    search_businessName.setError("Please enter keyword");
                } else {

                    businessNameList.clear();
                    loadLoadBusinessList(keyword);

                    search_businessName.setText(keyword);
                    businessList.clear();
                }
            }
        });


        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search_businessName.setText("");
                businessNameList.clear();
            }
        });


        loadBusinessType();

        spinner_businessType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BusinessTypeModel typeModel = (BusinessTypeModel) parent.getItemAtPosition(position);

                int businessId = typeModel.getBusinessId();
                businessList.clear();
                loadBusinessListUsingBusiId(businessId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }


    private void loadLoadBusinessList(final String keyword) {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.business_keyword_search_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "business list response" + response);
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
                                    businessNameList.add(model);

                                }

                                BusinessListAdapter adapter = new BusinessListAdapter(getActivity(), businessNameList);
                                mRecyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "business list error" + error);
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> businessParam = new HashMap<>();
                businessParam.put(KEY_WORDS, keyword);
                return businessParam;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void loadBusinessType() {

        mProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.business_type_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, "business type response" + response);
                        mProgressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONArray jsonArray = null;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int bTypeId = object.getInt("businessTypeId");
                                    String bTypeName = object.getString("businessType");

                                    BusinessTypeModel typeModel = new BusinessTypeModel(bTypeId, bTypeName);

                                    businessTypeList.add(typeModel);

                                }

                                BusinessTypeAdapter adapter = new BusinessTypeAdapter(getActivity(), businessTypeList);
                                spinner_businessType.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "business type error" + error);
                    }
                });

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
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

                                BusinessListAdapter adapter = new BusinessListAdapter(getActivity(), businessList);
                                mRecyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            } else {

                                Toast.makeText(getActivity(), "business list not found", Toast.LENGTH_SHORT).show();
                                businessList.clear();
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

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(busiListRequest);
    }
}
