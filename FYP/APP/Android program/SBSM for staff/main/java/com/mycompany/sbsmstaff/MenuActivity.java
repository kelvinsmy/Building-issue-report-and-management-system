package com.mycompany.sbsmstaff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class MenuActivity extends ActionBarActivity {
    String name;
    String st_id;
    String block;
    String taskn;
    TextView txtName1;
    private static final String TAG_TASK = "task";
    public final static String STAFF ="name";
    public final static String STAFF_ID ="Re_id";
    public final static String BLOCK ="block";
    public final static String TAG_STAFF_ID ="St_id";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    JSONParser jParser = new JSONParser();
    private ProgressDialog pDialog;

    ArrayList<HashMap<String, String>> productsList;
    JSONArray products = null;
    private static String url_my_report = "http://plbpc013.ouhk.edu.hk/s1129900/FYP/managergetnoreport.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        name= intent.getStringExtra(ProcessActivity.STAFF);
        st_id= intent.getStringExtra(ProcessActivity.STAFF_ID);
        block= intent.getStringExtra(ProcessActivity.BLOCK);
        txtName1 = (TextView) findViewById(R.id.textView2);
        String text = "Hello, Manager "+name +"\nBlock: "+ block;
        txtName1.setText(text);
        new LoadAllProducts().execute();

    }


    public void textOnClick2(View view){
        Intent intentR = new Intent(this, ReportActivity.class);
        intentR.putExtra(STAFF, name);
        intentR.putExtra(STAFF_ID, st_id);
        intentR.putExtra(BLOCK ,block);
        startActivity(intentR);
    }

    public void textOnClickR(View view){
        Intent intentR = new Intent(this, AssignedReportActivity.class);
        intentR.putExtra(STAFF, name);
        intentR.putExtra(STAFF_ID, st_id);
        intentR.putExtra(BLOCK ,block);
        startActivity(intentR);
    }

    public void textOnClickM(View view){

    }



    /*
    public void textOnClickM(View view){
        Intent intentM = new Intent(this, MyReportActivity.class);
        intentM.putExtra(RESIDENT, name);
        intentM.putExtra(RESIDENT_ID, re_id);
        startActivity(intentM);
    }
  */
    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first

        //productsList.clear();
        new LoadAllProducts().execute();


        // Activity being restarted from stopped state
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair(TAG_RE_ID, "2"));

            params.add(new BasicNameValuePair(BLOCK,block));
            params.add(new BasicNameValuePair(TAG_STAFF_ID,st_id));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_my_report, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        String task = c.getString(TAG_TASK);
                        //String block = c.getString(TAG_BLOCK);
                        //String location = c.getString(TAG_LOCATION);
                        //type = type + " ("+progress+")";


                        taskn="No. of New Report(s) : "+task;
                        //String tlocation = "Block "+block+",  " + location;
                        //
                        // String price = c.getString(TAG_PRICE);
                        //String description = c.getString(TAG_DESCRIPTION);
                        //

                        // creating new HashMap


                        // adding each child node to HashMap key => value

                    }
                } else {

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
            // dismiss the dialog after getting all products

            String text = "Hello, Manager "+name +"\nBlock: "+ block + "\n"+taskn;
            txtName1.setText(text);


        }

    }




}
