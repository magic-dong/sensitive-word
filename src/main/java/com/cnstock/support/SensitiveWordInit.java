package com.cnstock.support;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 屏蔽敏感词初始化
 * 
 * @author lzd
 * @date 2020年6月22日
 * @version
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class SensitiveWordInit {
	// 字符编码
	private final static String ENCODING = "UTF-8";
	
	//敏感屏蔽词文件所在路径
	private final static String CENSOR_WORD_URL="config/censorwords.txt";

	/**
	 * 初始化敏感字库
	 * @author lzd
	 * @date 2020年6月22日:上午10:55:23
	 * @return
	 * @description
	 */
	public Map initKeyWord() {
		// 读取敏感词库 ,存入Set中
		Set<String> wordSet = readSensitiveWordFile();
		// 将敏感词库加入到HashMap中//确定有穷自动机DFA
		return addSensitiveWordToHashMap(wordSet);
	}

	

    /**   
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br> 
     * 中 = { 
     *      isEnd = 0 
     *      国 = { 
     *           isEnd = 1 
     *           人 = {isEnd = 0 
     *                民 = {isEnd = 1} 
     *                } 
     *           男  = { 
     *                  isEnd = 0 
     *                   人 = { 
     *                        isEnd = 1 
     *                       } 
     *               } 
     *           } 
     *      } 
     * 五 = { 
     *      isEnd = 0 
     *      星 = { 
     *          isEnd = 0 
     *          红 = { 
     *              isEnd = 0 
     *              旗 = { 
     *                   isEnd = 1 
     *                  } 
     *              } 
     *          } 
     *      } 
     * 
 	 */

	
	/**
	 * 读取敏感词库 ,存入HashMap中 
	 * @author lzd
	 * @date 2020年6月22日:上午10:55:29
	 * @return
	 * @description
	 */
	private Set<String> readSensitiveWordFile() {

		Set<String> wordSet = null;
		try {
//			//方式一：
//			URL resource = SensitiveWordInit.class.getClassLoader().getResource(CENSOR_WORD_URL);
//			if(resource==null) return wordSet;
//			// 敏感词库
//			File file = new File(resource.getPath());
//			// 读取文件输入流
//			InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING);
//			// 文件是否是文件 和 是否存在
//			if (file.isFile() && file.exists()) {
//
//				wordSet = new HashSet<String>();
//				// StringBuffer sb = new StringBuffer();
//				// BufferedReader是包装类，先把字符读到缓存里，到缓存满了，再读入内存，提高了读的效率。
//				BufferedReader br = new BufferedReader(read);
//				String txt = null;
//				String regex = "\\s+|\t|\r|\n";//正则表达式,匹配任何空白字符，包括空格、制表符、换行符等
//				Pattern patt = Pattern.compile(regex);	//创建Pattern对象，处理正则表达式
//				// 读取文件，将文件内容放入到set中
//				while ((txt = br.readLine()) != null) {
//					Matcher matcher = patt.matcher(txt);	//先处理每一行的空白字符
//					txt=matcher.replaceAll("");
//					//UTF8+BOM文件格式存储的txt文件读取的第一个字符的ASCII码为65279,会出现1个长度的占用字符，但看不见
//					txt=txt.replaceAll(""+(char)65279,"");
//					if(txt!=null && !"".equals(txt) && !"".equals(txt.trim())){
//						wordSet.add(txt);
//					}
//				}
//
//				br.close();
//			}
//
//			// 关闭文件流
//			read.close();
			
			//方式二：
			//读取文件输入流
			InputStream in = SensitiveWordInit.class.getClassLoader().getResourceAsStream(CENSOR_WORD_URL);
			if(in!=null){
				InputStreamReader read = new InputStreamReader(in, ENCODING);
				wordSet = new HashSet<String>();
				// StringBuffer sb = new StringBuffer();
				// BufferedReader是包装类，先把字符读到缓存里，到缓存满了，再读入内存，提高了读的效率。
				BufferedReader br = new BufferedReader(read);
				String txt = null;
				String regex = "\\s+|\t|\r|\n";//正则表达式,匹配任何空白字符，包括空格、制表符、换行符等
				Pattern patt = Pattern.compile(regex);	//创建Pattern对象，处理正则表达式
				// 读取文件，将文件内容放入到set中
				while ((txt = br.readLine()) != null) {
					Matcher matcher = patt.matcher(txt);	//先处理每一行的空白字符
					txt=matcher.replaceAll("");
					//UTF8+BOM文件格式存储的txt文件读取的第一个字符的ASCII码为65279,会出现1个长度的占用字符，但看不见
					txt=txt.replaceAll(""+(char)65279,"");
					if(txt!=null && !"".equals(txt) && !"".equals(txt.trim())){
						wordSet.add(txt);
					}
				}

				br.close();
				// 关闭文件流
				read.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wordSet;
	}
	
	/**
	 * 将HashSet中的敏感词,存入HashMap中
	 * @author lzd
	 * @date 2020年6月22日:上午10:58:46
	 * @param wordSet
	 * @return
	 * @description
	 */
	private Map addSensitiveWordToHashMap(Set<String> wordSet) {

		// 初始化敏感词容器，减少扩容操作
		Map wordMap = new HashMap(wordSet.size());

		for (String word : wordSet) {
			Map nowMap = wordMap;
			for (int i = 0; i < word.length(); i++) {
				// 转换成char型
				char keyChar = word.charAt(i);
				// 获取
				Object tempMap = nowMap.get(keyChar);
				// 如果存在该key，直接赋值
				if (tempMap != null) {
					nowMap = (Map) tempMap;
				}
				// 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
				else {
					// 设置标志位
					Map<String, String> newMap = new HashMap<String, String>();
					newMap.put("isEnd", "0");
					// 添加到集合
					nowMap.put(keyChar, newMap);
					nowMap = newMap;
				}
				// 最后一个
				if (i == word.length() - 1) {
					nowMap.put("isEnd", "1");
				}
			}
		}
		return wordMap;
	}
	
}
