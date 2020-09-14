package com.zeyadabdohosiny.r2;

import android.app.Dialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.Query;
import com.zeyadabdohosiny.r2.Adapters.Recycle_View_Home_Page;
import com.zeyadabdohosiny.r2.MvvmAndRommUserInfo.Shop;
import com.zeyadabdohosiny.r2.MvvmAndRommUserInfo.Users;

import com.zeyadabdohosiny.r2.UiActicity.AboutUsActicty;
import com.zeyadabdohosiny.r2.UiActicity.History_List;
import com.zeyadabdohosiny.r2.UiActicity.MainActivity;
import com.zeyadabdohosiny.r2.UiActicity.MapsActivity;
import com.facebook.login.LoginManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Stack;

import javax.annotation.Nullable;

import com.zeyadabdohosiny.r2.UiActicity.Register_Page;

import static com.zeyadabdohosiny.r2.PlayStationPage.Time_To_Wait_Request;

//import com.onesignal.OneSignal;

public class Test extends AppCompatActivity {
    //Keys
    public final static String TAG = "Home_Page";
    public final static String Shared_Prefs = "sharedPref";
    public final static String UserID = "UserID";
    public final static String Name_Of_The_Intent = "IntentName";
    public static final String Shop_ID = "ShopId";


    //Views

    DrawerLayout drawer;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView nav_View;
    ImageView NavigationHeaderImageview,menu_Image_View;
    TextView HeaderName, HeaderPhone;
    Dialog mydialog;
    EditText searchEditText;

    // Recylce View
    Stack <Shop> stack=new Stack<>();
    ArrayList<Shop> Shops = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private Recycle_View_Home_Page mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //Vriables

    static String UserIda;
    String NameOftheUser, ImageUri, PhoneNumber, NameOftheIntent, NameofThepc;
    int rateOfUser;
    boolean setOrNot;


    //Picasoo Librrary To Upload Pic by Url

    Picasso picasso;

    //FireBase
    private FirebaseFirestore db;
    private static CollectionReference InfoRef;
    private CollectionReference shopsRef;
    // Progress Dialog
    private static final long START_TIME_IN_MILLIS = 10000;

