package com.inovaceadmin.aap.inovaceadmin.Activities;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonToken;
import com.inovaceadmin.aap.inovaceadmin.R;
import com.inovaceadmin.aap.inovaceadmin.Resources.Resource;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView textViewEmail,textViewPassword;
    Button buttonSignIn;
    JSONObject jsonObject;
    JsonObjectRequest jsonObjectRequest;
    String token,refreshToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logIn();
    }
    public boolean logIn(){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        textViewEmail = findViewById(R.id.login_username);
        textViewPassword = findViewById(R.id.login_password);
        textViewEmail.setText("asif@inovacetech.com");
        textViewPassword.setText("123456");
        buttonSignIn = findViewById(R.id.activity_login_signin);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm()){
                    Resource resource = new Resource();
                    jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, resource.signInURL, jsonObject,
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
                        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                            Log.e("network",response.toString());
                            Map<String,String> map = response.headers;
                            Log.d("map",map.toString());
                            token = map.get("token");
                            refreshToken = map.get("refresh-token");
                            /**
                             * committing userdata into local cahce
                             */
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token", token);
                            editor.putString("refreshToken", refreshToken);
                            editor.commit();
                            /**
                             * Switch to MainActivity
                             */
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("token",token);
                            intent.putExtra("refreshToken",refreshToken);
                            startActivity(intent);
                            finish();
                            return super.parseNetworkResponse(response);
                        }
                    };

                    requestQueue.add(jsonObjectRequest);
                }
            }
        });
        return true;
    }
    public boolean validateForm(){
        if(TextUtils.isEmpty(textViewEmail.getText())){
            textViewEmail.setError("Field cannot be empty");
            return false;
        }
        if(TextUtils.isEmpty(textViewPassword.getText())){
            textViewEmail.setError("Field cannot be empty");
            return false;
        }
        jsonObject = new JSONObject();
        try {
            jsonObject.put("email", textViewEmail.getText().toString());
            jsonObject.put("password", textViewPassword.getText().toString());
            Log.e("in try",jsonObject.toString());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }
}
