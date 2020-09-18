package com.cnstock.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestDao {
	
	Map<String,Object> selectById(Integer id);

}
