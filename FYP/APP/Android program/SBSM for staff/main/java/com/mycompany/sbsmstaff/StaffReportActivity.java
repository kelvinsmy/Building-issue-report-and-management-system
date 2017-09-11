package com.mycompany.sbsmstaff;

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


public class StaffReportActivity extends ListActivity {
    String name;
    String st_id;
    String block;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_STAFFID = "St_id";
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
    JSONParser jParser = new JSONParser();
    private ProgressDialog pDialog;

    ArrayList<HashMap<String, String>> productsList;
    JSONArray products = null;
    private static String url_my_report = "http://plbpc013.ouhk.edu.hk/s1129900/FYP/getStaffReport.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_report);

      // ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sort, android.R.layout.simple_spinner_item);
       //adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        productsList = new ArrayList<HashMap<String, String>>();
        Intent intentReport = getIntent();
        name= intentReport.getStringExtra(MenuActivity.STAFF);
        st_id= intentReport.getStringExtra(MenuActivity.STAFF_ID);
        block= intentReport.getStringExtra(MenuActivity.BLOCK);
        new LoadAllProducts().execute();

         clickCallBack();
    }


    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first

        productsList.clear();
        new LoadAllProducts().execute();

        clickCallBack();
        // Activity being restarted from stopped state
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
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
            pDialog = new ProgressDialog(StaffReportActivity.this);
            pDialog.setMessage("Loading reports. Please wait...");
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
            params.add(new BasicNameValuePair(TAG_STAFFID, st_id));
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

                        // Storing each json item in variable
                        String rid= c.getString(TAG_RID);
                        String type = c.getString(TAG_TYPE);
                        //String progress = c.getString(TAG_PROGRESS);
                        String block = c.getString(TAG_BLOCK);
                        String location = c.getString(TAG_LOCATION);
                        String time= c.getString(TAG_CREATE);


                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String dH="";
                        String dD="";
                        try {
                            Date tDate = sdf.parse(time);

                            String ttDate=sdf.format(tDate);
                            //Date tttDate=sdf.parse(ttDate);
                            Date d=new Date();
                            //Date c = Calendar.getInstance();

                            long diff = d.getTime() - tDate.getTime();
                            long diffHours = diff / (60 * 60 * 1000) % 24;
                            long diffDays = diff / (24 * 60 * 60 * 1000);
                            dH= Long.toString(diffHours);
                            dD= Long.toString(diffDays);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }




                        String tlocation = "Block "+block+",  " + location+"\n"+dD+" day(s) "+dH+" hour(s) ago";
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
                            StaffReportActivity.this, productsList,
                            R.layout.list_item, new String[] { TAG_TYPE,
                            TAG_TLOCATION, TAG_RID },
                            new int[] { R.id.pp, R.id.lo, R.id.price });
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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem

                String rid = ((TextView) view.findViewById(R.id.price)).getText()
                        .toString();


                // Toast message = Toast.makeText(ReportActivity.this, "Report is created successfully!", Toast.LENGTH_SHORT);
                //message.show();





                Intent intent = new Intent(getApplicationContext(), StaffActivity.class);
                intent.putExtra(RID, rid);
                intent.putExtra(BLOCK ,block);

                startActivity(intent);

                // Starting new intent

                 /*
                String pid = ((TextView) view.findViewById(R.id.pid)).getText()
                        .toString();

                Toast.makeText(getApplicationContext(),pid, Toast.LENGTH_SHORT).show();
                */
            }
        });

    }





}
