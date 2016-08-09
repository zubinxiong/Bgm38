package me.ewriter.bangumitv.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zubin on 2016/7/25.
 */
public class BaseResponse {

    /**
     * request : /ep/638069/status/remove?source=onAir
     * code : 200
     * error : OK
     */

    @SerializedName("request")
    private String request;
    @SerializedName("code")
    private int code;
    @SerializedName("error")
    private String error;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
