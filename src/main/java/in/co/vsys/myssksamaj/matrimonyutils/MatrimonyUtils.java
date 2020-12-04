package in.co.vsys.myssksamaj.matrimonyutils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.adapter.CustomPagerAdapter;
import in.co.vsys.myssksamaj.contracts.AcceptInvitationContract;
import in.co.vsys.myssksamaj.contracts.ImagesContract;
import in.co.vsys.myssksamaj.contracts.InvitationNotificationContract;
import in.co.vsys.myssksamaj.contracts.InviteContract;
import in.co.vsys.myssksamaj.contracts.ProfileViewNotificationContract;
import in.co.vsys.myssksamaj.contracts.SendNotificationContract;
import in.co.vsys.myssksamaj.contracts.ShortListContract;
import in.co.vsys.myssksamaj.contracts.VisitContract;
import in.co.vsys.myssksamaj.model.ImagesDataModel;
import in.co.vsys.myssksamaj.model.PagerModel;
import in.co.vsys.myssksamaj.photoview.PhotoView;
import in.co.vsys.myssksamaj.presenters.AcceptInvitationPresenter;
import in.co.vsys.myssksamaj.presenters.ImagesPresenter;
import in.co.vsys.myssksamaj.presenters.InvitationNotificationPresenter;
import in.co.vsys.myssksamaj.presenters.InvitePresenter;
import in.co.vsys.myssksamaj.presenters.ProfileViewNotificationPresenter;
import in.co.vsys.myssksamaj.presenters.SendNotificationPresenter;
import in.co.vsys.myssksamaj.presenters.ShortListPresenter;
import in.co.vsys.myssksamaj.presenters.VisitPresenter;
import in.co.vsys.myssksamaj.utils.Utilities;
import me.relex.circleindicator.CircleIndicator;

public class MatrimonyUtils {

    private static final String TAG = MatrimonyUtils.class.getSimpleName();
    private static final String SENDER_ID = "SenderMemberid";
    private static final String RECEIVER_ID = "ReceiverMemberId";
    private static final String USER_PROFILE_ID = "ProfileViewMemberId";
    private static final String DEVICE_ID = "DeviceId";
    private static final String NOTIFICATION_MESSAGE = "Message";
    private static final String NOTIFICATION_APP_NAME = "AppName";
    private static final String NOTIFICATION_IMAGE_URL = "ImageURL";
    private static final String NOTIFICATION_RECEIVER_ID = "UserId";
    private static final String NOTIFICATION_SENDOR_ID = "MemberId";
    private static final String NOTIFICATION_MEMBER_NAME = "MemberName";
    private static final String APP_NAME = "MY SSK SAMAJ";

    private static final String STATUS = "Status";
    public int shortlistSuccess, invitelistSucess, profileViewSuccess;
    public List<PagerModel> phList;
    private static final String MEMBER_ID = "MemberId";

