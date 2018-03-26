/**
 * @fileName:  HomeworkPicScoreBean.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月23日 下午2:34:42
 */ 
package com.xuanli.oepcms.controller.bean; 

/** 
 * @author  QiaoYu 
 */
public class HomeworkPicScoreBean {
	private int homeworktype;
	private String text;
	private double score;
	private double fluency;
	private double integrity;
	private double pronunciation;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHomeworktype() {
		return homeworktype;
	}
	public void setHomeworktype(int homeworktype) {
		this.homeworktype = homeworktype;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public double getFluency() {
		return fluency;
	}
	public void setFluency(double fluency) {
		this.fluency = fluency;
	}
	
	public double getIntegrity() {
		return integrity;
	}
	public void setIntegrity(double integrity) {
		this.integrity = integrity;
	}
	public double getPronunciation() {
		return pronunciation;
	}
	public void setPronunciation(double pronunciation) {
		this.pronunciation = pronunciation;
	}
	
}
