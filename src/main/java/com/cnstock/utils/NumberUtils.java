package com.cnstock.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 与BigDecimal计算相关的类
 * @author lzd
 * @date 2019年4月3日
 * @version
 */
public class NumberUtils {

	/**
	 * 万
	 */
	public final static String  WAN="10000";
	
	/**
	 * 亿
	 */
	public final static String  YI="100000000";
	
    /**
     * 将Object类型转化为BigDecimal
     *
     * @param obj 传入参数
     * @return 返回值
     */
    public static BigDecimal objConvertBigDecimal(Object obj) {
        if (obj == null || obj.toString().equals("null")){
            return null;
        }
        return new BigDecimal(obj.toString());
    }

    /**
     * 保留两位小数返回百分数
     *
     * @param obj 传入参数
     * @return 返回值
     */
    public static String objConvertPercent(Object obj) {
        if (isNull(obj)) {
            return "";
        }
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMinimumFractionDigits(2);
        percent.setGroupingUsed(false);
        return percent.format(objConvertBigDecimal(obj).doubleValue());
    }

    /**
     * 两个参数相除
     * @param obj1
     * @param obj2
     * @return (obj1/obj2)
     */
    public static BigDecimal divide(Object obj1, Object obj2) {
        if (isNull(obj1, obj2)) {
            return null;
        }
        if("0".equals(obj2.toString().trim()) || "0.00".equals(obj2.toString().trim())){
          	 return null;
        }
        BigDecimal b1 = new BigDecimal(obj1.toString());
        BigDecimal b2 = new BigDecimal(obj2.toString());
        return b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * 两个参数相除后返回百分数
     * @param obj1
     * @param obj2
     * @return (obj1/obj2)*100%
     */
    public static String divideAndPercent(Object obj1, Object obj2) {
    	BigDecimal divideResult = divide(obj1, obj2);
    	if(divideResult==null){
    		return null;
    	}
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMinimumFractionDigits(2);
        percent.setGroupingUsed(false);
        return percent.format(divideResult.doubleValue());
    }
    
    /**
     * 数值转万元
     * @author lzd
     * @date 2019年4月30日:上午11:04:40
     * @param obj1
     * @return
     */
    public static BigDecimal moneyConvertWan(Object obj1) {
        if (isNull(obj1)) {
            return null;
        }
        BigDecimal b1 = new BigDecimal(obj1.toString());
        BigDecimal b2 = new BigDecimal(NumberUtils.WAN);
        return divide(b1,b2);
    }
    
    /**
     * 数值转亿元
     * @author lzd
     * @date 2019年4月30日:上午11:04:40
     * @param obj1
     * @return
     */
    public static BigDecimal moneyConvertYi(Object obj1) {
        if (isNull(obj1)) {
            return null;
        }
        BigDecimal b1 = new BigDecimal(obj1.toString());
        BigDecimal b2 = new BigDecimal(NumberUtils.YI);
        return divide(b1,b2);
    }

    /**
     * 数值转换
     * @author lzd
     * @date 2019年7月11日:上午11:27:57
     * @param obj1 
     * @param target 万：10000，亿：100000000
     * @return
     * @description
     */
    public static String moneyConvert(Object obj1,Object target) {
        if (isNull(obj1,target)) {
            return "";
        }
        BigDecimal b1 = new BigDecimal(obj1.toString());
        BigDecimal b2 = new BigDecimal(target.toString());
        BigDecimal divide = divide(b1,b2);
        if(divide !=null){
        	return divide.toString();
        }
        return "";
    }
   
    
    /**
     * 前两个参数想减后再相除返回相对增长百分数
     * @param obj1
     * @param obj2
     * @return (obj1-obj2)/obj2*100%
     */
    public static String subtractAndDividePercent(Object obj1, Object obj2) {
        if (isNull(obj1, obj2)) {
            return "";
        }
        if("0".equals(obj2.toString().trim()) || "0.00".equals(obj2.toString().trim())){
       	    return "";
        }
        BigDecimal b1 = new BigDecimal(obj1.toString().replaceAll("%", ""));
        BigDecimal b2 = new BigDecimal(obj2.toString().replaceAll("%", ""));
        BigDecimal b3 = (b1.subtract(b2)).divide(b2, 4, BigDecimal.ROUND_HALF_UP);
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMinimumFractionDigits(2);
        percent.setGroupingUsed(false);
        return percent.format(b3.doubleValue());
    }
    
    /**
     * 前两个参数想减后再相除第二个参数返回相对同比百分数
     * @author lzd
     * @date 2020年3月27日:上午10:23:01
     * @param obj1
     * @param obj2
     * @return (obj1-obj2)/|obj2|*100%
     * @description
     */
    public static String subtractAndDivideAbsPercent(Object obj1, Object obj2){
    	if (isNull(obj1, obj2)) {
            return "";
        }
    	if("0".equals(obj2.toString().trim()) || "0.00".equals(obj2.toString().trim())){
       	    return "";
        }
    	BigDecimal b1 = new BigDecimal(obj1.toString().replaceAll("%", ""));
        BigDecimal b2 = new BigDecimal(obj2.toString().replaceAll("%", ""));
        BigDecimal b3= new BigDecimal(Math.abs(b2.doubleValue())+"");
        BigDecimal b4 = (b1.subtract(b2)).divide(b3, 4, BigDecimal.ROUND_HALF_UP);
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMinimumFractionDigits(2);
        percent.setGroupingUsed(false);
        return percent.format(b4.doubleValue());   
    }
    
    /**
     * 百分比的绝对值
     * @author lzd
     * @date 2019年4月29日:下午5:02:24
     * @param obj1
     * @return
     */
    public static String absAndPercent(String obj1){
    	 if (isNull(obj1)) {
             return "";
         }
    	 BigDecimal b1 = new BigDecimal(obj1.replaceAll("%", ""));
    	 BigDecimal b2 = new BigDecimal("100");
    	 NumberFormat percent = NumberFormat.getPercentInstance();
         percent.setMinimumFractionDigits(2);
         percent.setGroupingUsed(false);
         return percent.format(b1.abs().divide(b2, 4, BigDecimal.ROUND_HALF_UP).doubleValue());
    }
    
    /**
     * 普通数字的绝对值
     * @author lzd
     * @date 2019年6月10日:上午11:18:16
     * @param obj
     * @return
     * @description
     */
    public static String abs(Double obj){
    	return abs(obj, 2);
    }
    
    /**
     * 普通数字的绝对值保留length位小数
     * @author lzd
     * @date 2020年1月14日:下午5:55:04
     * @param obj
     * @param length
     * @return
     * @description
     */
    public static String abs(Double obj,Integer length){
    	if (isNull(obj)) {
             return "";
        }
    	String obj1=String.valueOf(obj);
    	BigDecimal b1 = new BigDecimal(obj1);
    	String doubleLength=(length!=null && length>0)?("%."+length+"f"):"%s";
    	String abs=String.format(doubleLength, b1.abs().doubleValue());
        return abs;
    }
    
    public static String abs(Object obj){
    	return abs(obj, null);
    }
    
    public static String abs(Object obj,Integer length){
    	if (isNull(obj)) {
             return "";
        }
    	String obj1=obj.toString().replaceAll("%", "");
    	BigDecimal b1 = new BigDecimal(obj1);
    	String doubleLength=(length!=null && length>0)?("%."+length+"f"):"%s";
    	String abs=String.format(doubleLength, b1.abs().doubleValue());
        return abs;
    }
    
    /**
     * 两个百分比相减返回百分比
     * @param obj1 param1
     * @param obj2 param2
     * @return (param1-param2)%
     */
    public static String subtractAndPercent(String obj1, String obj2) {
        if (isNull(obj1, obj2)) {
            return "";
        }
        BigDecimal b1 = new BigDecimal(obj1.replaceAll("%", ""));
        BigDecimal b2 = new BigDecimal(obj2.replaceAll("%", ""));
        BigDecimal b3=new BigDecimal("100");
        BigDecimal b4 = b1.subtract(b2).divide(b3,4,BigDecimal.ROUND_HALF_UP);
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMinimumFractionDigits(2);
        percent.setGroupingUsed(false);
        return percent.format(b4.doubleValue());
    }
    
    /**
     * 两个BigDecimal相减返回百分比
     * @param obj1 param1
     * @param obj2 param2
     * @return (param1-param2)%
     */
    public static String subtractAndPercent(BigDecimal obj1, BigDecimal obj2) {
        if (isNull(obj1, obj2)) {
            return "";
        }
        BigDecimal b3=new BigDecimal("100");
        BigDecimal b4 = obj1.subtract(obj2).divide(b3,4,BigDecimal.ROUND_HALF_UP);
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMinimumFractionDigits(2);
        percent.setGroupingUsed(false);
        return percent.format(b4.doubleValue());
    }
    /**
     * 前两个参数想减后再相除返回百分比
     *
     * @param obj1 param1
     * @param obj2 param2
     * @param obj3 param3
     * @return (param1-param2)/param3*100%
     */
    public static String subtractAndDividePercent(Object obj1, Object obj2, Object obj3) {
        if (isNull(obj1, obj2, obj3)) {
            return "";
        }
        if("0".equals(obj3.toString().trim()) || "0.00".equals(obj3.toString().trim())){
       	 return null;
        }
        BigDecimal b1 = new BigDecimal(obj1.toString());
        BigDecimal b2 = new BigDecimal(obj2.toString());
        BigDecimal b3 = new BigDecimal(obj3.toString());
        BigDecimal b4 = (b1.subtract(b2)).divide(b3, 4, BigDecimal.ROUND_HALF_UP);
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMinimumFractionDigits(2);
        percent.setGroupingUsed(false);
        return percent.format(b4.doubleValue());
    }

    /**
     * 前两个参数想减后再相除
     *
     * @param obj1 param1
     * @param obj2 param2
     * @param obj3 param3
     * @return (param1-param2)/param3
     */
    public static BigDecimal subtractAndDivide(Object obj1, Object obj2, Object obj3) {
        if (isNull(obj1, obj2, obj3)) {
            return null;
        }
        if("0".equals(obj3.toString().trim()) || "0.00".equals(obj3.toString().trim())){
        	 return null;
        }
        BigDecimal b1 = new BigDecimal(obj1.toString());
        BigDecimal b2 = new BigDecimal(obj2.toString());
        BigDecimal b3 = new BigDecimal(obj3.toString());
        return (b1.subtract(b2)).divide(b3, 4, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 从一组数据中找出最大的前3个数据
     *
     * @param map 参数集合
     * @return 返回前3个数据量最大的值secuName和值一一对应
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String> queryBig(Map<String, Object> map) {
        List dataList = new ArrayList();
        String secuName_1 = "";
        String secuName_2 = "";
        String secuName_3 = "";
        BigDecimal b1 = new BigDecimal(-1);
        BigDecimal b2 = new BigDecimal(-1);
        BigDecimal b3 = new BigDecimal(-1);
        for (String secuName : map.keySet()) {
            Object temper = map.get(secuName);
            if (temper != null) {
                //大于第一个数字
                if (new BigDecimal(temper.toString()).compareTo(b1) == 1) {
                    if (!StringUtils.isEmpty(secuName_3)) {
                        secuName_3 = secuName_2;
                        b3 = b2;
                    }
                    if (!StringUtils.isEmpty(secuName_2)) {
                        secuName_2 = secuName_1;
                        b2 = b1;
                    }
                    secuName_1 = secuName;
                    b1 = new BigDecimal(temper.toString());
                }
                //等于第一个数字
                else if (new BigDecimal(temper.toString()).compareTo(b1) == 0) {
                    if (!StringUtils.isEmpty(secuName_3)) {
                        secuName_3 = secuName_2;
                        b3 = b2;
                    }
                    secuName_2 = secuName;
                    b2 = new BigDecimal(temper.toString());
                }
                //小于第一个数字，就和第二个数字做比较
                //大于或者等于第二个数字
                else if (new BigDecimal(temper.toString()).compareTo(b2) >= 0) {
                    if (!StringUtils.isEmpty(secuName_3)) {
                        secuName_3 = secuName_2;
                        b3 = b2;
                    }
                    secuName_2 = secuName;
                    b2 = new BigDecimal(temper.toString());
                }
                //小于第二个数字，就和第三个数字做比较
                //大于第三个数字
                else if (new BigDecimal(temper.toString()).compareTo(b3) == 1) {
                    secuName_3 = secuName;
                    b3 = new BigDecimal(temper.toString());
                }
            }
        }
        if (!StringUtils.isEmpty(secuName_1) && b1.compareTo(BigDecimal.ZERO) == 1) {
            dataList.add(secuName_1);
            dataList.add(b1);
        }
        if (!StringUtils.isEmpty(secuName_2) && b2.compareTo(BigDecimal.ZERO) == 1) {
            dataList.add(secuName_2);
            dataList.add(b2);
        }
        if (!StringUtils.isEmpty(secuName_3) && b3.compareTo(BigDecimal.ZERO) == 1) {
            dataList.add(secuName_3);
            dataList.add(b3);
        }
        return dataList;
    }

    /**
     * 判断是否是null
     *
     * @param obj 传入参数
     * @return true:为null或者空或者0
     */
    public static boolean isNull(Object... obj) {
        boolean flag = false;
        for (Object o : obj) {
            if (o == null || "".equals(o.toString()) || "null".equals(o.toString())) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    
    /**
     * 两个string 类型的数值 相乘
     *
     * @param value1
     * @param val2
     * @return
     */
    public static String multiply(String val1, String val2) {
    	if(isNull(val1,val2)){
    		return null;
    	}
        double value = new BigDecimal(val1).multiply(new BigDecimal(val2)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        return value + "";
    }
    
    /**
     * 前两个string 类型的数值 相乘除以后一个参数
     *  val1*val2/val3
     * @param val1
     * @param val2
     * @return
     */
    public static Double multiplyAndDivide(String val1, String val2,String val3) {
    	Double result=null;
    	if(isNull(val1,val2,val3)){
    		return null;
    	}
    	try {
    		BigDecimal mutiply = new BigDecimal(val1).multiply(new BigDecimal(val2)).setScale(4, BigDecimal.ROUND_HALF_UP);
    		double value = mutiply.divide(new BigDecimal(val3),4,BigDecimal.ROUND_HALF_UP).doubleValue();
    		result=value;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return result;
    }
    
    
    /**
     * 两个字符串数字相减
     * @author lzd
     * @date 2019年5月9日:下午5:22:15
     * @param obj1
     * @param obj2
     * @return
     * @description
     */
    public static BigDecimal subtract(String obj1, String obj2){
        if (isNull(obj1, obj2)) {
            return null;
        }
        BigDecimal b1 = new BigDecimal(obj1.replaceAll("%", ""));
        BigDecimal b2 = new BigDecimal(obj2.replaceAll("%", ""));
        return b1.subtract(b2);
    }
    
    /**
     * 两个BigDecimal数字相减
     * @author lzd
     * @date 2019年5月9日:下午5:22:15
     * @param obj1
     * @param obj2
     * @return
     * @description
     */
    public static BigDecimal subtract(BigDecimal obj1, BigDecimal obj2){
        if (isNull(obj1, obj2)) {
            return null;
        }
        return obj1.subtract(obj2);
    }
    
    /**
     * 计算一组非空的数据的平均值（保留4位小数）
     * @author lzd
     * @date 2020年1月14日:下午5:16:34
     * @param args
     * @return
     * @description
     */
    public static BigDecimal avgArray(Object...args){
    	if (isNull(args)) {
	        return null;
	    }
    	BigDecimal sum=new BigDecimal("0");
    	BigDecimal num=new BigDecimal(args.length+"");
    	for (int i = 0; i < args.length; i++) {
    		BigDecimal obj=new BigDecimal(args[i].toString().replaceAll("%", ""));
    		sum=sum.add(obj);
		}
    	return sum.divide(num,2,BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * 计算一组非空的数据的平均值（保留4位小数）
     * @author lzd
     * @date 2020年1月14日:下午5:16:34
     * @param args
     * @return
     * @description
     */
    public static String avgArrayToStr(Object...args){
    	BigDecimal avgArray = avgArray(args);
    	if(avgArray==null){
    		return null;
    	}
    	return avgArray.toString();
    }
    
    /**
     * 保留几位小数
     * @author lzd
     * @date 2020年1月16日:下午5:12:54
     * @param obj
     * @param length
     * @return
     * @description
     */
    public static String stayLengthDouble(Object obj,int length){
    	if (isNull(obj)) {
	        return null;
	    }
    	BigDecimal number=new BigDecimal(obj.toString().replaceAll("%", ""));
    	number = number.setScale(length,BigDecimal.ROUND_HALF_UP);
//    	String doubleLength="%."+length+"f";
//    	String numStr=String.format(doubleLength, number.doubleValue());
    	String numStr=number.doubleValue()+"";
    	return numStr;
    }
    
    /**
     * 数字超过11位处理Double不使用科学计数法显示问题
     * @author lzd
     * @date 2020年4月16日:上午10:19:11
     * @param number
     * @return
     * @description
     */
    public static String doubleFormat(Double number){
    	if(number==null){
    		return null;
    	}
    	DecimalFormat decimalFormat = new DecimalFormat("#0.0000");
		String numFormat = decimalFormat.format(number);
		return numFormat;
    }
}
