/**
 * 
 */
package com.xuanli.oepcms.thirdapp.sdk.xl.bean;

import java.util.List;

/**
 * @author lijinchao
 * @date 2018年3月21日 上午9:54:06
 */
public class SyncOtherLinkBean extends SyncBaseBean {
	private List<OtherLinkBean> result;

	public List<OtherLinkBean> getResult() {
		return result;
	}

	public void setResult(List<OtherLinkBean> result) {
		this.result = result;
	}
}
