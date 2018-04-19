package com.example.bruce.httpget;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JTL526922 on 2017/11/21.
 */

public class UserService {

    /**用户登录*/
    public static JSONObject userLoginJson(String username,String password) throws JSONException {

        JSONObject json = new JSONObject();

        json.put("username",username);
        json.put("password",password);
        return json;
    }//userLoginJson

    /**上传图片*/
    public static JSONObject uploadImg(String img) throws JSONException {

        JSONObject jsonImg = new JSONObject();

        jsonImg.put("img",img);

        return jsonImg;
    }//uploadImg


    /**下载图片*/



}
