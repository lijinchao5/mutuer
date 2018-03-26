/**
 * 
 */
package com.xuanli.oepcms.thirdapp.sdk.xl.bean;

import java.util.List;

/**
 * @author lijinchao
 * @date 2018年3月14日 下午4:20:31
 */
public class SyncReadSentenceResultBean {
	private List<ReadSentenceBean> sections;

	public List<ReadSentenceBean> getSections() {
		return sections;
	}

	public void setSections(List<ReadSentenceBean> sections) {
		this.sections = sections;
	}
}
