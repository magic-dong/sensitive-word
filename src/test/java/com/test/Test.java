package com.test;

import com.cnstock.support.SensitiveFilterService;

public class Test {
	private  static final int MAXIMUM_CAPACITY = 1 << 30;
	
	public static void main(String[] args) {
		SensitiveFilterService filter = SensitiveFilterService.getInstance();
		String txt = "您好，建议收到。感谢您对公司的关注。";
		System.out.println("替换前文字：【 " + txt + " 】");
		// 替换敏感字字符
		txt = filter.replaceSensitiveWord(txt, 2, "*");
		System.out.println("替换后文字：【 " + txt + " 】");

//		String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
//		Pattern p = Pattern.compile(regEx);
//		Matcher m = p.matcher(txt);
//		txt= m.replaceAll("").trim();
//		System.out.println(txt);
	}
	
	public static int test(int cap){
		int n = cap - 1;
	    n |= n >>> 1;
	    n |= n >>> 2;
	    n |= n >>> 4;
	    n |= n >>> 8;
	    n |= n >>> 16;
	    
	    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
	}
	
}
