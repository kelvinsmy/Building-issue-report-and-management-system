package com.mycompany.loginv3;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.TabHost;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Use AppCompatActivity if SDK uptodate
public class MyNewReportActivity extends ActionBarActivity implements TabHost.OnTabChangeListener {
    String name;
    String re_id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_new_report);

        Intent intentReport = getIntent();
        name= intentReport.getStringExtra(MenuActivity.RESIDENT);
        re_id= intentReport.getStringExtra(MenuActivity.RESIDENT_ID);
        // MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        //ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        // viewPager.setAdapter(adapter);
        FragmentTabHost tabHost = (FragmentTabHost)findViewById(R.id.tabhost);
        tabHost.setup(this,getSupportFragmentManager(),R.id.fragmenttabholder);

        tabHost.addTab(tabHost.newTabSpec("Completed").setIndicator("Completed"), FragmentCom.class, null);
        tabHost.addTab(tabHost.newTabSpec("In progress").setIndicator("In progress"),FragmentIn.class,null);


        tabHost.setOnTabChangedListener(this);

    }

    @Override
    public void onTabChanged(String tabId) {
        Log.w("TabID",tabId);
    }

    public String getData(){
        return re_id;
    }


}
