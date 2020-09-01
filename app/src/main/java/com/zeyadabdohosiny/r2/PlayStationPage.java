package com.zeyadabdohosiny.r2;

import android.app.Dialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeyadabdohosiny.r2.BackEndService.ForGroundService;
import com.zeyadabdohosiny.r2.Adapters.Recycle_View_List_Pc;
import com.zeyadabdohosiny.r2.BackEndService.newService;

import com.zeyadabdohosiny.r2.RequestModel.Requestmodel;
import com.zeyadabdohosiny.r2.ShopsPages.Shop_Pc;
import com.zeyadabdohosiny.r2.UiActicity.MainActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.annotation.Nullable;

import com.zeyadabdohosiny.r2.UiActicity.Register_Page;

public class PlayStationPage extends AppCompatActivity {
      static final String Time_To_Wait_Request="TimeForWait";
   // Dialog Lt2ked l 7agz
     Dialog mydialog;
 //   private static final long START_TIME_IN_MILLIS = 10000;
    public static final String TAG = "PlayStation_Page";
    Switch aSwitch;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore database;
    CollectionReference PcRef;
    CollectionReference userDataInfo;
    CollectionReference RoomCollection;
    CollectionReference RequestRef;

    static CountDownTimer countDownTimer;
    boolean mTimerRunning;
    private static long mTimeLeftInMillis ;
    //
    ArrayList<Shop_Pc> Shops = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private Recycle_View_List_Pc mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int rate;
    String ShopId;
    String UserId;
    String Admin;
    String userWhoRequest;
    String nameOfPcWhichRequuested;
    String userImageuri;
    int progressstete;
    static   boolean setOrNot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_station_page);
        database = FirebaseFirestore.getInstance();
        userDataInfo=database.collection("App");
        // Test

        // Deh kont 3amlaha 3shan at2ked en l Ragel Sa7eb l m7el yfdl hena
        firebaseAuth = FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences(Test.Shared_Prefs, MODE_PRIVATE);
        Admin = sharedPreferences.getString(Register_Page.User_Admin, "");
        userWhoRequest=getIntent().getStringExtra(Register_Page.NameOfUser_String);
        userImageuri=getIntent().getStringExtra(Register_Page.User_Profle_Uri);
        rate=getIntent().getIntExtra("Rate",0);
        Log.d(TAG,Admin);


        if (Admin.contains("@Admin.com")) {

            ShopId = getIntent().getStringExtra(Test.Shop_ID);
         //   Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"ADmin ");



        } else {
            Log.d(TAG,"Mesh Admin");
           // Toast.makeText(this, "not null", Toast.LENGTH_SHORT).show();
            UserId = getIntent().getStringExtra(Test.UserID);
            Log.d(TAG, ""+UserId);
            ShopId = getIntent().getStringExtra(Test.Shop_ID);
            userDataInfo.document(UserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    setOrNot=documentSnapshot.getBoolean("setOrNot");
                  //  Toast.makeText(PlayStationPage.this, "Bollen"+setOrNot, Toast.LENGTH_SHORT).show();
                }
            });

        }


        Log.d(TAG, ShopId);
        //

        PcRef = database.collection("Shop").document(ShopId).collection("Pcs");


        //
        //If User Set Or Not

        // recylce View Adapter
        mRecyclerView = findViewById(R.id.Pc_List);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        PcRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                // This To not More Load oF the pcs
                Shops.clear();
                //

                Shop_Pc shop_pc = new Shop_Pc();
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    shop_pc = snapshot.toObject(Shop_Pc.class);
                    Shops.add(shop_pc);
                 //   Toast.makeText(PlayStationPage.this, ""+shop_pc.getImageUrl(), Toast.LENGTH_SHORT).show();

                }
                mAdapter = new Recycle_View_List_Pc(Shops,Admin);
                mRecyclerView.setAdapter(mAdapter);
               // mAdapter.notifyDataSetChanged();
              //  Log.d(TAG, "Snapshot" + Shops.get(1).getImageUrl());
              mAdapter.setOnItemClickListnerButton(new Recycle_View_List_Pc.OnUserItemClickListener() {
                  @Override
                  public void OnItemClick(int Position) {

                      final    int pos=Position;
                      mydialog=new Dialog(PlayStationPage.this);
                      mydialog.setContentView(R.layout.dialog_confirm);
                      mydialog.setTitle("تنبيه");
                      Button b=mydialog.findViewById(R.id.Dialog_Confirm);


                      b.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              if(setOrNot==false) {

                                  Calendar calendar = Calendar.getInstance();
                                  SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                                  String currentTime = format.format(calendar.getTime());
                                  Requestmodel req = new Requestmodel(UserId, userWhoRequest, userImageuri,rate, currentTime.toString(), Shops.get(pos).getNumberofpc());
                                  Toast.makeText(PlayStationPage.this, "Btton Click", Toast.LENGTH_SHORT).show();
                                  RoomCollection = database.collection("Shop").document(ShopId).collection("Request Room");
                                  RoomCollection.document(UserId).set(req);
                                  // Call A Dialog to Stop User To make More Than One Request After 5 mn
                                  mTimeLeftInMillis = 300000;
                                  showProgreesDialog();
                                  Intent in=new Intent(getApplicationContext(), ForGroundService.class);
                                  in.putExtra(Test.UserID,UserId);
                                  in.putExtra("Shop_Name",Shops.get(pos).getNumberofpc());
                                  Log.d(TAG,"l Mafrod Deh tesht8al");
                                  startService(in);
                                  //    AcceptedRequest.enqueueWork( getApplicationContext(),in);
                              }else {
                                  Toast.makeText(PlayStationPage.this, " لا يمكنك حجز جهاز اخر قبل رد المحل المحجوز مسبقا", Toast.LENGTH_LONG).show();
                              }
                          }
                      });
                      mydialog.show();



                  }
              });

                mAdapter.setOnItemClickListner(new Recycle_View_List_Pc.OnUserItemClickListener() {
                    @Override
                          public void OnItemClick(int Position) {

               //         Toast.makeText(PlayStationPage.this, "zkex", Toast.LENGTH_SHORT).show();

                       // setOrNot=true;

                    }
                });

               //  Dah 3shan lma Baagy a8yr f Switch l List Bddaf kaza mara F dah 3shan tb2a heya mara wa7da  bas

            }
        });




        // Handle The Service Of The Request To the Shop

        if (Admin.contains("@Admin.com")) {
       //     Toast.makeText(this, "Yasta", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"sHA8AL L Serviceyabny");
            Intent intent = new Intent(getApplicationContext(), newService.class);
            intent.putExtra(Test.Shop_ID, ShopId);
            intent.putExtra(Test.UserID, userWhoRequest);
            intent.putExtra("Hello", nameOfPcWhichRequuested);
            startService(intent);


            }
        }




    public void Logout(View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        UpdateUi();
        LoginManager.getInstance().logOut();
        SharedPreferences sharedPreferences = getSharedPreferences(Test.Shared_Prefs, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Register_Page.User_Admin, "Null");
        editor.apply();
        finish();
    }

    private void UpdateUi() {
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);
        finish();
    }



    public static void showProgreesDialog() {

      //  dialog.setMessage("من فضلك انتظر رد المحل");
       // dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
       // dialog.show();
      //  dialog.setCancelable(false);
        countDownTimer=new CountDownTimer(mTimeLeftInMillis,100 ) {

            @Override
            public void onTick(long l) {
                mTimeLeftInMillis=l;
                Log.d(TAG,"Haaaa"+l);
             //   UpdatecountdownText();
                  setOrNot=true;




            }

            @Override
            public void onFinish() {

                mTimeLeftInMillis=0;
                setOrNot=false;
           //     dialog.setCancelable(true);
                Log.d(TAG,"Yasta "+mTimeLeftInMillis   +setOrNot );



            }
        }.start();


    }

    @Override
    protected void onDestroy() {
        SharedPreferences ref = getSharedPreferences(Test.Shared_Prefs, MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putInt(Time_To_Wait_Request,(int) mTimeLeftInMillis);
        editor.apply();
        Log.d(TAG,""+mTimeLeftInMillis);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater a=getMenuInflater();
        a.inflate(R.menu.requestmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item3:
                   if(Admin.contains("@Admin.com")){
                       Intent in=new Intent(this, Requested_Page.class);
                       startActivity(in);
                   }else {
                       Toast.makeText(this, " للاسف هذه الخاصيه متاحه لاصحاب المحلات فقط", Toast.LENGTH_SHORT).show();
                   }

                   break;

        }
        return super.onOptionsItemSelected(item);
    }
}
