package com.mycompany.loginv3;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kelvin on 2015/8/7.
 */
public class ProcessActivity extends Activity{
    String username;
    String password;
    String age;
    String name;
    String block;
    String room;
    String re_id;
    JSONParser jsonParser = new JSONParser();
    boolean validate = false;
    boolean animtri = false;
    private ProgressDialog pDialog;
    TextView txtName;
    JSONArray productObj;
    private static final String TAG_USERNAME = "username";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCT = "product";
    private static final String TAG_NAME = "name";
    private static final String TAG_RE_ID = "Re_id";
    private static final String TAG_BLOCK = "block";
    private static final String TAG_ROOM = "Room";
    public static final String PREF ="PREF";
    public static final String PREF_NAME = "PREF_NAME";

    public static final String PREF_PASSWORD = "PREF_PASSWORD";
    public final static String RESIDENT ="name";
    public final static String RESIDENT_ID ="Re_id";

    ;
    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        username= i.getStringExtra(Login.Username123);
        password= i.getStringExtra(Login.Password123);

        setContentView(R.layout.activity_process);
        //
        /*
        final ImageView iv= (ImageView) findViewById(R.id.imageView1);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        final Animation an2= AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);
        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation){

            }
            @Override
            public void onAnimationEnd(Animation animation){

            }
            @Override
            public void onAnimationRepeat(Animation animation){

            }
        });
        */
         //

        if(isNetworkConnected()==true) {


            new GetUser().execute();

        }else{

            txtName = (TextView) findViewById(R.id.textView);
            String texterror ="No internet connection!";
            txtName.setText(texterror);
        }



    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void goToMenu()
    {
        final ImageView iv= (ImageView) findViewById(R.id.imageView1);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        final Animation an2= AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);
        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation){

            }
            @Override
            public void onAnimationEnd(Animation animation){
                iv.startAnimation(an2);
                finish();

                Intent intent = new Intent(ProcessActivity.this, MenuActivity.class);
                intent.putExtra(RESIDENT, name);
                intent.putExtra(RESIDENT_ID, re_id);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation){

            }
        });



        //Intent intent = new Intent(this, MenuActivity.class);
       // intent.putExtra(RESIDENT, name);
        //intent.putExtra(RESIDENT_ID, re_id);
       // startActivity(intent);
    }

    class GetUser extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pDialog = new ProgressDialog(ProcessActivity.this);
            //pDialog.setMessage("Loading . Please wait...");
            //pDialog.setIndeterminate(false);
            //pDialog.setCancelable(true);
           // pDialog.show();
        }
        protected String doInBackground(String... args){
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_USERNAME,username));
            params.add(new BasicNameValuePair(TAG_PASSWORD,password));
            JSONObject json =jsonParser.makeHttpRequest("http://plbpc013.ouhk.edu.hk/s1129900/login/getuser.php", "GET", params);

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // successfully received product details
                    productObj = json
                            .getJSONArray(TAG_PRODUCT); // JSON Array

                    // get first product object from JSON Array
                    JSONObject product = productObj.getJSONObject(0);

                        validate = true;
                        re_id = product.getString(TAG_RE_ID);
                        name = product.getString(TAG_NAME);
                        block = product.getString(TAG_BLOCK);
                        room = product.getString(TAG_ROOM);





                }
                else
                {
                    validate = false;
                }

            }catch
                    (JSONException e) {
                e.printStackTrace();
            }
            return null;
            }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
           // pDialog.dismiss();


            if (validate==true) {
                txtName = (TextView) findViewById(R.id.textView);
               // String text ="Resident_id: "+re_id+"\nName : "+name+"\nBlock : "+block+"\nRoom: "+room+"\nUsername : "+username+"\nPassword : "+password;
                String text ="Loading ...";
                txtName.setText(text);
                SharedPreferences settings = getSharedPreferences(PREF,0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(PREF_NAME,username);
                editor.putString(PREF_PASSWORD,password);
                editor.commit();
                goToMenu();



            }else
            {
                txtName = (TextView) findViewById(R.id.textView);
                String text="Login fail!";
                txtName.setText(text);
               // Intent intent = new Intent();

               // setResult(RESULT_OK,intent);
               // ProcessActivity.this.finish();
            }

        }


        }




}
