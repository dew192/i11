package atry.i11.com.i11;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import proto.inventa.cct.com.inventalibrary.InventaSdk;
import proto.inventa.cct.com.inventalibrary.discovery.DiscoveryHelper;
import proto.inventa.cct.com.inventalibrary.inventadiscovery.DistanceRssiModel;
import proto.inventa.cct.com.inventalibrary.notification.NotificationDisplayHelper;
import proto.inventa.cct.com.inventalibrary.notification.NotificationHelper;
import proto.inventa.cct.com.inventalibrary.rest.NotificationApiHelper;

import static proto.inventa.cct.com.inventalibrary.discovery.DiscoveryHelper.KEY_INSTORE_ID;

public class DiscoveryBroadcastReceiver extends BroadcastReceiver {
    public static final String LOG_TAG = DiscoveryBroadcastReceiver.class.getSimpleName() + " InventaNotification ";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        Log.d(LOG_TAG, " intent.getAction() " + action);
        if(action != null) {

            switch (action) {
                case DiscoveryHelper.ACTION_DISCOVERY_GEOZONES_BROADCAST:
                    showGeoZones(intent);
                    break;

                case DiscoveryHelper.ACION_DISCOVERY_ENGAGEMENTZONES_BROADCAST:
                    showEngagementZones(intent);
                    break;

                case DiscoveryHelper.ACION_DISCOVERY_INSTORE_BROADCAST:
                    showInStore(intent);
                    break;
                case NotificationHelper.ACTION_NOTIFICATION_CLICK_GZONE:
                    Log.d("inventa123456", "  ACTION_NOTIFICATION_CLICK_GZONE ");
                    performGzoneNotificationClick(intent);
                    break;
                case NotificationHelper.ACTION_NOTIFICATION_CLICK_EZONE:
                    Log.d("inventa123456", "  ACTION_NOTIFICATION_CLICK_EZONE ");
                    performEzoneNotificationClick(intent);
                    break;
                case NotificationHelper.ACTION_NOTIFICATION_CLICK_INSTORE:
                    Log.d("inventa123456", "  ACTION_NOTIFICATION_CLICK_INSTORE ");
                    break;
                default:
                    break;
            }
        }
    }

    private void performGzoneNotificationClick(Intent intent) {
        String geoZoneId = intent.getStringExtra(NotificationHelper.NOTIFICATION_GEOZONE_ID);
        String actionUrl = intent.getStringExtra(NotificationHelper.NOTIFICATION_ACTION_URL);
        String notification_id = intent.getStringExtra(NotificationHelper.NOTIFICATION_ID);
        Log.d(LOG_TAG, "  ACTION_NOTIFICATION_CLICK_GZONE " +
                "geoZoneId  " + geoZoneId
                + " actionUrl " + actionUrl
                + " notification_id " + notification_id);
    }

    private void performEzoneNotificationClick(Intent intent) {
        String ezoneId = intent.getStringExtra(NotificationHelper.NOTIFICATION_EZONE_ID);
        String storeId = intent.getStringExtra(NotificationHelper.NOTIFICATION_STORE_ID);
        String actionUrl = intent.getStringExtra(NotificationHelper.NOTIFICATION_ACTION_URL);
        String notification_id = intent.getStringExtra(NotificationHelper.NOTIFICATION_ID);
        Log.d(LOG_TAG, "  ACTION_NOTIFICATION_CLICK_eZONE " +
                "ezoneId  " + ezoneId
                + " actionUrl " + actionUrl
                + " notification_id " + notification_id
                + " storeid "+storeId);
    }

    @SuppressWarnings("unchecked")
    private void showInStore(Intent intent) {
//        if(InventaClientApplication.getContext().getResources().getBoolean(R.bool.instore_logger)) {
//            ArrayList<DistanceRssiModel> distanceRssiModels = (ArrayList<DistanceRssiModel>) intent.getSerializableExtra("inStoreLogger");
//            if(distanceRssiModels != null && distanceRssiModels.size() > 0) {
//                InStoreLoggerActivity.appendText(distanceRssiModels.toString());
//
//                if(InventaClientApplication.getContext().getResources().getBoolean(R.bool.instore_logger_autosave)) {
//                    saveDistance(distanceRssiModels);
//                } else {
//                    Intent i = new Intent(InventaSdk.getContext().getApplicationContext(), DialogHelper.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    i.putExtra("inStoreLogger", distanceRssiModels);
//                    InventaSdk.getContext().startActivity(i);
//                }
//            }
//        }
//        ArrayList<String> p_ids = (ArrayList) intent.getSerializableExtra(DiscoveryHelper.KEY_INSTORE_LIST);
//        Log.d(LOG_TAG, p_ids.toString());
//        for (String singleId : p_ids) {
//            String username = ProfileHelper.getProfileUsernameById(singleId);
//
//            //retrieve storeid, so that on click of instore notification, that store is launched
//            if (!username.isEmpty()) {
//                if (ProfileHelper.isStoreManagerRoleProfile(singleId) && ProfileHelper.isSelfProfileConsumerRoleProfile()) {
//
//                    // I am the consumer and the discovered profile is the store manager
//                    findStoreName(singleId, username, NotificationDisplayHelper.CHANNEL_DISCOVERY, NotificationHelper.NOTIFICATION_IDENTIFIER_INSTORE);
//
//                } else if (ProfileHelper.isConsumerRoleProfile(singleId) && ProfileHelper.isSelfStoreManagerRoleProfile()) {
//
//                    // I am the store manager and the discovered profile is the consumer
//                    String eventText = "is in the store";
//                    String storeManagerProfileId = InventaSdk.getUserId();
//                    findStoreIdOfStoreManager(storeManagerProfileId,username,eventText);
//                    //  showNotification(singleId, username, eventText, proto.inventa.cct.com.inventalibrary.utils.NotificationDisplayHelper.CHANNEL_DISCOVERY, NotificationDisplayHelper.NOTIFICATION_IDENTIFIER_INSTORE);
//                }
//            }
//        }
    }

    @SuppressWarnings("unchecked")
    private void showEngagementZones(Intent intent) {
        HashMap<Integer, ArrayList<String>>
                engagementZonesMap = (HashMap<Integer, ArrayList<String>>)
                intent.getSerializableExtra(DiscoveryHelper.KEY_ENGAGEMENTZONES_MAP);
        Log.d(LOG_TAG, "ENGAGEMENTZONES_MAP received");

        if (engagementZonesMap.containsKey(DiscoveryHelper.GEOFENCE_TRANSITION_ENTER)) {
            for (String singleId : engagementZonesMap.get(DiscoveryHelper.GEOFENCE_TRANSITION_ENTER)) {
                Log.d(LOG_TAG, " eZone entered triggered "+singleId);
            }
        }

        if (engagementZonesMap.containsKey(DiscoveryHelper.GEOFENCE_TRANSITION_DWELL)) {
            for (String singleId : engagementZonesMap.get(DiscoveryHelper.GEOFENCE_TRANSITION_DWELL)) {
                Log.d(LOG_TAG, " eZone entered dwelt "+singleId);
            }
        }

        if (engagementZonesMap.containsKey(DiscoveryHelper.GEOFENCE_TRANSITION_EXIT)) {
            for (String singleId : engagementZonesMap.get(DiscoveryHelper.GEOFENCE_TRANSITION_EXIT)) {
                Log.d(LOG_TAG, " eZone exit triggered "+singleId);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void showGeoZones(Intent intent) {
        HashMap<Integer, ArrayList<String>> geoZonesMap = (HashMap<Integer, ArrayList<String>>)
                intent.getSerializableExtra(DiscoveryHelper.KEY_GEOZONES_MAP);
        Log.d(LOG_TAG, "KEY_GEOZONES_MAP received : ");

        if(geoZonesMap != null) {

            if (geoZonesMap.containsKey(DiscoveryHelper.GEOFENCE_TRANSITION_ENTER)) {
                for (String singleId : geoZonesMap.get(DiscoveryHelper.GEOFENCE_TRANSITION_ENTER)) {
                    Log.d(LOG_TAG, " geoZone entered "+singleId);
                }
            }

            if (geoZonesMap.containsKey(DiscoveryHelper.GEOFENCE_TRANSITION_DWELL)) {
                for (String singleId : geoZonesMap.get(DiscoveryHelper.GEOFENCE_TRANSITION_DWELL)) {
                    Log.d(LOG_TAG, " geoZone dwell "+singleId);
                }
            }

            if (geoZonesMap.containsKey(DiscoveryHelper.GEOFENCE_TRANSITION_EXIT)) {
                for (String singleId : geoZonesMap.get(DiscoveryHelper.GEOFENCE_TRANSITION_EXIT)) {
                    Log.d(LOG_TAG, " geoZone exit "+singleId);
                }

            }
        }
    }



    //show instore notification
    public void showStoreNameForInstore(List<String> storeIds) {

        String eventText = "Welcome to  - " ;
        if (storeIds != null && storeIds.size() > 0) {
            String storeId = storeIds.get(0);
            String storeName = InventaSdk.getRealmDataHelper().getStoreNameByStoreId(storeId);
            Log.d(LOG_TAG," InStore notif found for "+storeName);
            showNotification(storeId, storeName, eventText.concat(storeName), NotificationDisplayHelper.CHANNEL_DISCOVERY, NotificationHelper.NOTIFICATION_IDENTIFIER_INSTORE);
        }
    }

    private void sendStoreIdForInstore(List<String> storeIds, String username, String eventText) {

        if (storeIds != null && storeIds.size() > 0) {
            String storeId = storeIds.get(0);
            Log.d(LOG_TAG," InStore notif found for "+eventText);
            showNotification(storeId, username, eventText, NotificationDisplayHelper.CHANNEL_DISCOVERY, NotificationHelper.NOTIFICATION_IDENTIFIER_INSTORE);
        }
    }

    public void showNotification(String zoneId, String title, String eventText, @NotificationDisplayHelper.CHANNELS String channel, String type) {

        NotificationDisplayHelper notificationDisplayHelper = new NotificationDisplayHelper(InventaSdk.getContext(), channel, false);
        int notificationId = 0;
        switch (type) {

            case NotificationHelper.NOTIFICATION_IDENTIFIER_INSTORE:

                createInStoreNotification(zoneId, title, eventText, notificationDisplayHelper);
                break;
        }
    }

    private void findStoreName(String id, String username, String channelDiscovery, String singleId) {

        //Show notif after you receive store name from server
        NotificationApiHelper notificationApiHelper = NotificationApiHelper.getInstance();
        notificationApiHelper.getAssignedStoreByProfileId(id, new NotificationHelper.OnAssignedStoreManagerByProfileID() {
            @Override
            public void OnAssignedStoreManagerByProfileID(List<String> storeIds) {
                showStoreNameForInstore(storeIds);
            }

            @Override
            public void onError(String errorMsg) {
                Log.e(LOG_TAG, errorMsg);
            }
        });

    }

    private void findStoreIdOfStoreManager(String id, String username, String eventText) {
        //TODO temp method to find store managers storeid by his profileid, Later Retrieve his storeid  from db

        NotificationApiHelper notificationApiHelper = NotificationApiHelper.getInstance();
        notificationApiHelper.getAssignedStoreByProfileId(id, new NotificationHelper.OnAssignedStoreManagerByProfileID() {
            @Override
            public void OnAssignedStoreManagerByProfileID(List<String> storeIds) {
                sendStoreIdForInstore(storeIds, username, eventText);
            }
            @Override
            public void onError(String errorMsg) {
                Log.e(LOG_TAG, errorMsg);
            }
        });
    }

    private void createInStoreNotification(String zoneId, String title, String eventText, NotificationDisplayHelper notificationDisplayHelper) {
        int notificationId;
        Intent inStoreIntent = new Intent(InventaSdk.getContext(), MainActivity.class);

        inStoreIntent.putExtra("UUID", zoneId);
        inStoreIntent.putExtra(NotificationHelper.KEY_NOTIF_TYPE, "InStore");

        inStoreIntent.putExtra(KEY_INSTORE_ID, zoneId);
        notificationId = 1;
        PendingIntent inStorePendingIntent = notificationDisplayHelper.setupPendingIntent(inStoreIntent);
        NotificationCompat.Builder inStoreNotiBuilder = notificationDisplayHelper.getNotificationCompatBuilder(title, eventText);
        inStoreNotiBuilder.setContentIntent(inStorePendingIntent);
        inStoreNotiBuilder.setAutoCancel(true);

        //create a unique notifcation id each time, so that one notification doesnt replace another
        if(!notificationDisplayHelper.isNotificationActive(zoneId, notificationId)) {
            notificationDisplayHelper.notify(zoneId, notificationId, inStoreNotiBuilder);
        }
    }

    public void saveDistance(ArrayList<DistanceRssiModel> inStoreLoggerList) {
//        DBManager dbManager;
//        if(inStoreLoggerList == null || inStoreLoggerList.size() == 0) {
//            return;
//        }
//        String selfDeviceName = InventaSdk.getRealmDataHelper().getDeviceNameById(InventaSdk.getUserId());
//
//        dbManager = new DBManager(InventaClientApplication.getContext());
//        dbManager.open();
//
//        for (int currentPosition = 0; currentPosition < inStoreLoggerList.size(); currentPosition++) {
//            dbManager.insert(System.currentTimeMillis(), selfDeviceName,
//                    inStoreLoggerList.get(currentPosition).getDeviceName(),
//                    inStoreLoggerList.get(currentPosition).getBleRssi(),
//                    inStoreLoggerList.get(currentPosition).getWifiRssi(),
//                    inStoreLoggerList.get(currentPosition).getStoreWifiRssi(),
//                    inStoreLoggerList.get(currentPosition).getBluetoothRssi(),
//                    inStoreLoggerList.get(currentPosition).getBleDistance1(),
//                    inStoreLoggerList.get(currentPosition).getBleDistance2(),
//                    inStoreLoggerList.get(currentPosition).getBleDistance3(),
//                    inStoreLoggerList.get(currentPosition).getWifiDistance1(),
//                    inStoreLoggerList.get(currentPosition).getWifiDistance2(),
//                    inStoreLoggerList.get(currentPosition).getWifiDistance3(),
//                    InventaClientApplication.realWorldDistance,
//                    InventaSdk.getRealmDataHelper().getProfileUsernameById(InventaSdk.getUserId()),
//                    inStoreLoggerList.get(currentPosition).getUsername()
//            );
//        }
//        dbManager.close();
//        dbManager.exportDB();
    }
}
