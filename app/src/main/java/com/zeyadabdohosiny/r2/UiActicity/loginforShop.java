package com.zeyadabdohosiny.r2.UiActicity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zeyadabdohosiny.r2.PlayStationPage;
import com.zeyadabdohosiny.r2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.zeyadabdohosiny.r2.Test.Shared_Prefs;
import static com.zeyadabdohosiny.r2.Test.Shop_ID;
import static com.zeyadabdohosiny.r2.UiActicity.MainActivity.Name_Of_The_Intent;
import static com.zeyadabdohosiny.r2.UiActicity.Register_Page.User_Admin;

public class loginforShop extends AppCompatActivity {
    // My TAgs
    public static final String TAG = "LoginPage";
    //Views
    EditText Username, Pssword;

    //Strings
    String userName, userPassword;
    //FireBaseAuth
    FirebaseAuth firebaseAuth;
    FirebaseFirestore database;
    CollectionReference AppCollection;
    //Deh laazmtha lma Hafta7 l home mn L Regst Y3ml ll Activity deh finish
    public static Activity Login_Acticity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginfor_shop);
        //Id Of Views
        //FireBaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        Username = findViewById(R.id.Login_UserName);
        Pssword = findViewById(R.id.Login_Paswword);
        //ActicityInstance
        Login_Acticity = this;


    }

    public void Login(View view) {

        userName = Username.getText().toString().trim();
        userPassword = Pssword.getText().toString().trim();

        try {

            firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                    //    Toast.makeText(Login_Acticity, "ya zmele", Toast.LENGTH_SHORT).show();

                        if (userName.contains("@Admin.com")) {
                  //          Toast.makeText(Login_Acticity, "ya zmele", Toast.LENGTH_SHORT).show();

                            Intent In = new Intent(getApplicationContext(), PlayStationPage.class);
                            In.putExtra(Name_Of_The_Intent, "login");
                            In.putExtra(Shop_ID, firebaseAuth.getCurrentUser().getUid());
                            // Add The Shared Pref 3shan Adman In sa7eb l ma7el yfdal f saf7et ma7lo
                            SharedPreferences sharedPreferences = getSharedPreferences(Shared_Prefs, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(User_Admin, "@Admin.com");
                            editor.apply();
                            startActivity(In);
                            Login_Acticity.finish();


                        }

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "" + e);
                }
            });

        } catch (Exception x) {


        }
    }
}
