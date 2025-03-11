package com.app.digi.response;

public class ResponseBase {
    public String statusMessage;
    public int statusCode;

    @Override
    public String toString() {
        return "ResponseBase{" +
                "statusMessage='" + statusMessage + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}
