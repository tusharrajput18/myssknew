package in.co.vsys.myssksamaj.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easebuzz.payment.kit.PWECouponsActivity;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import datamodels.StaticDataModel;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CustomPagerAdapter;
import in.co.vsys.myssksamaj.contracts.DownloadProfileContract;
import in.co.vsys.myssksamaj.contracts.GetPdfUrlContract;
import in.co.vsys.myssksamaj.contracts.MemberDetailsContract;
import in.co.vsys.myssksamaj.contracts.SearchContract;
import in.co.vsys.myssksamaj.contracts.ShortListContract;
import in.co.vsys.myssksamaj.contracts.TransactionDataContract;
import in.co.vsys.myssksamaj.contracts.UserContract;
import in.co.vsys.myssksamaj.fragments.ImageDisplayFragment;
import in.co.vsys.myssksamaj.fragments.ShowContactFragment;
import in.co.vsys.myssksamaj.helpers.PaymentHelper;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.interfaces.ViewClickListener;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.data_models.MemberDetailsModel;
import in.co.vsys.myssksamaj.model.data_models.UserDetailsModel;
import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.presenters.DownloadProfilePresenter;
import in.co.vsys.myssksamaj.presenters.GetPdfUrlPresenter;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.presenters.MemberDetailsPresenter;
import in.co.vsys.myssksamaj.presenters.SearchPresenter;
import in.co.vsys.myssksamaj.presenters.ShortListPresenter;
import in.co.vsys.myssksamaj.presenters.TransactionPresenter;
import in.co.vsys.myssksamaj.presenters.UserPresenter;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.EasebuzzConstants;
import in.co.vsys.myssksamaj.utils.Utilities;

public class SearchDisplayActivity extends AppCompatActivity implements TransactionDataContract.TransactionView,
        MemberDetailsContract.MemberDetailsView, ShortListContract.ShortListView, SearchContract.SearchView,
        UserContract.UserDetailsView, GetPdfUrlContract.GetPdfUrlView, DownloadProfileContract.DownloadProfileView {

    private Context mContext;
    private static final String TAG = SearchDisplayActivity.class.getSimpleName();
    private static final String UNIQUE_ID = "UniqueId";
    private String uniqueId;
    private int memberId;
    private String accountCreatedBy;
    private String candidate = "In my own words";
    private String parents = "a few words about my daughter";
    //    private ShortlistedModel shortlistedModel;
//    private CollapsingToolbarLayout mCoordinatorLayout;
    private String username;
    private String fileName;
    //  private CircleImageView ;
    private CircularImageView img_profileViewverPic;
    private ImageView profileUrl, imgInvite, imgShortlist, imgShortlisted, imgInvited, imgChat, kundli_photo;
    private TextView tv_memberName, tv_memberId, tv_otherDetails, tv_profile_created_by, tv_profileView_introduction, tv_profileView_name, tv_profileView_gender,
            tv_profileView_age, tv_profileView_height, tv_profileView_married_status, tv_profileView_mother_tongue, tv_profileView_physical_status, tv_profileView_body_type,
            tv_profileView_profile_created_by, tv_profileView_eating_habit, tv_profileView_drinking_habit, tv_profileView_smoking_habit, tv_profileView_eduction,
            tv_profileView_eduction_in, tv_profileView_working_with, tv_profileView_working_as, tv_profileView_annual_income, tv_profileView_country, tv_profileView_state,
            tv_profileView_city, tv_profileView_family_type, tv_profileView_father_status, tv_profileView_mother_status, tv_profileView_no_of_brother,
            tv_profileView_no_of_brother_married, tv_profileView_no_of_sister, tv_profileView_no_of_sister_married, tv_profileView_birth_date, tv_profileView_birth_time,
            tv_profileView_birth_place, tv_profileView_gotra, tv_profileView_rashi, tv_profileView_nakshtra, tv_profileView_charan, tv_profileView_naadi, tv_profileView_gan;
    private TextView tv_shortlist, tv_shortlisted, tv_invite, tv_invited, tv_profileView_kundli, tvCaste, tvSubCaste;
    //    private View view_kundli_view;
    private SharedPreferences mPreferences;
    private AppBarLayout appBarLayout;

    private AlertDialog alertDialog;
    private Dialog dialog;
    private String mTransactionId = "", mPackageId = "1";
    private TransactionDataContract.TransactionOps transactionDataPresenter;
    private MemberDetailsContract.MemberDetailsOps memberDetailsPresenter;
    private List<MemberDetailsModel> mMemberDetailsList = new ArrayList<>();
    private List<UserProfileModel> mUserProfileModels = new ArrayList<>();
    private SharedPreferences mPreference;
    private ViewPager viewPager;
    private CustomPagerAdapter customPagerAdapter;
    private int viewPosition, pagePosition, isOnline;
    private ShortListContract.ShortListOps shortListPresenter;
    private UserContract.UserDetailsOps userPresenter;
    private Toolbar toolbar;
    private UserProfileModel currentProfileSelected;
    private MemberDetailsModel currentMemberDetailsModel;

    private DownloadProfileContract.DownloadProfileOps downloadProfilePresenter;
    private GetPdfUrlContract.GetPdfUrlOps getPdfUrlPresenter;

    private SearchContract.SearchOps searchPresenter;
    private String minAge, maxAge, minHeight, maxHeight, gender, motherTongue, marriedStatus, countryId,
            stateId, cityId, strIncome, strOccupation, strPhisicalDisablity,manglik;

    private int mUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_display);
        mContext = this;

//        mCoordinatorLayout = (CollapsingToolbarLayout) findViewById(R.id.search_collapsing_toolbar);

        mPreference = PreferenceManager.getDefaultSharedPreferences(this);

        appBarLayout = (AppBarLayout) findViewById(R.id.search_appBarLayout);
        toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        viewPager = findViewById(R.id.viewpager);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mUserType = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.USERTYPE);
        Log.d(TAG, "onCreate: "+mUserType);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        memberDetailsPresenter = new MemberDetailsPresenter(this);
        shortListPresenter = new ShortListPresenter(this);

        transactionDataPresenter = new TransactionPresenter(this);
        searchPresenter = new SearchPresenter(this);
        userPresenter = new UserPresenter(this);
        uniqueId = mPreferences.getString("uniqueId", "");
        //memberId = mPreference.getInt("memberId", 0);

        if (Utilities.isIntentPresent(getIntent())) {
            memberId = mPreferences.getInt("memberId", 0);
            accountCreatedBy = mPreferences.getString("accountCreatedBY", "");
            if (getIntent().getExtras().containsKey("searchName")) {
                String name = getIntent().getStringExtra("searchName");
                searchPresenter.searchMemberByName(name,String.valueOf(memberId));
            }
            if (getIntent().getExtras().containsKey("position")) {
                //memberIntent=getIntent().getStringExtra("memberId");

                memberId = mPreferences.getInt("memberId", 0);


                getData();
                Log.e(TAG, "isOnline: " + isOnline + "\n S_Gendor: " + gender + "\n S_minAge: " + minAge + "\n S_maxAge: " + maxAge + "\n S_minHeight: " + minHeight + "\n S_motherTongueSelectedId: " + motherTongue + "\n S_marriedStatus: " + marriedStatus + "\n S_countrySelectedId: " + countryId + "\n S_stateSelectedId: " + stateId);


                pagePosition = getIntent().getIntExtra("position", 0);
                searchPresenter.searchMember(gender, minAge, maxAge, minHeight, maxHeight, motherTongue, marriedStatus, countryId, stateId, cityId, String.valueOf(isOnline), strIncome, strOccupation, strPhisicalDisablity,manglik,String.valueOf(memberId));


            }
            if (getIntent().getExtras().containsKey("searchProfileId")) {
                Log.e("id", getIntent().getStringExtra("searchProfileId"));
                memberDetailsPresenter.getMemberDetails(getIntent().getStringExtra("searchProfileId"));
            }

        }/*else {

            memberDetailsPresenter.getMemberDetails(uniqueId);
        }*/



        /*mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        memberId = mPreferences.getInt("memberId", 0);
        accountCreatedBy = mPreferences.getString("accountCreatedBY", "");
        uniqueId = mPreferences.getString("uniqueId", "");

        transactionDataPresenter = new TransactionPresenter(this);
        searchPresenter = new SearchPresenter(this);

        if (Utilities.isIntentPresent(getIntent())) {
            String name = getIntent().getStringExtra("searchName");
            searchPresenter.searchMemberByName(name);
            return;
        }

        memberDetailsPresenter = new MemberDetailsPresenter(this);

        memberDetailsPresenter.getMemberDetails(uniqueId);
shortListPresenter = new ShortListPresenter(this);
        */

        getPdfUrlPresenter = new GetPdfUrlPresenter(this);
        downloadProfilePresenter = new DownloadProfilePresenter(this);

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
            intent.putExtra(Constants.ShareableIntents.INCOME_ID, strIncome);
            intent.putExtra(Constants.ShareableIntents.OCCUPATION_ID, strOccupation);
            intent.putExtra(Constants.ShareableIntents.PHYSICALLY_DISABLE, strPhisicalDisablity);


            motherTongue = intent.getStringExtra(Constants.ShareableIntents.MOTHER_TONGUE);
            marriedStatus = intent.getStringExtra(Constants.ShareableIntents.MARRIED_STATUS);






            /*countryId = intent.getStringExtra(Constants.ShareableIntents.COUNTRY_ID);
            stateId = intent.getStringExtra(Constants.ShareableIntents.STATE_ID);
            cityId = intent.getStringExtra(Constants.ShareableIntents.CITY_ID);


*/
            strIncome = intent.getExtras().getString(Constants.ShareableIntents.INCOME_ID, "");
            strOccupation = intent.getExtras().getString(Constants.ShareableIntents.OCCUPATION_ID, "");
            strPhisicalDisablity = intent.getExtras().getString(Constants.ShareableIntents.PHYSICALLY_DISABLE, "");
            manglik = intent.getExtras().getString(Constants.ShareableIntents.MANGLIK, "");

            Log.e(TAG, "strIncome: " + strIncome + "\n strOccupation:" + strOccupation + "\n strPhisicalDisablity:" + strPhisicalDisablity + "\n manglik:" + manglik);



            countryId = intent.getExtras().getString(Constants.ShareableIntents.COUNTRY_ID, "");
            stateId = intent.getExtras().getString(Constants.ShareableIntents.STATE_ID, "");
            cityId = intent.getExtras().getString(Constants.ShareableIntents.CITY_ID, "");
            isOnline = getIntent().getIntExtra(Constants.ShareableIntents.IS_ONLINE, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
               /* startActivity(new Intent(mContext, SearchListActivity.class));
                finish();*/
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void showLoading() {
        if (dialog != null) {
            if (dialog.isShowing())
                dialog.dismiss();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog == null) {
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
                    //View view = getLayoutInflater().inflate(R.layout.progress);
                    builder.setView(R.layout.progress);
                    dialog = builder.create();
                }
                try {
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void showError(final String message) {



        ((SearchDisplayActivity)mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        });

        dialog.dismiss();



       // Toast.makeText(SearchDisplayActivity.this, ""+message, Toast.LENGTH_SHORT).show();

      //  Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * NOTE: Payment SDK implementation and methods from here onwards:
     */

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
    public void showMemberDetails(List<MemberDetailsModel> memberDetails) {
        mMemberDetailsList.clear();
        mMemberDetailsList.addAll(memberDetails);

        customPagerAdapter = new CustomPagerAdapter(mContext, memberDetails,
                R.layout.row_profile, new CustomPagerAdapter.ItemClickedListener() {
            @Override
            public void onItemClicked(View view, Object object, int position) {

            }

            @Override
            public void onViewBound(View view, Object object, final int position) {
                final MemberDetailsModel mMemberDetails = (MemberDetailsModel) object;
                LinearLayout basicDetailsContainer = view.findViewById(R.id.basic_info_container);

                TextView tv_online = view.findViewById(R.id.tv_profileView_online);
                TextView tv_offline = view.findViewById(R.id.tv_profileView_Offline);



                profileUrl = (ImageView) view.findViewById(R.id.img_profileView_image);


                tv_memberId = (TextView) view.findViewById(R.id.tv_profileView_memberId);
//        view_kundli_view = (View) findViewById(R.id.view_kundli_view);
                tv_otherDetails = (TextView) view.findViewById(R.id.tv_profile_otherDetails);
                imgInvite = (ImageView) view.findViewById(R.id.img_profileView_invite);
                kundli_photo = (ImageView) view.findViewById(R.id.img_profileView_kundli);

                imgInvited = (ImageView) view.findViewById(R.id.img_profileView_invited);
                imgChat = (ImageView) view.findViewById(R.id.img_profileView_chat);


                if(mMemberDetails.getOnlinestatus().equalsIgnoreCase("online")){
                    tv_online.setVisibility(View.VISIBLE);
                    tv_online.setText("Online");
                    tv_offline.setVisibility(View.GONE);

                }else {
                    tv_offline.setVisibility(View.VISIBLE);
                    tv_offline.setText("Offline");
                    tv_online.setVisibility(View.GONE);
                }

                if (Utilities.isEmpty(mMemberDetails.getKundaliPhoto())) {
                    kundli_photo.setVisibility(View.GONE);
                } else {
                    kundli_photo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ImageDisplayFragment imageDisplayFragment = new ImageDisplayFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("kundali", mMemberDetails.getKundaliPhoto());
                            bundle.putString("memberName", mMemberDetails.getFirstName() + "_" + mMemberDetails.getLastName());
                            imageDisplayFragment.setArguments(bundle);
                            imageDisplayFragment.show(getSupportFragmentManager(), "Kundali");
                        }
                    });
                }

                LinearLayout chatcontainer = (LinearLayout) view.findViewById(R.id.chatContainer);
                chatcontainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(mUserType==1){
                            startActivity(new Intent(mContext, ChatActivity.class));

                            String name = mMemberDetails.getFirstName() + " " + mMemberDetails.getLastName();
                            String imageUrl = mMemberDetails.getMainProfilePhoto();

                            mPreference.edit().putInt("messageReceiverId", Integer.parseInt(mMemberDetails.getMemberId())).apply();
                            mPreference.edit().putString("messageReceiverName", name).apply();
                            mPreference.edit().putString("messageReceiverImage", imageUrl).apply();
                            mPreference.edit().putString("receiverTokenId", mMemberDetails.getDeviceId()).apply();
                        }else {
                            displayPreimiumalert();
                        }


                    }
                });
                /*imgChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });*/

                ImageView download = view.findViewById(R.id.download);
                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fileName = mMemberDetails.getFirstName() + "_" + mMemberDetails.getLastName();
                        getPdfUrlPresenter.getProfilePdf(mMemberDetails.getMemberId());
                        //Toast.makeText(mContext, "530", Toast.LENGTH_SHORT).show();

                      /*  String memberId = currentProfileSelected.getMemberId();
                        getPdfUrlPresenter.getProfilePdf(memberId);*/
                    }
                });


                imgShortlist = (ImageView) view.findViewById(R.id.img_profileView_shortlist);
                imgShortlisted = (ImageView) view.findViewById(R.id.img_profileView_shortlisted);
                tv_memberName = (TextView) view.findViewById(R.id.tv_profileView_name);
                tv_profile_created_by = (TextView) view.findViewById(R.id.tv_profile_created_by);
                tv_profileView_kundli = (TextView) view.findViewById(R.id.tv_profileView_kundli);
                tv_profileView_introduction = (TextView) view.findViewById(R.id.tv_profileView_introduction);
                tv_profileView_name = (TextView) view.findViewById(R.id.tv_profileView_name);
                tv_profileView_gender = (TextView) view.findViewById(R.id.tv_profileView_gender);
                tv_profileView_age = (TextView) view.findViewById(R.id.tv_profileView_age);
                tv_profileView_height = (TextView) view.findViewById(R.id.tv_profileView_height);
                tv_profileView_married_status = (TextView) view.findViewById(R.id.tv_profileView_married_status);
                tv_profileView_mother_tongue = (TextView) view.findViewById(R.id.tv_profileView_mother_tongue);
                tv_profileView_physical_status = (TextView) view.findViewById(R.id.tv_profileView_physical_status);
                tv_profileView_body_type = (TextView) view.findViewById(R.id.tv_profileView_body_type);
                // TextView tv_profileView_complexion = findViewById(R.id.tv_profileView_complexion);
                tv_profileView_profile_created_by = (TextView) view.findViewById(R.id.tv_profileView_profile_created_by);
                tv_profileView_eating_habit = (TextView) view.findViewById(R.id.tv_profileView_eating_habit);
                tv_profileView_drinking_habit = (TextView) view.findViewById(R.id.tv_profileView_drinking_habit);
                tv_profileView_smoking_habit = (TextView) view.findViewById(R.id.tv_profileView_smoking_habit);
                tv_profileView_eduction = (TextView) view.findViewById(R.id.tv_profileView_eduction);
                tv_profileView_eduction_in = (TextView) view.findViewById(R.id.tv_profileView_eduction_in);
                tv_profileView_working_with = (TextView) view.findViewById(R.id.tv_profileView_working_with);
                tv_profileView_working_as = (TextView) view.findViewById(R.id.tv_profileView_working_as);
                tv_profileView_annual_income = (TextView) view.findViewById(R.id.tv_profileView_annual_income);
                tv_profileView_country = (TextView) view.findViewById(R.id.tv_profileView_country);
                tv_profileView_state = (TextView) view.findViewById(R.id.tv_profileView_state);
                tv_profileView_city = (TextView) view.findViewById(R.id.tv_profileView_city);
                tv_profileView_family_type = (TextView) view.findViewById(R.id.tv_profileView_family_type);
                tv_profileView_father_status = (TextView) view.findViewById(R.id.tv_profileView_father_status);
                tv_profileView_mother_status = (TextView) view.findViewById(R.id.tv_profileView_mother_status);
                tv_profileView_no_of_brother = (TextView) view.findViewById(R.id.tv_profileView_no_of_brother);
                tv_profileView_no_of_brother_married = (TextView) view.findViewById(R.id.tv_profileView_no_of_brother_married);
                tv_profileView_no_of_sister = (TextView) view.findViewById(R.id.tv_profileView_no_of_sister);
                tv_profileView_no_of_sister_married = (TextView) view.findViewById(R.id.tv_profileView_no_of_sister_married);
                tv_profileView_birth_date = (TextView) view.findViewById(R.id.tv_profileView_birth_date);
                tv_profileView_birth_time = (TextView) view.findViewById(R.id.tv_profileView_birth_time);
                tv_profileView_birth_place = (TextView) view.findViewById(R.id.tv_profileView_birth_place);
                tv_profileView_gotra = (TextView) view.findViewById(R.id.tv_profileView_gotra);
                tv_profileView_rashi = (TextView) view.findViewById(R.id.tv_profileView_rashi);
                tv_profileView_nakshtra = (TextView) view.findViewById(R.id.tv_profileView_nakshtra);
                tv_profileView_charan = (TextView) view.findViewById(R.id.tv_profileView_charan);
                tv_profileView_naadi = (TextView) view.findViewById(R.id.tv_profileView_naadi);
                tv_profileView_gan = (TextView) view.findViewById(R.id.tv_profileView_gan);
                tvCaste = (TextView) view.findViewById(R.id.tv_profileView_caste);
                tvSubCaste = (TextView) view.findViewById(R.id.tv_profileView_subCaste);
                tv_shortlist = view.findViewById(R.id.tv_shortlist);
                tv_shortlisted = view.findViewById(R.id.tv_shortlisted);
                tv_invite = view.findViewById(R.id.tv_profileView_invite);
                tv_invited = view.findViewById(R.id.tv_profileView_invited);


                profileUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MatrimonyUtils.loadImages(mContext, Integer.parseInt(mMemberDetails.getMemberId()),
                                null);
                    }
                });

                imgInvite.setEnabled(true);
                if (accountCreatedBy.equals("P")) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        imgInvite.setImageAlpha(64);
                    }
                } else if (accountCreatedBy.equals("C")) {

                    if (TextUtils.isEmpty(mMemberDetails.getInvited()) || mMemberDetails.getInvited().contains("0")) {

                        imgInvite.setVisibility(View.VISIBLE);
                        imgInvited.setVisibility(View.GONE);
                        tv_invite.setVisibility(View.VISIBLE);
                        tv_invited.setVisibility(View.GONE);

                    } else {

                        imgInvited.setVisibility(View.VISIBLE);
                        imgInvite.setVisibility(View.GONE);
                        tv_invite.setVisibility(View.GONE);
                        tv_invited.setVisibility(View.VISIBLE);

                    }
                }

