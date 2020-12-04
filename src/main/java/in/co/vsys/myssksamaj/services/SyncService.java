package in.co.vsys.myssksamaj.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import in.co.vsys.myssksamaj.contracts.UpdateMsgSentToDeliveredContract;
import in.co.vsys.myssksamaj.contracts.UpdateMsgSentToReadContract;
import in.co.vsys.myssksamaj.helpers.SharedPrefsHelper;
import in.co.vsys.myssksamaj.interactors.CustomChatInteractor;
import in.co.vsys.myssksamaj.model.responses.Entity;
import in.co.vsys.myssksamaj.presenters.MainPresenter;
import in.co.vsys.myssksamaj.presenters.UpdateMsgSentToDeliveredPresenter;
import in.co.vsys.myssksamaj.presenters.UpdateMsgSentToReadPresender;
import in.co.vsys.myssksamaj.utils.Constants;
import in.co.vsys.myssksamaj.utils.Utilities;

public class SyncService extends Service implements UpdateMsgSentToDeliveredContract.UpdateMsgDeliveredView, UpdateMsgSentToReadContract.updateMsgSentToReadView {

    private static final String TAG = "SyncService";
    private Context mContext;
    private UpdateMsgSentToDeliveredContract.UpdateMsgDeliveredOPS mUpdateMsgDeliveredPresenter;
    private UpdateMsgSentToReadContract.updateMsgSentToReadOPS mUpdateMsgSentToReadPresenter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mUpdateMsgDeliveredPresenter = new UpdateMsgSentToDeliveredPresenter(this);
        mUpdateMsgSentToReadPresenter = new UpdateMsgSentToReadPresender(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        onCreate();
       /* Toast.makeText(mContext, "Service Is started", Toast.LENGTH_SHORT).show();
        String reciverId = String.valueOf(MainPresenter.getInstance().getMemberId(mContext));*/
        if (intent != null) {
            String action = intent.getAction();

            switch (action) {
                case Constants.SERVICE_ACTION.DELIVERD_MSG:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mUpdateMsgDeliveredPresenter.updateMsgDelivered(String.valueOf(MainPresenter.getInstance().getMemberId(mContext)));
                        }
                    }).start();
                    break;
                case Constants.SERVICE_ACTION.READ_MSG:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int message_receiverId = SharedPrefsHelper.getInstance(mContext).getIntVal("messageReceiverId");
                            mUpdateMsgSentToReadPresenter.updateMsgSentToRead(Utilities.getString(message_receiverId), String.valueOf(MainPresenter.getInstance().getMemberId(mContext)));
                        }
                    }).start();
                    break;
            }

        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                mUpdateMsgDeliveredPresenter.updateMsgDelivered(String.valueOf(MainPresenter.getInstance().getMemberId(mContext)));
            }
        }).start();
        return START_REDELIVER_INTENT;
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
    public void showResponse(Entity entity) {
        if (CustomChatInteractor.getInstance().isViewValid()) {
            CustomChatInteractor.getInstance().reloadChat();
        }
    }
}