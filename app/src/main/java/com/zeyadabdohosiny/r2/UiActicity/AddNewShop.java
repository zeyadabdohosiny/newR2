package com.zeyadabdohosiny.r2.UiActicity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.zeyadabdohosiny.r2.MvvmAndRommUserInfo.Shop;
import com.zeyadabdohosiny.r2.PlayStationPage;
import com.zeyadabdohosiny.r2.R;
import com.zeyadabdohosiny.r2.ShopsPages.Shop_Pc;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import static android.R.layout.simple_expandable_list_item_1;
import static com.zeyadabdohosiny.r2.UiActicity.Register_Page.NameOfUser_String;
import static com.zeyadabdohosiny.r2.UiActicity.Register_Page.User_Profle_Uri;
import static com.zeyadabdohosiny.r2.Test.Shop_ID;
import static com.zeyadabdohosiny.r2.Test.UserID;

public class AddNewShop extends AppCompatActivity {
    //Static Variables
    public static final int Pick_Image_Request = 2;
    //
    // Ways To Upload The Image
    StorageReference mStorageRef;
    StorageTask mUploadTask;
    Uri mImageUri;
    //
    Button menuButton;
    EditText shopLang,shopLatd,Password;
    // Spinner
    Spinner spinner;
    FirebaseFirestore database;
    CollectionReference LocationRef;
    CollectionReference PcRef;
    CollectionReference ShopRef;
    // Variables
    String userImageUri,nameofTheShop,userLong,userlatd;
    String [] myarray ={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"};
    int numberOfPcs;

    ArrayAdapter <CharSequence> arrayAdapter;

    String UserKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_shop);
        // FireBase Instance
        database=FirebaseFirestore.getInstance();
        LocationRef=database.collection("Shop");
        mStorageRef = FirebaseStorage.getInstance().getReference("Upload");


        //Spinner
        UserKey=getIntent().getStringExtra(UserID);

        spinner=findViewById(R.id.spinnerForShops);
        shopLang=findViewById(R.id.Lang);
        shopLatd=findViewById(R.id.Latd);
        Password=findViewById(R.id.newShopPassword);
        menuButton=findViewById(R.id.button_for_menu);
        arrayAdapter=new ArrayAdapter<CharSequence>(this, simple_expandable_list_item_1,myarray);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               String a=adapterView.getItemAtPosition(i).toString();
               numberOfPcs=Integer.parseInt(a);



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Toast.makeText(this, ""+numberOfPcs, Toast.LENGTH_SHORT).show();
       menuButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               OpenFileChosen();
           }
       });

    }

    public void AddnewShop(View view) {
       // Toast.makeText(this, ""+Password.getText().toString(), Toast.LENGTH_SHORT).show();
        if (Password.getText().toString() .equalsIgnoreCase("terrors123456")==true) {
            //Saved Data Of the SHop And go To Ps Page
            nameofTheShop=getIntent().getStringExtra(NameOfUser_String);
            userImageUri=getIntent().getStringExtra(User_Profle_Uri);

            ShopRef=database.collection("Shop");
            PcRef = database.collection("Shop");

            double lang=Double.parseDouble(shopLang.getText().toString());
            double latd=Double.parseDouble(shopLatd.getText().toString());

            ShopRef.document(UserKey).set(new Shop(UserKey,nameofTheShop, userImageUri ,lang,latd));

            for (int i = 1; i < numberOfPcs; i++) {
                Shop_Pc shop_pc = new Shop_Pc(UserKey,nameofTheShop,"PS"+i, false, "https://firebasestorage.googleapis.com/v0/b/r2firebaseproject-8587c.appspot.com/o/Upload%2Ffb765b8752d50de50cfa15203f9a7acd.png?alt=media&token=37b685bb-3b74-4eac-8a34-77085f5b75c3");
                PcRef.document(UserKey).collection("Pcs").document("PS" + i).set(shop_pc);
            }
            UploadTask();
            Intent in = new Intent(this, PlayStationPage.class);
            in.putExtra(Shop_ID, UserKey);
            startActivity(in);
            finish();
        }else {
            Toast.makeText(this, "كسمك يا معرص", Toast.LENGTH_SHORT).show();
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
 public void   UploadTask(){
     String Mynewuri;
     final StorageReference filerefrance = mStorageRef.child(System.currentTimeMillis() + "-" + getFileExtension(mImageUri));
     mUploadTask=filerefrance.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
         @Override
         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               filerefrance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       ShopRef=database.collection("Shop");
                       ShopRef.document(UserKey).update("menuImage",uri.toString());

                   }
               });
         }
     });
 }


}