//                    tvCaste.setText("" + mMemberDetails.getCaste());
//                    tvSubCaste.setText("" + mMemberDetails.getSubcaste());

                tv_profileView_name.setText(mMemberDetails.getFirstName() + " " + mMemberDetails.getLastName());
                tv_profileView_introduction.setText(mMemberDetails.getSelfIntroduction());
                tv_profileView_physical_status.setText(mMemberDetails.getPhysicalchallenge());
                tv_profileView_eating_habit.setText(mMemberDetails.getFoodtype());
                tv_profileView_smoking_habit.setText(mMemberDetails.getSmokehabit());
                tv_profileView_drinking_habit.setText(mMemberDetails.getDrinkhabit());
                tv_profileView_eduction.setText(mMemberDetails.getMemberEducationName());
                tv_profileView_eduction_in.setText(mMemberDetails.getEducationInName());
                tv_profileView_working_with.setText(mMemberDetails.getEducationWithName());
                tv_profileView_working_as.setText(mMemberDetails.getEducationWithName());


                if (TextUtils.isEmpty(mMemberDetails.getShorted()) || mMemberDetails.getShorted().contains("0")) {

                    imgShortlist.setVisibility(View.VISIBLE);
                    imgShortlisted.setVisibility(View.GONE);
                    tv_shortlist.setVisibility(View.VISIBLE);
                    tv_shortlisted.setVisibility(View.GONE);

                } else {

                    imgShortlisted.setVisibility(View.VISIBLE);
                    tv_shortlist.setVisibility(View.GONE);
                    tv_shortlist.setVisibility(View.GONE);
                    tv_shortlisted.setVisibility(View.VISIBLE);

                }

                imgShortlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int receiverId = Integer.parseInt(mMemberDetails.getMemberId());

                        shortListPresenter.performShortlist("" + memberId, "" + receiverId, "1");

                        imgShortlisted.setVisibility(imgShortlisted.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                        imgShortlist.setVisibility(imgShortlist.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

                        tv_shortlist.setVisibility(tv_shortlist.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                        tv_shortlisted.setVisibility(tv_shortlisted.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

                    }
                });

                imgShortlisted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int receiverId = Integer.parseInt(mMemberDetails.getMemberId());

                        shortListPresenter.performShortlist("" + memberId, "" + receiverId, "0");

                        imgShortlisted.setVisibility(imgShortlisted.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                        imgShortlist.setVisibility(imgShortlist.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

                        tv_shortlist.setVisibility(tv_shortlist.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                        tv_shortlisted.setVisibility(tv_shortlisted.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                    }
                });


                imgInvite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (accountCreatedBy.equals("P")) {

                            Toast.makeText(mContext, "You can not invite Candidate profile ", Toast.LENGTH_LONG).show();

                        } else {
                            int senderId = Integer.parseInt(mMemberDetails.getMemberId());
                            MatrimonyUtils.inviteTask(mContext, memberId, senderId, "1");

                            imgInvited.setVisibility(View.VISIBLE);
                            imgInvite.setVisibility(View.GONE);

                            tv_invite.setVisibility(View.GONE);
                            tv_invited.setVisibility(View.VISIBLE);
                        }
                    }
                });

                imgInvited.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
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
                            return;
                        }

                        int senderId = Integer.parseInt(mMemberDetails.getMemberId());
                        MatrimonyUtils.inviteTask(mContext, memberId, senderId, "0");

                        imgInvite.setVisibility(View.VISIBLE);
                        imgInvited.setVisibility(View.GONE);

                        tv_invite.setVisibility(View.VISIBLE);
                        tv_invited.setVisibility(View.GONE);
                    }
                });

                try {
                    mMemberDetails.setAge(MatrimonyUtils.getAge(mMemberDetails.getDOB()));
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                tv_memberId.setText(mMemberDetails.getUniqueId());
                String age_height_other = mMemberDetails.getAge() + ", " + mMemberDetails.getMemberHeight() + ", " + mMemberDetails.getMemberCity() +
                        ", " + mMemberDetails.getMemberState() + ", " + mMemberDetails.getMemberCountry();
                tv_otherDetails.setText(age_height_other);

                String profileCreatedBy = mMemberDetails.getAccountCreatedBy();

                if (profileCreatedBy.equals("C")) {
                    tv_profile_created_by.setText(candidate);
                    tv_profileView_profile_created_by.setText("Self");
                } else {
                    tv_profile_created_by.setText(parents);
                    tv_profileView_profile_created_by.setText("Parent");
                }

                username = mMemberDetails.getFirstName() + " " + mMemberDetails.getLastName();
                tv_profileView_name.setText(username);
//        mCoordinatorLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorBlack));
//        mCoordinatorLayout.setTitle(username);
                tv_profileView_gender.setText(mMemberDetails.getGender());
                tv_profileView_age.setText(mMemberDetails.getAge());
                tv_profileView_height.setText(mMemberDetails.getMemberHeight());
                tv_profileView_married_status.setText(mMemberDetails.getMarriedStatus());
                tv_profileView_mother_tongue.setText(mMemberDetails.getMotherTongue());
                tv_profileView_country.setText(mMemberDetails.getMemberCountry());
                tv_profileView_state.setText(mMemberDetails.getMemberState());
                tv_profileView_city.setText(mMemberDetails.getMemberCity());
                tv_profileView_birth_date.setText(mMemberDetails.getDOB());
                tv_profileView_annual_income.setText(mMemberDetails.getMemberInCome());
                tv_profileView_family_type.setText(mMemberDetails.getFamilyType());
                tv_profileView_father_status.setText(mMemberDetails.getFatherstatus());
                tv_profileView_mother_status.setText(mMemberDetails.getMotherstatus());
                tv_profileView_no_of_brother.setText(mMemberDetails.getNoOfBorther());
                tv_profileView_no_of_brother_married.setText(mMemberDetails.getNoOfBortherMarried());
                tv_profileView_no_of_sister.setText(mMemberDetails.getNoOfSister());
                tv_profileView_no_of_sister_married.setText(mMemberDetails.getNoOfBortherMarried());
                tv_profileView_birth_time.setText(mMemberDetails.getBirthTime());
                tv_profileView_birth_place.setText(mMemberDetails.getBirthPlace());
                tv_profileView_gotra.setText(mMemberDetails.getGotra());
                tv_profileView_rashi.setText(mMemberDetails.getRashi());
                tv_profileView_nakshtra.setText(mMemberDetails.getNakshtra());
                tv_profileView_charan.setText(mMemberDetails.getCharan());
                tv_profileView_naadi.setText(mMemberDetails.getNaddi());
                tv_profileView_gan.setText(mMemberDetails.getGan());
                tv_profileView_body_type.setText(mMemberDetails.getBodytype());

                if (mMemberDetails.getMainProfilePhoto().isEmpty()) {

                    Picasso.get()
                            .load(R.drawable.img_preview)
                            .placeholder(R.drawable.img_preview)
                            .error(R.drawable.img_preview)
                            .into(profileUrl);
                } else {

                    Picasso.get()
                            .load(mMemberDetails.getMainProfilePhoto())
                            .placeholder(R.drawable.img_preview)
                            .into(profileUrl);
                }

                if (mMemberDetails.getKundaliPhoto().length() > 10) {

                    tv_profileView_kundli.setVisibility(View.VISIBLE);
                    kundli_photo.setVisibility(View.VISIBLE);
//            view_kundli_view.setVisibility(View.VISIBLE);

                    Picasso.get()
                            .load(mMemberDetails.getKundaliPhoto())
                            .placeholder(R.drawable.img_preview)
                            .error(R.drawable.img_preview)
                            .into(kundli_photo);

                    kundli_photo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ImageDisplayFragment imageDisplayFragment = new ImageDisplayFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("kundali", mMemberDetails.getKundaliPhoto());
                            bundle.putString("memberName", mMemberDetails.getFirstName() + "_" + mMemberDetails.getLastName());
                            imageDisplayFragment.setArguments(bundle);
                            imageDisplayFragment.show(getSupportFragmentManager(), "Kundali");
                        }
                    });
                } else {

                    tv_profileView_kundli.setVisibility(View.GONE);
                    kundli_photo.setVisibility(View.GONE);
                }

                showBasicDetails(mMemberDetails, basicDetailsContainer);
            }
        });

        viewPager.setAdapter(customPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // viewPosition = position;
                String toolbarFName = mMemberDetailsList.get(position).getFirstName();
                toolbarFName = toolbarFName.substring(0, 1).toUpperCase() + toolbarFName.substring(1).toLowerCase();
                String toolbarLName = mMemberDetailsList.get(position).getLastName();
                toolbarLName = toolbarLName.substring(0, 1).toUpperCase() + toolbarLName.substring(1).toLowerCase();
                toolbar.setTitle(toolbarFName + " " + toolbarLName);
                currentMemberDetailsModel = mMemberDetailsList.get(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(pagePosition);
    }

    private void showBasicDetails(final MemberDetailsModel memberDetails, LinearLayout basicDetailsContainer) {
        basicDetailsContainer.addView(addData("Name", memberDetails.getFirstName() + " " + memberDetails.getLastName(),
                null));

        if (Utilities.isEmpty(memberDetails.getEmailId()) && Utilities.isEmpty(memberDetails.getMobile())) {
            return;
        }

        basicDetailsContainer.addView(addViewContact(new ViewClickListener() {
            @Override
            public void onViewClick(View view) {
                ShowContactFragment showContactFragment = new ShowContactFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.MEMBER_ID, memberDetails.getMemberId());
                bundle.putString(Constants.CONTACT_NO, memberDetails.getMobile());
                bundle.putString(Constants.EMAIL, memberDetails.getEmailId());
                showContactFragment.setArguments(bundle);

                showContactFragment.show(getSupportFragmentManager(), "ShowContactFragment");
            }
        }));
    }

    private View addData(String title, String text, ViewClickListener viewClickListener) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.info_layout_container, null);
        TextView titleTxt = view.findViewById(R.id.title);
        TextView value = view.findViewById(R.id.value);

        Utilities.setText(titleTxt, title);
        Utilities.setText(value, text, viewClickListener);
        return view;
    }

    private View addViewContact(final ViewClickListener viewClickListener) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_view_action_layout, null);
        Button view_contact = view.findViewById(R.id.view_contact);
        view_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewClickListener.onViewClick(v);
            }
        });
        return view;
    }

    @Override
    public void showShortlisted(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    private View boundView;

    @Override
    public void showSearchResults(final List<UserProfileModel> userProfileModels) {
        Log.e("pagePos", pagePosition + "");

        customPagerAdapter = new CustomPagerAdapter(mContext, userProfileModels,
                R.layout.row_profile, new CustomPagerAdapter.ItemClickedListener() {

            @Override
            public void onItemClicked(View view, Object object, int position) {

            }

            @Override
            public void onViewBound(View view, Object object, int position) {
                try {
                    //showSearchModelData(view, object, position,userProfileModels);
                    showData(view, object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        viewPager.setAdapter(customPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // viewPosition = position;
                String toolbarFName = userProfileModels.get(position).getFirstName();
                toolbarFName = toolbarFName.substring(0, 1).toUpperCase() + toolbarFName.substring(1).toLowerCase();
                String toolbarLName = userProfileModels.get(position).getLastName();
                toolbarLName = toolbarLName.substring(0, 1).toUpperCase() + toolbarLName.substring(1).toLowerCase();
                toolbar.setTitle(toolbarFName + " " + toolbarLName);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(pagePosition);

    }

    @Override
    public void showSearchedResults(final List<UserProfileModel> userProfileModels) {
        customPagerAdapter = new CustomPagerAdapter(mContext, userProfileModels,
                R.layout.row_profile, new CustomPagerAdapter.ItemClickedListener() {
            @Override
            public void onItemClicked(View view, Object object, int position) {

            }

            @Override
            public void onViewBound(View view, Object object, final int position) {
                try {
                    //   showSearchModelData(view, object, position);

                    final UserProfileModel userProfileModel = (UserProfileModel) object;
                    LinearLayout basicDetailsContainer = view.findViewById(R.id.basic_info_container);

                    ImageView download=view.findViewById(R.id.download);
                    ImageView img_det_premium=view.findViewById(R.id.img_det_premium);

                    ImageView profileUrl = view.findViewById(R.id.img_profileView_image);
                    TextView tv_memberId = view.findViewById(R.id.tv_profileView_memberId);
                    TextView tv_otherDetails = view.findViewById(R.id.tv_profile_otherDetails);
                    final ImageView imgInvite = view.findViewById(R.id.img_profileView_invite);
                    final ImageView imgInvited = view.findViewById(R.id.img_profileView_invited);
                    final ImageView imgAccept = view.findViewById(R.id.img_profileView_accept);
                    final ImageView imgShortlist = view.findViewById(R.id.img_profileView_shortlist);
                    final ImageView imgShortlisted = view.findViewById(R.id.img_profileView_shortlisted);
                    final TextView tv_shortlist = view.findViewById(R.id.tv_shortlist);
                    final TextView tv_shortlisted = view.findViewById(R.id.tv_shortlisted);
                    final TextView tv_invite = view.findViewById(R.id.tv_profileView_invite);
                    final TextView tv_invited = view.findViewById(R.id.tv_profileView_invited);
                    final TextView tv_accept = view.findViewById(R.id.tv_profileView_accept);
                    ImageView imgChat = view.findViewById(R.id.img_profileView_chat);
                    TextView tv_profile_created_by = view.findViewById(R.id.tv_profile_created_by);
                    TextView tv_profile_managed_by = view.findViewById(R.id.tv_profile_managedBy);
                    TextView tv_profileView_introduction = view.findViewById(R.id.tv_profileView_introduction);
                    TextView tv_profileView_name = view.findViewById(R.id.tv_profileView_name);
                    TextView tv_profileView_gender = view.findViewById(R.id.tv_profileView_gender);
                    TextView tv_profileView_age = view.findViewById(R.id.tv_profileView_age);
                    TextView tv_profileView_height = view.findViewById(R.id.tv_profileView_height);
                    TextView tv_profileView_married_status = view.findViewById(R.id.tv_profileView_married_status);
                    TextView tv_profileView_mother_tongue = view.findViewById(R.id.tv_profileView_mother_tongue);
                    TextView tv_profileView_physical_status = view.findViewById(R.id.tv_profileView_physical_status);
                    TextView tv_profileView_body_type = view.findViewById(R.id.tv_profileView_body_type);
                    TextView tv_profileView_complexion = view.findViewById(R.id.tv_profileView_complexion);
                    TextView tv_profileView_profile_created_by = view.findViewById(R.id.tv_profileView_profile_created_by);
                    TextView tv_profileView_eating_habit = view.findViewById(R.id.tv_profileView_eating_habit);
                    TextView tv_profileView_drinking_habit = view.findViewById(R.id.tv_profileView_drinking_habit);
                    TextView tv_profileView_smoking_habit = view.findViewById(R.id.tv_profileView_smoking_habit);
                    TextView tv_profileView_eduction = view.findViewById(R.id.tv_profileView_eduction);
                    TextView tv_profileView_eduction_in = view.findViewById(R.id.tv_profileView_eduction_in);
                    TextView tv_profileView_working_with = view.findViewById(R.id.tv_profileView_working_with);
                    TextView tv_profileView_working_as = view.findViewById(R.id.tv_profileView_working_as);
                    TextView tv_profileView_annual_income = view.findViewById(R.id.tv_profileView_annual_income);
                    TextView tv_profileView_country = view.findViewById(R.id.tv_profileView_country);
                    TextView tv_profileView_state = view.findViewById(R.id.tv_profileView_state);
                    TextView tv_profileView_city = view.findViewById(R.id.tv_profileView_city);
                    TextView tv_profileView_family_type = view.findViewById(R.id.tv_profileView_family_type);
                    TextView tv_profileView_father_status = view.findViewById(R.id.tv_profileView_father_status);
                    TextView tv_profileView_mother_status = view.findViewById(R.id.tv_profileView_mother_status);
                    TextView tv_profileView_no_of_brother = view.findViewById(R.id.tv_profileView_no_of_brother);
                    TextView tv_profileView_no_of_brother_married = view.findViewById(R.id.tv_profileView_no_of_brother_married);
                    TextView tv_profileView_no_of_sister = view.findViewById(R.id.tv_profileView_no_of_sister);
                    TextView tv_profileView_no_of_sister_married = view.findViewById(R.id.tv_profileView_no_of_sister_married);
                    TextView tv_profileView_birth_date = view.findViewById(R.id.tv_profileView_birth_date);
                    TextView tv_profileView_birth_time = view.findViewById(R.id.tv_profileView_birth_time);
                    TextView tv_profileView_birth_place = view.findViewById(R.id.tv_profileView_birth_place);
                    TextView tv_profileView_gotra = view.findViewById(R.id.tv_profileView_gotra);
                    TextView tv_profileView_rashi = view.findViewById(R.id.tv_profileView_rashi);
                    TextView tv_profileView_nakshtra = view.findViewById(R.id.tv_profileView_nakshtra);
                    TextView tv_profileView_charan = view.findViewById(R.id.tv_profileView_charan);
                    TextView tv_profileView_naadi = view.findViewById(R.id.tv_profileView_naadi);
                    TextView tv_profileView_gan = view.findViewById(R.id.tv_profileView_gan);
                    TextView tv_profileView_manglik = view.findViewById(R.id.tv_profileView_manglik);
                    TextView tv_online = view.findViewById(R.id.tv_profileView_online);
                    TextView tv_offline = view.findViewById(R.id.tv_profileView_Offline);
                    LinearLayout layout_profession = view.findViewById(R.id.layout_profileViewProfession);
                    LinearLayout layout_location = view.findViewById(R.id.layout_tv_profileViewLocation);
                    LinearLayout layout_familyDetails = view.findViewById(R.id.layout_profileViewFamilyDetails);
                    LinearLayout layout_Astro = view.findViewById(R.id.layout_profileViewAstroDetails);
                    View view_profession = view.findViewById(R.id.view_profileViewProfession);
                    View view_location = view.findViewById(R.id.view_profileViewLocation);
                    View view_family = view.findViewById(R.id.view_profileViewFamilyDetails);
                    View view_astro = view.findViewById(R.id.view_profileViewAstroDetails);
                    TextView tv_professionHeading = view.findViewById(R.id.tv_profileViewProfession);
                    TextView tv_locationHeading = view.findViewById(R.id.tv_profileViewLocation);
                    TextView tv_familyHeading = view.findViewById(R.id.tv_profileViewFamilyDetails);
                    TextView tv_astroHeading = view.findViewById(R.id.tv_profileViewAstroDetails);
                    TextView tv_age_heading = view.findViewById(R.id.tv_age);
                    TextView tv_height_heading = view.findViewById(R.id.tv_height);
                    TextView tv_marriedStatus_heading = view.findViewById(R.id.tv_married_status);
                    TextView tv_motherTounge_heading = view.findViewById(R.id.tv_mother_tongue);
                    TextView tv_physicalStatus_heading = view.findViewById(R.id.tv_physical);
                    TextView tv_bodyType_heading = view.findViewById(R.id.tv_body_type);
                    TextView tv_complexion_heading = view.findViewById(R.id.tv_body_complexion);
                    TextView tv_eating_heading = view.findViewById(R.id.tv_Eating_Habits);
                    TextView tv_drinking_heading = view.findViewById(R.id.tv_drinking_habit);
                    TextView tv_smoking_heading = view.findViewById(R.id.tv_smoking_habits);
                    TextView tv_age_heading_dot = view.findViewById(R.id.tv_age_dot);
                    TextView tv_height_heading_dot = view.findViewById(R.id.tv_height_dot);
                    TextView tv_marriedStatus_heading_dot = view.findViewById(R.id.tv_married_statusDot);
                    TextView tv_motherTounge_heading_dot = view.findViewById(R.id.tv_mother_tongueDot);
                    TextView tv_physicalStatus_heading_dot = view.findViewById(R.id.tv_physicalDot);
                    TextView tv_bodyType_heading_dot = view.findViewById(R.id.tv_body_typeDot);
                    TextView tv_complexion_heading_dot = view.findViewById(R.id.tv_body_complexionDot);
                    TextView tv_eating_heading_dot = view.findViewById(R.id.tv_Eating_HabitsDot);
                    TextView tv_drinking_heading_dot = view.findViewById(R.id.tv_drinking_habitDot);
                    TextView tv_smoking_heading_dot = view.findViewById(R.id.tv_smoking_habitsDot);
//                    TextView tvCaste = view.findViewById(R.id.tv_profileView_caste);
//                    TextView tvSubCaste = view.findViewById(R.id.tv_profileView_subCaste);
                    TextView tvContact = view.findViewById(R.id.tv_contact);
                    TextView tvEmail = view.findViewById(R.id.tv_email);

                    memberId = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.MEMBER_ID);
                    final String accountCreatedBy = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.ACCOUNT_CREATED_BY);
                    int userId = Integer.parseInt(userProfileModel.getMemberId());
                    MatrimonyUtils.sendMemberIdTask(memberId, userId);
                    String name = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.MEMBER_NAME);
                    String imageUrl = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.PROFILE_URL);

                    String message = "" + name + " " + "viewed your profile";

                    MatrimonyUtils.sendProfileViewNotification(userProfileModel.getDeviceId(), message, imageUrl,
                            String.valueOf(userProfileModel.getMemberId()), String.valueOf(memberId), name);

                    tv_invite.setEnabled(true);

                    imgInvite.setVisibility(View.GONE);
                    imgInvited.setVisibility(View.GONE);
                    tv_invite.setVisibility(View.GONE);
                    tv_invited.setVisibility(View.GONE);
                    imgAccept.setVisibility(View.GONE);
                    tv_accept.setVisibility(View.GONE);

                    if (accountCreatedBy.equals("P")) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                imgInvite.setImageAlpha(64);
//            }
                    } else if (accountCreatedBy.equals("C")) {

                        if (Utilities.getString(userProfileModel.getInvited()).equals(String.valueOf(userProfileModel.getMemberId()))) {
                            imgAccept.setVisibility(View.VISIBLE);
                            tv_accept.setVisibility(View.VISIBLE);
                            imgInvited.setVisibility(View.GONE);
                            imgInvite.setVisibility(View.GONE);
                            tv_invite.setVisibility(View.GONE);
                            tv_invited.setVisibility(View.GONE);
                        } else {
//                if (TextUtils.isEmpty(recentlyJointModel.getInvitedFlag()) || recentlyJointModel.getInvitedFlag().contains("0")) {
//
//                    imgInvite.setVisibility(View.VISIBLE);
//                    imgInvited.setVisibility(View.GONE);
//                    tv_invite.setVisibility(View.VISIBLE);
//                    tv_invited.setVisibility(View.GONE);
//                    imgAccept.setVisibility(View.GONE);
//                } else {
//                    imgInvited.setVisibility(View.VISIBLE);
//                    imgInvite.setVisibility(View.GONE);
//                    tv_invite.setVisibility(View.GONE);
//                    tv_invited.setVisibility(View.VISIBLE);
//                    imgAccept.setVisibility(View.GONE);
//                }
                        }
                    }

                    if (TextUtils.isEmpty(userProfileModel.getInvited()) || userProfileModel.getInvited().contains("0")) {
                        imgInvite.setVisibility(View.VISIBLE);
                        imgInvited.setVisibility(View.GONE);
                        tv_invite.setVisibility(View.VISIBLE);
                        tv_invited.setVisibility(View.GONE);
                        imgAccept.setVisibility(View.GONE);
                    } else {
                        imgInvited.setVisibility(View.VISIBLE);
                        imgInvite.setVisibility(View.GONE);
                        tv_invite.setVisibility(View.GONE);
                        tv_invited.setVisibility(View.VISIBLE);
                        imgAccept.setVisibility(View.GONE);
                    }

                    if (TextUtils.isEmpty(userProfileModel.getShorted()) || userProfileModel.getShorted().contains("0")) {
                        imgShortlist.setVisibility(View.VISIBLE);
                        imgShortlisted.setVisibility(View.GONE);
                        tv_shortlist.setVisibility(View.VISIBLE);
                        tv_shortlisted.setVisibility(View.GONE);

                    } else {
                        imgShortlisted.setVisibility(View.VISIBLE);
                        tv_shortlist.setVisibility(View.GONE);
                        tv_shortlist.setVisibility(View.GONE);
                        tv_shortlisted.setVisibility(View.VISIBLE);
                    }

                    imgShortlist.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int receiverId = Integer.parseInt(userProfileModel.getMemberId());
                            MatrimonyUtils.addShortlistTask(mContext, memberId, receiverId, "1");

                            imgShortlisted.setVisibility(View.VISIBLE);
                            imgShortlist.setVisibility(View.GONE);

                            tv_shortlist.setVisibility(View.GONE);
                            tv_shortlisted.setVisibility(View.VISIBLE);
                        }
                    });

                    imgShortlisted.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int receiverId = Integer.parseInt(userProfileModel.getMemberId());
                            MatrimonyUtils.addShortlistTask(mContext, memberId, receiverId, "0");

                            imgShortlisted.setVisibility(View.GONE);
                            imgShortlist.setVisibility(View.VISIBLE);

                            tv_shortlist.setVisibility(View.VISIBLE);
                            tv_shortlisted.setVisibility(View.GONE);
                        }
                    });

                    imgInvite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (accountCreatedBy.equals("P")) {
                                Toast.makeText(mContext, "You cannot invite Candidate profile ", Toast.LENGTH_LONG).show();
                            } else {

                                int senderId = Integer.parseInt(userProfileModel.getMemberId());
                                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "1");

                                String name = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.MEMBER_NAME);
                                String imageUrl = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.PROFILE_URL);

                                String message = "Invitation Received from " + name;

                                MatrimonyUtils.sendInvitationNotification(userProfileModel.getDeviceId(), message,
                                        imageUrl, String.valueOf(userProfileModel.getMemberId()), String.valueOf(memberId),
                                        name);

                                imgInvited.setVisibility(View.VISIBLE);
                                imgInvite.setVisibility(View.GONE);

                                tv_invite.setVisibility(View.GONE);
                                tv_invited.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                    imgInvited.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int senderId = Integer.parseInt(userProfileModel.getMemberId());
                            MatrimonyUtils.inviteTask(mContext, memberId, senderId, "0");

                            imgInvite.setVisibility(View.VISIBLE);
                            imgInvited.setVisibility(View.GONE);

                            tv_invite.setVisibility(View.VISIBLE);
                            tv_invited.setVisibility(View.GONE);
                        }
                    });


                    download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                           // Toast.makeText(SearchDisplayActivity.this, "1243", Toast.LENGTH_SHORT).show();

                            fileName = userProfileModel.getFirstName() + "_" + userProfileModel.getLastName();
                            getPdfUrlPresenter.getProfilePdf(userProfileModel.getMemberId());
                        }
                    });

                    if(userProfileModel.getIspremium().equalsIgnoreCase("0")){
                        img_det_premium.setVisibility(View.GONE);
                    }else {
                        img_det_premium.setVisibility(View.VISIBLE);
                    }

                    if (userProfileModel.getAccountCreatedBy().equals("P")) {

                        tv_age_heading.setVisibility(View.GONE);
                        tv_age_heading_dot.setVisibility(View.GONE);
                        tv_height_heading.setVisibility(View.GONE);
                        tv_height_heading_dot.setVisibility(View.GONE);
                        tv_marriedStatus_heading.setVisibility(View.GONE);
                        tv_marriedStatus_heading_dot.setVisibility(View.GONE);
                        tv_motherTounge_heading.setVisibility(View.GONE);
                        tv_motherTounge_heading_dot.setVisibility(View.GONE);
                        tv_physicalStatus_heading.setVisibility(View.GONE);
                        tv_physicalStatus_heading_dot.setVisibility(View.GONE);
                        tv_bodyType_heading.setVisibility(View.GONE);
                        tv_bodyType_heading_dot.setVisibility(View.GONE);
                        tv_complexion_heading.setVisibility(View.GONE);
                        tv_complexion_heading_dot.setVisibility(View.GONE);
                        tv_eating_heading.setVisibility(View.GONE);
                        tv_eating_heading_dot.setVisibility(View.GONE);
                        tv_drinking_heading.setVisibility(View.GONE);
                        tv_drinking_heading_dot.setVisibility(View.GONE);
                        tv_smoking_heading.setVisibility(View.GONE);
                        tv_smoking_heading_dot.setVisibility(View.GONE);
                        tv_profileView_introduction.setVisibility(View.GONE);
                        tv_profileView_age.setVisibility(View.GONE);
                        tv_profileView_height.setVisibility(View.GONE);
                        tv_profileView_married_status.setVisibility(View.GONE);
                        tv_profileView_mother_tongue.setVisibility(View.GONE);
                        tv_profileView_physical_status.setVisibility(View.GONE);
                        tv_profileView_body_type.setVisibility(View.GONE);
                        tv_profileView_complexion.setVisibility(View.GONE);
                        tv_profileView_drinking_habit.setVisibility(View.GONE);
                        tv_profileView_smoking_habit.setVisibility(View.GONE);
                        tv_profileView_eating_habit.setVisibility(View.GONE);

                        layout_profession.setVisibility(View.GONE);
                        layout_familyDetails.setVisibility(View.GONE);
                        layout_location.setVisibility(View.GONE);
                        layout_Astro.setVisibility(View.GONE);
                        view_profession.setVisibility(View.GONE);
                        view_family.setVisibility(View.GONE);
                        view_astro.setVisibility(View.GONE);
                        view_location.setVisibility(View.GONE);
                        tv_professionHeading.setVisibility(View.GONE);
                        tv_familyHeading.setVisibility(View.GONE);
                        tv_locationHeading.setVisibility(View.GONE);
                        tv_astroHeading.setVisibility(View.GONE);

                        String age_height_other = userProfileModel.getMemberCity() +
                                ", " + userProfileModel.getMemberState() + ", " + userProfileModel.getMemberCountry();
                        tv_otherDetails.setText(age_height_other);

                    } else if (userProfileModel.getAccountCreatedBy().equals("C")) {

                        try {
                            userProfileModel.setAge(MatrimonyUtils.getAge(userProfileModel.getDOB()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        String age_height_other = userProfileModel.getAge()
                                + ", " + userProfileModel.getMemberHeight()
                                + ", " + userProfileModel.getMemberCity()
                                + ", " + userProfileModel.getMemberState()
                                + ", " + userProfileModel.getMemberCountry();

                        tv_otherDetails.setText(age_height_other);

                        tv_profileView_age.setText(userProfileModel.getAge());
                        tv_profileView_height.setText(userProfileModel.getMemberHeight());
                        tv_profileView_married_status.setText(userProfileModel.getMarriedStatus());
                        tv_profileView_mother_tongue.setText(userProfileModel.getMotherTongue());

                        tv_profileView_birth_date.setText(userProfileModel.getDOB());
                        tv_profileView_introduction.setText(userProfileModel.getSelfIntroduction());
                        tv_profileView_eduction.setText(userProfileModel.getMemberEducationName());
                        tv_profileView_eduction_in.setText(userProfileModel.getEducationInName());
                        tv_profileView_working_with.setText(userProfileModel.getEducationWithName());
                        tv_profileView_working_as.setText(userProfileModel.getMemberOccupation());
                        tv_profileView_annual_income.setText(userProfileModel.getMemberInCome());
                        tv_profileView_family_type.setText(userProfileModel.getFamilyType());
                        tv_profileView_father_status.setText(userProfileModel.getFatherstatus());
                        tv_profileView_mother_status.setText(userProfileModel.getMotherstatus());
                        tv_profileView_no_of_brother.setText(userProfileModel.getNoOfBorther());
                        tv_profileView_no_of_brother_married.setText(userProfileModel.getNoOfBortherMarried());
                        tv_profileView_no_of_sister.setText(userProfileModel.getNoOfSister());
                        tv_profileView_no_of_sister_married.setText(userProfileModel.getNoOfSisterMarried());
                        tv_profileView_birth_place.setText(userProfileModel.getBirthPlace());
                        tv_profileView_birth_time.setText(userProfileModel.getBirthTime());
                        tv_profileView_gotra.setText(userProfileModel.getGotra());
                        tv_profileView_rashi.setText(userProfileModel.getRashi());
                        tv_profileView_nakshtra.setText(userProfileModel.getNakshtra());
                        tv_profileView_charan.setText(userProfileModel.getCharan());
                        tv_profileView_naadi.setText(userProfileModel.getNaddi());
                        tv_profileView_gan.setText(userProfileModel.getGan());
                        tv_profileView_physical_status.setText(userProfileModel.getPhysicalchallenge());
                        tv_profileView_drinking_habit.setText(userProfileModel.getDrinkhabit());
                        tv_profileView_smoking_habit.setText(userProfileModel.getSmokehabit());
                        tv_profileView_eating_habit.setText(userProfileModel.getFoodtype());
                        tv_profileView_manglik.setText(userProfileModel.getManglik());
                        tv_profileView_body_type.setText(userProfileModel.getBodytype());
                        tv_profileView_complexion.setText(userProfileModel.getComplexion());

                        AppCompatImageView img_kundli = view.findViewById(R.id.img_profileView_kundli);
                        TextView tv_kundli = view.findViewById(R.id.tv_profileView_kundli);

                        if (userProfileModel.getKundaliPhoto().equals("") || userProfileModel.getKundaliPhoto().equalsIgnoreCase("")) {

                            img_kundli.setVisibility(View.GONE);
                            tv_kundli.setVisibility(View.GONE);
                        } else {

                            img_kundli.setVisibility(View.VISIBLE);
                            tv_kundli.setVisibility(View.VISIBLE);
                            img_kundli.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ImageDisplayFragment imageDisplayFragment = new ImageDisplayFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("kundali", userProfileModel.getKundaliPhoto());
                                    bundle.putString("memberName", userProfileModel.getFirstName() + "_" + userProfileModel.getLastName());
                                    imageDisplayFragment.setArguments(bundle);
                                    imageDisplayFragment.show(getSupportFragmentManager(), "Kundali");
                                }
                            });

                            Picasso.get()
                                    .load(userProfileModel.getKundaliPhoto())
                                    .placeholder(R.drawable.circle_preview)
                                    .into(img_kundli);
                        }
                    }

                    tv_memberId.setText(userProfileModel.getUniqueId());

                    String profileCreatedBy = userProfileModel.getAccountCreatedBy();
                    String profileManagedBy = userProfileModel.getAccountManageBy();

                    switch (profileManagedBy) {
                        case "Self":

                            tv_profile_managed_by.setText("Profile Created by Self");
                            break;
                        case "Parent":

                            tv_profile_managed_by.setText("Profile Created by Parent");
                            break;
                        case "Sibling":

                            tv_profile_managed_by.setText("Profile Created by Sibling");
                            break;
                    }

                    if (profileCreatedBy.equals("C")) {
                        tv_profile_created_by.setText(candidate);
                        tv_profileView_profile_created_by.setText("Self");
                    } else {
                        tv_profile_created_by.setVisibility(View.GONE);
                        tv_profileView_profile_created_by.setText("Parent");
                        tv_profile_managed_by.setText("Profile Created by Parent");
                    }

                    String username = userProfileModel.getFirstName() + " " + userProfileModel.getLastName();
                    tv_profileView_name.setText(username);
                    tv_profileView_gender.setText(userProfileModel.getGender());

                    tv_profileView_country.setText(userProfileModel.getMemberCountry());
                    tv_profileView_state.setText(userProfileModel.getMemberState());
                    tv_profileView_city.setText(userProfileModel.getMemberCity());

                    if (userProfileModel.getMainProfilePhoto().isEmpty()) {

                        Picasso.get()
                                .load(R.drawable.img_preview)
                                .placeholder(R.drawable.img_preview)
                                .error(R.drawable.img_preview)
                                .into(profileUrl);
                    } else {
                        Picasso.get()
                                .load(userProfileModel.getMainProfilePhoto())
                                .placeholder(R.drawable.img_preview)
                                .into(profileUrl);
                    }

                    profileUrl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int mId = Integer.parseInt(userProfileModel.getMemberId());
                            MatrimonyUtils.loadImages(mContext, mId, null);
                        }
                    });


                    LinearLayout chatContainer = (LinearLayout) view.findViewById(R.id.chatContainer);
                    chatContainer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(mUserType==1) {
                                mContext.startActivity(new Intent(mContext, ChatActivity.class));

                                String name = userProfileModel.getFirstName() + " " + userProfileModel.getLastName();
                                String imageUrl = userProfileModel.getMainProfilePhoto();

                                SharedPrefsHelper.getInstance(mContext).saveIntVal(SharedPrefsHelper.MESSAGE_RECEIVER_ID,
                                        Integer.parseInt(userProfileModel.getMemberId()));
                                SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_NAME,
                                        name);
                                SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_IMAGE,
                                        imageUrl);
                                SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.RECEIVER_TOKEN_ID,
                                        userProfileModel.getDeviceId());
                            }else {
                                displayPreimiumalert();
                            }
                        }
                    });



                   /* imgChat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mContext.startActivity(new Intent(mContext, ChatActivity.class));

                            String name = userProfileModel.getFirstName() + " " + userProfileModel.getLastName();
                            String imageUrl = userProfileModel.getMainProfilePhoto();

                            SharedPrefsHelper.getInstance(mContext).saveIntVal(SharedPrefsHelper.MESSAGE_RECEIVER_ID,
                                    Integer.parseInt(userProfileModel.getMemberId()));
                            SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_NAME,
                                    name);
                            SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_IMAGE,
                                    imageUrl);
                            SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.RECEIVER_TOKEN_ID,
                                    userProfileModel.getDeviceId());
                        }
                    });*/

                    String onlineTime = userProfileModel.getOnlineTime();
                    String offlineTime = userProfileModel.getOffline_Time();

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
                    Date onlineDate = null, offlineDate = null;

                    try {
                        if (!TextUtils.isEmpty(onlineTime) && onlineTime.length() > 5) {
                            onlineDate = formatter.parse(onlineTime);
                        }
                        if (!TextUtils.isEmpty(offlineTime) && offlineTime.length() > 5) {
                            offlineDate = formatter.parse(offlineTime);
                        }

                        assert onlineDate != null;
                        assert offlineDate != null;

                        if (onlineDate != null && offlineDate != null) {
                            if (onlineDate.compareTo(offlineDate) < 0) {
                                userProfileModel.setOnline(false);
                            } else {
                                userProfileModel.setOnline(true);
                            }
                        } else {
                            userProfileModel.setOnline(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (userProfileModel.getOnlinestatus().equalsIgnoreCase("online")) {
                        tv_online.setVisibility(View.VISIBLE);
                        tv_online.setText("Online");
                        tv_offline.setVisibility(View.GONE);
                    } else {
                        tv_offline.setVisibility(View.VISIBLE);
                        tv_offline.setText("Offline ");
                        tv_online.setVisibility(View.GONE);
                    }

//                    Utilities.showContactNumber(mContext, tvContact, Utilities.getString(userProfileModel.getMobile()));
//                    Utilities.showEmail(mContext, tvEmail, Utilities.getString(userProfileModel.getEmailid()));
                    //            kundli_photo.setVisibility(View.GONE);
                    //            view_kundli_view.setVisibility(View.GONE);

                    showBasicDetails(userProfileModel, basicDetailsContainer);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        viewPager.setAdapter(customPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // viewPosition = position;
                String toolbarFName = userProfileModels.get(position).getFirstName();
                toolbarFName = toolbarFName.substring(0, 1).toUpperCase() + toolbarFName.substring(1).toLowerCase();
                String toolbarLName = userProfileModels.get(position).getLastName();
                toolbarLName = toolbarLName.substring(0, 1).toUpperCase() + toolbarLName.substring(1).toLowerCase();
                toolbar.setTitle(toolbarFName + " " + toolbarLName);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(pagePosition);
    }

    private void showBasicDetails(final UserProfileModel userProfileModel, LinearLayout basicDetailsContainer) {
        basicDetailsContainer.addView(addData("Name", userProfileModel.getFirstName() + " " + userProfileModel.getLastName(),
                null));
       /* if (Utilities.isEmpty(userProfileModel.getEmailid()) && Utilities.isEmpty(userProfileModel.getMobile())) {
            return;
        }*/

        basicDetailsContainer.addView(addViewContact(new ViewClickListener() {
            @Override
            public void onViewClick(View view) {
                ShowContactFragment showContactFragment = new ShowContactFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CONTACT_NO, userProfileModel.getMobile());
                bundle.putString(Constants.EMAIL, userProfileModel.getEmailid());
                showContactFragment.setArguments(bundle);

                showContactFragment.show(getSupportFragmentManager(), "ShowContactFragment");
            }
        }));
    }

    @Override
    public void showUserDetails(UserDetailsModel userDetailsModel) {
        View view = boundView;

        final UserDetailsModel userProfileModel = userDetailsModel;
        Log.e("onBoundPager", userProfileModel.getMemberId());
        // final UserProfileModel userProfileModel = (UserProfileModel) object;
        ImageView download=view.findViewById(R.id.download);
        profileUrl = view.findViewById(R.id.img_profileView_image);
        tv_memberId = view.findViewById(R.id.tv_profileView_memberId);
        tv_otherDetails = view.findViewById(R.id.tv_profile_otherDetails);
        imgInvite = view.findViewById(R.id.img_profileView_invite);
        imgInvited = view.findViewById(R.id.img_profileView_invited);
        final ImageView imgAccept = view.findViewById(R.id.img_profileView_accept);
        final ImageView imgShortlist = view.findViewById(R.id.img_profileView_shortlist);
        final ImageView imgShortlisted = view.findViewById(R.id.img_profileView_shortlisted);
        final TextView tv_shortlist = view.findViewById(R.id.tv_shortlist);
        final TextView tv_shortlisted = view.findViewById(R.id.tv_shortlisted);
        final TextView tv_invite = view.findViewById(R.id.tv_profileView_invite);
        final TextView tv_invited = view.findViewById(R.id.tv_profileView_invited);
        final TextView tv_accept = view.findViewById(R.id.tv_profileView_accept);
        ImageView imgChat = view.findViewById(R.id.img_profileView_chat);
        TextView tv_profile_created_by = view.findViewById(R.id.tv_profile_created_by);
        TextView tv_profile_managed_by = view.findViewById(R.id.tv_profile_managedBy);
        TextView tv_profileView_introduction = view.findViewById(R.id.tv_profileView_introduction);
        TextView tv_profileView_name = view.findViewById(R.id.tv_profileView_name);
        TextView tv_profileView_gender = view.findViewById(R.id.tv_profileView_gender);
        TextView tv_profileView_age = view.findViewById(R.id.tv_profileView_age);
        TextView tv_profileView_height = view.findViewById(R.id.tv_profileView_height);
        TextView tv_profileView_married_status = view.findViewById(R.id.tv_profileView_married_status);
        TextView tv_profileView_mother_tongue = view.findViewById(R.id.tv_profileView_mother_tongue);
        TextView tv_profileView_physical_status = view.findViewById(R.id.tv_profileView_physical_status);
        TextView tv_profileView_body_type = view.findViewById(R.id.tv_profileView_body_type);
        TextView tv_profileView_complexion = view.findViewById(R.id.tv_profileView_complexion);
        TextView tv_profileView_profile_created_by = view.findViewById(R.id.tv_profileView_profile_created_by);
        TextView tv_profileView_eating_habit = view.findViewById(R.id.tv_profileView_eating_habit);
        TextView tv_profileView_drinking_habit = view.findViewById(R.id.tv_profileView_drinking_habit);
        TextView tv_profileView_smoking_habit = view.findViewById(R.id.tv_profileView_smoking_habit);
        TextView tv_profileView_eduction = view.findViewById(R.id.tv_profileView_eduction);
        TextView tv_profileView_eduction_in = view.findViewById(R.id.tv_profileView_eduction_in);
        TextView tv_profileView_working_with = view.findViewById(R.id.tv_profileView_working_with);
        TextView tv_profileView_working_as = view.findViewById(R.id.tv_profileView_working_as);
        TextView tv_profileView_annual_income = view.findViewById(R.id.tv_profileView_annual_income);
        TextView tv_profileView_country = view.findViewById(R.id.tv_profileView_country);
        TextView tv_profileView_state = view.findViewById(R.id.tv_profileView_state);
        TextView tv_profileView_city = view.findViewById(R.id.tv_profileView_city);
        TextView tv_profileView_family_type = view.findViewById(R.id.tv_profileView_family_type);
        TextView tv_profileView_father_status = view.findViewById(R.id.tv_profileView_father_status);
        TextView tv_profileView_mother_status = view.findViewById(R.id.tv_profileView_mother_status);
        TextView tv_profileView_no_of_brother = view.findViewById(R.id.tv_profileView_no_of_brother);
        TextView tv_profileView_no_of_brother_married = view.findViewById(R.id.tv_profileView_no_of_brother_married);
        TextView tv_profileView_no_of_sister = view.findViewById(R.id.tv_profileView_no_of_sister);
        TextView tv_profileView_no_of_sister_married = view.findViewById(R.id.tv_profileView_no_of_sister_married);
        TextView tv_profileView_birth_date = view.findViewById(R.id.tv_profileView_birth_date);
        TextView tv_profileView_birth_time = view.findViewById(R.id.tv_profileView_birth_time);
        TextView tv_profileView_birth_place = view.findViewById(R.id.tv_profileView_birth_place);
        TextView tv_profileView_gotra = view.findViewById(R.id.tv_profileView_gotra);
        TextView tv_profileView_rashi = view.findViewById(R.id.tv_profileView_rashi);
        TextView tv_profileView_nakshtra = view.findViewById(R.id.tv_profileView_nakshtra);
        TextView tv_profileView_charan = view.findViewById(R.id.tv_profileView_charan);
        TextView tv_profileView_naadi = view.findViewById(R.id.tv_profileView_naadi);
        TextView tv_profileView_gan = view.findViewById(R.id.tv_profileView_gan);
        TextView tv_profileView_manglik = view.findViewById(R.id.tv_profileView_manglik);
        TextView tv_online = view.findViewById(R.id.tv_profileView_online);
        TextView tv_offline = view.findViewById(R.id.tv_profileView_Offline);
        LinearLayout layout_profession = view.findViewById(R.id.layout_profileViewProfession);
        LinearLayout layout_location = view.findViewById(R.id.layout_tv_profileViewLocation);
        LinearLayout layout_familyDetails = view.findViewById(R.id.layout_profileViewFamilyDetails);
        LinearLayout layout_Astro = view.findViewById(R.id.layout_profileViewAstroDetails);
        View view_profession = view.findViewById(R.id.view_profileViewProfession);
        View view_location = view.findViewById(R.id.view_profileViewLocation);
        View view_family = view.findViewById(R.id.view_profileViewFamilyDetails);
        View view_astro = view.findViewById(R.id.view_profileViewAstroDetails);
        TextView tv_professionHeading = view.findViewById(R.id.tv_profileViewProfession);
        TextView tv_locationHeading = view.findViewById(R.id.tv_profileViewLocation);
        TextView tv_familyHeading = view.findViewById(R.id.tv_profileViewFamilyDetails);
        TextView tv_astroHeading = view.findViewById(R.id.tv_profileViewAstroDetails);
        TextView tv_age_heading = view.findViewById(R.id.tv_age);
        TextView tv_height_heading = view.findViewById(R.id.tv_height);
        TextView tv_marriedStatus_heading = view.findViewById(R.id.tv_married_status);
        TextView tv_motherTounge_heading = view.findViewById(R.id.tv_mother_tongue);
        TextView tv_physicalStatus_heading = view.findViewById(R.id.tv_physical);
        TextView tv_bodyType_heading = view.findViewById(R.id.tv_body_type);
        TextView tv_complexion_heading = view.findViewById(R.id.tv_body_complexion);
        TextView tv_eating_heading = view.findViewById(R.id.tv_Eating_Habits);
        TextView tv_drinking_heading = view.findViewById(R.id.tv_drinking_habit);
        TextView tv_smoking_heading = view.findViewById(R.id.tv_smoking_habits);
        TextView tv_age_heading_dot = view.findViewById(R.id.tv_age_dot);
        TextView tv_height_heading_dot = view.findViewById(R.id.tv_height_dot);
        TextView tv_marriedStatus_heading_dot = view.findViewById(R.id.tv_married_statusDot);
        TextView tv_motherTounge_heading_dot = view.findViewById(R.id.tv_mother_tongueDot);
        TextView tv_physicalStatus_heading_dot = view.findViewById(R.id.tv_physicalDot);
        TextView tv_bodyType_heading_dot = view.findViewById(R.id.tv_body_typeDot);
        TextView tv_complexion_heading_dot = view.findViewById(R.id.tv_body_complexionDot);
        TextView tv_eating_heading_dot = view.findViewById(R.id.tv_Eating_HabitsDot);
        TextView tv_drinking_heading_dot = view.findViewById(R.id.tv_drinking_habitDot);
        TextView tv_smoking_heading_dot = view.findViewById(R.id.tv_smoking_habitsDot);
        TextView tvCaste = view.findViewById(R.id.tv_profileView_caste);
        TextView tvSubCaste = view.findViewById(R.id.tv_profileView_subCaste);
        TextView tvContact = view.findViewById(R.id.tv_contact);
        TextView tvEmail = view.findViewById(R.id.tv_email);

        memberId = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.MEMBER_ID);
        final String accountCreatedBy = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.ACCOUNT_CREATED_BY);
        int userId = Integer.parseInt(userProfileModel.getMemberId());
        MatrimonyUtils.sendMemberIdTask(memberId, userId);
        String name = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.MEMBER_NAME);
        String imageUrl = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.PROFILE_URL);

        String message = "" + name + " " + "viewed your profile";

        MatrimonyUtils.sendProfileViewNotification(userProfileModel.getDeviceId(), message, imageUrl,
                String.valueOf(userProfileModel.getMemberId()), String.valueOf(memberId), name);

        tv_invite.setEnabled(true);

        imgInvite.setVisibility(View.GONE);
        imgInvited.setVisibility(View.GONE);
        tv_invite.setVisibility(View.GONE);
        tv_invited.setVisibility(View.GONE);
        imgAccept.setVisibility(View.GONE);
        tv_accept.setVisibility(View.GONE);

        if (accountCreatedBy.equals("P")) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                imgInvite.setImageAlpha(64);
