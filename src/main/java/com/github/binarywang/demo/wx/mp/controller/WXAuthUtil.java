package com.github.binarywang.demo.wx.mp.controller;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class WXAuthUtil {
    public static final String APPID="wxc63d03897d2f7f52";
    public static final String APPSECRET ="1d1accdb102126d78d853c3001a31009";

    public static Map<String, String> doGetJson(String url) throws ClientProtocolException, IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet =new HttpGet(url);
        HttpResponse response =  client.execute(httpGet);
        HttpEntity entity =response.getEntity();
        String result= "";
        Map<String, String> map = null;
        if(entity!=null)
        {
            //把返回的结果转换为JSON对象
            result =EntityUtils.toString(entity, "UTF-8");
//              jsonObject =JSON.parseObject(result);
            GsonBuilder gb = new GsonBuilder();
            Gson g = gb.create();
            map = g.fromJson(result, new TypeToken<Map<String, Object>>() {}.getType());

        }

        return map;
    }
}
