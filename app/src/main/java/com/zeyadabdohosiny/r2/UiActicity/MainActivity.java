package com.zeyadabdohosiny.r2.UiActicity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zeyadabdohosiny.r2.MvvmAndRommUserInfo.Users;
import com.zeyadabdohosiny.r2.PlayStationPage;
import com.zeyadabdohosiny.r2.R;
import com.zeyadabdohosiny.r2.Test;

import java.util.Arrays;

import static com.zeyadabdohosiny.r2.Test.Shared_Prefs;
import static com.zeyadabdohosiny.r2.Test.Shop_ID;
import static com.zeyadabdohosiny.r2.UiActicity.Register_Page.User_Admin;
import static com.zeyadabdohosiny.r2.UiActicity.Register_Page.User_Profle_Uri;
// import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {
    //My Final Strings
    public static final String TAG = "LoginPage";
    public final static String Name_Of_The_Intent = "IntentName";

    //Views
    TextView about_app_textview;
    EditText Username, Pssword;
    //Strings
    String userName, userPassword;
    int rateOfTheUser;
    //  for CHeack new user
    boolean newUser;
    //FireBaseAuth
    FirebaseAuth firebaseAuth;
    //Deh laazmtha lma Hafta7 l home mn L Regst Y3ml ll Activity deh finish
    public static Activity Login_Acticity;
    // This Is For FaceBook Login
    CallbackManager mCallbackManager;

    //Fire Base to Save Data Of faceBookLogin
    FirebaseFirestore database;
    CollectionReference AppCollection;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // About app

        about_app_textview = findViewById(R.id.About_App_tv);
        about_app_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), aboutApp.class);
                startActivity(in);
            }
        });

        // ActicityInstance

        Login_Acticity = this;
        // Shared Pref
        SharedPreferences sharedPreferences = getSharedPreferences(Shared_Prefs, MODE_PRIVATE);
        String Admin = sharedPreferences.getString(User_Admin, "");
        // Log.d(TAG, Admin);
        // Cheack IF User Stell Login

        firebaseAuth = FirebaseAuth.getInstance();
        // fire BAse FireStore


        database = FirebaseFirestore.getInstance();


        if (firebaseAuth.getCurrentUser() != null && Admin.contains("@Admin.com")) {
            Intent In = new Intent(this, PlayStationPage.class);
            In.putExtra(Shop_ID, firebaseAuth.getCurrentUser().getUid());
            Log.d(TAG, firebaseAuth.getCurrentUser().getUid());
            startActivity(In);
            Login_Acticity.finish();
            //  Log.d(TAG, Admin);
            return;

        }


        if (firebaseAuth.getCurrentUser() != null) {
            Log.d(TAG, firebaseAuth.getCurrentUser().getUid());
            Intent In = new Intent(this, Test.class);
            In.putExtra(Name_Of_The_Intent, "login");
            startActivity(In);
            Login_Acticity.finish();
        }


        // Id Of Views

        Username = findViewById(R.id.Login_UserName);
        Pssword = findViewById(R.id.Login_Paswword);

        // Initialize Facebook Login button

        mCallbackManager = CallbackManager.Factory.create();
        final Button loginButton = findViewById(R.id.Facebook_login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // Log.d(TAG, "facebook:onSuccess:" + loginResult);Lo
                        //  Log.d(TAG,"walllllllla ") ;
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        //              Log.d(TAG, "facebook:onCancel");
                        //                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        //                Log.d(TAG, "facebook:onError", error);
                        // ...
                    }
                });
            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Ads

                MobileAds.initialize(getApplicationContext(), "ca-app-pub-3034528190190998~4899485261");
                mAdView = findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);


            }
        }, 2000);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // Go to Rigister Page

    public void Register(View view) {
        Intent intent = new Intent(this, Register_Page.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "بالرجاء الانتباه هذه الصفحه مخصصه لاصحاب المحلات فقط", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    private void handleFacebookAccessToken(final AccessToken token) {
        //  Log.d(TAG, "handleFacebookAccessToken:" + token);
        Log.d(TAG, token.getUserId());
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            FirebaseUser user = firebaseAuth.getCurrentUser();


                            updateUI(user);

                        } else {

                        }


                    }
                });
    }

    private void updateUI(final FirebaseUser user) {

        rateOfTheUser = 200;
        //Toast.makeText(getApplicationContext(), "Da5al hena", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getSharedPreferences(Shared_Prefs, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(User_Admin, "@Yahoo.com");
        editor.apply();
        final Intent in = new Intent(this, Test.class);
        in.putExtra(Name_Of_The_Intent, "login");
        in.putExtra("UserID", user.getUid().toString());
        in.putExtra(User_Profle_Uri, user.getPhotoUrl());
        AppCollection = database.collection("App");
        AppCollection = database.collection("App");
        AppCollection.document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                try {
                    Users users = documentSnapshot.toObject(Users.class);
                    rateOfTheUser = users.getRate();

                } catch (Exception x) {
                    Log.d(TAG, "sfas" + x);

                }


            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (rateOfTheUser == 200) {
                    rateOfTheUser = 3;

                }

                AppCollection.document(user.getUid()).set(new Users(user.getDisplayName(), ""
                        , user.getUid().toString(), user.getPhotoUrl().toString(), ""
                        , rateOfTheUser, false, true));
                startActivity(in);
                finish();
            }

        }, 1000);


    }

}
