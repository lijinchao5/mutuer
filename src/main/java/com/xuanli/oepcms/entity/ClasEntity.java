package com.xuanli.oepcms.entity;

import java.util.Date;

import com.xuanli.oepcms.util.BasePageBean;

public class ClasEntity extends BasePageBean{
    private Long id;

    private String clasId;

    private String name;

    private Integer schoolId;

    private String createId;

    private Date createDate;

    private String updateId;

    private Date updateDate;

    private String enableFlag;
    
    private String grade;
    
    //一共多少学生
    private int totalStudent;
    //一共多少考试
    private int totalExam;
    //一共多少次家庭作业
    private int totalHomework;
    //一共多少模考
    private int totalVExam;
    //是否可以批量,0:可以  其他:都不可以
    private int canBatch;
    
    private int complateExam;
    private int complateHomework;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClasId() {
		return clasId;
	}
	public void setClasId(String clasId) {
		this.clasId = clasId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
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
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public int getTotalStudent() {
		return totalStudent;
	}
	public void setTotalStudent(int totalStudent) {
		this.totalStudent = totalStudent;
	}
	public int getTotalExam() {
		return totalExam;
	}
	public void setTotalExam(int totalExam) {
		this.totalExam = totalExam;
	}
	public int getTotalHomework() {
		return totalHomework;
	}
	public void setTotalHomework(int totalHomework) {
		this.totalHomework = totalHomework;
	}
	public int getTotalVExam() {
		return totalVExam;
	}
	public void setTotalVExam(int totalVExam) {
		this.totalVExam = totalVExam;
	}
	public int getCanBatch() {
		return canBatch;
	}
	public void setCanBatch(int canBatch) {
		this.canBatch = canBatch;
	}
	public int getComplateExam() {
		return complateExam;
	}
	public void setComplateExam(int complateExam) {
		this.complateExam = complateExam;
	}
	public int getComplateHomework() {
		return complateHomework;
	}
	public void setComplateHomework(int complateHomework) {
		this.complateHomework = complateHomework;
	}
    
}