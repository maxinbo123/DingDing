package com.example.dingding.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cglib.beans.BeanMap;

import java.util.*;

/**
 * Created by maxb on 2019/11/14.
 */
public class StringUtils {

    /**
     *
     * 判断str是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "null".equals(str) || "NULL".equals(str)) {
            return true;
        }
        return false;
    }
    /**
     * 将String 转化为map   单个
     * @param param
     * @param resMap
     */
    public static void stringToMap(String param,Map<String,Object> resMap){
        if(isEmpty(param)){
            return;
        }
        JSONObject jsonObject = JSON.parseObject(param);
        Set<String> stringSet = jsonObject.keySet();
        for(String key : stringSet){
            Object value = jsonObject.get(key);
            resMap.put(key,value);
        }
    }
    /**
     * 将String 转化为map   集合
     * @param jsonStr
     * @param res
     */
    public static void jsonStr2List(String jsonStr,List<Map<String,Object>> res){
        JSONArray ja = JSON.parseArray(jsonStr);
        for (int j = 0; j < ja.size(); j++){
            String jm = ja.getString(j);
            if(jm.indexOf("{") == 0){
                Map map = new HashMap();
                stringToMap(jm,map);
                res.add(map);
            }
        }
    }

    /**
     * bean转化为map
     * @param bean
     * @param <T>
     * @return
     */
  public static <T> Map<String,Object> beanToMap(T bean){
        Map<String,Object> map = new HashMap<>();
        if(bean != null){
            BeanMap beanMap = BeanMap.create(bean);
            for(Object key : beanMap.keySet()){
                map.put(key+"",beanMap.get(key));
            }
        }
        return map;
  }

    /**
     * map转化为map
     * @param map
     * @param bean
     * @param <T>
     * @return
     */
  public static <T>T mapToBean(Map<String,Object> map, T bean){
      BeanMap beanMap = BeanMap.create(bean);
      beanMap.putAll(map);
      return bean;
  }

    /**
     * maps转化为list
     * @param maps
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
  public static <T> List<T> mapsToObjs(List<Map<String,Object>> maps,Class<T> clazz)throws Exception{
      List<T> list = new ArrayList<>();
      if(maps != null && maps.size()> 0){
          Map<String,Object> map = null;
          T bean = null;
          for(int i=0, size = maps.size(); i<size;i++){
              map = maps.get(i);
              bean = clazz.newInstance();
              mapToBean(map,bean);
              list.add(bean);
          }
      }
      return list;
  }

  public static <T> List<Map<String,Object>> objectToMaps(List<T> objList){
      List<Map<String,Object>> list = new ArrayList<>();
      if(objList != null && objList.size() > 0){
          Map<String,Object> map = null;
          T bean = null;
          for(int i=0,size = objList.size();i<size;i++){
              bean = objList.get(i);
              map = beanToMap(bean);
              list.add(map);
          }
      }
      return list;
  }


}
