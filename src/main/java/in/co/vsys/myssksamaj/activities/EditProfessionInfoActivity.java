package in.co.vsys.myssksamaj.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.model.AnnualIncomeModel;
import in.co.vsys.myssksamaj.model.CountryModel;
import in.co.vsys.myssksamaj.model.EductionInModel;
import in.co.vsys.myssksamaj.model.EductionModel;
import in.co.vsys.myssksamaj.model.WorkingWithModel;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.Utilities;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

public class EditProfessionInfoActivity extends AppCompatActivity {

    private Context mContext;
    private AppCompatSpinner spinner_eduction, spinner_eduction_IN, spinner_workingWith, spinner_workingAs, spinner_AnnualIncome;
    private AppCompatButton btn_update;
    private EditText companyNameEdt;
    private ProgressBar mProgressBar;
    private static final String TAG = EditProfessionInfoActivity.class.getSimpleName();
    private List<CountryModel> workAsList;
    private List<EductionModel> eductionList;
    private List<EductionInModel> eductionInList;
    private List<WorkingWithModel> workingWithList;
    private List<AnnualIncomeModel> annualIncomeList;
    private static final String MEMBER_ID = "MemberId";
    private static final String HIGHER_EDUCTION_ID = "HigherEducationId";
    private static final String EDUCTION_TYPE = "EducationType";
    private static final String EDUCTION_IN = "EducationIn";
    private static final String WORKING_WITH = "EducationWith";
    private static final String WORKING_AS = "OccupationId";
    private static final String ANNUAL_INCOME = "Income";
    private static final String PROFESSION_PROFILE_PERCENTAGE = "HigherEducationInformation";
    private int eduId, eduInId, workAsId, workWithId, annualId, memberId;
    private SharedPreferences mSharedPreferences;
    private String eduction, eductionIn, workAs, workWith, annualIncome, companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matri_profession_edit);
        mContext = this;

        companyNameEdt = (EditText) findViewById(R.id.company_name);
        spinner_eduction = (AppCompatSpinner) findViewById(R.id.spinner_eProfession_eduction);
        spinner_eduction_IN = (AppCompatSpinner) findViewById(R.id.spinner_eProfession_eduction_IN);
        spinner_workingWith = (AppCompatSpinner) findViewById(R.id.spinner_eProfession_workingWith);
        spinner_workingAs = (AppCompatSpinner) findViewById(R.id.spinner_eProfession_workingAs);
        spinner_AnnualIncome = (AppCompatSpinner) findViewById(R.id.spinner_eProfession_AIncome);
        btn_update = (AppCompatButton) findViewById(R.id.btn_eProfession_update);
        mProgressBar = (ProgressBar) findViewById(R.id.my_eProfession_progressBar);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        memberId = mSharedPreferences.getInt("memberId", 0);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_editProfessionInfo);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Edit Profile Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        workAsList = new ArrayList<>();
        eductionInList = new ArrayList<>();
        eductionList = new ArrayList<>();
        workingWithList = new ArrayList<>();
        annualIncomeList = new ArrayList<>();

        Bundle profBundle = getIntent().getExtras();

        if (profBundle != null) {

            eduction = profBundle.getString("edit_eduction");
            eductionIn = profBundle.getString("edit_eductionIn");
            workWith = profBundle.getString("edit_workWith");
            workAs = profBundle.getString("edit_workAs");
            annualIncome = profBundle.getString("edit_annualIncome");
            companyName = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.COMPANY_NAME);
            companyNameEdt.setText(companyName);
        }

        loadOccuptionTask();
        loadEductionINTask();
        loadAllEductionMasterTask();
        loadWorkingWithTask();
        loadAnnualIncomeTask();

        spinner_eduction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    spinner_eduction.setSelection(getIndex(spinner_eduction, eduction));

                }

                EductionModel eductionModel = (EductionModel) parent.getItemAtPosition(position);
                eduId = eductionModel.getEductionId();
                Log.d(TAG, "educ Id" + eduId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_eduction_IN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    spinner_eduction_IN.setSelection(getIndex(spinner_eduction_IN, eductionIn));

                }

                EductionInModel eductionModel = (EductionInModel) parent.getItemAtPosition(position);
                eduInId = eductionModel.getEductionINId();
                Log.d(TAG, "educIn Id" + eduInId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_workingAs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    spinner_workingAs.setSelection(getIndex(spinner_workingAs, workAs));
                }


                CountryModel countryModel = (CountryModel) parent.getItemAtPosition(position);
                workAsId = countryModel.getCountryId();
                Log.d(TAG, "workAs Id" + workAsId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_workingWith.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) {

                    spinner_workingWith.setSelection(getIndex(spinner_workingWith, workWith));
                }

                WorkingWithModel withModel = (WorkingWithModel) parent.getItemAtPosition(position);
                workWithId = withModel.getWorkingWithId();
                Log.d(TAG, "workWith Id" + workWithId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_AnnualIncome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    spinner_AnnualIncome.setSelection(getIndex(spinner_AnnualIncome, annualIncome));
                }

                AnnualIncomeModel annualIncomeModel = (AnnualIncomeModel) parent.getItemAtPosition(position);
                annualId = annualIncomeModel.getAnnualIncomeId();
                Log.d(TAG, "annual Id" + annualId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProfessionTast();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                //startActivity(new Intent(mContext, EditMyProfileActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, EditMyProfileActivity.class));
    }*/

    private void loadOccuptionTask() {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.alloccuptionUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "workingAs response " + response);

                        try {
                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int occuptionId = object.getInt("occupationid");
                                    String occuption = object.getString("occupation");

                                    CountryModel countryModel = new CountryModel(occuptionId, occuption);
                                    workAsList.add(countryModel);
                                }

                                CustomSpinnerAdapter tempAdapter = new CustomSpinnerAdapter(mContext, workAsList,
                                        R.layout.row_simple_spinner, new CustomSpinnerAdapter.ItemClickedListener() {
                                    @Override
                                    public void onViewBound(View view, Object object, int position) {
                                        try {
                                            CountryModel countryModel = (CountryModel) object;

                                            TextView tv_rowTitle = view.findViewById(R.id.row_spin_heading);
                                            tv_rowTitle.setText(countryModel.getCountryName());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                spinner_workingAs.setAdapter(tempAdapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "working as master error " + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void loadAllEductionMasterTask() {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.eductionMasterUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "Eduction Master response " + response);

                        try {
                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int eductionId = object.getInt("eductaionid");
                                    String eductionName = object.getString("educationname");

                                    EductionModel eductionModel = new EductionModel(eductionId, eductionName);
                                    eductionList.add(eductionModel);
                                }

                                CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(mContext,
                                        eductionList, R.layout.row_simple_spinner, new CustomSpinnerAdapter.ItemClickedListener() {
                                    @Override
                                    public void onViewBound(View view, Object object, int position) {
                                        try {
                                            EductionModel eductionModel = (EductionModel) object;
                                            TextView tv_rowTitle = view.findViewById(R.id.row_spin_heading);
                                            tv_rowTitle.setText(eductionModel.getEductionMaster());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                spinner_eduction.setAdapter(customSpinnerAdapter);
                            } else {

                                Toast.makeText(mContext, "Eduction list not found...!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "Eduction Master error " + error);
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void loadEductionINTask() {

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


                                    EductionInModel eductionInModel = new EductionInModel(eductionINId, eductionINName);
                                    eductionInList.add(eductionInModel);
                                }

                                CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(mContext,
                                        eductionInList, R.layout.row_simple_spinner, new CustomSpinnerAdapter.ItemClickedListener() {
                                    @Override
                                    public void onViewBound(View view, Object object, int position) {
                                        try {
                                            EductionInModel eductionInModel = (EductionInModel) object;
                                            TextView tv_rowTitle = view.findViewById(R.id.row_spin_heading);
                                            tv_rowTitle.setText(eductionInModel.getEductionIn());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                spinner_eduction_IN.setAdapter(customSpinnerAdapter);

                            } else {

                                Toast.makeText(mContext, "server not responding...!", Toast.LENGTH_SHORT).show();
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
                });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void loadWorkingWithTask() {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.workingWithUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "Working with Master response " + response);

                        try {
                            JSONArray jsonArray;
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int workingWithId = object.getInt("workwith");
                                    String workingWithName = object.getString("workwithname");


                                    WorkingWithModel workingWithModel = new WorkingWithModel(workingWithId, workingWithName);
                                    workingWithList.add(workingWithModel);
                                }

                                CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(mContext,
                                        workingWithList, R.layout.row_simple_spinner, new CustomSpinnerAdapter.ItemClickedListener() {
                                    @Override
                                    public void onViewBound(View view, Object object, int position) {
                                        try {
                                            WorkingWithModel workingWithModel = (WorkingWithModel) object;
                                            TextView tv_rowTitle = view.findViewById(R.id.row_spin_heading);
                                            tv_rowTitle.setText(workingWithModel.getWorkingWith());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                spinner_workingWith.setAdapter(customSpinnerAdapter);

                            } else {

                                Toast.makeText(mContext, "server not responding...!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "Working with Master IN error " + error);
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void loadAnnualIncomeTask() {

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


                                    AnnualIncomeModel annualIncomeModel = new AnnualIncomeModel(annualIncomeId, annualIncomeName);
                                    annualIncomeList.add(annualIncomeModel);
                                }

                                CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(mContext, annualIncomeList, R.layout.row_simple_spinner,
                                        new CustomSpinnerAdapter.ItemClickedListener() {
                                            @Override
                                            public void onViewBound(View view, Object object, int position) {
                                                try {
                                                    AnnualIncomeModel annualIncomeModel = (AnnualIncomeModel) object;
                                                    TextView tv_rowTitle = view.findViewById(R.id.row_spin_heading);
                                                    tv_rowTitle.setText(annualIncomeModel.getAnnualIncome());
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                spinner_AnnualIncome.setAdapter(customSpinnerAdapter);
                            } else {

                                Toast.makeText(mContext, "server not responding...!", Toast.LENGTH_SHORT).show();
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
                });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void updateProfessionTast() {

        mProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.member_profession_edit_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.e(TAG, "edit profession response" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if (success == 1) {

                                Toast.makeText(mContext, "profession updated successfully...", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(mContext, EditMyProfileActivity.class));
                            } else {
                                Toast.makeText(mContext, getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "edit profession error" + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> editParam = new HashMap<>();
                editParam.put(MEMBER_ID, String.valueOf(memberId));
                editParam.put(HIGHER_EDUCTION_ID, "0");
                editParam.put(EDUCTION_TYPE, String.valueOf(eduId));
                editParam.put(EDUCTION_IN, String.valueOf(eduInId));
                editParam.put(WORKING_WITH, String.valueOf(workWithId));
                editParam.put(WORKING_AS, String.valueOf(workAsId));
                editParam.put(ANNUAL_INCOME, String.valueOf(annualId));
                editParam.put(PROFESSION_PROFILE_PERCENTAGE, "10");
                editParam.put(SharedPrefsHelper.COMPANY_NAME, Utilities.extractDataFromEditText(companyNameEdt));
                return editParam;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private int getIndex(AppCompatSpinner spinner, String string) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {

            String checkString = spinner.getItemAtPosition(i).toString();

            if (checkString != null && checkString.equalsIgnoreCase(string)) {

                index = i;
                break;
            }
        }

        return index;
    }
}