package com.mycompany.loginv3;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Login extends ActionBarActivity {
    Button bLogin;
    TextView registerLink;
    public static final String PREF ="PREF";
    public static final String PREF_NAME = "PREF_NAME";

    public static final String PREF_PASSWORD = "PREF_PASSWORD";


    //EditText etUsername, etPassword;
    public final static String Username123 ="username";
    public final static String Password123 ="password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // bLogin = (Button) findViewById(R.id.bLogin);
        //etUsername = (EditText) findViewById(R.id.etUsername);
        //etPassword = (EditText) findViewById(R.id.etPassword);

        restoredPrefs();


    }


    private void restoredPrefs(){
        SharedPreferences settings = getSharedPreferences(PREF,0);
        String pref_name = settings.getString(PREF_NAME, "");
        String pref_password = settings.getString(PREF_PASSWORD, "");
        if(!pref_name.equals("")) {
            Intent i = new Intent(this, ProcessActivity.class);
            i.putExtra(Username123, pref_name);
            i.putExtra(Password123, pref_password);
            startActivity(i);
        }
    }


   public void textOnClick(View view){
       Intent i = new Intent(this, ProcessActivity.class);
       //etUsername = (EditText) findViewById(R.id.etUsername);
       //etPassword = (EditText) findViewById(R.id.etPassword);


       EditText etUsername = (EditText) findViewById(R.id.etUsername);
       EditText etPassword = (EditText) findViewById(R.id.etPassword);

      String username = etUsername.getText().toString();
      String password = etPassword.getText().toString();

       if(!username.equals("") &&!password.equals("")&&username.length()<20) {
           // Launching All products Activity
           //Intent i = new Intent(getApplicationContext(), ProcessActivity.class);
           i.putExtra(Username123, username);
           i.putExtra(Password123, password);
           startActivity(i);
       }else
       {
           Toast.makeText(getBaseContext(),"Invalid input!", Toast.LENGTH_LONG).show();
       }
   }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
