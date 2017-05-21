package org.mushare.tsukuba.service.common;

import org.mushare.tsukuba.domain.Category;
import org.mushare.tsukuba.domain.User;

public enum Result {

    Success(901),
    SessionError(902),
    ObjectIdError(903),
    SaveInternalError(904),

    UserEmailRegistered(1001),
    CategoryRemoveNotAllow(1002),
    SelectionRemoveNotAllow(1003),
    OptionRemoveNotAllow(1004),
    OptionNotInCategory(1005),
    MessageModifyNoPrevilege(1006),
    FavoriteNotExisted(1007);

    public int code;

    private Result(int code) {
        this.code = code;
    }

}
