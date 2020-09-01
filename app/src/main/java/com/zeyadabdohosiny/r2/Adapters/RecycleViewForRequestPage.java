package com.zeyadabdohosiny.r2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zeyadabdohosiny.r2.R;
import com.zeyadabdohosiny.r2.RequestModel.Requestmodel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecycleViewForRequestPage  extends RecyclerView.Adapter<RecycleViewForRequestPage.ExampleViewHolder> {
    ArrayList<Requestmodel> list;
    private RecycleViewForRequestPage.OnUserItemClickListener acceptlistner;
    private RecycleViewForRequestPage.OnUserItemClickListener rejectlistner;
    private RecycleViewForRequestPage.OnUserItemClickListener UserComelister;
    private RecycleViewForRequestPage.OnUserItemClickListener UserDontcomelistner;

    public RecycleViewForRequestPage(ArrayList<Requestmodel> list) {
        this.list = list;
    }

    public interface OnUserItemClickListener {

        void OnItemClick(int Position);

    }
    public void setOnItemClickListnerAcceptedButton(RecycleViewForRequestPage.OnUserItemClickListener lstner) {
        acceptlistner = lstner;
    }
    public void setOnItemClickListnerRejectedButton(RecycleViewForRequestPage.OnUserItemClickListener lstner) {
        rejectlistner = lstner;
    }
    public void setOnItemClickListnerUserComeButton(RecycleViewForRequestPage.OnUserItemClickListener lstner) {
        UserComelister = lstner;
    }
    public void setOnItemClickListnerUserDontButton(RecycleViewForRequestPage.OnUserItemClickListener lstner) {
        UserDontcomelistner = lstner;
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{
     ImageView userimageView;
     TextView textViewForname,textviewforPc,textViewfortime,rateOfTheUser;
     Button accept,reject,Usercome,UserDontcome;


        public ExampleViewHolder(@NonNull View itemView,final RecycleViewForRequestPage.OnUserItemClickListener accepted,final OnUserItemClickListener rejected,final RecycleViewForRequestPage.OnUserItemClickListener usercome,final RecycleViewForRequestPage.OnUserItemClickListener userdontcome) {
            super(itemView);
            userimageView=itemView.findViewById(R.id.Image_For_Requuest_page);
            textViewForname=itemView.findViewById(R.id.Name_For_Requuest_page);
            textviewforPc=itemView.findViewById(R.id.NameOfThePc_For_Requuest_page);
            textViewfortime=itemView.findViewById(R.id.TimeOftheRequest);
            rateOfTheUser=itemView.findViewById(R.id.RateofTheUser);
            accept=itemView.findViewById(R.id.Accept_Request_Page);
            reject=itemView.findViewById(R.id.Rejected_requestPage);
            Usercome=itemView.findViewById(R.id.UserIsCome);
            UserDontcome=itemView.findViewById(R.id.UserDontCome);


            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    accepted.OnItemClick(getAdapterPosition());
                }
            });
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rejected.OnItemClick(getAdapterPosition());
                }
            });
            Usercome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    usercome.OnItemClick(getAdapterPosition());
                }
            });
            UserDontcome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userdontcome.OnItemClick(getAdapterPosition());
                }
            });

        }
    }



    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_page_card_view, parent, false);
        RecycleViewForRequestPage.ExampleViewHolder exm = new RecycleViewForRequestPage.ExampleViewHolder(v,acceptlistner,rejectlistner,UserComelister, UserDontcomelistner);
        return exm;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
           Requestmodel currentRequest=list.get(position);
           holder.textViewForname.setText(currentRequest.getNameOfTheUser());
           holder.textviewforPc.setText(currentRequest.getNameofThepc());
           holder.textViewfortime.setText(currentRequest.getTime());
           String x=""+currentRequest.getRateOftheUser();
           holder.rateOfTheUser.setText(x);
        //   holder.rateOfTheUser.setText(currentRequest.getRateOftheUser());
           Picasso.get().load(currentRequest.getImageuri()).into(holder.userimageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
