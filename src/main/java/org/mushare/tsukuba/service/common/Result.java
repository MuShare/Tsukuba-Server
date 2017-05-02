package org.mushare.tsukuba.service.common;

public enum Result {

    Success(901),
    SessionError(902),
    ObjectIdError(903),
    SaveInternalError(904),

    CategoryRemoveNotAllow(1041);

    public int code;

    private Result(int code) {
        this.code = code;
    }

}
