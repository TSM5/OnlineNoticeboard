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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Faculty extends Fragment{
    EditText unm, pwd;
    private ProgressDialog pDialog;
    String unm1,pwd1;
    boolean status;
    JSONParser jsonParser = new JSONParser();
    private static String url_facultylogin ="http://www.ctcorphyd.com/notifications/facultylogin.php";
    private static final String TAG_SUCCESS = "success";

    public Faculty() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.facultylogin, container, false);

        unm = (EditText)view.findViewById(R.id.unm);
        pwd = (EditText)view.findViewById(R.id.pwd);

                Button menuButton = (Button)view.findViewById(R.id.login);
                menuButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        unm1 = unm.getText().toString().trim();
                        pwd1 = pwd.getText().toString().trim();

                        if (null == unm1 || unm1.trim().length() == 0) {
                            unm.setError("Enter Your Username");
                            unm.requestFocus();
                        } else if (null == pwd1 || pwd1.trim().length() == 0) {
                            pwd.setError("Enter Your Password");
                            pwd.requestFocus();
                        } else {
                            new verify().execute();

                        }


                    }

                });




        // Registration
        TextView reg = (TextView)view.findViewById(R.id.reg);
        reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FacultytReg.class);
                startActivity(i);
            }
        });



        return view;
    }
    class verify extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Processing...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name",unm1));
            params.add(new BasicNameValuePair("pwd",pwd1));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_facultylogin,
                    "POST", params);

            // check log cat for response
            Log.d("Response for Login", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success==1) {
                    // successfully created product
                    Intent i = new Intent(getActivity(),FacultyHome.class);
                    i.putExtra("uname",unm1);
                    startActivity(i);
                    //  Toast.makeText(getApplicationContext(),"suces",Toast.LENGTH_SHORT).show();
                    // closing this screen

                } else {
                    status=true;
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
            if(status) {
                status=false;
                Toast.makeText(getActivity(), "Invalid Credentials..!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(),MainActivity.class);
                startActivity(i);
            }

            pDialog.dismiss();
        }

    }



}
