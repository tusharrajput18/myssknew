package in.co.vsys.myssksamaj.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.helpers.ConnectionHelper;
import in.co.vsys.myssksamaj.model.MemberModel;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

public class EditLifeStyleActivity extends AppCompatActivity {

    private Context mContext;
    private ProgressBar mProgressBar;
    private static final String MEMBER_ID = "MemberId";
    private static final String FOOD_TYPE = "FoodType";
    private static final String DRINK_HABIT = "DrinkHabit";
    private static final String SMOKE_HABIT = "SmokeHabit";
    private static final String COMPLEXION = "Complexion";
    private static final String BODY_TYPE = "BodyType";
    private static final String PHYSICAL_CHANLENGE = "PhysicalChallenge";
    private static final String LIFESTYLE_PROFILE_PERCENTAGE = "lifeStyleInfoPercentage";
    private SharedPreferences mPreferences;
    private int memberId;
    private String eatingHabit, drinkingHabit, smokingHabit, bodyType, complexion, physicalChallenge;
    private AppCompatRadioButton radioButton_veg, radioButton_non_veg, radio_drink_yes, radio_drink_no, radio_drink_occ, radio_smoke_yes, radio_physical_yes, radio_physical_no,
            radio_smoke_no, radio_smoke_occ;
    private AppCompatCheckBox chk_slim, chk_averge, chk_heavy, chk_fair, chk_wheatish, chk_whetish_brown, chk_dark;
    private ConnectionHelper mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matri_edit_life_style);
        mContext = this;

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_lifeStyle);

        radioButton_veg = (AppCompatRadioButton) findViewById(R.id.lifeStyleEditVeg);
        radioButton_non_veg = (AppCompatRadioButton) findViewById(R.id.lifeStyleEditNonVeg);
        radio_drink_yes = (AppCompatRadioButton) findViewById(R.id.lifeStyleEditDrinkYes);
        radio_drink_no = (AppCompatRadioButton) findViewById(R.id.lifeStyleEditDrinkNo);
        radio_drink_occ = (AppCompatRadioButton) findViewById(R.id.lifeStyleEditDrinkOcc);
        radio_smoke_yes = (AppCompatRadioButton) findViewById(R.id.lifeStyleEditSmokeYes);
        radio_smoke_no = (AppCompatRadioButton) findViewById(R.id.lifeStyleEditSmokeNo);
        radio_smoke_occ = (AppCompatRadioButton) findViewById(R.id.lifeStyleEditSmokeOcc);
        radio_physical_yes = (AppCompatRadioButton) findViewById(R.id.lifeStyle_radio_PhysicalYes);
        radio_physical_no = (AppCompatRadioButton) findViewById(R.id.lifeStyle_radio_PhysicalNo);
        chk_slim = (AppCompatCheckBox) findViewById(R.id.lifeStyleEditBodySlim);
        chk_averge = (AppCompatCheckBox) findViewById(R.id.lifeStyleEditBodyAverage);
        chk_heavy = (AppCompatCheckBox) findViewById(R.id.lifeStyleEditBodyHeavy);
        chk_fair = (AppCompatCheckBox) findViewById(R.id.lifeStyleEditComplexionFair);
        chk_wheatish = (AppCompatCheckBox) findViewById(R.id.lifeStyleEditComplexionWheatish);
        chk_whetish_brown = (AppCompatCheckBox) findViewById(R.id.lifeStyleEditComplexionWheatishBrown);
        chk_dark = (AppCompatCheckBox) findViewById(R.id.lifeStyleEditComplexionDark);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        memberId = mPreferences.getInt("memberId", 0);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_editLifeStyleInfo);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Edit Life Style Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        getIntentData();

        findViewById(R.id.btn_lifeStyleupdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ConnectionHelper.networkConnectivity(mContext)) {

                    if (radioButton_veg.isChecked()) {

                        eatingHabit = "Vegetarian";

                    } else if (radioButton_non_veg.isChecked()) {

                        eatingHabit = "Non-Vegetarian";
                    }

                    if (radio_drink_yes.isChecked()) {

                        drinkingHabit = "Yes";
                    } else if (radio_drink_no.isChecked()) {

                        drinkingHabit = "No";

                    } else if (radio_drink_occ.isChecked()) {

                        drinkingHabit = "Occasionally";
                    }

                    if (radio_smoke_yes.isChecked()) {

                        smokingHabit = "Yes";

                    } else if (radio_smoke_no.isChecked()) {

                        smokingHabit = "No";

                    } else if (radio_smoke_occ.isChecked()) {

                        smokingHabit = "Occasionally";
                    }

                    StringBuilder result = new StringBuilder();

                    if (chk_slim.isChecked()) {

                        result.append("Slim,");
                    }

                    if (chk_averge.isChecked()) {

                        result.append("Average,");
                    }

                    if (chk_heavy.isChecked()) {

                        result.append("Heavy");
                    }

                    bodyType = result.toString();

                    StringBuilder complexionResult = new StringBuilder();

                    if (chk_fair.isChecked()) {

                        complexionResult.append("Fair,");
                    }

                    if (chk_wheatish.isChecked()) {

                        complexionResult.append("Wheatish,");
                    }

                    if (chk_whetish_brown.isChecked()) {

                        complexionResult.append("Wheatish Brown,");
                    }

                    if (chk_dark.isChecked()) {

                        complexionResult.append("Dark");
                    }

                    complexion = complexionResult.toString();

                    if (radio_physical_yes.isChecked()) {

                        physicalChallenge = "Yes";

                    } else if (radio_physical_no.isChecked()) {

                        physicalChallenge = "No";
                    }

                    MemberModel memberModel = new MemberModel();

                    memberModel.setUserMemberId(memberId);
                    memberModel.setFoodType(eatingHabit);
                    memberModel.setDrinkingHabit(drinkingHabit);
                    memberModel.setSmokingHabit(smokingHabit);
                    memberModel.setPhysicalChallege(physicalChallenge);
                    memberModel.setBodyType(bodyType);
                    memberModel.setComplexion(complexion);

                    updateLifeStyle(memberModel);

                } else {

                    Toast.makeText(EditLifeStyleActivity.this, "No internet connection...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getIntentData() {

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            eatingHabit = bundle.getString("edit_lifeStyle_eating");
            drinkingHabit = bundle.getString("edit_lifeStyle_drinking");
            smokingHabit = bundle.getString("edit_lifeStyle_smoking");
            bodyType = bundle.getString("edit_lifeStyle_bodyType");
            complexion = bundle.getString("edit_lifeStyle_complexion");
            physicalChallenge = bundle.getString("edit_lifeStyle_physical");


            switch (eatingHabit) {

                case "Vegetarian":
                    radioButton_veg.setChecked(true);
                    break;

                case "Non-Vegetarian":
                    radioButton_non_veg.setChecked(true);
                    break;
            }

            switch (drinkingHabit) {
                case "Yes":

                    radio_drink_yes.setChecked(true);
                    break;
                case "No":
                    radio_drink_no.setChecked(true);
                    break;

                case "Occasionally":

                    radio_drink_occ.setChecked(true);
                    break;

                default:
                    radio_drink_no.setChecked(true);
                    break;
            }

            switch (smokingHabit) {

                case "Yes":
                    radio_smoke_yes.setChecked(true);
                    break;

                case "No":
                    radio_smoke_no.setChecked(true);
                    break;

                case "Occasionally":
                    radio_smoke_occ.setChecked(true);
                    break;

                default:
                    radio_drink_no.setChecked(true);
                    break;
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

            String[] complexionChecked = complexion.split(",");

            chk_fair.setChecked(false);
            chk_wheatish.setChecked(false);
            chk_whetish_brown.setChecked(false);
            chk_dark.setChecked(false);

            for (String checkedItem : complexionChecked) {

                if (chk_fair.getText().equals(checkedItem)) {

                    chk_fair.setChecked(true);
                }

                if (chk_wheatish.getText().equals(checkedItem)) {

                    chk_wheatish.setChecked(true);
                }

                if (chk_whetish_brown.getText().equals(checkedItem)) {

                    chk_whetish_brown.setChecked(true);
                }

                if (chk_dark.getText().equals(checkedItem)) {

                    chk_dark.setChecked(true);
                }
            }

            switch (physicalChallenge) {
                case "Yes":

                    radio_physical_yes.setChecked(true);
                    break;
                case "No":

                    radio_physical_no.setChecked(true);
                    break;

                default:

                    radio_physical_no.setChecked(true);
                    break;
            }

        }
    }

    private void updateLifeStyle(final MemberModel memberModel) {


        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.matrimony_life_style,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.GONE);

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                Toast.makeText(EditLifeStyleActivity.this, "Information updated successfully...", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditLifeStyleActivity.this, EditMyProfileActivity.class));

                            } else {

                                Toast.makeText(EditLifeStyleActivity.this, "Information not updated...", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        mProgressBar.setVisibility(View.GONE);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> param = new HashMap<>();
                param.put(MEMBER_ID, String.valueOf(memberModel.getUserMemberId()));
                param.put(FOOD_TYPE, memberModel.getFoodType());
                param.put(DRINK_HABIT, memberModel.getDrinkingHabit());
                param.put(SMOKE_HABIT, memberModel.getSmokingHabit());
                param.put(COMPLEXION, memberModel.getComplexion());
                param.put(BODY_TYPE, memberModel.getBodyType());
                param.put(PHYSICAL_CHANLENGE, memberModel.getPhysicalChallege());
                param.put(LIFESTYLE_PROFILE_PERCENTAGE, "10");

                return param;
            }
        };


        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditLifeStyleActivity.this, EditMyProfileActivity.class));
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
            //    startActivity(new Intent(EditLifeStyleActivity.this, EditMyProfileActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
