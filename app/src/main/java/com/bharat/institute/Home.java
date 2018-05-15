package com.bharat.institute;

/**
 * Created by Mahammad Ali on 28-05-16.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment{
    ArrayList al=null;
    JSONArray notice = null;
    ArrayAdapter<String> itemsAdapter;
    JSONParser jsonParser = new JSONParser();
    private static String notificationlist ="http://www.ctcorphyd.com/notifications/viewnotify.php";
    private static final String TAG_SUCCESS = "success";
    private ProgressDialog pDialog;
    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       al= new ArrayList();
       new Listview().execute();

View v=inflater.inflate(R.layout.home, container, false);


      itemsAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,al);

        ListView listView = (ListView)v.findViewById(R.id.lv);
        //itemsAdapter.notifyDataSetChanged();
        listView.setAdapter(itemsAdapter);
        itemsAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //create a Fragment

            }
        });


        return v;
    }

    class Listview extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            // Building Parameters
            String s=null;
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(notificationlist,
                    "GET", params);

            // check log cat for response
            Log.d("Response for Notifns", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    notice=json.getJSONArray("notify");
al.clear();
                    // looping through All Products
                    for (int i = 0; i < notice.length(); i++) {
                        JSONObject c = notice.getJSONObject(i);
                        s="Date : "+ c.getString("date")+" "+"\n\n"+
                                "          "+ c.getString("message")+" "+"\n\n"+
                                "                   - "+ c.getString("uname")+"("+c.getString("type")+")";

                        // Storing each json item in variable
                        al.add(s);

                    }


                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
          //  Toast.makeText(getApplicationContext(), "Registered Successfully..!", Toast.LENGTH_SHORT).show();
            itemsAdapter.notifyDataSetChanged();
            pDialog.dismiss();
        }

    }

}
