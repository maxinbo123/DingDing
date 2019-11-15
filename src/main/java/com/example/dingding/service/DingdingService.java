package com.example.dingding.service;

import java.util.List;
import java.util.Map;

/**
 * Created by maxb on 2019/11/14.
 */
public interface DingdingService {

    /**
     * 根据userid获取用户信息（userid）
     * @param param
     * @return
     */
    Map<String,Object> getUserInfoByLogin(Map<String,String> param);



    /**
     * 根据token获取钉钉唯一标识（unionid）
     * @param param
     * @return
     */
    String getUnionid(Map<String,String> param);

    /**
     * 根据unionid获取钉钉用户uid（userid）
     * @param param
     * @return
     */
    String getUserId(String param);

    /**
     * 根据userid获取用户信息（userid）
     * @param param
     * @return
     */
    Map<String,Object> getUserInfo(String param);

    /**
     * 根据角色列表
     * @return
     */
    List<Map<String,Object>> getRole();

    /**
     * 根据员工管理权限
     * @return
     */
    Map<String,Object> getUserRole(Map<String,String> param);


    /**
     * 根据部门列表
     * @return
     */
    List<Map<String,Object>> getDepartment(Map<String,String> param);


    /**
     * 获取token
     * @return
     */

    String getAccesstoken();
}
