package com.example.tturner.roundwatchrendering;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        Intent startIntent = new Intent(this, MainActivity.class);
        PendingIntent startPendingIntent = PendingIntent.getActivity(this,0, startIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent notifIntent = new Intent(this, CustomNotificationActivity.class);
        notifIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent notifPendingIntent = PendingIntent.getActivity(this, 0, notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.WearableExtender wearableExtension = new NotificationCompat.WearableExtender();
        wearableExtension.setDisplayIntent(notifPendingIntent);
        //wearableExtension.setBackground(getNotifBackgroundResId(context));
        wearableExtension.setCustomSizePreset(NotificationCompat.WearableExtender.SIZE_SMALL);
        //wearableExtension.setCustomContentHeight(context.getResources().getDimensionPixelSize(R.dimen.agnostic_view_height));

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .extend(wearableExtension)
                .setContentTitle("Test Notif")
                .setVibrate(new long[] { 0, 200 });

        NotificationManagerCompat.from(this).notify(999, notificationBuilder.build());
    }
}
