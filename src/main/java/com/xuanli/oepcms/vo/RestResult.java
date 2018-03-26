package com.xuanli.oepcms.vo;

import com.xuanli.oepcms.contents.ExceptionCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResult<T> {
	/**状态*/
    private int code;
    /**对应状态的消息*/
    private String message;
	/**具体业务数据*/
    private T result;
    
    public static RestResult<String> okNoResult(String message) {
    	RestResult<String> okResult = new RestResult<String>();
    	okResult.setMessage(message);
    	okResult.setResult("");
    	okResult.setCode(ExceptionCode.SUCCESS_CODE);
    	return okResult;
    }
    
    public static <T> RestResult<T> ok(T result) {
        RestResult<T> okResult = new RestResult<T>();
        okResult.setResult(result);
        okResult.setCode(ExceptionCode.SUCCESS_CODE);
        return okResult;
    }

    public static <T> RestResult<T> failed(int code, String message) {
        RestResult<T> failedResult = new RestResult<T>();
        failedResult.setCode(code);
        failedResult.setMessage(message);
        return failedResult;
    }

    public static <T> RestResult<T> failed(int code, String message, T result) {
        RestResult<T> failedResult = new RestResult<T>();
        failedResult.setCode(code);
        failedResult.setMessage(message);
        failedResult.setResult(result);
        return failedResult;
    }

	/** 
	 * @return 返回 code 
	 */
	public int getCode() {
		return code;
	}

	/** 
	 * @setParam 设置code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/** 
	 * @return 返回 message 
	 */
	public String getMessage() {
		return message;
	}

	/** 
	 * @setParam 设置message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/** 
	 * @return 返回 result 
	 */
	public T getResult() {
		return result;
	}

	/** 
	 * @setParam 设置result
	 */
	public void setResult(T result) {
		this.result = result;
	}
    
}
