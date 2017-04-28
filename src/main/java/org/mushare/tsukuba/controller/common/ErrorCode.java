package org.mushare.tsukuba.controller.common;

public enum ErrorCode {
    ErrorToken(901, "Token is wrong."),

    ErrorEmailExist(1011, "This email has been registered.");

    public int code;
    public String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
