package com.xuanli.oepcms.entity;

import java.util.Date;

public class HomeworkDetailEntity {
    private Long id;

    private Long homeworkId;

    private Long sectionDetailId;

    private String homeworkType;

    private Long createId;

    private Date createDate;

    private Long updateId;

    private Date updateDate;
    
    private String dialogName;
    //无实际意义
    private Integer allSize;
    

    public Integer getAllSize() {
		return allSize;
	}

	public void setAllSize(Integer allSize) {
		this.allSize = allSize;
	}

	public String getDialogName() {
		return dialogName;
	}

	public void setDialogName(String dialogName) {
		this.dialogName = dialogName;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Long homeworkId) {
        this.homeworkId = homeworkId;
    }

    public Long getSectionDetailId() {
        return sectionDetailId;
    }

    public void setSectionDetailId(Long sectionDetailId) {
        this.sectionDetailId = sectionDetailId;
    }

    public String getHomeworkType() {
        return homeworkType;
    }

    public void setHomeworkType(String homeworkType) {
        this.homeworkType = homeworkType == null ? null : homeworkType.trim();
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
}