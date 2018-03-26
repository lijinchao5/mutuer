package com.xuanli.oepcms.entity;

import java.util.Date;

import com.xuanli.oepcms.util.BasePageBean;

import io.swagger.annotations.ApiModel;
@ApiModel
public class HomeworkEntity extends BasePageBean{
    private Long id;

    private String name;

    private Date endTime;

    private Long clasId;

    private String remark;

    private Long createId;

    private Date createDate;

    private Long updateId;

    private Date updateDate;

    
    private String detail;
    private int complateTotal;
    private int totalStudent;
    
    
    private String status;
    //查询使用
    private String clasIds;
    
    
    
    public String getClasIds() {
		return clasIds;
	}

	public void setClasIds(String clasIds) {
		this.clasIds = clasIds;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getComplateTotal() {
		return complateTotal;
	}

	public void setComplateTotal(int complateTotal) {
		this.complateTotal = complateTotal;
	}

	public int getTotalStudent() {
		return totalStudent;
	}

	public void setTotalStudent(int totalStudent) {
		this.totalStudent = totalStudent;
	}

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
        this.name = name == null ? null : name.trim();
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getClasId() {
        return clasId;
    }

    public void setClasId(Long clasId) {
        this.clasId = clasId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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