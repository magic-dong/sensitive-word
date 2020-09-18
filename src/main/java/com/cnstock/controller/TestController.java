package com.cnstock.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cnstock.dao.TestDao;
import com.cnstock.json.MessageResult;

@RestController
@RequestMapping("/test")
public class TestController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private TestDao testDao;
	
	
	@GetMapping("/init")
	public MessageResult init(@RequestParam Map<String, Object> params) {
		try {
			return success();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return error(e.getMessage());
		}
		
	}
	
	@GetMapping("/get")
	public MessageResult getMysqlData(@RequestParam Map<String, Object> params){
		try {
			Map<String, Object> obj =testDao.selectById(1);
			return success(obj);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return error(e.getMessage());
		}
	}
	
}