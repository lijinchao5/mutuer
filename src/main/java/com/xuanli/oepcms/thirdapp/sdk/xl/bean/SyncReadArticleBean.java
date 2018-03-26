/**
 * 
 */
package com.xuanli.oepcms.thirdapp.sdk.xl.bean;

import java.util.List;

/**
 * @author lijinchao
 * @date 2018年3月14日 下午3:17:57
 */
public class SyncReadArticleBean extends SyncBaseBean{
	private List<ReadArticleBean> result;

	public List<ReadArticleBean> getResult() {
		return result;
	}

	public void setResult(List<ReadArticleBean> result) {
		this.result = result;
	}
}
