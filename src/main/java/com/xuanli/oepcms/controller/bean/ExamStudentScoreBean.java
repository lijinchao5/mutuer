/**
 * @fileName:  ExamStudentScoreBean.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月24日 上午11:29:58
 */
package com.xuanli.oepcms.controller.bean;

/**
 * @author QiaoYu
 */
public class ExamStudentScoreBean extends ExamBean{
	private Double fluency;
	private Double integrity;
	private Double pronunciation;
	private String remark;
	private String text;
	private String audioPath;
	private Double studentScore;
	public Double getFluency() {
		return fluency;
	}
	public void setFluency(Double fluency) {
		this.fluency = fluency;
	}
	public Double getIntegrity() {
		return integrity;
	}
	public void setIntegrity(Double integrity) {
		this.integrity = integrity;
	}
	public Double getPronunciation() {
		return pronunciation;
	}
	public void setPronunciation(Double pronunciation) {
		this.pronunciation = pronunciation;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAudioPath() {
		return audioPath;
	}
	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}
	public Double getStudentScore() {
		return studentScore;
	}
	public void setStudentScore(Double studentScore) {
		this.studentScore = studentScore;
	}
}
