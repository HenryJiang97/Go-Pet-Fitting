//package com.example.gopetfitting;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.NotificationCompat;
//
//public class SendNotificationActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        createNotificationChannel();
//        setContentView(R.layout.activity_send_notification);
//
//        Button notificationbutton = (Button) findViewById(R.id.sendNotificationButton);
//        notificationbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendNotification(v);
//            }
//        });
//
//    }
//
//    public void createNotificationChannel() {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id), name, importance);
//            channel.setDescription(description);
//
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//
//    public void sendNotification(View view){
//        // Prepare intent which is triggered if the
//        // notification is selected
//        Intent intent = new Intent(this, MomentsActivity.class);
//        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
//
//        PendingIntent momentsIntent = PendingIntent.getActivity(this, (int)System.currentTimeMillis(),
//                new Intent(this, MomentsActivity.class), 0);
//
//
//        // Build notification
//        String channelId = getString(R.string.channel_id);
//
////        Notification noti = new Notification.Builder(this)   DEPRECATED
//        Notification noti = new NotificationCompat.Builder(this,channelId)
//
//                .setContentTitle("New post from Moments")
//                .setContentText("").setSmallIcon(R.drawable.foo)
//
//                .addAction(R.drawable.foo, "Moments", momentsIntent).setContentIntent(pIntent).build();
////                .addAction(R.drawable.icon, "More", pIntent)
////              .addAction(R.drawable.icon, "And more", pIntent).build();
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        // hide the notification after its selected
//        noti.flags |= Notification.FLAG_AUTO_CANCEL ;
//
//        notificationManager.notify(0, noti);
//    }
//
//
//}
