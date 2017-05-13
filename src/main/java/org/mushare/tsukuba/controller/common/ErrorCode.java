package org.mushare.tsukuba.controller.common;

public enum ErrorCode {
    // Admin
    ErrorAdminSession(801, "Admin's session is timeout."),
    ErrorObjecId(802, "Object cannot be found by this cid."),

    // Basic
    ErrorToken(901, "Token is wrong."),
    ErrorSaveFailed(902, "Save failed."),
    ErrorInvalidParameter(903, "Invalid parameter."),

    // User
    ErrorEmailExist(1011, "This email has been registered."),
    ErrorIllegalIDeviceOS(1021, "Device OS should be iOS or Android."),
    ErrorEmailNotExist(1022, "This email is not exsit."),
    ErrorPasswordWrong(1023, "Password is wrong."),
    ErrorFacebookAccessTokenInvalid(1031, "Facebook access token is invalid"),
    ErrorSendResetPasswordMail(1061, "Send reset password email failed."),

    // Message
    ErrorModifyMessageNoPrivilege(2021, "You do not have previlege to modify this message"),
    ErrorSavePicture(2041, "Save picture error.");

    public int code;
    public String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
