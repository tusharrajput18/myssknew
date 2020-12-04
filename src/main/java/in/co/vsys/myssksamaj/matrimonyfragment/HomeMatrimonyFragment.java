package in.co.vsys.myssksamaj.matrimonyfragment;

import android.app.Dialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.easebuzz.payment.kit.PWECouponsActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datamodels.StaticDataModel;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.EditMyProfileActivity;
import in.co.vsys.myssksamaj.activities.ListActivity;
import in.co.vsys.myssksamaj.activities.ListActivity1;
import in.co.vsys.myssksamaj.activities.MatriMatchesActivity;
import in.co.vsys.myssksamaj.activities.MatrimonyProfileViewActivity;
import in.co.vsys.myssksamaj.activities.ProfileVisitedListActivity;
import in.co.vsys.myssksamaj.activities.ProfileVisitedViewActivity;
import in.co.vsys.myssksamaj.activities.RecentMatchesPagerActivity;
import in.co.vsys.myssksamaj.activities.UserDetailsActivity;
import in.co.vsys.myssksamaj.adapter.CustomHorizontalListAdapter;
import in.co.vsys.myssksamaj.contracts.RecentlyJointContract;
import in.co.vsys.myssksamaj.contracts.RecentlyJointContractPaging;
import in.co.vsys.myssksamaj.contracts.RecentlyProfileViewedContract;
import in.co.vsys.myssksamaj.contracts.TransactionDataContract;
import in.co.vsys.myssksamaj.contracts.UserMatchContract;
import in.co.vsys.myssksamaj.helpers.ConnectionHelper;
import in.co.vsys.myssksamaj.helpers.PaymentHelper;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.interfaces.ListClickListener;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.data_models.UserMatchModel;
import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.presenters.RecentlyJointPagingPresenter;
import in.co.vsys.myssksamaj.presenters.RecentlyJointPresenter;
import in.co.vsys.myssksamaj.presenters.RecentlyProfileViewedPresenter;
import in.co.vsys.myssksamaj.presenters.TransactionPresenter;
import in.co.vsys.myssksamaj.presenters.UserMatchPresenter;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.ReduceComplexComponent;
import in.co.vsys.myssksamaj.utils.Utilities;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeMatrimonyFragment extends Fragment implements ListClickListener, TransactionDataContract.TransactionView,
        RecentlyProfileViewedContract.RecentlyProfileViewedView, RecentlyJointContractPaging.RecentlyJointPagingView,
        UserMatchContract.UserMatchView, LifecycleObserver {

    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String MEMBER_ID = "MemberId";
    private static final String PAGE = "PAGE";
    private RecyclerView mRecyclerViewRecentlyJoined;
    private RecyclerView mRecyclerViewProfileVisiotors;
    private RecyclerView mRecyclerViewRecentlYMatches;
    private ProgressBar mProgressBar;
    private List<UserProfileModel> recentlyJointModelList = new ArrayList<>();
    private List<UserMatchModel> matchesModelList = new ArrayList<>();
    private List<UserProfileModel> recentlyProfileVisitorModelList = new ArrayList<>();
    private CustomHorizontalListAdapter recentlyJointAdapter;
    private CustomHorizontalListAdapter recentMatchesAdapter;
    private CustomHorizontalListAdapter recentlyPVisitorAdapter;
    private CardView profileVisitorLayout, matcheLayout;
    private int profile_success, memberId;
    private String accountCreatedBy;
    private static final String TAG = HomeMatrimonyFragment.class.getSimpleName();
    private int percentFlag, progress;
    private SharedPreferences mPreference;
    private String basicProfilePercentage, photoProfilePercentage, familyProfilePercentage,
            professionProfilePercentage, horoscopeProfilePercentage, introductionProfilePercentage,
            lookingForProfilePercentage, lifeStyleProfilePercentage,loginType;

    private LinearLayout recentlyJoinedContainer, recentMatchContainer, recentlyViewedContainer;

    private int recentlyJoinedPastVisibleItems, recentlyJoinedVisibleItemCount, recentlyJoinedTotalItemCount,
            recentlyJoinedPage = 0;
    private boolean recentlyJoinedLoading = false;

    private int profileVisitorsPastVisibleItems, profileVisitorsVisibleItemCount, profileVisitorsTotalItemCount,
            profileVisitorsPage = 0;
    private boolean profileVisitorsLoading = false;

    private int profileMatchesPastVisibleItems, profileMatchesVisibleItemCount, profileMatchesTotalItemCount,
            profileMatchesPage = 0;
    private boolean profileMatchesLoading = false;

    private Context mContext;
    private AlertDialog alertDialog;
    private Dialog dialog;
    private int mMemberId;
    private String mTransactionId = "", mPackageId = "1";
    private TransactionDataContract.TransactionOps transactionDataPresenter;

    //presenters
    private RecentlyProfileViewedContract.RecentlyProfileViewedOps recentlyProfileViewedPresenter;
    private RecentlyJointContractPaging.RecentlyJointPagingOps recentlyJointPresenter;
    private UserMatchContract.UserMatchOps userMatchPresenter;


    /*recentMatchContainer*/

    public HomeMatrimonyFragment() {
        // Required empty public constructor
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void appInResumeState() {
        String status="online";
        sendLogoutTimeToServer(status);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void appInPauseState() {
        String status="offline";
        sendLogoutTimeToServer(status);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void appInStopState() {
        String status="offline";
        sendLogoutTimeToServer(status);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void appInDestroyState() {
        String status="offline";
        sendLogoutTimeToServer(status);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        initPresenters();

        mPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        loginType = "" + mPreference.getString("loginType", null);

        Log.d(TAG, "onCreate: "+loginType);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home_matrimony, container, false);

        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
       // appInPauseState();
       // appInResumeState();
        //appInStopState();
        //appInDestroyState();

        recentlyJoinedContainer = view.findViewById(R.id.recently_joined_container);
        recentMatchContainer = view.findViewById(R.id.recent_match_container);
        recentlyViewedContainer = view.findViewById(R.id.recently_viewed_container);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllData();
            }
        });

        mRecyclerViewRecentlyJoined = view.findViewById(R.id.recycler_recentlyJoint);

        if(loginType.equalsIgnoreCase("P")){
            recentMatchContainer.setVisibility(View.GONE);
        }else {
            recentMatchContainer.setVisibility(View.VISIBLE);
        }

        mRecyclerViewRecentlyJoined.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dx > 0) {
                    recentlyJoinedVisibleItemCount = mRecyclerViewRecentlyJoined.getLayoutManager().getChildCount();
                    recentlyJoinedTotalItemCount = mRecyclerViewRecentlyJoined.getLayoutManager().getItemCount();
                    recentlyJoinedPastVisibleItems = ((LinearLayoutManager) mRecyclerViewRecentlyJoined.getLayoutManager()).findFirstVisibleItemPosition();

                    if (!recentlyJoinedLoading) {
                        if ((recentlyJoinedVisibleItemCount + recentlyJoinedPastVisibleItems) >= recentlyJoinedTotalItemCount) {
                            recentlyJoinedLoading = true;
                            recentlyJoinedPage++;
                            Log.d(TAG, "onScrolled: "+recentlyJoinedPage);
                            recentlyJointPresenter.getRecentlyJoint("" + memberId, "" + recentlyJoinedPage);
                        }
                    }
                }
            }
        });

        mRecyclerViewProfileVisiotors = view.findViewById(R.id.recycler_recentlyVisitor);
        mRecyclerViewProfileVisiotors.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dx > 0) {
                    profileVisitorsVisibleItemCount = mRecyclerViewProfileVisiotors.getLayoutManager().getChildCount();
                    profileVisitorsTotalItemCount = mRecyclerViewProfileVisiotors.getLayoutManager().getItemCount();
                    profileVisitorsPastVisibleItems = ((LinearLayoutManager) mRecyclerViewProfileVisiotors.getLayoutManager()).findFirstVisibleItemPosition();

                    if (!profileVisitorsLoading) {
                        if ((profileVisitorsVisibleItemCount + profileVisitorsPastVisibleItems) >= profileVisitorsTotalItemCount) {
                            profileVisitorsLoading = true;
                            profileVisitorsPage++;
                            recentlyProfileViewedPresenter.getRecentlyProfileVisited("" + memberId, "" + profileVisitorsPage);
                        }
                    }
                }
            }
        });

        mRecyclerViewRecentlYMatches = view.findViewById(R.id.recycler_recentlyMatches);
        mRecyclerViewRecentlYMatches.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {


                if (dx > 0) {
                    profileMatchesVisibleItemCount = mRecyclerViewRecentlYMatches.getLayoutManager().getChildCount();
                    profileMatchesTotalItemCount = mRecyclerViewRecentlYMatches.getLayoutManager().getItemCount();
                    profileMatchesPastVisibleItems = ((LinearLayoutManager) mRecyclerViewRecentlYMatches.getLayoutManager()).findFirstVisibleItemPosition();

                    if (!profileMatchesLoading) {
                        if ((profileMatchesVisibleItemCount + profileMatchesPastVisibleItems) >= profileMatchesTotalItemCount) {
                            profileMatchesLoading = true;
                            profileMatchesPage++;

                            Log.d(TAG, "onScrolled: "+profileMatchesPage);
                            userMatchPresenter.getUserMatch("" + memberId, "" + profileMatchesPage);
                        }
                    }
                }
            }
        });

        mProgressBar = view.findViewById(R.id.homeFragment_progressBar);
        profileVisitorLayout = view.findViewById(R.id.profileVisitorLayout);
        matcheLayout = view.findViewById(R.id.layout_matches);
        TextView recently_joint_viewAll = view.findViewById(R.id.recently_joint_viewAll);
        TextView profileVisited_viewAll = view.findViewById(R.id.profileVisited_viewAll);
        TextView matches_viewAll = view.findViewById(R.id.recently_matches_viewAll);



        basicProfilePercentage = mPreference.getString("basicInfoPer", "0");
        photoProfilePercentage = "" + mPreference.getString("photoInfoPer", "0");
        familyProfilePercentage = "" + mPreference.getString("familyInfoPer", "0");
        professionProfilePercentage = "" + mPreference.getString("higherEduInfoPer", "0");
        horoscopeProfilePercentage = "" + mPreference.getString("horoscopeInfoPer", "0");
        introductionProfilePercentage = "" + mPreference.getString("introInfoPer", "0");
        lookingForProfilePercentage = "" + mPreference.getString("desiredInfoPer", "0");
        lifeStyleProfilePercentage = "" + mPreference.getString("lifeStyleInfoPer", "0");


        try {

            if (TextUtils.isEmpty(basicProfilePercentage)) {

                basicProfilePercentage = "20";
            }

            if (TextUtils.isEmpty(photoProfilePercentage)) {

                photoProfilePercentage = "0";
            }
            if (TextUtils.isEmpty(familyProfilePercentage)) {

                familyProfilePercentage = "0";
            }
            if (TextUtils.isEmpty(professionProfilePercentage)) {

                professionProfilePercentage = "0";
            }
            if (TextUtils.isEmpty(horoscopeProfilePercentage)) {

                horoscopeProfilePercentage = "0";
            }
            if (TextUtils.isEmpty(introductionProfilePercentage)) {

                introductionProfilePercentage = "0";
            }
            if (TextUtils.isEmpty(lookingForProfilePercentage)) {

                lookingForProfilePercentage = "0";
            }

            if (TextUtils.isEmpty(lifeStyleProfilePercentage)) {

                lifeStyleProfilePercentage = "0";
            }

            progress = Integer.parseInt(basicProfilePercentage) + Integer.parseInt(photoProfilePercentage)
                    + Integer.parseInt(familyProfilePercentage) + Integer.parseInt(professionProfilePercentage)
                    + Integer.parseInt(horoscopeProfilePercentage) + Integer.parseInt(introductionProfilePercentage)
                    + Integer.parseInt(lookingForProfilePercentage) + Integer.parseInt(lifeStyleProfilePercentage);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        memberId = mPreference.getInt("memberId", 0);
        percentFlag = mPreference.getInt("alertPercentFlag", 0);
        accountCreatedBy = mPreference.getString("accountCreatedBY", "");

        if (percentFlag == 1 && progress <= 90 && accountCreatedBy.equals("C")) {

            displayProfileCompleteAlert();
        }

        mRecyclerViewRecentlyJoined.setHasFixedSize(true);
        mRecyclerViewRecentlyJoined.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewProfileVisiotors.setLayoutManager(linearLayoutManager);
        mRecyclerViewProfileVisiotors.setHasFixedSize(true);

        mRecyclerViewRecentlYMatches.setHasFixedSize(true);
        mRecyclerViewRecentlYMatches.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));

        recently_joint_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ListActivity.class));
            }
        });

        profileVisited_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileVisitedListActivity.class));
            }
        });

        matches_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(getActivity(), ListActivity1.class/* MatriMatchesActivity.class*/));
                startActivity(new Intent(getActivity(),MatriMatchesActivity.class));
            }
        });

        transactionDataPresenter = new TransactionPresenter(this);
        if (ConnectionHelper.networkConnectivity(mContext)) {
            getAllData();
        } else {
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void initPresenters() {
        recentlyProfileViewedPresenter = new RecentlyProfileViewedPresenter(this);
        recentlyJointPresenter = new RecentlyJointPagingPresenter(this);
        userMatchPresenter = new UserMatchPresenter(this);
    }

    private void getAllData() {
        showLoading();
        recentlyJointPresenter.getRecentlyJoint("" + memberId, "" + recentlyJoinedPage);
        Log.d(TAG, "getAllData: "+memberId+" "+recentlyJoinedPage);

        if (accountCreatedBy.equals("C")) {
            userMatchPresenter.getUserMatch("" + memberId, "" + profileMatchesPage);
        }
        recentlyProfileViewedPresenter.getRecentlyProfileVisited("" + memberId, "" + profileVisitorsPage);
    }

    private void showRecentlyJointView(View itemView, final UserProfileModel recentlyJointModel, final int position) {

        try {
            ImageView profileUrl;
            final TextView tv_userId, tv_userAge, tv_height, tv_motherTounge, tv_city_country, tv_userIncome,
                    btn_invite, btn_invited, btn_accept;
            TextView tv_langaugeHeading, tv_addressHeading, tv_incomeHeading;
            TextView tv_ageHeading, tv_heightHeading,tv_online1,tv_offline1;
            LinearLayout linear_off,linear_on;
            ImageView img_premium;

            img_premium=(ImageView)itemView.findViewById(R.id.img_premium);
            linear_off=(LinearLayout)itemView.findViewById(R.id.linear_off);
            linear_on=(LinearLayout)itemView.findViewById(R.id.linear_on);

            tv_online1=(TextView)itemView.findViewById(R.id.tv_online1);
            tv_offline1=(TextView)itemView.findViewById(R.id.tv_offline1);
            profileUrl = itemView.findViewById(R.id.img_recentlyJoint_profilePic);
            tv_userId = itemView.findViewById(R.id.tv_recentlyJoint_profileId);
            tv_userAge = itemView.findViewById(R.id.tv_recentlyJoint_userAge);
            tv_height = itemView.findViewById(R.id.tv_recentlyJoint_userHeight);
            btn_invited = itemView.findViewById(R.id.btn_recentlyJoint_invited);
            tv_motherTounge = itemView.findViewById(R.id.tv_recentlyJoint_userLanguage);
            tv_city_country = itemView.findViewById(R.id.tv_recentlyJoint_userCity_Country);
            tv_userIncome = itemView.findViewById(R.id.tv_recentlyJoint_userIncome);
            btn_invite = itemView.findViewById(R.id.btn_recentlyJoint_invite);
            tv_langaugeHeading = itemView.findViewById(R.id.tv_recentlyJoint_languageHeading);
            tv_addressHeading = itemView.findViewById(R.id.tv_recentlyJoint_addressHeading);
            tv_incomeHeading = itemView.findViewById(R.id.tv_recentlyJoint_incomeHeading);
            tv_ageHeading = itemView.findViewById(R.id.tv_recentlyJoint_ageHeading);
            tv_heightHeading = itemView.findViewById(R.id.tv_recentlyJoint_heightHeading);
            btn_accept = itemView.findViewById(R.id.btn_recentlyJoint_accept);

            btn_invite.setVisibility(View.GONE);
            btn_invited.setVisibility(View.GONE);
            btn_accept.setVisibility(View.GONE);

            btn_invite.setEnabled(true);

            if (accountCreatedBy.equals("P")) {

                //  btn_invite.setEnabled(false);
                btn_invite.getBackground().setAlpha(64);

            } else if (accountCreatedBy.equals("C")) {

                btn_invite.setEnabled(true);

                if (recentlyJointModel.getInvited().equals(String.valueOf(recentlyJointModel.getMemberId()))) {

                    btn_accept.setVisibility(View.VISIBLE);
                    btn_invited.setVisibility(View.GONE);
                    btn_invite.setVisibility(View.GONE);

                } else {

                    if (TextUtils.isEmpty(recentlyJointModel.getInvited()) || recentlyJointModel.getInvited().contains("0")) {

                        btn_invite.setVisibility(View.VISIBLE);
                        btn_invited.setVisibility(View.GONE);
                        btn_accept.setVisibility(View.GONE);

                    } else {

                        btn_invite.setVisibility(View.GONE);
                        btn_invited.setVisibility(View.VISIBLE);
                        btn_accept.setVisibility(View.GONE);
                    }
                }
            }

            if(recentlyJointModel.getIspremium().equalsIgnoreCase("0")){

                img_premium.setVisibility(View.GONE);

            }else {
                img_premium.setVisibility(View.VISIBLE);
            }

           // Log.d("mytag", "showRecentlyJointView: 1111111111111"+recentlyJointModel.getOnlinestatus());

            if(recentlyJointModel.getOnlinestatus().equalsIgnoreCase("online")){
                linear_on.setVisibility(View.VISIBLE);
                linear_off.setVisibility(View.GONE);
            }else {
                linear_on.setVisibility(View.GONE);
                linear_off.setVisibility(View.VISIBLE);
            }



            if (recentlyJointModel.getMainProfilePhoto().isEmpty()) {

                Picasso.get()
                        .load(R.drawable.img_preview)
                        .placeholder(R.drawable.img_preview)
                        .error(R.drawable.img_preview)
                        .into(profileUrl);
            } else {

                Picasso.get()
                        .load(recentlyJointModel.getMainProfilePhoto())
                        .placeholder(R.drawable.img_preview)
                        .into(profileUrl);
            }


            tv_userId.setText(": " + recentlyJointModel.getUniqueId());

            if (recentlyJointModel.getAccountCreatedBy().equals("C")) {

                try {

                    int year = Calendar.getInstance().get(Calendar.YEAR);

                    String dob = recentlyJointModel.getDOB();

                    String[] dob1 = dob.split("/");

                    String dateOfBirth = dob1[2];

                    int age = (year - Integer.parseInt(dateOfBirth));

                    recentlyJointModel.setAge("" + age);
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                tv_height.setText(": " + recentlyJointModel.getMemberHeight());
                tv_userAge.setText(": " + recentlyJointModel.getAge());

                if (recentlyJointModel.getMemberInCome() == null || recentlyJointModel.getMemberInCome().equalsIgnoreCase("")) {

                    tv_userIncome.setVisibility(View.GONE);
                    tv_incomeHeading.setVisibility(View.GONE);
                } else {
                    tv_incomeHeading.setVisibility(View.VISIBLE);
                    tv_userIncome.setVisibility(View.VISIBLE);
                    tv_userIncome.setText(": " + recentlyJointModel.getMemberInCome());
                }
            } else {
                //  btn_invite.setEnabled(false);
                //   btn_invite.getBackground().setAlpha(64);
                tv_height.setVisibility(View.GONE);
                tv_userAge.setVisibility(View.GONE);
                tv_ageHeading.setVisibility(View.GONE);
                tv_userAge.setTypeface(tv_userAge.getTypeface(), Typeface.BOLD);
                tv_heightHeading.setVisibility(View.GONE);

                tv_incomeHeading.setVisibility(View.VISIBLE);
                tv_userIncome.setVisibility(View.VISIBLE);
                tv_incomeHeading.setText("Profile ");
                tv_userIncome.setText(": Profile Created by Samaj Bandhav");
                tv_userIncome.setTypeface(tv_userIncome.getTypeface(), Typeface.BOLD);
            }

            if (recentlyJointModel.getMotherTongue() == null || recentlyJointModel.getMotherTongue().equalsIgnoreCase("")) {

                tv_motherTounge.setVisibility(View.GONE);
                tv_langaugeHeading.setVisibility(View.GONE);
            } else {

                tv_motherTounge.setVisibility(View.VISIBLE);
                tv_langaugeHeading.setVisibility(View.VISIBLE);
                tv_motherTounge.setText(": " + recentlyJointModel.getMotherTongue());
            }

            String city = "" + recentlyJointModel.getMemberCity();
            String country = "" + recentlyJointModel.getMemberCountry();
            String city_country;

            if (city.equals("") || city.equalsIgnoreCase("")) {

                city_country = "" + country;
            } else {

                city_country = city + "," + country;
            }

            if (city_country.equals("") || city_country.equalsIgnoreCase("")) {

                tv_city_country.setVisibility(View.GONE);
                tv_addressHeading.setVisibility(View.GONE);
            } else {
                tv_addressHeading.setVisibility(View.VISIBLE);
                tv_city_country.setVisibility(View.VISIBLE);
                tv_city_country.setText(": " + city_country);
            }

//            profileUrl.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    if (!MainPresenter.getInstance().isPremiumMember()) {
//                        onListItemClicked(recentlyJointModel);
//                        return;
//                    }
//
//                    mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
//                    int userMemeberId = Integer.parseInt(recentlyJointModel.getMemberId());
//
//                    String memberName = recentlyJointModel.getFirstName() + " " + recentlyJointModel.getLastName();
//
//                    Intent intent = new Intent(mContext, MatrimonyProfileViewActivity.class);
//                    intent.putExtra("position", position);
//                    intent.putExtra("memberId", userMemeberId);
//                    intent.putExtra("flagHomeActivity", 1);
//                    MatrimonyProfileViewActivity.userName = memberName;
//
//                    mContext.startActivity(intent);
//                }
//            });

            btn_invite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                        onListItemClicked(recentlyJointModel);
//                        return;
//                    }

                    if (accountCreatedBy.equals("P")) {

                        Toast.makeText(mContext, "You can not invite Candidate profile ", Toast.LENGTH_LONG).show();

                    } else {

                        int senderId = Integer.parseInt(recentlyJointModel.getMemberId());
                        MatrimonyUtils.inviteTask(mContext, memberId, senderId, "1");

                        String name = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.MEMBER_NAME);
                        String imageUrl = SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.PROFILE_URL);

                        String message = "Invitation Received from " + name;

                        MatrimonyUtils.sendInvitationNotification(recentlyJointModel.getDeviceId(),
                                message, imageUrl, String.valueOf(recentlyJointModel.getMemberId()), String.valueOf(memberId), name);

                        btn_invite.setVisibility(View.GONE);
                        btn_invited.setVisibility(View.VISIBLE);
                    }
                }
            });

            btn_invited.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                        onListItemClicked(recentlyJointModel);
