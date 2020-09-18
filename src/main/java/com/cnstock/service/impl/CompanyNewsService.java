package com.cnstock.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cnstock.dao.CompanyNewsDao;
import com.cnstock.entity.CompanyNews;
import com.cnstock.service.ICompanyNewsService;
import com.cnstock.support.SensitiveFilterService;
import com.cnstock.utils.DateUtils;
import com.cnstock.utils.HttpClientUtils;
import com.cnstock.utils.StringUtils;

@Service
public class CompanyNewsService implements ICompanyNewsService {
	private final static Logger logger=LoggerFactory.getLogger(CompanyNewsService.class);

	private static String  HDY_HTTP_URL="http://esquery.api.cnstock.com/index.php/news/index?type=hdy";
	
	//敏感词过滤类
	private final static SensitiveFilterService SENSITIVEFILTER=SensitiveFilterService.getInstance();
	
	//常见符号
	private final static String REGEX="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
	
	//过滤剔除后最少长度
	private final static int FILTER_MIN_LENGTH=20;
	
	@Autowired
	private CompanyNewsDao companyNewsDao;
	
	/**
	 * 调用互动易接口分页获取数据并入库更新数据
	 * @author lzd
	 * @date 2020年6月23日:下午4:11:02
	 * @param params
	 * @return
	 * @description
	 */
	@Override
	public boolean saveHdyDataByPage(Map<String, Object> params) {
		// TODO Auto-generated method stub
		//当前页数
		String pageStr=String.valueOf(params.get("page"));
		Integer page=Integer.valueOf(pageStr);
		//最大显示行数
		String sizeStr=String.valueOf(params.get("size"));
		Integer size=Integer.valueOf(sizeStr);
		//调用互动易接口
		String httpUrl = HDY_HTTP_URL+"&page="+page+"&pagesize="+size;
		//Json数据转List<Map<String,Object>>集合
		List<Map<String, Object>> hdyList = jsonConvertList(httpUrl);
		if (hdyList != null && !hdyList.isEmpty()) {
			for (Map<String, Object> hdyMap : hdyList) {
				try {
					//主键ID
					String id=String.valueOf(hdyMap.get("id"));
					//判断是否数据异常
					if(StringUtils.isNull(id)) continue;
					
					//股票代码
					String secucode=String.valueOf(hdyMap.get("secucode"));
					secucode=StringUtils.isNotNull(secucode)?secucode:null;
					//证券简称
					String secuabbr=String.valueOf(hdyMap.get("secuabbr"));
					secuabbr=StringUtils.isNotNull(secuabbr)?secuabbr:null;
					//类型
					String type=String.valueOf(hdyMap.get("type"));
					type=StringUtils.isNotNull(type)?type:null;
					//问题
					String question=String.valueOf(hdyMap.get("question"));
					question=StringUtils.isNotNull(question)?question:null;
					//答案
					String answer=String.valueOf(hdyMap.get("answer"));
					answer=StringUtils.isNotNull(answer)?answer:null;
					//来源
					String source=String.valueOf(hdyMap.get("source"));
					source=StringUtils.isNotNull(source)?source:null;
					//发布时间
					String publishTime=String.valueOf(hdyMap.get("publishtime"));
					publishTime=StringUtils.isNotNull(publishTime)?publishTime:null;
					//提问时间
					String qtime=String.valueOf(hdyMap.get("qtime"));
					Date qTime=StringUtils.isNotNull(qtime)?DateUtils.parseDate(qtime):null;
					//回答时间
					String atime=String.valueOf(hdyMap.get("atime"));
					Date aTime=StringUtils.isNotNull(atime)?DateUtils.parseDate(atime):null;
				
					//查询是否已存在
					CompanyNews companyNews = companyNewsDao.selectById(id);
					String msg="互动易数据";
					if(companyNews==null){//新增
						companyNews=new CompanyNews(id, secucode, secuabbr, type, 
								question, answer, source, 
								publishTime, qTime, aTime);
						//添加
						int save = companyNewsDao.save(companyNews);
						msg+=save>0?"添加成功！":"添加失败！";
						logger.info(msg);
					}else{//更新
						companyNews.setSecucode(secucode);
						companyNews.setSecuabbr(secuabbr);
						companyNews.setType(type);
						companyNews.setQuestion(question);
						companyNews.setAnswer(answer);
						companyNews.setSource(source);
						companyNews.setPublishTime(publishTime);
						companyNews.setQTime(qTime);
						companyNews.setATime(aTime);
						//修改
						int save = companyNewsDao.updateById(companyNews);
						msg+=save>0?"更新成功！":"更新失败！";
						logger.info(msg);
					}
				} catch (Exception e) {
					// TODO: handle exception
					logger.error(e.getMessage(),e);
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Json数据转List<Map<String,Object>>集合
	 * @author lzd
	 * @date 2020年6月23日:下午3:20:35
	 * @param httpUrl
	 * @return
	 * @description
	 */
	protected List<Map<String, Object>> jsonConvertList(String httpUrl) {
		List<Map<String, Object>> dataMapList = null;
		if (StringUtils.isNotNull(httpUrl)) {
			JSONObject parseObject = JSONObject.parseObject(HttpClientUtils.doGet(httpUrl, "UTF-8"));
			if (parseObject != null && "success".equals(parseObject.get("msg"))) {
				// 获取目标数据
				String data = String.valueOf(parseObject.get("item"));
				// 判断是否有有效数据
				if (StringUtils.isNotNull(data) && data.startsWith("[")) {
					JSONArray parseArray = JSONArray.parseArray(data);
					if (parseArray != null && parseArray.size() > 0) {
						dataMapList = new ArrayList<>(parseArray.size());
						for (int i = 0; i < parseArray.size(); i++) {
							Map<String, Object> resultMap = JSON.parseObject(parseArray.get(i).toString(),
									new TypeReference<Map<String, Object>>() {
									});
							dataMapList.add(resultMap);
						}
					}
				}
			}
		}
		return dataMapList;
	}

	/**
	 * 分页查询互动易数据
	 *  注：当isFilter=true则显示过滤掉敏感词和屏蔽词后的内容
	 * @author lzd
	 * @date 2020年6月23日:下午4:56:09
	 * @param params
	 * @return
	 * @description
	 */
	@Override
	public List<CompanyNews> selectListBy(Map<String, Object> params) {
		// TODO Auto-generated method stub
		//当前页数
		String pageStr=String.valueOf(params.get("page"));
		Integer page=Integer.valueOf(pageStr);
		//最大显示行数
		String sizeStr=String.valueOf(params.get("size"));
		Integer size=Integer.valueOf(sizeStr);
		//是否过滤
		String isFilterStr=String.valueOf(params.get("isFilter"));
		boolean isFilter=Boolean.valueOf(isFilterStr);
		
		List<CompanyNews> newsList = companyNewsDao.selectBy(params, (page-1)*size, size);
		if(newsList!=null && !newsList.isEmpty()){
			for (CompanyNews companyNews : newsList) {
				try {
					//答案
					String answer = companyNews.getAnswer();
					if(isFilter){
						//替换敏感字字符
						answer=SENSITIVEFILTER.replaceSensitiveWord(answer, 2, "*");
						companyNews.setAnswer(answer);
					}
				} catch (Exception e) {
					// TODO: handle exception
					logger.error(e.getMessage(),e);
				}
			}
		}
		return newsList;
	}

	/**
	 * 条件分页查询过滤屏蔽词后的互动易数据
	 * @author lzd
	 * @date 2020年6月24日:下午12:06:32
	 * @param params
	 * @return
	 * @description
	 */
	@Override
	public List<Map<String, Object>> selectFilterListBy(Map<String, Object> params) {
		// TODO Auto-generated method stub
		//当前页数
		String pageStr=String.valueOf(params.get("page"));
		Integer page=Integer.valueOf(pageStr);
		//最大显示行数
		String sizeStr=String.valueOf(params.get("size"));
		Integer size=Integer.valueOf(sizeStr);	
		//查询数据集合
		List<CompanyNews> newsList = companyNewsDao.selectBy(params, (page-1)*size, size);
		List<Map<String, Object>> filterList=null;
		if(newsList!=null && !newsList.isEmpty()){
			filterList=new ArrayList<>(newsList.size());
			for (CompanyNews companyNews : newsList) {
				try {
					Map<String, Object> newsMap=new LinkedHashMap<String, Object>();
					newsMap.put("id", companyNews.getId());
					newsMap.put("secucode", companyNews.getSecucode());
					newsMap.put("secuabbr", companyNews.getSecuabbr());
					newsMap.put("question", companyNews.getQuestion());
					//答案
					String answer = companyNews.getAnswer();
					newsMap.put("answer", answer);
					//替换敏感字字符
					String filterAnswer=SENSITIVEFILTER.replaceSensitiveWord(answer, 2, "*");
					newsMap.put("filterAnswer", filterAnswer);
					Pattern p = Pattern.compile(REGEX);
					Matcher m = p.matcher(filterAnswer);
					filterAnswer= m.replaceAll("").trim();
					newsMap.put("isSatisfied", filterAnswer.length()>FILTER_MIN_LENGTH?false:true);
					newsMap.put("type", companyNews.getType());
					newsMap.put("source", companyNews.getSource());
					newsMap.put("publishTime", companyNews.getPublishTime());
					newsMap.put("qTime", companyNews.getQTime());
					newsMap.put("aTime", companyNews.getATime());
					filterList.add(newsMap);
					
				} catch (Exception e) {
					// TODO: handle exception
					logger.error(e.getMessage(),e);
				}
			}
		}
		return filterList;
	}

	
	/**
	 * 条件查询互动易数据总条数
	 * @author lzd
	 * @date 2020年6月23日:下午5:37:08
	 * @param params
	 * @return
	 * @description
	 */
	@Override
	public int selectCountBy(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return companyNewsDao.selectCountBy(params);
	}

}
