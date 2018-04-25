/**
 * @fileName:  ExceptionCode.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月15日 上午10:01:31
 */
package com.xuanli.oepcms.contents;

/**
 * @author QiaoYu
 */
public class ExceptionCode {
	/** 成功 */
	public static final Integer SUCCESS_CODE = 0;
	/** 未知错误 */
	public static final Integer UNKNOW_CODE = 99999;

	/** 增加用户错误 */
	public static final Integer ADDUSER_ERROR_CODE = 1000;
	/** 验证码错误 */
	public static final Integer CAPTCHA_ERROR_CODE = 1001;
	/** 用户信息错误 */
	public static final Integer USERINFO_ERROR_CODE = 1002;
	/** 用户被禁用 **/
	public static final Integer USERINFO_NOUSE_ERROR = 1004;
	/** 删除学生出现错误 **/
	public static final Integer DELETE_STUDENT_ERROR = 1005;
	/** 重置学生密码失败 **/
	public static final int REST_STUDENT_PASSWORD_ERROR = 1006;
	/** 批量添加出现错误 **/
	public static final int ADD_BATCH_ERROR = 1007;
	/** 已经批量添加过了 **/
	public static final int NOT_AGING_ADD_ERROR = 1008;
	/** 批量添加数量不能超过100 **/
	public static final int ADD_BATCH_SIZE_ERROR = 1008;
	/** 写评语未选择学生 */
	public static final int UPDATE_BATCH_REMARK_ERROR = 1009;
	/** 完善用户信息错误 */
	public static final int PERFECT_USERINFO_ERROR = 1010;
	/** 修改密码失败 **/
	public static final int UPDATE_PASSWORD_ERROR = 1011;
	/** 学生添加班级错误 **/
	public static final int ADD_STUDENT_CLASS_ERROR = 1012;

	/** 参数验证错误 */
	public static final int PARAMETER_VALIDATE_ERROR_CODE = 7001;

	/** 短信验证码错误 */
	public static final Integer MOBILE_MESSAGE_ERROR_CODE = 9001;
	/** 发送短信错误 */
	public static final Integer SENDMSG_ERROR_CODE = 9002;
	/** 手机号码错误 */
	public static final Integer MOBILE_ERROR_CODE = 9003;

	/** 同步教材错误 */
	public static final Integer SYNC_BOOK_ERROR = 2001;
	/** 同步试卷错误 */
	public static final Integer SYNC_PAPER_ERROR = 2002;
	/** 同步自主练习文章错误 */
	public static final Integer SYNC_ARTICLE_ERROR = 2003;
	/** 同步试题库错误 */
	public static final Integer SYNC_QUESTION_ERROR = 2004;
	/** 同步链接错误 */
	public static final Integer SYNC_OTHER_LINK_ERROR = 2005;

	/** 学生答题超时 */
	public static final Integer HOME_WORK_TIME_OUT = 3001;

	/** 用户未登陆 **/
	public static final Integer USER_NO_LOGIN = 99998;
	
	/** 评分音频错误 **/
	public static final int AUDIO_CHECK_ERROR = 4001;
}
