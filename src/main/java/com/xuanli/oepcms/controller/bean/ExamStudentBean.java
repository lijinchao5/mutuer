/**
 * @fileName:  ExamStudentBean.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月24日 上午11:07:10
 */ 
package com.xuanli.oepcms.controller.bean; 

/** 
 * @author  QiaoYu 
 */
public class ExamStudentBean {
	private Long id;
	private Long examId;
	private Long studentId;
	private Integer type;
	private String typeName;
	private double totalScore;
	private double studentScore;
	
	//查询使用
	private String studentName;
	//仅供查询是的结果--学生名次
	private Integer rank;
	
	private Integer studentRank;
	
	private String studentPhoto;
	
	private String remark;
	private Integer timeOut;
	
	private String complate;
	
	
	/** 
	 * @return 返回 remark 
	 */
	public String getRemark() {
		return remark;
	}
	/** 
	 * @setParam 设置remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/** 
	 * @return 返回 timeOut 
	 */
	public Integer getTimeOut() {
		return timeOut;
	}
	/** 
	 * @setParam 设置timeOut
	 */
	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}
	/** 
	 * @return 返回 complate 
	 */
	public String getComplate() {
		return complate;
	}
	/** 
	 * @setParam 设置complate
	 */
	public void setComplate(String complate) {
		this.complate = complate;
	}
	/** 
	 * @return 返回 studentPhoto 
	 */
	public String getStudentPhoto() {
		return studentPhoto;
	}
	/** 
	 * @setParam 设置studentPhoto
	 */
	public void setStudentPhoto(String studentPhoto) {
		this.studentPhoto = studentPhoto;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getStudentRank() {
		return studentRank;
	}
	public void setStudentRank(Integer studentRank) {
		this.studentRank = studentRank;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Long getExamId() {
		return examId;
	}
	public void setExamId(Long examId) {
		this.examId = examId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public double getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}
	public double getStudentScore() {
		return studentScore;
	}
	public void setStudentScore(double studentScore) {
		this.studentScore = studentScore;
	}
}
