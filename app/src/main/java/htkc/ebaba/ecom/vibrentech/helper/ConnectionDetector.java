package htkc.ebaba.ecom.vibrentech.helper;

import android.content.Context;
import android.net.ConnectivityManager;


public class ConnectionDetector {
    private Context mContext;

    public ConnectionDetector(Context context){
        this.mContext = context;
    }

    public boolean isConnectingToInternet(){

        ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService( Context.CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected() == true)
        {
            return true;
        }

        return false;

    }
}
