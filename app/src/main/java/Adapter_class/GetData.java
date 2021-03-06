package Adapter_class;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.armino.auto_and_rider.Rider_activity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by armino on 10/10/2017.
 */

public class GetData extends AsyncTask<String, Void, String> {
public static int status=0;

public static  ArrayList<String> al=new ArrayList<>();
public static Map<String,String> map=new HashMap<String,String>();
    ProgressBar progressBar;
    ProgressDialog pdia;



    protected void onPreExecute(){
        super.onPreExecute();
        if(status==1 )
        {
            al.clear();
            map.clear();
            status=0;
        }
       else
        {
           status=1;
        }
        pdia = new ProgressDialog(Rider_activity.RIDER_CONTEXT);
        pdia.setMessage("Loading...");
        pdia.show();

    }

    protected String doInBackground(String... arg0) {

                   try{


                // URL url = new URL("http://aproxy.noip.me/api?lat=11.2918833&lon=75.781179&rad=10.0");
                URL url = new URL("http://aproxy.noip.me/api");

                JSONObject postDataParams = new JSONObject();


                postDataParams.put("lat",11.2918833);
                postDataParams.put("lon",75.781179);
                postDataParams.put("rad",10.0);


                Log.e("params",postDataParams.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";

                while((line = in.readLine()) != null) {

                    sb.append(line);
                    Log.e(" buffer1:",sb.toString());
                    break;
                }
                String json =sb.toString();



                JSONArray jArray = new JSONArray(json);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    Log.e("Number Plate:",jObj.getString("number_plate"));
                    Log.e("Phone no:",jObj.getString("phone"));
                      al.add(jObj.getString("number_plate"));
                    map.put(jObj.getString("number_plate"),jObj.getString("phone"));

                }

                Log.e("Strig buffer",sb.toString());
                in.close();
                return sb.toString();

            }
            else {
                return new String("false : "+responseCode);
            }
        }
        catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        pdia.dismiss();
        System.out.println("Result:"+result);

    }


    public String getPostDataString(JSONObject params) throws Exception {

        Log.e("jsonobject",params.toString());
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");
            Log.e("result1:",result.toString());
            result.append(URLEncoder.encode(key, "UTF-8"));
            Log.e("result1:",result.toString());
            result.append("=");
            Log.e("result2:",result.toString());
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
            Log.e("result3:",result.toString());
        }
        return result.toString();
    }
}