//            }
        } else if (accountCreatedBy.equals("C")) {

            if (Utilities.getString(userProfileModel.getInvited()).equals(String.valueOf(userProfileModel.getMemberId()))) {
                imgAccept.setVisibility(View.VISIBLE);
                tv_accept.setVisibility(View.VISIBLE);
                imgInvited.setVisibility(View.GONE);
                imgInvite.setVisibility(View.GONE);
                tv_invite.setVisibility(View.GONE);
                tv_invited.setVisibility(View.GONE);
            } else {
//                if (TextUtils.isEmpty(recentlyJointModel.getInvitedFlag()) || recentlyJointModel.getInvitedFlag().contains("0")) {
//
//                    imgInvite.setVisibility(View.VISIBLE);
//                    imgInvited.setVisibility(View.GONE);
//                    tv_invite.setVisibility(View.VISIBLE);
//                    tv_invited.setVisibility(View.GONE);
//                    imgAccept.setVisibility(View.GONE);
//                } else {
//                    imgInvited.setVisibility(View.VISIBLE);
//                    imgInvite.setVisibility(View.GONE);
//                    tv_invite.setVisibility(View.GONE);
//                    tv_invited.setVisibility(View.VISIBLE);
//                    imgAccept.setVisibility(View.GONE);
//                }
            }
        }

        if (TextUtils.isEmpty(userProfileModel.getInvited()) || userProfileModel.getInvited().contains("0")) {
            imgInvite.setVisibility(View.VISIBLE);
            imgInvited.setVisibility(View.GONE);
            tv_invite.setVisibility(View.VISIBLE);
            tv_invited.setVisibility(View.GONE);
            imgAccept.setVisibility(View.GONE);
        } else {
            imgInvited.setVisibility(View.VISIBLE);
            imgInvite.setVisibility(View.GONE);
            tv_invite.setVisibility(View.GONE);
            tv_invited.setVisibility(View.VISIBLE);
            imgAccept.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(userProfileModel.getShorted()) || userProfileModel.getShorted().contains("0")) {
            imgShortlist.setVisibility(View.VISIBLE);
            imgShortlisted.setVisibility(View.GONE);
            tv_shortlist.setVisibility(View.VISIBLE);
            tv_shortlisted.setVisibility(View.GONE);

        } else {
            imgShortlisted.setVisibility(View.VISIBLE);
            tv_shortlist.setVisibility(View.GONE);
            tv_shortlist.setVisibility(View.GONE);
            tv_shortlisted.setVisibility(View.VISIBLE);
        }

        imgShortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int receiverId = Integer.parseInt(userProfileModel.getMemberId());
                MatrimonyUtils.addShortlistTask(mContext, memberId, receiverId, "1");

                imgShortlisted.setVisibility(View.VISIBLE);
                imgShortlist.setVisibility(View.GONE);

                tv_shortlist.setVisibility(View.GONE);
                tv_shortlisted.setVisibility(View.VISIBLE);
            }
        });

        imgShortlisted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int receiverId = Integer.parseInt(userProfileModel.getMemberId());
                MatrimonyUtils.addShortlistTask(mContext, memberId, receiverId, "0");

                imgShortlisted.setVisibility(View.GONE);
                imgShortlist.setVisibility(View.VISIBLE);

                tv_shortlist.setVisibility(View.VISIBLE);
                tv_shortlisted.setVisibility(View.GONE);
            }
        });

        imgInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountCreatedBy.equals("P")) {
                    Toast.makeText(mContext, "You cannot invite Candidate profile ", Toast.LENGTH_LONG).show();
                } else {

                    int senderId = Integer.parseInt(userProfileModel.getMemberId());
                    MatrimonyUtils.inviteTask(mContext, memberId, senderId, "1");

                    String name = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.MEMBER_NAME);
                    String imageUrl = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.PROFILE_URL);

                    String message = "Invitation Received from " + name;

                    MatrimonyUtils.sendInvitationNotification(userProfileModel.getDeviceId(), message,
                            imageUrl, String.valueOf(userProfileModel.getMemberId()), String.valueOf(memberId),
                            name);

                    imgInvited.setVisibility(View.VISIBLE);
                    imgInvite.setVisibility(View.GONE);

                    tv_invite.setVisibility(View.GONE);
                    tv_invited.setVisibility(View.VISIBLE);
                }
            }
        });

        imgInvited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int senderId = Integer.parseInt(userProfileModel.getMemberId());
                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "0");

                imgInvite.setVisibility(View.VISIBLE);
                imgInvited.setVisibility(View.GONE);

                tv_invite.setVisibility(View.VISIBLE);
                tv_invited.setVisibility(View.GONE);
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SearchDisplayActivity.this, "a", Toast.LENGTH_SHORT).show();
            }
        });

        if (userProfileModel.getAccountCreatedBy().equals("P")) {

            tv_age_heading.setVisibility(View.GONE);
            tv_age_heading_dot.setVisibility(View.GONE);
            tv_height_heading.setVisibility(View.GONE);
            tv_height_heading_dot.setVisibility(View.GONE);
            tv_marriedStatus_heading.setVisibility(View.GONE);
            tv_marriedStatus_heading_dot.setVisibility(View.GONE);
            tv_motherTounge_heading.setVisibility(View.GONE);
            tv_motherTounge_heading_dot.setVisibility(View.GONE);
            tv_physicalStatus_heading.setVisibility(View.GONE);
            tv_physicalStatus_heading_dot.setVisibility(View.GONE);
            tv_bodyType_heading.setVisibility(View.GONE);
            tv_bodyType_heading_dot.setVisibility(View.GONE);
            tv_complexion_heading.setVisibility(View.GONE);
            tv_complexion_heading_dot.setVisibility(View.GONE);
            tv_eating_heading.setVisibility(View.GONE);
            tv_eating_heading_dot.setVisibility(View.GONE);
            tv_drinking_heading.setVisibility(View.GONE);
            tv_drinking_heading_dot.setVisibility(View.GONE);
            tv_smoking_heading.setVisibility(View.GONE);
            tv_smoking_heading_dot.setVisibility(View.GONE);
            tv_profileView_introduction.setVisibility(View.GONE);
            tv_profileView_age.setVisibility(View.GONE);
            tv_profileView_height.setVisibility(View.GONE);
            tv_profileView_married_status.setVisibility(View.GONE);
            tv_profileView_mother_tongue.setVisibility(View.GONE);
            tv_profileView_physical_status.setVisibility(View.GONE);
            tv_profileView_body_type.setVisibility(View.GONE);
            tv_profileView_complexion.setVisibility(View.GONE);
            tv_profileView_drinking_habit.setVisibility(View.GONE);
            tv_profileView_smoking_habit.setVisibility(View.GONE);
            tv_profileView_eating_habit.setVisibility(View.GONE);

            layout_profession.setVisibility(View.GONE);
            layout_familyDetails.setVisibility(View.GONE);
            layout_location.setVisibility(View.GONE);
            layout_Astro.setVisibility(View.GONE);
            view_profession.setVisibility(View.GONE);
            view_family.setVisibility(View.GONE);
            view_astro.setVisibility(View.GONE);
            view_location.setVisibility(View.GONE);
            tv_professionHeading.setVisibility(View.GONE);
            tv_familyHeading.setVisibility(View.GONE);
            tv_locationHeading.setVisibility(View.GONE);
            tv_astroHeading.setVisibility(View.GONE);

            String age_height_other = userProfileModel.getMemberCity() +
                    ", " + userProfileModel.getMemberState() + ", " + userProfileModel.getMemberCountry();
            tv_otherDetails.setText(age_height_other);

        } else if (userProfileModel.getAccountCreatedBy().equals("C")) {

            try {
                userProfileModel.setAge(MatrimonyUtils.getAge(userProfileModel.getDOB()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            String age_height_other = userProfileModel.getAge()
                    + ", " + userProfileModel.getMemberHeight()
                    + ", " + userProfileModel.getMemberCity()
                    + ", " + userProfileModel.getMemberState()
                    + ", " + userProfileModel.getMemberCountry();

            tv_otherDetails.setText(age_height_other);

            tv_profileView_age.setText(userProfileModel.getAge());
            tv_profileView_height.setText(userProfileModel.getMemberHeight());
            tv_profileView_married_status.setText(userProfileModel.getMarriedStatus());
            tv_profileView_mother_tongue.setText(userProfileModel.getMotherTongue());

            tv_profileView_birth_date.setText(userProfileModel.getDOB());
            tv_profileView_introduction.setText(userProfileModel.getSelfIntroduction());
            tv_profileView_eduction.setText(userProfileModel.getMemberEducationName());
            tv_profileView_eduction_in.setText(userProfileModel.getEducationInName());
            tv_profileView_working_with.setText(userProfileModel.getEducationWithName());
            tv_profileView_working_as.setText(userProfileModel.getMemberOccupation());
            tv_profileView_annual_income.setText(userProfileModel.getMemberInCome());
            tv_profileView_family_type.setText(userProfileModel.getFamilyType());
            tv_profileView_father_status.setText(userProfileModel.getFatherstatus());
            tv_profileView_mother_status.setText(userProfileModel.getMotherstatus());
            tv_profileView_no_of_brother.setText(userProfileModel.getNoOfBorther());
            tv_profileView_no_of_brother_married.setText(userProfileModel.getNoOfBortherMarried());
            tv_profileView_no_of_sister.setText(userProfileModel.getNoOfSister());
            tv_profileView_no_of_sister_married.setText(userProfileModel.getNoOfSisterMarried());
            tv_profileView_birth_place.setText(userProfileModel.getBirthPlace());
            tv_profileView_birth_time.setText(userProfileModel.getBirthTime());
            tv_profileView_gotra.setText(userProfileModel.getGotra());
            tv_profileView_rashi.setText(userProfileModel.getRashi());
            tv_profileView_nakshtra.setText(userProfileModel.getNakshtra());
            tv_profileView_charan.setText(userProfileModel.getCharan());
            tv_profileView_naadi.setText(userProfileModel.getNaddi());
            tv_profileView_gan.setText(userProfileModel.getGan());
            tv_profileView_physical_status.setText(userProfileModel.getPhysicalchallenge());
            tv_profileView_drinking_habit.setText(userProfileModel.getDrinkhabit());
            tv_profileView_smoking_habit.setText(userProfileModel.getSmokehabit());
            tv_profileView_eating_habit.setText(userProfileModel.getFoodtype());
            tv_profileView_manglik.setText(userProfileModel.getManglik());
            tv_profileView_body_type.setText(userProfileModel.getBodytype());
            tv_profileView_complexion.setText(userProfileModel.getComplexion());

            AppCompatImageView img_kundli = view.findViewById(R.id.img_profileView_kundli);
            TextView tv_kundli = view.findViewById(R.id.tv_profileView_kundli);

            if (userProfileModel.getKundaliPhoto().equals("") || userProfileModel.getKundaliPhoto().equalsIgnoreCase("")) {

                img_kundli.setVisibility(View.GONE);
                tv_kundli.setVisibility(View.GONE);
            } else {

                img_kundli.setVisibility(View.VISIBLE);
                tv_kundli.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(userProfileModel.getKundaliPhoto())
                        .placeholder(R.drawable.circle_preview)
                        .into(img_kundli);
            }
        }

        tv_memberId.setText(userProfileModel.getUniqueId());

        String profileCreatedBy = userProfileModel.getAccountCreatedBy();
        String profileManagedBy = userProfileModel.getAccountManageBy();

        switch (profileManagedBy) {
            case "Self":

                tv_profile_managed_by.setText("Profile Created by Self");
                break;
            case "Parent":

                tv_profile_managed_by.setText("Profile Created by Parent");
                break;
            case "Sibling":

                tv_profile_managed_by.setText("Profile Created by Sibling");
                break;
        }

        if (profileCreatedBy.equals("C")) {
            tv_profile_created_by.setText(candidate);
            tv_profileView_profile_created_by.setText("Self");
        } else {
            tv_profile_created_by.setVisibility(View.GONE);
            tv_profileView_profile_created_by.setText("Parent");
            tv_profile_managed_by.setText("Profile Created by Parent");
        }

        String username = userProfileModel.getFirstName() + " " + userProfileModel.getLastName();
        tv_profileView_name.setText(username);
        tv_profileView_gender.setText(userProfileModel.getGender());

        tv_profileView_country.setText(userProfileModel.getMemberCountry());
        tv_profileView_state.setText(userProfileModel.getMemberState());
        tv_profileView_city.setText(userProfileModel.getMemberCity());

        if (userProfileModel.getMainProfilePhoto().isEmpty()) {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(profileUrl);
        } else {
            Picasso.get()
                    .load(userProfileModel.getMainProfilePhoto())
                    .placeholder(R.drawable.img_preview)
                    .into(profileUrl);
        }

        profileUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mId = Integer.parseInt(userProfileModel.getMemberId());
                MatrimonyUtils.loadImages(mContext, mId, null);
            }
        });

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+mUserType);
                if(mUserType==1) {

                    mContext.startActivity(new Intent(mContext, ChatActivity.class));

                    String name = userProfileModel.getFirstName() + " " + userProfileModel.getLastName();
                    String imageUrl = userProfileModel.getMainProfilePhoto();

                    SharedPrefsHelper.getInstance(mContext).saveIntVal(SharedPrefsHelper.MESSAGE_RECEIVER_ID,
                            Integer.parseInt(userProfileModel.getMemberId()));
                    SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_NAME,
                            name);
                    SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_IMAGE,
                            imageUrl);
                    SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.RECEIVER_TOKEN_ID,
                            userProfileModel.getDeviceId());
                }else {
                    final Dialog dialogChoice = new Dialog(SearchDisplayActivity.this);
                    dialogChoice.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogChoice.setContentView(R.layout.row_chat_premium_dilaog);
                    dialogChoice.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialogChoice.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialogChoice.show();
                    dialogChoice.setCancelable(true);

                    TextView chat_go_premium = (TextView) dialogChoice.findViewById(R.id.chat_go_premium);
                    chat_go_premium.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogChoice.dismiss();
                            Intent intent=new Intent(SearchDisplayActivity.this,DonationActivity.class);
                            startActivity(intent);

                        }
                    });
                }

            }
        });

        String onlineTime = userProfileModel.getOnlineTime();
        String offlineTime = userProfileModel.getOfflineTime();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        Date onlineDate = null, offlineDate = null;

        try {
            if (!TextUtils.isEmpty(onlineTime) && onlineTime.length() > 5) {
                onlineDate = formatter.parse(onlineTime);
            }
            if (!TextUtils.isEmpty(offlineTime) && offlineTime.length() > 5) {
                offlineDate = formatter.parse(offlineTime);
            }

            assert onlineDate != null;
            assert offlineDate != null;

            if (onlineDate != null && offlineDate != null) {
                if (onlineDate.compareTo(offlineDate) < 0) {
                    userProfileModel.setOnline(false);
                } else {
                    userProfileModel.setOnline(true);
                }
            } else {
                userProfileModel.setOnline(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (userProfileModel.getOnlinestatus().equalsIgnoreCase("online")) {
            tv_online.setVisibility(View.VISIBLE);
            tv_online.setText("Online");
            tv_offline.setVisibility(View.GONE);
        } else {
            tv_offline.setVisibility(View.VISIBLE);
            tv_offline.setText("Offline ");
            tv_online.setVisibility(View.GONE);
        }

        Utilities.showContactNumber(mContext, tvContact, Utilities.getString(userProfileModel.getMobile()));
        Utilities.showEmail(mContext, tvEmail, Utilities.getString(userProfileModel.getEmailId()));
        //            kundli_photo.setVisibility(View.GONE);
        //            view_kundli_view.setVisibility(View.GONE);

    }

  /*  private void showSearchModelData(View view, Object object, int position) {

        final UserProfileModel userProfileModel = (UserProfileModel) object;
        LinearLayout basicDetailsContainer = view.findViewById(R.id.basic_info_container);

        //toolbar.setTitle(userProfileModel.getFirstName() + " " + userProfileModel.getLastName());

        ImageView profileUrl = view.findViewById(R.id.img_profileView_image);
        TextView tv_memberId = view.findViewById(R.id.tv_profileView_memberId);
        TextView tv_otherDetails = view.findViewById(R.id.tv_profile_otherDetails);
        final ImageView imgInvite = view.findViewById(R.id.img_profileView_invite);
        final ImageView imgInvited = view.findViewById(R.id.img_profileView_invited);
        final ImageView imgAccept = view.findViewById(R.id.img_profileView_accept);
        final ImageView imgShortlist = view.findViewById(R.id.img_profileView_shortlist);
        final ImageView imgShortlisted = view.findViewById(R.id.img_profileView_shortlisted);
        final TextView tv_shortlist = view.findViewById(R.id.tv_shortlist);
        final TextView tv_shortlisted = view.findViewById(R.id.tv_shortlisted);
        final TextView tv_invite = view.findViewById(R.id.tv_profileView_invite);
        final TextView tv_invited = view.findViewById(R.id.tv_profileView_invited);
        final TextView tv_accept = view.findViewById(R.id.tv_profileView_accept);
        ImageView imgChat = view.findViewById(R.id.img_profileView_chat);
        TextView tv_profile_created_by = view.findViewById(R.id.tv_profile_created_by);
        TextView tv_profile_managed_by = view.findViewById(R.id.tv_profile_managedBy);
        TextView tv_profileView_introduction = view.findViewById(R.id.tv_profileView_introduction);
        TextView tv_profileView_name = view.findViewById(R.id.tv_profileView_name);
        TextView tv_profileView_gender = view.findViewById(R.id.tv_profileView_gender);
        TextView tv_profileView_age = view.findViewById(R.id.tv_profileView_age);
        TextView tv_profileView_height = view.findViewById(R.id.tv_profileView_height);
        TextView tv_profileView_married_status = view.findViewById(R.id.tv_profileView_married_status);
        TextView tv_profileView_mother_tongue = view.findViewById(R.id.tv_profileView_mother_tongue);
        TextView tv_profileView_physical_status = view.findViewById(R.id.tv_profileView_physical_status);
        TextView tv_profileView_body_type = view.findViewById(R.id.tv_profileView_body_type);
        TextView tv_profileView_complexion = view.findViewById(R.id.tv_profileView_complexion);
        TextView tv_profileView_profile_created_by = view.findViewById(R.id.tv_profileView_profile_created_by);
        TextView tv_profileView_eating_habit = view.findViewById(R.id.tv_profileView_eating_habit);
        TextView tv_profileView_drinking_habit = view.findViewById(R.id.tv_profileView_drinking_habit);
        TextView tv_profileView_smoking_habit = view.findViewById(R.id.tv_profileView_smoking_habit);
        TextView tv_profileView_eduction = view.findViewById(R.id.tv_profileView_eduction);
        TextView tv_profileView_eduction_in = view.findViewById(R.id.tv_profileView_eduction_in);
        TextView tv_profileView_working_with = view.findViewById(R.id.tv_profileView_working_with);
        TextView tv_profileView_working_as = view.findViewById(R.id.tv_profileView_working_as);
        TextView tv_profileView_annual_income = view.findViewById(R.id.tv_profileView_annual_income);
        TextView tv_profileView_country = view.findViewById(R.id.tv_profileView_country);
        TextView tv_profileView_state = view.findViewById(R.id.tv_profileView_state);
        TextView tv_profileView_city = view.findViewById(R.id.tv_profileView_city);
        TextView tv_profileView_family_type = view.findViewById(R.id.tv_profileView_family_type);
        TextView tv_profileView_father_status = view.findViewById(R.id.tv_profileView_father_status);
        TextView tv_profileView_mother_status = view.findViewById(R.id.tv_profileView_mother_status);
        TextView tv_profileView_no_of_brother = view.findViewById(R.id.tv_profileView_no_of_brother);
        TextView tv_profileView_no_of_brother_married = view.findViewById(R.id.tv_profileView_no_of_brother_married);
        TextView tv_profileView_no_of_sister = view.findViewById(R.id.tv_profileView_no_of_sister);
        TextView tv_profileView_no_of_sister_married = view.findViewById(R.id.tv_profileView_no_of_sister_married);
        TextView tv_profileView_birth_date = view.findViewById(R.id.tv_profileView_birth_date);
        TextView tv_profileView_birth_time = view.findViewById(R.id.tv_profileView_birth_time);
        TextView tv_profileView_birth_place = view.findViewById(R.id.tv_profileView_birth_place);
        TextView tv_profileView_gotra = view.findViewById(R.id.tv_profileView_gotra);
        TextView tv_profileView_rashi = view.findViewById(R.id.tv_profileView_rashi);
        TextView tv_profileView_nakshtra = view.findViewById(R.id.tv_profileView_nakshtra);
        TextView tv_profileView_charan = view.findViewById(R.id.tv_profileView_charan);
        TextView tv_profileView_naadi = view.findViewById(R.id.tv_profileView_naadi);
        TextView tv_profileView_gan = view.findViewById(R.id.tv_profileView_gan);
        TextView tv_profileView_manglik = view.findViewById(R.id.tv_profileView_manglik);
        TextView tv_online = view.findViewById(R.id.tv_profileView_online);
        TextView tv_offline = view.findViewById(R.id.tv_profileView_Offline);
        LinearLayout layout_profession = view.findViewById(R.id.layout_profileViewProfession);
        LinearLayout layout_location = view.findViewById(R.id.layout_tv_profileViewLocation);
        LinearLayout layout_familyDetails = view.findViewById(R.id.layout_profileViewFamilyDetails);
        LinearLayout layout_Astro = view.findViewById(R.id.layout_profileViewAstroDetails);
        View view_profession = view.findViewById(R.id.view_profileViewProfession);
        View view_location = view.findViewById(R.id.view_profileViewLocation);
        View view_family = view.findViewById(R.id.view_profileViewFamilyDetails);
        View view_astro = view.findViewById(R.id.view_profileViewAstroDetails);
        TextView tv_professionHeading = view.findViewById(R.id.tv_profileViewProfession);
        TextView tv_locationHeading = view.findViewById(R.id.tv_profileViewLocation);
        TextView tv_familyHeading = view.findViewById(R.id.tv_profileViewFamilyDetails);
        TextView tv_astroHeading = view.findViewById(R.id.tv_profileViewAstroDetails);
        TextView tv_age_heading = view.findViewById(R.id.tv_age);
        TextView tv_height_heading = view.findViewById(R.id.tv_height);
        TextView tv_marriedStatus_heading = view.findViewById(R.id.tv_married_status);
        TextView tv_motherTounge_heading = view.findViewById(R.id.tv_mother_tongue);
        TextView tv_physicalStatus_heading = view.findViewById(R.id.tv_physical);
        TextView tv_bodyType_heading = view.findViewById(R.id.tv_body_type);
        TextView tv_complexion_heading = view.findViewById(R.id.tv_body_complexion);
        TextView tv_eating_heading = view.findViewById(R.id.tv_Eating_Habits);
        TextView tv_drinking_heading = view.findViewById(R.id.tv_drinking_habit);
        TextView tv_smoking_heading = view.findViewById(R.id.tv_smoking_habits);
        TextView tv_age_heading_dot = view.findViewById(R.id.tv_age_dot);
        TextView tv_height_heading_dot = view.findViewById(R.id.tv_height_dot);
        TextView tv_marriedStatus_heading_dot = view.findViewById(R.id.tv_married_statusDot);
        TextView tv_motherTounge_heading_dot = view.findViewById(R.id.tv_mother_tongueDot);
        TextView tv_physicalStatus_heading_dot = view.findViewById(R.id.tv_physicalDot);
        TextView tv_bodyType_heading_dot = view.findViewById(R.id.tv_body_typeDot);
        TextView tv_complexion_heading_dot = view.findViewById(R.id.tv_body_complexionDot);
        TextView tv_eating_heading_dot = view.findViewById(R.id.tv_Eating_HabitsDot);
        TextView tv_drinking_heading_dot = view.findViewById(R.id.tv_drinking_habitDot);
        TextView tv_smoking_heading_dot = view.findViewById(R.id.tv_smoking_habitsDot);
        TextView tvCaste = view.findViewById(R.id.tv_profileView_caste);
        TextView tvSubCaste = view.findViewById(R.id.tv_profileView_subCaste);
        TextView tvContact = view.findViewById(R.id.tv_contact);
        TextView tvEmail = view.findViewById(R.id.tv_email);

        memberId = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.MEMBER_ID);
        final String accountCreatedBy = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.ACCOUNT_CREATED_BY);
        int userId = Integer.parseInt(userProfileModel.getMemberId());
        MatrimonyUtils.sendMemberIdTask(memberId, userId);
        String name = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.MEMBER_NAME);
        String imageUrl = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.PROFILE_URL);

        String message = "" + name + " " + "viewed your profile";

        MatrimonyUtils.sendProfileViewNotification(userProfileModel.getDeviceId(), message, imageUrl,
                String.valueOf(userProfileModel.getMemberId()), String.valueOf(memberId), name);
        tv_invite.setEnabled(true);

        imgInvite.setVisibility(View.GONE);
        imgInvited.setVisibility(View.GONE);
        tv_invite.setVisibility(View.GONE);
        tv_invited.setVisibility(View.GONE);
        imgAccept.setVisibility(View.GONE);
        tv_accept.setVisibility(View.GONE);

        if (accountCreatedBy.equals("P")) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                imgInvite.setImageAlpha(64);
//            }
        } else if (accountCreatedBy.equals("C")) {

            if (Utilities.getString(userProfileModel.getInvited()).equals(String.valueOf(userProfileModel.getMemberId()))) {
                imgAccept.setVisibility(View.VISIBLE);
                tv_accept.setVisibility(View.VISIBLE);
                imgInvited.setVisibility(View.GONE);
                imgInvite.setVisibility(View.GONE);
                tv_invite.setVisibility(View.GONE);
                tv_invited.setVisibility(View.GONE);
            }
        }

        if (TextUtils.isEmpty(userProfileModel.getInvited()) || userProfileModel.getInvited().contains("0")) {
            imgInvite.setVisibility(View.VISIBLE);
            imgInvited.setVisibility(View.GONE);
            tv_invite.setVisibility(View.VISIBLE);
            tv_invited.setVisibility(View.GONE);
            imgAccept.setVisibility(View.GONE);
        } else {
            imgInvited.setVisibility(View.VISIBLE);
            imgInvite.setVisibility(View.GONE);
            tv_invite.setVisibility(View.GONE);
            tv_invited.setVisibility(View.VISIBLE);
            imgAccept.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(userProfileModel.getShorted()) || userProfileModel.getShorted().contains("0")) {
            imgShortlist.setVisibility(View.VISIBLE);
            imgShortlisted.setVisibility(View.GONE);
            tv_shortlist.setVisibility(View.VISIBLE);
            tv_shortlisted.setVisibility(View.GONE);

        } else {
            imgShortlisted.setVisibility(View.VISIBLE);
            tv_shortlist.setVisibility(View.GONE);
            tv_shortlist.setVisibility(View.GONE);
            tv_shortlisted.setVisibility(View.VISIBLE);
        }

        imgShortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int receiverId = Integer.parseInt(userProfileModel.getMemberId());
                MatrimonyUtils.addShortlistTask(mContext, memberId, receiverId, "1");

                imgShortlisted.setVisibility(View.VISIBLE);
                imgShortlist.setVisibility(View.GONE);

                tv_shortlist.setVisibility(View.GONE);
                tv_shortlisted.setVisibility(View.VISIBLE);
            }
        });

        imgShortlisted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int receiverId = Integer.parseInt(userProfileModel.getMemberId());
                MatrimonyUtils.addShortlistTask(mContext, memberId, receiverId, "0");

                imgShortlisted.setVisibility(View.GONE);
                imgShortlist.setVisibility(View.VISIBLE);

                tv_shortlist.setVisibility(View.VISIBLE);
                tv_shortlisted.setVisibility(View.GONE);
            }
        });

        imgInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountCreatedBy.equals("P")) {
                    Toast.makeText(mContext, "You cannot invite Candidate profile ", Toast.LENGTH_LONG).show();
                } else {

                    int senderId = Integer.parseInt(userProfileModel.getMemberId());
                    MatrimonyUtils.inviteTask(mContext, memberId, senderId, "1");

                    String name = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.MEMBER_NAME);
                    String imageUrl = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.PROFILE_URL);

                    String message = "Invitation Received from " + name;

                    MatrimonyUtils.sendInvitationNotification(userProfileModel.getDeviceId(), message,
                            imageUrl, String.valueOf(userProfileModel.getMemberId()), String.valueOf(memberId),
                            name);

                    imgInvited.setVisibility(View.VISIBLE);
                    imgInvite.setVisibility(View.GONE);

                    tv_invite.setVisibility(View.GONE);
                    tv_invited.setVisibility(View.VISIBLE);
                }
            }
        });

        imgInvited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int senderId = Integer.parseInt(userProfileModel.getMemberId());
                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "0");

                imgInvite.setVisibility(View.VISIBLE);
                imgInvited.setVisibility(View.GONE);

                tv_invite.setVisibility(View.VISIBLE);
                tv_invited.setVisibility(View.GONE);
            }
        });

        if (userProfileModel.getAccountCreatedBy().equals("P")) {

            tv_age_heading.setVisibility(View.GONE);
            tv_age_heading_dot.setVisibility(View.GONE);
            tv_height_heading.setVisibility(View.GONE);
            tv_height_heading_dot.setVisibility(View.GONE);
            tv_marriedStatus_heading.setVisibility(View.GONE);
            tv_marriedStatus_heading_dot.setVisibility(View.GONE);
            tv_motherTounge_heading.setVisibility(View.GONE);
            tv_motherTounge_heading_dot.setVisibility(View.GONE);
            tv_physicalStatus_heading.setVisibility(View.GONE);
            tv_physicalStatus_heading_dot.setVisibility(View.GONE);
            tv_bodyType_heading.setVisibility(View.GONE);
            tv_bodyType_heading_dot.setVisibility(View.GONE);
            tv_complexion_heading.setVisibility(View.GONE);
            tv_complexion_heading_dot.setVisibility(View.GONE);
            tv_eating_heading.setVisibility(View.GONE);
            tv_eating_heading_dot.setVisibility(View.GONE);
            tv_drinking_heading.setVisibility(View.GONE);
            tv_drinking_heading_dot.setVisibility(View.GONE);
            tv_smoking_heading.setVisibility(View.GONE);
            tv_smoking_heading_dot.setVisibility(View.GONE);
            tv_profileView_introduction.setVisibility(View.GONE);
            tv_profileView_age.setVisibility(View.GONE);
            tv_profileView_height.setVisibility(View.GONE);
            tv_profileView_married_status.setVisibility(View.GONE);
            tv_profileView_mother_tongue.setVisibility(View.GONE);
            tv_profileView_physical_status.setVisibility(View.GONE);
            tv_profileView_body_type.setVisibility(View.GONE);
            tv_profileView_complexion.setVisibility(View.GONE);
            tv_profileView_drinking_habit.setVisibility(View.GONE);
            tv_profileView_smoking_habit.setVisibility(View.GONE);
            tv_profileView_eating_habit.setVisibility(View.GONE);

            layout_profession.setVisibility(View.GONE);
            layout_familyDetails.setVisibility(View.GONE);
            layout_location.setVisibility(View.GONE);
            layout_Astro.setVisibility(View.GONE);
            view_profession.setVisibility(View.GONE);
            view_family.setVisibility(View.GONE);
            view_astro.setVisibility(View.GONE);
            view_location.setVisibility(View.GONE);
            tv_professionHeading.setVisibility(View.GONE);
            tv_familyHeading.setVisibility(View.GONE);
            tv_locationHeading.setVisibility(View.GONE);
            tv_astroHeading.setVisibility(View.GONE);

            String age_height_other = userProfileModel.getMemberCity() +
                    ", " + userProfileModel.getMemberState() + ", " + userProfileModel.getMemberCountry();
            tv_otherDetails.setText(age_height_other);

        } else if (userProfileModel.getAccountCreatedBy().equals("C")) {

            try {
                userProfileModel.setAge(MatrimonyUtils.getAge(userProfileModel.getDOB()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            String age_height_other = userProfileModel.getAge()
                    + ", " + userProfileModel.getMemberHeight()
                    + ", " + userProfileModel.getMemberCity()
                    + ", " + userProfileModel.getMemberState()
                    + ", " + userProfileModel.getMemberCountry();

            tv_otherDetails.setText(age_height_other);

            tv_profileView_age.setText(userProfileModel.getAge());
            tv_profileView_height.setText(userProfileModel.getMemberHeight());
            tv_profileView_married_status.setText(userProfileModel.getMarriedStatus());
            tv_profileView_mother_tongue.setText(userProfileModel.getMotherTongue());

            tv_profileView_birth_date.setText(userProfileModel.getDOB());
            tv_profileView_introduction.setText(userProfileModel.getSelfIntroduction());
            tv_profileView_eduction.setText(userProfileModel.getMemberEducationName());
            tv_profileView_eduction_in.setText(userProfileModel.getEducationInName());
            tv_profileView_working_with.setText(userProfileModel.getEducationWithName());
            tv_profileView_working_as.setText(userProfileModel.getMemberOccupation());
            tv_profileView_annual_income.setText(userProfileModel.getMemberInCome());
            tv_profileView_family_type.setText(userProfileModel.getFamilyType());
            tv_profileView_father_status.setText(userProfileModel.getFatherstatus());
            tv_profileView_mother_status.setText(userProfileModel.getMotherstatus());
            tv_profileView_no_of_brother.setText(userProfileModel.getNoOfBorther());
            tv_profileView_no_of_brother_married.setText(userProfileModel.getNoOfBortherMarried());
            tv_profileView_no_of_sister.setText(userProfileModel.getNoOfSister());
            tv_profileView_no_of_sister_married.setText(userProfileModel.getNoOfSisterMarried());
            tv_profileView_birth_place.setText(userProfileModel.getBirthPlace());
            tv_profileView_birth_time.setText(userProfileModel.getBirthTime());
            tv_profileView_gotra.setText(userProfileModel.getGotra());
            tv_profileView_rashi.setText(userProfileModel.getRashi());
            tv_profileView_nakshtra.setText(userProfileModel.getNakshtra());
            tv_profileView_charan.setText(userProfileModel.getCharan());
            tv_profileView_naadi.setText(userProfileModel.getNaddi());
            tv_profileView_gan.setText(userProfileModel.getGan());
            tv_profileView_physical_status.setText(userProfileModel.getPhysicalchallenge());
            tv_profileView_drinking_habit.setText(userProfileModel.getDrinkhabit());
            tv_profileView_smoking_habit.setText(userProfileModel.getSmokehabit());
            tv_profileView_eating_habit.setText(userProfileModel.getFoodtype());
            tv_profileView_manglik.setText(userProfileModel.getManglik());
            tv_profileView_body_type.setText(userProfileModel.getBodytype());
            tv_profileView_complexion.setText(userProfileModel.getComplexion());

            AppCompatImageView img_kundli = view.findViewById(R.id.img_profileView_kundli);
            TextView tv_kundli = view.findViewById(R.id.tv_profileView_kundli);


            img_kundli.setVisibility(View.GONE);
            tv_kundli.setVisibility(View.GONE);
            if (userProfileModel.getKundaliPhoto().equals("") || userProfileModel.getKundaliPhoto().equalsIgnoreCase("")) {

                img_kundli.setVisibility(View.GONE);
                tv_kundli.setVisibility(View.GONE);
            } else {

                img_kundli.setVisibility(View.VISIBLE);
                tv_kundli.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(userProfileModel.getKundaliPhoto())
                        .placeholder(R.drawable.circle_preview)
                        .into(img_kundli);

                img_kundli.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageDisplayFragment imageDisplayFragment = new ImageDisplayFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("kundali", userProfileModel.getKundaliPhoto());
                        bundle.putString("memberName", userProfileModel.getFirstName() + "_" + userProfileModel.getLastName());
                        imageDisplayFragment.setArguments(bundle);
                        imageDisplayFragment.show(getSupportFragmentManager(), "Kundali");
                        int mId = Integer.parseInt(userProfileModel.getMemberId());
                        MatrimonyUtils.loadImages(mContext, mId, null);
                    }
                });
            }
        }

        tv_memberId.setText(userProfileModel.getUniqueId());

        String profileCreatedBy = userProfileModel.getAccountCreatedBy();
        String profileManagedBy = userProfileModel.getAccountManageBy();

        switch (profileManagedBy) {
            case "Self":

                tv_profile_managed_by.setText("Profile Created by Self");
                break;
            case "Parent":

                tv_profile_managed_by.setText("Profile Created by Parent");
                break;
            case "Sibling":

                tv_profile_managed_by.setText("Profile Created by Sibling");
                break;
        }

        if (profileCreatedBy.equals("C")) {
            tv_profile_created_by.setText(candidate);
            tv_profileView_profile_created_by.setText("Self");
        } else {
            tv_profile_created_by.setVisibility(View.GONE);
            tv_profileView_profile_created_by.setText("Parent");
            tv_profile_managed_by.setText("Profile Created by Parent");
        }

        String username = userProfileModel.getFirstName() + " " + userProfileModel.getLastName();
        tv_profileView_name.setText(username);
        tv_profileView_gender.setText(userProfileModel.getGender());

        tv_profileView_country.setText(userProfileModel.getMemberCountry());
        tv_profileView_state.setText(userProfileModel.getMemberState());
        tv_profileView_city.setText(userProfileModel.getMemberCity());

        if (userProfileModel.getMainProfilePhoto().isEmpty()) {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(profileUrl);
        } else {
            Picasso.get()
                    .load(userProfileModel.getMainProfilePhoto())
                    .placeholder(R.drawable.img_preview)
                    .into(profileUrl);
        }

        profileUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mId = Integer.parseInt(userProfileModel.getMemberId());
                MatrimonyUtils.loadImages(mContext, mId, null);
            }
        });


        LinearLayout chatContainer = view.findViewById(R.id.chatContainer);
        chatContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ChatActivity.class));

                String name = userProfileModel.getFirstName() + " " + userProfileModel.getLastName();
                String imageUrl = userProfileModel.getMainProfilePhoto();

                SharedPrefsHelper.getInstance(mContext).saveIntVal(SharedPrefsHelper.MESSAGE_RECEIVER_ID,
                        Integer.parseInt(userProfileModel.getMemberId()));
                SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_NAME,
                        name);
                SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_IMAGE,
                        imageUrl);
                SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.RECEIVER_TOKEN_ID,
                        userProfileModel.getDeviceId());
            }
        });



                   *//* imgChat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mContext.startActivity(new Intent(mContext, ChatActivity.class));

                            String name = userProfileModel.getFirstName() + " " + userProfileModel.getLastName();
                            String imageUrl = userProfileModel.getMainProfilePhoto();

                            SharedPrefsHelper.getInstance(mContext).saveIntVal(SharedPrefsHelper.MESSAGE_RECEIVER_ID,
                                    Integer.parseInt(userProfileModel.getMemberId()));
                            SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_NAME,
                                    name);
                            SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_IMAGE,
                                    imageUrl);
                            SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.RECEIVER_TOKEN_ID,
                                    userProfileModel.getDeviceId());
                        }
                    });*//*

        String onlineTime = userProfileModel.getOnlineTime();
        String offlineTime = userProfileModel.getOffline_Time();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        Date onlineDate = null, offlineDate = null;

        try {
            if (!TextUtils.isEmpty(onlineTime) && onlineTime.length() > 5) {
                onlineDate = formatter.parse(onlineTime);
            }
            if (!TextUtils.isEmpty(offlineTime) && offlineTime.length() > 5) {
                offlineDate = formatter.parse(offlineTime);
            }

            assert onlineDate != null;
            assert offlineDate != null;

            if (onlineDate != null && offlineDate != null) {
                if (onlineDate.compareTo(offlineDate) < 0) {
                    userProfileModel.setOnline(false);
                } else {
                    userProfileModel.setOnline(true);
                }
            } else {
                userProfileModel.setOnline(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (userProfileModel.getOnlinestatus().equalsIgnoreCase("online")) {
            tv_online.setVisibility(View.VISIBLE);
            tv_online.setText("Online");
            tv_offline.setVisibility(View.GONE);
        } else {
            tv_offline.setVisibility(View.VISIBLE);
            tv_offline.setText("Offline ");
            tv_online.setVisibility(View.GONE);
        }

        Utilities.showContactNumber(mContext, tvContact, Utilities.getString(userProfileModel.getMobile()));
        Utilities.showEmail(mContext, tvEmail, Utilities.getString(userProfileModel.getEmailid()));
        //            kundli_photo.setVisibility(View.GONE);
        //            view_kundli_view.setVisibility(View.GONE);

        showBasicDetails(userProfileModel, basicDetailsContainer);
    }*/

    private void showData(View view, Object object) {
        final UserProfileModel userProfileModel = (UserProfileModel) object;

        LinearLayout basicDetailsContainer = view.findViewById(R.id.basic_info_container);

        profileUrl = (ImageView) view.findViewById(R.id.img_profileView_image);
        tv_memberId = (TextView) view.findViewById(R.id.tv_profileView_memberId);

        TextView tv_online = view.findViewById(R.id.tv_profileView_online);
        TextView tv_offline = view.findViewById(R.id.tv_profileView_Offline);

//        view_kundli_view = (View) findViewById(R.id.view_kundli_view);
        tv_otherDetails = (TextView) view.findViewById(R.id.tv_profile_otherDetails);
        imgInvite = (ImageView) view.findViewById(R.id.img_profileView_invite);
        kundli_photo = (ImageView) view.findViewById(R.id.img_profileView_kundli);

        imgInvited = (ImageView) view.findViewById(R.id.img_profileView_invited);
        imgChat = (ImageView) view.findViewById(R.id.img_profileView_chat);

        if (Utilities.isEmpty(userProfileModel.getKundaliPhoto())) {
            kundli_photo.setVisibility(View.GONE);
        } else {
            kundli_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageDisplayFragment imageDisplayFragment = new ImageDisplayFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("kundali", userProfileModel.getKundaliPhoto());
                    bundle.putString("memberName", userProfileModel.getFirstName() + "_" + userProfileModel.getLastName());
                    imageDisplayFragment.setArguments(bundle);
                    imageDisplayFragment.show(getSupportFragmentManager(), "Kundali");
                }
            });
        }

        LinearLayout chatcontainer = (LinearLayout) view.findViewById(R.id.chatContainer);
        chatcontainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mUserType==1) {
                    startActivity(new Intent(mContext, ChatActivity.class));

                    String name = userProfileModel.getFirstName() + " " + userProfileModel.getLastName();
                    String imageUrl = userProfileModel.getMainProfilePhoto();

                    mPreference.edit().putInt("messageReceiverId", Integer.parseInt(userProfileModel.getMemberId())).apply();
                    mPreference.edit().putString("messageReceiverName", name).apply();
                    mPreference.edit().putString("messageReceiverImage", imageUrl).apply();
                    mPreference.edit().putString("receiverTokenId", userProfileModel.getDeviceId()).apply();
                }else {
                    displayPreimiumalert();
                }

            }
        });
                /*imgChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });*/


        imgShortlist = (ImageView) view.findViewById(R.id.img_profileView_shortlist);
        imgShortlisted = (ImageView) view.findViewById(R.id.img_profileView_shortlisted);
        tv_memberName = (TextView) view.findViewById(R.id.tv_profileView_name);
        tv_profile_created_by = (TextView) view.findViewById(R.id.tv_profile_created_by);
        tv_profileView_kundli = (TextView) view.findViewById(R.id.tv_profileView_kundli);
        tv_profileView_introduction = (TextView) view.findViewById(R.id.tv_profileView_introduction);
        tv_profileView_name = (TextView) view.findViewById(R.id.tv_profileView_name);
        tv_profileView_gender = (TextView) view.findViewById(R.id.tv_profileView_gender);
        tv_profileView_age = (TextView) view.findViewById(R.id.tv_profileView_age);
        tv_profileView_height = (TextView) view.findViewById(R.id.tv_profileView_height);
        tv_profileView_married_status = (TextView) view.findViewById(R.id.tv_profileView_married_status);
        tv_profileView_mother_tongue = (TextView) view.findViewById(R.id.tv_profileView_mother_tongue);
        tv_profileView_physical_status = (TextView) view.findViewById(R.id.tv_profileView_physical_status);
        tv_profileView_body_type = (TextView) view.findViewById(R.id.tv_profileView_body_type);
        // TextView tv_profileView_complexion = findViewById(R.id.tv_profileView_complexion);
        tv_profileView_profile_created_by = (TextView) view.findViewById(R.id.tv_profileView_profile_created_by);
        tv_profileView_eating_habit = (TextView) view.findViewById(R.id.tv_profileView_eating_habit);
        tv_profileView_drinking_habit = (TextView) view.findViewById(R.id.tv_profileView_drinking_habit);
        tv_profileView_smoking_habit = (TextView) view.findViewById(R.id.tv_profileView_smoking_habit);
        tv_profileView_eduction = (TextView) view.findViewById(R.id.tv_profileView_eduction);
        tv_profileView_eduction_in = (TextView) view.findViewById(R.id.tv_profileView_eduction_in);
        tv_profileView_working_with = (TextView) view.findViewById(R.id.tv_profileView_working_with);
        tv_profileView_working_as = (TextView) view.findViewById(R.id.tv_profileView_working_as);
        tv_profileView_annual_income = (TextView) view.findViewById(R.id.tv_profileView_annual_income);
        tv_profileView_country = (TextView) view.findViewById(R.id.tv_profileView_country);
        tv_profileView_state = (TextView) view.findViewById(R.id.tv_profileView_state);
        tv_profileView_city = (TextView) view.findViewById(R.id.tv_profileView_city);
        tv_profileView_family_type = (TextView) view.findViewById(R.id.tv_profileView_family_type);
        tv_profileView_father_status = (TextView) view.findViewById(R.id.tv_profileView_father_status);
        tv_profileView_mother_status = (TextView) view.findViewById(R.id.tv_profileView_mother_status);
        tv_profileView_no_of_brother = (TextView) view.findViewById(R.id.tv_profileView_no_of_brother);
        tv_profileView_no_of_brother_married = (TextView) view.findViewById(R.id.tv_profileView_no_of_brother_married);
        tv_profileView_no_of_sister = (TextView) view.findViewById(R.id.tv_profileView_no_of_sister);
        tv_profileView_no_of_sister_married = (TextView) view.findViewById(R.id.tv_profileView_no_of_sister_married);
        tv_profileView_birth_date = (TextView) view.findViewById(R.id.tv_profileView_birth_date);
        tv_profileView_birth_time = (TextView) view.findViewById(R.id.tv_profileView_birth_time);
        tv_profileView_birth_place = (TextView) view.findViewById(R.id.tv_profileView_birth_place);
        tv_profileView_gotra = (TextView) view.findViewById(R.id.tv_profileView_gotra);
        tv_profileView_rashi = (TextView) view.findViewById(R.id.tv_profileView_rashi);
        tv_profileView_nakshtra = (TextView) view.findViewById(R.id.tv_profileView_nakshtra);
        tv_profileView_charan = (TextView) view.findViewById(R.id.tv_profileView_charan);
        tv_profileView_naadi = (TextView) view.findViewById(R.id.tv_profileView_naadi);
        tv_profileView_gan = (TextView) view.findViewById(R.id.tv_profileView_gan);
        tvCaste = (TextView) view.findViewById(R.id.tv_profileView_caste);
        tvSubCaste = (TextView) view.findViewById(R.id.tv_profileView_subCaste);
        tv_shortlist = view.findViewById(R.id.tv_shortlist);
        tv_shortlisted = view.findViewById(R.id.tv_shortlisted);
        tv_invite = view.findViewById(R.id.tv_profileView_invite);
        tv_invited = view.findViewById(R.id.tv_profileView_invited);

        ImageView download=view.findViewById(R.id.download);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(SearchDisplayActivity.this, "2720", Toast.LENGTH_SHORT).show();
                fileName = userProfileModel.getFirstName() + "_" + userProfileModel.getLastName();
                getPdfUrlPresenter.getProfilePdf(userProfileModel.getMemberId());
                //Toast.makeText(SearchDisplayActivity.this, "t", Toast.LENGTH_SHORT).show();
            }
        });


        profileUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatrimonyUtils.loadImages(mContext, Integer.parseInt(userProfileModel.getMemberId()),
                        null);
            }
        });

        imgInvite.setEnabled(true);
        if (accountCreatedBy.equals("P")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                imgInvite.setImageAlpha(64);
            }
        } else if (accountCreatedBy.equals("C")) {

            if (TextUtils.isEmpty(userProfileModel.getInvited()) || userProfileModel.getInvited().contains("0")) {

                imgInvite.setVisibility(View.VISIBLE);
                imgInvited.setVisibility(View.GONE);
                tv_invite.setVisibility(View.VISIBLE);
                tv_invited.setVisibility(View.GONE);

            } else {

                imgInvited.setVisibility(View.VISIBLE);
                imgInvite.setVisibility(View.GONE);
                tv_invite.setVisibility(View.GONE);
                tv_invited.setVisibility(View.VISIBLE);

            }
        }

        if(userProfileModel.getOnlinestatus().equalsIgnoreCase("online")){
            tv_online.setVisibility(View.VISIBLE);
            tv_online.setText("Online");
            tv_offline.setVisibility(View.GONE);
        }else {
            tv_offline.setVisibility(View.VISIBLE);
            tv_offline.setText("Offline");
            tv_online.setVisibility(View.GONE);
        }

