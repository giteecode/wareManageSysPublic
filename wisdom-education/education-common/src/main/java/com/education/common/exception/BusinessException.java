package com.education.common.exception;

import com.education.common.utils.ResultCode;

/**
 * 业务异常
 * @author zengjintao
 * @version 1.0
 * @create 2018/9/7 21:47
 **/
public class BusinessException extends RuntimeException {

    private ResultCode resultCode;

    public BusinessException(ResultCode resultCode){
        this.resultCode = resultCode;
    }

    public BusinessException(String message){
        super(message);
    }

    public BusinessException(Throwable cause){
        super(cause);
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
