package in.co.vsys.myssksamaj.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.easebuzz.payment.kit.PWECouponsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import datamodels.StaticDataModel;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CustomListAdapter;
import in.co.vsys.myssksamaj.contracts.SearchContract;
import in.co.vsys.myssksamaj.contracts.TransactionDataContract;
import in.co.vsys.myssksamaj.helpers.PaymentHelper;
import in.co.vsys.myssksamaj.interfaces.ListClickListener;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.presenters.SearchPresenter;
import in.co.vsys.myssksamaj.presenters.TransactionPresenter;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.EasebuzzConstants;
import in.co.vsys.myssksamaj.utils.Utilities;

public class SearchListActivity extends AppCompatActivity implements ListClickListener, TransactionDataContract.TransactionView,
        SearchContract.SearchView {

    private Context mContext;
    private static final String TAG = SearchListActivity.class.getSimpleName();
    private String mTransactionId = "", mPackageId = "1";
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private List<UserProfileModel> searchList = new ArrayList<>();
    private CustomListAdapter searchListAdapter;
    private SharedPreferences mPreferences;
    private int memberId, isOnline;
    private String minAge, maxAge, minHeight, maxHeight, gender, motherTongue, marriedStatus, countryId = "",
            stateId = "", cityId = "", strIncome, strProfession, strPhysicalDisable, manglik;

    private AlertDialog alertDialog;
    private TransactionDataContract.TransactionOps transactionDataPresenter;
    private SearchContract.SearchOps searchPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        mContext = this;

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

        transactionDataPresenter = new TransactionPresenter(this);
        searchPresenter = new SearchPresenter(this);

        searchPresenter.searchMember(gender, minAge, maxAge, minHeight, maxHeight, motherTongue, marriedStatus, countryId, stateId, cityId, String.valueOf(isOnline), strIncome, strProfession, strPhysicalDisable, manglik,String.valueOf(memberId));

        Log.e("Search Parameters", "gender:" + gender +
                "\n " + "Minage:" + minAge +
                "\n" + "maxage:" + maxAge +
                "\n"+"minHeight:" + minHeight +
                "\n " + "maxHeight:" + maxHeight +
                "\n" + "motherTongue:" + motherTongue +
                "\n" + "marriedStatus:" + marriedStatus +
                "\n " + "countryId:" + countryId +
                "\n" + "cityId:" + cityId +
                "\n" + "isOnline:" + isOnline +
                "\n " + "strIncome:" + strIncome +
                "\n" + "strProfession:" + strProfession);
        Log.e("search paramet", "strPhysicalDisable:" + strPhysicalDisable + "\n manglik" + manglik);
    }

    private void getData() {
        try {
            if (!Utilities.isIntentPresent(getIntent()))
                return;

            Intent intent = getIntent();
            gender = intent.getStringExtra(Constants.ShareableIntents.GENDER);

            minAge = intent.getStringExtra(Constants.ShareableIntents.MIN_AGE);
            maxAge = intent.getStringExtra(Constants.ShareableIntents.MAX_AGE);

            minHeight = intent.getStringExtra(Constants.ShareableIntents.MIN_HEIGHT);
            maxHeight = intent.getStringExtra(Constants.ShareableIntents.MAX_HEIGHT);


            strIncome = intent.getStringExtra(Constants.ShareableIntents.INCOME_ID);
            strProfession = intent.getStringExtra(Constants.ShareableIntents.OCCUPATION_ID);
            strPhysicalDisable = intent.getStringExtra(Constants.ShareableIntents.PHYSICALLY_DISABLE);

            Log.e(TAG, "strIncome: "+strIncome+"\n strProfession"+strProfession+"\n strPhysicalDisable"+strPhysicalDisable );

//            motherTongue = intent.getStringExtra(Constants.ShareableIntents.MOTHER_TONGUE);
//            marriedStatus = intent.getStringExtra(Constants.ShareableIntents.MARRIED_STATUS);

            /*countryId = intent.getStringExtra(Constants.ShareableIntents.COUNTRY_ID);
            stateId = intent.getStringExtra(Constants.ShareableIntents.STATE_ID);
            cityId = intent.getStringExtra(Constants.ShareableIntents.CITY_ID);

*/
            motherTongue = intent.getExtras().getString(Constants.ShareableIntents.MOTHER_TONGUE, "");
            marriedStatus = intent.getExtras().getString(Constants.ShareableIntents.MARRIED_STATUS, "");

            countryId = intent.getExtras().getString(Constants.ShareableIntents.COUNTRY_ID, "");
            stateId = intent.getExtras().getString(Constants.ShareableIntents.STATE_ID, "");
            cityId = intent.getExtras().getString(Constants.ShareableIntents.CITY_ID, "");
            manglik = intent.getExtras().getString(Constants.ShareableIntents.MANGLIK, "No");

            isOnline = getIntent().getIntExtra(Constants.ShareableIntents.IS_ONLINE, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                /*startActivity(new Intent(SearchListActivity.this, ProfileSearchActivity.class));
                finish();*/
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClicked(Object object) {
        AlertDialog.Builder db = new AlertDialog.Builder(mContext, R.style.MyAlertDialogStyle);
//        db.setView(dialog_layout);
        db.setTitle("Update subscription");
        db.setMessage(PaymentHelper.GET_PREMIUM);
        db.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                transactionDataPresenter.getTransactionData("" + memberId, mPackageId);
            }
        });

        db.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog = db.show();

    }

    @Override
    public void showTransactionData(String transactionId, String amount) {
        try {
            mTransactionId = transactionId;
            float fAmount = Float.parseFloat(amount);

            String memberId = "" + MainPresenter.getInstance().getMemberId(mContext);
            String memberName = MainPresenter.getInstance().getMemberName(mContext);
            String emailId = MainPresenter.getInstance().getEmailId(mContext);
            String phone = MainPresenter.getInstance().getPhone(mContext);
            String productInfo = "Yearly subscription";

            initiatePayment(mContext, memberId, transactionId, fAmount, productInfo, memberName, emailId, phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    // When payment is done through easebuzz then invoke this method.

    // This will set SharedPreferences and update the drawer view with appropriate "Paid/Premium member"
    private void successfulTransactionCompletion() {
        try {
            mPreferences.edit().putInt("userType", 1).apply();
            MainPresenter.getInstance().setUserType(mContext, 1);
            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transactionFailure() {
        try {
            mPreferences.edit().putInt("userType", 0).apply();
            MainPresenter.getInstance().setUserType(mContext, 0);
            Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showTransactionUpdate(String message) {
//        mDrawerLayout.closeDrawer(GravityCompat.START);

//        displayNavText(navigationView);
    }

    public void initiatePayment(Context context, String memberId, String transactionId, float amount,
                                String productInfo, String firstName, String emailId,
                                String phone) {
        try {
            Intent intent = new Intent(context, PWECouponsActivity.class);

            intent.putExtra("trxn_id", transactionId);
            intent.putExtra("trxn_amount", amount);
            intent.putExtra("trxn_prod_info", productInfo);
            intent.putExtra("trxn_firstname", firstName);
            intent.putExtra("trxn_email_id", emailId);
            intent.putExtra("trxn_phone", phone);
            intent.putExtra("trxn_key", PaymentHelper.MerchantKey);
            intent.putExtra("trxn_is_coupon_enabled", PaymentHelper.merchant_is_coupon_enabled);
            intent.putExtra("trxn_salt", PaymentHelper.Salt);
            intent.putExtra("unique_id", memberId);
            intent.putExtra("enable_save_card", 1);
            intent.putExtra("pay_mode", PaymentHelper.payment_mode);

            startActivityForResult(intent, StaticDataModel.PWE_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String result = data.getStringExtra("result");

        String status;
//        result = EasebuzzConstants.PAYMENT_SUCCESSFUL;

        /**
         * Note:
         * if not successful. Cancel is depicted by multiple string constants eg, user_cancelled, payment_failed, timeout etc
         * for other constants check the documentation of easebuzz.in here:
         * https://docs.easebuzz.in/mobile-integration-android/initiate-pymnt
         */
        if (result.equals(EasebuzzConstants.PAYMENT_SUCCESSFUL)) {
            status = "1";
            successfulTransactionCompletion();
        } else {
            status = "0";
            transactionFailure();
        }

        transactionDataPresenter.updateTransactionData("" + memberId, "" + mPackageId,
                mTransactionId, status);
    }

    @Override
    public void showSearchResults(List<UserProfileModel> userProfileModels) {
        Log.d(TAG, "searchData11111111111: " + userProfileModels.size());
        searchList.clear();
        searchList.addAll(userProfileModels);
        Log.d(TAG, "showSearchResults22222222222222222222: " + searchList.size());
        Log.e("SearchData", searchList.size() + "");
        searchListAdapter = new CustomListAdapter(mContext, mRecyclerView, searchList,
                R.layout.recyclerview_row, new CustomListAdapter.ItemClickedListener() {
            @Override
            public void onViewBound(View itemView, Object object, int position) {
                final UserProfileModel searchModel = (UserProfileModel) object;

                LinearLayout linearLayout;
                ImageView profileUrl, img_invite, img_invited, img_shortlisted, img_shortlist, img_chat;
                TextView tv_shortlist, tv_shortlisted, tv_userId, tv_userAge_height, tv_motherTounge,
                        tv_married_status, tv_profession, tv_income, tv_city_state_country, tv_invite, tv_invited;

                ImageView img_premium=itemView.findViewById(R.id.img_premium);

                profileUrl = itemView.findViewById(R.id.img_scroll_profileImage);
                img_invite = itemView.findViewById(R.id.img_scroll_invite);
                img_invited = itemView.findViewById(R.id.img_scroll_invited);
                img_shortlist = itemView.findViewById(R.id.img_scroll_shortlist);
                img_shortlisted = itemView.findViewById(R.id.img_scroll_shortlisted);
                img_chat = itemView.findViewById(R.id.img_scroll_chat);
                tv_userId = itemView.findViewById(R.id.tv_scroll_memberId);
                tv_userAge_height = itemView.findViewById(R.id.tv_scroll_profile_otherDetails);
                tv_motherTounge = itemView.findViewById(R.id.tv_scroll_profile_languageDetails);
                tv_profession = itemView.findViewById(R.id.tv_scroll_profile_ProfessionDetails);
                tv_income = itemView.findViewById(R.id.tv_scroll_profile_saleryDetails);
                tv_married_status = itemView.findViewById(R.id.tv_scroll_profile_marriedStatus1);
                tv_city_state_country = itemView.findViewById(R.id.tv_scroll_profile_AddressDetails);
                tv_shortlist = itemView.findViewById(R.id.tv_scroll_shortlist);
                tv_shortlisted = itemView.findViewById(R.id.tv_scroll_shortlisted);
                tv_invite = itemView.findViewById(R.id.tv_scroll_invite);
                tv_invited = itemView.findViewById(R.id.tv_scroll_invited);
                linearLayout = itemView.findViewById(R.id.linearLayout);
                TextView tv_online = itemView.findViewById(R.id.tv_profileView_online);
                TextView tv_offline = itemView.findViewById(R.id.tv_profileView_Offline);

                tv_userId.setText(searchModel.getUniqueId());
                linearLayout.setVisibility(View.GONE);

                if(searchModel.getIspremium().equalsIgnoreCase("0")){
                    img_premium.setVisibility(View.GONE);
                }else {
                    img_premium.setVisibility(View.VISIBLE);
                }

                try {
                    searchModel.setAge(MatrimonyUtils.getAge(searchModel.getDOB()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String age1 = "" + searchModel.getAge();
                String height = "" + searchModel.getMemberHeight();
                String age_height;

                if (age1.equals("") || age1.equalsIgnoreCase("")) {
                    age_height = "" + height;
                } else {
                    age_height = age1 + " , " + height;
                }

                if (age_height.equals("") || age_height.equalsIgnoreCase("")) {
                    tv_userAge_height.setVisibility(View.GONE);

                } else {
                    tv_userAge_height.setVisibility(View.VISIBLE);
                    tv_userAge_height.setText(age_height);
                }

                if (searchModel.getMotherTongue().equals("") || searchModel.getMotherTongue().equalsIgnoreCase("")) {
                    tv_motherTounge.setVisibility(View.GONE);
                } else {
                    tv_motherTounge.setVisibility(View.VISIBLE);
                    tv_motherTounge.setText(searchModel.getMotherTongue());
                }

                String city = "" + searchModel.getMemberCity();
                String country = "" + searchModel.getMemberCountry();
                String city_country;

                if (city.equals("") || city.equalsIgnoreCase("")) {

                    city_country = "" + country;
                } else {

                    city_country = city + "," + country;
                }


                if (city_country.equals("") || city_country.equalsIgnoreCase("")) {

                    tv_city_state_country.setVisibility(View.GONE);
                } else {

                    tv_city_state_country.setVisibility(View.VISIBLE);
                    tv_city_state_country.setText(city_country);
                }


                if (searchModel.getMarriedStatus().equals("") || searchModel.getMarriedStatus().equalsIgnoreCase("")) {

                    tv_married_status.setVisibility(View.GONE);

                } else {

                    tv_married_status.setVisibility(View.VISIBLE);
                    tv_married_status.setText(searchModel.getMarriedStatus());
                }

              /*  if (searchModel.getEducationIn().equals("") || searchModel.getEducationIn().equalsIgnoreCase("")) {

                    tv_profession.setVisibility(View.GONE);

                } else {

                    tv_profession.setVisibility(View.VISIBLE);
                    tv_profession.setText(searchModel.getEducationIn());
                }*/

                if (searchModel.getMemberInCome().equals("") || searchModel.getMemberInCome().equalsIgnoreCase("")) {

                    tv_income.setVisibility(View.GONE);

                } else {

                    tv_income.setVisibility(View.VISIBLE);
                    tv_income.setText(searchModel.getMemberInCome());
                }

                if (searchModel.getMainProfilePhoto().isEmpty()) {

                    Picasso.get()
                            .load(R.drawable.img_preview)
                            .error(R.drawable.img_preview)
                            .into(profileUrl);
                } else {

                    Picasso.get()
                            .load(searchModel.getMainProfilePhoto())
                            .placeholder(R.drawable.img_preview)
                            .error(R.drawable.img_preview)
                            .into(profileUrl);
                }


                if (searchModel.getManglik().isEmpty()) {
                    searchModel.setManglik("Yes");
                }

//                profileUrl.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        if (!MainPresenter.getInstance().isPremiumMember()) {
//                            onListItemClicked(searchModel);
//                            return;
//                        }
//
//                        Intent intent = new Intent(mContext, SearchDisplayActivity.class);
//                        String uniqueId = searchModel.getUniqueId();
//
//                        mPreferences.edit().putString("uniqueId", uniqueId).apply();
//
//                        mContext.startActivity(intent);
//                    }
//                });

//                try {
//                    String onlineTime = searchModel.getOnlineTime();
//                    String offlineTime = searchModel.getOffline_Time();
//
//                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
//                    Date onlineDate = null, offlineDate = null;
//
//                    if (!TextUtils.isEmpty(onlineTime) && onlineTime.length() > 5) {
//                        onlineDate = formatter.parse(onlineTime);
//                    }
//                    if (!TextUtils.isEmpty(offlineTime) && offlineTime.length() > 5) {
//                        offlineDate = formatter.parse(offlineTime);
//                    }
//
//                    assert onlineDate != null;
//                    assert offlineDate != null;
//
//                    if (onlineDate != null && offlineDate != null) {
//                        if (onlineDate.compareTo(offlineDate) < 0) {
//                            searchModel.setOnline(false);
//                        } else {
//                            searchModel.setOnline(true);
//                        }
//                    } else {
//                        searchModel.setOnline(false);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                if (searchModel.getOnlinestatus().equals("Offline") || searchModel.getOnlinestatus().equals("offline")) {
                    searchModel.setOnline(false);
                } else {
                    searchModel.setOnline(true);
                }


                if (searchModel.getOnlinestatus().equalsIgnoreCase("online")) {
                    tv_online.setVisibility(View.VISIBLE);
                    tv_online.setText("Online");
                    tv_offline.setVisibility(View.GONE);
                } else {
                    tv_offline.setVisibility(View.VISIBLE);
                    tv_offline.setText("Offline ");
                    tv_online.setVisibility(View.GONE);
                }
            }

            @Override
            public void onItemClicked(View view, Object object, int position) {
                UserProfileModel searchModel = (UserProfileModel) object;

               /* if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                    onListItemClicked(searchModel);
                    return;
                }*/




                Intent intent = new Intent(mContext, SearchDisplayActivity.class);
                String uniqueId = searchModel.getUniqueId();

                mPreferences.edit().putString("uniqueId", uniqueId).apply();
                Log.e("pushPos", position + "");
                intent.putExtra(Constants.ShareableIntents.IS_ONLINE, isOnline);
                intent.putExtra(Constants.ShareableIntents.GENDER, gender);
                intent.putExtra(Constants.ShareableIntents.MIN_AGE, minAge);
                intent.putExtra(Constants.ShareableIntents.MAX_AGE, maxAge);
                intent.putExtra(Constants.ShareableIntents.MIN_HEIGHT, minHeight);
                intent.putExtra(Constants.ShareableIntents.MAX_HEIGHT, maxHeight);
                intent.putExtra(Constants.ShareableIntents.MOTHER_TONGUE, motherTongue);
                intent.putExtra(Constants.ShareableIntents.MARRIED_STATUS, marriedStatus);
                intent.putExtra(Constants.ShareableIntents.COUNTRY_ID, countryId);
                intent.putExtra(Constants.ShareableIntents.STATE_ID, stateId);
                intent.putExtra(Constants.ShareableIntents.CITY_ID, cityId);

                intent.putExtra(Constants.ShareableIntents.INCOME_ID, strIncome);
                intent.putExtra(Constants.ShareableIntents.OCCUPATION_ID, strProfession);
                intent.putExtra(Constants.ShareableIntents.PHYSICALLY_DISABLE, strPhysicalDisable);

                Log.e("111111111111111111", "strIncome: "+strIncome+" strProfession---"+strProfession+" strPhysicalDisable---"+strPhysicalDisable );

                intent.putExtra(Constants.ShareableIntents.MANGLIK, manglik);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(searchListAdapter);
    }

    @Override
    public void showSearchedResults(List<UserProfileModel> userProfileModels) {

    }
}
