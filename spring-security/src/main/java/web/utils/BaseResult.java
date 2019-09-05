package web.utils;

public class BaseResult<T> {

    private static final long serialVersionUID = -313965489774631737L;

    private boolean success = false;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 结果集
     */
    private T data;

    /**
     * 构造方法
     */
    public BaseResult() {
    }

    /**
     * 构造方法
     *
     * @param success 成功标识
     */
    public BaseResult(boolean success) {
        this.success = success;
    }

    /**
     * 设置结果集
     */
    public BaseResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }


    private static BaseResult result(boolean success, Object data, String msg) {
        BaseResult<Object> result = new BaseResult<>();
        result.setSuccess(success);
        result.setData(data);
        result.setMessage(msg);
        return result;
    }

    public static BaseResult success(Object data) {
        return result(true, data, "");
    }

    public static BaseResult error(String errorMsg) {
        return result(false, null, errorMsg);
    }

    public static BaseResult error() {
        return result(false, null, "");
    }

    public static BaseResult success() {
        return result(true, null, "");
    }

    /**
     * Getter method for property <tt>success</tt>.
     *
     * @return property value of success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Setter method for property <tt>success</tt>.
     *
     * @param success value to be assigned to property success
     */
    public BaseResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    /**
     * Getter method for property <tt>message</tt>.
     *
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>message</tt>.
     *
     * @param message value to be assigned to property message
     */
    public BaseResult setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Getter method for property <tt>data</tt>.
     *
     * @return property value of data
     */
    public T getData() {
        return data;
    }

    /**
     * Setter method for property <tt>data</tt>.
     *
     * @param data value to be assigned to property data
     */
    public BaseResult setData(T data) {
        this.data = data;
        return this;
    }
}