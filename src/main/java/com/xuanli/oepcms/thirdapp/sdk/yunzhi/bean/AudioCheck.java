/**
 * @fileName:  AudioCheck.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年4月18日 下午2:29:06
 */ 
package com.xuanli.oepcms.thirdapp.sdk.yunzhi.bean; 

/** 
 * @author  QiaoYu[www.codelion.cn]
 */
public class AudioCheck {
	/**
	 * 置信度的值为0和10，10代表可能存在该项音质问题，0代表该项检测正常
	 */
	//音量过小的置信度
	private Integer volume;
	//截幅的置信度
	private Integer clipping;
	//噪音过大的置信度
	private Integer noise;
	//截断的置信度
	private Integer cut;
	//是否音频过短
	private Boolean tooShort;
	//是否是空音频
	private Boolean emptyAudio;
	public Integer getVolume() {
		return volume;
	}
	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	public Integer getClipping() {
		return clipping;
	}
	public void setClipping(Integer clipping) {
		this.clipping = clipping;
	}
	public Integer getNoise() {
		return noise;
	}
	public void setNoise(Integer noise) {
		this.noise = noise;
	}
	public Integer getCut() {
		return cut;
	}
	public void setCut(Integer cut) {
		this.cut = cut;
	}
	public Boolean getTooShort() {
		return tooShort;
	}
	public void setTooShort(Boolean tooShort) {
		this.tooShort = tooShort;
	}
	public Boolean getEmptyAudio() {
		return emptyAudio;
	}
	public void setEmptyAudio(Boolean emptyAudio) {
		this.emptyAudio = emptyAudio;
	}
	
}
