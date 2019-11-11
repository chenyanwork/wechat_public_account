package com.github.binarywang.demo.wx.mp.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * weixin-java-mp-demo-springboot 2019/9/29 huijin.shuailijie Init
 */
public class TulingApiUtil {
    /**
     * 调用图灵机器人api接口，获取智能回复内容，解析获取自己所需结果
     *
     * @param content
     * @return
     */

    private static final String KEY = "f0a1d01439f24a709f44a8a6bba3c735";

    public static String getTulingResult(String content) {


        /** 此处为图灵api接口，参数key需要自己去注册申请，先以11111111代替 */
        String apiUrl = "http://www.tuling123.com/openapi/api?key=" + KEY + "&info=";
        String param = "";


        try {
            param = apiUrl + URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            System.out.println("UnsupportedEncodingException");
            e1.printStackTrace();
        } //将参数转为url编码


        /** 发送httpget请求 */
        HttpGet request = new HttpGet(param);
        String result = "";
        try {

            HttpResponse response = HttpClients.createDefault().execute(request);
            /**
             * 特别注意  这一步一定要加commons-logging 这个jar包  否则会没反应，调试了好久！！
             * 似乎这个jar包是打印信息的
             */
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                result = EntityUtils.toString(response.getEntity());
            } else {
            }
        } catch (ClientProtocolException e) {
            System.out.println("ClientProtocolException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }


        /** 请求失败处理 */
        if (null == result) {
            return "对不起，你说的话真是太高深了……";
        }


        try {
            StringBuffer bf = new StringBuffer();
            String s = "";
            JSONObject json = new JSONObject(result);
            //以code=100000为例，参考图灵机器人api文档
            /**
             *  code  说明
             100000文本类
             200000链接类
             302000新闻类
             308000菜谱类
             */
            if (100000 == json.getInt("code")) {
                s = json.getString("text");
                bf.append(s);
            } else if (200000 == json.getInt("code")) {
                s = json.getString("text");
                bf.append(s);
                bf.append("\n");
                s = json.getString("url");
                bf.append(s);
            } else if (302000 == json.getInt("code")) {
                s = "待开发有点麻烦！\n";
                bf.append(s);
            } else if (308000 == json.getInt("code")) {
                s = "待开发有点麻烦！\n";
                bf.append(s);
            }
            result = bf.toString();
        } catch (JSONException e) {
            System.out.println("JSONException");
            e.printStackTrace();
        }
        return result;
    }
}
