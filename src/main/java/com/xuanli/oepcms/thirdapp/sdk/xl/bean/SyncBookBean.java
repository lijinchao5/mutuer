/**
 * @fileName:  SyncBookBean.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月28日 上午9:52:08
 */ 
package com.xuanli.oepcms.thirdapp.sdk.xl.bean;

import java.util.List;

/** 
 * @author  QiaoYu 
 */
public class SyncBookBean extends SyncBaseBean{
	private List<BookBean> result;

	public List<BookBean> getResult() {
		return result;
	}

	public void setResult(List<BookBean> result) {
		this.result = result;
	}
	
}
