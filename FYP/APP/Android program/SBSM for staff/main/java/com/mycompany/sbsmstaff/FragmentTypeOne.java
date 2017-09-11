package com.mycompany.sbsmstaff;

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

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class FragmentTypeOne extends ListFragment {

    String rid;
    String value;
    String re_id="1";
    String name;
    String st_id;
    String block;
    String stid;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_RID = "Rid";
    private static final String TAG_BLOCK = "block";
    private static final String TAG_TYPE = "type";
    private static final String TAG_PROGRESS = "progress";
    private static final String TAG_NAME = "name";
    private static final String TAG_TASK = "task";
    private static final String TAG_STATE = "State";
    public final static String RID ="Rid";
    private static final String TAG_STID = "St_id";
    private static final String TAG_NAMESTATE = "namestate";
    private static final String TAG_TASKN = "taskn";
    JSONParser jParser = new JSONParser();
    private ProgressDialog pDialog;

    ArrayList<HashMap<String, String>> productsList;
    JSONArray products = null;
    private static String url_my_report = "http://plbpc013.ouhk.edu.hk/s1129900/FYP/stafftypeone.php";
    private static String updateReportAssign = "http://plbpc013.ouhk.edu.hk/s1129900/FYP/updateReportAssign.php";
    /*
    public FragmentTab1() {
        // Required empty public constructor
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_type_one, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ManAssignActivity manAssignActivity = (ManAssignActivity)activity;
        block = manAssignActivity.getBlock();
        rid = manAssignActivity.getRid();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        productsList = new ArrayList<HashMap<String, String>>();
        //block="A";
        new LoadAllProducts().execute();
        clickCallBack();
        //TextView txtResult = (TextView) this.getView().findViewById(R.id.textView1);
        //txtResult.setText(value);

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
                        String stid=c.getString(TAG_STID);
                        // Storing each json item in variable
                        String name= c.getString(TAG_NAME);
                        String State = c.getString(TAG_STATE);
                        String task = c.getString(TAG_TASK);
                        //String block = c.getString(TAG_BLOCK);
                        //String location = c.getString(TAG_LOCATION);
                        //type = type + " ("+progress+")";

                        String namestate= name+"\nstate: "+ State;
                        String taskn="No. of current tasks : "+task;
                        //String tlocation = "Block "+block+",  " + location;
                        //
                        // String price = c.getString(TAG_PRICE);
                        //String description = c.getString(TAG_DESCRIPTION);
                        //

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_STID, stid);
                        map.put(TAG_NAMESTATE, namestate);
                        map.put(TAG_TASKN, taskn);
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




            // updating UI from Background Thread
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */


                    SimpleAdapter adapter = new SimpleAdapter(
                            getActivity(), productsList,
                            R.layout.list_item, new String[] { TAG_NAMESTATE,
                            TAG_TASKN, TAG_STID },
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

               stid = ((TextView) view.findViewById(R.id.price)).getText()
                        .toString();


                // Toast message = Toast.makeText(ReportActivity.this, "Report is created successfully!", Toast.LENGTH_SHORT);
                //message.show();


                //Intent intent = new Intent(getApplicationContext(), ManActivity.class);
                //intent.putExtra(RID, rid);

                //startActivity(intent);

                //Intent intent = new Intent(getActivity(), mFragmentFavorite.class);
                //startActivity(intent);
                new SaveProductDetailsSpam().execute();
                getActivity().finish();

                // Starting new intent

                 /*
                String pid = ((TextView) view.findViewById(R.id.pid)).getText()
                        .toString();

                Toast.makeText(getApplicationContext(),pid, Toast.LENGTH_SHORT).show();
                */
            }
        });

    }

    class SaveProductDetailsSpam extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {

            // getting updated data from EditTexts


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_RID,rid));
            params.add(new BasicNameValuePair(TAG_STID,stid));

            //params.add(new BasicNameValuePair(TAG_PROGRESS, progress));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jParser.makeHttpRequest(updateReportAssign,
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


            Toast message = Toast.makeText(getActivity(), "This report is assigned.", Toast.LENGTH_SHORT);
            message.show();
        }
    }



}
