package ew.finalwork.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import ew.finalwork.services.SyncService;

public class NetworkChangeReceiver extends BroadcastReceiver {

    public static final String SYNC_COMMAND = "sync_command";

    private static NetworkChangeReceiver receiver = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Tag", SYNC_COMMAND);
        String action = intent.getAction();
        if (action != null){
            switch(action){
                case SYNC_COMMAND:
                    if (checkInternet(context)){
                        Log.i("Tag", SYNC_COMMAND);
                        Intent serviceIntent = new Intent(context, SyncService.class);
                        context.startService(serviceIntent);
                    }
                    break;
                default:
                    break;
            }
        }

    }

    public static NetworkChangeReceiver getInstance(){
        if (receiver == null){
            receiver = new NetworkChangeReceiver();
        }
        return receiver;
    }

    private boolean checkInternet(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
