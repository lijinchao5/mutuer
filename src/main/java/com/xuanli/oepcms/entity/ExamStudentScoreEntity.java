package com.xuanli.oepcms.entity;

import java.util.Date;

public class ExamStudentScoreEntity {
    private Long id;

    private Long examId;

    private Long subjectDetailId;

    private Long subjectOptionId;

    private String text;

    private String audioPath;

    private Double score;

    private Date createDate;

    private Long createId;

    private Date updateDate;

    private Long updateId;

    private String enableFlag;

    private String remark;

    private Double integrity;

    private Double pronunciation;

    private Double fluency;

    private Long studentId;

    private Double percentScore;
    
    private Double similarScore;
    
    public Double getSimilarScore() {
		return similarScore;
	}

	public void setSimilarScore(Double similarScore) {
		this.similarScore = similarScore;
	}

	public Double getPercentScore() {
		return percentScore;
	}

	public void setPercentScore(Double percentScore) {
		this.percentScore = percentScore;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Long getSubjectDetailId() {
        return subjectDetailId;
    }

    public void setSubjectDetailId(Long subjectDetailId) {
        this.subjectDetailId = subjectDetailId;
    }

    public Long getSubjectOptionId() {
        return subjectOptionId;
    }

    public void setSubjectOptionId(Long subjectOptionId) {
        this.subjectOptionId = subjectOptionId;
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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Double getFluency() {
        return fluency;
    }

    public void setFluency(Double fluency) {
        this.fluency = fluency;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}