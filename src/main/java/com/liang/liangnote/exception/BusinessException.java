package com.liang.liangnote.exception;

/**
 * 业务异常类
 * @author liang
 * @version 1.0.0
 * @date 2025/5/27 19:00
 */
public class BusinessException extends RuntimeException {
    
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
} 