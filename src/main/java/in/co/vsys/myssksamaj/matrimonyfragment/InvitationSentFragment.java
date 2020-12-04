package in.co.vsys.myssksamaj.matrimonyfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

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
import in.co.vsys.myssksamaj.activities.InvitationDisplayActivity;
import in.co.vsys.myssksamaj.adapter.CustomListAdapter;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.InvitationModel;
import in.co.vsys.myssksamaj.utils.Config;
import in.co.vsys.myssksamaj.utils.Utilities;
import in.co.vsys.myssksamaj.utils.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvitationSentFragment extends Fragment {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private List<InvitationModel> invitationModelList = new ArrayList<>();
    private CustomListAdapter invitationAdapter;
    private static final String MEMBER_ID = "MemberId";
    private int memberId;

    public InvitationSentFragment() {
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
        View view = inflater.inflate(R.layout.fragment_invitation_sent, container, false);

        mRecyclerView = view.findViewById(R.id.invitationSent_recyclerView);
        mProgressBar = view.findViewById(R.id.invitationSent_progressbar);
        memberId = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.MEMBER_ID);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        loadServerData();

        return view;
    }


    private void loadServerData() {
        mProgressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.invitationSendByMeUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("invitation sent list", response);
                        mProgressBar.setVisibility(View.INVISIBLE);

                        JSONArray jsonArray;
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            int success = jsonObject.getInt("success");

                            if (success == 1) {

                                jsonArray = jsonObject.getJSONArray("data");
                                invitationModelList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    InvitationModel invitationModel = new InvitationModel();

                                    invitationModel.setMemberId(jsonObject1.getInt("MemberId"));
                                    invitationModel.setUniqueId(jsonObject1.getString("UniqueId"));
                                    invitationModel.setDateOfBirth(jsonObject1.getString("DOB"));
                                    invitationModel.setFirstName(jsonObject1.getString("FirstName"));
                                    invitationModel.setLastName(jsonObject1.getString("LastName"));
                                    invitationModel.setUserHeight(jsonObject1.getString("MemberHeight"));
                                    invitationModel.setMotherTounge(jsonObject1.getString("MotherTongue"));
                                    invitationModel.setEductionIn(jsonObject1.getString("EducationInName"));
                                    invitationModel.setUserProfileUrl(jsonObject1.getString("MainProfilePhoto"));
                                    invitationModel.setUserCity(jsonObject1.getString("MemberCity"));
                                    invitationModel.setUserCountry(jsonObject1.getString("MemberCountry"));
                                    invitationModel.setProfileCreatedBy(jsonObject1.getString("AccountCreatedBy"));

                                    invitationModelList.add(invitationModel);
                                }

                                invitationAdapter = new CustomListAdapter(mContext, mRecyclerView,
                                        invitationModelList, R.layout.row_invitation_list,
                                        new CustomListAdapter.ItemClickedListener() {
                                            @Override
                                            public void onViewBound(View view, Object object, int position) {
                                                showInvitationData(view, (InvitationModel) object, position);
                                            }

                                            @Override
                                            public void onItemClicked(View view, Object object, int position) {

                                            }
                                        });
                                mRecyclerView.setAdapter(invitationAdapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("invitation sent error ", error.toString());
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(MEMBER_ID, String.valueOf(memberId));

                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void showInvitationData(View itemView, final InvitationModel invitationModel, final int position) {

        ImageView img_profile, img_close;
        AppCompatTextView tv_uniqueId, tv_userAge, tv_userEduction, tv_Usercity_country;
        RelativeLayout mLinearLayout;
        LinearLayout invitationLayout, mInvitationReceivedLayout;

        img_profile = itemView.findViewById(R.id.invitation_imageView);
        tv_uniqueId = itemView.findViewById(R.id.tv_invitation_memberId);
        tv_userAge = itemView.findViewById(R.id.tv_invitation_age_height_language);
        tv_userEduction = itemView.findViewById(R.id.tv_invitation_workingWith);
        tv_Usercity_country = itemView.findViewById(R.id.tv_invitation_city_country);
        mLinearLayout = itemView.findViewById(R.id.linearLayout_invitation);
        invitationLayout = itemView.findViewById(R.id.layout_invitation_sent);
        mInvitationReceivedLayout = itemView.findViewById(R.id.layout_invitation_received);
        img_close = itemView.findViewById(R.id.img_invitation_close);

        if (invitationModel.getUserProfileUrl().isEmpty()) {

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

        memberId = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.MEMBER_ID);

        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, InvitationDisplayActivity.class);
                intent.putExtra("position", position);
                InvitationDisplayActivity.memberName = invitationModel.getFirstName() + " " + invitationModel.getLastName();
                mContext.startActivity(intent);
            }
        });

        String accountCreatedBy = invitationModel.getProfileCreatedBy();
        invitationLayout.setVisibility(View.VISIBLE);
        mInvitationReceivedLayout.setVisibility(View.GONE);
        tv_uniqueId.setText("" + invitationModel.getUniqueId());

        if (accountCreatedBy.equals("P")) {

            tv_userAge.setVisibility(View.VISIBLE);
            tv_userAge.setText("Profile managed by Parent");
            tv_userEduction.setVisibility(View.GONE);

        } else if (accountCreatedBy.equals("C")) {

            invitationModel.setUserAge(MatrimonyUtils.getAge(invitationModel.getDateOfBirth()));
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

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int senderId = invitationModelList.get(position).getMemberId();
                removeRecyclerItem(position, memberId, senderId, "0");
            }
        });
    }

    public void removeRecyclerItem(int position, int memberId, int receiverId, String status) {

        MatrimonyUtils.inviteTask(mContext, memberId, receiverId, status);
        // invitationAdapter.notifyDataSetChanged();
        invitationModelList.remove(position);
        invitationAdapter.notifyItemRemoved(position);
        invitationAdapter.notifyItemRangeChanged(position, invitationModelList.size());
    }
}