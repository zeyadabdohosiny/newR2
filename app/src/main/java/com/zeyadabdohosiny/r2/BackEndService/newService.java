package com.zeyadabdohosiny.r2.BackEndService;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.zeyadabdohosiny.r2.PlayStationPage;
import com.zeyadabdohosiny.r2.R;
import com.zeyadabdohosiny.r2.RequestModel.Requestmodel;
import com.zeyadabdohosiny.r2.Requested_Page;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static com.zeyadabdohosiny.r2.BackEndService.App.Chaannel_ID;
//import static com.example.r2.BackEndService.App.Chaannel_ID2;
import static com.zeyadabdohosiny.r2.BackEndService.App.Chaannel_ID2;
import static com.zeyadabdohosiny.r2.Test.Shop_ID;

public class newService extends Service {
    // Variables
    String userWhoRequest;
    String nameOfPcWhichRequuested;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    CollectionReference RequestRef;
    private static final String TAG = "my new Service";
    public static final int JOB_ID = 3;
    String ShopId;
    boolean isopen = true;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        Intent in = new Intent(getApplicationContext(), PlayStationPage.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, in, 0);
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), Chaannel_ID)
                .setContentText("R2")
                .setContentTitle(" App Is Running ")
                .setSound(null)
                .setSmallIcon(R.drawable.welcomeicon)
                .build();
        startForeground(2,notification);

        Log.d(TAG, "Service Run");
        ShopId = intent.getStringExtra(Shop_ID);
        RequestRef = firestore.collection("Shop").document(ShopId).collection("Request Room");
        RequestRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
            //    if (isopen == false) {
                    Requestmodel requestmodel = new Requestmodel();
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {

                        requestmodel = snapshot.toObject(Requestmodel.class);
                        userWhoRequest = requestmodel.getUserId();
                        nameOfPcWhichRequuested = requestmodel.getNameofThepc();


                    }
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                    Intent in = new Intent(getApplicationContext(), Requested_Page.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, in, 0);
                    Notification notification = new NotificationCompat.Builder(getApplicationContext(), Chaannel_ID2)
                            .setContentText(" تنبيه")
                            .setContentTitle(" من الممكن ان يكون لديك طلب حجز ")
                            .setContentIntent(pendingIntent)
                            .setSmallIcon(R.drawable.welcomeicon)
                            .build();
                    notificationManager.notify(1,notification);


                    //   notificationManager.notify(1233, notification);
              //  }

                isopen = false;

            }
        });




        Log.d(TAG, "Ya rab YaMo Sahel");

        return START_NOT_STICKY;
    }
}
