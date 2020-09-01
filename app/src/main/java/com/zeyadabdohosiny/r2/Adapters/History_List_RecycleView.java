package com.zeyadabdohosiny.r2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zeyadabdohosiny.r2.R;
import com.zeyadabdohosiny.r2.RequestModel.HistoryModle;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class History_List_RecycleView extends RecyclerView.Adapter<History_List_RecycleView.ExampleViewHolder>{

    ArrayList<HistoryModle> list;
    private OnUserItemClickListener mlistener;

    public History_List_RecycleView(ArrayList<HistoryModle> list) {
        this.list = list;
    }
    public interface OnUserItemClickListener {

        void OnItemClick(int Position);

    }
    public void setOnItemClickListner(OnUserItemClickListener lstner){
        mlistener=lstner;
    }
    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView shopImage;
        TextView nameOfTheShop,numberOfPc,theDate,rateTheShop,usercomeornot;


        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            shopImage=itemView.findViewById(R.id.Image_For_HistoryPage);
            nameOfTheShop=itemView.findViewById(R.id.Name_For_HistoryPage);

            theDate=itemView.findViewById(R.id.HistoryPage_date);
            usercomeornot=itemView.findViewById(R.id.TxusercomeOrnot);


        }
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_of_user_card_view,parent,false);
        History_List_RecycleView.ExampleViewHolder exv=new ExampleViewHolder(v);
        return exv;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        HistoryModle currentRequest=list.get(position);
        holder.nameOfTheShop.setText(currentRequest.getNameOfTheShop());
        holder.theDate.setText(currentRequest.getDateOfRegestration());
        holder.usercomeornot.setText(currentRequest.getComeOrNot());
        //   holder.rateOfTheUser.setText(currentRequest.getRateOftheUser());
        Picasso.get().load(currentRequest.getImageView()).into(holder.shopImage);

    }

    @Override
    public int getItemCount() {

        return list.size();
    }
}
