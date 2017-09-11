package com.mycompany.loginv3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

public class ReportNewActivity extends ActionBarActivity {
String pType;
String pD;
String lB;
String lF;
String lio;
String lD;

boolean con1;
boolean con2;
boolean con3;
boolean con4;
boolean con5;
boolean con6;
    String name;
    String re_id;
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    private static final String TAG_SUCCESS = "success";
    private static final String url_create_report = "http://plbpc013.ouhk.edu.hk/s1129900/FYP/create_report.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_new);
        Intent intentReport = getIntent();
        name= intentReport.getStringExtra(MenuActivity.RESIDENT);
        re_id= intentReport.getStringExtra(MenuActivity.RESIDENT_ID);


        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
       // Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.problem, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    //Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " slected", Toast.LENGTH_LONG).show();
                    pType=parent.getItemAtPosition(position)+"";
                    con1=true;
                    int problemtype=position;
                    problemphaseTwo(problemtype);
                }else{

                    Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(ReportNewActivity.this, R.array.none, android.R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                    con1=false;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinner3 = (Spinner) findViewById(R.id.spinner);
        // Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.block, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                   // Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " slected", Toast.LENGTH_LONG).show();
                    con2=true;
                    lB=parent.getItemAtPosition(position)+"";
                    phaseFloor();
                }else{
                    con2=false;
                    Spinner spinner4 = (Spinner) findViewById(R.id.spinner3);
                    // Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
                    ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(ReportNewActivity.this, R.array.none, android.R.layout.simple_spinner_item);
                    adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner4.setAdapter(adapter4);
                    Spinner spinner5 = (Spinner) findViewById(R.id.spinner4);
                    // Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
                    ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(ReportNewActivity.this, R.array.none, android.R.layout.simple_spinner_item);
                    adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner5.setAdapter(adapter5);

                    Spinner spinner6 = (Spinner) findViewById(R.id.spinner5);
                    // Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
                    ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(ReportNewActivity.this, R.array.none, android.R.layout.simple_spinner_item);
                    adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner6.setAdapter(adapter6);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*
        Button btnCreateProduct = (Button) findViewById(R.id.button2);
        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new CreateNewProduct().execute();
            }
        });
*/

    }

    public void phaseFloor(){
        Spinner spinner4 = (Spinner) findViewById(R.id.spinner3);
        // Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.floor, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0) {
                    //Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " slected", Toast.LENGTH_LONG).show();
                    lF=parent.getItemAtPosition(position)+"";
                    int problemtype=position;
                    con3=true;
                   phaseInout();
                }else{
                    con3=false;
                    Spinner spinner5 = (Spinner) findViewById(R.id.spinner4);
                    // Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
                    ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(ReportNewActivity.this, R.array.none, android.R.layout.simple_spinner_item);
                    adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner5.setAdapter(adapter5);

                    Spinner spinner6 = (Spinner) findViewById(R.id.spinner5);
                    // Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
                    ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(ReportNewActivity.this, R.array.none, android.R.layout.simple_spinner_item);
                    adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner6.setAdapter(adapter6);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void phaseInout(){
        Spinner spinner5 = (Spinner) findViewById(R.id.spinner4);
        // Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this, R.array.inout, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter5);
        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position != 0) {
                    //Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " slected", Toast.LENGTH_LONG).show();
                    lio = parent.getItemAtPosition(position) + "";
                    int problemtype = position;
                    con4=true;
                    phaseFine(problemtype);
                }else{
                    con4=false;
                    Spinner spinner6 = (Spinner) findViewById(R.id.spinner5);
                    // Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
                    ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(ReportNewActivity.this, R.array.none, android.R.layout.simple_spinner_item);
                    adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner6.setAdapter(adapter6);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void phaseFine(int problemtype){
        if(problemtype==1) {
            Spinner spinner6 = (Spinner) findViewById(R.id.spinner5);
            // Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
            ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this, R.array.detin, android.R.layout.simple_spinner_item);
            adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner6.setAdapter(adapter6);
            spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   if(position!=0) {
                       //Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " slected", Toast.LENGTH_LONG).show();
                       con5=true;
                       int problemtype = position;
                       lD = parent.getItemAtPosition(position) + "";
                   }else{
                       con5=false;
                   }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        if(problemtype==2){
            Spinner spinner6 = (Spinner) findViewById(R.id.spinner5);
            // Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
            ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this, R.array.detout, android.R.layout.simple_spinner_item);
            adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner6.setAdapter(adapter6);
            spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position!=0) {
                        con5=true;
                        //Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " slected", Toast.LENGTH_LONG).show();
                        int problemtype = position;
                    }else{
                        con5=false;
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }

    public void problemphaseTwo(int problemtype){
        if(problemtype==1) {
            Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.problemE, android.R.layout.simple_spinner_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   // ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                    //((TextView) parent.getChildAt(0)).setTextSize(5);

                  if(position!=0) {
                      // Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " slected", Toast.LENGTH_LONG).show();
                      pD = parent.getItemAtPosition(position) + "";
                      con6=true;
                  }else{
                      con6=false;
                  }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        if(problemtype==2) {
            Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.problemW, android.R.layout.simple_spinner_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position!=0) {
                        // Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " slected", Toast.LENGTH_LONG).show();
                        pD = parent.getItemAtPosition(position) + "";
                        con6=true;
                    }else{
                        con6=false;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        if(problemtype==3) {
            Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.problemR, android.R.layout.simple_spinner_item);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position!=0) {
                        // Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " slected", Toast.LENGTH_LONG).show();
                        pD = parent.getItemAtPosition(position) + "";
                        con6=true;
                    }else{
                        con6=false;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    public void submit(View view){
        if(con1&&con2&&con3&&con4&&con5&&con6) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(ReportNewActivity.this);
            dialog.setTitle("Confirmation");
            dialog.setMessage("Are you sure to submit this report?");

            dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialogInterface, int ih) {

                    new CreateNewProduct().execute();


                    ReportNewActivity.this.finish();

                }
            });
            dialog.show();
        }else{
            Toast.makeText(getBaseContext(), "Missing information!", Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report_new, menu);
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

    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReportNewActivity.this);
            pDialog.setMessage("Creating Report..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String type = pD;
            String block = lB;
            String location = "Floor "+lF+", "+lio+", "+lD;

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
                    ReportNewActivity.this.finish();

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
            Toast message = Toast.makeText(ReportNewActivity.this, "Report is created successfully!", Toast.LENGTH_SHORT);
            message.show();
        }

    }






}
