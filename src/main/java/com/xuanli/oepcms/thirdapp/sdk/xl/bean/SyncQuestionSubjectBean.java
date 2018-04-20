/**
 * 
 */
package com.xuanli.oepcms.thirdapp.sdk.xl.bean;

import java.util.List;

/**
 * @author lijinchao
 * @date 2018年3月15日 上午11:22:16
 */
public class SyncQuestionSubjectBean extends SyncBaseBean{
	private List<QuestionSubjectBean> result;

	public List<QuestionSubjectBean> getResult() {
		return result;
	}

	public void setResult(List<QuestionSubjectBean> result) {
		this.result = result;
	}
}
