/**
 * @fileName:  SyncBookDetailResultBean.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月28日 上午10:01:28
 */ 
package com.xuanli.oepcms.thirdapp.sdk.xl.bean;

import java.util.List;

/** 
 * @author  QiaoYu 
 */
public class SyncBookDetailResultBean {
	private List<UnitBean> units;
	private List<SectionBean> sections;
	private List<SectionDetailBean> sectiondetails;
	public List<UnitBean> getUnits() {
		return units;
	}
	public void setUnits(List<UnitBean> units) {
		this.units = units;
	}
	public List<SectionBean> getSections() {
		return sections;
	}
	public void setSections(List<SectionBean> sections) {
		this.sections = sections;
	}
	public List<SectionDetailBean> getSectiondetails() {
		return sectiondetails;
	}
	public void setSectiondetails(List<SectionDetailBean> sectiondetails) {
		this.sectiondetails = sectiondetails;
	}
}
