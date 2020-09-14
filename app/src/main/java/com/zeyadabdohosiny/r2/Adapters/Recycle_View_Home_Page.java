package com.zeyadabdohosiny.r2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zeyadabdohosiny.r2.MvvmAndRommUserInfo.Shop;
import com.zeyadabdohosiny.r2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Recycle_View_Home_Page extends RecyclerView.Adapter<Recycle_View_Home_Page.ExampleViewHolder> {


    ArrayList <Shop> Shops;
    private OnUserItemClickListener mlistener;
    private OnUserItemClickListener locationListner;
    private OnUserItemClickListener menuListner;

    public Recycle_View_Home_Page(ArrayList<Shop> shops) {
        Shops = shops;
    }

    public interface OnUserItemClickListener {

        void OnItemClick(int Position);

    }
    public void setOnItemClickListner(OnUserItemClickListener lstner){
        mlistener=lstner;
    }
    public void setOnItemClickListnerLocation(OnUserItemClickListener lstner) {
        locationListner = lstner;
    }
    public void setOnItemClickListnerMenu(OnUserItemClickListener lstner) {
        menuListner = lstner;
    }



    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameofTheshop,location,menu;


        public ExampleViewHolder(@NonNull View itemView, final OnUserItemClickListener listener, final OnUserItemClickListener locationlistner, final OnUserItemClickListener menuListner) {
            super(itemView);
            imageView=itemView.findViewById(R.id.Shop_Image_view);
            nameofTheshop=itemView.findViewById(R.id.nameOftheshop);
            location=itemView.findViewById(R.id.locationOfTheShop);
            menu=itemView.findViewById(R.id.menu_Of_TheShop);
            location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    locationlistner.OnItemClick(getAdapterPosition());
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(getAdapterPosition());

                }
            });
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuListner.OnItemClick(getAdapterPosition());
                }
            });


        }
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.play_station_home_page_card_view, parent, false);
        ExampleViewHolder exm = new ExampleViewHolder(v,mlistener,locationListner,menuListner);
        return exm;

    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Shop users=Shops.get(position);
        holder.nameofTheshop.setText(users.getName());
        Picasso.get().load(users.getImage_Uri()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return Shops.size();
    }
    public void fillterlist(ArrayList<Shop> list){
        Shops=list;
        notifyDataSetChanged();

    }


}