    static CountDownTimer countDownTimer;
    boolean mTimerRunning;
    private static long mTimeLeftInMillis;
    static int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);




        // Deh 3shan at2kd en l user Maym3mlsh kaza requesy
        SharedPreferences sharedPreferencess = getSharedPreferences(Shared_Prefs, MODE_PRIVATE);
        a = sharedPreferencess.getInt(Time_To_Wait_Request, 0);

        drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        nav_View = findViewById(R.id.nav_view);

        final View view = nav_View.inflateHeaderView(R.layout.nav_header);

        NavigationHeaderImageview = view.findViewById(R.id.ProfilePicImgageview);

        nav_View.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Usermenuselected(item);
                return false;

            }
        });

        //FireBaseInstance

        db = FirebaseFirestore.getInstance();
        InfoRef = db.collection("App");
        //GetDataFromTheIntent

        Intent in = getIntent();
        NameOftheIntent = in.getStringExtra(Name_Of_The_Intent).trim();

        UserIda = in.getStringExtra(UserID);
        NameOftheUser = in.getStringExtra(Register_Page.NameOfUser_String);

        //   Log.d(TAG, NameOftheIntent + UserIda+"what is wrong"+ "    "+NameOftheUser);


        //IF User Come From Login Page

        if (UserIda != null && NameOftheIntent.equalsIgnoreCase("Login")) {



            Log.d(TAG, "Login IF");
            //Log.d(TAG, NameOftheUser + ImageUri + PhoneNumber);
            db = FirebaseFirestore.getInstance();
            CollectionReference InfoRef = db.collection("App");
            DocumentReference doc = db.document("App/" + UserIda);
            InfoRef.document(UserIda).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    Users users = documentSnapshot.toObject(Users.class);
                    UserIda = users.getUserIdToken();
                    NameOftheUser = users.getName();
                    ImageUri = users.getImage_Uri();
                    PhoneNumber = users.getPhone();
                    rateOfUser=users.getRate();
                    Log.d(TAG, "" + UserIda + NameOftheUser +"  yastaaaaaa   " +rateOfUser);
                    // ٍ SHared Pref To Safe Data
                    SharedPreferences ref = getSharedPreferences(Shared_Prefs, MODE_PRIVATE);
                    SharedPreferences.Editor editor = ref.edit();
                    editor.putString(UserID, UserIda);
                    editor.putString(Register_Page.User_Profle_Uri, ImageUri);
                    editor.putString(Register_Page.NameOfUser_String, NameOftheUser);
                    editor.putString(Register_Page.User_Phone_Number, PhoneNumber);
                    Log.d(TAG, "wakla" +rateOfUser);
                    editor.apply();


                }
            });

        }


        // If UserComeFrom RegisterPage

        if (UserIda != null && NameOftheIntent.equalsIgnoreCase("Register")) {
            Log.d(TAG, "Da5al L Rgister");
            in = getIntent();
            UserIda = in.getStringExtra(UserID);
            NameOftheUser = in.getStringExtra(Register_Page.NameOfUser_String);
            ImageUri = in.getStringExtra(Register_Page.User_Profle_Uri);
            PhoneNumber = in.getStringExtra(Register_Page.User_Phone_Number);
            Log.d(TAG, NameOftheUser);
            // One Signal

            // ٍ SHared Pref To Safe Data
            SharedPreferences ref = getSharedPreferences(Shared_Prefs, MODE_PRIVATE);
            SharedPreferences.Editor editor = ref.edit();
            editor.putString(UserID, UserIda);
            editor.putString(Register_Page.User_Profle_Uri, ImageUri);
            editor.putString(Register_Page.NameOfUser_String, NameOftheUser);
            editor.putString(Register_Page.User_Phone_Number, PhoneNumber);
            editor.apply();


        }


        // If UserClose The App And Open It Again

        if (UserIda == null) {

            SharedPreferences sharedPreferences = getSharedPreferences(Shared_Prefs, MODE_PRIVATE);
            UserIda = sharedPreferences.getString(UserID, "");
            NameOftheUser = sharedPreferences.getString(Register_Page.NameOfUser_String, "");
            PhoneNumber = sharedPreferences.getString(Register_Page.User_Phone_Number, "");
            ImageUri = sharedPreferences.getString(Register_Page.User_Profle_Uri, "");



        }
        CollectionReference InfoRef = db.collection("App");
        InfoRef.document(UserIda).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Users users = documentSnapshot.toObject(Users.class);
                rateOfUser=users.getRate();



                if(rateOfUser==0){
                    Toast.makeText(getApplicationContext(), " للاسف انت ممنوع من استخدام الابلكيشن"+rateOfUser, Toast.LENGTH_SHORT).show();
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signOut();
                    UpdateUi();
                    LoginManager.getInstance().logOut();
                    finish();


                }else {
                  //  Toast.makeText(getApplicationContext(), "Mad5lsh l if ezay", Toast.LENGTH_SHORT).show();
                }

            }
        });





        // Cheack If User Request A Device Or not
        if (a != 0) {
            Log.d(TAG, "Da5al If y3ny l ragel lesaloh wa2t");
            Log.d(TAG, "aaaa" + a);
            mTimeLeftInMillis = a;
            showProgreesDialog();


        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Picasso.get().load(ImageUri)
                        .into(NavigationHeaderImageview);
                HeaderName = view.findViewById(R.id.Header_Name);
                HeaderPhone = view.findViewById(R.id.Header_Phone);
                HeaderName.setText(NameOftheUser);
                HeaderPhone.setText(PhoneNumber);
                Log.d(TAG, "Aywa" + NameOftheUser);
            }
        }, 1000);


        //RecycleView For A ListOfThePs
        mRecyclerView = findViewById(R.id.Recycle_View_Shops);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        shopsRef = db.collection("Shop");
        shopsRef.orderBy("rate", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                // This To not More Load oF the pcs
                Shops.clear();

                Shop users = new Shop();
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {

                    users = snapshot.toObject(Shop.class);
                    Shops.add(users);

                 //   Shops=new ArrayList<>(stack);

                  //  Shops.add(users);
                }
                mAdapter = new Recycle_View_Home_Page(Shops);
                mRecyclerView.setAdapter(mAdapter);
                //Send Langtude And Lattidue TO Maps Acticity
                mAdapter.setOnItemClickListnerLocation(new Recycle_View_Home_Page.OnUserItemClickListener() {
                    @Override
                    public void OnItemClick(int Position) {
                        double x=Shops.get(Position).getLangtude();
                        double y=Shops.get(Position).getLatude();
                    //    Toast.makeText(Test.this, "lang"+x+"latd"+y, Toast.LENGTH_SHORT).show();
                        String shopName=Shops.get(Position).getName().toString();
                        Intent in = new Intent(getApplicationContext(), MapsActivity.class);
                        in.putExtra("lang",x);
                        in.putExtra("lat",y);
                        in.putExtra("ShopName",shopName);
                        startActivity(in);
                    }
                });
                mAdapter.setOnItemClickListner(new Recycle_View_Home_Page.OnUserItemClickListener() {
                    @Override
                    public void OnItemClick(int Position) {
                  //      Toast.makeText(Test.this, "" + Shops.get(Position).getName().toString(), Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(getApplicationContext(), PlayStationPage.class);
                        in.putExtra(UserID, UserIda);
                        in.putExtra(Register_Page.NameOfUser_String, NameOftheUser);
                        in.putExtra(Register_Page.User_Profle_Uri, ImageUri);
                        in.putExtra(Shop_ID, Shops.get(Position).getUserIdToken());
                        in.putExtra("Rate",rateOfUser);

                        startActivity(in);
                    }
                });

           mAdapter.setOnItemClickListnerMenu(new Recycle_View_Home_Page.OnUserItemClickListener() {
               @Override
               public void OnItemClick(int Position) {
             //      Toast.makeText(Test.this, "EEEEEEEEEeeeee", Toast.LENGTH_SHORT).show();
                   mydialog=new Dialog(Test.this);
                   mydialog.setContentView(R.layout.dialog_box_for_menu);
                   mydialog.setTitle("Menu");
                   menu_Image_View=mydialog.findViewById(R.id.Image_for_menu);

                   Picasso.get().load(Shops.get(Position).getMenuImage().toString()).into(menu_Image_View);
                   mydialog.show();

               }
           });
                //Shops.clear();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    public void Usermenuselected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_logoutt:
                Log.d(TAG, "Eh ya gd3aaan");
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                UpdateUi();
                LoginManager.getInstance().logOut();
                finish();
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_offer:
                Toast.makeText(this, "OFfer", Toast.LENGTH_SHORT).show();
                break;
            case  R.id.nav_history:
                Intent historyintent=new Intent(this, History_List.class);
                startActivity(historyintent);
                break;
            case R.id.nav_rate:
                Toast.makeText(this, "Your Rate is  "+rateOfUser, Toast.LENGTH_SHORT).show();
                break;
            case  R.id.nav_AboutUs:
                Intent AboutUsintent=new Intent(this, AboutUsActicty.class);
                startActivity(AboutUsintent);
                break;
            case R.id.nav_Search:
             Search();
            break;



        }
    }

    private void Search() {
        // Search Edit Text
        searchEditText=findViewById(R.id.SearchEditText);
        searchEditText.setVisibility(View.VISIBLE);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filtter_search(editable.toString());

            }
        });
    }

    private void filtter_search(String toString) {
        ArrayList<Shop>fillter=new ArrayList<>();
        for(Shop shop: Shops){
              if(shop.getName().toLowerCase().contains(toString.toLowerCase())){
                  fillter.add(shop);
              }
        }
        mAdapter.fillterlist(fillter);
    }


    private void updateUI(FirebaseUser currentUser) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    // Deh 3shan atla3 mn  kos Om L Logout 3shan l Facebook Bara tt8yar
    public void Logout(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(Shared_Prefs, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Register_Page.User_Admin, "");
        editor.apply();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        UpdateUi();
        LoginManager.getInstance().logOut();
        finish();

    }

    private void UpdateUi() {
        Intent in = new Intent(this, MainActivity.class);

        startActivity(in);
        finish();
    }

    public static void showProgreesDialog() {
        Log.d(TAG, "2a3ed wala l2 " + UserIda);
        InfoRef.document(UserIda).update("setOrNot", true);

        countDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {

            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                //   UpdatecountdownText();

                Log.d(TAG,""+l);
            }

            @Override
            public void onFinish() {

                mTimeLeftInMillis = 0;
                InfoRef.document(UserIda).update("setOrNot", false);
                Log.d(TAG, "Yasta " + mTimeLeftInMillis);
                a = 0;

            }
        }.start();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences ref = getSharedPreferences(Shared_Prefs, MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putInt(Time_To_Wait_Request, (int) a);
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences ref = getSharedPreferences(Shared_Prefs, MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putInt(Time_To_Wait_Request, (int) a);
        editor.apply();
        String a;
    }
}
