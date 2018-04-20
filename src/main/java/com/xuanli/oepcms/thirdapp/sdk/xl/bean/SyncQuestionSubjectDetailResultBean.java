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
	List<QuestionSubjectDetailBean> details;
	List<QuestionOptionBean> options;
	
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
