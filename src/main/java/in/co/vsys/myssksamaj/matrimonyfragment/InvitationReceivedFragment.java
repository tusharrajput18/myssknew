package in.co.vsys.myssksamaj.matrimonyfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.InvitationReceivedDisplayActivity;
import in.co.vsys.myssksamaj.adapter.CustomListAdapter;
import in.co.vsys.myssksamaj.contracts.AcceptInvitationContract;
import in.co.vsys.myssksamaj.contracts.RequestReceivedContract;
import in.co.vsys.myssksamaj.helpers.ImageHelper;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.presenters.AcceptInvitationPresenter;
import in.co.vsys.myssksamaj.presenters.RequestReceivedPresenter;
import in.co.vsys.myssksamaj.utils.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvitationReceivedFragment extends Fragment implements RequestReceivedContract.RequestReceivedView,
        AcceptInvitationContract.AcceptInvitationView {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private List<UserProfileModel> mUserProfileModels = new ArrayList<>();
    private CustomListAdapter invitationAdapter;
    private int memberId;

    //presenters
    private RequestReceivedContract.RequestReceivedOps requestReceivedPresenter;
    private AcceptInvitationContract.AcceptInvitationOps acceptInvitationPresenter;

    public InvitationReceivedFragment() {
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

        View inviteReceived = inflater.inflate(R.layout.fragment_invitation_received, container, false);

        mRecyclerView = inviteReceived.findViewById(R.id.invitationReceived_recyclerView);
        mProgressBar = inviteReceived.findViewById(R.id.invitationReceived_progressbar);

        memberId = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.MEMBER_ID);

        acceptInvitationPresenter = new AcceptInvitationPresenter(this);
        requestReceivedPresenter = new RequestReceivedPresenter(this);
        requestReceivedPresenter.getRequestReceived("" + memberId);

        return inviteReceived;
    }

    private void showInvitationData(View itemView, final UserProfileModel userProfileModel, final int position) {
        ImageView img_profile, img_close;
        AppCompatTextView tv_uniqueId, tv_userAge, tv_userEduction, tv_Usercity_country;
        RelativeLayout mLinearLayout;
        LinearLayout mInvitationReceivedLayout, mInvitationSent;

        img_profile = itemView.findViewById(R.id.invitation_imageView);
        tv_uniqueId = itemView.findViewById(R.id.tv_invitation_memberId);
        tv_userAge = itemView.findViewById(R.id.tv_invitation_age_height_language);
        tv_userEduction = itemView.findViewById(R.id.tv_invitation_workingWith);
        tv_Usercity_country = itemView.findViewById(R.id.tv_invitation_city_country);
        mLinearLayout = itemView.findViewById(R.id.linearLayout_invitation);
        mInvitationReceivedLayout = itemView.findViewById(R.id.layout_invitation_received);
        mInvitationSent = itemView.findViewById(R.id.layout_invitation_sent);
        img_close = itemView.findViewById(R.id.img_invitation_close);

        Utilities.setDataAndVisibility(tv_uniqueId, userProfileModel.getUniqueId());
        Utilities.setDataAndVisibility(tv_userAge, MatrimonyUtils.getAge(userProfileModel.getDOB()));
        Utilities.setDataAndVisibility(tv_userEduction, userProfileModel.getMemberEducationName());
        Utilities.setDataAndVisibility(tv_Usercity_country, userProfileModel.getMemberCity()
                + ", " + userProfileModel.getMemberCountry());

        mInvitationReceivedLayout.setVisibility(View.VISIBLE);

        ImageHelper.showImage(img_profile, userProfileModel.getMainProfilePhoto(), R.drawable.circle_preview);

        LinearLayout acceptInvitationContainer = itemView.findViewById(R.id.accept_invitation_container);
        LinearLayout cancelInvitationContainer = itemView.findViewById(R.id.cancel_invitation_container);

        acceptInvitationContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptInvitationPresenter.acceptInvitation("" + userProfileModel.getMemberId(),
                        "" + memberId, "1");

                mUserProfileModels.remove(position);
                invitationAdapter.notifyItemRemoved(position);
                invitationAdapter.notifyDataSetChanged();
            }
        });

        cancelInvitationContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptInvitationPresenter.acceptInvitation("" + userProfileModel.getMemberId(),
                        "" + memberId, "0");

                mUserProfileModels.remove(position);
                invitationAdapter.notifyItemRemoved(position);
                invitationAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showRequestReceived(List<UserProfileModel> userProfileModels) {
        mUserProfileModels.clear();
        mUserProfileModels.addAll(userProfileModels);

        invitationAdapter = new CustomListAdapter(mContext, mRecyclerView,
                mUserProfileModels, R.layout.row_invitation_list, new CustomListAdapter.ItemClickedListener() {
            @Override
            public void onViewBound(View view, Object object, int position) {
                showInvitationData(view, (UserProfileModel) object, position);
            }

            @Override
            public void onItemClicked(View view, Object object, int position) {
                UserProfileModel userProfileModel = (UserProfileModel) object;
                Intent intent = new Intent(mContext, InvitationReceivedDisplayActivity.class);
                intent.putExtra("position", position);
                InvitationReceivedDisplayActivity.memberName = userProfileModel.getFirstName() + " " + userProfileModel.getLastName();

                mContext.startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(invitationAdapter);
        invitationAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showInvitationAccepted(String message) {

    }
}