    public static void addShortlistTask(final Context mContext, final int senderId, final int receiverId, final String status) {

        final ProgressDialog mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Please wait...");

        ShortListContract.ShortListOps shortListPresenter = new ShortListPresenter(new ShortListContract.ShortListView() {
            @Override
            public void showShortlisted(String message) {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showLoading() {
                mDialog.show();
            }

            @Override
            public void hideLoading() {
                mDialog.dismiss();
            }

            @Override
            public void showError(String message) {
                Log.d(TAG, "addShortlistTask" + message);
            }
        });
        shortListPresenter.performShortlist("" + senderId, "" + receiverId, status);
    }

    public static void inviteTask(final Context mContext, final int senderId, final int receivedId, final String status) {

        final ProgressDialog mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Please wait...");
        mDialog.setCancelable(false);

        InviteContract.InviteOps invitePresenter = new InvitePresenter(new InviteContract.InviteView() {
            @Override
            public void showInvited(String message) {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showLoading() {
                mDialog.show();
            }

            @Override
            public void hideLoading() {
                mDialog.dismiss();
            }

            @Override
            public void showError(String message) {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        });
        invitePresenter.performInvite("" + senderId, "" + receivedId, status);
    }

    public static void loadImages(final Context mContext, final int mId, final ImagesContract.ImagesView imagesView) {

        final ProgressDialog mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Please wait...");

        ImagesContract.ImageOps imageOps = new ImagesPresenter(new ImagesContract.ImagesView() {
            @Override
            public void showImages(ImagesDataModel imagesDataModel) {
                if (imagesView != null)
                    imagesView.showImages(imagesDataModel);

                List<String> images = new ArrayList<>();
                if (!Utilities.isEmpty(imagesDataModel.getMainProfilePhoto()))
                    images.add(imagesDataModel.getMainProfilePhoto());
                if (!Utilities.isEmpty(imagesDataModel.getMemberPhoto1()))
                    images.add(imagesDataModel.getMemberPhoto1());
                if (!Utilities.isEmpty(imagesDataModel.getMemberPhoto2()))
                    images.add(imagesDataModel.getMemberPhoto2());
                if (!Utilities.isEmpty(imagesDataModel.getMemberPhoto3()))
                    images.add(imagesDataModel.getMemberPhoto3());
                if (!Utilities.isEmpty(imagesDataModel.getMemberPhoto4()))
                    images.add(imagesDataModel.getMemberPhoto4());
                if (!Utilities.isEmpty(imagesDataModel.getMemberPhoto5()))
                    images.add(imagesDataModel.getMemberPhoto5());
                if (!Utilities.isEmpty(imagesDataModel.getMemberPhoto6()))
                    images.add(imagesDataModel.getMemberPhoto6());
                if (!Utilities.isEmpty(imagesDataModel.getMemberPhoto7()))
                    images.add(imagesDataModel.getMemberPhoto7());
                if (!Utilities.isEmpty(imagesDataModel.getMemberPhoto8()))
                    images.add(imagesDataModel.getMemberPhoto8());
                if (!Utilities.isEmpty(imagesDataModel.getMemberPhoto9()))
                    images.add(imagesDataModel.getMemberPhoto9());
                if (!Utilities.isEmpty(imagesDataModel.getMemberPhoto10()))
                    images.add(imagesDataModel.getMemberPhoto10());

                if(Utilities.isListEmpty(images))
                    return;

                showPOpImages(mContext, images);
            }

            @Override
            public void showLoading() {
                try {
                    mDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void hideLoading() {
                try {
                    mDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void showError(String message) {

            }
        });
        imageOps.getAllImages("" + mId);
    }

    public static void showPOpImages(Context context, List<String> images) {

        Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.row_images_scroll);

        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(context, images, R.layout.pager_layout,
                new CustomPagerAdapter.ItemClickedListener() {
                    @Override
                    public void onItemClicked(View view, Object object, int position) {

                    }

                    @Override
                    public void onViewBound(View view, Object object, int position) {
                        String imageUrl = (String) object;
                        PhotoView imageView = (PhotoView) view.findViewById(R.id.imagePagerView);

                        if (Utilities.isEmpty(imageUrl)) {
                            Picasso.get()
                                    .load(R.drawable.img_preview)
                                    .placeholder(R.drawable.img_preview)
                                    .error(R.drawable.img_preview)
                                    .into(imageView);
                        } else {

                            Picasso.get()
                                    .load(imageUrl)
                                    .placeholder(R.drawable.img_preview)
                                    .into(imageView);
                        }
                    }
                });


        ViewPager viewPager = dialog.findViewById(R.id.image_viewPager);
        viewPager.setAdapter(customPagerAdapter);

        CircleIndicator circleIndicator = dialog.findViewById(R.id.circleIndictor);
        circleIndicator.setViewPager(viewPager);

        dialog.show();
    }

    public static void sendMemberIdTask(final int memberId, final int userMemberId) {

        VisitContract.VisitOps visitPresenter = new VisitPresenter(new VisitContract.VisitView() {
            @Override
            public void showVisitPerformed(String message) {

            }

            @Override
            public void showLoading() {

            }

            @Override
            public void hideLoading() {

            }

            @Override
            public void showError(String message) {
                Log.d(TAG, "profile visit " + message);
            }
        });
        visitPresenter.performVisit("" + memberId, "" + userMemberId);
    }

    public void sendNotification(final String tokenId, final String message) {

        SendNotificationContract.SendNotificationOps sendNotificationPresenter =
                new SendNotificationPresenter(new SendNotificationContract.SendNotificationView() {
                    @Override
                    public void showNotificationSent(String message) {

                    }
                });
        sendNotificationPresenter.sendNotification(tokenId, message);
    }

    public static void sendInvitationNotification(final String tokenId, final String message,
                                                  final String imageUrl, final String receiverId, final String senderId,
                                                  final String memberName) {

        InvitationNotificationContract.InvitationNotificationOps invitationNotificationOps =
                new InvitationNotificationPresenter(new InvitationNotificationContract.InvitationNotificationView() {

                    @Override
                    public void showInvitationNotificationSent() {
                        Log.e(TAG, "notification sent");
                    }
                });
        invitationNotificationOps.sendInvitationNotification(tokenId, message, APP_NAME, imageUrl,
                receiverId, senderId, memberName);
    }

    public static void sendProfileViewNotification(final String tokenId, final String message,
                                                   final String imageUrl, final String receiverId, final String senderId,
                                                   final String MemberName) {

        ProfileViewNotificationContract.ProfileViewNotificationOps profileViewNotificationPresenter =
                new ProfileViewNotificationPresenter(new ProfileViewNotificationContract.ProfileViewNotificationView() {
                    @Override
                    public void showNotificationSent() {
                        Log.e(TAG, "notification sent: ");
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
                });
        profileViewNotificationPresenter.sendViewProfileNotification(tokenId, message, APP_NAME, imageUrl,
                receiverId, senderId, MemberName);
    }

    public static void acceptInvitation(final Context mContext, final int senderId, final int receiverId, final String status) {

        final ProgressDialog mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Please wait...");
        mDialog.setCancelable(false);

        AcceptInvitationContract.AcceptInvitationOps acceptInvitationPresenter =
                new AcceptInvitationPresenter(
                        new AcceptInvitationContract.AcceptInvitationView() {
                            @Override
                            public void showInvitationAccepted(String message) {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void showLoading() {
                                mDialog.show();
                            }

                            @Override
                            public void hideLoading() {
                                mDialog.dismiss();
                            }

                            @Override
                            public void showError(String message) {

                            }
                        });
        acceptInvitationPresenter.acceptInvitation("" + senderId, "" + receiverId, status);
    }

    public static String getAge(String dob) {
        if (Utilities.isEmpty(dob)) {
            return "";
        }
        int age = Utilities.getAge(dob);
        if (age == 0)
            return "";

        return "" + age;
    }

}