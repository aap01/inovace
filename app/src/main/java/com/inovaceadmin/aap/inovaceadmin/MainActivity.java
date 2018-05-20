package com.inovaceadmin.aap.inovaceadmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private android.support.v7.widget.Toolbar toolBar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /***code for drawer toolbar***/
        toolBar = findViewById(R.id.activity_main_actionbar);
        setSupportActionBar(toolBar);



        /***code for drawer toggle button***/
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
                    drawerLayout.closeDrawer(1);
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
