package org.mushare.tsukuba.service.common;

public enum Result {

    Success(901),
    SessionError(902),
    ObjectIdError(903),
    SaveInternalError(904),

    UserEmailRegistered(1011),

    CategoryRemoveNotAllow(1111),

    SelectionRemoveNotAllow(1211),

    OptionRemoveNotAllow(1311);

    public int code;

    private Result(int code) {
        this.code = code;
    }

}
