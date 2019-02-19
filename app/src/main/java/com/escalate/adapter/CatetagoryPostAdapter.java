package com.escalate.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CatetagoryPostAdapter extends ArrayAdapter {


    public CatetagoryPostAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        // TODO Auto-generated constructor stub
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View spinnerView = super.getDropDownView(position, convertView, parent);

//        TextView spinnerTextV = (TextView) spinnerView;
//        if (position == 0) {
//            //Set the disable spinner item color fade .
//            spinnerTextV.setTextColor(Color.parseColor("#eeeeee"));
//        } else {
//            spinnerTextV.setTextColor(Color.parseColor("#000000"));
//
//        }

        return spinnerView;
    }
}