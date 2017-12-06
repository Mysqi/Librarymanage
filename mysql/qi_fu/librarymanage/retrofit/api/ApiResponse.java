package com.mysql.qi_fu.librarymanage.retrofit.api;

/**
 * Created by qi_fu on 2017/4/13.
 */

public class ApiResponse<T> {
    private int code;
    private String msg;
    private T data;
    private boolean success;

    public ApiResponse() {
    }

    public ApiResponse(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    public ApiResponse(short code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ApiResponse(int code, String msg, T data, boolean success) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSucceed() {
        return this.success;
    }

    public static ApiResponse<?> success(String msg) {
        ApiResponse<?> apiResponse = new ApiResponse();
        apiResponse.setSuccess(true);
        apiResponse.setMsg(msg);

        return apiResponse;
    }

    public static ApiResponse<?> error(String msg) {
        ApiResponse<?> apiResponse = new ApiResponse();
        apiResponse.setSuccess(false);
        apiResponse.setMsg(msg);

        return apiResponse;
    }

    public static ApiResponse<?> error(String msg, int code) {
        ApiResponse<?> apiResponse = new ApiResponse();
        apiResponse.setSuccess(false);
        apiResponse.setMsg(msg);
        apiResponse.setCode(code);

        return apiResponse;
    }

    public static ApiResponse<?> fail(String msg) {
        ApiResponse<?> apiResponse = new ApiResponse();
        apiResponse.setSuccess(true);
        apiResponse.setMsg(msg);

        return apiResponse;
    }
}
