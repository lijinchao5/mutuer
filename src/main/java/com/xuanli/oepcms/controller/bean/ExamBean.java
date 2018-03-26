/**
 * @fileName:  ExamBean.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月24日 上午9:20:42
 */ 
package com.xuanli.oepcms.controller.bean; 

/** 
 * @author  QiaoYu 
 */
public class ExamBean {
	 private Long id;
	 private Long examId;
	 private Long subjectId;
	 private String audio;
	 private Integer type;
	 private String guide;
	 private String question;
	 private String guideAudio;
	 private Integer readTime;
	 private String questionAudio;
	 private Integer writeTime;
	 private Double score;
	 private String originalText;
	 private String result;
	 private String pointResult;
	 private String correntResult;
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
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public String getAudio() {
		return audio;
	}
	public void setAudio(String audio) {
		this.audio = audio;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getGuide() {
		return guide;
	}
	public void setGuide(String guide) {
		this.guide = guide;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getGuideAudio() {
		return guideAudio;
	}
	public void setGuideAudio(String guideAudio) {
		this.guideAudio = guideAudio;
	}
	public Integer getReadTime() {
		return readTime;
	}
	public void setReadTime(Integer readTime) {
		this.readTime = readTime;
	}
	public String getQuestionAudio() {
		return questionAudio;
	}
	public void setQuestionAudio(String questionAudio) {
		this.questionAudio = questionAudio;
	}
	public Integer getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(Integer writeTime) {
		this.writeTime = writeTime;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getOriginalText() {
		return originalText;
	}
	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getPointResult() {
		return pointResult;
	}
	public void setPointResult(String pointResult) {
		this.pointResult = pointResult;
	}
	public String getCorrentResult() {
		return correntResult;
	}
	public void setCorrentResult(String correntResult) {
		this.correntResult = correntResult;
	}
}
