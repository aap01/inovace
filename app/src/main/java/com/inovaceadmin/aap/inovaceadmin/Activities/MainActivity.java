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
import com.inovaceadmin.aap.inovaceadmin.R;
import com.inovaceadmin.aap.inovaceadmin.Resources.Resource;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private android.support.v7.widget.Toolbar toolBar;
    String token=null,refreshToken = null;
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
    public boolean configureLayout(){
        /**
         * implementing attendance GET method
         */
        Resource resource = new Resource();
        JSONObject jsonObject = new JSONObject();

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, resource.getDepartment, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response",response.toString());
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
        requestQueue.add(jsonObjectRequest);




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
