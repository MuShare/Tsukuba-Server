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
            code: 901,
            name: "CategoryRemoveNotAllow",
            message: "Actived category cannot be removed."
        }
    }
}