//                        return;
//                    }

                    int senderId = Integer.parseInt(recentlyJointModel.getMemberId());
                    MatrimonyUtils.inviteTask(mContext, memberId, senderId, "0");

                    btn_invite.setVisibility(View.VISIBLE);
                    btn_invited.setVisibility(View.GONE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showRecentlyProfileVisitor(View itemView, final UserProfileModel model, final int position) {
        try {
            ImageView img_profileVisitedImage;
            img_profileVisitedImage = itemView.findViewById(R.id.img_profileVisitedImage);

            ImageView img_premium;

            img_premium=(ImageView)itemView.findViewById(R.id.img_premium);

            if(model.getIspremium().equalsIgnoreCase("0")){

                img_premium.setVisibility(View.GONE);

            }else {
                img_premium.setVisibility(View.VISIBLE);
            }

            if (model.getMainProfilePhoto().isEmpty()) {

                Picasso.get()
                        .load(R.drawable.img_preview)
                        .placeholder(R.drawable.img_preview)
                        .error(R.drawable.img_preview)
                        .into(img_profileVisitedImage);
            } else {

                Picasso.get()
                        .load(model.getMainProfilePhoto())
                        .placeholder(R.drawable.img_preview)
                        .into(img_profileVisitedImage);
            }

            img_profileVisitedImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                        onListItemClicked(model);
//                        return;
//                    }

                    Intent intent = new Intent(mContext, ProfileVisitedViewActivity.class);
                    int actualPositionInList=getActualPositionProfileVisited(model.getMemberId());
                    String memberName = model.getFirstName() + " " + model.getLastName();

                    intent.putExtra("name",memberName);

                    intent.putExtra("page", getPage(actualPositionInList,position));
                    intent.putExtra("PVisitedPosition", getPosition(actualPositionInList,position));
                    intent.putExtra("PVisitedFlag", 1);
                    intent.putExtra("memberName", memberName);
                    intent.putExtra("memberid",model.getMemberId());

                    mContext.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showRecentMatchedList(View itemView, final UserMatchModel userMatchModel, final int position) {
        ImageView profileUrl;
        final TextView tv_langaugeHeading, tv_addressHeading, tv_incomeHeading, tv_userId, tv_userAge,
                tv_userHeight, tv_motherTounge, tv_city_country, tv_userIncome, btn_invite, btn_invited, btn_accept;

        ImageView img_premium;

        img_premium=(ImageView)itemView.findViewById(R.id.img_premium);

        profileUrl = itemView.findViewById(R.id.img_recentlyJoint_profilePic);
        tv_userId = itemView.findViewById(R.id.tv_recentlyJoint_profileId);
        tv_userAge = itemView.findViewById(R.id.tv_recentlyJoint_userAge);
        tv_userHeight = itemView.findViewById(R.id.tv_recentlyJoint_userHeight);
        tv_motherTounge = itemView.findViewById(R.id.tv_recentlyJoint_userLanguage);
        tv_city_country = itemView.findViewById(R.id.tv_recentlyJoint_userCity_Country);
        tv_userIncome = itemView.findViewById(R.id.tv_recentlyJoint_userIncome);
        btn_invite = itemView.findViewById(R.id.btn_recentlyJoint_invite);
        btn_invited = itemView.findViewById(R.id.btn_recentlyJoint_invited);
        tv_langaugeHeading = itemView.findViewById(R.id.tv_recentlyJoint_languageHeading);
        tv_addressHeading = itemView.findViewById(R.id.tv_recentlyJoint_addressHeading);
        tv_incomeHeading = itemView.findViewById(R.id.tv_recentlyJoint_incomeHeading);
        btn_accept = itemView.findViewById(R.id.btn_recentlyJoint_accept);

        btn_invite.setVisibility(View.GONE);
        btn_invited.setVisibility(View.GONE);
        btn_accept.setVisibility(View.GONE);

        if (userMatchModel.getInvited().equals(String.valueOf(userMatchModel.getM_ID()))) {
            btn_accept.setVisibility(View.VISIBLE);
            btn_invited.setVisibility(View.GONE);
            btn_invite.setVisibility(View.GONE);

        } else {

            if (TextUtils.isEmpty(userMatchModel.getInvited()) || userMatchModel.getInvited().contains("0")) {

                btn_invite.setVisibility(View.VISIBLE);
                btn_invited.setVisibility(View.GONE);

            } else {

                btn_invite.setVisibility(View.GONE);
                btn_invited.setVisibility(View.VISIBLE);
            }
        }

        if(userMatchModel.getIspremium().equalsIgnoreCase("0")){

            img_premium.setVisibility(View.GONE);

        }else {
            img_premium.setVisibility(View.VISIBLE);
        }



        if (userMatchModel.getMainProfilePhoto().isEmpty()) {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(profileUrl);
        } else {

            Picasso.get()
                    .load(userMatchModel.getMainProfilePhoto())
                    .placeholder(R.drawable.img_preview)
                    .into(profileUrl);
        }


        if (userMatchModel.getMotherTongue() == null || userMatchModel.getMotherTongue().equalsIgnoreCase("")) {

            tv_motherTounge.setVisibility(View.GONE);
            tv_langaugeHeading.setVisibility(View.GONE);
        } else {

            tv_motherTounge.setVisibility(View.VISIBLE);
            tv_langaugeHeading.setVisibility(View.VISIBLE);
            tv_motherTounge.setText(": " + userMatchModel.getMotherTongue());
        }

        String city = "" + userMatchModel.getCity();
        String country = "" + userMatchModel.getCountry();
        String city_country;

        if (city.equals("") || city.equalsIgnoreCase("")) {

            city_country = "" + country;
        } else {

            city_country = city + "," + country;
        }


        if (city_country.equals("") || city_country.equalsIgnoreCase("")) {

            tv_city_country.setVisibility(View.GONE);
            tv_addressHeading.setVisibility(View.GONE);
        } else {
            tv_addressHeading.setVisibility(View.VISIBLE);
            tv_city_country.setVisibility(View.VISIBLE);
            tv_city_country.setText(": " + city_country);
        }

        if (userMatchModel.getIncome() == null || userMatchModel.getIncome().equalsIgnoreCase("")) {

            tv_userIncome.setVisibility(View.GONE);
            tv_incomeHeading.setVisibility(View.GONE);
        } else {
            tv_incomeHeading.setVisibility(View.VISIBLE);
            tv_userIncome.setVisibility(View.VISIBLE);
            tv_userIncome.setText(": " + userMatchModel.getIncome());
        }

        tv_userId.setText(": " + userMatchModel.getUniqueId());

        userMatchModel.setAge(MatrimonyUtils.getAge(userMatchModel.getDOB()));
        tv_userAge.setText(": " + userMatchModel.getAge());
        tv_userHeight.setText(": " + userMatchModel.getHeight());

        profileUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    onListItemClicked(userMatchModel);
//                    return;
//                }

                int uniqueId = Integer.parseInt(userMatchModel.getM_ID());
                String name=userMatchModel.getFirstName()+" "+userMatchModel.getLastName();

               Intent intent = new Intent(mContext, UserDetailsActivity.class);
               intent.putExtra("uniqueId", uniqueId);
               intent.putExtra("flagMatches", 1);
               intent.putExtra("name", name);

                UserDetailsActivity.minHeight = Integer.parseInt(userMatchModel.getMIN_Height());
                UserDetailsActivity.minAge = Integer.parseInt(userMatchModel.getMIN_Age());
               UserDetailsActivity.drinkType = Integer.parseInt(userMatchModel.getDrink_Habit());
                UserDetailsActivity.foodType = Integer.parseInt(userMatchModel.getFood_Type());
                UserDetailsActivity.smokeType = Integer.parseInt(userMatchModel.getSmoke_Habit());
                UserDetailsActivity.marriedStatus = Integer.parseInt(userMatchModel.getMarried_Status());
                UserDetailsActivity.receivedInviteFlag = userMatchModel.getInvited();
                startActivity(intent);


             /*   int actualPositionInList=getActualPositionMatch(String.valueOf(uniqueId));
                String memberName = userMatchModel.getFirstName() + " " + userMatchModel.getLastName();

                Log.d(TAG, "actualPositionInList: "+actualPositionInList+" "+profileMatchesPage);

                Intent intent = new Intent(mContext, RecentMatchesPagerActivity.class);
                intent.putExtra("position", getPosition(actualPositionInList,profileMatchesPage));
                intent.putExtra("pageCount",getPage(actualPositionInList,profileMatchesPage));
                intent.putExtra("memberId", uniqueId);
                intent.putExtra("flagHomeActivity", 1);
                MatrimonyProfileViewActivity.userName = memberName;
                mContext.startActivity(intent);*/
            }
        });

        btn_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    onListItemClicked(userMatchModel);
//                    return;
//                }

                int senderId = Integer.parseInt(userMatchModel.getM_ID());
                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "1");

                btn_invite.setVisibility(View.GONE);
                btn_invited.setVisibility(View.VISIBLE);

            }
        });

        btn_invited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    onListItemClicked(userMatchModel);
