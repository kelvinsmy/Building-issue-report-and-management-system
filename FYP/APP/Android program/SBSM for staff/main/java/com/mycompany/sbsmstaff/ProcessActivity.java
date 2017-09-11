package com.mycompany.sbsmstaff;
import java.net.InetAddress;
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
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    String position;
    String st_id;
    String access;
    JSONParser jsonParser = new JSONParser();
    boolean validate = false;
    private ProgressDialog pDialog;
    TextView txtName;
    JSONArray productObj;
    private static final String TAG_USERNAME = "username";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCT = "product";
    private static final String TAG_NAME = "name";
    private static final String TAG_ST_ID = "St_id";
    private static final String TAG_BLOCK = "block";
    private static final String TAG_POSITION = "position";
    public static final String PREF ="PREF";
    public static final String PREF_NAME = "PREF_NAME";
    private static final String TAG_ACCESS = "access";
    public static final String PREF_PASSWORD = "PREF_PASSWORD";
    public final static String STAFF ="name";
    public final static String STAFF_ID ="St_id";
    public final static String STAFF_ACCESS ="access";
    public final static String BLOCK ="block";
    ;

    //
    boolean connected = false;
    ///
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        username= i.getStringExtra(Login.Username123);
        password= i.getStringExtra(Login.Password123);

        setContentView(R.layout.activity_process);

       //connected=true;

        ///

        ///
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
        if(access.equals("1")) {
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra(STAFF, name);
            intent.putExtra(STAFF_ID, st_id);
            intent.putExtra(STAFF_ACCESS, access);
            intent.putExtra(BLOCK, block);
            startActivity(intent);
        }

        if(access.equals("0")) {
            Intent intent = new Intent(this, WorkerMenuActivity.class);
            intent.putExtra(STAFF, name);
            intent.putExtra(STAFF_ID, st_id);
            intent.putExtra(STAFF_ACCESS, access);
            intent.putExtra(BLOCK, block);
            startActivity(intent);
        }


    }

    class GetUser extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProcessActivity.this);
            pDialog.setMessage("Loading . Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... args){
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_USERNAME,username));
            params.add(new BasicNameValuePair(TAG_PASSWORD,password));
            JSONObject json =jsonParser.makeHttpRequest("http://plbpc013.ouhk.edu.hk/s1129900/FYP/getStaff.php", "GET", params);

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // successfully received product details
                    productObj = json
                            .getJSONArray(TAG_PRODUCT); // JSON Array

                    // get first product object from JSON Array
                    JSONObject product = productObj.getJSONObject(0);

                    validate = true;
                    st_id = product.getString(TAG_ST_ID);
                    name = product.getString(TAG_NAME);
                    block = product.getString(TAG_BLOCK);
                    position = product.getString(TAG_POSITION);
                    access =  product.getString(TAG_ACCESS);




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
            pDialog.dismiss();


            if (validate==true) {
                txtName = (TextView) findViewById(R.id.textView);
                // String text ="Resident_id: "+re_id+"\nName : "+name+"\nBlock : "+block+"\nRoom: "+room+"\nUsername : "+username+"\nPassword : "+password;
                String text ="Loading...";
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
                String text="Wrong information";
                txtName.setText(text);
            }

        }


    }


    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first

        this.finish();
    }



}
