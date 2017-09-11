package com.mycompany.sbsmstaff;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class AssignedReportActivity extends ListActivity {
    String name;
    String st_id;
    String block;
    String rid="";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_STAFFID = "St_id";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_RE_ID = "Re_id";
    private static final String TAG_BLOCK = "block";
    private static final String TAG_TYPE = "type";
    private static final String TAG_PROGRESS = "progress";
    private static final String TAG_RID = "Rid";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_TLOCATION = "tlocation";
    public final static String RID ="Rid";
    public final static String BLOCK="block";
    private static final String TAG_CREATE = "created_at";
    private static final String TAG_FINISH = "finished_at";
    private static final String TAG_NAME = "name";
    JSONParser jParser = new JSONParser();
    private ProgressDialog pDialog;

    ArrayList<HashMap<String, String>> productsList;
    JSONArray products = null;
    private static String url_my_report = "http://plbpc013.ouhk.edu.hk/s1129900/FYP/getAssignedReport.php";
    private static String updateReport= "http://plbpc013.ouhk.edu.hk/s1129900/FYP/updateReportSp.php";
    private static String resetReport= "http://plbpc013.ouhk.edu.hk/s1129900/FYP/resetReportSp.php";

    private static final String TAG_RIDD = "rid";
    String email;
    JSONParser jsonParser = new JSONParser();
    JSONArray productObj;
    private static final String TAG_EMAIL = "email";
    private static final String TAG_PRODUCT = "product";


    boolean checkRO=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_report);
        Spinner spinner11 = (Spinner) findViewById(R.id.spinner123);
        // Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
       ArrayAdapter<CharSequence> adapter11 = ArrayAdapter.createFromResource(this, R.array.sort, android.R.layout.simple_spinner_item);
        adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinner11.setAdapter(adapter11);
        productsList = new ArrayList<HashMap<String, String>>();
        Intent intentReport = getIntent();
        name= intentReport.getStringExtra(MenuActivity.STAFF);
        st_id= intentReport.getStringExtra(MenuActivity.STAFF_ID);
        block= intentReport.getStringExtra(MenuActivity.BLOCK);
        new LoadAllProducts().execute();

        clickCallBack();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assigned_report, menu);
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
            pDialog = new ProgressDialog(AssignedReportActivity.this);
            pDialog.setMessage("Loading Reports. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_BLOCK,block));
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
                        String tlocation="";
                        // Storing each json item in variable
                        String rid= c.getString(TAG_RID);
                        String type = c.getString(TAG_TYPE);
                        String progress = c.getString(TAG_PROGRESS);
                        String block = c.getString(TAG_BLOCK);
                        String location = c.getString(TAG_LOCATION);
                        String time= c.getString(TAG_CREATE);
                        String ftime =c.getString(TAG_FINISH);
                        String name= c.getString(TAG_NAME);

                        type= "Progress: "+progress+"    Staff: "+name+"\n"+type;
                        //type= type+"\n("+progress+")";
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String dH="";
                        String dD="";
                        if(progress.equals("Assigned")) {
                            try {
                                Date tDate = sdf.parse(time);

                                String ttDate = sdf.format(tDate);
                                //Date tttDate=sdf.parse(ttDate);
                                Date d = new Date();
                                //Date c = Calendar.getInstance();

                                long diff = d.getTime() - tDate.getTime();
                                long diffHours = diff / (60 * 60 * 1000) % 24;
                                long diffDays = diff / (24 * 60 * 60 * 1000);
                                dH = Long.toString(diffHours);
                                dD = Long.toString(diffDays);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                           tlocation = "-Block " + block + ",  " + location + "\n-" + dD + " day(s) " + dH + " hour(s) ago";
                        }
                        if(progress.equals("Finished")){
                            try {
                                Date sDate = sdf.parse(time);
                                Date fDate = sdf.parse(ftime);

                                long diff = fDate.getTime() - sDate.getTime();
                                long diffHours = diff / (60 * 60 * 1000) % 24;
                                long diffDays = diff / (24 * 60 * 60 * 1000);
                                dH= Long.toString(diffHours);
                                dD= Long.toString(diffDays);
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                            tlocation = "-Block "+block+",  " + location+"\n-Finished in "+dD+" day(s) and "+dH+" hour(s)";
                        }
                        //
                        // String price = c.getString(TAG_PRICE);
                        //String description = c.getString(TAG_DESCRIPTION);
                        //

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_RID, rid);
                        map.put(TAG_TYPE, type);
                        map.put(TAG_TLOCATION, tlocation);
                        map.put(TAG_PROGRESS,progress);
                        // map.put(TAG_PRICE, price);
                        //map.put(TAG_DESCRIPTION, description);
                        // adding HashList to ArrayList
                        productsList.add(map);
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
            pDialog.dismiss();



            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */


                    SimpleAdapter adapter = new SimpleAdapter(
                            AssignedReportActivity.this, productsList,
                            R.layout.list_item, new String[] { TAG_TYPE,
                            TAG_TLOCATION, TAG_RID, TAG_PROGRESS },
                            new int[] { R.id.pp, R.id.lo, R.id.price, R.id.description});
                    // updating listview
                    setListAdapter(adapter);

                }
            });

        }

    }

    public void clickCallBack(){
        ListView lv;
        lv = getListView();
        lv.setTextFilterEnabled(true);

        //////
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast message = Toast.makeText(AssignedReportActivity.this, "This report is not noooo yet!", Toast.LENGTH_SHORT);
                //message.show();
                rid = ((TextView) view.findViewById(R.id.price)).getText()
                        .toString();
                AlertDialog.Builder dialog = new AlertDialog.Builder(AssignedReportActivity.this);
                dialog.setTitle("Alert!");
                dialog.setMessage("Are you sure to reset the report?");

                dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogInterface, int ih) {

                        new ResetProductDetails().execute();


                        // ManActivity.this.finish();

                    }
                });
                dialog.show();

                return true;
            }});
        /////
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem

                rid = ((TextView) view.findViewById(R.id.price)).getText()
                        .toString();
                String pro =((TextView) view.findViewById(R.id.description)).getText()
                        .toString();

                if(pro.equals("Finished")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(AssignedReportActivity.this);
                    dialog.setTitle("Alert!");
                    dialog.setMessage("Are you sure to complete the report?");

                    dialog.setNegativeButton("Rework", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialogInterface, int i) {
                             checkRO=false;
                            updateReport="http://plbpc013.ouhk.edu.hk/s1129900/FYP/reworkReport.php";
                            new SaveProductDetails().execute();
                        }
                    });
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialogInterface, int ih) {
                            checkRO=true;
                            updateReport= "http://plbpc013.ouhk.edu.hk/s1129900/FYP/updateReportSp.php";
                            new SaveProductDetails().execute();


                            // ManActivity.this.finish();


                        }
                    });
                    dialog.show();


                    // Toast message = Toast.makeText(ReportActivity.this, "Report is created successfully!", Toast.LENGTH_SHORT);
                    //message.show();


                    //Intent intent = new Intent(getApplicationContext(), ManActivity.class);
                    //intent.putExtra(RID, rid);
                    // intent.putExtra(BLOCK ,block);

                    // startActivity(intent);

                    // Starting new intent

                 /*
                String pid = ((TextView) view.findViewById(R.id.pid)).getText()
                        .toString();

                Toast.makeText(getApplicationContext(),pid, Toast.LENGTH_SHORT).show();
                */
                }else{
                    Toast message = Toast.makeText(AssignedReportActivity.this, "This report is not finished yet!", Toast.LENGTH_SHORT);
                    message.show();
                }
            }
        });

    }


    class SaveProductDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AssignedReportActivity.this);
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
                    //setResult(100, i);
                   // finish();
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

            Toast message = Toast.makeText(AssignedReportActivity.this, "Successful!", Toast.LENGTH_SHORT);
            message.show();
           new Getemail().execute();
        }
    }

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




            if(checkRO==true) {

                sendemail();
            }else{
                productsList.clear();
                new LoadAllProducts().execute();
            }

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
            //this.finish();
            productsList.clear();
            new LoadAllProducts().execute();

            clickCallBack();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AssignedReportActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }


    ///
    class ResetProductDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pDialog = new ProgressDialog(AssignedReportActivity.this);
            //pDialog.setMessage("Loading ...");
            //pDialog.setIndeterminate(false);
           // pDialog.setCancelable(true);
            //pDialog.show();
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
            JSONObject json = jParser.makeHttpRequest(resetReport,
                    "POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully updated
                    //Intent i = getIntent();
                    //send result code 100 to notify about product update
                    //setResult(100, i);
                   // finish();
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
            //pDialog.dismiss();

            Toast message = Toast.makeText(AssignedReportActivity.this, "This report is reset!", Toast.LENGTH_SHORT);
            message.show();
            //new Getemail().execute();
            productsList.clear();
            new LoadAllProducts().execute();

            clickCallBack();
        }
    }

    ///

}
