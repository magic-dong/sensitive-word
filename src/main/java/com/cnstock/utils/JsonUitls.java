package com.cnstock.utils;

import java.util.List;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

/**
 * Json数据处理
 * @author lzd
 * @date 2019年4月4日
 * @version
 */
public class JsonUitls {
	
	/**
	 * 对象转Json字符串
	 * @author lzd
	 * @date 2019年4月4日:下午5:15:30
	 * @param obj 对象
	 * @return
	 */
	public static String  ObjectToJson(Object obj){
		if(obj!=null){
			try {
				Gson gson=new Gson();
				String json=gson.toJson(obj);
				return json;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Json字符串转对象
	 * @author lzd
	 * @date 2019年4月4日:下午5:15:30
	 * @param json 对象字符串
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object JsonToObject(String json,Class classzz){
		if(json !=null && !"".equals(json)){
			
			try {
				Gson gson=new Gson();
				return gson.fromJson(json, classzz);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	/**
	 * list集合转Json字符串
	 * @author lzd
	 * @date 2019年4月4日:下午5:28:16
	 * @param list
	 * @return
	 */
	public static String ListToJson(List<?> list){
		return ObjectToJson(list);
	}
	
	/**
	 * Json字符串转list集合
	 * @author lzd
	 * @date 2019年4月4日:下午5:28:16
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "serial" })
	public static List<?> JsonToList(String json){
		if(json !=null && !"".equals(json)){
			try {
				Gson gson=new Gson();
				return gson.fromJson(json, new TypeToken<List<?>>() {}.getType());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
}
