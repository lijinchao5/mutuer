/**
 * @fileName:  SyncSchoolBeans.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年4月19日 下午2:36:43
 */ 
package com.xuanli.oepcms.school;

import java.util.List;

/** 
 * @author  QiaoYu[www.codelion.cn]
 */
public class SyncSchoolBeans {
	private Long id;
	private Long region;
	private List<String> letters;
	private String name;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRegion() {
		return region;
	}
	public void setRegion(Long region) {
		this.region = region;
	}
	public List<String> getLetters() {
		return letters;
	}
	public void setLetters(List<String> letters) {
		this.letters = letters;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
