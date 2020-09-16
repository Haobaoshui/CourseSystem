package com.haobaoshui.course.model;

public class ResultNormal {
    private String message;
    private int resultCode;

    public ResultNormal(String message, int resultCode) {
        this.message = message;
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
