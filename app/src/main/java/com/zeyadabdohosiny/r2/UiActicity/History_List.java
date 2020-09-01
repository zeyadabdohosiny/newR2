package com.zeyadabdohosiny.r2.UiActicity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zeyadabdohosiny.r2.Adapters.History_List_RecycleView;
import com.zeyadabdohosiny.r2.R;
import com.zeyadabdohosiny.r2.RequestModel.HistoryModle;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class History_List extends AppCompatActivity {
    // Array List
    ArrayList<HistoryModle> historyList=new ArrayList<>();
    // Views
    EditText rateet;
    Button rateTheShop;
    // RecylceView
    RecyclerView mrecyclerView;
    RecyclerView.LayoutManager mlayoutManager;
    History_List_RecycleView adapter;
    //FireStore
    FirebaseFirestore database;
    CollectionReference HistroyRef;
    //FireBaseAuth
    FirebaseAuth firebaseAuth;
    // Eamples Dialogs
    Dialog rateDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history__list);
       // Ads
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Ads
                AdView mAdView;
                MobileAds.initialize(getApplicationContext(),"ca-app-pub-3034528190190998~4899485261");
                mAdView = findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);


            }
        },2000);

            database=FirebaseFirestore.getInstance();
            firebaseAuth=FirebaseAuth.getInstance();
            mrecyclerView=findViewById(R.id.History_RecycleView);
            mlayoutManager=new LinearLayoutManager(this);
            mrecyclerView.setLayoutManager(mlayoutManager);
            HistroyRef=database.collection("App");
            HistroyRef.document(firebaseAuth.getCurrentUser().getUid()).collection("History").addSnapshotListener(new EventListener<QuerySnapshot>() {

                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                try {
                    historyList.clear();
                    for (QueryDocumentSnapshot snapshot:queryDocumentSnapshots){
                        HistoryModle modle=snapshot.toObject(HistoryModle.class);
                        historyList.add(modle);

                    }
                    adapter=new History_List_RecycleView(historyList);
                    mrecyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListner(new History_List_RecycleView.OnUserItemClickListener() {
                        @Override
                        public void OnItemClick(int Position) {
                        rateDialog=new Dialog(History_List.this);
                        rateDialog.setContentView(R.layout.rate_dialog);
                        rateet =rateDialog.findViewById(R.id.takeRateFromUser);
                        rateTheShop=rateDialog.findViewById(R.id.Rate_button);
                        rateTheShop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String a=rateet.getText().toString();
                      //          Toast.makeText(History_List.this, "Yasta", Toast.LENGTH_SHORT).show();
                            }
                        });
                        rateDialog.show();


                        }
                    });
                }catch (Exception x){
                    Toast.makeText(History_List.this, " للاسف ليس لديك سابق حجز ", Toast.LENGTH_SHORT).show();
                    Log.d("HistoryPage",""+x);
                }

                }
            });
        }




}
