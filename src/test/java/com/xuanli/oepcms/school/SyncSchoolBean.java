/**
 * @fileName:  SyncSchoolBean.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年4月19日 下午2:34:38
 */
package com.xuanli.oepcms.school;

import java.util.List;

/**
 * @author QiaoYu[www.codelion.cn]
 */
public class SyncSchoolBean {
	private List<SyncSchoolBeans> rows;
	private boolean success;
	private long total;

	public List<SyncSchoolBeans> getRows() {
		return rows;
	}

	public void setRows(List<SyncSchoolBeans> rows) {
		this.rows = rows;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

}
