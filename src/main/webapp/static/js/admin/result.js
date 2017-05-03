if(typeof Result == "undefined"){
    var Result = {
        Success: {
            code: 901,
            name: "Success",
            message: "Success"
        },
        SessionError: {
            code: 902,
            name: "SessionError",
            message: "Session is timeout, login again!"
        },
        ObjectIdError: {
            code: 903,
            name: "ObjectIdError",
            message: "Cannot find the object by this object id."
        },
        SaveInternalError: {
            code: 904,
            name: "SaveInternalError",
            message: "Cannot save the object due to a server internal error."
        },
        CategoryRemoveNotAllow: {
            code: 1111,
            name: "CategoryRemoveNotAllow",
            message: "Actived category or category with selections cannot be removed."
        },
        SelectionRemoveNotAllow: {
            code: 1211,
            name: "SelectionRemoveNotAllow",
            message: "Actived selection or selection with options cannot be removed."
        },
        OptionRemoveNotAllow: {
            code: 1311,
            name: "OptionRemoveNotAllow",
            message: "Actived option cannot be removed."
        }
    }
}