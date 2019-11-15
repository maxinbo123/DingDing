package com.example.dingding.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.example.dingding.util.OkhttpUtil;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maxb on 2019/11/14.
 */
@Service("dingdingService")
public class DingdingServiceImpl implements DingdingService {
    private String url = "https://oapi.dingtalk.com/sns/getuserinfo_bycode";

    @Value("#{propertiesReader[yourAppId]}")
    private String yourAppId ;

    @Value("#{propertiesReader[yourAppSecret]}")
    private String yourAppSecret;

    @Value("#{propertiesReader[testAppId]}")
    private String testAppId;
    @Value("#{propertiesReader[testAppSecret]}")
    private String testAppSecret;
    private static Logger interfaceLog = LoggerFactory.getLogger("dingding");

    @Override
    public Map<String, Object> getUserInfoByLogin(Map<String, String> param) {
        interfaceLog.info("入参：" + JSON.toJSONString(param));
        //1. 根据入参获取unionid
        String unionid = getUnionid(param);
        interfaceLog.info("unionid：" + unionid);
        //2. 根据unionid获取userId
        String userId = getUserId(unionid);
        interfaceLog.info("userId：" + userId);
        //3. 根据userId获取用户信息
        Map<String, Object> res = getUserInfo(userId);
        interfaceLog.info("用户信息：" + res);
        return res;
    }

    @Override
    public String getUnionid(Map<String, String> param) {
        String res = "";
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/sns/getuserinfo_bycode");
        OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
        req.setTmpAuthCode(param.get("code"));
        try{
            OapiSnsGetuserinfoBycodeResponse response = client.execute(req,yourAppId,yourAppSecret);
            if(response != null && response.isSuccess()){
                OapiSnsGetuserinfoBycodeResponse.UserInfo dingUser =  response.getUserInfo();
                res = dingUser.getUnionid();
            }
        }catch (ApiException e){
               System.out.print("异常啦");
        }

        return res;
    }

    @Override
    public String getUserId(String param) {
        String res = "";
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getUseridByUnionid");
        OapiUserGetUseridByUnionidRequest request = new OapiUserGetUseridByUnionidRequest();
        request.setUnionid(param);
        request.setHttpMethod("GET");
        OapiUserGetUseridByUnionidResponse response;
        try{
            response = client.execute(request, getAccesstoken());
            res = response.getUserid();
        }catch (Exception e){
            System.out.print("异常啦");
        }

        return res;
    }

    @Override
    public Map<String, Object> getUserInfo(String param) {
        Map<String, Object> map = new HashMap<>();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(param);
        request.setHttpMethod("GET");
        OapiUserGetResponse response;
        try {
            response = client.execute(request,  getAccesstoken());
            map  = com.example.dingding.util.StringUtils.beanToMap(response);
        }catch (Exception e){

        }
        return map;
    }

    @Override
    public String getAccesstoken() {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(testAppId);
        request.setAppsecret(testAppSecret);
        request.setHttpMethod("GET");
        OapiGettokenResponse response;
        try{
            response = client.execute(request);
            if(response.isSuccess()){
                return response.getAccessToken();
            }
        }catch (Exception e){

        }
        return "";
    }

    @Override
    public List<Map<String, Object>> getRole() {
        List<Map<String, Object>> result = new ArrayList<>();
        String token = getAccesstoken();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/role/list");
        OapiRoleListRequest request = new OapiRoleListRequest();
        request.setOffset(0L);
        request.setSize(10L);
        OapiRoleListResponse response;
        try{
            response = client.execute(request, token);
            List<OapiRoleListResponse.OpenRoleGroup> rules = response.getResult().getList();
            result  = com.example.dingding.util.StringUtils.objectToMaps(rules);
        }catch (Exception e){

        }
        return result;
    }

    @Override
    public Map<String, Object> getUserRole(Map<String, String> param) {
        Map<String, Object> map = new HashMap<>();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/user/get_admin_scope");
        OapiUserGetAdminScopeRequest req = new OapiUserGetAdminScopeRequest();
        String token = getAccesstoken();
        req.setUserid(param.get("userId"));
        OapiUserGetAdminScopeResponse rsp;
        try{
            rsp = client.execute(req, token);
            map = com.example.dingding.util.StringUtils.beanToMap(rsp);
        }catch (Exception e){

        }
        return map;
    }

    @Override
    public List<Map<String, Object>> getDepartment(Map<String, String> param) {
        List<Map<String, Object>> result = new ArrayList<>();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        String token = getAccesstoken();
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId(param.get("id"));
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response;
        try{
            response = client.execute(request, token);
            result = com.example.dingding.util.StringUtils.objectToMaps(response.getDepartment());
        }catch (Exception e){

        }
        return result;
    }
}
