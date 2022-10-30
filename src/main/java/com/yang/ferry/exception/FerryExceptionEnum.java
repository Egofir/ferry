package com.yang.ferry.exception;

public enum FerryExceptionEnum {
    NEED_USER_NAME(10001, "用户名不能为空"),
    NEED_PASSWORD(10002, "密码不能为空"),
    PASSWORD_NOT_CONFORM(10003, "密码不符合规范"),
    NAME_EXISTED(10004, "不允许重名，注册失败"),
    INSERT_FAILED(10005, "插入失败，请重试"),
    SENSITIVEWORD_EXISTED(10006, "用户名存在敏感词汇"),
    SYSTEM_ERROR(20000, "系统异常");

    Integer code;
    String message;

    FerryExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }
}
