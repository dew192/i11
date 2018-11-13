package atry.i11.com.i11;

import android.content.Context;

import proto.inventa.cct.com.inventalibrary.InventaSdk;
import proto.inventa.cct.com.inventalibrary.discovery.DiscoveryHelper;

public class InventaClientApplication extends InventaSdk {

    public static final String TAG = InventaClientApplication.class.getSimpleName();
    private static Context applicationContext;
    public static int realWorldDistance = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        DiscoveryHelper.setDiscoveryTransitionsReceiver(new DiscoveryBroadcastReceiver());
        applicationContext = getApplicationContext();

    }

    /**
     * Gets the application context.
     *
     * @return the context
     */
    public static Context getContext() {
        return applicationContext;
    }



}
