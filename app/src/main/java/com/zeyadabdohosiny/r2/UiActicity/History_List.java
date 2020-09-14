package com.zeyadabdohosiny.r2.UiActicity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.zeyadabdohosiny.r2.Adapters.History_List_RecycleView;
import com.zeyadabdohosiny.r2.MvvmAndRommUserInfo.Shop;
import com.zeyadabdohosiny.r2.R;
import com.zeyadabdohosiny.r2.RequestModel.HistoryModle;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class History_List extends AppCompatActivity {
    // Array List

    ArrayList<HistoryModle> historyList = new ArrayList<>();

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
    CollectionReference rateOfTheShopCollection;

    //FireBaseAuth

    FirebaseAuth firebaseAuth;

    // Eamples Dialogs

    Dialog rateDialog;

    // variables
    int rateOfTheShop, rateOfTheShopBefore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history__list);
        // Ads

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Ads
                AdView mAdView;
                MobileAds.initialize(getApplicationContext(), "ca-app-pub-3034528190190998~4899485261");
                mAdView = findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);


            }
        }, 2000);

        //FireStoreCode And recycle View

        database = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        mrecyclerView = findViewById(R.id.History_RecycleView);
        mlayoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(mlayoutManager);
        HistroyRef = database.collection("App");
        HistroyRef.document(firebaseAuth.getCurrentUser().getUid()).collection("History").addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                try {
                    // I clear The Data 3shan mat3mlsh Upload keter

                    historyList.clear();
                    //
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                        HistoryModle modle = snapshot.toObject(HistoryModle.class);
                        historyList.add(modle);

                    }
                    adapter = new History_List_RecycleView(historyList);
                    mrecyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListner(new History_List_RecycleView.OnUserItemClickListener() {
                        @Override
                        public void OnItemClick(final int Position) {
                            // Make A dialog That can the User Rate The Shop
                            rateDialog = new Dialog(History_List.this);
                            rateDialog.setContentView(R.layout.rate_dialog);
                            rateet = rateDialog.findViewById(R.id.takeRateFromUser);
                            rateTheShop = rateDialog.findViewById(R.id.Rate_button);


                            rateTheShop.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    rateOfTheShop = Integer.parseInt(rateet.getText().toString());
                                    if (rateOfTheShop == 1 || rateOfTheShop == 2 || rateOfTheShop == 3) {
                                        rateOfTheShopCollection = database.collection("Shop");
                                        rateOfTheShopCollection.document(historyList.get(Position).getShop_id()).get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        Shop shop=documentSnapshot.toObject(Shop.class);
                                                        rateOfTheShopBefore=shop.getRate();
                                                        rateOfTheShopCollection.document(historyList.get(Position)
                                                                .getShop_id()).update("Rate", rateOfTheShopBefore + rateOfTheShop);
                                                        Toast.makeText(History_List.this
                                                                , "" + rateOfTheShop + historyList.get(Position).getShop_id(),
                                                                Toast.LENGTH_SHORT).show();

                                                    }
                                                });


                                    } else {
                                        Toast.makeText(History_List.this, "من فضلك تأكد من رقم التقييم", Toast.LENGTH_SHORT).show();
                                    }
                                    String a = rateet.getText().toString();
                                    Toast.makeText(History_List.this, "Yasta", Toast.LENGTH_SHORT).show();
                                }
                            });
                            rateDialog.show();


                        }
                    });
                } catch (Exception x) {
                    Toast.makeText(History_List.this, " للاسف ليس لديك سابق حجز ", Toast.LENGTH_SHORT).show();
                    Log.d("HistoryPage", "" + x);
                }

            }
        });
    }


}
