package com.github.binarywang.demo.wx.mp.controller;

import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Edward
 */
@AllArgsConstructor
@Controller
@RequestMapping("/wx")
public class WxCommonController {
    private final WxMpService wxService;

    @RequestMapping("/getopenid")
    public String greetUser(@RequestParam(name ="openid", required = false) String openid) {
        System.out.println(openid);
        return "greet_user";
    }

}
