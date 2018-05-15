package com.bharat.institute;

/**
 * Created by Mahammad Ali on 28-05-16.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;


public class FacultyNotify extends Fragment{
    private ProgressDialog pDialog;
    EditText unm, pwd;
    String uname;
    boolean status;
    JSONParser jsonParser = new JSONParser();
    private static String url_create_product ="http://www.ctcorphyd.com/notifications/facultylogin.php";
    private static final String TAG_SUCCESS = "success";

    public FacultyNotify() {
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


        View view =inflater.inflate(R.layout.facultyhome, container, false);
       ImageView menuButton = (ImageView)view.findViewById(R.id.addnotify);
        menuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              Intent intent=new Intent(getActivity(),AddNotify1.class);
                startActivity(intent);

            }

        });
        return view;
    }






}



