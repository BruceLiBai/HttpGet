package com.example.bruce.httpget;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JTL526922 on 2018/4/10.
 * 返回json数据对象
 */

public class JsonData {

    /**
     * @param username 用户名
     * @param password 密码
     * @param role 角色
     * @return json对象
     */
    public static JSONObject loginJson(final String username, final String password,final String role){

        JSONObject json = new JSONObject();

        try {
            json.put("username",username);
            json.put("password",password);
            json.put("role",role);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }//loginJson



}
