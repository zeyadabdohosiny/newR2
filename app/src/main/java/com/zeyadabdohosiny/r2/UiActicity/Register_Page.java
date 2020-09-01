package com.zeyadabdohosiny.r2.UiActicity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.zeyadabdohosiny.r2.MvvmAndRommUserInfo.Users;
import com.zeyadabdohosiny.r2.R;
import com.zeyadabdohosiny.r2.Test;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import static com.zeyadabdohosiny.r2.Test.Shared_Prefs;
import static com.zeyadabdohosiny.r2.Test.UserID;

public class Register_Page extends AppCompatActivity {
    // My Final Variables
    public static final String TAG = "Register Page";
    //Keys
    public static final String NameOfUser_String = "NameOfUser";
    public static final int Pick_Image_Request = 1;
    public static final String User_Profle_Uri = "UserProifleUri";
    public static final String User_Phone_Number = "UserPhoneNumber";
    public static final String User_Admin = "UserOrAdmin";
    static int Preqcode = 1;
    //Views
    EditText etFistName, etLastname, etUsername, etPassword, etConfirmPassword, etPhone;
    ImageView proflePic;
    Button SelectProfilePic;
    CheckBox checkBox;
    //Variables
    String RegisterFirstName, RegisterLastname, RegisterUserName, RegisterPassword, RegisterConfirmPaswword, RegisterPhone, Fullname, Token;

