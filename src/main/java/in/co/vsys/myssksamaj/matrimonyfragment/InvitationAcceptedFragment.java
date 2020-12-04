package in.co.vsys.myssksamaj.matrimonyfragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.DonationActivity;
import in.co.vsys.myssksamaj.activities.InvitationAcceptActivity;
import in.co.vsys.myssksamaj.activities.InvitationDisplayActivity;
import in.co.vsys.myssksamaj.activities.ChatActivity;
import in.co.vsys.myssksamaj.adapter.CustomListAdapter;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.model.InvitationModel;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.Utilities;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvitationAcceptedFragment extends Fragment {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private List<InvitationModel> invitationModelList = new ArrayList<>();
    private CustomListAdapter invitationAdapter;
    private static final String MEMBER_ID = "MemberId";
    private SharedPreferences mPreferences;
    private int memberId;
    private int mUserType;


    public InvitationAcceptedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inviteAccepted = inflater.inflate(R.layout.fragment_invitation_accepted, container, false);
        mRecyclerView = inviteAccepted.findViewById(R.id.invitationAccepted_recyclerView);
        mProgressBar = inviteAccepted.findViewById(R.id.invitationAccepted_progressbar);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserType = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.USERTYPE);
        memberId = mPreferences.getInt("memberId", 0);

        loadInvitationAccepted();

        return inviteAccepted;

    }

    private void loadInvitationAccepted() {

        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.matrimony_invitation_accepted_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mProgressBar.setVisibility(View.INVISIBLE);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray;

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");
                                invitationModelList.clear();

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    InvitationModel invitationModel = new InvitationModel();

                                    invitationModel.setFirstName(jsonObject1.getString("FirstName"));
                                    invitationModel.setLastName(jsonObject1.getString("LastName"));
                                    invitationModel.setMemberId(jsonObject1.getInt("MemberId"));
                                    invitationModel.setUniqueId(jsonObject1.getString("UniqueId"));
                                    invitationModel.setDateOfBirth(jsonObject1.getString("DOB"));
                                    invitationModel.setUserHeight(jsonObject1.getString("MemberHeight"));
                                    invitationModel.setMotherTounge(jsonObject1.getString("MotherTongue"));
                                    invitationModel.setEductionIn(jsonObject1.getString("EducationInName"));
                                    invitationModel.setUserProfileUrl(jsonObject1.getString("MainProfilePhoto"));
                                    invitationModel.setUserCity(jsonObject1.getString("MemberCity"));
                                    invitationModel.setUserCountry(jsonObject1.getString("MemberCountry"));
                                    invitationModel.setProfileCreatedBy(jsonObject1.getString("AccountCreatedBy"));
                                    invitationModel.setTokenId(jsonObject1.getString("DeviceId"));

                                    invitationModelList.add(invitationModel);

                                }

                                invitationAdapter = new CustomListAdapter(getActivity(), mRecyclerView, invitationModelList,
                                        R.layout.row_layout_inviation_accept, new CustomListAdapter.ItemClickedListener() {
                                    @Override
                                    public void onViewBound(View view, Object object, int position) {
                                        InvitationModel invitationModel = (InvitationModel) object;
                                        showInvitationData(view, invitationModel, position);
                                    }

                                    @Override
                                    public void onItemClicked(View view, Object object, int position) {
                                        InvitationModel invitationModel = (InvitationModel) object;

                                        Intent intent = new Intent(mContext, InvitationAcceptActivity.class);
                                        intent.putExtra("position", position);
                                        Log.e("postion",""+position);
                                        InvitationAcceptActivity.memberName = invitationModel.getFirstName() + " " + invitationModel.getLastName();
                                        mContext.startActivity(intent);
                                    }
                                });
                                mRecyclerView.setAdapter(invitationAdapter);
                                invitationAdapter.notifyDataSetChanged();

                            } else {

                                Toast.makeText(getActivity(), "No list available...", Toast.LENGTH_SHORT).show();
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
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> param = new HashMap<>();
                param.put(MEMBER_ID, String.valueOf(memberId));

                return param;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void showInvitationData(View itemView, final InvitationModel invitationModel, final int position) {

        final ImageView img_profile, img_chat;
        TextView tv_uniqueId, tv_userAge, tv_userEduction, tv_Usercity_country;

        img_profile = itemView.findViewById(R.id.invitation_imageView);
        img_chat = itemView.findViewById(R.id.img_invitation_chat);
        tv_uniqueId = itemView.findViewById(R.id.tv_invitation_memberId);
        tv_userAge = itemView.findViewById(R.id.tv_invitation_age_height_language);
        tv_userEduction = itemView.findViewById(R.id.tv_invitation_workingWith);
        tv_Usercity_country = itemView.findViewById(R.id.tv_invitation_city_country);

        if (Utilities.isEmpty(invitationModel.getUserProfileUrl())) {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .placeholder(R.drawable.img_preview)
                    .into(img_profile);
        } else {

            Picasso.get()
                    .load(invitationModel.getUserProfileUrl())
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(img_profile);
        }

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, InvitationAcceptActivity.class);
                intent.putExtra("position", position);
                Log.e("postion",""+position);
                InvitationAcceptActivity.memberName = invitationModel.getFirstName() + " " + invitationModel.getLastName();
                mContext.startActivity(intent);

            }
        });

        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        memberId = mPreferences.getInt("memberId", 0);
        String accountCreatedBy = invitationModel.getProfileCreatedBy();

        tv_uniqueId.setText("" + invitationModel.getUniqueId());

        if (accountCreatedBy.equals("P")) {

            tv_userAge.setVisibility(View.VISIBLE);
            tv_userAge.setText("Profile managed by Parent");
            tv_userEduction.setVisibility(View.GONE);

        } else if (accountCreatedBy.equals("C")) {


            int year = Calendar.getInstance().get(Calendar.YEAR);

            String dob = invitationModel.getDateOfBirth();

            String[] dob1 = dob.split("/");

            String dateOfBirth = dob1[2];

            int age = (year - Integer.parseInt(dateOfBirth));

            invitationModel.setUserAge("" + age);

            String motherTougue = invitationModel.getMotherTounge();
            String height = invitationModel.getUserHeight();

            tv_userAge.setText("" + invitationModel.getUserAge() + ", " + height + ", " + motherTougue);


            if (invitationModel.getEductionIn().equals("") || invitationModel.getEductionIn().equalsIgnoreCase("")) {

                tv_userEduction.setVisibility(View.GONE);
            } else {
                tv_userEduction.setText(invitationModel.getEductionIn());
            }
        }

        String city = "" + invitationModel.getUserCity();
        String country = "" + invitationModel.getUserCountry();
        String city_country;

        if (city.equals("") || city.equalsIgnoreCase("")) {

            city_country = "" + country;
        } else {

            city_country = city + "," + country;
        }


        if (city_country.equals("") || city_country.equalsIgnoreCase("")) {

            tv_Usercity_country.setVisibility(View.GONE);
        } else {

            tv_Usercity_country.setVisibility(View.VISIBLE);
            tv_Usercity_country.setText("" + city_country);
        }

        img_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mUserType==1) {
                    mContext.startActivity(new Intent(mContext, ChatActivity.class));

                    String name = invitationModel.getFirstName() + " " + invitationModel.getLastName();
                    String imageUrl = invitationModel.getUserProfileUrl();

                    SharedPrefsHelper.getInstance(mContext).saveIntVal(SharedPrefsHelper.MESSAGE_RECEIVER_ID, invitationModel.getMemberId());
                    SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_NAME, name);
                    SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.MESSAGE_RECEIVER_IMAGE, imageUrl);
                    SharedPrefsHelper.getInstance(mContext).saveStringVal(SharedPrefsHelper.RECEIVER_TOKEN_ID, invitationModel.getTokenId());
                }else {
                    final Dialog dialogChoice = new Dialog(mContext);
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
                            Intent intent=new Intent(mContext, DonationActivity.class);
                            mContext.startActivity(intent);

                        }
                    });
                }
            }
        });
    }
}