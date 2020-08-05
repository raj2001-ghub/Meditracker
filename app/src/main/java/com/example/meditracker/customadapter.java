package com.example.meditracker;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class customadapter extends RecyclerView.Adapter<customadapter.MyViewHolder> {

    private static Context context;
    private ArrayList doc_name,med_name,illness_name,date,totalcost,numpills;
    public customadapter(Context context,ArrayList doc_name,ArrayList med_name,ArrayList illness_name,ArrayList date,ArrayList totalcost,ArrayList numpills){
        this.context=context;
        this.doc_name=doc_name;
        this.med_name=med_name;
        this.illness_name=illness_name;
        this.date=date;
        this.totalcost=totalcost;
        this.numpills=numpills;
    }

    @NonNull
    @Override
    public customadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.rows_design,parent,false);                            ///////this line connects the row_design with the custom Adapter file..
        return new customadapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull customadapter.MyViewHolder holder, int position) {
    holder.date.setText(String.valueOf(date.get(position)));
        holder.price.setText("Rs "+String.valueOf(totalcost.get(position)));
        holder.medname.setText(String.valueOf(med_name.get(position)));
    }

    @Override
    public int getItemCount() {
        return doc_name.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView date,price;
        EditText medname;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date_row);
            price=itemView.findViewById(R.id.price_row);
            medname=itemView.findViewById(R.id.medname_row);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {                                                             //////////////this part is for what happens when we click a particular element of that list
            System.out.println(getAdapterPosition());
            Intent intent=new Intent(context,meddetails.class);
            intent.putExtra("POSITION",String.valueOf(getAdapterPosition()));
            context.startActivity(intent);

        }
    }

}