//        tvCaste.setText("" + userProfileModel.getCaste());
//        tvSubCaste.setText("" + userProfileModel.getSubcaste());

        tv_profileView_name.setText(userProfileModel.getFirstName() + " " + userProfileModel.getLastName());
        tv_profileView_introduction.setText(userProfileModel.getSelfIntroduction());
        tv_profileView_physical_status.setText(userProfileModel.getPhysicalchallenge());
        tv_profileView_eating_habit.setText(userProfileModel.getFoodtype());
        tv_profileView_smoking_habit.setText(userProfileModel.getSmokehabit());
        tv_profileView_drinking_habit.setText(userProfileModel.getDrinkhabit());
        tv_profileView_eduction.setText(userProfileModel.getMemberEducationName());
        tv_profileView_eduction_in.setText(userProfileModel.getEducationInName());
        tv_profileView_working_with.setText(userProfileModel.getEducationWithName());
        tv_profileView_working_as.setText(userProfileModel.getEducationWithName());


        if (TextUtils.isEmpty(userProfileModel.getShorted()) || userProfileModel.getShorted().contains("0")) {

            imgShortlist.setVisibility(View.VISIBLE);
            imgShortlisted.setVisibility(View.GONE);
            tv_shortlist.setVisibility(View.VISIBLE);
            tv_shortlisted.setVisibility(View.GONE);

        } else {

            imgShortlisted.setVisibility(View.VISIBLE);
            tv_shortlist.setVisibility(View.GONE);
            tv_shortlist.setVisibility(View.GONE);
            tv_shortlisted.setVisibility(View.VISIBLE);

        }

        imgShortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int receiverId = Integer.parseInt(userProfileModel.getMemberId());

                shortListPresenter.performShortlist("" + memberId, "" + receiverId, "1");

                imgShortlisted.setVisibility(imgShortlisted.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                imgShortlist.setVisibility(imgShortlist.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

                tv_shortlist.setVisibility(tv_shortlist.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                tv_shortlisted.setVisibility(tv_shortlisted.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

            }
        });

        imgShortlisted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int receiverId = Integer.parseInt(userProfileModel.getMemberId());

                shortListPresenter.performShortlist("" + memberId, "" + receiverId, "0");

                imgShortlisted.setVisibility(imgShortlisted.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                imgShortlist.setVisibility(imgShortlist.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

                tv_shortlist.setVisibility(tv_shortlist.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                tv_shortlisted.setVisibility(tv_shortlisted.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });


        imgInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (accountCreatedBy.equals("P")) {

                    Toast.makeText(mContext, "You can not invite Candidate profile ", Toast.LENGTH_LONG).show();

                } else {
                    int senderId = Integer.parseInt(userProfileModel.getMemberId());
                    MatrimonyUtils.inviteTask(mContext, memberId, senderId, "1");

                    imgInvited.setVisibility(View.VISIBLE);
                    imgInvite.setVisibility(View.GONE);

                    tv_invite.setVisibility(View.GONE);
                    tv_invited.setVisibility(View.VISIBLE);
                }
            }
        });

        imgInvited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
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
                    return;
                }

                int senderId = Integer.parseInt(userProfileModel.getMemberId());
                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "0");

                imgInvite.setVisibility(View.VISIBLE);
                imgInvited.setVisibility(View.GONE);

                tv_invite.setVisibility(View.VISIBLE);
                tv_invited.setVisibility(View.GONE);
            }
        });

        try {
            userProfileModel.setAge(MatrimonyUtils.getAge(userProfileModel.getDOB()));
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        tv_memberId.setText(userProfileModel.getUniqueId());
        String age_height_other = userProfileModel.getAge() + ", " + userProfileModel.getMemberHeight() + ", " + userProfileModel.getMemberCity() +
                ", " + userProfileModel.getMemberState() + ", " + userProfileModel.getMemberCountry();
        tv_otherDetails.setText(age_height_other);

        String profileCreatedBy = userProfileModel.getAccountCreatedBy();

        if (profileCreatedBy.equals("C")) {
            tv_profile_created_by.setText(candidate);
            tv_profileView_profile_created_by.setText("Self");
        } else {
            tv_profile_created_by.setText(parents);
            tv_profileView_profile_created_by.setText("Parent");
        }

        username = userProfileModel.getFirstName() + " " + userProfileModel.getLastName();
        tv_profileView_name.setText(username);
//        mCoordinatorLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorBlack));
//        mCoordinatorLayout.setTitle(username);
        tv_profileView_gender.setText(userProfileModel.getGender());
        tv_profileView_age.setText(userProfileModel.getAge());
        tv_profileView_height.setText(userProfileModel.getMemberHeight());
        tv_profileView_married_status.setText(userProfileModel.getMarriedStatus());
        tv_profileView_mother_tongue.setText(userProfileModel.getMotherTongue());
        tv_profileView_country.setText(userProfileModel.getMemberCountry());
        tv_profileView_state.setText(userProfileModel.getMemberState());
        tv_profileView_city.setText(userProfileModel.getMemberCity());
        tv_profileView_birth_date.setText(userProfileModel.getDOB());
        tv_profileView_annual_income.setText(userProfileModel.getMemberInCome());
        tv_profileView_family_type.setText(userProfileModel.getFamilyType());
        tv_profileView_father_status.setText(userProfileModel.getFatherstatus());
        tv_profileView_mother_status.setText(userProfileModel.getMotherstatus());
        tv_profileView_no_of_brother.setText(userProfileModel.getNoOfBorther());
        tv_profileView_no_of_brother_married.setText(userProfileModel.getNoOfBortherMarried());
        tv_profileView_no_of_sister.setText(userProfileModel.getNoOfSister());
        tv_profileView_no_of_sister_married.setText(userProfileModel.getNoOfBortherMarried());
        tv_profileView_birth_time.setText(userProfileModel.getBirthTime());
        tv_profileView_birth_place.setText(userProfileModel.getBirthPlace());
        tv_profileView_gotra.setText(userProfileModel.getGotra());
        tv_profileView_rashi.setText(userProfileModel.getRashi());
        tv_profileView_nakshtra.setText(userProfileModel.getNakshtra());
        tv_profileView_charan.setText(userProfileModel.getCharan());
        tv_profileView_naadi.setText(userProfileModel.getNaddi());
        tv_profileView_gan.setText(userProfileModel.getGan());
        tv_profileView_body_type.setText(userProfileModel.getBodytype());

        if (userProfileModel.getMainProfilePhoto().isEmpty()) {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(profileUrl);
        } else {

            Picasso.get()
                    .load(userProfileModel.getMainProfilePhoto())
                    .placeholder(R.drawable.img_preview)
                    .into(profileUrl);
        }

        if (userProfileModel.getKundaliPhoto().length() > 10) {

            tv_profileView_kundli.setVisibility(View.VISIBLE);
            kundli_photo.setVisibility(View.VISIBLE);
//            view_kundli_view.setVisibility(View.VISIBLE);

            Picasso.get()
                    .load(userProfileModel.getKundaliPhoto())
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(kundli_photo);
        } else {

            tv_profileView_kundli.setVisibility(View.GONE);
            kundli_photo.setVisibility(View.GONE);
//            view_kundli_view.setVisibility(View.GONE);
        }

        showBasicDetails(userProfileModel, basicDetailsContainer);
    }

    @Override
    public void showPdfUrl(String url) {

        downloadProfilePresenter.downloadFile(fileName,url);
        //downloadProfilePresenter.downloadFile(currentProfileSelected, url);
    }

    @Override
    public void showFileDownloaded(String message) {

    }

    public void displayPreimiumalert(){
        final Dialog dialogChoice = new Dialog(SearchDisplayActivity.this);
        dialogChoice.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogChoice.setContentView(R.layout.row_chat_premium_dilaog);
        dialogChoice.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogChoice.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogChoice.show();
        dialogChoice.setCancelable(true);

        TextView chat_go_premium = (TextView) dialogChoice.findViewById(R.id.chat_go_premium);
        chat_go_premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogChoice.dismiss();
                Intent intent=new Intent(SearchDisplayActivity.this,DonationActivity.class);
                startActivity(intent);

            }
        });
    }
}