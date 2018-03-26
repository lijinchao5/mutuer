/**
 * 
 */
package com.xuanli.oepcms.thirdapp.sdk.xl.bean;

import java.util.List;

/**
 * @author lijinchao
 * @date 2018年3月15日 上午11:22:35
 */
public class SyncQuestionSubjectDetailResultBean {
	List<QuestionSubjectBean> subjects;
	List<QuestionSubjectDetailBean> details;
	List<QuestionOptionBean> options;
	
	public List<QuestionSubjectBean> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<QuestionSubjectBean> subjects) {
		this.subjects = subjects;
	}
	public List<QuestionSubjectDetailBean> getDetails() {
		return details;
	}
	public void setDetails(List<QuestionSubjectDetailBean> details) {
		this.details = details;
	}
	public List<QuestionOptionBean> getOptions() {
		return options;
	}
	public void setOptions(List<QuestionOptionBean> options) {
		this.options = options;
	}
}
