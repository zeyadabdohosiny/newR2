package com.zeyadabdohosiny.r2.BackEndService;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationManagerCompat;

public class App extends Application {
    public static final String Chaannel_ID="Channel1";
    public static final String Chaannel_ID2="Channel2";

    NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManagerCompat= NotificationManagerCompat.from(this);
        CreateNotification();
    }
    public void CreateNotification(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(
                    Chaannel_ID,
                    "channel1",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("this is Chhanel1");
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            NotificationChannel channe2=new NotificationChannel(
                    Chaannel_ID2,
                    "channel2",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("this is Chhanel2");
            NotificationManager notificationManager2=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channe2);
        }
    }
}
