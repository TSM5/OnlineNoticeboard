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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FacultytReg extends Activity  {

    EditText unm, pwd, email, mno, rno;
    SQLiteDatabase db;
    int sts=0;
    String rno1,unm1,pwd1,email1,mno1;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private Spinner spinner;
    String subject;
    // url to create new product
    private static String url_create_product ="http://www.ctcorphyd.com/notifications/facultyreg.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_register);
        unm = (EditText) findViewById(R.id.unm);
        pwd = (EditText) findViewById(R.id.pwd);
        email = (EditText) findViewById(R.id.email);
        mno = (EditText) findViewById(R.id.mno);



    }

    public void onNothingSelected(AdapterView arg0) {
        // TODO Auto-generated method stub

    }



    public void register(View v) {



       email1 = email.getText().toString();
        //tring email = emailValidate.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String unmpattern="[A-Za-z]+";

       unm1 = unm.getText().toString().trim();
        pwd1 = pwd.getText().toString().trim();
         mno1 = mno.getText().toString().trim();

            if (null == unm1 || unm1.trim().length() == 0) {
                unm.setError("Enter Your UserName");
                unm.requestFocus();
            } else {
                if (!unm1.matches(unmpattern)) {
                    unm.setError("Incorect Name Entry ");
                    unm.requestFocus();
                } else {

                    if (null == pwd1 || pwd1.trim().length() == 0) {
                        pwd.setError("Enter Your Password");
                        pwd.requestFocus();
                    } else {

                        if (null == email1 || email1.trim().length() == 0) {
                            email.setError("Enter Your Email ID");
                            email.requestFocus();
                        } else {

                            if (!email1.matches(emailPattern)) {
                                email.setError("Incorect Email Id Entry ");
                                email.requestFocus();
                            } else {

                                if (null == mno1 || mno1.trim().length() == 0) {
                                    mno.setError("Enter Your Mobile No.");
                                    mno.requestFocus();
                                } else {
                                    if (mno1.length() != 10) {
                                        mno.setError("Invalid Phone Number.");
                                        mno.requestFocus();
                                    } else {


                                        new Register().execute();

                                        //Toast.makeText(getApplicationContext(), "Registered Successfully..!", Toast.LENGTH_SHORT).show();

                                        // finish();
                                        return;
                                    }

                                }
                            }
                        }

                    }
                }

        }


        }
	/*
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
    }
   
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
        super.onOptionsItemSelected(item);
 
        switch(item.getItemId()){
            case R.id.action1:
            	
            	Intent i=new Intent(getApplicationContext(),VoterLogin.class);
            	startActivity(i);
                //Toast.makeText(getBaseContext(), "You selected Phone", Toast.LENGTH_SHORT).show();
                break;
 
            case R.id.action2:
            	Intent ii=new Intent(getApplicationContext(),AdminLogin.class);
            	startActivity(ii);
               // Toast.makeText(getBaseContext(), "You selected Computer", Toast.LENGTH_SHORT).show();
                break;
 
            case R.id.action3:
            	Intent iii=new Intent(getApplicationContext(),Contact.class);
            	startActivity(iii);
                break;
 
         
 
        }
        return true;
 
    }*/

    class Register extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FacultytReg.this);
            pDialog.setMessage("Registering...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("subject",subject));
            params.add(new BasicNameValuePair("name",unm1.trim()));
            params.add(new BasicNameValuePair("pwd",pwd1.trim()));
            params.add(new BasicNameValuePair("email",email1));
            params.add(new BasicNameValuePair("mno",mno1));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check log cat for response
            Log.d("Response for Register", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    i.putExtra("status","success");
                    startActivity(i);
                  //  Toast.makeText(getApplicationContext(),"suces",Toast.LENGTH_SHORT).show();
                    // closing this screen
                    sts=1;
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
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            Toast.makeText(getApplicationContext(), "Registered Successfully..!", Toast.LENGTH_SHORT).show();

            pDialog.dismiss();
        }

    }



        }
