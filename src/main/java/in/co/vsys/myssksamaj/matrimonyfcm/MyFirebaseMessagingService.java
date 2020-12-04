package in.co.vsys.myssksamaj.matrimonyfcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import in.co.vsys.myssksamaj.R;
import in.co.vsys.myssksamaj.activities.HomeActivity;
import in.co.vsys.myssksamaj.interactors.CustomChatInteractor;
import in.co.vsys.myssksamaj.model.NotificationModel;
import in.co.vsys.myssksamaj.services.SyncService;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.MatrimonyDBHelper;

/**
 * Created by nilesh on 19/7/16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private Context mContext;
    private NotificationCompat.Builder mBuilder;
    private String title;
    private String message, image, memberName, createdby, typeId, senderId = "";
    private NotificationManager mNotificationManager;
    public static final String NOTIFICATION_CHANNEL_ID = "in.co.vsys.malisamajmatrimony";
    private Bitmap bitmap;
    private MatrimonyDBHelper dbHelper;
    private SharedPreferences mPreferences;
    private int memberId;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            mContext = this;
            Log.e("message", "onMessageReceived: " + remoteMessage.getData());

            Intent notificationIntent;
            notificationIntent = new Intent(mContext, HomeActivity.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

            PendingIntent rePendingIntent = PendingIntent.getActivity(mContext, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            dbHelper = new MatrimonyDBHelper(mContext);
            memberId = mPreferences.getInt("memberId", 0);

            if (remoteMessage.getData().size() > 0) {

                title = remoteMessage.getData().get("Title");
                message = remoteMessage.getData().get("Message");
                image = remoteMessage.getData().get("ImageURL");
                memberName = remoteMessage.getData().get("MemberName");
                createdby = remoteMessage.getData().get("createdby");

                if (remoteMessage.getData().containsKey("TypeID")) {
                    typeId = remoteMessage.getData().get("TypeID");

/*
                    if (Integer.parseInt(typeId) == 1 || Integer.parseInt(typeId) == 2) {
                        constructChatNotification(remoteMessage);
                        return;
                    }

*/

                    if (Integer.parseInt(typeId) == 1) {
                        constructChatNotification(remoteMessage);
                        return;
                    }
                }

                NotificationModel notificationModel = new NotificationModel();
                notificationModel.setNotificationTitle("" + memberName);
                notificationModel.setNotificationDesc("" + message);
                notificationModel.setNotificationImage("" + image);
                notificationModel.setNotificationTime("" + System.currentTimeMillis());
                notificationModel.setNotificationMemberId(memberId);
                notificationModel.setCreatedBy(createdby);

                boolean insert = dbHelper.addNotification(notificationModel);

                if (insert) {
                } else {
                    Log.e("Notification class", "==============: " + "Notification not inserted");
                }
            }

            mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            } else {
                mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
                mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            }

            mBuilder.setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(rePendingIntent);

            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                int importance = NotificationManager.IMPORTANCE_HIGH;

                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, title, importance);
                notificationChannel.setDescription(Notification.CATEGORY_MESSAGE);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);

                mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);

                if (mNotificationManager != null) {

                    handleNotificationByTypeID(typeId);
                    if (typeId.equals("1")) {
                        mNotificationManager.createNotificationChannel(notificationChannel);
                    }
                }
            }
            handleNotificationByTypeID(typeId);

            if (typeId.equals("2")) {
                mNotificationManager.notify(234, mBuilder.build());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleNotificationByTypeID(String typeId) {

        if (typeId.equals("2")) {
            return;
        }
        if (typeId.equals("3")) {
            if (CustomChatInteractor.getInstance().isViewValid()) {
                CustomChatInteractor.getInstance().reloadChat();
            }
            return;
        }
        if (typeId.equals("4")) {
            if (CustomChatInteractor.getInstance().isViewValid()) {

                if (CustomChatInteractor.getInstance().getCurrentChatPersonId().equalsIgnoreCase(senderId)) {
                    CustomChatInteractor.getInstance().reloadChat();
                    return;
                } else {
                    Intent serviceIntent = new Intent(mContext, SyncService.class);
                    serviceIntent.setAction(Constants.SERVICE_ACTION.READ_MSG);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        mContext.startService(serviceIntent);
                    } else {
                        mContext.startService(serviceIntent);
                    }
                }
            }
            return;
        }


    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    private void constructChatNotification(RemoteMessage remoteMessage) {
        Intent notificationIntent;
        notificationIntent = new Intent(mContext, HomeActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationIntent.putExtra(Constants.ShareableIntents.Screen, Constants.SCREEN.CHAT_LIST);

        PendingIntent rePendingIntent = PendingIntent.getActivity(mContext, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (remoteMessage.getData().size() > 0) {

            title = remoteMessage.getData().get("Title");
            message = remoteMessage.getData().get("Message");
            image = remoteMessage.getData().get("ImageURL");
            memberName = remoteMessage.getData().get("MemberName");
            createdby = remoteMessage.getData().get("createdby");
            if (remoteMessage.getData().containsKey("TypeID")) {
                typeId = remoteMessage.getData().get("TypeID");

                if (remoteMessage.getData().containsKey("senderId"))
                    senderId = remoteMessage.getData().get("senderId");
            }

            NotificationModel notificationModel = new NotificationModel();
            notificationModel.setNotificationTitle("" + memberName);
            notificationModel.setNotificationDesc("" + message);
            notificationModel.setNotificationImage("" + image);
            notificationModel.setNotificationTime("" + System.currentTimeMillis());
            notificationModel.setNotificationMemberId(memberId);
            notificationModel.setCreatedBy(createdby);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
            mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        } else {
            mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
            mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        }

        mBuilder.setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(rePendingIntent);


        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//            If the chat screen is open then show show notification, just update the chat
        if (CustomChatInteractor.getInstance().isViewValid()) {

            if (CustomChatInteractor.getInstance().getCurrentChatPersonId().equalsIgnoreCase(senderId)) {
                CustomChatInteractor.getInstance().reloadChat();

                Intent serviceIntent = new Intent(mContext, SyncService.class);
                serviceIntent.setAction(Constants.SERVICE_ACTION.READ_MSG);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mContext.startService(serviceIntent);
                } else {
                    mContext.startService(serviceIntent);
                }

                return;
            } else {

                Intent serviceIntent = new Intent(mContext, SyncService.class);
                serviceIntent.setAction(Constants.SERVICE_ACTION.DELIVERD_MSG);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mContext.startService(serviceIntent);
                } else {
                    mContext.startService(serviceIntent);
                }
            }
        } else {
            Intent serviceIntent = new Intent(mContext, SyncService.class);
            serviceIntent.setAction(Constants.SERVICE_ACTION.DELIVERD_MSG);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mContext.startService(serviceIntent);
            } else {
                mContext.startService(serviceIntent);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, title, importance);
            notificationChannel.setDescription(Notification.CATEGORY_MESSAGE);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);

            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);

            if (mNotificationManager != null) {

                handleNotificationByTypeID(typeId);
                if (typeId.equals("1")) {
                    mNotificationManager.createNotificationChannel(notificationChannel);
                }
            }
        }
        handleNotificationByTypeID(typeId);
        if (typeId.equals("1")) {
            mNotificationManager.notify(234, mBuilder.build());
        }

    }
}