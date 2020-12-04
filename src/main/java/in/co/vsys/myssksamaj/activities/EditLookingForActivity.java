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
import android.support.v7.widget.AppCompatCheckBox;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CustomSpinnerAdapter;
import in.co.vsys.myssksamaj.adapter.MTPopAdapter;
import in.co.vsys.myssksamaj.interfaces.DisplaySelectedValue;
import in.co.vsys.myssksamaj.model.AnnualIncomeModel;
import in.co.vsys.myssksamaj.model.DrawerItem;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.Utilities;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditLookingForActivity extends AppCompatActivity implements DisplaySelectedValue {

    private Context mContext;
    private static final String TAG = EditLookingForActivity.class.getSimpleName();
    private AppCompatTextView tv_mToungue, tv_country, tv_state, tv_city, tv_eduction, tv_eductionIN,
            tv_workWith, tv_workAs, tv_mStatus;
    private ProgressBar mProgressBar;
    private AppCompatSpinner spinner_minAge, spinner_maxAge, spinner_minHeight, spinner_maxHeight, spinner_income;
    private AppCompatRadioButton radioButton_veg, radioButton_non_veg, radio_button_any, radio_drink_yes, radio_drink_no, radio_drink_any, radio_drink_occ, radio_smoke_yes, radio_smoke_any,
            radio_smoke_no, radio_smoke_occ;
    private AppCompatRadioButton radioButtonEating, radioButtonDrinking, radioButtonSmoking;
    private RadioGroup radioGroupDrinking, radioGroupSmoking, radioGroupEating;
    private AppCompatCheckBox chk_slim, chk_averge, chk_heavy;
    private AppCompatButton btn_update;
    private AppCompatEditText edt_introduction;
    private CustomSpinnerAdapter annualIncomeAdapter;
    private ArrayList<DrawerItem> marriedList;
    private List<AnnualIncomeModel> annualIncomeList;
    private ArrayList<DrawerItem> countryList;
    private ArrayList<DrawerItem> stateList;
    private ArrayList<DrawerItem> cityList;
    private ArrayList<DrawerItem> motherTougueLIst;
    private ArrayList<DrawerItem> occuptionList;
    private ArrayList<DrawerItem> workingWithList;
    private ArrayList<DrawerItem> eductionInList;
    private ArrayList<DrawerItem> educationTypeList;
    private static final String STATE_ID = "StateId";

    private static final String MEMBER_ID = "MemberId";
    private static final String MIN_AGE = "MinAge";
    private static final String MAX_AGE = "MaxAge";
    private static final String MIN_HEIGHT = "MinHeight";
    private static final String MAX_HEIGHT = "MaxHeight";
    private static final String PARTENER_MARRIED_STATUS = "PartnerMarriedStatus";
    private static final String EDUCTION_TYPE = "EducationType";
    private static final String EDUCTION_IN = "EducationIn";
    private static final String EDUCTION_WITH = "EducationWith";
    private static final String MOTHER_TONGUE = "MotherTongue";
    private static final String COUNTRY_ID = "Country";
    private static final String STATE_ID1 = "State";
    private static final String CITY_ID = "City";
    private static final String HIGHER_EDUCTION = "HigherEducation";
    private static final String OCCUPTION = "Occupation";
    private static final String INCOME = "Income";
    private static final String FOODTYPE = "FoodType";
    private static final String DRINK_HABIT = "DrinkHabit";
    private static final String SMOKE_HABIT = "SmokeHabit";
    private static final String COMPLEXION = "Complexion";
    private static final String BODY_TYPE = "BodyType";
    private static final String PHYSICAL_CHALLENED = "PhysicalChallenge";
    private static final String PARTNER_INTRODUCTION = "PartnerIntroduction";
    private static final String COUNTRY_STATE_ID = "CountryID";
    private static final String LOOKING_PROFILE_PERCENTAGE = "DesiredPartnerInformationPercentage";

    private int annualId;
    private SharedPreferences mSharedPreferences;

    private String selectId, selectValue;
    private String minAge, maxAge, minHeght, maxHeight, married_status, country, state, city, eatingHabits, smokingHabits, drinkingHabits,
            bodyType = "", complextion = "", physicalChallenge, introduction, eduction, eductionIN, workingWith, workingAs, annualIncome, motherTounge;
    private String S_motherToungeSelectedId, S_eductionSelectedId, S_countrySelectedId, S_stateSelectedId, S_citySelectedId, S_eductionInSelectedId, S_workWithSelectedId, S_workAsSelectedId;
    private int memberId;
    private AppCompatCheckBox chk_fair, chk_wheatish, chk_wheatish_brown, chk_dark;
    private AppCompatRadioButton radioPhysicalYes, radioPhysicalNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matri_edit_looking);
        mContext = this;

        spinner_minAge = (AppCompatSpinner) findViewById(R.id.spinner_eLFor_minAge);
        spinner_maxAge = (AppCompatSpinner) findViewById(R.id.spinner_eLFor_maxAge);
        spinner_minHeight = (AppCompatSpinner) findViewById(R.id.spinner_eLFor_minHeight);
        spinner_maxHeight = (AppCompatSpinner) findViewById(R.id.spinner_eLFor_maxHeight);
        tv_mToungue = (AppCompatTextView) findViewById(R.id.tv_eLFor_mTounge);

        tv_country = (AppCompatTextView) findViewById(R.id.tv_eLFor_country);
        tv_state = (AppCompatTextView) findViewById(R.id.tv_eLFor_state);
        tv_city = (AppCompatTextView) findViewById(R.id.tv_eLFor_city);
        tv_eduction = (AppCompatTextView) findViewById(R.id.tv_eLFor_eduction);
        tv_eductionIN = (AppCompatTextView) findViewById(R.id.tv_eLFor_eductionIN);
        tv_workWith = (AppCompatTextView) findViewById(R.id.tv_eLFor_workingWith);
        tv_mStatus = (AppCompatTextView) findViewById(R.id.tv_eLFor_mStatus);
        tv_workAs = (AppCompatTextView) findViewById(R.id.tv_eLFor_workingAs);
        spinner_income = (AppCompatSpinner) findViewById(R.id.spinner_eLFor_aIncome);
        radioGroupEating = (RadioGroup) findViewById(R.id.radioGroup_eatingLookingFor);
        radioGroupSmoking = (RadioGroup) findViewById(R.id.radioGroup_smokingLookingFor);
        radioGroupDrinking = (RadioGroup) findViewById(R.id.radioGroup_drinkingLookingFor);
        radioButton_veg = (AppCompatRadioButton) findViewById(R.id.eLFor_radio_vegetarian);
        radioButton_non_veg = (AppCompatRadioButton) findViewById(R.id.eLFor_radio_non_veg);
        radio_button_any = (AppCompatRadioButton) findViewById(R.id.eLFor_radio_non_any);
        radio_drink_yes = (AppCompatRadioButton) findViewById(R.id.eLFor_radio_DrinkYes);
        radio_drink_no = (AppCompatRadioButton) findViewById(R.id.eLFor_radio_DrinkNo);
        radio_drink_occ = (AppCompatRadioButton) findViewById(R.id.eLFor_radio_DrinkOcc);
        radio_drink_any = (AppCompatRadioButton) findViewById(R.id.eLFor_radio_DrinkOcc_any);
        radio_smoke_yes = (AppCompatRadioButton) findViewById(R.id.eLFor_radio_SmokeYes);
        radio_smoke_no = (AppCompatRadioButton) findViewById(R.id.eLFor_radio_SmokeNo);
        radio_smoke_occ = (AppCompatRadioButton) findViewById(R.id.eLFor_radio_SmokeOcc);
        radio_smoke_any = (AppCompatRadioButton) findViewById(R.id.eLFor_radio_SmokeAny);
        chk_slim = (AppCompatCheckBox) findViewById(R.id.eLFor_chkSlim);
        chk_averge = (AppCompatCheckBox) findViewById(R.id.eLFor_chkAverage);
        chk_heavy = (AppCompatCheckBox) findViewById(R.id.eLFor_heavy);
        mProgressBar = (ProgressBar) findViewById(R.id.eLFor_progressBar);
        edt_introduction = (AppCompatEditText) findViewById(R.id.edt_about_partner);
        btn_update = (AppCompatButton) findViewById(R.id.btn_eLFor_update);
        chk_fair = (AppCompatCheckBox) findViewById(R.id.eLFor_ComplexionFair);
        chk_wheatish = (AppCompatCheckBox) findViewById(R.id.eLFor_ComplexionWheatish);
        chk_wheatish_brown = (AppCompatCheckBox) findViewById(R.id.eLFor_ComplexionWheatishBrown);
        chk_dark = (AppCompatCheckBox) findViewById(R.id.eLFor_ComplexionDark);
        radioPhysicalYes = (AppCompatRadioButton) findViewById(R.id.eLFor_radio_PhysicalYes);
        radioPhysicalNo = (AppCompatRadioButton) findViewById(R.id.eLFor_radio_PhysicalNo);

        countryList = new ArrayList<>();
        stateList = new ArrayList<>();
        cityList = new ArrayList<>();
        motherTougueLIst = new ArrayList<>();
        occuptionList = new ArrayList<>();
        educationTypeList = new ArrayList<>();
        eductionInList = new ArrayList<>();
        workingWithList = new ArrayList<>();
        annualIncomeList = new ArrayList<>();

        init();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_editLookingInfo);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Edit Looking for Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        getIntentData();

        loadAnnualIncomeTask();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        memberId = mSharedPreferences.getInt("memberId", 0);

        spinner_minAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorBlack));

                if (position == 0) {

                    spinner_minAge.setSelection(getIndex(spinner_minAge, minAge));

                }

                minAge = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_maxAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorBlack));
                if (position == 0) {

                    spinner_maxAge.setSelection(getIndex(spinner_maxAge, maxAge));

                }

                maxAge = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_minHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorBlack));
                if (position == 0) {

                    spinner_minHeight.setSelection(getIndex(spinner_minHeight, minHeght));

                }

                minHeght = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_maxHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorBlack));

                if (position == 0) {

                    spinner_maxHeight.setSelection(getIndex(spinner_maxHeight, maxHeight));

                }

                maxHeight = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       /* spinner_mStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorBlack));

                if (position == 0) {

                    spinner_mStatus.setSelection(getIndex(spinner_mStatus, married_status));

                }

                married_status = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        tv_mStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MarriedStatusPOp();
            }
        });

        tv_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadCountryTask();
            }
        });

        tv_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadStateTask(S_countrySelectedId);
            }
        });

        tv_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadCityTask(S_stateSelectedId);
            }
        });

        tv_mToungue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadMotherTongueTask();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int eatingSelectedId = radioGroupEating.getCheckedRadioButtonId();

                radioButtonEating = (AppCompatRadioButton) findViewById(eatingSelectedId);

                eatingHabits = "" + radioButtonEating.getText();

                int drinkingSelectedId = radioGroupDrinking.getCheckedRadioButtonId();

                radioButtonDrinking = (AppCompatRadioButton) findViewById(drinkingSelectedId);

                drinkingHabits = "" + radioButtonDrinking.getText();

                int smokingSelectedId = radioGroupSmoking.getCheckedRadioButtonId();

                radioButtonSmoking = (AppCompatRadioButton) findViewById(smokingSelectedId);

                smokingHabits = "" + radioButtonSmoking.getText();

                bodyType = "";

                if (chk_slim.isChecked()) {

                    bodyType = "" + chk_slim.getText();
                }

                if (chk_averge.isChecked()) {

                    bodyType = bodyType + "," + chk_averge.getText();
                }

                if (chk_heavy.isChecked()) {

                    bodyType = bodyType + "," + chk_heavy.getText();
                }

                StringBuilder complexionResult = new StringBuilder();

                if (chk_fair.isChecked()) {

                    complexionResult.append("Fair,");
                }

                if (chk_wheatish.isChecked()) {

                    complexionResult.append("Wheatish,");
                }

                if (chk_wheatish_brown.isChecked()) {

                    complexionResult.append("Wheatish Brown,");
                }

                if (chk_dark.isChecked()) {

                    complexionResult.append("Dark");
                }

                complextion = complexionResult.toString();

                if (radioPhysicalYes.isChecked()) {

                    physicalChallenge = "" + radioPhysicalYes.getText();
                } else if (radioPhysicalNo.isChecked()) {

                    physicalChallenge = "" + radioPhysicalNo.getText();
                }


                introduction = edt_introduction.getText().toString().trim();

                updateLookingFor();
            }
        });

        tv_eduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadAllEductionMasterTask();
            }
        });

        tv_eductionIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadEductionINTask();
            }
        });

        tv_workWith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadWorkingWithTask();
            }
        });

        tv_workAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadOccuptionTask();
            }
        });

        spinner_income.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (position == 0) {

                    spinner_income.setSelection(getIndex(spinner_income, annualIncome));

                }

                AnnualIncomeModel annualIncomeModel = (AnnualIncomeModel) adapterView.getItemAtPosition(position);
                annualId = annualIncomeModel.getAnnualIncomeId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void init() {

        marriedList = new ArrayList<>();

        ArrayAdapter<String> minAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.drop_age));
        spinner_minAge.setAdapter(minAdapter);
        spinner_maxAge.setAdapter(minAdapter);

        ArrayAdapter<String> minHeightAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.drop_height));
        spinner_minHeight.setAdapter(minHeightAdapter);
        spinner_maxHeight.setAdapter(minHeightAdapter);

        marriedList.add(new DrawerItem(1, "Select Married Status"));
        marriedList.add(new DrawerItem(2, "UnMarried"));
        marriedList.add(new DrawerItem(3, "Married"));
        marriedList.add(new DrawerItem(4, "Awaiting Divorce"));
        marriedList.add(new DrawerItem(5, "Divorced"));
        marriedList.add(new DrawerItem(6, "Widowed"));
        marriedList.add(new DrawerItem(7, "Annulled"));

     /*   ArrayAdapter<DrawerItem> marriedAdapter = new ArrayAdapter<DrawerItem>(this, android.R.layout.simple_spinner_dropdown_item, marriedList);
        spinner_mStatus.setAdapter(marriedAdapter);*/
    }

    private void getIntentData() {

        Bundle lookingIntent = getIntent().getExtras();

        if (lookingIntent != null) {

            minAge = lookingIntent.getString("edit_pMinAge");
            maxAge = lookingIntent.getString("edit_pMaxAge");
            String mHeight = lookingIntent.getString("edit_pMinHeight");

            if (!TextUtils.isEmpty(mHeight) && mHeight.length() > 0) {

                minHeght = mHeight.replace(" Ft", "");
            }

            String maxHei = lookingIntent.getString("edit_pMaxHeight");

            if (!TextUtils.isEmpty(maxHei) && maxHei.length() > 0) {

                maxHeight = maxHei.replace(" Ft", "");
            }

            married_status = lookingIntent.getString("edit_pMarriedStatus");
            country = lookingIntent.getString("edit_pCountry");
            state = lookingIntent.getString("edit_pState");
            city = lookingIntent.getString("edit_pCity");
            eatingHabits = lookingIntent.getString("edit_pDietaryHabit");
            drinkingHabits = lookingIntent.getString("edit_pDrinkingHabit");
            smokingHabits = lookingIntent.getString("edit_pSmokingHabit");
            bodyType = lookingIntent.getString("edit_pBodyType");
            complextion = lookingIntent.getString("edit_partner_complexion");
            physicalChallenge = lookingIntent.getString("edit_partner_physicalChallenged");
            Log.d(TAG, "getIntentData: " + bodyType);
            introduction = lookingIntent.getString("edit_partner_introduction");
            eduction = lookingIntent.getString("edit_parnter_eduction");
            eductionIN = lookingIntent.getString("edit_partner_eductionIn");
            workingWith = lookingIntent.getString("edit_partner_workWith");
            workingAs = lookingIntent.getString("edit_partner_workAs");
            annualIncome = lookingIntent.getString("edit_partner_income");
            motherTounge = lookingIntent.getString("edit_pMotherTounge");
            S_motherToungeSelectedId = lookingIntent.getString("edit_partner_motherTongueId");
            S_eductionSelectedId = lookingIntent.getString("edit_partner_eductionId");
            S_countrySelectedId = lookingIntent.getString("edit_partner_countryId");
            S_stateSelectedId = lookingIntent.getString("edit_partner_stateId");
            S_citySelectedId = lookingIntent.getString("edit_partner_cityId");
            S_eductionInSelectedId = lookingIntent.getString("edit_partner_eductionInId");
            S_workWithSelectedId = lookingIntent.getString("edit_partner_workingWithId");
            S_workAsSelectedId = lookingIntent.getString("edit_partner_workingAsId");

            Log.d(TAG, "getIntentData: " + S_citySelectedId);

            if (!married_status.equals("")) {

                tv_mStatus.setText(married_status);
            } else {

                tv_mStatus.setText("Select Married Status");
            }

            if (!country.equals("")) {

                tv_country.setText(country);
            } else {

                tv_country.setText("Select Country");
            }

            if (!state.equals("")) {

                tv_state.setText(state);
            } else {

                tv_state.setText("Select State");
            }

            if (!motherTounge.equals("")) {

                tv_mToungue.setText(motherTounge);
            } else {

                tv_mToungue.setText("Select Mother Tongue");
            }

            if (!city.equals("")) {

                tv_city.setText(city);
            } else {

                tv_city.setText("Select City");
            }

            if (!eduction.equals("")) {

                tv_eduction.setText(eduction);
            } else {

                tv_eduction.setText("Select Eduction");
            }

            if (!eductionIN.equals("")) {

                tv_eductionIN.setText(eductionIN);
            } else {

                tv_eductionIN.setText("Select Eduction In");
            }

            if (!workingWith.equals("")) {

                tv_workWith.setText(workingWith);

            } else {

                tv_workWith.setText("Select Working With");
            }

            if (!workingAs.equals("")) {

                tv_workAs.setText(workingAs);
            } else {

                tv_workAs.setText("Select Working As");
            }

            if (eatingHabits.equals("Vegetarian")) {

                radioButton_veg.setChecked(true);

            } else {

                radioButton_veg.setChecked(false);
                radioButton_non_veg.setChecked(true);
            }

            switch (drinkingHabits) {
                case "Yes":

                    radio_drink_yes.setChecked(true);
                    break;
                case "No":
                    radio_drink_no.setChecked(true);
                    break;

                case "Occasionally":

                    radio_drink_occ.setChecked(true);
                    break;

                case "Any":
                    radio_drink_any.setChecked(true);
                    break;
                default:

                    radio_drink_no.setChecked(true);
                    break;
            }

            switch (smokingHabits) {

                case "Yes":
                    radio_smoke_yes.setChecked(true);
                    break;

                case "No":
                    radio_smoke_no.setChecked(true);
                    break;

                case "Occasionally":
                    radio_smoke_occ.setChecked(true);
                    break;

                case "Any":
                    radio_smoke_any.setChecked(true);
                    break;

                default:
                    radio_drink_no.setChecked(true);
                    break;
            }

            switch (physicalChallenge) {

                case "Yes":
                    radioPhysicalYes.setChecked(true);
                    break;
                case "No":
                    radioPhysicalNo.setChecked(true);
                    break;

                default:
                    radioPhysicalNo.setChecked(true);
            }


            String[] checkedItems = bodyType.split(",");

            chk_slim.setChecked(false);
            chk_heavy.setChecked(false);
            chk_averge.setChecked(false);

            for (String checkedItem : checkedItems) {

                if (chk_slim.getText().equals(checkedItem)) {

                    chk_slim.setChecked(true);
                }

                if (chk_heavy.getText().equals(checkedItem)) {

                    chk_heavy.setChecked(true);
                }

                if (chk_averge.getText().equals(checkedItem)) {

                    chk_averge.setChecked(true);
                }
            }


            String[] complexionChecked = complextion.split(",");

            chk_fair.setChecked(false);
            chk_wheatish.setChecked(false);
            chk_wheatish_brown.setChecked(false);
            chk_dark.setChecked(false);

            for (String checkedItem : complexionChecked) {

                if (chk_fair.getText().equals(checkedItem)) {

                    chk_fair.setChecked(true);
                }

                if (chk_wheatish.getText().equals(checkedItem)) {

                    chk_wheatish.setChecked(true);
                }

                if (chk_wheatish_brown.getText().equals(checkedItem)) {

                    chk_wheatish_brown.setChecked(true);
                }

                if (chk_dark.getText().equals(checkedItem)) {

                    chk_dark.setChecked(true);
                }
            }


            if (!introduction.equals("")) {

                edt_introduction.setText(introduction);
            } else {

                edt_introduction.setHint("Enter Partner Introduction");
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

    private void updateLookingFor() {

        mProgressBar.setVisibility(View.VISIBLE);
        StringRequest lookingRequest = new StringRequest(Request.Method.POST, Config.member_looking_edit_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "edit looking: " + response);

                        try {
                            JSONObject object = new JSONObject(response);

                            int success = object.getInt("success");

                            if (success == 1) {

                                Toast.makeText(mContext, "Desired Partner Information Successfully Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(mContext, EditMyProfileActivity.class));
                            } else {

                                Toast.makeText(mContext, "Details not updated...", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.e(TAG, "onErrorResponse: ", error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put(MEMBER_ID, String.valueOf(memberId));
                params.put(MIN_AGE, minAge);
                params.put(MAX_AGE, maxAge);
                params.put(MIN_HEIGHT, minHeght);
                params.put(MAX_HEIGHT, maxHeight);
                params.put(PARTENER_MARRIED_STATUS, married_status);
                params.put(EDUCTION_TYPE, S_eductionSelectedId);
                params.put(EDUCTION_IN, S_eductionInSelectedId);
                params.put(EDUCTION_WITH, S_workWithSelectedId);
                params.put(MOTHER_TONGUE, S_motherToungeSelectedId);
                params.put(COUNTRY_ID, S_countrySelectedId);
                params.put(STATE_ID1, S_stateSelectedId);
                params.put(CITY_ID, S_citySelectedId);
                params.put(HIGHER_EDUCTION, "0");
                params.put(OCCUPTION, S_workAsSelectedId);
                params.put(INCOME, String.valueOf(annualId));
                params.put(FOODTYPE, eatingHabits);
                params.put(DRINK_HABIT, drinkingHabits);
                params.put(SMOKE_HABIT, smokingHabits);
                params.put(COMPLEXION, complextion);
                params.put(BODY_TYPE, bodyType);
                params.put(PHYSICAL_CHALLENED, physicalChallenge);
                params.put(PARTNER_INTRODUCTION, introduction);
                params.put(LOOKING_PROFILE_PERCENTAGE, "20");

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(lookingRequest);

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

                                Toast.makeText(mContext, "Country not available", Toast.LENGTH_SHORT).show();
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

                                Toast.makeText(mContext, "State not available", Toast.LENGTH_SHORT).show();
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

    private void loadMotherTongueTask() {

        motherTougueLIst.clear();

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

                                    DrawerItem item = new DrawerItem();

                                    item.setMotherTongueId(object.getInt("mothertongueid"));
                                    item.setMotherTongueName(object.getString("mothertongue"));

                                    if (!TextUtils.isEmpty(S_motherToungeSelectedId) && S_motherToungeSelectedId.length() > 0) {

                                        String s = S_motherToungeSelectedId.replace(" ", "");

                                        String[] idArray = s.split(",");

                                        boolean found = checkIfExists(idArray, String.valueOf(item.getMotherTongueId()));

                                        if (found) {

                                            item.setSelected(true);

                                        } else {

                                            item.setSelected(false);
                                        }

                                    }

                                    motherTougueLIst.add(item);
                                }

                                motherTouguePOp();


                            } else {

                                Toast.makeText(mContext, "MotherList not available", Toast.LENGTH_SHORT).show();
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

    private void CountryPop() {

        AlertDialog.Builder aBuilder = new AlertDialog.Builder(mContext);

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


        final MTPopAdapter adapter = new MTPopAdapter(mContext, countryList, EditLookingForActivity.this);
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


        AlertDialog.Builder aBuilder = new AlertDialog.Builder(EditLookingForActivity.this);

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


        final MTPopAdapter adapter = new MTPopAdapter(EditLookingForActivity.this, stateList, EditLookingForActivity.this);
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

    private void motherTouguePOp() {


        AlertDialog.Builder aBuilder = new AlertDialog.Builder(EditLookingForActivity.this);

        aBuilder.setTitle("Select MotherTongue");
        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.row_listview, null);

        aBuilder.setView(convertView);
        ListView mListView = convertView.findViewById(R.id.pop_listView);


        final MTPopAdapter adapter = new MTPopAdapter(EditLookingForActivity.this, motherTougueLIst, EditLookingForActivity.this);
        motherTougueLIst.remove(0);
        mListView.setAdapter(adapter);
        aBuilder.setPositiveButton("OK", null);
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

                    tv_mToungue.setText("" + selectedName);
                }

                String ids = idBuilder.toString();

                if (!TextUtils.isEmpty(ids) && ids.length() > 0) {

                    S_motherToungeSelectedId = "";
                    S_motherToungeSelectedId = ids;
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


    }

    private void loadAllEductionMasterTask() {

        educationTypeList.clear();
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

                                    DrawerItem item = new DrawerItem();

                                    item.setMotherTongueId(object.getInt("eductaionid"));
                                    item.setMotherTongueName(object.getString("educationname"));

                                    if (!TextUtils.isEmpty(S_eductionSelectedId) && S_eductionSelectedId.length() > 0) {

                                        String s = S_eductionSelectedId.replace(" ", "");

                                        String[] idArray = s.split(",");

                                        boolean found = checkIfExists(idArray, String.valueOf(item.getMotherTongueId()));

                                        if (found) {

                                            item.setSelected(true);

                                        } else {

                                            item.setSelected(false);
                                        }

                                    }

                                    educationTypeList.add(item);
                                }

                                EductionTypePOp();

                            } else {

                                Toast.makeText(EditLookingForActivity.this, "server not responding...!", Toast.LENGTH_SHORT).show();
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
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void EductionTypePOp() {


        AlertDialog.Builder aBuilder = new AlertDialog.Builder(EditLookingForActivity.this);

        aBuilder.setTitle("Select Eduction");
        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.row_listview, null);

        aBuilder.setView(convertView);

        ListView mListView = convertView.findViewById(R.id.pop_listView);
        SearchView searchView = (SearchView) convertView.findViewById(R.id.searchView);
        searchView.setQueryHint("Search here");
        searchView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        final MTPopAdapter adapter = new MTPopAdapter(EditLookingForActivity.this, educationTypeList, EditLookingForActivity.this);
        educationTypeList.remove(0);
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

                    tv_eduction.setText("" + selectedName);
                }

                String ids = idBuilder.toString();

                if (!TextUtils.isEmpty(ids) && ids.length() > 0) {

                    S_eductionSelectedId = "";
                    S_eductionSelectedId = ids;
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


    }

    private void CityPOp() {

        AlertDialog.Builder aBuilder = new AlertDialog.Builder(EditLookingForActivity.this);

        aBuilder.setTitle("Select City");
        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.row_listview, null);

        aBuilder.setView(convertView);
        ListView mListView = convertView.findViewById(R.id.pop_listView);


        final MTPopAdapter adapter = new MTPopAdapter(EditLookingForActivity.this, cityList, EditLookingForActivity.this);
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

    }

    private void loadEductionINTask() {

        eductionInList.clear();
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

                                    DrawerItem drawerItem = new DrawerItem();

                                    drawerItem.setMotherTongueId(object.getInt("educationinid"));
                                    drawerItem.setMotherTongueName(object.getString("educationinname"));

                                    if (!TextUtils.isEmpty(S_eductionInSelectedId) && S_eductionInSelectedId.length() > 0) {

                                        String s = S_eductionInSelectedId.replace(" ", "");

                                        String[] idArray = s.split(",");

                                        boolean found = checkIfExists(idArray, String.valueOf(drawerItem.getMotherTongueId()));

                                        if (found) {

                                            drawerItem.setSelected(true);

                                        } else {

                                            drawerItem.setSelected(false);
                                        }

                                    }

                                    eductionInList.add(drawerItem);
                                }

                                EductionINPOp();

                            } else {

                                Toast.makeText(EditLookingForActivity.this, "server not responding...!", Toast.LENGTH_SHORT).show();
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

    private void EductionINPOp() {


        AlertDialog.Builder aBuilder = new AlertDialog.Builder(EditLookingForActivity.this);

        aBuilder.setTitle("Select Eduction In");
        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.row_listview, null);

        aBuilder.setView(convertView);
        ListView mListView = convertView.findViewById(R.id.pop_listView);
       /* SearchView searchView = (SearchView) convertView.findViewById(R.id.searchItem);
        searchView.setQueryHint("Search here");
        searchView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));*/

        final MTPopAdapter adapter = new MTPopAdapter(EditLookingForActivity.this, eductionInList, EditLookingForActivity.this);
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

                StringBuilder builder = adapter.getSelectedName();
                StringBuilder idBuilder = adapter.getSelectedIds();

                String selectedName = builder.toString();

                if (!TextUtils.isEmpty(selectedName) && selectedName.length() > 0) {

                    tv_eductionIN.setText("" + selectedName);
                }

                String ids = idBuilder.toString();

                if (!TextUtils.isEmpty(ids) && ids.length() > 0) {

                    S_eductionInSelectedId = "";
                    S_eductionInSelectedId = ids;
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


    }

    private void loadWorkingWithTask() {

        workingWithList.clear();

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

                                    DrawerItem workingWithModel = new DrawerItem();

                                    workingWithModel.setMotherTongueId(object.getInt("workwith"));
                                    workingWithModel.setMotherTongueName(object.getString("workwithname"));

                                    if (!TextUtils.isEmpty(S_workWithSelectedId) && S_workWithSelectedId.length() > 0) {

                                        String s = S_workWithSelectedId.replace(" ", "");

                                        String[] idArray = s.split(",");

                                        boolean found = checkIfExists(idArray, String.valueOf(workingWithModel.getMotherTongueId()));

                                        if (found) {

                                            workingWithModel.setSelected(true);

                                        } else {

                                            workingWithModel.setSelected(false);
                                        }

                                    }


                                    workingWithList.add(workingWithModel);
                                }

                                WorkingWithPOp();


                            } else {

                                Toast.makeText(EditLookingForActivity.this, "server not responding...!", Toast.LENGTH_SHORT).show();
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
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void WorkingWithPOp() {


        AlertDialog.Builder aBuilder = new AlertDialog.Builder(EditLookingForActivity.this);

        aBuilder.setTitle("Select Working With");
        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.row_listview, null);

        aBuilder.setView(convertView);
        ListView mListView = convertView.findViewById(R.id.pop_listView);
     /* SearchView searchView = (SearchView) convertView.findViewById(R.id.searchItem);
        searchView.setQueryHint("Search here");
        searchView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));*/

        final MTPopAdapter adapter = new MTPopAdapter(EditLookingForActivity.this, workingWithList, EditLookingForActivity.this);
        workingWithList.remove(0);
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

                    tv_workWith.setText("" + selectedName);
                }

                String ids = idBuilder.toString();

                if (!TextUtils.isEmpty(ids) && ids.length() > 0) {

                    S_workWithSelectedId = "";

                    S_workWithSelectedId = ids;
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


    }

    private void loadOccuptionTask() {

        occuptionList.clear();
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

                                    DrawerItem workingInModel = new DrawerItem();
                                    workingInModel.setMotherTongueId(object.getInt("occupationid"));
                                    workingInModel.setMotherTongueName(object.getString("occupation"));

                                    if (!TextUtils.isEmpty(S_workAsSelectedId) && S_workAsSelectedId.length() > 0) {

                                        String s = S_workAsSelectedId.replace(" ", "");

                                        String[] idArray = s.split(",");

                                        boolean found = checkIfExists(idArray, String.valueOf(workingInModel.getMotherTongueId()));

                                        if (found) {

                                            workingInModel.setSelected(true);

                                        } else {

                                            workingInModel.setSelected(false);
                                        }

                                    }


                                    occuptionList.add(workingInModel);
                                }

                                WorkingAsPOp();

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

    private void WorkingAsPOp() {


        AlertDialog.Builder aBuilder = new AlertDialog.Builder(EditLookingForActivity.this);

        aBuilder.setTitle("Select Working As");
        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.row_listview, null);

        aBuilder.setView(convertView);
        ListView mListView = convertView.findViewById(R.id.pop_listView);
      /* SearchView searchView = (SearchView) convertView.findViewById(R.id.searchItem);
        searchView.setQueryHint("Search here");
        searchView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));*/

        final MTPopAdapter adapter = new MTPopAdapter(EditLookingForActivity.this, occuptionList, EditLookingForActivity.this);
        occuptionList.remove(0);
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

                    tv_workAs.setText("" + selectedName);
                }

                String ids = idBuilder.toString();

                if (!TextUtils.isEmpty(ids) && ids.length() > 0) {

                    S_workAsSelectedId = "";

                    S_workAsSelectedId = ids;
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


    }

    private void MarriedStatusPOp() {


        AlertDialog.Builder aBuilder = new AlertDialog.Builder(EditLookingForActivity.this);

        aBuilder.setTitle("Select Married Status");
        LayoutInflater inflater = getLayoutInflater();

        View convertView = (View) inflater.inflate(R.layout.row_listview, null);

        aBuilder.setView(convertView);
        ListView mListView = convertView.findViewById(R.id.pop_listView);
      /* SearchView searchView = (SearchView) convertView.findViewById(R.id.searchItem);
        searchView.setQueryHint("Search here");
        searchView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));*/

        final MTPopAdapter adapter = new MTPopAdapter(EditLookingForActivity.this, marriedList, EditLookingForActivity.this);
        marriedList.remove(0);
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
                // StringBuilder idBuilder = adapter.getSelectedIds();

                String selectedName = builder.toString();

                if (!TextUtils.isEmpty(selectedName) && selectedName.length() > 0) {

                    tv_mStatus.setText("" + selectedName);
                    married_status = "" + selectedName;
                }

                /*String ids = idBuilder.toString();

                if (!TextUtils.isEmpty(ids) && ids.length() > 0) {

                    S_workAsSelectedId = "";

                    S_workAsSelectedId = ids;
                }*/

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
                                Log.e("anualIncomeSize",annualIncomeList.size()+"");
                                showAnnualIncomeList();

                            } else {

                                Toast.makeText(EditLookingForActivity.this, "server not responding...!", Toast.LENGTH_SHORT).show();
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

    private void showAnnualIncomeList() {
        annualIncomeAdapter = new CustomSpinnerAdapter(mContext, annualIncomeList,
                R.layout.row_text, new CustomSpinnerAdapter.ItemClickedListener() {
            @Override
            public void onViewBound(View view, Object object, int position) {
                try {
                    AnnualIncomeModel annualIncomeModel = (AnnualIncomeModel) object;

                    TextView title = view.findViewById(R.id.row_text);
                    title.setText(Utilities.getString(annualIncomeModel.getAnnualIncome()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        spinner_income.setAdapter(annualIncomeAdapter);

        annualIncomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayData(String selectedId, String selectedValue) {

        selectId = "" + selectedId;
        selectValue = "" + selectedValue;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
              //  startActivity(new Intent(EditLookingForActivity.this, EditMyProfileActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(EditLookingForActivity.this, EditMyProfileActivity.class));
    }*/

    private static boolean checkIfExists(String[] myStringArray, String stringToLocate) {

        for (String element : myStringArray) {
            if (element.equals(stringToLocate)) {
                return true;
            }
        }
        return false;
    }
}
