package com.exa.dailyshoppinglist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    View myview;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        myview=itemView;
    }
    public void setType(String type){
        TextView mType =myview.findViewById(R.id.type);
        mType.setText(type);
    }
    public void  setDate(String date){

        TextView mdate =myview.findViewById(R.id.date);
        mdate.setText(date);
    }
    public void setammount (int ammount){

        TextView mAmmont =myview.findViewById(R.id.amount);
        String stam = String.valueOf(ammount);
        mAmmont.setText(stam);
    }
public void setnote(String note){
        TextView mnote = myview.findViewById(R.id.note);
        mnote.setText(note);
}
}
