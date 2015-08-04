package com.maria.webapplication;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.apache.http.client.HttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onClick(View view){
        String url="https://www.google.com";
        new OpenUrlTask().execute(url);

    }

    private class OpenUrlTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return openURLfromNetwork(params[0]);
        }

        protected String openURLfromNetwork(String urlstring){
            String result="";
            try{
                URL url=new URL(urlstring);
                HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
                try{
                    InputStream is=urlConnection.getInputStream();
                    BufferedReader br=new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb= new StringBuilder();
                    String line=br.readLine();
                    while(line!=null){
                        sb.append(line+"/n");
                        line=br.readLine();
                    }
                    result=sb.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
                return result;

            }catch(Exception e){
                e.printStackTrace();
            }
            return "";
        }

        protected void onPostExecute(String result){
            TextView tv= (TextView)findViewById(R.id.text);
            tv.setText(result);
        }
    }
}
