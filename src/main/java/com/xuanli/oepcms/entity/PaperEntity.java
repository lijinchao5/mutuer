package com.xuanli.oepcms.entity;

import java.util.Date;

public class PaperEntity {
	private Long id;

	private String paperNum;

	private String name;

	private String notice;

	private Double totalScore;

	private Integer totalTime;

	private Integer studySectionId;

	private Integer gradeLevelId;

	private Integer addressProvince;

	private Integer term;

	private Integer paperType;

	private Integer addressCity;

	private Integer addressArea;

	private Integer examType;

	private Integer questionType;

	private Long createId;

	private Date createDate;

	private Long updateId;

	private Date updateDate;

	private String enableFlag;

	private Long cmsId;

	private String paperProvince;

	public String getPaperProvince() {
		return paperProvince;
	}

	public void setPaperProvince(String paperProvince) {
		this.paperProvince = paperProvince;
	}

	public Integer getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(Integer addressCity) {
		this.addressCity = addressCity;
	}

	public Integer getAddressArea() {
		return addressArea;
	}

	public void setAddressArea(Integer addressArea) {
		this.addressArea = addressArea;
	}

	public Integer getPaperType() {
		return paperType;
	}

	public void setPaperType(Integer paperType) {
		this.paperType = paperType;
	}

	public Long getCmsId() {
		return cmsId;
	}

	public void setCmsId(Long cmsId) {
		this.cmsId = cmsId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaperNum() {
		return paperNum;
	}

	public void setPaperNum(String paperNum) {
		this.paperNum = paperNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}

	public Integer getStudySectionId() {
		return studySectionId;
	}

	public void setStudySectionId(Integer studySectionId) {
		this.studySectionId = studySectionId;
	}

	public Integer getGradeLevelId() {
		return gradeLevelId;
	}

	public void setGradeLevelId(Integer gradeLevelId) {
		this.gradeLevelId = gradeLevelId;
	}

	public Integer getAddressProvince() {
		return addressProvince;
	}

	public void setAddressProvince(Integer addressProvince) {
		this.addressProvince = addressProvince;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Integer getExamType() {
		return examType;
	}

	public void setExamType(Integer examType) {
		this.examType = examType;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
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