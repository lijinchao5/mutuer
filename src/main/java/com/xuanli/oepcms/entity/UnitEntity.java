package com.xuanli.oepcms.entity;

import java.util.Date;

public class UnitEntity {
    private Long id;

    private String name;

    private String bookId;

    private String createId;

    private Date createDate;

    private String updateId;

    private Date updateDate;

    private String enableFlag;
    
    private Long cmsId;
    
    //使用查询使用
    private Integer bgrade;
    private Integer bversion;
    private Integer bvolume;
    
    
    public Integer getBgrade() {
		return bgrade;
	}

	public void setBgrade(Integer bgrade) {
		this.bgrade = bgrade;
	}

	public Integer getBversion() {
		return bversion;
	}

	public void setBversion(Integer bversion) {
		this.bversion = bversion;
	}

	public Integer getBvolume() {
		return bvolume;
	}

	public void setBvolume(Integer bvolume) {
		this.bvolume = bvolume;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
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