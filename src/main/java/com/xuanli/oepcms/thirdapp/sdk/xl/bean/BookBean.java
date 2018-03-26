/**
 * 
 */
package com.xuanli.oepcms.thirdapp.sdk.xl.bean;

import java.util.Date;

/**
 * @author lijinchao
 * @date 2018年2月26日 下午6:29:56
 */
public class BookBean {
	private Long id;
	private String name;
	private Integer bookVersion;
	private String grade;
	private String bookVolume;
	private String bookVersionName;
	private String gradeName;
	private String bookVolumeName;
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private String enableFlag;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getBookVersion() {
		return bookVersion;
	}
	public void setBookVersion(Integer bookVersion) {
		this.bookVersion = bookVersion;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getBookVolume() {
		return bookVolume;
	}
	public void setBookVolume(String bookVolume) {
		this.bookVolume = bookVolume;
	}
	public String getBookVersionName() {
		return bookVersionName;
	}
	public void setBookVersionName(String bookVersionName) {
		this.bookVersionName = bookVersionName;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getBookVolumeName() {
		return bookVolumeName;
	}
	public void setBookVolumeName(String bookVolumeName) {
		this.bookVolumeName = bookVolumeName;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getEnableFlag() {
		return enableFlag;
	}
	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}
	
}
