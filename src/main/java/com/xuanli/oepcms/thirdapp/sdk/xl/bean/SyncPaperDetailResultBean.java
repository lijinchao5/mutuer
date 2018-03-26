/**
 * 
 */
package com.xuanli.oepcms.thirdapp.sdk.xl.bean;

import java.util.List;

/**
 * @author lijinchao
 * @date 2018年3月5日 下午12:44:06
 */
public class SyncPaperDetailResultBean {
	private List<PaperSubjectBean> subjects;
	private List<PaperSubjectDetailBean> details;
	private List<PaperOptionBean> options;
	public List<PaperSubjectBean> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<PaperSubjectBean> subjects) {
		this.subjects = subjects;
	}
	public List<PaperSubjectDetailBean> getDetails() {
		return details;
	}
	public void setDetails(List<PaperSubjectDetailBean> details) {
		this.details = details;
	}
	public List<PaperOptionBean> getOptions() {
		return options;
	}
	public void setOptions(List<PaperOptionBean> options) {
		this.options = options;
	}
}
