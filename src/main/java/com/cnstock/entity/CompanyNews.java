package com.cnstock.entity;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyNews extends BaseEntity<String>{
	
	//主键ID
	private String id;
	//股票代码
	private String secucode;
	//证券简称
	private String secuabbr;
	//类型
	private String type;
	//问题
	private String question;
	//答案
	private String answer;
	//来源
	private String source;
	//发布时间
	private String publishTime;
	//提问时间
	private Date qTime;
	//回答时间
	private Date aTime;
	
	public CompanyNews(){
		
	}

	public CompanyNews(String secucode, String secuabbr, String type, String question, String answer,
			String source, String publishTime, Date qTime, Date aTime) {
		super();
		this.secucode = secucode;
		this.secuabbr = secuabbr;
		this.type = type;
		this.question = question;
		this.answer = answer;
		this.source = source;
		this.publishTime = publishTime;
		this.qTime = qTime;
		this.aTime = aTime;
	}
	
	public CompanyNews(String id, String secucode, String secuabbr, String type, String question, String answer,
			String source, String publishTime, Date qTime, Date aTime) {
		super();
		this.id = id;
		this.secucode = secucode;
		this.secuabbr = secuabbr;
		this.type = type;
		this.question = question;
		this.answer = answer;
		this.source = source;
		this.publishTime = publishTime;
		this.qTime = qTime;
		this.aTime = aTime;
	}
	
}