//                    return;
//                }

                int senderId = Integer.parseInt(userMatchModel.getM_ID());
                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "0");

                btn_invite.setVisibility(View.VISIBLE);
                btn_invited.setVisibility(View.GONE);
            }
        });
    }





    private void displayProfileCompleteAlert() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.row_profile_alert, null);
        TextView tvMemberName = view.findViewById(R.id.row_parcentName);
        final TextView tvPercentList = view.findViewById(R.id.row_percent_list);
        ProgressBar mProgressBar = view.findViewById(R.id.row_percentProgressBar);
        TextView tv_close = view.findViewById(R.id.row_percent_close);
        TextView tv_progress = view.findViewById(R.id.row_percent_totalProgress);
        ImageView img = view.findViewById(R.id.row_parcentImage);

        mBuilder.setView(view);

        if (mPreference.getString("nav_profileUrl", "").length() > 10) {

            Picasso.get()
                    .load(mPreference.getString("nav_profileUrl", ""))
                    .placeholder(R.drawable.img_preview)
                    .into(img);
        } else {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .into(img);
        }

        mProgressBar.setProgress(progress);

        tv_progress.setText("Your profile is " + "" + progress + "% completed so far");


        if (photoProfilePercentage.equals("0")) {

            tvPercentList.setText("Upload Profile Photos (+10%)");
        } else {

            if (lookingForProfilePercentage.equals("0")) {

                tvPercentList.setText("Upload Looking For Information (+20%)");
            } else {

                if (lifeStyleProfilePercentage.equals("0")) {

                    tvPercentList.setText("Upload LifeStyle Information (+10%)");
                } else {

                    if (horoscopeProfilePercentage.equals("0")) {

                        tvPercentList.setText("Upload Horoscope Information (+10%)");
                    } else {

                        if (professionProfilePercentage.equals("0")) {

                            tvPercentList.setText("Upload Profession Information (+10%)");
                        } else {

                            if (introductionProfilePercentage.equals("0")) {

                                tvPercentList.setText("Upload Introduction (+10%)");
                            } else {

                                if (familyProfilePercentage.equals("0")) {

                                    tvPercentList.setText("Upload Family Information (+10%)");
                                }
                            }
                        }
                    }
                }
            }
        }

        tvMemberName.setText(SharedPrefsHelper.getInstance(mContext).getStringVal(SharedPrefsHelper.MEMBER_NAME));

        mBuilder.setCancelable(true);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPreference.edit().remove("alertPercentFlag").apply();
                dialog.dismiss();

            }
        });

        tvPercentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), EditMyProfileActivity.class));
                mPreference.edit().remove("alertPercentFlag").apply();
                dialog.dismiss();
            }
        });
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
                transactionDataPresenter.getTransactionData("" + mMemberId, mPackageId);
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
    public void showLoading() {
        try {
            swipeRefreshLayout.setRefreshing(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hideLoading() {
        try {
            swipeRefreshLayout.setRefreshing(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showError(String message) {
        hideLoading();
        try {
//            ((HomeActivity) getActivity()).showError(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

            getActivity().startActivityForResult(intent, StaticDataModel.PWE_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showRecentlyJoint(List<UserProfileModel> data) {
        try {
            recentlyJoinedLoading = false;
            if (Utilities.isListEmpty(data)) {
                recentlyJoinedContainer.setVisibility(View.GONE);
                return;
            }
            recentlyJoinedContainer.setVisibility(View.VISIBLE);

//            recentlyJointModelList.clear();

            Log.d(TAG, "showRecentlyJoint: "+data.size());
            int j = 0;

            while (j<data.size()){


               if(data.get(j).getSenderBlocked().equalsIgnoreCase("0")){

                   recentlyJointModelList.add(data.get(j));
                   Log.d(TAG, "showRecentlyJoint: "+data.get(j).getFirstName());
               }

                j++;
            }

            Log.d(TAG, "showRecentlyJoint: "+recentlyJointModelList.size());

            if (recentlyJointAdapter == null) {
                recentlyJointAdapter = new CustomHorizontalListAdapter(mContext, mRecyclerViewRecentlyJoined,
                        recentlyJointModelList, R.layout.row_horizontal_recycler, false,
                        new CustomHorizontalListAdapter.ItemClickedListener() {
                            @Override
                            public void onViewBound(View view, Object object, int position) {
                                showRecentlyJointView(view, (UserProfileModel) object, position);
                            }

                            @Override
                            public void onItemClicked(View view, Object object, int position) {
                                UserProfileModel userProfileModel = (UserProfileModel) object;
//                                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                                    onListItemClicked(userProfileModel);
//                                    return;
//                                }

                                int userMemeberId = Integer.parseInt(userProfileModel.getMemberId());
                                int actualPositionInList=getActualPositionRecentlyJoined(userProfileModel.getMemberId());


                                String memberName = userProfileModel.getFirstName() + " " + userProfileModel.getLastName();
                                Intent intent = new Intent(mContext, MatrimonyProfileViewActivity.class);
                                intent.putExtra("position", getPosition(actualPositionInList,recentlyJoinedPage));
                                intent.putExtra("pageCount",getPage(actualPositionInList,recentlyJoinedPage));
                                intent.putExtra("memberId", userMemeberId);
                                intent.putExtra("flagHomeActivity", 1);
                                MatrimonyProfileViewActivity.userName = memberName;
                                mContext.startActivity(intent);
                            }
                        });
                mRecyclerViewRecentlyJoined.setAdapter(recentlyJointAdapter);
            } else
                recentlyJointAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showRecentlyProfileViewed(List<UserProfileModel> data) {
        try {
            profileVisitorsLoading = false;
            if (Utilities.isListEmpty(data)) {
                recentlyViewedContainer.setVisibility(View.GONE);
                return;
            }
            recentlyViewedContainer.setVisibility(View.VISIBLE);

//            recentlyProfileVisitorModelList.clear();

            int j = 0;

            while (j<data.size()){
                if(data.get(j).getSenderBlocked().equalsIgnoreCase("0")){

                    recentlyProfileVisitorModelList.add(data.get(j));
                    Log.d(TAG, "showRecentlyJoint: "+data.get(j).getFirstName());
                }

                j++;
            }

           // recentlyProfileVisitorModelList.addAll(data);

            if (recentlyPVisitorAdapter == null) {
                recentlyPVisitorAdapter = new CustomHorizontalListAdapter(mContext,
                        mRecyclerViewProfileVisiotors, recentlyProfileVisitorModelList,
                        R.layout.profile_visited_row, false, new CustomHorizontalListAdapter.ItemClickedListener() {
                    @Override
                    public void onViewBound(View view, Object object, int position) {
                        showRecentlyProfileVisitor(view, (UserProfileModel) object,
                                position);
                    }

                    @Override
                    public void onItemClicked(View view, Object object, int position) {

                    }
                });
                mRecyclerViewProfileVisiotors.setAdapter(recentlyPVisitorAdapter);
            } else
                recentlyPVisitorAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showUserMatch(List<UserMatchModel> data) {
        try {
            profileMatchesLoading = false;
            if (Utilities.isListEmpty(data)) {
                recentMatchContainer.setVisibility(View.GONE);
                return;
            }
            recentMatchContainer.setVisibility(View.VISIBLE);

            int j = 0;

            while (j<data.size()){


                if(data.get(j).getSenderBlocked().equalsIgnoreCase("0")){

                    matchesModelList.add(data.get(j));
                    Log.d(TAG, "showRecentlyJoint: "+data.get(j).getFirstName());
                }

                j++;
            }

//            matchesModelList.clear();
          //  matchesModelList.addAll(data);
            if(recentMatchesAdapter==null) {
                recentMatchesAdapter = new CustomHorizontalListAdapter(mContext, mRecyclerViewRecentlYMatches, matchesModelList,
                        R.layout.row_horizontal_recycler, false, new CustomHorizontalListAdapter.ItemClickedListener() {
                    @Override
                    public void onViewBound(View itemView, Object object, int position) {
                        UserMatchModel userMatchModel = (UserMatchModel) object;
                        showRecentMatchedList(itemView, userMatchModel, position);
                    }

                    @Override
                    public void onItemClicked(View view, Object object, int position) {

                    }
                });
                mRecyclerViewRecentlYMatches.setAdapter(recentMatchesAdapter);
            }else
                recentMatchesAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private int getPosition(int actualPositionInList,int pageCount) {
        if(pageCount>0){
            return mod(actualPositionInList,10);
        }else {
            return actualPositionInList;
        }

    }

    private int mod(int x, int y)
    {
        int result = x % y;
        if (result < 0)
            result += y;
        return result;
    }

    private int getActualPositionRecentlyJoined(String userMemberId) {
        int pos=0;
        for (int i=0;i<recentlyJointModelList.size();i++){
            if(userMemberId.equals(recentlyJointModelList.get(i).getMemberId())){
                pos=i;
                break;
            }
        }
        return pos;
    }

    private int getActualPositionMatch(String userMemberId) {
        int pos=0;
        for (int i=0;i<matchesModelList.size();i++){
            if(userMemberId.equals(matchesModelList.get(i).getM_ID())){
                pos=i;
                break;
            }
        }
        return pos;
    }

    private int getActualPositionProfileVisited(String userMemberId) {
        int pos=0;
        for (int i=0;i<recentlyProfileVisitorModelList.size();i++){
            if(userMemberId.equals(recentlyProfileVisitorModelList.get(i).getMemberId())){
                pos=i;
                break;
            }
        }
        return pos;
    }

    private int getPage(int actualPositionInList,int pageCount) {
        if(pageCount>0){
            return actualPositionInList/10;
        }else {
            return 0;
        }
    }


    private void sendLogoutTimeToServer(final String status) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.ToggleOnlineStatus /*Config.matrimony_log_out_url*/,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e(TAG, "logout response====: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                //  Toast.makeText(mContext, "Log out time send", Toast.LENGTH_SHORT).show();
                            } else {

                                 Toast.makeText(mContext, "Log out time not send...", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e(TAG, "logout error===========: ", error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();

                param.put(MEMBER_ID, String.valueOf(memberId));
                param.put("Status", status);
                Log.d("mytag", "getParams1: "+memberId);

                return param;
            }
        };

        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

}