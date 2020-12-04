package in.co.vsys.myssksamaj.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CustomPagerAdapter;
import in.co.vsys.myssksamaj.contracts.DownloadProfileContract;
import in.co.vsys.myssksamaj.contracts.GetPdfUrlContract;
import in.co.vsys.myssksamaj.contracts.RecentlyJointContractPaging;
import in.co.vsys.myssksamaj.contracts.UserContract;
import in.co.vsys.myssksamaj.contracts.UserMatchContract;
import in.co.vsys.myssksamaj.fragments.ImageDisplayFragment;
import in.co.vsys.myssksamaj.fragments.ShowContactFragment;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.interfaces.InitiatePaymentListener;
import in.co.vsys.myssksamaj.interfaces.ViewClickListener;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.data_models.UserDetailsModel;
import in.co.vsys.myssksamaj.model.data_models.UserMatchModel;
import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.model.responses.UserDetailsResponse;
import in.co.vsys.myssksamaj.presenters.DownloadProfilePresenter;
import in.co.vsys.myssksamaj.presenters.GetPdfUrlPresenter;
import in.co.vsys.myssksamaj.presenters.RecentlyJointPagingPresenter;
import in.co.vsys.myssksamaj.presenters.UserMatchPresenter;
import in.co.vsys.myssksamaj.presenters.UserPresenter;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

import static in.co.vsys.myssksamaj.contracts.UserContract.*;

public class MatrimonyProfileViewActivity1 extends AppCompatActivity implements DownloadProfileContract.DownloadProfileView,
        GetPdfUrlContract.GetPdfUrlView, InitiatePaymentListener
