package com.bharat.institute;

/**
 * Created by Mahammad Ali on 28-05-16.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HODNotify extends Fragment{
    private ProgressDialog pDialog;
    EditText unm, pwd;
    String uname;
    boolean status;
    JSONParser jsonParser = new JSONParser();
    private static String url_create_product ="http://www.ctcorphyd.com/notifications/hodlogin.php";
    private static final String TAG_SUCCESS = "success";

    public HODNotify() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.hodlogin);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view =inflater.inflate(R.layout.hodhome, container, false);
       ImageView menuButton = (ImageView)view.findViewById(R.id.addnotify);
        menuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              Intent intent=new Intent(getActivity(),AddNotify.class);
                startActivity(intent);

            }

        });
        return view;
    }






}



