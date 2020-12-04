package in.co.vsys.myssksamaj.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.easebuzz.payment.kit.PWECouponsActivity;
import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CustomListAdapter;
import in.co.vsys.myssksamaj.contracts.RecentlyJointContractPaging;
import in.co.vsys.myssksamaj.contracts.TransactionDataContract;
import in.co.vsys.myssksamaj.fragments.ShowContactFragment;
import in.co.vsys.myssksamaj.helpers.PaymentHelper;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.interfaces.ListClickListener;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.RecentlyJointModel;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.presenters.TransactionPresenter;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.EasebuzzConstants;
import in.co.vsys.myssksamaj.utils.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import datamodels.StaticDataModel;

public class ListActivity extends AppCompatActivity implements
        ListClickListener, TransactionDataContract.TransactionView{
    private static final String MEMBER_ID = "MemberId";
    private static final String TAG = ListActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private List<RecentlyJointModel> recentlyJointModelList;
    private CustomListAdapter recyclerAdapter;
    private ImageView btn_back;
    private SharedPreferences mPreferences;
    private ProgressBar mProgressBar;

    private Context mContext;
    private Dialog dialog;
    private int mMemberId;
    private String mTransactionId = "", mPackageId = "1";
    private AlertDialog alertDialog;
    private TransactionDataContract.TransactionOps transactionDataPresenter;
    private String accountCreatedBy;

    private int recentlyJoinedPastVisibleItems, recentlyJoinedVisibleItemCount, recentlyJoinedTotalItemCount,
            recentlyJoinedPage = 0;
    private boolean recentlyJoinedLoading = false;
    private SharedPreferences mPreference;
    private int memberId;
    private RecentlyJointContractPaging.RecentlyJointPagingOps recentlyJointPresenter;
    private int mUserType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mContext = this;

        mRecyclerView = (RecyclerView) findViewById(R.id.list_recyclerviewRecently);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_recentlyLIst);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_visitedList);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Recently Joined");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recentlyJointModelList = new ArrayList<>();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mMemberId = mPreferences.getInt("memberId", 0);
        accountCreatedBy = mPreferences.getString("accountCreatedBY", "");
        mUserType = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.USERTYPE);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mPreference = PreferenceManager.getDefaultSharedPreferences(this);
        memberId = mPreference.getInt("memberId", 0);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) {
                    recentlyJoinedVisibleItemCount = mRecyclerView.getLayoutManager().getChildCount();
                    recentlyJoinedTotalItemCount = mRecyclerView.getLayoutManager().getItemCount();
                    recentlyJoinedPastVisibleItems = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                    if (!recentlyJoinedLoading) {
                        if ((recentlyJoinedVisibleItemCount + recentlyJoinedPastVisibleItems) >= recentlyJoinedTotalItemCount) {
                            recentlyJoinedLoading = true;
                            recentlyJoinedPage++;
                            loadServerData("" + memberId, "" + recentlyJoinedPage);
                           // recentlyJointPresenter.getRecentlyJoint("" + memberId, "" + recentlyJoinedPage);
                        }
                    }
                }
            }
        });

        loadServerData("" + memberId, "" + recentlyJoinedPage);

        transactionDataPresenter = new TransactionPresenter(this);
    }

    private void loadServerData(final String mID, final String pagingCount) {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.recentlyJointUrl_paging,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  Log.d(TAG, "recentlyJoint response" + response);
                        mProgressBar.setVisibility(View.INVISIBLE);

                        JSONArray jsonArray;
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    RecentlyJointModel recentlyJointModel = new RecentlyJointModel();

                                    int memberId = jsonObject1.getInt("MemberId");
                                    String uniqueId = jsonObject1.getString("UniqueId");
                                    String userAge = jsonObject1.getString("DOB");
                                    String userHeight = jsonObject1.getString("MemberHeight");
                                    String motherTongue = jsonObject1.getString("MotherTongue");
                                    String userProfileUrl = jsonObject1.getString("MainProfilePhoto");
                                    String userCity = jsonObject1.getString("MemberCity");
                                    String userCountry = jsonObject1.getString("MemberCountry");
                                    String userEductionIN = jsonObject1.getString("EducationInName");
                                    String userIncome = jsonObject1.getString("MemberInCome");
                                    String marriedStatus = jsonObject1.getString("MarriedStatus");
                                    String shortlist = jsonObject1.getString("Shorted");
                                    String invite = jsonObject1.getString("Invited");
                                    String firstName = jsonObject1.getString("FirstName");
                                    String lastName = jsonObject1.getString("LastName");
                                    String tokenId = jsonObject1.getString("DeviceId");
                                    String inviteChatFlag = jsonObject1.getString("IsChat");
                                    String onlinestatus = jsonObject1.getString("onlinestatus");
                                    String ispremium = jsonObject1.getString("ispremium");
                                    String SenderBlocked = jsonObject1.getString("SenderBlocked");

                                    recentlyJointModel.setInviteReceivedFlag(jsonObject1.getString("ReceiverFlag"));
                                    recentlyJointModel.setInviteChatFlag(inviteChatFlag);
                                    recentlyJointModel.setTokenId("" + tokenId);
                                    recentlyJointModel.setProfileCreatedBy("" + jsonObject1.getString("AccountCreatedBy"));
                                    recentlyJointModel.setOnlieTime(jsonObject1.getString("Online Time"));
                                    recentlyJointModel.setOfflineTime(jsonObject1.getString("Offline Time"));
                                    recentlyJointModel.setUserMemberId(memberId);
                                    recentlyJointModel.setUniqueId(uniqueId);
                                    recentlyJointModel.setDOB(userAge);
                                    recentlyJointModel.setUserHeight(userHeight);
                                    recentlyJointModel.setMotherTongue(motherTongue);
                                    recentlyJointModel.setUserProfileUrl(userProfileUrl);
                                    recentlyJointModel.setUserCity(userCity);
                                    recentlyJointModel.setUserCountry(userCountry);
                                    recentlyJointModel.setEductionIn(userEductionIN);
                                    recentlyJointModel.setUserIncome(userIncome);
                                    recentlyJointModel.setMarriedStatus(marriedStatus);
                                    recentlyJointModel.setShortlistedFlag(shortlist);
                                    recentlyJointModel.setInvitedFlag(invite);
                                    recentlyJointModel.setFirstName(firstName);
                                    recentlyJointModel.setLastName(lastName);
                                    recentlyJointModel.setOnlinestatus(onlinestatus);
                                    recentlyJointModel.setIspremium(ispremium);
                                    recentlyJointModel.setSenderBlocked(SenderBlocked);

                                    if(recentlyJointModel.getSenderBlocked().equalsIgnoreCase("0")){
                                        recentlyJointModelList.add(recentlyJointModel);
                                    }



                                }


                                recentlyJoinedLoading = false;
                                if (recyclerAdapter == null) {
                                recyclerAdapter = new CustomListAdapter(mContext, mRecyclerView,
                                        recentlyJointModelList, R.layout.recyclerview_row,
                                        new CustomListAdapter.ItemClickedListener() {
                                            @Override
                                            public void onViewBound(View view, Object object, int position) {
                                                showRecentlyJointData(view, (RecentlyJointModel) object, position);
                                            }

                                            @Override
                                            public void onItemClicked(View view, Object object, int position) {

                                            }
                                        });
                                mRecyclerView.setAdapter(recyclerAdapter);
                                } else
                                    recyclerAdapter.notifyDataSetChanged();
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
                        //   Log.d(TAG, "recently error " + error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(MEMBER_ID, String.valueOf(mID));
                params.put("page", String.valueOf(pagingCount));

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void showRecentlyJointData(View itemView, final RecentlyJointModel jointModel, final int position) {
        final ImageView profileUrl, img_invite, img_invited, img_shortlisted, img_shortlist, img_chat, img_accept;
        final TextView tv_shortlist, tv_shortlisted, tv_userId, tv_userAge_height, tv_motherTounge,
                tv_married_status, tv_profession, tv_income, tv_city_state_country, tv_invite, tv_invited;
        TextView tv_online, tv_offline, tv_accept;
        ImageView img_premium;

        img_premium=(ImageView)itemView.findViewById(R.id.img_premium);

        profileUrl = itemView.findViewById(R.id.img_scroll_profileImage);
        img_invite = itemView.findViewById(R.id.img_scroll_invite);
        img_accept = itemView.findViewById(R.id.img_scroll_accept);
        tv_accept = itemView.findViewById(R.id.tv_scroll_accept);
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
        tv_online = itemView.findViewById(R.id.tv_profileView_online);
        tv_offline = itemView.findViewById(R.id.tv_profileView_Offline);

        img_invite.setEnabled(true);

        if (accountCreatedBy.equals("P")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                img_invite.setImageAlpha(64);
            }

        } else if (accountCreatedBy.equals("C")) {

            img_accept.setVisibility(View.GONE);
            tv_accept.setVisibility(View.GONE);
            img_invite.setVisibility(View.GONE);
            tv_invite.setVisibility(View.GONE);

            if (jointModel.getInviteReceivedFlag().equals(String.valueOf(jointModel.getUserMemberId()))) {

                img_invite.setVisibility(View.GONE);
                tv_invite.setVisibility(View.GONE);
                img_invited.setVisibility(View.GONE);
                tv_invited.setVisibility(View.GONE);
                img_accept.setVisibility(View.VISIBLE);
                tv_accept.setVisibility(View.VISIBLE);

            } else {
                if (TextUtils.isEmpty(jointModel.getInvitedFlag()) || jointModel.getInvitedFlag().contains("0")) {

                    img_invite.setVisibility(View.VISIBLE);
                    img_invited.setVisibility(View.GONE);
                    tv_invite.setVisibility(View.VISIBLE);
                    tv_invited.setVisibility(View.GONE);
                    img_accept.setVisibility(View.GONE);
                    tv_accept.setVisibility(View.GONE);

                } else {

                    img_invited.setVisibility(View.VISIBLE);
                    img_invite.setVisibility(View.GONE);
                    tv_invite.setVisibility(View.GONE);
                    tv_invited.setVisibility(View.VISIBLE);
                    img_accept.setVisibility(View.GONE);
                    tv_accept.setVisibility(View.GONE);
                }
            }
        }



        if (TextUtils.isEmpty(jointModel.getShortlistedFlag()) || jointModel.getShortlistedFlag().contains("0")) {

            img_shortlist.setVisibility(View.VISIBLE);
            img_shortlisted.setVisibility(View.GONE);
            tv_shortlist.setVisibility(View.VISIBLE);
            tv_shortlisted.setVisibility(View.GONE);

        } else {

            img_shortlisted.setVisibility(View.VISIBLE);
            img_shortlist.setVisibility(View.GONE);
            tv_shortlist.setVisibility(View.GONE);
            tv_shortlisted.setVisibility(View.VISIBLE);
        }

        tv_userId.setText(jointModel.getUniqueId());

        if (jointModel.getProfileCreatedBy().equals("P")) {

            tv_userAge_height.setVisibility(View.VISIBLE);
            tv_userAge_height.setText("Profile managed by Parent");
            tv_motherTounge.setVisibility(View.GONE);
            tv_married_status.setVisibility(View.GONE);
            tv_profession.setVisibility(View.GONE);
            tv_income.setVisibility(View.GONE);


        } else if (jointModel.getProfileCreatedBy().equals("C")) {

            try {
                jointModel.setUserAge(MatrimonyUtils.getAge(jointModel.getDOB()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            String age1 = "" + jointModel.getUserAge();
            String height = "" + jointModel.getUserHeight();
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

            if (jointModel.getMotherTongue().equals("") || jointModel.getMotherTongue().equalsIgnoreCase("")) {

                tv_motherTounge.setVisibility(View.GONE);

            } else {

                tv_motherTounge.setVisibility(View.VISIBLE);
                tv_motherTounge.setText(jointModel.getMotherTongue());
            }

            if (jointModel.getMarriedStatus().equals("") || jointModel.getMarriedStatus().equalsIgnoreCase("")) {

                tv_married_status.setVisibility(View.GONE);

            } else {

                tv_married_status.setVisibility(View.VISIBLE);
                tv_married_status.setText(jointModel.getMarriedStatus());
            }

            if (jointModel.getEductionIn().equals("") || jointModel.getEductionIn().equalsIgnoreCase("")) {

                tv_profession.setVisibility(View.GONE);

            } else {

                tv_profession.setVisibility(View.VISIBLE);
                tv_profession.setText(jointModel.getEductionIn());
            }

            if (jointModel.getUserIncome().equals("") || jointModel.getUserIncome().equalsIgnoreCase("")) {

                tv_income.setVisibility(View.GONE);

            } else {

                tv_income.setVisibility(View.VISIBLE);
                tv_income.setText(jointModel.getUserIncome());
            }
        }

        String city = "" + jointModel.getUserCity();
        String country = "" + jointModel.getUserCountry();
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

        if (jointModel.getUserProfileUrl().isEmpty()) {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(profileUrl);
        } else {

            Picasso.get()
                    .load(jointModel.getUserProfileUrl())
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(profileUrl);
        }

        if(jointModel.getIspremium().equalsIgnoreCase("0")){

            img_premium.setVisibility(View.GONE);

        }else {
            img_premium.setVisibility(View.VISIBLE);
        }

        profileUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
////                    onListItemClicked(jointModel);
////                    return;
////                }


                int actualPositionInList=getActualPosition(jointModel.getUserMemberId());
                Intent intent = new Intent(mContext, MatrimonyProfileViewActivity.class);
                intent.putExtra("position", getPosition(actualPositionInList));
                intent.putExtra("pageCount",getPage(actualPositionInList));
                intent.putExtra("memberId",jointModel.getUserMemberId());

                Log.e("dataPPP",actualPositionInList+"::::"+getPosition(actualPositionInList)+"::"+getPage(actualPositionInList));

                intent.putExtra("flagHomeActivity", 2);
                MatrimonyProfileViewActivity.userName = jointModel.getFirstName() + " " + jointModel.getLastName();

                mContext.startActivity(intent);
            }
        });

        img_shortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    onListItemClicked(jointModel);