//        implements TransactionDataContract.TransactionView
{

    private Context mContext;
    private String candidate = "In my own words";
    private static final String TAG = MatrimonyProfileViewActivity1.class.getSimpleName();
    private int memberId, userMemeberId;
    private SharedPreferences mPreference;
    private ProgressDialog progressDialog;
    private ViewPager viewPager;
    private int position1;
    private Toolbar mToolbar;
    private int viewPosition, homeActivityFlag, listFlagActivity;
    public static String userName = "";
    private ProgressDialog mDialog;
    private CustomPagerAdapter customPagerAdapter;
    int pageCount;
    private String mTransactionId = "", mPackageId = "1";
    private boolean isSwiped = false;
    private List<UserMatchModel> mUserProfileModelList = new ArrayList<>();
    private DownloadProfileContract.DownloadProfileOps downloadProfilePresenter;
    private GetPdfUrlContract.GetPdfUrlOps getPdfUrlPresenter;
    private UserMatchModel currentProfileSelected;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrimony_profile_view);
        mContext = this;

        viewPager = findViewById(R.id.viewpager);
        mToolbar = findViewById(R.id.toolbar_profileView);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent p = getIntent();

        if (p != null) {
            if (p.getExtras().containsKey("position")) {
                position1 = p.getExtras().getInt("position", 0);
            }
            if (p.getExtras().containsKey("pageCount")) {
                pageCount = p.getExtras().getInt("pageCount", 0);
            }
            userMemeberId = p.getExtras().getInt("memberId", 0);

            homeActivityFlag = p.getExtras().getInt("flagHomeActivity", 0);

        }

        mPreference = PreferenceManager.getDefaultSharedPreferences(this);
        memberId = mPreference.getInt("memberId", 0);

        Log.e("intent", position1 + "::" + pageCount);


        if (position1 == 0) {
            mToolbar.setTitle("" + userName);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadRecentlyData();
            }
        }).start();

        getPdfUrlPresenter = new GetPdfUrlPresenter(this);
        downloadProfilePresenter = new DownloadProfilePresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.nav_share:
                Toast.makeText(this, "Share Click", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadRecentlyData() {
        UserMatchContract.UserMatchOps userMatchOps = new UserMatchPresenter(new UserMatchContract.UserMatchView() {
            @Override
            public void showUserMatch(List<UserMatchModel> data) {
                mUserProfileModelList.addAll(data);
                Log.e("size", "" + mUserProfileModelList.size());
                showRecentlyJointItemModel();
            }

            @Override
            public void showLoading() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mDialog == null) {
                            mDialog = new ProgressDialog(mContext);
                            mDialog.setMessage(getString(R.string.please_wait));
                        }
                        mDialog.show();
                    }
                });

            }

            @Override
            public void hideLoading() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                    }
                });

            }

            @Override
            public void showError(final String message) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        userMatchOps.getUserMatch(String.valueOf(memberId), String.valueOf(pageCount));
        Log.d("mytag", "loadRecentlymatch: " + memberId + " " + pageCount);




       /* RecentlyJointContractPaging.RecentlyJointPagingOps recentlyJointPagingPresenter =
                new RecentlyJointPagingPresenter(new RecentlyJointContractPaging.RecentlyJointPagingView() {
                    @Override
                    public void showRecentlyJoint(List<UserProfileModel> userProfileModels) {

                        mUserProfileModelList.addAll(userProfileModels);
                        Log.e("size", "" + mUserProfileModelList.size());
                        showRecentlyJointItemModel();
                    }

                    @Override
                    public void showLoading() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mDialog == null) {
                                    mDialog = new ProgressDialog(mContext);
                                    mDialog.setMessage(getString(R.string.please_wait));
                                }
                                mDialog.show();
                            }
                        });
                    }

                    @Override
                    public void hideLoading() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void showError(String message) {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                });
        recentlyJointPagingPresenter.getRecentlyJoint("" + memberId, "" + pageCount);*/
    }

    private void showRecentlyJointItemModel() {
        try {
            currentProfileSelected = mUserProfileModelList.get(position1);
            if (customPagerAdapter == null) {
                customPagerAdapter = new CustomPagerAdapter(mContext, mUserProfileModelList,
                        R.layout.fragment_recent_matches, new CustomPagerAdapter.ItemClickedListener() {
                    @Override
                    public void onItemClicked(View view, Object object, final int position) {

                    }

                    @Override
                    public void onViewBound(View view, Object object, int position) {

                        final UserMatchModel userProfileModel = mUserProfileModelList.get(position);

                        // currentProfileSelected = userProfileModel;

                        try {

                            LinearLayout basicDetailsContainer = findViewById(R.id.basic_info_container);

                            showBasicDetails(userProfileModel, basicDetailsContainer);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                viewPager.setAdapter(customPagerAdapter);

                // final int lastPageIndex = viewPager.getAdapter().getCount() - 1;

                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {

                        Log.d("mytag", "onPageSelected: " + position);

                        try {
                            String toolbarFName = mUserProfileModelList.get(position).getFirstName();
                            toolbarFName = toolbarFName.substring(0, 1).toUpperCase() + toolbarFName.substring(1).toLowerCase();
                            String toolbarLName = mUserProfileModelList.get(position).getLastName();
                            toolbarLName = toolbarLName.substring(0, 1).toUpperCase() + toolbarLName.substring(1).toLowerCase();
                            mToolbar.setTitle(toolbarFName + " " + toolbarLName);
                            if (isSwiped) {
                                Log.d("mytag", "onPageSelected: " + position);


                                if ((customPagerAdapter.getCount() - 1) == position + pageCount) {
                                    Log.d("mytag", "onPageSelected: " + customPagerAdapter.getCount() + " " + position);
                                    pageCount++;
                                    Log.d("mytag", "pageCount: " + pageCount);
                                    isSwiped = false;
                                    loadRecentlyData();

                                    Log.d("mytag", "pageCount: " + pageCount);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (position == mUserProfileModelList.size()) {
                            viewPager.setCurrentItem(0);
                        }
                    }


                    @Override
                    public void onPageScrollStateChanged(int state) {
                        if (state == ViewPager.SCROLL_STATE_SETTLING) {
                            isSwiped = true;
                        }

                        /*viewPager.getCurrentItem()

                        if(position==0){
                            viewPager.setCurrentItem(lastPageIndex - 1, false);
                        }*/
                       /* if(position==lastPageIndex)
                            viewPager.setCurrentItem(1, false);*/

                      /*  if(state==ViewPager.SCROLL_STATE_IDLE){
                            viewPager.setCurrentItem(1);
                        }*/


                    }
                });


               /* if (mUserProfileModelList.size() == 10) {
                    viewPager.setCurrentItem(position1);
                }*/
            } else {
                customPagerAdapter.updateList(mUserProfileModelList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    @Override
//    public void showTransactionData(String transactionId, String amount) {
//        try {
//            mTransactionId = transactionId;
//            float fAmount = Float.parseFloat(amount);
//
//            String memberId = "" + MainPresenter.getInstance().getMemberId(mContext);
//            String memberName = MainPresenter.getInstance().getMemberName(mContext);
//            String emailId = MainPresenter.getInstance().getEmailId(mContext);
//            String phone = MainPresenter.getInstance().getPhone(mContext);
//            String productInfo = "Yearly subscription";
//
//            initiatePayment(mContext, memberId, transactionId, fAmount, productInfo, memberName, emailId, phone);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void initiatePayment(Context context, String memberId, String transactionId, float amount,
//                                String productInfo, String firstName, String emailId,
//                                String phone) {
//        try {
//            Intent intent = new Intent(context, PWECouponsActivity.class);
//
//            intent.putExtra("trxn_id", transactionId);
//            intent.putExtra("trxn_amount", amount);
//            intent.putExtra("trxn_prod_info", productInfo);
//            intent.putExtra("trxn_firstname", firstName);
//            intent.putExtra("trxn_email_id", emailId);
//            intent.putExtra("trxn_phone", phone);
//            intent.putExtra("trxn_key", PaymentHelper.MerchantKey);
//            intent.putExtra("trxn_is_coupon_enabled", PaymentHelper.merchant_is_coupon_enabled);
//            intent.putExtra("trxn_salt", PaymentHelper.Salt);
//            intent.putExtra("unique_id", memberId);
//            intent.putExtra("enable_save_card", 1);
//            intent.putExtra("pay_mode", PaymentHelper.payment_mode);
//
//            startActivityForResult(intent, StaticDataModel.PWE_REQUEST_CODE);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        String result = data.getStringExtra("result");
//
//        String status;
//
//        /**
//         * Note:
//         * if not successful. Cancel is depicted by multiple string constants eg, user_cancelled, payment_failed, timeout etc
//         * for other constants check the documentation of easebuzz.in here:
//         * https://docs.easebuzz.in/mobile-integration-android/initiate-pymnt
//         */
//        if (requestCode == StaticDataModel.PWE_REQUEST_CODE) {
//            if (result.equals(EasebuzzConstants.PAYMENT_SUCCESSFUL)) {
//                status = "1";
//                successfulTransactionCompletion();
//            } else {
//                status = "0";
//                transactionFailure();
//            }
//            transactionDataPresenter.updateTransactionData("" + memberId, "" + mPackageId,
//                    mTransactionId, status);
//        }
//    }
//
//    private void successfulTransactionCompletion() {
//        try {
//            SharedPrefsHelper.getInstance(mContext).saveIntVal(SharedPrefsHelper.USERTYPE, 1);
//            MainPresenter.getInstance().setUserType(mContext, 1);
//            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
//            customPagerAdapter.notifyDataSetChanged();
//
////            Utilities.showContactNumber(mContext, tvContact, Utilities.getString(mUserDetailsModel.getMobile()));
////            Utilities.showEmail(mContext, tvEmail, Utilities.getString(mUserDetailsModel.getEmailId()));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void transactionFailure() {
//        try {
//            SharedPrefsHelper.getInstance(mContext).saveIntVal(SharedPrefsHelper.USERTYPE, 0);
//            MainPresenter.getInstance().setUserType(mContext, 0);
//            Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
//            customPagerAdapter.notifyDataSetChanged();
////            Utilities.showContactNumber(mContext, tvContact, Utilities.getString(mUserDetailsModel.getMobile()));
////            Utilities.showEmail(mContext, tvEmail, Utilities.getString(mUserDetailsModel.getEmailId()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void showTransactionUpdate(String message) {
//
//    }

    /*private void showBasicDetails1(){
        showBasicDetails(userProfileModel, basicDetailsContainer);
    }*/

    private void showBasicDetails(final UserMatchModel userProfileModel, LinearLayout basicDetailsContainer) {
        basicDetailsContainer.addView(addData("Name", userProfileModel.getFirstName() + " " + userProfileModel.getLastName(),
                null));

        Log.d("mytag", "showBasicDetails container..................: ");

       /* if (Utilities.isEmpty(userProfileModel.getEmailid()) && Utilities.isEmpty(userProfileModel.getMobile())) {
            return;
        }*/

        basicDetailsContainer.addView(addViewContact(new ViewClickListener() {
            @Override
            public void onViewClick(View view) {
                Log.d("mytag", "basicDetailsContainer:........................ ");
                ShowContactFragment showContactFragment = new ShowContactFragment();
                showContactFragment.setInitiatePaymentListener(MatrimonyProfileViewActivity1.this);
                Bundle bundle = new Bundle();

                bundle.putString(Constants.MEMBER_ID, userProfileModel.getM_ID());
                bundle.putString(Constants.CONTACT_NO, ""/* userProfileModel.getMobile()*/);
                bundle.putString(Constants.EMAIL, ""/*userProfileModel.getEmailId()*/);
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
        Log.d("mytag", "addData:11111 22222222222222222222");
        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_view_action_layout, null);
        Button view_contact = view.findViewById(R.id.view_contact);
        view_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mytag", "addData: 22222222222222222222");
                viewClickListener.onViewClick(v);
            }
        });
        return view;
    }

   /* private void showBasicDetails(final UserProfileModel userProfileModel, LinearLayout basicDetailsContainer) {
        basicDetailsContainer.addView(addData("Name", userProfileModel.getFirstName() + " " + userProfileModel.getLastName(),
                null));

        Log.d("mytag", "showBasicDetails container..................: ");

        if (Utilities.isEmpty(userProfileModel.getEmailid()) && Utilities.isEmpty(userProfileModel.getMobile())) {
            return;
        }

        basicDetailsContainer.addView(addViewContact(new ViewClickListener() {
            @Override
            public void onViewClick(View view) {
                ShowContactFragment showContactFragment = new ShowContactFragment();
                showContactFragment.setInitiatePaymentListener(MatrimonyProfileViewActivity.this);
                Bundle bundle = new Bundle();

                bundle.putString(Constants.MEMBER_ID, userProfileModel.getMemberId());
                bundle.putString(Constants.CONTACT_NO, userProfileModel.getMobile());
                bundle.putString(Constants.EMAIL, userProfileModel.getEmailid());
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
    }*/

    @Override
    public void showFileDownloaded(String message) {

    }

    @Override
    public void showLoading() {
        try {
            if (progressDialog.isShowing())
                return;
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setTitle("Loading");
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void hideLoading() {
        try {
            if (!progressDialog.isShowing())
                return;
            progressDialog.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showError(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showPdfUrl(String url) {
        // Toast.makeText(mContext, ""+fileName+" "+url, Toast.LENGTH_SHORT).show();
        downloadProfilePresenter.downloadFile(fileName, url);
    }

    @Override
    public void initiatePayment() {
        Intent intent = new Intent(mContext, PackagesActivity.class);
        startActivityForResult(intent, 111);
    }

//    @Override
//    public void showLoading() {
//
//    }
//
//    @Override
//    public void hideLoading() {
//
//    }
//
//    @Override
//    public void showError(String message) {
//
//    }
}