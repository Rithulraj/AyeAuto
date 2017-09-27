package com.example.armino.auto_and_rider;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by armino on 20-09-2017.
 */

class Custom_listview extends BaseAdapter{
    Activity context;
    String[] names={"Jithesh","Sooraj","Habeeb","Hari","ffff","fffffff"};
   static boolean colorFlag=true;

    public Custom_listview(Activity mainActivity) {
        this.context=mainActivity;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater in=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=in.inflate(R.layout.custom_listview,null,true);

        RelativeLayout list_layout = (RelativeLayout) v.findViewById(R.id.list_layout);
        if(i % 2 == 1) {

            list_layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }


        TextView auto_name=(TextView)v.findViewById(R.id.auto_list_names);
        auto_name.setText(names[i]);
        return v;

    }
}
