/**
 * @fileName:  HomeworkScoreBean.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月22日 下午3:46:27
 */ 
package com.xuanli.oepcms.controller.bean;

import java.util.List;

import com.xuanli.oepcms.entity.HomeworkStudentScoreWordEntity;

/** 
 * @author  QiaoYu 
 */
public class HomeworkScoreBean {
	private Long id;
	private Long studentId;
	private String name;
	private Long homeworkId;
	private Long sectionId;
	private String audioPath;
	private String studentText;
	private Double studentScore;
	private Integer homeworkType;
	private Long sectionDetailId;
	private String standerAudioPath;
	private String standerText;
	private String standerMAudioPath;
	private String standerWAudioPath;
	private String standerWordMean;
	//电脑说的为F,需要人为录音的为T
	private String dialogName;
	private Integer dialogNum;
	private Long homeworkDetailId;
	
	
	public Long getHomeworkDetailId() {
		return homeworkDetailId;
	}
	public void setHomeworkDetailId(Long homeworkDetailId) {
		this.homeworkDetailId = homeworkDetailId;
	}
	private List<HomeworkStudentScoreWordEntity> homeworkStudentScoreWordEntities;
	
	
	public List<HomeworkStudentScoreWordEntity> getHomeworkStudentScoreWordEntities() {
		return homeworkStudentScoreWordEntities;
	}
	public void setHomeworkStudentScoreWordEntities(List<HomeworkStudentScoreWordEntity> homeworkStudentScoreWordEntities) {
		this.homeworkStudentScoreWordEntities = homeworkStudentScoreWordEntities;
	}
	public Integer getDialogNum() {
		return dialogNum;
	}
	public void setDialogNum(Integer dialogNum) {
		this.dialogNum = dialogNum;
	}
	public String getDialogName() {
		return dialogName;
	}
	public void setDialogName(String dialogName) {
		this.dialogName = dialogName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getHomeworkId() {
		return homeworkId;
	}
	public void setHomeworkId(Long homeworkId) {
		this.homeworkId = homeworkId;
	}
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public String getAudioPath() {
		return audioPath;
	}
	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}
	public String getStudentText() {
		return studentText;
	}
	public void setStudentText(String studentText) {
		this.studentText = studentText;
	}
	public Double getStudentScore() {
		return studentScore;
	}
	public void setStudentScore(Double studentScore) {
		this.studentScore = studentScore;
	}
	public Integer getHomeworkType() {
		return homeworkType;
	}
	public void setHomeworkType(Integer homeworkType) {
		this.homeworkType = homeworkType;
	}
	public Long getSectionDetailId() {
		return sectionDetailId;
	}
	public void setSectionDetailId(Long sectionDetailId) {
		this.sectionDetailId = sectionDetailId;
	}
	public String getStanderAudioPath() {
		return standerAudioPath;
	}
	public void setStanderAudioPath(String standerAudioPath) {
		this.standerAudioPath = standerAudioPath;
	}
	public String getStanderText() {
		return standerText;
	}
	public void setStanderText(String standerText) {
		this.standerText = standerText;
	}
	public String getStanderMAudioPath() {
		return standerMAudioPath;
	}
	public void setStanderMAudioPath(String standerMAudioPath) {
		this.standerMAudioPath = standerMAudioPath;
	}
	public String getStanderWAudioPath() {
		return standerWAudioPath;
	}
	public void setStanderWAudioPath(String standerWAudioPath) {
		this.standerWAudioPath = standerWAudioPath;
	}
	public String getStanderWordMean() {
		return standerWordMean;
	}
	public void setStanderWordMean(String standerWordMean) {
		this.standerWordMean = standerWordMean;
	}
	
}
