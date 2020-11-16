package com.example.demo.contant;

/**
 * @author Pecker
 * @Description 返回状态码
 * @since 2020-10-17
 */
public enum ResultCodeEnum {
    SUCCESS(1, "请求成功");

    private Integer code;

    private String message;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
