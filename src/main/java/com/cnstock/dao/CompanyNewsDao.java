package com.cnstock.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cnstock.entity.CompanyNews;

@Mapper
public interface CompanyNewsDao {

	List<CompanyNews> selectBy(@Param("params") Map<String,Object> params,@Param("page")Integer page,@Param("size")Integer size);
	
	int selectCountBy(@Param("params") Map<String,Object> params);
	
	CompanyNews selectById(String id);
	
	int save(CompanyNews companyNews);
	
	int updateById(CompanyNews companyNews);
	
}
