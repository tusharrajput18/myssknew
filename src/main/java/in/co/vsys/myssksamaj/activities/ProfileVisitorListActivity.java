package in.co.vsys.myssksamaj.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.easebuzz.payment.kit.PWECouponsActivity;

import java.util.ArrayList;
import java.util.List;

import datamodels.StaticDataModel;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.contracts.TransactionDataContract;
import in.co.vsys.myssksamaj.fragments.BlockProfile;
import in.co.vsys.myssksamaj.fragments.CandidateProfileVisit;
import in.co.vsys.myssksamaj.fragments.ParentProfileVisit;
import in.co.vsys.myssksamaj.helpers.PaymentHelper;
import in.co.vsys.myssksamaj.interfaces.ListClickListener;
import in.co.vsys.myssksamaj.matrimonyfragment.InvitationAcceptedFragment;
import in.co.vsys.myssksamaj.matrimonyfragment.InvitationReceivedFragment;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.utils.EasebuzzConstants;

public class ProfileVisitorListActivity extends AppCompatActivity implements TransactionDataContract.TransactionView, ListClickListener {

    private static final String MEMBER_ID = "MemberId";
//    private CustomListAdapter profileVisitedAdapter;
//    private RecyclerView mRecyclerView;
//    private int memberId;
//    private ProgressBar mProgressBar;

    private static final String TAG = ProfileVisitorListActivity.class.getSimpleName();

    private SharedPreferences mPreferences;
    private Context mContext;
    private AlertDialog alertDialog;
    private Dialog dialog;
    private String mTransactionId = "", mPackageId = "1";
//    private TransactionDataContract.TransactionOps transactionDataPresenter;

    private TabLayout tabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_visitor_list);
        mContext = this;

//        mRecyclerView = (RecyclerView) findViewById(R.id.profileVisitedList);
//        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_visitorList);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_profileVisited);

        mViewPager = (ViewPager) findViewById(R.id.invitation_viewpager);
        setupViewPager(mViewPager);
        tabLayout = (TabLayout) findViewById(R.id.invitationTab);
        tabLayout.setupWithViewPager(mViewPager);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Profile Visitor List");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

//        profileVisitorModelList = new ArrayList<>();

//        SharedPreferences mPreference = PreferenceManager.getDefaultSharedPreferences(this);
//        memberId = mPreference.getInt("memberId", 0);

//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

//        loadProfileVisitorData();

//        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        transactionDataPresenter = new TransactionPresenter(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ParentProfileVisit(), "Parents");
        adapter.addFragment(new CandidateProfileVisit(), "Candidates");
        adapter.addFragment(new BlockProfile(), "Blocked Profile");
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

//    private void loadProfileVisitorData() {
//
//        MyProfileViewedContract.MyProfileViewedOps myProfileViewedOps = new MyProfileViewedPresenter(
//                new MyProfileViewedContract.MyProfileViewedView() {
//                    @Override
//                    public void showLoading() {
//                        mProgressBar.setVisibility(View.VISIBLE);
//                    }
//
//                    @Override
//                    public void hideLoading() {
//                        mProgressBar.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void showError(String message) {
//                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void showPeopleWhoViewedMyProfile(List<UserProfileModel> userProfileModels) {
//                        profileVisitorModelList.clear();
//                        profileVisitorModelList.addAll(userProfileModels);
//
//                        profileVisitedAdapter = new CustomListAdapter(mContext, mRecyclerView,
//                                profileVisitorModelList, R.layout.recyclerview_row, new CustomListAdapter.ItemClickedListener() {
//                            @Override
//                            public void onViewBound(View view, Object object, int position) {
//                                showProfileVisitedData(view, (UserProfileModel) object, position);
//                            }
//
//                            @Override
//                            public void onItemClicked(View view, Object object, int position) {
//
//                            }
//                        });
//                        mRecyclerView.setAdapter(profileVisitedAdapter);
//                    }
//                });
//        myProfileViewedOps.getPeopleWhoViewedMyProfile("" + memberId);

