package com.cnstock.service;

import java.util.List;
import java.util.Map;

import com.cnstock.entity.CompanyNews;

public interface ICompanyNewsService {
	
	/**
	 * 调用互动易接口分页获取数据并入库更新数据
	 * @author lzd
	 * @date 2020年6月23日:下午4:11:02
	 * @param params
	 * @return
	 * @description
	 */
	boolean saveHdyDataByPage(Map<String, Object> params);
	
	/**
	 * 条件分页查询互动易数据
	 *  注：当isFilter=true则显示过滤掉敏感词和屏蔽词后的内容
	 * @author lzd
	 * @date 2020年6月23日:下午4:56:09
	 * @param params
	 * @return
	 * @description
	 */
	List<CompanyNews> selectListBy(Map<String, Object> params);
	
	/**
	 * 条件分页查询过滤掉屏蔽词后的互动易数据
	 * @author lzd
	 * @date 2020年6月24日:下午12:06:32
	 * @param params
	 * @return
	 * @description
	 */
	List<Map<String, Object>> selectFilterListBy(Map<String, Object> params);
	
	/**
	 * 条件查询互动易数据总条数
	 * @author lzd
	 * @date 2020年6月23日:下午5:37:08
	 * @param params
	 * @return
	 * @description
	 */
	int selectCountBy(Map<String, Object> params);
}
