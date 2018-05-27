package com.inovaceadmin.aap.inovaceadmin.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.inovaceadmin.aap.inovaceadmin.Adapters.DashboardRecyclerViewAdapter;
import com.inovaceadmin.aap.inovaceadmin.Models.DashboardCard;
import com.inovaceadmin.aap.inovaceadmin.R;
import com.inovaceadmin.aap.inovaceadmin.Resources.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private android.support.v7.widget.Toolbar toolBar;
    String token="",refreshToken = "";
    Date date;
    JsonObjectRequest jsonObjectRequest;
    boolean res =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * Retrieving userdata from cache
         */
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPreferences.getString("token","");
        refreshToken = sharedPreferences.getString("refreshToken","");


        /*Intent intent = getIntent();
        token = intent.getStringExtra("token");

        refreshToken = intent.getStringExtra("refreshToken");*/

        if(token!=""){
            Log.e("TOKEN",token);
            Log.e("REFRESHTOKEN",refreshToken);
            fetchServerResponse();
            configureBasicLayout();
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
    public void fetchServerResponse(){
        /**
         * implementing attendance GET method
         */
        Resource resource = new Resource();
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        /**
         * Getting departmentList
         */
        JsonObjectRequest jsonObjectRequest_department = new JsonObjectRequest(Request.Method.GET, resource.getDepartment(null), null,
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
            attendanceRequest.put("from",Long.toString(yesterday));
            attendanceRequest.put("to",Long.toString(today));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest_attendance = new JsonObjectRequest(Request.Method.GET, resource.getAttendance(attendanceRequest), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("attendance Response",response.toString());
                        //addLayoutDynamically(response);
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

        JSONObject attendanceFeedRequest = new JSONObject();
        try {
            attendanceFeedRequest.put("shiftId","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest_attendanceFeed = new JsonObjectRequest(Request.Method.GET, resource.getAttendanceFeed(attendanceFeedRequest), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("attendanceFeedResponse",response.toString());
                        //addLayoutDynamically(response);
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
        JSONObject overallattendanceRequest = new JSONObject();
        try {
            overallattendanceRequest.put("shiftId","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest_overall = new JsonObjectRequest(Request.Method.GET, resource.getOveralldeptWiseAtt(overallattendanceRequest), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("overall",response.toString());
                        configureLayoutDynamically(response);
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
        requestQueue.add(jsonObjectRequest_department);
        requestQueue.add(jsonObjectRequest_attendance);
        requestQueue.add(jsonObjectRequest_attendanceFeed);
        requestQueue.add(jsonObjectRequest_overall);
    }

    public void configureLayoutDynamically(JSONObject jsonObject){
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("departments");
            int i = 0;
            List cardList = new ArrayList<>();
            while(i!=jsonArray.length()){
                String departmentName = jsonArray.getJSONObject(i).getString("name");
                String absent = Integer.toString((int)jsonArray.getJSONObject(i).get("absent"));
                String present = Integer.toString((int)jsonArray.getJSONObject(i).get("present"));
                String total = Integer.toString((int)jsonArray.getJSONObject(i).get("totalEmployee"));
                System.out.println(departmentName+'\n'+"absent: "+absent+'\n'+"present: "+present+'\n'+"total: "+total);
                cardList.add(new DashboardCard(departmentName,absent,present,total));
                i++;
            }
            RecyclerView recyclerView = findViewById(R.id.activity_main_card_recyclerview);
            DashboardRecyclerViewAdapter dashboardRecyclerViewAdapter = new DashboardRecyclerViewAdapter(this,cardList);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(dashboardRecyclerViewAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void configureBasicLayout(){
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
                    /**
                     * caching logout in local data
                     */
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", "");
                    editor.putString("refreshToken", "");
                    editor.commit();
                    /**
                     * switching to loginactivity
                     */
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                }
                return false;
            }
        });
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
