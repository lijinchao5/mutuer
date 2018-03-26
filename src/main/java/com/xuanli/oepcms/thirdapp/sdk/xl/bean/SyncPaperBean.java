/**
 * 
 */
package com.xuanli.oepcms.thirdapp.sdk.xl.bean;

import java.util.List;

/**
 * @author lijinchao
 * @date 2018年3月5日 下午12:42:04
 */
public class SyncPaperBean extends SyncBaseBean{
	private List<PaperBeans> result;

	public List<PaperBeans> getResult() {
		return result;
	}

	public void setResult(List<PaperBeans> result) {
		this.result = result;
	}
}
