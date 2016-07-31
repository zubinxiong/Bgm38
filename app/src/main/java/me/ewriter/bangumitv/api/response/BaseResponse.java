package me.ewriter.bangumitv.api.response;

import java.io.Serializable;

/**
 * Created by Zubin on 2016/7/25.
 */
public abstract class BaseResponse implements Serializable {


    private static final long serialVersionUID = 1610062787694313823L;

    private String request;
    private int code;
    private String error;

    public String getRequest() {
        return request;
    }

    public int getCode() {
        return code;
    }

    public String getError() {
        return error;
    }
}
