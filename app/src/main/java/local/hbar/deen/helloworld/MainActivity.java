package local.hbar.deen.helloworld;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    private static String ACTION_UPDATE_NOTIFICATION;

    private NotificationManager mNotifyManager;
    private NotificationReceiver mNotificationReceiver = new NotificationReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        ACTION_UPDATE_NOTIFICATION = getApplicationContext().getPackageName() +
                "ACTION_UPDATE_NOTIFICATION";

        findViewById(R.id.notify_btn).setOnClickListener((View v) ->
                mNotifyManager.notify(NOTIFICATION_ID,
                        getNotificationBuilder()
                                .addAction(R.drawable.ic_notification_icon,
                                        "Update Notification",
                                        PendingIntent.getBroadcast(this,
                                                NOTIFICATION_ID,
                                                new Intent(ACTION_UPDATE_NOTIFICATION),
                                                PendingIntent.FLAG_ONE_SHOT))
                                .build())
        );

        findViewById(R.id.cancel_btn).setOnClickListener((View v) ->
                mNotifyManager.cancel(NOTIFICATION_ID)
        );

        findViewById(R.id.update_btn).setOnClickListener((View v) ->
                mNotifyManager
                        .notify(NOTIFICATION_ID,
                                getNotificationBuilder()
                                        .setStyle(new NotificationCompat.BigTextStyle()
                                                .bigText("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                                                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
                                                .setBigContentTitle("Updated Content!"))
                                        .build())
        );


        registerReceiver(mNotificationReceiver, new IntentFilter(ACTION_UPDATE_NOTIFICATION));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mNotificationReceiver);
        super.onDestroy();
    }

    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Primary Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification Description");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }


    private NotificationCompat.Builder getNotificationBuilder() {


        return new NotificationCompat
                .Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Hello!")
                .setContentText("Hello, Android!")
                // Opens MainActivity on clicking Notification
                .setContentIntent(PendingIntent.getActivity(this,
                        NOTIFICATION_ID,
                        new Intent(this, MainActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT))
                // Remove Notification after clicking
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_notification_icon);
    }

    class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            findViewById(R.id.update_btn).performClick();
        }
    }
}