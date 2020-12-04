package in.co.vsys.myssksamaj.broadcast_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import in.co.vsys.myssksamaj.services.SyncService;
import in.co.vsys.myssksamaj.utils.Utilities;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        try {
            Log.d(NetworkChangeReceiver.class.getSimpleName(), "onReceive()");

            if (Utilities.isInternetAvailable(context, new Utilities.InternetConnectionListener() {
                @Override
                public void isInternetConnected(boolean isConnected) {
                    if (!isConnected)
                        return;

                    Intent serviceIntent = new Intent(context, SyncService.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(serviceIntent);
                    } else {
                        context.startService(serviceIntent);
                    }
                }
            })) ;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}