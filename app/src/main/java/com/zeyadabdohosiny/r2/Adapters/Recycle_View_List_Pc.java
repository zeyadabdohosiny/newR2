package com.zeyadabdohosiny.r2.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.zeyadabdohosiny.r2.R;
import com.zeyadabdohosiny.r2.ShopsPages.Shop_Pc;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class Recycle_View_List_Pc extends RecyclerView.Adapter<Recycle_View_List_Pc.ExampleViewHolder> {
    public static final String TAG = "Wenabyb2aaa";
    ArrayList<Shop_Pc> pclist;
    String UserOrAdmin;


    public Recycle_View_List_Pc(ArrayList<Shop_Pc> pclist, String userOrAdmin) {
        this.pclist = pclist;
        UserOrAdmin = userOrAdmin;
    }

    private OnUserItemClickListener mlistener;
    private OnUserItemClickListener mlistener1;
    private OnUserItemClickListener mlistener2;

    public Recycle_View_List_Pc() {
    }

    public Recycle_View_List_Pc(ArrayList<Shop_Pc> pclist) {
        this.pclist = pclist;
    }

    public interface OnUserItemClickListener {

        void OnItemClick(int Position);

    }

    public void setOnItemClickListner(OnUserItemClickListener lstner) {
        mlistener = lstner;
    }

    public void setOnItemClickListnerButton(OnUserItemClickListener lstner) {
        mlistener1 = lstner;
    }

    public void setOnItemClickListnerSwitch(OnUserItemClickListener lstner) {
        mlistener2 = lstner;
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        Button button;
        SwitchCompat aSwitch;
        TextView textView;


        public ExampleViewHolder(@NonNull View itemView, final OnUserItemClickListener listener, final OnUserItemClickListener buttonListner) {
            super(itemView);
            imageView = itemView.findViewById(R.id.Pc_Image_id);
            button = itemView.findViewById(R.id.Pc_Button);
            aSwitch = itemView.findViewById(R.id.Pc_Switch);
            textView=itemView.findViewById(R.id.Number_of_Thepc);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonListner.OnItemClick(getAdapterPosition());
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(getAdapterPosition());

                }
            });


        }


    }


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pc_card_view, parent, false);
        ExampleViewHolder exm = new ExampleViewHolder(v, mlistener, mlistener1);
        return exm;

    }

    @Override
    public void onBindViewHolder(@NonNull final ExampleViewHolder holder, int position) {
        final Shop_Pc shop_pc = pclist.get(position);
        holder.textView.setText(shop_pc.getNumberofpc());
        FirebaseFirestore db;
        final CollectionReference cref;
        db = FirebaseFirestore.getInstance();
        cref = db.collection("Shop").document(shop_pc.getShop_Id()).collection("Pcs");
        cref.document(shop_pc.getNumberofpc().toString()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Shop_Pc shop_pc = documentSnapshot.toObject(Shop_Pc.class);
                // Toast.makeText(SwitchActicity.this, "yasta"+shop_pc.isOpen(), Toast.LENGTH_SHORT).show();
                if (shop_pc.isOpen() == true) {
                    holder.aSwitch.setChecked(true);
                    holder.aSwitch.setText("لا يمكنك الحجز");
                } else {
                    holder.aSwitch.setChecked(false);
                    holder.aSwitch.setText(" يمكنك الحجز");
                }

            }
        });


        // holder.button.setText("احجز");


        if (UserOrAdmin.contains("Admin") == true) {
            holder.button.setEnabled(false);
            holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {


                        cref.document(shop_pc.getNumberofpc().toString()).update("open", true);
                    } else {
                        cref.document(shop_pc.getNumberofpc().toString()).update("open", false);
                    }
                }
            });


        } else {
            holder.aSwitch.setEnabled(false);
        //    holder.aSwitch.setBackgroundColor();
            holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {

                        //  cref.document(shop_pc.getNumberofpc().toString()).update()
                        holder.button.setEnabled(false);

                        Log.d(TAG, "True");
                    } else {
                        //  cref.document(shop_pc.getNumberofpc().toString()).update("open",false);
                        holder.button.setEnabled(true);
                        Log.d(TAG, "false");
                    }


                }
            });

        }


        Picasso.get().load(shop_pc.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return pclist.size();
    }

}
