package com.cnstock.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cnstock.entity.CompanyNews;
import com.cnstock.json.MessageResult;
import com.cnstock.service.ICompanyNewsService;
import com.cnstock.utils.StringUtils;

@RestController
@RequestMapping("/news")
public class CompanyNewsController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(CompanyNewsController.class);
	
	@Autowired
	private ICompanyNewsService companyNewsService;
	
	@GetMapping("/hdySave")
	public MessageResult hdySave(@RequestParam Map<String, Object> params){
		try {
			//当前页数
			String pageStr=String.valueOf(params.get("page"));
			Integer page=StringUtils.isNotNull(pageStr)?Integer.valueOf(pageStr):null;
			page=(page!=null && page>0)?page:1;
			params.put("page", page);
			//最大显示行数
			String sizeStr=String.valueOf(params.get("size"));
			Integer size=StringUtils.isNotNull(sizeStr)?Integer.valueOf(sizeStr):null;
			size=(size!=null && size>0)?size:100;
			params.put("size", size);
			//调用互动易接口分页获取数据并入库更新数据
			boolean saveFlag = companyNewsService.saveHdyDataByPage(params);
			String msg=saveFlag?"互动易数据更新成功！":"互动易数据更新失败！";
			return success(msg);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return error(e.getMessage());
		}
	}
	
	
	@GetMapping("/list")
	public MessageResult list(@RequestParam Map<String, Object> params){
		try {
			//当前页数
			String pageStr=String.valueOf(params.get("page"));
			Integer page=StringUtils.isNotNull(pageStr)?Integer.valueOf(pageStr):null;
			page=(page!=null && page>0)?page:1;
			params.put("page", page);
			//最大显示行数
			String sizeStr=String.valueOf(params.get("size"));
			Integer size=StringUtils.isNotNull(sizeStr)?Integer.valueOf(sizeStr):null;
			size=(size!=null && size>0)?size:100;
			params.put("size", size);
			//是否过滤(默认不过滤)
			String isFilterStr=String.valueOf(params.get("isFilter"));
			boolean isFilter=StringUtils.isNotNull(isFilterStr)?Boolean.valueOf(isFilterStr):false;
			params.put("isFilter", isFilter);
			//条件查询互动易总条数
			int count = companyNewsService.selectCountBy(params);
			if(count<=0){
				return success("Data Not Found");
			}
			//分页查询互动易数据
			List<CompanyNews> list = companyNewsService.selectListBy(params);
			MessageResult message=new MessageResult();
			message.put("code", 200);
			message.put("success", true);
			message.put("count", count);
			message.put("page", page);
			message.put("size", size);
			message.put("data",((list!=null && !list.isEmpty())?list:"Data Not Found"));
			return message;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return error(e.getMessage());
		}
	}
	
	
	@GetMapping("/filterCompare")
	public MessageResult getFilterCompareList(@RequestParam Map<String, Object> params){
		try {
			//当前页数
			String pageStr=String.valueOf(params.get("page"));
			Integer page=StringUtils.isNotNull(pageStr)?Integer.valueOf(pageStr):null;
			page=(page!=null && page>0)?page:1;
			params.put("page", page);
			//最大显示行数
			String sizeStr=String.valueOf(params.get("size"));
			Integer size=StringUtils.isNotNull(sizeStr)?Integer.valueOf(sizeStr):null;
			size=(size!=null && size>0)?size:100;
			params.put("size", size);
			//是否过滤(默认不过滤)
			String isFilterStr=String.valueOf(params.get("isFilter"));
			boolean isFilter=StringUtils.isNotNull(isFilterStr)?Boolean.valueOf(isFilterStr):false;
			params.put("isFilter", isFilter);
			//条件查询互动易总条数
			int count = companyNewsService.selectCountBy(params);
			if(count<=0){
				return success("Data Not Found");
			}
			//条件分页查询过滤屏蔽词后的互动易数据
			List<Map<String, Object>> list = companyNewsService.selectFilterListBy(params);
			MessageResult message=new MessageResult();
			message.put("code", 200);
			message.put("success", true);
			message.put("count", count);
			message.put("page", page);
			message.put("size", size);
			message.put("data",((list!=null && !list.isEmpty())?list:"Data Not Found"));
			return message;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return error(e.getMessage());
		}
	}
	
	
}