//        StringRequest recentlYProfileVisitors = new StringRequest(Request.Method.POST, Config.recentlyProfileVisitorUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        mProgressBar.setVisibility(View.GONE);
//
//                        try {
//                            JSONArray jsonArray;
//                            JSONObject rJsonObject = new JSONObject(response);
//
//                            int success = rJsonObject.getInt("success");
//
//                            if (success == 1) {
//
//                                jsonArray = rJsonObject.getJSONArray("data");
//
//                                for (int i = 0; i < jsonArray.length(); i++) {
//
//                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//
//                                    RecentlyProfileVisitorModel model = new RecentlyProfileVisitorModel();
//
//                                    int memberId = jsonObject1.getInt("MemberId");
//                                    String uniqueId = jsonObject1.getString("UniqueId");
//                                    String firstName = jsonObject1.getString("FirstName");
//                                    String lastName = jsonObject1.getString("LastName");
//                                    String userDateOfBirth = jsonObject1.getString("DOB");
//                                    String userHeight = jsonObject1.getString("MemberHeight");
//                                    String motherTongue = jsonObject1.getString("MotherTongue");
//                                    String userProfileUrl = jsonObject1.getString("MainProfilePhoto");
//                                    String userCity = jsonObject1.getString("MemberCity");
//                                    String userCountry = jsonObject1.getString("MemberCountry");
//                                    String userEductionIN = jsonObject1.getString("EducationInName");
//                                    String userIncome = jsonObject1.getString("MemberInCome");
//                                    String marriedStatus = jsonObject1.getString("MarriedStatus");
//                                    String shortlist = jsonObject1.getString("ShortListed");
//                                    String invite = jsonObject1.getString("Invited");
//                                    String accountCreatedBy = jsonObject1.getString("AccountCreatedBy");
//                                    model.setOnlieTime(jsonObject1.getString("Online Time"));
//                                    model.setOfflineTime(jsonObject1.getString("Offline Time"));
//                                    model.setTokenId(jsonObject1.getString("DeviceId"));
//                                    model.setInviteChatFlag(jsonObject1.getString("IsChat"));
//                                    model.setInviteReceivedFlag(jsonObject1.getString("ReceiverFlag"));
//
//                                    model.setFirstName(firstName);
//                                    model.setLastName(lastName);
//                                    model.setUserMemberId(memberId);
//                                    model.setUniqueId(uniqueId);
//                                    model.setDOB(userDateOfBirth);
//                                    model.setUserHeight(userHeight);
//                                    model.setMotherTongue(motherTongue);
//                                    model.setUserProfileUrl(userProfileUrl);
//                                    model.setUserCity(userCity);
//                                    model.setUserCountry(userCountry);
//                                    model.setEductionIn(userEductionIN);
//                                    model.setUserIncome(userIncome);
//                                    model.setMarriedStatus(marriedStatus);
//                                    model.setShortlistedFlag(shortlist);
//                                    model.setInvitedFlag(invite);
//                                    model.setProfileCreatedBy(accountCreatedBy);
//
//
//                                    profileVisitorModelList.add(model);
//
//                                }
//
//                                profileVisitedAdapter = new CustomListAdapter(mContext, mRecyclerView,
//                                        profileVisitorModelList, R.layout.recyclerview_row, new CustomListAdapter.ItemClickedListener() {
//                                    @Override
//                                    public void onViewBound(View view, Object object, int position) {
//                                        showProfileVisitedData(view, (RecentlyProfileVisitorModel) object, position);
//                                    }
//
//                                    @Override
//                                    public void onItemClicked(View view, Object object, int position) {
//
//                                    }
//                                });
//                                mRecyclerView.setAdapter(profileVisitedAdapter);
//                            } else {
//
//                                Toast.makeText(ProfileVisitorListActivity.this, "list not available", Toast.LENGTH_SHORT).show();
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        mProgressBar.setVisibility(View.GONE);
//                    }
//                }) {
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> param = new HashMap<>();
//                param.put(MEMBER_ID, String.valueOf(memberId));
//
//                return param;
//            }
//        };
//
//        VolleySingleton.getInstance(this).addToRequestQueue(recentlYProfileVisitors);
//    }

