package in.co.vsys.myssksamaj.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CustomSpinnerAdapter;
import in.co.vsys.myssksamaj.model.CountryModel;
import in.co.vsys.myssksamaj.model.EductionModel;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditFamilyInfoActivity extends AppCompatActivity {

    private Context mContext;
    private ProgressBar mProgressBar;
    private AppCompatRadioButton radioButtonJoint, radioButtonNuclear;
    private AppCompatEditText edt_fatherPost, edt_fatherCompany, edt_motherPost, edt_motherCompany,
            edt_noOfBrothers, edt_noOfBrotherMarried,
            edt_noOfSisters, edt_noOfSistersMarried;
    private AppCompatSpinner spinner_fatherStatus, spinner_motherStatus;
    private AppCompatButton btn_update;
    private List<CountryModel> fatherStatusList;
    private List<EductionModel> motherStatusList;
    private LinearLayout fatherLayout, motherLayout;
    private int flag = 0;
    private String familyType, fatherStatus, fPost, fCompany, motherStatus, mPost, mCompany, noOfBrothers,
            noOfBrothersMarried, noOfSistersMarried, noOfSisters;

    private static final String TAG = EditFamilyInfoActivity.class.getSimpleName();
    private static final String MEMBER_ID = "MemberId";
    private static final String FAMILY_TYPE = "FamilyType";
    private static final String FATHER_OCCUPTION_ID = "FatherOccupationId";
    private static final String MOTHER_OCCUPTION_ID = "MotherOccupationId";
    private static final String FATHER_STATUS = "FatherStatus";
    private static final String FATHER_COMPANY = "FatherCompany";
    private static final String FATHER_POST = "FatherPost";
    private static final String MOTHER_STATUS = "MotherStatus";
    private static final String MOTHER_COMPANY = "MotherCompany";
    private static final String MOTHER_POST = "MotherPost";
    private static final String MARRIED_BROTHERS = "NoofBrotherMarried";
    private static final String NOT_MARRIED_BROTHERS = "NoofBrother";
    private static final String MARRIED_SISTERS = "NoofSister";
    private static final String NOT_MARRIED_SISTERS = "NoofSisterMarried";
    private static final String COUNTRY = "FamilyCountry";
    private static final String STATE = "FamilyState";
    private static final String CITY = "FamilyCity";
    private static final String ABOUT_FAMILY = "AboutFamily";
    private static final String FAMILY_PROFILE_PERCENTAGE = "FamilyInformationPercentage";
    private SharedPreferences mSharedPreferences;
    private int memberId;
    private TextView tv_no_b_married, tv_no_s_married;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matri_edit_family);
        mContext = this;

        radioButtonJoint = (AppCompatRadioButton) findViewById(R.id.radio_edit_familyJOint);
        radioButtonNuclear = (AppCompatRadioButton) findViewById(R.id.radio_edit_familyNuclear);
        edt_fatherCompany = (AppCompatEditText) findViewById(R.id.edt_edit_fatherCompany);
        edt_fatherPost = (AppCompatEditText) findViewById(R.id.edt_edit_fatherPost);
        edt_motherPost = (AppCompatEditText) findViewById(R.id.edt_edit_motherPost);
        edt_motherCompany = (AppCompatEditText) findViewById(R.id.edt_edit_motherCompany);
        edt_noOfBrothers = (AppCompatEditText) findViewById(R.id.edt_eFamily_brothers);
        edt_noOfBrotherMarried = (AppCompatEditText) findViewById(R.id.edt_eFamily_bMarried);
        edt_noOfSisters = (AppCompatEditText) findViewById(R.id.edt_eFamily_Sisters);
        edt_noOfSistersMarried = (AppCompatEditText) findViewById(R.id.edt_eFamily_sMarried);
        spinner_fatherStatus = (AppCompatSpinner) findViewById(R.id.spinner_eFamily_fStatus);
        spinner_motherStatus = (AppCompatSpinner) findViewById(R.id.spinner_eFamily_mStatus);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_family_edit);
        btn_update = (AppCompatButton) findViewById(R.id.btn_eFamily_update);
        fatherLayout = (LinearLayout) findViewById(R.id.father_layout);
        motherLayout = (LinearLayout) findViewById(R.id.mother_layout);
        tv_no_b_married = (TextView) findViewById(R.id.tv_no_of_b_m);
        tv_no_s_married = (TextView) findViewById(R.id.tv_no_of_s_m);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        memberId = mSharedPreferences.getInt("memberId", 0);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_editFamilyInfo);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Family Information");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        init();
        getIntentTask();

        spinner_fatherStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    spinner_fatherStatus.setSelection(getIndex(spinner_fatherStatus, fatherStatus));
                }

                flag = 1;
                if ((position == 1) || (position == 2) || (position == 3)) {
                    fatherLayout.setVisibility(View.VISIBLE);
                } else {
                    fatherLayout.setVisibility(View.GONE);
                }

                CountryModel countryModel = (CountryModel) parent.getItemAtPosition(position);
                fatherStatus = countryModel.getCountryName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_motherStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                flag = 2;

                if (position == 0) {
                    spinner_motherStatus.setSelection(getIndex(spinner_motherStatus, motherStatus));
                }

                if ((position == 1) || (position == 2) || (position == 3)) {
                    motherLayout.setVisibility(View.VISIBLE);
                } else {
                    motherLayout.setVisibility(View.GONE);
                }

                EductionModel eductionModel = (EductionModel) parent.getItemAtPosition(position);
                motherStatus = eductionModel.getEductionMaster();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edt_noOfBrothers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int value;

                try {

                    value = Integer.parseInt(String.valueOf(s));

                    if (value > 0) {

                        edt_noOfBrotherMarried.setVisibility(View.VISIBLE);
                        tv_no_b_married.setVisibility(View.VISIBLE);
                    } else {

                        edt_noOfBrotherMarried.setVisibility(View.GONE);
                        tv_no_b_married.setVisibility(View.GONE);
                        edt_noOfBrotherMarried.setText("0");
                    }

                } catch (NumberFormatException e) {

                    e.printStackTrace();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_noOfSisters.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int value;

                try {

                    value = Integer.parseInt(String.valueOf(s));

                    if (value > 0) {

                        edt_noOfSistersMarried.setVisibility(View.VISIBLE);
                        tv_no_s_married.setVisibility(View.VISIBLE);


                    } else {

                        edt_noOfSistersMarried.setVisibility(View.GONE);
                        tv_no_s_married.setVisibility(View.GONE);
                        edt_noOfSistersMarried.setText("0");
                    }

                } catch (NumberFormatException e) {

                    e.printStackTrace();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fCompany = edt_fatherCompany.getText().toString().trim();
                fPost = edt_fatherPost.getText().toString().trim();
                mCompany = edt_motherCompany.getText().toString().trim();
                mPost = edt_motherPost.getText().toString().trim();
                noOfBrothers = edt_noOfBrothers.getText().toString().trim();
                noOfBrothersMarried = edt_noOfBrotherMarried.getText().toString().trim();
                noOfSisters = edt_noOfSisters.getText().toString().trim();
                noOfSistersMarried = edt_noOfSistersMarried.getText().toString().trim();


                if (radioButtonJoint.isChecked()) {

                    familyType = "Joint";
                } else if (radioButtonNuclear.isChecked()) {

                    familyType = "Nuclear";
                }

                updateFamilyTask();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
//                startActivity(new Intent(EditFamilyInfoActivity.this, EditMyProfileActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditFamilyInfoActivity.this, EditMyProfileActivity.class));
    }*/
    private void init() {

        fatherStatusList = new ArrayList<>();
        motherStatusList = new ArrayList<>();

        fatherStatusList.add(new CountryModel("--Select Father Status--"));
        fatherStatusList.add(new CountryModel("Employed"));
        fatherStatusList.add(new CountryModel("Business"));
        fatherStatusList.add(new CountryModel("Retired"));
        fatherStatusList.add(new CountryModel("Not Employed"));
        fatherStatusList.add(new CountryModel("Passed Away"));

        motherStatusList.add(new EductionModel("--Select Mother Status--"));
        motherStatusList.add(new EductionModel("Employed"));
        motherStatusList.add(new EductionModel("Business"));
        motherStatusList.add(new EductionModel("Retired"));
        motherStatusList.add(new EductionModel("HouseWife"));
        motherStatusList.add(new EductionModel("Passed Away"));

        CustomSpinnerAdapter tempAdapter = new CustomSpinnerAdapter(mContext, fatherStatusList,
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

        spinner_fatherStatus.setAdapter(tempAdapter);

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(mContext, motherStatusList, R.layout.row_simple_spinner,
                new CustomSpinnerAdapter.ItemClickedListener() {
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
        spinner_motherStatus.setAdapter(customSpinnerAdapter);

    }

    private void updateFamilyTask() {

        mProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.member_family_edit_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.e(TAG, "edit family response" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if (success == 1) {

                                Toast.makeText(EditFamilyInfoActivity.this, "family info updated successfully...", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditFamilyInfoActivity.this, EditMyProfileActivity.class));
                            } else {
                                Toast.makeText(EditFamilyInfoActivity.this, getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "edit family info error" + error);
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> famParam = new HashMap<>();
                famParam.put(MEMBER_ID, String.valueOf(memberId));
                famParam.put(FAMILY_TYPE, familyType);
                famParam.put(FATHER_OCCUPTION_ID, "0");
                famParam.put(MOTHER_OCCUPTION_ID, "0");
                famParam.put(FATHER_STATUS, fatherStatus);
                famParam.put(FATHER_COMPANY, fCompany);
                famParam.put(FATHER_POST, fPost);
                famParam.put(MOTHER_STATUS, motherStatus);
                famParam.put(MOTHER_COMPANY, mCompany);
                famParam.put(MOTHER_POST, mPost);
                famParam.put(NOT_MARRIED_BROTHERS, noOfBrothers);
                famParam.put(MARRIED_BROTHERS, noOfBrothersMarried);
                famParam.put(NOT_MARRIED_SISTERS, noOfSisters);
                famParam.put(MARRIED_SISTERS, noOfSistersMarried);
                famParam.put(COUNTRY, "0");
                famParam.put(STATE, "0");
                famParam.put(CITY, "0");
                famParam.put(ABOUT_FAMILY, "0");
                famParam.put(FAMILY_PROFILE_PERCENTAGE, "10");
                return famParam;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void getIntentTask() {

        Bundle familyIntent = getIntent().getExtras();

        if (familyIntent != null) {

            familyType = familyIntent.getString("edit_familyType");
            fatherStatus = familyIntent.getString("edit_fatherStatus");
            fCompany = familyIntent.getString("edit_fatherCompany");
            fPost = familyIntent.getString("edit_fatherPost");
            motherStatus = familyIntent.getString("edit_motherStatus");
            mCompany = familyIntent.getString("edit_motherCompany");
            mPost = familyIntent.getString("edit_motherPost");
            noOfBrothers = familyIntent.getString("edit_noOfBrothers");
            noOfBrothersMarried = familyIntent.getString("edit_noOfBrotherMarried");
            noOfSisters = familyIntent.getString("edit_noOfSisters");
            noOfSistersMarried = familyIntent.getString("edit_noOfSisterMarried");

            if (familyType.equals("Joint")) {

                radioButtonJoint.setChecked(true);

            }
            if (familyType.equals("Nuclear")) {

                radioButtonNuclear.setChecked(true);

            }

            if (!fCompany.equals("")) {

                edt_fatherCompany.setText(fCompany);
            } else {

                edt_fatherCompany.setHint("Father Company");
            }

            if (!fPost.equals("")) {

                edt_fatherPost.setText(fPost);
            } else {

                edt_fatherPost.setHint("Father Post");
            }

            if (!mCompany.equals("")) {

                edt_motherCompany.setText(mCompany);
            } else {

                edt_motherCompany.setHint("Mother Company");
            }

            if (!mPost.equals("")) {

                edt_motherPost.setText(mPost);
            } else {

                edt_motherPost.setHint("Mother Post");
            }

            if (!noOfBrothers.equals("")) {

                edt_noOfBrothers.setText(noOfBrothers);
            } else {

                edt_noOfBrothers.setHint("Brothers");
            }

            if (!noOfBrothersMarried.equals("")) {

                edt_noOfBrotherMarried.setText(noOfBrothersMarried);
            } else {

                edt_noOfBrotherMarried.setHint("Brothers Married");
            }

            if (!noOfSisters.equals("")) {

                edt_noOfSisters.setText(noOfSisters);
            } else {

                edt_noOfSisters.setHint("Sisters");
            }

            if (!noOfSistersMarried.equals("")) {

                edt_noOfSistersMarried.setText(noOfSistersMarried);
            } else {

                edt_noOfSistersMarried.setHint("Sisters Married");
            }

        }

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



