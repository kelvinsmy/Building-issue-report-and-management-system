package com.mycompany.loginv3;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReportActivity extends ActionBarActivity {
String name;
String re_id;
TextView txtName2;

private ProgressDialog pDialog;

JSONParser jsonParser = new JSONParser();
EditText inputtype;
EditText inputblock;
EditText inputlocation;

private static final String TAG_SUCCESS = "success";
private static final String url_create_report = "http://plbpc013.ouhk.edu.hk/s1129900/FYP/create_report.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent intentReport = getIntent();
        name= intentReport.getStringExtra(MenuActivity.RESIDENT);
        re_id= intentReport.getStringExtra(MenuActivity.RESIDENT_ID);

        //txtName2 = (TextView) findViewById(R.id.textView3);
        //String text = "Hello, "+name+re_id;
        //txtName2.setText(text);





        inputtype = (EditText) findViewById(R.id.inputtype);
        inputblock = (EditText) findViewById(R.id.inputblock);
        inputlocation = (EditText) findViewById(R.id.inputlocation);

       /*
        Button btnCreateProduct = (Button) findViewById(R.id.btnCreateReport);
        // button click event

            btnCreateProduct.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    // creating new product in background thread
                    new CreateNewProduct().execute();
                }
            });

            Toast.makeText(getBaseContext(),"Invalid input!", Toast.LENGTH_LONG).show();
        */
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

    public void textOnClickReport(View view){
        String typecon = inputtype.getText().toString();
        String locationcon = inputlocation.getText().toString();
        String blockcon = inputblock.getText().toString();

        if(blockcon.matches("[ABC]+"))
        {
            if(blockcon.length()<2&&typecon.length()>0&&locationcon.length()>0) {
                new CreateNewProduct().execute();
            }else
                Toast.makeText(getBaseContext(),"empty input!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getBaseContext(),"Invalid block input!", Toast.LENGTH_LONG).show();
        }


        /*
        Pattern p = Pattern.compile("[^A-Z]");
        if (p.matcher(blockcon).find()) {
            if(blockcon.length()<2&&typecon.length()>0&&locationcon.length()>0) {
                new CreateNewProduct().execute();
            }else
                Toast.makeText(getBaseContext(),"empty input!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getBaseContext(),"Invalid block input!", Toast.LENGTH_LONG).show();
        }

*/
    }






    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReportActivity.this);
            pDialog.setMessage("Creating Report..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String type = inputtype.getText().toString();
            String block = inputblock.getText().toString();
            String location = inputlocation.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("Re_id", re_id));
            params.add(new BasicNameValuePair("type", type));
            params.add(new BasicNameValuePair("block", block));
            params.add(new BasicNameValuePair("location", location));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_report,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    //Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                    //startActivity(i);
                    Intent intent = new Intent();

                    setResult(RESULT_OK,intent);
                    ReportActivity.this.finish();

                    //Toast message = Toast.makeText(ReportActivity.this, "Report is created successfully!", Toast.LENGTH_SHORT);
                    //message.show();
                    // closing this screen
                    //finish();
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
            pDialog.dismiss();
            Toast message = Toast.makeText(ReportActivity.this, "Report is created successfully!", Toast.LENGTH_SHORT);
            message.show();
        }

    }





}
