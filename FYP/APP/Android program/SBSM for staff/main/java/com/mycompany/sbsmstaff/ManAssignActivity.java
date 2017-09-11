package com.mycompany.sbsmstaff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TabHost;



import android.app.ProgressDialog;
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
public class ManAssignActivity extends ActionBarActivity implements TabHost.OnTabChangeListener {
String rid;
String block;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_assign);
        Intent intent = getIntent();
        rid= intent.getStringExtra(ManActivity.RID);

        block= intent.getStringExtra(ReportActivity.BLOCK);
        // MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        //ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        // viewPager.setAdapter(adapter);
        FragmentTabHost tabHost = (FragmentTabHost)findViewById(R.id.tabhost);
        tabHost.setup(this,getSupportFragmentManager(),R.id.fragmenttabholder);

        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Cleaner"), FragmentTypeOne.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Technician"),FragmentTypeTwo.class,null);
        //tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("TAB 3"),FragmentTab3.class,null);

        tabHost.setOnTabChangedListener(this);

    }

    @Override
    public void onTabChanged(String tabId) {
        Log.w("TabID", tabId);
    }

    public String getBlock(){
        return block;
    }
    public String getRid(){return rid;}





}
