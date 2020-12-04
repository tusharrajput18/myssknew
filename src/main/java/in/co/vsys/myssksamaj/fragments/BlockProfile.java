package in.co.vsys.myssksamaj.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.ChatActivity;
import in.co.vsys.myssksamaj.activities.PaymentActivity;
import in.co.vsys.myssksamaj.activities.ProfileVisitorViewActivity;
import in.co.vsys.myssksamaj.adapter.CustomListAdapter;
import in.co.vsys.myssksamaj.contracts.BlockContract;
import in.co.vsys.myssksamaj.contracts.BlockListContract;
import in.co.vsys.myssksamaj.contracts.GetVisitorsByRoleContract;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.matrimonyutils.MatrimonyUtils;
import in.co.vsys.myssksamaj.model.data_models.UserProfileModel;
import in.co.vsys.myssksamaj.model.responses.BlockListModelRes;
import in.co.vsys.myssksamaj.presenters.BlockListPresenter;
import in.co.vsys.myssksamaj.presenters.BlockPresenter;
import in.co.vsys.myssksamaj.presenters.GetVisitorsByRolePresenter;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.utils.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlockProfile extends Fragment implements BlockContract.BlockView, BlockListContract.GetBlockListView {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private List<BlockListModelRes> profileVisitorModelList = new ArrayList<>();
    private GetVisitorsByRoleContract.GetVisitorsOps getVisitorsPresenter;
    private CustomListAdapter profileVisitedAdapter;
    private int memberId = 0, packageId = 1;
    private BlockContract.BlockOps blockPresenter;
    private boolean isBlocked = false;
    private static final String TAG = BlockProfile.class.getSimpleName();


    private BlockListContract.BlockListOps getBlockListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_profile_visit, container, false);
        mContext = getActivity();

        memberId = SharedPrefsHelper.getInstance(mContext).getIntVal(SharedPrefsHelper.MEMBER_ID);
        initUI(view);
       /* getVisitorsPresenter = new GetVisitorsByRolePresenter(this);
        getVisitorsPresenter.getVisitorsByRole("" + memberId, "P");*/
        blockPresenter = new BlockPresenter(this);

        getBlockListView=new BlockListPresenter(this);
        getBlockListView.getBlockListByMemberId(String.valueOf(memberId));

        return view;
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
    }

    private void initUI(View view) {
        mRecyclerView = view.findViewById(R.id.profileVisitedList);
    }

    private void showProfileVisitedData(View itemView, final BlockListModelRes model, final int position) {
        final ImageView img_scroll_profileImage, img_accept;
        final TextView tv_scroll_memberId, tv_scroll_profile_otherDetails, tv_scroll_profile_languageDetails, tv_scroll_profile_ProfessionDetails, tv_scroll_profile_saleryDetails, tv_scroll_profile_marriedStatus1, tv_scroll_profile_AddressDetails, tv_invite, tv_invited, tv_shortlist, tv_shortlisted;
        final ImageView img_scroll_invite, img_invited, img_scroll_shortlist, img_scroll_shortlisted, img_scroll_chat;
        final TextView tv_online, tv_offline, tv_accept;
        LinearLayout offlineLayout;
        final ImageView blocimageview;
        final LinearLayout block_overlay;
        Button unblock;
        TextView tv_scroll_blocked_name;

        img_scroll_profileImage = itemView.findViewById(R.id.img_scroll_profileImage);
        blocimageview = itemView.findViewById(R.id.blocimageview);
        tv_scroll_memberId = itemView.findViewById(R.id.tv_scroll_memberId);
        img_accept = itemView.findViewById(R.id.img_scroll_accept);
        tv_accept = itemView.findViewById(R.id.tv_scroll_accept);
        tv_scroll_profile_otherDetails = itemView.findViewById(R.id.tv_scroll_profile_otherDetails);
        tv_scroll_profile_languageDetails = itemView.findViewById(R.id.tv_scroll_profile_languageDetails);
        tv_scroll_profile_ProfessionDetails = itemView.findViewById(R.id.tv_scroll_profile_ProfessionDetails);
        tv_scroll_profile_saleryDetails = itemView.findViewById(R.id.tv_scroll_profile_saleryDetails);
        tv_scroll_profile_marriedStatus1 = itemView.findViewById(R.id.tv_scroll_profile_marriedStatus1);
        tv_scroll_profile_AddressDetails = itemView.findViewById(R.id.tv_scroll_profile_AddressDetails);
        img_scroll_invite = itemView.findViewById(R.id.img_scroll_invite);
        img_invited = itemView.findViewById(R.id.img_scroll_invited);
        img_scroll_shortlist = itemView.findViewById(R.id.img_scroll_shortlist);
        img_scroll_shortlisted = itemView.findViewById(R.id.img_scroll_shortlisted);
        img_scroll_chat = itemView.findViewById(R.id.img_scroll_chat);
        tv_shortlist = itemView.findViewById(R.id.tv_scroll_shortlist);
        tv_shortlisted = itemView.findViewById(R.id.tv_scroll_shortlisted);
        tv_invite = itemView.findViewById(R.id.tv_scroll_invite);
        tv_invited = itemView.findViewById(R.id.tv_scroll_invited);
        tv_online = itemView.findViewById(R.id.tv_profileView_online);
        tv_offline = itemView.findViewById(R.id.tv_profileView_Offline);
        block_overlay = itemView.findViewById(R.id.block_overlay);
        unblock = itemView.findViewById(R.id.unblock);

        tv_scroll_blocked_name = itemView.findViewById(R.id.tv_scroll_blocked_name);


        if(model.getBlockedstatus().equalsIgnoreCase("1")){
            blocimageview.setVisibility(View.GONE);
            block_overlay.setVisibility(View.VISIBLE);
        }
        else {
            blocimageview.setVisibility(View.VISIBLE);
            block_overlay.setVisibility(View.GONE);
        }

        if (model.getSelfphoto().isEmpty()) {

            Picasso.get()
                    .load(R.drawable.img_preview)
                    .placeholder(R.drawable.img_preview)
                    .error(R.drawable.img_preview)
                    .into(img_scroll_profileImage);
        } else {

            Picasso.get()
                    .load( model.getSelfphoto())
                    .placeholder(R.drawable.img_preview)
                    .into(img_scroll_profileImage);
        }

        tv_scroll_memberId.setText(model.getUnqid());

        tv_scroll_blocked_name.setText(model.getName());

       blocimageview.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               blockDialog();
           }
           private void blockDialog() {
               AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme);
               alertDialog.setTitle(R.string.app_name);
               alertDialog.setIcon(R.mipmap.ic_launcher);
               alertDialog.setMessage("Do you want to block " + model.getName() + "?");

               alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                       isBlocked = true;
                       model.setBlockedstatus("1");
                       blockPresenter.updateBlockStatus(memberId, Integer.parseInt(model.getMemberId()), 1);
                       block_overlay.setVisibility(View.VISIBLE);
                       blocimageview.setVisibility(View.GONE);
                   }
               });

               alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                   }
               });
               alertDialog.show();
           }
       });


        unblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBlocked = false;
                model.setBlockedstatus("0");
                block_overlay.setVisibility(View.GONE);
                blockPresenter.updateBlockStatus(memberId, Integer.parseInt(model.getMemberId()), 0);
                blocimageview.setVisibility(View.VISIBLE);

            }
        });


     /*   blocimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(mContext, "" + model.getMemberId(), Toast.LENGTH_SHORT).show();
                blockDialog();

                img_scroll_profileImage.setEnabled(false);
                img_scroll_shortlist.setEnabled(false);
                img_scroll_shortlisted.setEnabled(false);
                img_scroll_invite.setEnabled(false);
                img_invited.setEnabled(false);
                img_scroll_chat.setEnabled(false);
            }*/

            /*private void blockDialog() {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme);
                alertDialog.setTitle(R.string.app_name);
                alertDialog.setIcon(R.mipmap.ic_launcher);
                alertDialog.setMessage("Do you want to block " + model.getFirstName() + "?");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        isBlocked = true;
                        model.setBlocked("1");
                        blockPresenter.updateBlockStatus(memberId, Integer.parseInt(model.getMemberId()), 1);
                        block_overlay.setVisibility(View.VISIBLE);
                        blocimageview.setVisibility(View.GONE);

                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
*/
      /*  unblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBlocked = false;

                model.setBlocked("0");
                block_overlay.setVisibility(View.GONE);
                blockPresenter.updateBlockStatus(memberId, Integer.parseInt(model.getMemberId()), 0);
                blocimageview.setVisibility(View.VISIBLE);

                img_scroll_profileImage.setEnabled(true);
                img_scroll_shortlist.setEnabled(true);
                img_scroll_shortlisted.setEnabled(true);
                img_scroll_invite.setEnabled(true);
                img_invited.setEnabled(true);
                img_scroll_chat.setEnabled(true);
            }
        });*/






    }

    @Override
    public void showUsers(List<BlockListModelRes> userDetailsModels) {
        profileVisitorModelList.clear();
        profileVisitorModelList.addAll(userDetailsModels);
        profileVisitedAdapter = new CustomListAdapter(mContext, mRecyclerView,
                profileVisitorModelList, R.layout.recyclerview_row_block, new CustomListAdapter.ItemClickedListener() {
            @Override
            public void onViewBound(View view, Object object, int position) {
                showProfileVisitedData(view, (BlockListModelRes) object, position);
            }

            @Override
            public void onItemClicked(View view, Object object, int position) {


            }
        });
        mRecyclerView.setAdapter(profileVisitedAdapter);
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
    public void showBlockedStatus(String message) {

    }
}