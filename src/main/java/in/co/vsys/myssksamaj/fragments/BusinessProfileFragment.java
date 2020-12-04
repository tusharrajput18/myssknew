package in.co.vsys.myssksamaj.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessProfileFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    public static final String TAG = BusinessProfileFragment.class.getSimpleName();
    private int appLoginId;
    private static final String APP_LOGIN_ID = "MainAppId";
    private List<BusinessModel> businessLIst;

    public BusinessProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_business_profile, container, false);

        //((BusinessDashboardActivity) getActivity()).setActionBarTitle("Business Profile");

        mRecyclerView = view.findViewById(R.id.recycler_businessList);
        mProgressBar = view.findViewById(R.id.progressBar_businessList);

        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        appLoginId = mSharedPreferences.getInt("appLoginId", 0);

        businessLIst = new ArrayList<>();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        loadBusinessList();

        return view;
    }

    private void loadBusinessList() {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest businessRequest = new StringRequest(Request.Method.POST, Config.business_list_url,
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
                                    businessLIst.add(model);

                                }

                                BusinessListAdapter adapter = new BusinessListAdapter(getActivity(), businessLIst);
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
                businessParam.put(APP_LOGIN_ID, String.valueOf(appLoginId));
                return businessParam;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(businessRequest);
    }

}
