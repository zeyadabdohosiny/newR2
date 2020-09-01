package com.zeyadabdohosiny.r2.BackEndService;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.zeyadabdohosiny.r2.PlayStationPage;
import com.zeyadabdohosiny.r2.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import com.zeyadabdohosiny.r2.Test;

public class ForGroundService extends Service {
    public static final String TAG = "ForgroundService";
    boolean a = true;
    int b=0;
    FirebaseFirestore firestore;
    CollectionReference responseRequest;
    public static long mTimer = 60000;
    public static final int JOB_ID = 2;
    private static long mTimeLeftInMillis;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: "+a);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        Intent in = new Intent(getApplicationContext(), PlayStationPage.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, in, 0);
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), App.Chaannel_ID)
                .setContentText("New Notefication")
                .setContentTitle(" من فضلك انتظر خمس دقايق حتي يتم رد المحل")
              //  .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.welcomeicon)
                .build();
        startForeground(1233, notification);

        Log.d(TAG, "El Serivce Sha8ala" + a);
        String userId = intent.getStringExtra(Test.UserID);
        firestore = FirebaseFirestore.getInstance();
        final String shopName = intent.getStringExtra("Shop_Name");
        a=true;
        responseRequest = firestore.collection("App").document(userId).collection("ResponseRequset");

            responseRequest.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                    Log.d(TAG, "Bdayet l Service" + a);

                    Log.d(TAG, " El ASoool May5oshsh Hena" + a);
                    if (a==false) {
                        Log.d(TAG, " da5al el if" + a);
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                        Intent in = new Intent(getApplicationContext(), PlayStationPage.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, in, 0);
                        Notification notification1 = new NotificationCompat.Builder(getApplicationContext(), App.Chaannel_ID2)
                                .setContentText("بالرجاء التوجه سريعا الي المحل  ")
                                .setContentTitle(" لقد تم حجزك امامك 15 دقيقه للاتجاه الي المحل   " + shopName)
                //                .setContentIntent(pendingIntent)
                                .setSmallIcon(R.drawable.welcomeicon)
                                .build();
                        notificationManager.notify(2, notification1);
                    }
                    a=false;
                }
            });

      //  a = false;
        Log.d(TAG, "Wallla");
        mTimeLeftInMillis =300000;
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                CountDownTimer countDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                    @Override
                    public void onTick(long l) {
                        mTimeLeftInMillis = l;
                        Log.d(TAG, "onTick: " + l);
                    }

                    @Override
                    public void onFinish() {
                        mTimeLeftInMillis = 0;
                        stopService(intent);
                    }
                }.start();


            }
        });


        return START_NOT_STICKY;
    }
}
