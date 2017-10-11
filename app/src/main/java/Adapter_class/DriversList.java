package Adapter_class;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

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
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by armino on 10/11/2017.
 */

public class DriversList {

    public static ArrayList<String> al1=new ArrayList<>();
    public static Map<String,String> map1=new HashMap<String,String>();
    public String getList()
    {
        try{


            al1.clear();
            map1.clear();

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
                    al1.add(jObj.getString("number_plate"));
                    map1.put(jObj.getString("number_plate"),jObj.getString("phone"));

                   Log.e("ArayList...................____", al1.get(i));

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
