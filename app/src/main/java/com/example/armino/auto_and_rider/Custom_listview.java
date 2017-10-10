package com.example.armino.auto_and_rider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import Adapter_class.GetData;

/**
 * Created by armino on 20-09-2017.
 */

class Custom_listview extends BaseAdapter {
    Activity context;

   public String[] names={"Kl-45-443","KL-45-4523","KL-45-4553","KL-45-4823","KL-45-4527","KL-45-4153","KL-45-2823","KL-45-7823"};
  //public String[] names={"Kl-45-443","KL-45-4523"};
   //public String[] names={"Kl-45-443"};

    public Custom_listview(Activity mainActivity) {
        this.context=mainActivity;
    }

    @Override
    public int getCount() {
        return GetData.al.size();
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater in=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       final View v=in.inflate(R.layout.custom_listview,null,true);

        RelativeLayout list_layout = (RelativeLayout) v.findViewById(R.id.list_layout);

        ImageView call=(ImageView)v.findViewById(R.id. imageView2);
      final   TextView auto_name=(TextView)v.findViewById(R.id.auto_list_names);
        ImageView auto_pict=(ImageView)v.findViewById(R.id.customList_Imageview_ID);

        auto_name.setText(GetData.al.get(i));
        //auto_name.setText(GetData.map.get(i));
        Picasso.with(context).load(R.drawable.demo_dp4).into(auto_pict);


        call.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                System.out.println("image clicked:"+GetData.al.get(i)+"...............");
                Toast.makeText(context,"image clicked:"+GetData.al.get(i),Toast.LENGTH_LONG).show();
               // v.setBackgroundColor(Color.parseColor("#ff00bf"));
              //  auto_name.setTextColor(Color.parseColor("#19c5ff"));
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+ GetData.map.get(GetData.al.get(i))));//change the number
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                }
                context.startActivity(callIntent);
              //  notifyDataSetChanged();
                auto_name.setTextColor(Color.parseColor("#19c5ff"));

            }

        });

        return v;

    }



}
