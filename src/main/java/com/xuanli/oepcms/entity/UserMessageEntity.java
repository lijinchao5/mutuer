/**
 * 
 */
package com.xuanli.oepcms.entity;

/**
 * @author lijinchao
 * @date 2018年4月9日 下午2:29:44
 */
public class UserMessageEntity {
	private Long id;

	private String type;

	private String text;

	private Long userId;

	private Boolean flags;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean getFlags() {
		return flags;
	}

	public void setFlags(Boolean flags) {
		this.flags = flags;
	}
}
