package com.xuanli.oepcms.entity;

import java.util.Date;

public class UserSchoolEntity {
    private Integer id;

    private Long userId;

    private Long schoolId;
	// 查询使用
	private Date enddate;

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	/**
	 * 没有实际意义
	 */
    private Integer allSize;
    
    
    /** 
	 * @return 返回 allSize 
	 */
	public Integer getAllSize() {
		return allSize;
	}

	/** 
	 * @setParam 设置allSize
	 */
	public void setAllSize(Integer allSize) {
		this.allSize = allSize;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }
}