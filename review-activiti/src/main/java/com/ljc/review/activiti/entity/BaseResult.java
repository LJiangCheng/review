package com.ljc.review.activiti.entity;

public class BaseResult<T> {

    private boolean success;
    private String message;
    private T data;

    private BaseResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static BaseResult success() {
        return new BaseResult<>(true, "", null);
    }

    public static BaseResult success(Object data){
        return new BaseResult<>(true, "", data);
    }

    public static BaseResult success(Object data, String message) {
        return new BaseResult<>(true, message, data);
    }

    public static BaseResult error(String message) {
        return new BaseResult<>(false, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