//                    return;
//                }

                int senderId = jointModel.getUserMemberId();
                MatrimonyUtils.addShortlistTask(mContext, mMemberId, senderId, "1");

                img_shortlisted.setVisibility(View.VISIBLE);
                img_shortlist.setVisibility(View.GONE);

                tv_shortlist.setVisibility(View.GONE);
                tv_shortlisted.setVisibility(View.VISIBLE);

            }
        });

        img_shortlisted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    onListItemClicked(jointModel);
//                    return;
//                }

                int senderId = jointModel.getUserMemberId();
                MatrimonyUtils.addShortlistTask(mContext, mMemberId, senderId, "0");

                img_shortlist.setVisibility(View.VISIBLE);
                img_shortlisted.setVisibility(View.GONE);

                tv_shortlist.setVisibility(View.VISIBLE);
                tv_shortlisted.setVisibility(View.GONE);

            }
        });

        img_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
//                    onListItemClicked(jointModel);
//                    return;
//                }

                if (accountCreatedBy.equals("P")) {

                    Toast.makeText(mContext, "You can not invite Candidate profile ", Toast.LENGTH_LONG).show();
                } else {

                    int senderId = jointModel.getUserMemberId();
                    MatrimonyUtils.inviteTask(mContext, mMemberId, senderId, "1");

                    String name = mPreferences.getString("nav_memberName", "");
                    String imageUrl = mPreferences.getString("nav_profileUrl", "");
                    String message = "Invitation Received from " + name;

                    MatrimonyUtils.sendInvitationNotification(jointModel.getTokenId(), message,
                            imageUrl, String.valueOf(jointModel.getUserMemberId()), String.valueOf(mMemberId), name);

                    img_invited.setVisibility(View.VISIBLE);
                    img_invite.setVisibility(View.GONE);

                    tv_invite.setVisibility(View.GONE);
                    tv_invited.setVisibility(View.VISIBLE);
                }
            }
        });

        img_invited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                    onListItemClicked(jointModel);
                    return;
                }

                int senderId = jointModel.getUserMemberId();
                MatrimonyUtils.inviteTask(mContext, mMemberId, senderId, "0");

                img_invite.setVisibility(View.VISIBLE);
                img_invited.setVisibility(View.GONE);

                tv_invite.setVisibility(View.VISIBLE);
                tv_invited.setVisibility(View.GONE);
            }
        });

       /* img_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list.get(position).getInviteChatFlag().equals("1")) {

                    mContext.startActivity(new Intent(mContext, ChatActivity.class));

                    String name = list.get(position).getFirstName() + " " + list.get(position).getLastName();
                    String imageUrl = list.get(position).getUserProfileUrl();

                    mPreferences.edit().putInt("messageReceiverId", list.get(position).getUserMemberId()).apply();
                    mPreferences.edit().putString("messageReceiverName", name).apply();
                    mPreferences.edit().putString("messageReceiverImage", imageUrl).apply();
                    mPreferences.edit().putString("receiverTokenId", list.get(position).getTokenId()).apply();
                } else {

                    // Toast.makeText(mContext, "Please accept the invitation", Toast.LENGTH_SHORT).show();
                    matrimonyUtils.displayInvitationAlert(mContext);
                }
            }
        });*/

        img_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUserType==1){

                    if (!MainPresenter.getInstance().isPremiumMember(mContext)) {
                        onListItemClicked(jointModel);
                        return;
                    }

                    mContext.startActivity(new Intent(mContext, ChatActivity.class));

                    String name = jointModel.getFirstName() + " " + jointModel.getLastName();
                    String imageUrl = jointModel.getUserProfileUrl();

                    mPreferences.edit().putInt("messageReceiverId", jointModel.getUserMemberId()).apply();
                    mPreferences.edit().putString("messageReceiverName", name).apply();
                    mPreferences.edit().putString("messageReceiverImage", imageUrl).apply();
                    mPreferences.edit().putString("receiverTokenId", jointModel.getTokenId()).apply();

                }else {
                    final Dialog dialogChoice = new Dialog(ListActivity.this);
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
                            Intent intent=new Intent(ListActivity.this,DonationActivity.class);
                            startActivity(intent);

                        }
                    });

                }


            }
        });

        String onlineTime = jointModel.getOnlieTime();
        String offlineTime = jointModel.getOfflineTime();

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

            if (onlineDate.compareTo(offlineDate) < 0) {

                jointModel.setOnline(false);

            } else {
                jointModel.setOnline(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (jointModel.getOnlinestatus().equalsIgnoreCase("online")) {
            tv_online.setText("Online");
            tv_online.setVisibility(View.VISIBLE);
            tv_offline.setVisibility(View.GONE);
//            offlineLayout.setVisibility(View.GONE);
        } else {
//            offlineLayout.setVisibility(View.VISIBLE);
            tv_offline.setText("Offline");
            tv_offline.setVisibility(View.VISIBLE);
            tv_online.setVisibility(View.GONE);
        }
    }


    private int getPosition(int actualPositionInList) {
        if(recentlyJoinedPage>0){
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

    private int getActualPosition(int userMemberId) {
        int pos=0;
        for (int i=0;i<recentlyJointModelList.size();i++){
            if(userMemberId==recentlyJointModelList.get(i).getUserMemberId()){
                pos=i;
                break;
            }
        }
        return pos;
    }

    private int getPage(int actualPositionInList) {
        if(recentlyJoinedPage>0){
            return actualPositionInList/10;
        }else {
            return 0;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
//                startActivity(new Intent(ListActivity.this, HomeActivity.class));
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
            MainPresenter.getInstance().setUserType(mContext,1);
            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transactionFailure() {
        try {
            mPreferences.edit().putInt("userType", 0).apply();
            MainPresenter.getInstance().setUserType(mContext,0);
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

        transactionDataPresenter.updateTransactionData("" + mMemberId, "" + mPackageId,
                mTransactionId, status);
    }


}
