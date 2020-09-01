package com.zeyadabdohosiny.r2;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zeyadabdohosiny.r2.Adapters.RecycleViewForRequestPage;
import com.zeyadabdohosiny.r2.MvvmAndRommUserInfo.Users;

import com.zeyadabdohosiny.r2.RequestModel.HistoryModle;
import com.zeyadabdohosiny.r2.RequestModel.Requestmodel;
import com.zeyadabdohosiny.r2.RequestModel.Response;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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

public class Requested_Page extends AppCompatActivity {
    public final static String TAG = "NoteficationPagee";
    ArrayList<Requestmodel> list = new ArrayList<Requestmodel>();
    private RecyclerView mRecyclerView;
    private RecycleViewForRequestPage mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String IdOfUser ;
    String currentTime;
    String nameOfTheShop, shopImage,numberofPc;
    int rateoftheUser;
    // Dialog Lt2ked l 7agz
    Dialog mydialog;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore database;
    CollectionReference PcRef;
    CollectionReference RoomCollection;
    CollectionReference RequestRef;
    CollectionReference responseRequest;
    DocumentReference rateCollection;
    CollectionReference historyRef;
    DocumentReference dataOfTheShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested__page);

        firebaseAuth = FirebaseAuth.getInstance();
        mRecyclerView = findViewById(R.id.Requested_Page_Recycleview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();


        //
        RoomCollection = database.collection("Shop").document(firebaseAuth.getCurrentUser().getUid().toString()).collection("Request Room");
        RoomCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                list.clear();
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    Requestmodel requestmodel = snapshot.toObject(Requestmodel.class);
                    list.add(requestmodel);
                    list.get(0).getUserId();

                }

                mAdapter = new RecycleViewForRequestPage(list);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                mAdapter.setOnItemClickListnerRejectedButton(new RecycleViewForRequestPage.OnUserItemClickListener() {
                    @Override
                    public void OnItemClick(int Position) {
                        final  String UserId = list.get(Position).getUserId();
                        DocumentReference yasta = database.collection("Shop").document(firebaseAuth.getCurrentUser().getUid().toString()).collection("Request Room").document(UserId);
                        yasta.delete();

                    }
                });

                mAdapter.setOnItemClickListnerAcceptedButton(new RecycleViewForRequestPage.OnUserItemClickListener() {
                    @Override
                    public void OnItemClick(final int Position) {

                        mydialog=new Dialog(Requested_Page.this);
                        mydialog.setContentView(R.layout.dialog_confirm);
                        mydialog.setTitle("تنبيه");
                        Button b=mydialog.findViewById(R.id.Dialog_Confirm);

                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                IdOfUser = list.get(Position).getUserId();
                                Log.d(TAG, IdOfUser);

                                //  Log.d(TAG, IdOfUser);
                                responseRequest = database.collection("App").document(IdOfUser).collection("ResponseRequset");
                                responseRequest.add(new Response(true));



                            }
                        });
                        mydialog.show();

                    }
                });
                mAdapter.setOnItemClickListnerUserComeButton(new RecycleViewForRequestPage.OnUserItemClickListener() {
                    @Override
                    public void OnItemClick(int Position) {
                        final String UserId = list.get(Position).getUserId();
                        Log.d(TAG, "" + UserId);
                        rateCollection = database.collection("App").document(UserId);
                        // Add to History of The User
                        dataOfTheShop = database.collection("Shop").document(firebaseAuth.getCurrentUser().getUid());
                        historyRef = database.collection("App").document(IdOfUser).collection("History");
                        dataOfTheShop.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                Users users = documentSnapshot.toObject(Users.class);
                           //     Toast.makeText(Requested_Page.this, "Mara f Mara", Toast.LENGTH_SHORT).show();
                                Calendar calendar = Calendar.getInstance();
                                nameOfTheShop=users.getName();
                                shopImage=users.getImage_Uri();
                                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                currentTime = format.format(calendar.getTime());




                            }
                        });
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                historyRef.add(new HistoryModle(nameOfTheShop, shopImage,
                                        currentTime,firebaseAuth.getCurrentUser().getUid(),"Come"));
                            }
                        },1000);
                        rateCollection.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Users user = documentSnapshot.toObject(Users.class);
                                int rate = user.getRate();
                                rateCollection.update("rate", rate + 1);
                                DocumentReference yasta = database.collection("Shop").document(firebaseAuth.getCurrentUser().getUid().toString()).collection("Request Room").document(UserId);
                                yasta.delete();



                            }
                        });


                    }
                });
                mAdapter.setOnItemClickListnerUserDontButton(new RecycleViewForRequestPage.OnUserItemClickListener() {
                    @Override
                    public void OnItemClick(int Position) {
                        // Add to History of The User
                        dataOfTheShop = database.collection("Shop").document(firebaseAuth.getCurrentUser().getUid());
                        historyRef = database.collection("App").document(IdOfUser).collection("History");
                        dataOfTheShop.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                Users users = documentSnapshot.toObject(Users.class);
                                Toast.makeText(Requested_Page.this, "Mara f Mara", Toast.LENGTH_SHORT).show();
                                Calendar calendar = Calendar.getInstance();
                                nameOfTheShop=users.getName();
                                shopImage=users.getImage_Uri();
                                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd  HH:mm");

                                currentTime = format.format(calendar.getTime());




                            }
                        });
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                historyRef.add(new HistoryModle(nameOfTheShop, shopImage,
                                        currentTime,firebaseAuth.getCurrentUser().getUid(),"Don`t Come"));
                            }
                        },1000);

                        final String UserId = list.get(Position).getUserId();
                        rateCollection = database.collection("App").document(UserId);
                        rateCollection.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Users user = documentSnapshot.toObject(Users.class);
                                int rate = user.getRate();
                                rateCollection.update("rate", rate-1);
                                DocumentReference yasta = database.collection("Shop")
                                        .document(firebaseAuth.getCurrentUser()
                                                .getUid().toString()).collection("Request Room").document(UserId);
                                yasta.delete();
                            }
                        });


                    }
                });


            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final String UserId = list.get(viewHolder.getAdapterPosition()).getUserId();
              //  Toast.makeText(Requested_Page.this, "00000000000", Toast.LENGTH_SHORT).show();
                DocumentReference yasta = database.collection("Shop").document(firebaseAuth.getCurrentUser().getUid().toString()).
                        collection("Request Room").document(UserId);
                yasta.delete();
            }
        }).attachToRecyclerView(mRecyclerView);
    }



}

