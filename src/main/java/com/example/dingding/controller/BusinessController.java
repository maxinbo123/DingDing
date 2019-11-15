package com.example.dingding.controller;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.example.dingding.service.DingdingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;


/**
 * @Author: heshouyou
 * @Description  Dubbo业务执行入口
 * @Date Created in 2019/1/14 17:15
 */
@Controller
public class BusinessController extends BaseController{

    private Logger logger = LoggerFactory.getLogger("bussiness");

    @Autowired
    private DingdingService dingdingService;
    @RequestMapping("index")
    public String gotoManagerPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            return "Index.html";
        } catch (Exception e) {
            logger.error("跳转页面异常", e);
            return null;
        }
    }

    @RequestMapping("back")
    @ResponseBody
    public String back(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String,String> map = super.getParametersFromPage(request);
            Map<String,Object> res = dingdingService.getUserInfoByLogin(map);
            return JSON.toJSONString(res);
        } catch (Exception e) {
            logger.error("跳转页面异常", e);
            return null;
        }
    }
    @RequestMapping("test")
    @ResponseBody
    public String test(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> map = super.getParametersFromPage(request);
      return JSON.toJSONString(map);
    }

    @RequestMapping("role")
    @ResponseBody
    public String role(HttpServletRequest request, HttpServletResponse response) {
        return JSON.toJSONString(dingdingService.getRole());
    }

    @RequestMapping("userRole")
    @ResponseBody
    public String userRole(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> map = super.getParametersFromPage(request);
        return JSON.toJSONString(dingdingService.getUserRole(map));
    }

    @RequestMapping("department")
    @ResponseBody
    public String department(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> map = super.getParametersFromPage(request);
        return JSON.toJSONString(dingdingService.getDepartment(map));
    }

    @RequestMapping("getToken")
    @ResponseBody
    public String getToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            return dingdingService.getAccesstoken();
        } catch (Exception e) {
            logger.error("跳转页面异常", e);
            return "";
        }
    }
}