//    private void showProfileVisitedData(View itemView, final UserProfileModel model, final int position) {
//        ImageView img_scroll_profileImage, img_accept;
//        final TextView tv_scroll_memberId, tv_scroll_profile_otherDetails, tv_scroll_profile_languageDetails, tv_scroll_profile_ProfessionDetails, tv_scroll_profile_saleryDetails, tv_scroll_profile_marriedStatus1, tv_scroll_profile_AddressDetails, tv_invite, tv_invited, tv_shortlist, tv_shortlisted;
//        final ImageView img_scroll_invite, img_invited, img_scroll_shortlist, img_scroll_shortlisted, img_scroll_chat;
//        TextView tv_online, tv_offline, tv_accept;
//        LinearLayout offlineLayout;
//
//        img_scroll_profileImage = itemView.findViewById(R.id.img_scroll_profileImage);
//        tv_scroll_memberId = itemView.findViewById(R.id.tv_scroll_memberId);
//        img_accept = itemView.findViewById(R.id.img_scroll_accept);
//        tv_accept = itemView.findViewById(R.id.tv_scroll_accept);
//        tv_scroll_profile_otherDetails = itemView.findViewById(R.id.tv_scroll_profile_otherDetails);
//        tv_scroll_profile_languageDetails = itemView.findViewById(R.id.tv_scroll_profile_languageDetails);
//        tv_scroll_profile_ProfessionDetails = itemView.findViewById(R.id.tv_scroll_profile_ProfessionDetails);
//        tv_scroll_profile_saleryDetails = itemView.findViewById(R.id.tv_scroll_profile_saleryDetails);
//        tv_scroll_profile_marriedStatus1 = itemView.findViewById(R.id.tv_scroll_profile_marriedStatus1);
//        tv_scroll_profile_AddressDetails = itemView.findViewById(R.id.tv_scroll_profile_AddressDetails);
//        img_scroll_invite = itemView.findViewById(R.id.img_scroll_invite);
//        img_invited = itemView.findViewById(R.id.img_scroll_invited);
//        img_scroll_shortlist = itemView.findViewById(R.id.img_scroll_shortlist);
//        img_scroll_shortlisted = itemView.findViewById(R.id.img_scroll_shortlisted);
//        img_scroll_chat = itemView.findViewById(R.id.img_scroll_chat);
//        tv_shortlist = itemView.findViewById(R.id.tv_scroll_shortlist);
//        tv_shortlisted = itemView.findViewById(R.id.tv_scroll_shortlisted);
//        tv_invite = itemView.findViewById(R.id.tv_scroll_invite);
//        tv_invited = itemView.findViewById(R.id.tv_scroll_invited);
//        tv_online = itemView.findViewById(R.id.tv_scroll_online);
//        tv_offline = itemView.findViewById(R.id.tv_scroll_Offline);
//        offlineLayout = itemView.findViewById(R.id.layout_scroll_offline);
//
//
//        if (TextUtils.isEmpty(model.getShorted()) || model.getShorted().contains("0")) {
//
//            img_scroll_shortlist.setVisibility(View.VISIBLE);
//            img_scroll_shortlisted.setVisibility(View.GONE);
//            tv_shortlist.setVisibility(View.VISIBLE);
//            tv_shortlisted.setVisibility(View.GONE);
//
//        } else {
//
//            img_scroll_shortlisted.setVisibility(View.VISIBLE);
//            img_scroll_shortlist.setVisibility(View.GONE);
//            tv_shortlist.setVisibility(View.GONE);
//            tv_shortlisted.setVisibility(View.VISIBLE);
//
//        }
//
//        img_accept.setVisibility(View.GONE);
//        tv_accept.setVisibility(View.GONE);
//        img_scroll_invite.setVisibility(View.GONE);
//        tv_invite.setVisibility(View.GONE);
//
//        if (model.getInvited().equals(String.valueOf(model.getMemberId()))) {
//
//            img_scroll_invite.setVisibility(View.GONE);
//            tv_invite.setVisibility(View.GONE);
//            img_invited.setVisibility(View.GONE);
//            tv_invited.setVisibility(View.GONE);
//            img_accept.setVisibility(View.VISIBLE);
//            tv_accept.setVisibility(View.VISIBLE);
//
//        } else {
//
//            if (TextUtils.isEmpty(model.getInvited()) || model.getInvited().contains("0")) {
//
//                img_scroll_invite.setVisibility(View.VISIBLE);
//                img_invited.setVisibility(View.GONE);
//                tv_invite.setVisibility(View.VISIBLE);
//                tv_invited.setVisibility(View.GONE);
//
//            } else {
//
//                img_invited.setVisibility(View.VISIBLE);
//                img_scroll_invite.setVisibility(View.GONE);
//                tv_invite.setVisibility(View.GONE);
//                tv_invited.setVisibility(View.VISIBLE);
//            }
//        }
//
//        if (model.getMainProfilePhoto().isEmpty()) {
//
//            Picasso.get()
//                    .load(R.drawable.img_preview)
//                    .placeholder(R.drawable.img_preview)
//                    .error(R.drawable.img_preview)
//                    .into(img_scroll_profileImage);
//        } else {
//
//            Picasso.get()
//                    .load(model.getMainProfilePhoto())
//                    .placeholder(R.drawable.img_preview)
//                    .into(img_scroll_profileImage);
//        }
//
//        tv_scroll_memberId.setText(model.getUniqueId());
//
//        //String age_height = model.getUserAge() + "," + model.getUserHeight();
//
//        if (model.getAccountCreatedBy().equals("P")) {
//
//            tv_scroll_profile_otherDetails.setVisibility(View.VISIBLE);
//            tv_scroll_profile_otherDetails.setText("Profile Created by Samaj Bandhav");
//            tv_scroll_profile_saleryDetails.setVisibility(View.GONE);
//            tv_scroll_profile_ProfessionDetails.setVisibility(View.GONE);
//            tv_scroll_profile_languageDetails.setVisibility(View.GONE);
//            tv_scroll_profile_marriedStatus1.setVisibility(View.GONE);
//
//        } else if (model.getAccountCreatedBy().equals("C")) {
//            try {
//                model.setAge("" + model.getDOB());
//            } catch (ArrayIndexOutOfBoundsException e) {
//
//                e.printStackTrace();
//            }
//
//            String age = "" + model.getAge();
//            String height = "" + model.getMemberHeight();
//            String age_height;
//
//            if (age.equals("") || age.equalsIgnoreCase("")) {
//
//                age_height = "" + height;
//            } else {
//
//                age_height = age + " , " + height;
//            }
//
//            if (age_height.equals("") || age_height.equalsIgnoreCase("")) {
//
//                tv_scroll_profile_otherDetails.setVisibility(View.GONE);
//
//            } else {
//
//                tv_scroll_profile_otherDetails.setVisibility(View.VISIBLE);
//                tv_scroll_profile_otherDetails.setText(age_height);
//            }
//
//            if (model.getMotherTongue().equals("") || model.getMotherTongue().equalsIgnoreCase("")) {
//
//                tv_scroll_profile_languageDetails.setVisibility(View.GONE);
//
//            } else {
//
//                tv_scroll_profile_languageDetails.setVisibility(View.VISIBLE);
//                tv_scroll_profile_languageDetails.setText(model.getMotherTongue());
//            }
//
//            if (model.getMarriedStatus().equals("") || model.getMarriedStatus().equalsIgnoreCase("")) {
//
//                tv_scroll_profile_marriedStatus1.setVisibility(View.GONE);
//
//            } else {
//
//                tv_scroll_profile_marriedStatus1.setVisibility(View.VISIBLE);
//                tv_scroll_profile_marriedStatus1.setText(model.getMarriedStatus());
//            }
//
//            if (model.getMemberInCome().equals("") || model.getMemberInCome().equalsIgnoreCase("")) {
//
//                tv_scroll_profile_saleryDetails.setVisibility(View.GONE);
//
//            } else {
//
//                tv_scroll_profile_saleryDetails.setVisibility(View.VISIBLE);
//                tv_scroll_profile_saleryDetails.setText(model.getMemberInCome());
//            }
//
//            if (model.getEducationInName().equals("") || model.getEducationInName().equalsIgnoreCase("")) {
//
//                tv_scroll_profile_ProfessionDetails.setVisibility(View.GONE);
//
//            } else {
//
//                tv_scroll_profile_ProfessionDetails.setVisibility(View.VISIBLE);
//                tv_scroll_profile_ProfessionDetails.setText(model.getEducationInName());
//            }
//        }
//
//        String city = "" + model.getMemberCity();
//        String country = "" + model.getMemberCountry();
//        String city_country;
//
//        if (city.equals("") || city.equalsIgnoreCase("")) {
//
//            city_country = "" + country;
//        } else {
//
//            city_country = city + "," + country;
//        }
//
//        if (city_country.equals("") || city_country.equalsIgnoreCase("")) {
//
//            tv_scroll_profile_AddressDetails.setVisibility(View.GONE);
//        } else {
//
//            tv_scroll_profile_AddressDetails.setVisibility(View.VISIBLE);
//            tv_scroll_profile_AddressDetails.setText(city_country);
//        }
//
//        String onlineTime = model.getOnlineTime();
//        String offlineTime = model.getOffline_Time();
//
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
//
//        Date onlineDate = null, offlineDate = null;
//
//        try {
//
//            if (!TextUtils.isEmpty(onlineTime) && onlineTime.length() > 5) {
//
//                onlineDate = formatter.parse(onlineTime);
//            }
//
//            if (!TextUtils.isEmpty(offlineTime) && offlineTime.length() > 5) {
//
//                offlineDate = formatter.parse(offlineTime);
//            }
//
//            assert onlineDate != null;
//            assert offlineDate != null;
//
//            if (onlineDate.compareTo(offlineDate) < 0) {
//
//                model.setOnline(false);
//
//            } else {
//
//                model.setOnline(true);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (model.isOnline()) {
//
//            tv_online.setVisibility(View.VISIBLE);
//            tv_online.setText("Online");
//            offlineLayout.setVisibility(View.GONE);
//        } else {
//
//            offlineLayout.setVisibility(View.VISIBLE);
//            tv_offline.setText("" + model.getOffline_Time());
//            tv_online.setVisibility(View.GONE);
//        }
//
//        img_scroll_profileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
////                    onListItemClicked(model);
////                    return;
////                }
//
//                Intent intent = new Intent(mContext, ProfileVisitorViewActivity.class);
//                intent.putExtra("PVisitedPosition", position);
//                ProfileVisitorViewActivity.memberName = model.getFirstName() + " " + model.getLastName();
//                mContext.startActivity(intent);
//            }
//        });
//
//        img_scroll_shortlist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    onListItemClicked(model);
//                    return;
//                }
//
//                int senderId = Utilities.getInt(model.getMemberId());
//                MatrimonyUtils.addShortlistTask(mContext, memberId, senderId, "1");
//
//                img_scroll_shortlisted.setVisibility(View.VISIBLE);
//                img_scroll_shortlist.setVisibility(View.GONE);
//
//                tv_shortlist.setVisibility(View.GONE);
//                tv_shortlisted.setVisibility(View.VISIBLE);
//            }
//        });
//
//        img_scroll_shortlisted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    onListItemClicked(model);
//                    return;
//                }
//
//                int senderId = Utilities.getInt(model.getMemberId());
//                MatrimonyUtils.addShortlistTask(mContext, memberId, senderId, "0");
//
//                img_scroll_shortlist.setVisibility(View.VISIBLE);
//                img_scroll_shortlisted.setVisibility(View.GONE);
//
//                tv_shortlist.setVisibility(View.VISIBLE);
//                tv_shortlisted.setVisibility(View.GONE);
//            }
//        });
//
//        img_scroll_invite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    onListItemClicked(model);
//                    return;
//                }
//
//                int senderId = Utilities.getInt(model.getMemberId());
//                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "1");
//
//
//                String name = mPreferences.getString("nav_memberName", "");
//                String imageUrl = mPreferences.getString("nav_profileUrl", "");
//
//                String message = "Invitation Received from " + name;
//
//                MatrimonyUtils.sendInvitationNotification(model.getDeviceId(), message, imageUrl,
//                        model.getMemberId(), String.valueOf(memberId), name);
//
//                img_invited.setVisibility(View.VISIBLE);
//                img_scroll_invite.setVisibility(View.GONE);
//
//                tv_invite.setVisibility(View.GONE);
//                tv_invited.setVisibility(View.VISIBLE);
//            }
//        });
//
//        img_invited.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    onListItemClicked(model);
//                    return;
//                }
//
//                int senderId = Utilities.getInt(model.getMemberId());
//                MatrimonyUtils.inviteTask(mContext, memberId, senderId, "0");
//
//                img_scroll_invite.setVisibility(View.VISIBLE);
//                img_invited.setVisibility(View.GONE);
//
//                tv_invite.setVisibility(View.VISIBLE);
//                tv_invited.setVisibility(View.GONE);
//            }
//        });
//
//        /*img_scroll_chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (recentlyProfileVisitorModelList.get(position).getInviteChatFlag().equals("1")) {
//
//                    mContext.startActivity(new Intent(mContext, ChatActivity.class));
//
//                    String name = recentlyProfileVisitorModelList.get(position).getFirstName() + " " + recentlyProfileVisitorModelList.get(position).getLastName();
//                    String imageUrl = recentlyProfileVisitorModelList.get(position).getUserProfileUrl();
//
//                    mPreferences.edit().putInt("messageReceiverId", recentlyProfileVisitorModelList.get(position).getUserMemberId()).apply();
//                    mPreferences.edit().putString("messageReceiverName", name).apply();
//                    mPreferences.edit().putString("messageReceiverImage", imageUrl).apply();
//                    mPreferences.edit().putString("receiverTokenId", recentlyProfileVisitorModelList.get(position).getTokenId()).apply();
//
//                } else {
//
//                    Toast.makeText(mContext, "Please accept the invitation", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });*/
//
//        img_scroll_chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    onListItemClicked(model);
//                    return;
//                }
//
//                mContext.startActivity(new Intent(mContext, ChatActivity.class));
//
//                String name = model.getFirstName() + " " + model.getLastName();
//                String imageUrl = model.getMainProfilePhoto();
//
//                mPreferences.edit().putInt("messageReceiverId", Utilities.getInt(model.getMemberId())).apply();
//                mPreferences.edit().putString("messageReceiverName", name).apply();
//                mPreferences.edit().putString("messageReceiverImage", imageUrl).apply();
//                mPreferences.edit().putString("receiverTokenId", model.getDeviceId()).apply();
//            }
//        });
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClicked(Object object) {
        android.app.AlertDialog.Builder db = new android.app.AlertDialog.Builder(mContext, R.style.MyAlertDialogStyle);
//        db.setView(dialog_layout);
        db.setTitle("Update subscription");
        db.setMessage(PaymentHelper.GET_PREMIUM);
        db.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                transactionDataPresenter.getTransactionData("" + memberId, mPackageId);
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
    public void showError(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
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
//        String result = data.getStringExtra("result");
//
//        String status;
////        result = EasebuzzConstants.PAYMENT_SUCCESSFUL;
//
//        /**
//         * Note:
//         * if not successful. Cancel is depicted by multiple string constants eg, user_cancelled, payment_failed, timeout etc
//         * for other constants check the documentation of easebuzz.in here:
//         * https://docs.easebuzz.in/mobile-integration-android/initiate-pymnt
//         */
//        if (result.equals(EasebuzzConstants.PAYMENT_SUCCESSFUL)) {
//            status = "1";
//            successfulTransactionCompletion();
//        } else {
//            status = "0";
//            transactionFailure();
//        }

//        transactionDataPresenter.updateTransactionData("" + memberId, "" + mPackageId,
//                mTransactionId, status);

        if (requestCode == PaymentActivity.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                setupViewPager(mViewPager);
            }
        }
    }
}