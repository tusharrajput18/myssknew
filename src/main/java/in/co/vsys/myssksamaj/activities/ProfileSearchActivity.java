package in.co.vsys.myssksamaj.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import in.co.vsys.myssksamaj.adapter.CustomSpinnerAdapter;
import in.co.vsys.myssksamaj.adapter.MTPopAdapter;
import in.co.vsys.myssksamaj.contracts.AllAnnualIncomeContract;
import in.co.vsys.myssksamaj.contracts.AllOccupationContract;
import in.co.vsys.myssksamaj.interfaces.DisplaySelectedValue;
import in.co.vsys.myssksamaj.model.DrawerItem;
import in.co.vsys.myssksamaj.model.data_models.AllAnnualIncomeModel;
import in.co.vsys.myssksamaj.model.data_models.OccupationModel;
import in.co.vsys.myssksamaj.presenters.AllAnnualIncomePresenter;
import in.co.vsys.myssksamaj.presenters.AllOccupationPresenter;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

public class ProfileSearchActivity extends AppCompatActivity implements DisplaySelectedValue,
        AllOccupationContract.AllOccupationView, AllAnnualIncomeContract.AllAnnualIncomeView {

    private Context mContext;
    private static final String TAG = ProfileSearchActivity.class.getSimpleName();
    private static final String STATE_ID = "StateId";
    private static final String COUNTRY_STATE_ID = "CountryID";
    private ProgressBar mProgressBar;
    private AppCompatRadioButton radio_male, radio_female, radio_phusical_disable_yes, radio_phusical_disable_no;
    private ArrayList<DrawerItem> marriedList;
    private ArrayList<String> heightList;
    private ArrayList<DrawerItem> eductionInList;
    private ArrayList<DrawerItem> annualIncomeList;
    private ArrayList<DrawerItem> motherTougueLIst, stateList, cityList, countryList;
    private SharedPreferences mPreferences;
    private String S_minHeight = "", S_maxHeight = "", S_minAge = "", S_maxAge = "";
    private String S_Gendor = "";
    private int isOnline;
    private String manglik;
    private String S_motherTongueSelectedId, S_marriedStatus, S_stateSelectedId, S_countrySelectedId, S_citySelectedId;

    private RadioGroup onlineStatus;

    private String selectId, selectValue;
    private AppCompatEditText edt_search, edt_searchName;
    private AppCompatSpinner search_minAgeSpinner, search_maxAgeSpinner, search_minHeight, search_maxHeight;
    private AppCompatButton btn_search, btn_filter, btn_searchName;
    private LinearLayout layout_motherTougue, layout_marriedStatus, layout_country, layout_state, layout_city, layout_eduction, layout_income;
    private TextView tv_motherToungue, tv_country, tv_state, tv_city, tv_marriedStatus, tv_eduction, tv_annualIncome;

    private AllOccupationContract.AllOccupationOPS mAllOccupationPresenter;
    private AllAnnualIncomeContract.AllAnnualIncomeOPS mAllAnnualIncomePresenter;

    private AppCompatSpinner spnAnnualIncome, spnOccupation;
    private String strOccupation, strIncome, strPhisicalDisablity;
    private RadioGroup radiogroup_SearchManglik;
    private RadioButton radio_yesManglik, radio_noManglik;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_search);
        mContext = this;

        edt_search = (AppCompatEditText) findViewById(R.id.edt_search_profileId);
        btn_search = (AppCompatButton) findViewById(R.id.btn_search_Search);

        edt_searchName = (AppCompatEditText) findViewById(R.id.edt_search_name);
        btn_searchName = (AppCompatButton) findViewById(R.id.btn_search_name);

        spnOccupation = findViewById(R.id.search_spn_profession);
        spnAnnualIncome = findViewById(R.id.search_spn_income);

        mAllOccupationPresenter = new AllOccupationPresenter(this);
        mAllAnnualIncomePresenter = new AllAnnualIncomePresenter(this);

        mAllOccupationPresenter.getAlloccuption();
        mAllAnnualIncomePresenter.allAnnualIncome();

        spnOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                OccupationModel model = (OccupationModel) adapterView.getItemAtPosition(i);
                strOccupation = model.getOccupationid();
                if (strOccupation.equalsIgnoreCase("0")) {
                    strOccupation = "";
                } else {
                    strOccupation = model.getOccupationid();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spnAnnualIncome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AllAnnualIncomeModel model = (AllAnnualIncomeModel) adapterView.getItemAtPosition(i);

                strIncome = model.getAnnualincomeid();

                if (strIncome.equalsIgnoreCase("0")) {
                    strIncome = "";
                } else {
                    strIncome = model.getAnnualincomeid();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btn_searchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Utilities.extractDataFromEditText(edt_searchName);
                if (Utilities.isEmpty(name)) {
                    Toast.makeText(mContext, "Please enter name to search", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(mContext, SearchDisplayActivity.class);
                intent.putExtra("searchName", name);
                startActivity(intent);
            }
        });

        onlineStatus = findViewById(R.id.online_rg);

        search_minAgeSpinner = (AppCompatSpinner) findViewById(R.id.search_minAgeSpinner);
        search_maxAgeSpinner = (AppCompatSpinner) findViewById(R.id.search_maxAgeSpinner);
        search_minHeight = (AppCompatSpinner) findViewById(R.id.search_minHeight);
        search_maxHeight = (AppCompatSpinner) findViewById(R.id.search_maxHeight);
        btn_filter = (AppCompatButton) findViewById(R.id.btn_search_FSearch);
        radio_male = (AppCompatRadioButton) findViewById(R.id.radio_search_groom);
        radio_female = (AppCompatRadioButton) findViewById(R.id.radio_search_bride);
        radio_phusical_disable_yes = (AppCompatRadioButton) findViewById(R.id.radio_search_phisical_disabilty_yes);
        radio_phusical_disable_no = (AppCompatRadioButton) findViewById(R.id.radio_search_phisical_disabilty_no);

        radiogroup_SearchManglik = (RadioGroup) findViewById(R.id.radiogroup_SearchManglik);
        radio_yesManglik = (RadioButton) findViewById(R.id.radio_yesManglik);
        radio_noManglik = (RadioButton) findViewById(R.id.radio_noManglik);

        radiogroup_SearchManglik.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkid) {
                manglik = checkid == R.id.radio_noManglik ? "No" : "Yes";
            }
        });

        onlineStatus = (RadioGroup) findViewById(R.id.online_rg);
        onlineStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isOnline = checkedId == R.id.online ? 1 : 0;
            }
        });

        layout_motherTougue = (LinearLayout) findViewById(R.id.layout_search_motherTongue);
        layout_country = (LinearLayout) findViewById(R.id.layout_search_country);
        layout_state = (LinearLayout) findViewById(R.id.layout_search_state);
        layout_city = (LinearLayout) findViewById(R.id.layout_search_city);
        layout_marriedStatus = (LinearLayout) findViewById(R.id.layout_search_marriedStatus);
        layout_eduction = (LinearLayout) findViewById(R.id.layout_search_eduction);
        layout_income = (LinearLayout) findViewById(R.id.layout_search_income);
        tv_motherToungue = (AppCompatTextView) findViewById(R.id.tv_filter_mTongue);
        tv_country = (AppCompatTextView) findViewById(R.id.tv_search_country);
        tv_state = (AppCompatTextView) findViewById(R.id.tv_search_state);
        tv_city = (AppCompatTextView) findViewById(R.id.tv_search_city);
        tv_eduction = (AppCompatTextView) findViewById(R.id.tv_search_eduction);
        tv_annualIncome = (AppCompatTextView) findViewById(R.id.tv_search_income);
        tv_marriedStatus = (AppCompatTextView) findViewById(R.id.tv_search_married_status);
        mProgressBar = (ProgressBar) findViewById(R.id.search_progressBar);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mPreferences.edit().remove("S_minAge").apply();
        mPreferences.edit().remove("S_maxAge").apply();
        mPreferences.edit().remove("S_minHeight").apply();
        mPreferences.edit().remove("S_maxHeight").apply();
        mPreferences.edit().remove("S_motherTongue").apply();
        mPreferences.edit().remove("S_marriedStatus").apply();
        mPreferences.edit().remove("S_countryId").apply();
        mPreferences.edit().remove("S_stateId").apply();
        mPreferences.edit().remove("S_cityId").apply();
        mPreferences.edit().remove("S_gender").apply();
        mPreferences.edit().remove("uniqueId").apply();

        init();

        countryList = new ArrayList<>();
        stateList = new ArrayList<>();
        cityList = new ArrayList<>();


        Toolbar mToolbar = (Toolbar) findViewById(R.id.profile_search_toolbar);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Search Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

