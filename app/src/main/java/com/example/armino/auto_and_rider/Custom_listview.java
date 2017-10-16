package com.example.armino.auto_and_rider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import Adapter_class.DriversList;
import Adapter_class.GetData;

/**
 * Created by armino on 20-09-2017.
 */

class Custom_listview extends BaseAdapter {
    Activity context;
    ArrayList<String> dieledNo=new ArrayList<>();


    public Custom_listview(Activity mainActivity) {
        this.context=mainActivity;
    }

    @Override
    public int getCount() {
        return DriversList.al1.size();
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

        final RelativeLayout list_layout = (RelativeLayout) v.findViewById(R.id.list_layout);

        ImageView call=(ImageView)v.findViewById(R.id. imageView2);
      final   TextView auto_name=(TextView)v.findViewById(R.id.auto_list_names);
        ImageView auto_pict=(ImageView)v.findViewById(R.id.customList_Imageview_ID);

        auto_name.setText(DriversList.al1.get(i));

        Picasso.with(context).load(R.drawable.demo_dp4).into(auto_pict);
     /*   try {
            // Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(" http://corporate2.bdjobs.com/21329.jpg").getContent());
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL( DriversList.map2.get(DriversList.al1.get(i))).getContent());
            //  http://corporate2.bdjobs.com/21329.jpg
            auto_pict.setImageBitmap(bitmap);
            //convertView.setBackgroundResource(R.drawable.cardlayout);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        for(int l=0;l<dieledNo.size();l++)
        {
           if (dieledNo .contains(DriversList.al1.get(i)) ) {

               list_layout.setBackgroundColor(Color.parseColor("#C0C0C0"));
            }
        }

        call.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+ DriversList.map1.get(DriversList.al1.get(i))));//change the number
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                }
                context.startActivity(callIntent);

                list_layout.setBackgroundColor(Color.parseColor("#C0C0C0"));
                dieledNo.add(DriversList.al1.get(i));

            }

        });

        return v;

    }



}
