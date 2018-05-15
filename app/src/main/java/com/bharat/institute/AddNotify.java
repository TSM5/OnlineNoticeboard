package com.bharat.institute;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNotify extends Activity  {

    EditText notification;


    int sts=0;

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
String uname,dt,type="HOD";
    String notify;
    // url to create new product
    private static String temp="http://www.ctcorphyd.com/notifications/temp1.php";
    private static String notifys="http://www.ctcorphyd.com/notifications/addnotify.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnotify);
notification=(EditText)findViewById(R.id.notification);
        new temp().execute();

    }

    public void onNothingSelected(AdapterView arg0) {
        // TODO Auto-generated method stub

    }



    public void addNotification(View v) {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //get current date time with Date()
        Date date = new Date();

      dt=dateFormat.format(date);

       notify=notification.getText().toString().trim();
        //tring email = emailValidate.getText().toString().trim();

                                    if (notify==null|| notify.trim().length() == 0) {
                                        notification.setError("Please type the Text");
                                        notification.requestFocus();
                                    }  else {


                                            new Notify().execute();

                                           //Toast.makeText(getApplicationContext(), "Registered Successfully..!", Toast.LENGTH_SHORT).show();

                                           // finish();
                                            return;
                                        }

                                    }





    class Notify extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddNotify.this);
            pDialog.setMessage("Processing...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("msg", notify));
            params.add(new BasicNameValuePair("name", uname));
            params.add(new BasicNameValuePair("type", type));
            params.add(new BasicNameValuePair("date", dt));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(notifys,
                    "POST", params);

            // check log cat for response
            Log.d("Response for Register", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), HODHome.class);
                    startActivity(i);
                    //  Toast.makeText(getApplicationContext(),"suces",Toast.LENGTH_SHORT).show();
                    // closing this screen
                    sts = 1;
                    finish();
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
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            Toast.makeText(getApplicationContext(), "Notication Added  Successfully..!", Toast.LENGTH_SHORT).show();

            pDialog.dismiss();
        }


    }


    class temp extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddNotify.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("name",uname));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(temp,
                    "GET", params);

            // check log cat for response
            Log.d("Temp :", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    uname=json.getString("uname");

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
            // Toast.makeText(getApplicationContext(), "Registered Successfully..!", Toast.LENGTH_SHORT).show();

            pDialog.dismiss();
        }

    }








}
