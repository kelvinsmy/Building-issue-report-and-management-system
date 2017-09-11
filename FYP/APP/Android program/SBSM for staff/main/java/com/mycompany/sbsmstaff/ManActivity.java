package com.mycompany.sbsmstaff;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ManActivity extends ActionBarActivity {
    String rid;
    int success;
    String type;
    String block;
    String location;
    String time;
    String progress;
    TextView txtName1;
    TextView txtNameP;
    TextView txtNameL;
    TextView txtNameT;
    TextView txtNameI;
    String email;
    private static final String TAG_PRODUCT = "product";
    private static final String TAG_FTIME = "finished_at";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_RE_ID = "Re_id";
    private static final String TAG_BLOCK = "block";
    private static final String TAG_TYPE = "type";
    private static final String TAG_PROGRESS = "progress";
    private static final String TAG_RID = "Rid";
    private static final String TAG_RIDD = "rid";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_TLOCATION = "tlocation";
    public final static String RID ="Rid";
    public final static String BLOCK ="block";
    private static final String TAG_CREATE = "created_at";
    private static final String TAG_EMAIL = "email";

    JSONParser jParser = new JSONParser();
    private ProgressDialog pDialog;

    ArrayList<HashMap<String, String>> productsList;
    JSONArray products = null;
    private static String url_my_report = "http://plbpc013.ouhk.edu.hk/s1129900/FYP/getOneReport.php";
    private static String updateReport= "http://plbpc013.ouhk.edu.hk/s1129900/FYP/updateReport.php";
    private static String updateReportS= "http://plbpc013.ouhk.edu.hk/s1129900/FYP/updateReportS.php";

    ////
    JSONParser jsonParser = new JSONParser();
    JSONArray productObj;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man);

        Intent intent = getIntent();
        rid= intent.getStringExtra(ReportActivity.RID);
        block= intent.getStringExtra(ReportActivity.BLOCK);

        new LoadAllProducts().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_man, menu);
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
            params.add(new BasicNameValuePair(TAG_RID,rid));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_my_report, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products

                        JSONObject c = products.getJSONObject(0);

                        // Storing each json item in variable

                        type = c.getString(TAG_TYPE);

                         block = c.getString(TAG_BLOCK);
                         location = c.getString(TAG_LOCATION);
                         time = c.getString(TAG_CREATE);





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
            if(success==1){
                String tlocation = "Block "+block+",  " + location;
                String rrid= "No."+" "+rid;
                txtNameI = (TextView) findViewById(R.id.textView5);

                txtNameI.setText(rrid);


                txtNameP = (TextView) findViewById(R.id.textProblem);

                txtNameP.setText(type);

                txtNameL = (TextView) findViewById(R.id.textLocation);

                txtNameL.setText(tlocation);

                txtNameT = (TextView) findViewById(R.id.textTime);

                txtNameT.setText(time);

            }


        }

    }

    /////
    class Getemail extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected String doInBackground(String... args){
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair(TAG_RIDD,rid));
            JSONObject json =jsonParser.makeHttpRequest("http://plbpc013.ouhk.edu.hk/s1129900/FYP/getemail.php", "GET", params);

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // successfully received product details
                    productObj = json
                            .getJSONArray(TAG_PRODUCT); // JSON Array

                    // get first product object from JSON Array
                    JSONObject product = productObj.getJSONObject(0);



                    email =  product.getString(TAG_EMAIL);

                }

            }catch
                    (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details





            sendemail();


        }


    }










    public void sendemail(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");

        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
        //i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"kelvinsmy81@icloud.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Report Completion");
        i.putExtra(Intent.EXTRA_TEXT   , "Your report is completed!\nPlease login your app to check the detail");
        try {
            startActivity(Intent.createChooser(i, "Feedback to resident"));
            this.finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ManActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }


    ////



    /*
   public void showSpam(View view)
   {
       SpamDialog spamDialog = new SpamDialog();
       spamDialog.show(getFragmentManager(),"Alert!");
   }
*/
    public void showSpam(View view){
        AlertDialog.Builder dialog = new AlertDialog.Builder(ManActivity.this);
        dialog.setTitle("Alert!");
        dialog.setMessage("Are you sure to tag this report as a spam?");

        dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int i){

            }
        });
        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int ih){

                new SaveProductDetailsSpam().execute();
                ManActivity.this.finish();

            }
        });
       dialog.show();
    }

    public void showComplete(View view){
        AlertDialog.Builder dialog = new AlertDialog.Builder(ManActivity.this);
        dialog.setTitle("Alert!");
        dialog.setMessage("Are you sure to complete the report?");

        dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int i){

            }
        });
        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int ih){

                new SaveProductDetails().execute();


                ManActivity.this.finish();

            }
        });
        dialog.show();
    }

    class SaveProductDetailsSpam extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ManActivity.this);
            pDialog.setMessage("Updating ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {

            // getting updated data from EditTexts


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_RID,rid));

            //params.add(new BasicNameValuePair(TAG_PROGRESS, progress));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jParser.makeHttpRequest(updateReportS,
                    "POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully updated
                    Intent i = getIntent();
                    //send result code 100 to notify about product update
                    setResult(100, i);
                    finish();
                } else {
                    // failed to update product
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
            // dismiss the dialog once product uupdated
            pDialog.dismiss();

            Toast message = Toast.makeText(ManActivity.this, "The report is a spam!", Toast.LENGTH_SHORT);
            message.show();
        }
    }


    class SaveProductDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ManActivity.this);
            pDialog.setMessage("Updating ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {

            // getting updated data from EditTexts


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d=new Date();
            String dd=sdf.format(d);
            params.add(new BasicNameValuePair(TAG_FTIME,dd));
            params.add(new BasicNameValuePair(TAG_RID,rid));

            //params.add(new BasicNameValuePair(TAG_PROGRESS, progress));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jParser.makeHttpRequest(updateReport,
                    "POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully updated
                    Intent i = getIntent();
                    //send result code 100 to notify about product update
                    setResult(100, i);
                    finish();
                } else {
                    // failed to update product
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
            // dismiss the dialog once product uupdated
            pDialog.dismiss();

            Toast message = Toast.makeText(ManActivity.this, "This report is completed!", Toast.LENGTH_SHORT);
             message.show();
            new Getemail().execute();
        }
    }





    public void buttonOnClickS(View view){
        //Intent intentM = new Intent(this, MyReportActivity.class);
        Intent intentM = new Intent(this, ManAssignActivity.class);
        intentM.putExtra(RID, rid);
        intentM.putExtra(TAG_BLOCK ,block);

        // intentM.putExtra(RESIDENT, name);
        //intentM.putExtra(RESIDENT_ID, re_id);
        startActivity(intentM);
    }

    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first

        //productsList.clear();
        //new LoadAllProducts().execute();

        //clickCallBack();
        // Activity being restarted from stopped state
        //this.finish();
        // Intent i = getIntent();
        // send result code 100 to notify about product update
        // setResult(100, i);
        finish();

    }







}
