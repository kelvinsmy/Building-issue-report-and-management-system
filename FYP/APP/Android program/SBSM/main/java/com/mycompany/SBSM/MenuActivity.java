package com.mycompany.loginv3;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MenuActivity extends ActionBarActivity {
   String name;
   String re_id;
   TextView txtName1;
    public final static String RESIDENT ="name";
    public final static String RESIDENT_ID ="Re_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        name= intent.getStringExtra(ProcessActivity.RESIDENT);
        re_id= intent.getStringExtra(ProcessActivity.RESIDENT_ID);

        txtName1 = (TextView) findViewById(R.id.textView2);
        String text = "Hello, "+name;
        txtName1.setText(text);

    }

    public void textOnClick2(View view){
        //Intent intentR = new Intent(this, ReportActivity.class);
        Intent intentR = new Intent(this, ReportNewActivity.class);
        intentR.putExtra(RESIDENT, name);
        intentR.putExtra(RESIDENT_ID, re_id);
        startActivity(intentR);
    }

    public void textOnClickM(View view){
        //Intent intentM = new Intent(this, MyReportActivity.class);
        Intent intentM = new Intent(this, MyNewReportActivity.class);
        intentM.putExtra(RESIDENT, name);
        intentM.putExtra(RESIDENT_ID, re_id);
        startActivity(intentM);
    }

    public void textOnClickMM(View view){
        Intent intentM = new Intent(this, ReportActivity.class);
       // Intent intentM = new Intent(this, MyNewReportActivity.class);
        intentM.putExtra(RESIDENT, name);
        intentM.putExtra(RESIDENT_ID, re_id);
        startActivity(intentM);
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
}
