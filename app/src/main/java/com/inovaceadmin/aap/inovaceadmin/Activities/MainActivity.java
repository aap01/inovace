package com.inovaceadmin.aap.inovaceadmin.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.inovaceadmin.aap.inovaceadmin.R;
import com.inovaceadmin.aap.inovaceadmin.Resources.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private android.support.v7.widget.Toolbar toolBar;
    String token=null,refreshToken = null;
    Date date;
    JsonObjectRequest jsonObjectRequest;
    boolean res =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        refreshToken = intent.getStringExtra("refreshToken");

        if(token!=null){
            Log.e("TOKEN",token);
            Log.e("REFRESHTOKEN",refreshToken);
            configureLayout();
        }
        else {
            Intent intent1 = new Intent(this,LoginActivity.class);
            startActivity(intent1);
            finish();
        }
    }
    public void addLayoutDynamically(JSONObject jsonObject){
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = jsonObject.getJSONArray("departmentList");
            for(int i = 0; i<jsonArray.length(); i++){
                //Log.d("JSON OBJECT",jsonArray.getJSONObject(i).toString());
                String deartmentName = (String) jsonArray.getJSONObject(i).get("name");
                Log.d("departmentName",deartmentName);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public boolean configureLayout(){
        /**
         * implementing attendance GET method
         */
        Resource resource = new Resource();
        JSONObject jsonObject = new JSONObject();
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        /**
         * Getting departmentList
         */
        JsonObjectRequest jsonObjectRequest_department = new JsonObjectRequest(Request.Method.GET, resource.getDepartment, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response",response.toString());
                        addLayoutDynamically(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error",error.toString());
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map map = new HashMap<String,String>();
                map.put("token",token);
                map.put("refresh-token",refreshToken);
                return map;

            }

        };
        /**
         * configuring date json object for query based on date
         */
        final long today = System.currentTimeMillis();
        final long yesterday = today-86400000;
        Log.d("Today",Long.toString(today));
        Log.d("Yesterday",Long.toString(yesterday));
        JSONObject attendanceRequest = new JSONObject();
        try {
            attendanceRequest.put("from",yesterday);
            attendanceRequest.put("to",today);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest_attendance = new JsonObjectRequest(Request.Method.GET, resource.getAttendance, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("attendance Response",response.toString());
                        addLayoutDynamically(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error",error.toString());
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map map = new HashMap<String,String>();
                map.put("token",token);
                map.put("refresh-token",refreshToken);
                return map;

            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap<String,String>();
                map.put("from",yesterday);
                map.put("to",today);
                return map;
            }
        };
        requestQueue.add(jsonObjectRequest_department);
        requestQueue.add(jsonObjectRequest_attendance);

        /**
         * code for drawer toolbar
         */
        toolBar = findViewById(R.id.activity_main_actionbar);
        setSupportActionBar(toolBar);

        /**
         *code for drawer toggle button**
         */
        drawerLayout = findViewById(R.id.activity_main_drawerlayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**
         * Navigation menu item selection
         */
        NavigationView navigationView = findViewById(R.id.activity_main_navigationview);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.logout){
                   // drawerLayout.closeDrawer(1);
                    token = null;refreshToken=null;
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                }
                return false;
            }
        });
        return true;
    }
    /**
     * function for drawer open and close
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
