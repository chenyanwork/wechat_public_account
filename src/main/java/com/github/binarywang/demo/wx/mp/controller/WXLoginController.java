package com.github.binarywang.demo.wx.mp.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * FileName: WXLoginController.java
 * @Description: 通过公众号获取微信用户信息
 * All rights Reserved, Designed By JS-YFB
 * Copyright:   Copyright(C) 2017-2027
 * Company      JS-YFB LTD.
 * @author:     yc
 * @version     V1.0
 * Createdate:  2018年8月30日 下午11:18:02
 *
 */


@Controller
public class WXLoginController {
    private static final Logger logger = Logger.getLogger(WXLoginController.class);
    /**
     * 公众号微信登录授权
     * @param request
     * @param response
     * @return
     * @throws ParseException
     * @author  lbh
     * @date 创建时间：2018年1月18日 下午7:33:59
     * @parameter
     */
    @RequestMapping(value = "/wxLogin", method = RequestMethod.GET)
    public String wxLogin(HttpServletRequest request,
                          HttpServletResponse response)
        throws ParseException {
        //这个url的域名必须要进行再公众号中进行注册验证tfds.scjinsui.com:9001，这个地址是成功后的回调地址
        String backUrl="http://tdmkw6.natappfree.cc/faultplatform/callBack";
        // 第一步：用户同意授权，获取code
        String url ="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WXAuthUtil.APPID
            + "&redirect_uri="+backUrl
            + "&response_type=code"
            + "&scope=snsapi_userinfo"
            + "&state=STATE#wechat_redirect";

        logger.info("forward重定向地址{" + url + "}");
        //response.sendRedirect(url);
        return "redirect:"+url;//必须重定向，否则不能成功
    }
    /**
     * 公众号微信登录授权回调函数
     * @param modelMap
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     * @author  lbh
     * @date 创建时间：2018年1月18日 下午7:33:53
     * @parameter
     */
    @RequestMapping(value = "/callBack", method = RequestMethod.GET)
    @ResponseBody
    public String callBack(ModelMap modelMap,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取code
        String code =req.getParameter("code");
        //获取openid
        Map<String, String> map1 = getOpenId(code);
        String openid = map1.get("openid");
        //获取基础token
        Map<String, String> map2 = getToken();
        String token = map2.get("access_token");
        //获取用户信息
        Map<String, String> map3 = getUserInfo(token,openid);
//        net.sf.json.JSONObject jsonObject=WeiXin.getWeiXin(code);
//        return jsonObject.toString();
        return map3.toString();
    }

    /**
     * @Title: getToken
     * @Description: 获取基础token
     * @param: @return
     * @param: @throws ClientProtocolException
     * @param: @throws IOException
     * @return: Map<String,String>
     * @throws
     */
    public Map<String, String> getToken() throws ClientProtocolException, IOException{
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+WXAuthUtil.APPID
            + "&secret="+ WXAuthUtil.APPSECRET;
        Map<String, String> map = WXAuthUtil.doGetJson(url);
        return map;
    }


    /**
     * @Title: getOpenId
     * @Description: 通过code获取OpenId
     * @param: @param code
     * @param: @return
     * @param: @throws ClientProtocolException
     * @param: @throws IOException
     * @return: Map<String,String>
     * @throws
     */
    public Map<String, String> getOpenId(String code) throws ClientProtocolException, IOException{
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+WXAuthUtil.APPID
            + "&secret="+WXAuthUtil.APPSECRET
            + "&code="+code
            + "&grant_type=authorization_code";
        Map<String, String> map = WXAuthUtil.doGetJson(url);
        return map;
    }


    /**
     * @Title: getUserInfo
     * @Description: 获取useriNFO
     * @param: @param accessToken
     * @param: @param openId
     * @param: @return
     * @param: @throws ClientProtocolException
     * @param: @throws IOException
     * @return: Map<String,String>
     * @throws
     */
    public Map<String, String> getUserInfo(String accessToken, String openId) throws ClientProtocolException, IOException {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+openId;
        Map<String, String> map = WXAuthUtil.doGetJson(requestUrl);
        return map;

    }
}