    // Ways To Upload The Image
    StorageReference mStorageRef;
    StorageTask mUploadTask;
    String Name;
    Uri mImageUri;
    // Uri ImageUri;
    //FireBaseAuth
    FirebaseAuth firebaseAuth;
    ///////// FireBaseFireStroe
    FirebaseFirestore database;
    CollectionReference AppCollection;
    CollectionReference UserCollectionn;
    CollectionReference ShopRef;
    CollectionReference userGetRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__page);
        etFistName = findViewById(R.id.RegFirstName);
        etLastname = findViewById(R.id.RegisterLastName);
        etUsername = findViewById(R.id.RegUserName);
        etPassword = findViewById(R.id.RegPassword);
        etConfirmPassword = findViewById(R.id.RegConfirmPassword);
        etPhone = findViewById(R.id.RegPhoneNumber);
        SelectProfilePic = findViewById(R.id.ProfilePic);
        checkBox = findViewById(R.id.checkBox);

        //Ta3refat l FIre Base
        database = FirebaseFirestore.getInstance();
        AppCollection = database.collection("App");
        UserCollectionn = database.collection("Users");
        ShopRef = database.collection("Shop");
        userGetRequest = database.collection("GetRequest");
        // Takmlet Edafet l sora 3ala L fireBase
        mStorageRef = FirebaseStorage.getInstance().getReference("Upload");
        //////////////////// This Will Open The Gellaary
        SelectProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           if(Build.VERSION.SDK_INT >=20){
               checkAndRequestForPermtion();
           }else {

               OpenFileChosen();
           }

            }
        });
        //FireBase Auth
        firebaseAuth = FirebaseAuth.getInstance();


    }


    // ///////////////            Register Button
    public void Register(View view) {
        RegisterFirstName = etFistName.getText().toString();
        RegisterLastname = etLastname.getText().toString();
        RegisterUserName = etUsername.getText().toString().trim();
        RegisterPassword = etPassword.getText().toString();
        RegisterConfirmPaswword = etConfirmPassword.getText().toString();
        RegisterPhone = etPhone.getText().toString();
        //ElMAfrod A3ml Cheach Ashof feh UserName Nafs l esm Wala laaa


        ///        Bt2ked en L Views Mesh fadya
        if (RegisterFirstName.isEmpty() || RegisterLastname.isEmpty() || RegisterUserName.isEmpty() || RegisterPassword.isEmpty() || RegisterConfirmPaswword.isEmpty() || RegisterPhone.isEmpty()) {
            Toast.makeText(this, "من فضلك اكمل التسجيل", Toast.LENGTH_SHORT).show();
            // Bat2ked en L Mail Feh (@)
        } else if (RegisterUserName.contains("@Admin.com") == false) {
            Toast.makeText(this, "Wroong Mail", Toast.LENGTH_SHORT).show();
            //Bat2ked En Paswword w L confirm Bta3o Zay ba3d
        } else if (RegisterPassword.equalsIgnoreCase(RegisterConfirmPaswword) == false) {
            Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
        } else if (checkBox.isChecked() == false) {
            Toast.makeText(this, "بالرجاء قراءه الشروط و الاحكام ", Toast.LENGTH_SHORT).show();
        } else if (RegisterPhone.contains("010") == true && RegisterPhone.contains("012") == true && RegisterPhone.contains("011") == true || RegisterPhone.contains("015") == true) {
            Toast.makeText(this, "تأكد من رقم الموبيل", Toast.LENGTH_SHORT).show();
        } else if (RegisterPhone.length() != 11) {
            Toast.makeText(this, "تأكد من عدد ارقام رقم الموبيل", Toast.LENGTH_SHORT).show();
        }

        //  Create New Account
        else {

            Fullname = RegisterFirstName + "" + RegisterLastname;
            firebaseAuth.createUserWithEmailAndPassword(RegisterUserName, RegisterConfirmPaswword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    if (RegisterUserName.equalsIgnoreCase("@Admin.com")) {
                        // UploadTasl(RegisterUserName);

                    }
                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                    // B3mel Table F  FireDataBase Bl User Info + Ba5od L Id Bta3o m3aya LL Home Page

                    Log.d(TAG, "Method is Run");
                    //Send Data To Next Page
                    Intent in = new Intent(getApplicationContext(), Test.class);

                    UploadTasl(RegisterUserName);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Register_Page.this, "" + e, Toast.LENGTH_SHORT).show();
                }
            });

        }


    }

    // This Method Open The Gellary
    public void OpenFileChosen() {
        Intent in = new Intent();
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(in, Pick_Image_Request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Pick_Image_Request && resultCode == RESULT_OK && data != null) {
            mImageUri = data.getData();

        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    public void UploadTasl(final String name) {
        // Toast.makeText(Register_Page.this, ""+name, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Da5al el if " + name);
        final StorageReference filerefrance = mStorageRef.child(System.currentTimeMillis() + "-" + getFileExtension(mImageUri));
        mUploadTask = filerefrance.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filerefrance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (name.contains("@Admin.com") == true) {
                            Toast.makeText(Register_Page.this, "Da5al if", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Da5al el if bta3et l register bta3et l admin ");
                            //

                            Intent in = new Intent(getApplicationContext(), AddNewShop.class);
                            in.putExtra(UserID, firebaseAuth.getCurrentUser().getUid());
                            in.putExtra(NameOfUser_String, Fullname);
                            // HEna bafra2 ezay kan l ragel sa7eb  m7l b5leh yfdal gwa f l Saf7a bta3to
                            in.putExtra(User_Admin, name);
                            Log.d(TAG, Fullname);
                            in.putExtra(User_Profle_Uri, uri.toString());
                            in.putExtra(User_Phone_Number, RegisterPhone);
                            in.putExtra(NameOfUser_String, Fullname);
                            // Add The Shared Pref 3shan Adman In sa7eb l ma7el yfdal f saf7et ma7lo
                            SharedPreferences sharedPreferences = getSharedPreferences(Shared_Prefs, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(User_Admin, name);
                            editor.apply();
                            startActivity(in);
                            ShopRef.document(firebaseAuth.getCurrentUser().getUid()).set(new Users(Fullname, RegisterUserName, firebaseAuth.getCurrentUser().getUid(), uri.toString(), RegisterPhone));
                            Log.d(TAG, "" + uri);
                            MainActivity.Login_Acticity.finish();
                            finish();

                        }
                    }
                });
            }
        });


    }


    public void Conditions(View view) {
    }

    public void Login(View view) {
        Intent in = new Intent(this, loginforShop.class);
        startActivity(in);
        this.finish();

    }

    public void checkAndRequestForPermtion() {
        if (ContextCompat.checkSelfPermission(Register_Page.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Register_Page.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Please Accept for this permtion", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(Register_Page.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        Preqcode);


            }
        } else
            OpenFileChosen();

    }

}