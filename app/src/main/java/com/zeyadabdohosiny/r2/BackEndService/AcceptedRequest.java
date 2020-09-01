package com.zeyadabdohosiny.r2.BackEndService;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.zeyadabdohosiny.r2.PlayStationPage;
import com.zeyadabdohosiny.r2.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import static com.zeyadabdohosiny.r2.Test.UserID;

public class AcceptedRequest extends JobIntentService {
    public static final String TAG = "Acceptyasta";
     boolean a = true;
    FirebaseFirestore firestore;
    CollectionReference responseRequest;
    public static long mTimer = 60000;
    public static final int JOB_ID = 2;

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, AcceptedRequest.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
//        Toast.makeText(this, "Service Is RUn", Toast.LENGTH_SHORT).show();

        Log.d(TAG, "El Serivce Sha8ala" + a);
        String userId = intent.getStringExtra(UserID);
        firestore = FirebaseFirestore.getInstance();
        final String shopName = intent.getStringExtra("Shop_Name");

        responseRequest = firestore.collection("App").document(userId).collection("ResponseRequset");

        responseRequest.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                Log.d(TAG, "Bdayet l Service"+a);

                if (a == false) {
                    Log.d(TAG, " El ASoool May5oshsh Hena" + a);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                    Intent in = new Intent(getApplicationContext(), PlayStationPage.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, in, 0);
                    Notification notification = new NotificationCompat.Builder(getApplicationContext(), App.Chaannel_ID)
                            .setContentText("New Notefication")
                            .setContentTitle(" لقد تم حجزك من محل   " + shopName)
                            .setSmallIcon(R.drawable.welcomeicon)
                            .build();
                    notificationManager.notify(1233, notification);
                }


                a = false;
            }

        });

        for (int i = 0; i < 25; i++) {
            SystemClock.sleep(10000);
            Log.d(TAG, "onHandleWork:" + i);
            // Toast.makeText(getApplicationContext(), "Hello"+i, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        a = true;
        Log.d(TAG, "l Servce Et2flet " + a);
    }

    @Override
    public boolean onStopCurrentWork() {
        Log.d(TAG, "Current Work Stooped");
        return false;

    }

    @Override
    public boolean isStopped() {
        Log.d(TAG,"Is Stopeed");
        return super.isStopped();
    }
}



