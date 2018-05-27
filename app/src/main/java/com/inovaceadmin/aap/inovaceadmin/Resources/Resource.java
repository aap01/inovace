package com.inovaceadmin.aap.inovaceadmin.Resources;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class Resource {
    private String baseUrl = "http://dev.client.inovacetech.com/inovace-client/api/v1";
    public String getAttendance = "http://dev.client.inovacetech.com/inovace-client/api/v1/attendance";
    public String signInURL = "http://dev.client.inovacetech.com/inovace-client/api/v1/auth/signin";
    public String getAttendanceFeed = "http://dev.client.inovacetech.com/inovace-client/api/v1/dashboard/attendanceFeed";
    private String getQueryString(JSONObject jsonObject){
        if(jsonObject==null)return "";
        StringBuilder sb = new StringBuilder();
        Iterator<String> keys = jsonObject.keys();
        sb.append("?"); //start of query args
        while (keys.hasNext()) {
            String key = keys.next();
            sb.append(key);
            sb.append("=");
            try {
                sb.append((String) jsonObject.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            sb.append("&"); //To allow for another argument.
        }
        String s = sb.toString();
        int length = s.length();
        if (length < 2 )return "";
        else{
            int end = length-1;
            return s.substring(0,end);
        }
    }
    public String getDepartment(JSONObject jsonObject){ ////Department////
        return baseUrl+"/department"+getQueryString(jsonObject);
    }

    public String getAttendance(JSONObject jsonObject) {///
        return baseUrl+"/attendance"+getQueryString(jsonObject);
    }
    public String getAttendanceFeed(JSONObject jsonObject) {///attendancefeed////
        return baseUrl+"/dashboard/attendanceFeed"+getQueryString(jsonObject);
    }
    public String getOveralldeptWiseAtt(JSONObject jsonObject) {////Overall and Departmentwise attendance////
        return baseUrl+"/dashboard/overall-and-department-wise-attendance"+getQueryString(jsonObject);
    }
}