//        S_motherTongueSelectedId = mPreferences.getString("search_motherToungeId", "");
//        S_marriedStatus = mPreferences.getString("search_marriedStatus", "");

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uniqueId = edt_search.getText().toString().trim();
                if (uniqueId.length() > 3) {
                    Intent intent = new Intent(ProfileSearchActivity.this, SearchDisplayActivity.class);
                    mPreferences.edit().putString("uniqueId", uniqueId).apply();
                    intent.putExtra("searchProfileId", uniqueId);
                    startActivity(intent);
                } else {
                    Toast.makeText(ProfileSearchActivity.this, "Invalid Id...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radio_female.isChecked()) {
                    S_Gendor = "Female";
                }

                if (radio_male.isChecked()) {
                    S_Gendor = "Male";
                }

                if (radio_phusical_disable_no.isChecked()) {
                    strPhisicalDisablity = "No";
                }
                if (radio_phusical_disable_yes.isChecked()) {
                    strPhisicalDisablity = "Yes";
                }
                Intent intent = new Intent(ProfileSearchActivity.this, SearchListActivity.class);

//                mPreferences.edit().putString("S_minAge", S_minAge).apply();
//                mPreferences.edit().putString("S_maxAge", S_maxAge).apply();
//                mPreferences.edit().putString("S_minHeight", S_minHeight).apply();
//                mPreferences.edit().putString("S_maxHeight", S_maxHeight).apply();
//                mPreferences.edit().putString("S_motherTongue", S_motherTongueSelectedId).apply();
//                mPreferences.edit().putString("S_marriedStatus", S_marriedStatus).apply();
//isOnline
//                mPreferences.edit().putString("S_countryId", S_countrySelectedId).apply();
//                mPreferences.edit().putString("S_stateId", S_stateSelectedId).apply();
//                mPreferences.edit().putString("S_cityId", S_citySelectedId).apply();
//                mPreferences.edit().putString("S_gender", S_Gendor).apply();

                intent.putExtra(Constants.ShareableIntents.IS_ONLINE, isOnline);
                intent.putExtra(Constants.ShareableIntents.GENDER, S_Gendor);
                intent.putExtra(Constants.ShareableIntents.MIN_AGE, S_minAge);
                intent.putExtra(Constants.ShareableIntents.MAX_AGE, S_maxAge);
                intent.putExtra(Constants.ShareableIntents.MIN_HEIGHT, S_minHeight);
                intent.putExtra(Constants.ShareableIntents.MAX_HEIGHT, S_maxHeight);
                intent.putExtra(Constants.ShareableIntents.MOTHER_TONGUE, S_motherTongueSelectedId);
                intent.putExtra(Constants.ShareableIntents.MARRIED_STATUS, S_marriedStatus);
                intent.putExtra(Constants.ShareableIntents.COUNTRY_ID, S_countrySelectedId);
                intent.putExtra(Constants.ShareableIntents.STATE_ID, S_stateSelectedId);
                intent.putExtra(Constants.ShareableIntents.CITY_ID, S_citySelectedId);
                intent.putExtra(Constants.ShareableIntents.INCOME_ID, strIncome);
                intent.putExtra(Constants.ShareableIntents.OCCUPATION_ID, strOccupation);
                intent.putExtra(Constants.ShareableIntents.PHYSICALLY_DISABLE, strPhisicalDisablity);
                intent.putExtra(Constants.ShareableIntents.MANGLIK, manglik);

                Log.e(TAG, "isOnline: " + isOnline + "\n S_Gendor: " + S_Gendor + "\n S_minAge: " + S_minAge + "\n S_maxAge: " + S_maxAge + "\n S_minHeight: " + S_minHeight + "\n S_motherTongueSelectedId: " + S_motherTongueSelectedId + "\n S_marriedStatus: " + S_marriedStatus + "\n S_countrySelectedId: " + S_countrySelectedId + "\n S_stateSelectedId: " + S_stateSelectedId);
                Log.e(TAG, "strIncome: " + strIncome + "\n strOccupation:" + strOccupation + "\n strPhisicalDisablity:" + strPhisicalDisablity + "\n manglik:" + manglik);

                startActivity(intent);
            }
        });

        layout_motherTougue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMotherTongueTask();
            }
        });

        layout_marriedStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadMarriedList();
            }
        });


        layout_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadCountryTask();
            }
        });

        layout_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadStateTask(S_countrySelectedId);
            }
        });

        layout_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadCityTask(S_stateSelectedId);
            }
        });



       /* layout_eduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadEductionINTask();
            }
        });

        layout_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadAnnualIncomeTask();
            }
        });*/


    }

    private void loadMarriedList() {

        marriedList = new ArrayList<>();

        marriedList.add(new DrawerItem(1, "UnMarried"));
        marriedList.add(new DrawerItem(1, "Married"));
        marriedList.add(new DrawerItem(1, "Awaiting Divorce"));
        marriedList.add(new DrawerItem(1, "Divorced"));
        marriedList.add(new DrawerItem(1, "Widowed"));
        marriedList.add(new DrawerItem(1, "Annulled"));

        marriedStatus();
    }


    private void loadCountryTask() {

        countryList.clear();
        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.countryListUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.GONE);
                        Log.d(TAG, "countryList response :" + response);

                        try {

                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    DrawerItem item = new DrawerItem();

                                    item.setMotherTongueId(object.getInt("countrycode"));
                                    item.setMotherTongueName(object.getString("countryname"));

                                    if (!TextUtils.isEmpty(S_countrySelectedId) && S_countrySelectedId.length() > 0) {

                                        String s = S_countrySelectedId.replace(" ", "");

                                        String[] idArray = s.split(",");

                                        boolean found = checkIfExists(idArray, String.valueOf(item.getMotherTongueId()));

                                        if (found) {

                                            item.setSelected(true);

                                        } else {

                                            item.setSelected(false);
                                        }

                                    }

                                    countryList.add(item);
                                }

                                CountryPop();


                            } else {

                                Toast.makeText(ProfileSearchActivity.this, "Country not available", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "countryList Error :" + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void loadStateTask(final String cId) {

        stateList.clear();
        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.stateListUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.GONE);
                        Log.d(TAG, "StateList response :" + response);

                        try {

                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    DrawerItem item = new DrawerItem();

                                    item.setMotherTongueId(object.getInt("id"));
                                    item.setMotherTongueName(object.getString("statename"));


                                    if (!TextUtils.isEmpty(S_stateSelectedId) && S_stateSelectedId.length() > 0) {

                                        String s = S_stateSelectedId.replace(" ", "");

                                        String[] idArray = s.split(",");

                                        boolean found = checkIfExists(idArray, String.valueOf(item.getMotherTongueId()));

                                        if (found) {

                                            item.setSelected(true);

                                        } else {

                                            item.setSelected(false);
                                        }

                                    }


                                    stateList.add(item);
                                }


                                StatePOp();
                            } else {

                                Toast.makeText(ProfileSearchActivity.this, "State not available", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "state Error :" + error);
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> countryParam = new HashMap<>();

                countryParam.put(COUNTRY_STATE_ID, cId);

                return countryParam;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void CountryPop() {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(ProfileSearchActivity.this);

        aBuilder.setTitle("Select Country");
        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.row_listview, null);

        aBuilder.setView(convertView);
        ListView mListView = convertView.findViewById(R.id.pop_listView);

        SearchView mSearchView = convertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();

        final MTPopAdapter adapter = new MTPopAdapter(ProfileSearchActivity.this, countryList,
                ProfileSearchActivity.this);
        countryList.remove(0);
        mListView.setAdapter(adapter);
        aBuilder.setPositiveButton("Done", null);
        aBuilder.setNegativeButton("Cancel", null);
        final AlertDialog alert = aBuilder.create();
        alert.show();

        Button positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#FF0B8B42"));
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder builder = adapter.getSelectedName();
                StringBuilder idBuilder = adapter.getSelectedIds();

                String selectedName = builder.toString();

                if (!TextUtils.isEmpty(selectedName) && selectedName.length() > 0) {
                    tv_country.setText("" + selectedName);
                }

                String ids = idBuilder.toString();

                if (!TextUtils.isEmpty(ids) && ids.length() > 0) {
                    S_countrySelectedId = "";
                    S_countrySelectedId = ids;
                    mPreferences.edit().putString("search_countryId", S_countrySelectedId).apply();
                }
                alert.dismiss();
            }
        });

        Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFFF0400"));
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void loadCityTask(final String temp) {

        cityList.clear();
        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.multipleStateCityUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "CityList response :" + response);

                        try {

                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    DrawerItem item = new DrawerItem();

                                    item.setMotherTongueId(object.getInt("id"));
                                    item.setMotherTongueName(object.getString("cityname"));

                                    if (!TextUtils.isEmpty(S_citySelectedId) && S_citySelectedId.length() > 0) {

                                        String s = S_citySelectedId.replace(" ", "");

                                        String[] idArray = s.split(",");

                                        boolean found = checkIfExists(idArray, String.valueOf(item.getMotherTongueId()));

                                        if (found) {

                                            item.setSelected(true);

                                        } else {

                                            item.setSelected(false);
                                        }

                                    }

                                    cityList.add(item);
                                }

                                CityPOp();

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "city Error :" + error);
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(STATE_ID, temp);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void StatePOp() {


        AlertDialog.Builder aBuilder = new AlertDialog.Builder(ProfileSearchActivity.this);

        aBuilder.setTitle("Select State");
        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.row_listview, null);

        aBuilder.setView(convertView);
        ListView mListView = convertView.findViewById(R.id.pop_listView);
        SearchView mSearchView = convertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();


        final MTPopAdapter adapter = new MTPopAdapter(ProfileSearchActivity.this, stateList, ProfileSearchActivity.this);
        stateList.remove(0);
        mListView.setAdapter(adapter);

        // stateSearch(searchView, adapter);
        aBuilder.setPositiveButton("Done", null);
        aBuilder.setNegativeButton("Cancel", null);
        final AlertDialog alert = aBuilder.create();
        alert.show();

        Button positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#FF0B8B42"));
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder builder = adapter.getSelectedName();
                StringBuilder idBuilder = adapter.getSelectedIds();

                String selectedName = builder.toString();

                if (!TextUtils.isEmpty(selectedName) && selectedName.length() > 0) {

                    tv_state.setText("" + selectedName);
                }

                String ids = idBuilder.toString();

                if (!TextUtils.isEmpty(ids) && ids.length() > 0) {

                    S_stateSelectedId = "";

                    S_stateSelectedId = ids;
                }
                alert.dismiss();
            }
        });

        Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFFF0400"));
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.dismiss();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


    }

    private void CityPOp() {

        AlertDialog.Builder aBuilder = new AlertDialog.Builder(ProfileSearchActivity.this);

        aBuilder.setTitle("Select City");
        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.row_listview, null);

        aBuilder.setView(convertView);
        ListView mListView = convertView.findViewById(R.id.pop_listView);

        SearchView mSearchView = convertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();

        final MTPopAdapter adapter = new MTPopAdapter(ProfileSearchActivity.this, cityList, ProfileSearchActivity.this);
        cityList.remove(0);
        mListView.setAdapter(adapter);
        aBuilder.setPositiveButton("Done", null);
        aBuilder.setNegativeButton("Cancel", null);
        final AlertDialog alert = aBuilder.create();
        alert.show();

        Button positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#FF0B8B42"));
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                StringBuilder builder = adapter.getSelectedName();
                StringBuilder idBuilder = adapter.getSelectedIds();

                String selectedName = builder.toString();

                if (!TextUtils.isEmpty(selectedName) && selectedName.length() > 0) {

                    tv_city.setText("" + selectedName);
                }

                String ids = idBuilder.toString();

                if (!TextUtils.isEmpty(ids) && ids.length() > 0) {

                    S_citySelectedId = "";

                    S_citySelectedId = ids;
                }

                alert.dismiss();

            }
        });

        Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFFF0400"));
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.dismiss();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    private void init() {


        /*marriedList.add("Married");
        marriedList.add("Awaiting Divorce");
        marriedList.add("Divorced");
        marriedList.add("Widowed");
        marriedList.add("Annulled");*/

        heightList = new ArrayList<>();
        heightList.add("--Select Height--");
        heightList.add("4.0 ft");
        heightList.add("4.1 ft");
        heightList.add("4.2 ft");
        heightList.add("4.3 ft");
        heightList.add("4.4 ft");
        heightList.add("4.5 ft");
        heightList.add("4.6 ft");
        heightList.add("4.7 ft");
        heightList.add("4.8 ft");
        heightList.add("4.9 ft");
        heightList.add("4.10 ft");
        heightList.add("4.11 ft");
        heightList.add("5.0 ft");
        heightList.add("5.1 ft");
        heightList.add("5.2 ft");
        heightList.add("5.3 ft");
        heightList.add("5.4 ft");
        heightList.add("5.5 ft");
        heightList.add("5.6 ft");
        heightList.add("5.7 ft");
        heightList.add("5.8 ft");
        heightList.add("5.9 ft");
        heightList.add("5.10 ft");
        heightList.add("5.11 ft");
        heightList.add("6.0 ft");
        heightList.add("6.1 ft");
        heightList.add("6.2 ft");
        heightList.add("6.3 ft");
        heightList.add("6.4 ft");
        heightList.add("6.5 ft");
        heightList.add("6.6 ft");
        heightList.add("6.7 ft");
        heightList.add("6.8 ft");
        heightList.add("6.9 ft");
        heightList.add("6.10 ft");
        heightList.add("6.11 ft");
        heightList.add("7.0 ft");

        ArrayAdapter<String> minAdapter = new ArrayAdapter<>(this, R.layout.row_search_spinner, getResources().getStringArray(R.array.drop_age));
        search_minAgeSpinner.setAdapter(minAdapter);
        search_maxAgeSpinner.setAdapter(minAdapter);

        ArrayAdapter<String> heightAdapter = new ArrayAdapter<String>(this, R.layout.row_search_spinner, heightList);
        search_minHeight.setAdapter(heightAdapter);
        search_maxHeight.setAdapter(heightAdapter);

       /* ArrayAdapter<String> marriedAdapter = new ArrayAdapter<String>(this, R.layout.row_search_spinner, marriedList);
        marriedSpinner.setAdapter(marriedAdapter);*/

        search_minAgeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    S_minAge = "";
                } else {

                    S_minAge = parent.getItemAtPosition(position).toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search_maxAgeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    S_maxAge = "";
                } else {

                    S_maxAge = parent.getItemAtPosition(position).toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search_minHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    S_minHeight = "";
                } else {

                    S_minHeight = parent.getItemAtPosition(position).toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search_maxHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    S_maxHeight = "";
                } else {

                    S_maxHeight = parent.getItemAtPosition(position).toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void loadMotherTongueTask() {

        motherTougueLIst = new ArrayList<>();

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.motherTongueUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "motherTongue :" + response);

                        try {

                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    DrawerItem motherToungueModel = new DrawerItem();

                                    motherToungueModel.setMotherTongueId(object.getInt("mothertongueid"));
                                    motherToungueModel.setMotherTongueName(object.getString("mothertongue"));

                                    if (!TextUtils.isEmpty(S_motherTongueSelectedId) && S_motherTongueSelectedId.length() > 0) {

                                        String s = S_motherTongueSelectedId.replace(" ", "");

                                        String[] idArray = s.split(",");

                                        boolean found = checkIfExists(idArray, String.valueOf(motherToungueModel.getMotherTongueId()));

                                        if (found) {

                                            motherToungueModel.setSelected(true);

                                        } else {

                                            motherToungueModel.setSelected(false);
                                        }

                                    }


                                    motherTougueLIst.add(motherToungueModel);
                                }

                                motherTouguePOp();


                            } else {

                                Toast.makeText(ProfileSearchActivity.this, "MotherList not available", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "motherList Error :" + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void motherTouguePOp() {


        AlertDialog.Builder aBuilder = new AlertDialog.Builder(ProfileSearchActivity.this);

        aBuilder.setTitle("Select MotherTongue");
        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.row_listview, null);

        aBuilder.setView(convertView);
        ListView mListView = convertView.findViewById(R.id.pop_listView);

        SearchView mSearchView = convertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();

        final MTPopAdapter adapter = new MTPopAdapter(ProfileSearchActivity.this, motherTougueLIst, ProfileSearchActivity.this);
        motherTougueLIst.remove(0);
        mListView.setAdapter(adapter);
        aBuilder.setPositiveButton("Done", null);
        aBuilder.setNegativeButton("Cancel", null);
        final AlertDialog alert = aBuilder.create();
        alert.show();

        Button positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#FF0B8B42"));
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder builder = adapter.getSelectedName();
                StringBuilder idBuilder = adapter.getSelectedIds();

                String selectedName = builder.toString();

                if (!TextUtils.isEmpty(selectedName) && selectedName.length() > 0) {

                    tv_motherToungue.setText("" + selectedName);
                }

                String ids = idBuilder.toString();

                if (!TextUtils.isEmpty(ids) && ids.length() > 0) {

                    S_motherTongueSelectedId = "";

                    S_motherTongueSelectedId = ids;

                    mPreferences.edit().putString("search_motherToungeId", S_motherTongueSelectedId).apply();
                }

                alert.dismiss();
            }
        });

        Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFFF0400"));
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.dismiss();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


    }

   /* private void StatePOp() {


        AlertDialog.Builder aBuilder = new AlertDialog.Builder(ProfileSearchActivity.this);

        aBuilder.setTitle("Select State");
        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.row_listview, null);

        aBuilder.setView(convertView);
        ListView mListView = convertView.findViewById(R.id.pop_listView);
        SearchView mSearchView = convertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();

        final MTPopAdapter adapter = new MTPopAdapter(ProfileSearchActivity.this, stateList, ProfileSearchActivity.this);
        stateList.remove(0);
        mListView.setAdapter(adapter);

        // stateSearch(searchView, adapter);
        aBuilder.setPositiveButton("Done", null);
        aBuilder.setNegativeButton("Cancel", null);
        final AlertDialog alert = aBuilder.create();
        alert.show();

        Button positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#FF0B8B42"));
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<DrawerItem> mArrayMToungue = adapter.getCheckedItems();


                if (selectValue != null) {

                    String mTougueName1 = selectValue.substring(1, selectValue.length() - 1);
                    tv_state.setText(mTougueName1);
                    Log.d("selectedName", mTougueName1);
                    // Toast.makeText(ProfileSearchActivity.this, "Selected Items: " + mTougueName1, Toast.LENGTH_SHORT).show();
                }


                if (selectId != null) {

                    S_stateId = selectId.substring(1, selectId.length() - 1);
                }
                Log.d("selectedId", S_stateId);

                alert.dismiss();
            }
        });

        Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFFF0400"));
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.dismiss();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


    }

    private void CityPOp() {

        AlertDialog.Builder aBuilder = new AlertDialog.Builder(ProfileSearchActivity.this);

        aBuilder.setTitle("Select City");
        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.row_listview, null);

        aBuilder.setView(convertView);
        ListView mListView = convertView.findViewById(R.id.pop_listView);

        SearchView mSearchView = convertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();

        final MTPopAdapter adapter = new MTPopAdapter(ProfileSearchActivity.this, cityList, ProfileSearchActivity.this);
        cityList.remove(0);
        mListView.setAdapter(adapter);
        aBuilder.setPositiveButton("Done", null);
        aBuilder.setNegativeButton("Cancel", null);
        final AlertDialog alert = aBuilder.create();
        alert.show();

        Button positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#FF0B8B42"));
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<DrawerItem> mArrayMToungue = adapter.getCheckedItems();


                if (selectValue != null) {

                    String mTougueName1 = selectValue.substring(1, selectValue.length() - 1);
                    tv_city.setText(mTougueName1);
                    Log.d("selectedName", mTougueName1);
                    // Toast.makeText(ProfileSearchActivity.this, "Selected Items: " + mTougueName1, Toast.LENGTH_SHORT).show();
                }


                if (selectId != null) {

                    S_cityId = selectId.substring(1, selectId.length() - 1);
                }


                Log.d("selectedId", S_cityId);

                alert.dismiss();
            }
        });

        Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFFF0400"));
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.dismiss();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


    }*/

    private void marriedStatus() {

        AlertDialog.Builder aBuilder = new AlertDialog.Builder(ProfileSearchActivity.this);

        aBuilder.setTitle("Select Married Status");
        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.row_listview, null);

        aBuilder.setView(convertView);
        ListView mListView = convertView.findViewById(R.id.pop_listView);

        SearchView mSearchView = convertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();

        final MTPopAdapter adapter = new MTPopAdapter(ProfileSearchActivity.this, marriedList, ProfileSearchActivity.this);
        //  cityList.remove(0);
        mListView.setAdapter(adapter);
        aBuilder.setPositiveButton("Done", null);
        aBuilder.setNegativeButton("Cancel", null);
        final AlertDialog alert = aBuilder.create();
        alert.show();

        Button positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#FF0B8B42"));
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder builder = adapter.getSelectedName();

                String selectedName = builder.toString();

                if (!TextUtils.isEmpty(selectedName) && selectedName.length() > 0) {

                    tv_marriedStatus.setText("" + selectedName);
                    S_marriedStatus = "";

                    S_marriedStatus = selectedName;

                    mPreferences.edit().putString("search_marriedStatus", S_marriedStatus).apply();
                }
                alert.dismiss();
            }
        });

        Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFFF0400"));
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.dismiss();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }

   /* private void CountryPop() {

        AlertDialog.Builder aBuilder = new AlertDialog.Builder(ProfileSearchActivity.this);

        aBuilder.setTitle("Select Country");
        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.row_listview, null);

        aBuilder.setView(convertView);
        ListView mListView = convertView.findViewById(R.id.pop_listView);

        SearchView mSearchView = convertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();

        final MTPopAdapter adapter = new MTPopAdapter(ProfileSearchActivity.this, countryList, ProfileSearchActivity.this);
        countryList.remove(0);
        mListView.setAdapter(adapter);
        aBuilder.setPositiveButton("Done", null);
        aBuilder.setNegativeButton("Cancel", null);
        final AlertDialog alert = aBuilder.create();
        alert.show();


        Button positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#FF0B8B42"));
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<DrawerItem> mArrayMToungue = adapter.getCheckedItems();

                if (selectValue != null) {

                    String mTougueName1 = selectValue.substring(1, selectValue.length() - 1);
                    tv_country.setText(mTougueName1);
                    Log.d("selectedName", mTougueName1);
                    // Toast.makeText(ProfileSearchActivity.this, "Selected Items: " + mTougueName1, Toast.LENGTH_SHORT).show();
                }


                if (selectId != null) {

                    S_countryId = selectId.substring(1, selectId.length() - 1);
                }

                Log.d("selectedId", S_countryId);

                alert.dismiss();
            }
        });

        Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFFF0400"));
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.dismiss();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    private void EductionINPOp() {


        AlertDialog.Builder aBuilder = new AlertDialog.Builder(ProfileSearchActivity.this);

        aBuilder.setTitle("Select Eduction In");
        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.row_listview, null);

        aBuilder.setView(convertView);
        ListView mListView = convertView.findViewById(R.id.pop_listView);
        SearchView mSearchView = convertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();

        final MTPopAdapter adapter = new MTPopAdapter(ProfileSearchActivity.this, eductionInList, ProfileSearchActivity.this);
        eductionInList.remove(0);
        mListView.setAdapter(adapter);

        // stateSearch(searchView, adapter);
        aBuilder.setPositiveButton("Done", null);
        aBuilder.setNegativeButton("Cancel", null);
        final AlertDialog alert = aBuilder.create();
        alert.show();

        Button positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#FF0B8B42"));
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<DrawerItem> mArrayMToungue = adapter.getCheckedItems();
                //String temp = mArrayMToungue.toString();
                // String mTougueName = mSharedPreferences.getString("mTongueName", null);

                if (selectValue != null) {

                    String mTougueName1 = selectValue.substring(1, selectValue.length() - 1);
                    tv_eduction.setText(mTougueName1);
                    Log.d("selectedName", mTougueName1);
                    //  Toast.makeText(ProfileSearchActivity.this, "Selected Items: " + mTougueName1, Toast.LENGTH_SHORT).show();
                }

                //  String S_stateId1 = mSharedPreferences.getString("mTongueId", null);
                if (selectId != null) {

                    S_eductionIn = selectId.substring(1, selectId.length() - 1);
                }
                Log.d("selectedId", S_eductionIn);

                alert.dismiss();
            }
        });

        Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFFF0400"));
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.dismiss();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


    }

    private void loadEductionINTask() {

        eductionInList = new ArrayList<>();
        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.eductionInUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "Eduction IN Master response " + response);

                        try {
                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int eductionINId = object.getInt("educationinid");
                                    String eductionINName = object.getString("educationinname");


                                    DrawerItem eductionModel = new DrawerItem(eductionINId, eductionINName);
                                    eductionInList.add(eductionModel);
                                }

                                EductionINPOp();

                            } else {

                                Toast.makeText(ProfileSearchActivity.this, "list not found...!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "Eduction Master IN error " + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void loadAnnualIncomeTask() {

        annualIncomeList = new ArrayList<>();

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.annualIncomeUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "Annual Income Master response " + response);

                        try {
                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int annualIncomeId = object.getInt("annualincomeid");
                                    String annualIncomeName = object.getString("annualincome");


                                    DrawerItem eductionModel = new DrawerItem(annualIncomeId, annualIncomeName);
                                    annualIncomeList.add(eductionModel);
                                }

                                AnnualIncomePop();

                            } else {

                                Toast.makeText(ProfileSearchActivity.this, "income list not found...!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "annual income Master error " + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void AnnualIncomePop() {


        AlertDialog.Builder aBuilder = new AlertDialog.Builder(ProfileSearchActivity.this);

        aBuilder.setTitle("Select Annual Income");
        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.row_listview, null);

        aBuilder.setView(convertView);
        ListView mListView = convertView.findViewById(R.id.pop_listView);

        SearchView mSearchView = convertView.findViewById(R.id.searchView);

        mSearchView.setQueryHint("Search Here");
        mSearchView.setIconified(false);
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();


        final MTPopAdapter adapter = new MTPopAdapter(ProfileSearchActivity.this, annualIncomeList, ProfileSearchActivity.this);
        annualIncomeList.remove(0);
        mListView.setAdapter(adapter);

        // stateSearch(searchView, adapter);
        aBuilder.setPositiveButton("Done", null);
        aBuilder.setNegativeButton("Cancel", null);
        final AlertDialog alert = aBuilder.create();
        alert.show();

        Button positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#FF0B8B42"));
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<DrawerItem> mArrayMToungue = adapter.getCheckedItems();
                //String temp = mArrayMToungue.toString();
                // String mTougueName = mSharedPreferences.getString("mTongueName", null);

                if (selectValue != null) {

                    String mTougueName1 = selectValue.substring(1, selectValue.length() - 1);
                    tv_annualIncome.setText(mTougueName1);
                    Log.d("selectedName", mTougueName1);
                    //  Toast.makeText(ProfileSearchActivity.this, "Selected Items: " + mTougueName1, Toast.LENGTH_SHORT).show();
                }

                //  String S_stateId1 = mSharedPreferences.getString("mTongueId", null);
                if (selectId != null) {

                    S_annualIncome = selectId.substring(1, selectId.length() - 1);
                }
                Log.d("selectedId", S_eductionIn);

                alert.dismiss();
            }
        });

        Button negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FFFF0400"));
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.dismiss();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


    }*/

    @Override
    public void displayData(String selectedId, String selectedValue) {

        selectId = "" + selectedId;
        selectValue = "" + selectedValue;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

//                startActivity(new Intent(ProfileSearchActivity.this, HomeActivity.class));
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private static boolean checkIfExists(String[] myStringArray, String stringToLocate) {

        for (String element : myStringArray) {
            if (element.equals(stringToLocate)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void showAnnualIncome(final List<AllAnnualIncomeModel> annualIncomeList) {

        CustomSpinnerAdapter spnIncomeAdapter = new CustomSpinnerAdapter(mContext, annualIncomeList, R.layout.row_search_spinner, new CustomSpinnerAdapter.ItemClickedListener() {
            @Override
            public void onViewBound(View view, Object object, int position) {
                AllAnnualIncomeModel model = annualIncomeList.get(position);
                TextView text1 = view.findViewById(R.id.tv_spinner_heading);
                text1.setText(model.getAnnualincome());
            }
        });
        spnAnnualIncome.setAdapter(spnIncomeAdapter);

    }

    @Override
    public void showAllOccuption(final List<OccupationModel> occupationList) {
        CustomSpinnerAdapter spnOccupationAdapter = new CustomSpinnerAdapter(mContext, occupationList, R.layout.row_search_spinner, new CustomSpinnerAdapter.ItemClickedListener() {
            @Override
            public void onViewBound(View view, Object object, int position) {
                OccupationModel model = occupationList.get(position);
                TextView text1 = view.findViewById(R.id.tv_spinner_heading);
                text1.setText(model.getOccupation());
            }
        });
        spnOccupation.setAdapter(spnOccupationAdapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }
}
