# REST API Document

### 1. Category

(1)`api/category/list`
   
  - Get updated categories by revision.
  - method: GET
  - param: 
    - rev(int): revision, default value is 0
  - return:
    - update(boolean): categories is updated or not by this revision
    - rev(int): the newest revision
    - categories(List\<CategoryBean>): updated category list

### 2. Selection

(1)`api/selection/list`
   
  - Get updated selections by revision.
  - method: GET
  - param: 
    - rev(int): revision, default value is 0
  - return:
    - update(boolean): selections is updated or not by this revision
    - rev(int): the newest revision
    - selections(List\<SelectionBean>): updated selection list

### 3. Option

(1)`api/option/list`
   
  - Get updated options by revision.
  - method: GET
  - param: 
    - rev(int): revision, default value is 0
  - return:
    - update(boolean): options is updated or not by this revision
    - rev(int): the newest revision
    - options(List\<Option Bean>): updated option list

### 4. User

(1)`api/user/register`
   
  - User registeration by email.
  - method: POST
  - param: 
    - email(String): email address
    - name(String): user name
    - password(String)
  - return:
    - success(boolean): success flag

(2)`api/user/login`
   
  - User login by email.
  - method: POST
  - param: 
    - email(String): email address
    - password(String)
    - identifier: device identifier, UUID of the device
    - deviceToken: device token from APNs server for remote notification
    - os: operation system name
    - version: operation system version
    - lan: user language
  - return:
    - token(String): access token
    - user(UserBean): user information

(3)`api/user/facebook`
   
  - User login by facebook.
  - method: POST
  - param: 
    - accessToken(String): access token from facebook IDP
    - identifier: device identifier, UUID of the device
    - deviceToken: device token from APNs server for remote notification
    - os: operation system name
    - version: operation system version
    - lan: user language
  - return:
    - token(String): access token
    - user(UserBean): user information

(4)`api/user`
   
  - Pull the user information.
  - method: GET
  - header: 
    - token(String): access token
  - param: 
    - rev(String): revision, default value is 0
  - return:
    - update(boolean): update status of user information
    - user(UserBean): user information

(5)`api/user/{uid}`
   
  - Get the user information by user id.
  - method: GET
  - path variable: 
    - uid(String): user id
  - return:
    - user(UserBean): user information

(6)`api/user/modify/info`
   
  - Modify the user information.
  - method: POST
  - header: 
    - token(String): access token
  - param: 
    - name(String): user name
    - contact(String): contact way of this user
    - address(String)
  - return:
    - success(boolean): success flag
    - rev(int): the newest revision

(7)`api/user/modify/password`
   
  - Modify the user password.
  - method: POST
  - header: 
    - token(String): access token
  - param: 
    - password(String): user password
    - lan(String): device language, the default value is "en"
  - return:
    - success(boolean): success flag

(8)`api/user/avatar`
   
  - Upload the user avatar.
  - method: POST
  - header: 
    - token(String): access token
  - return:
    - avatar(String): storage path of the user avatar in server.

### 5. Message

(1)`api/message/create`
   
  - Create a new message.
  - method: POST
  - header: 
    - token(String): access token
  - param: 
    - cid(String): category id
    - title(String): message title
    - introduction(String): message introduction
    - oids(String): the JSON array string of option ids, ["oid1", "oid2", ..., "oidn"]
    - price(int): price
    - sell(boolean): the sell flag to distinguish sell message and buy message
  - return:
    - mid(String): message id of this new message

(2)`api/message/modify`
   
  - Modify an existing message.
  - method: POST
  - header: 
    - token(String): access token
  - param: 
    - mid(String): message id
    - title(String): message title
    - introduction(String): message introduction
    - oids(String): the JSON array string of option ids, ["oid1", "oid2", ..., "oidn"]
    - price(int): price, the default value is -1
  - return:
    - success(boolean): success flag

(3)`api/message/close`
   
  - Close an existing message.
  - method: POST
  - header: 
    - token(String): access token
  - param: 
    - mid(String): message id
  - return:
    - success(boolean): success flag

(4)`api/message/picture`
   
  - Upload a picture for a message.
  - method: POST
  - header: 
    - token(String): access token
  - param: 
    - mid(String): message id
  - return:
    - picture(PictureBean): picture information

(5)`api/message/picture`
   
  - Delete a picture.
  - method: DELETE
  - header: 
    - token(String): access token
  - param: 
    - pid(String): picture id
  - return:
    - success(boolean): success flag

(6)`api/message/list`
   
  - Get updated messages by revision.
  - method: GET
  - param: 
    - rev(int): revision, default value is 0
    - sell(boolean): the sell flag to distinguish sell message and buy message, default value is true
  - return:
    - messages(List\<MessageBean>): updated messages

(7)`api/message/list/user`
   
  - Get messages posted by a user.
  - method: GET
  - header: 
    - token(String): access token
  - param: 
    - sell(boolean): the sell flag to distinguish sell message and buy message, default value is true
  - return:
    - messages(List\<MessageBean>): updated messages

(8)`api/message/list/favorites`
   
  - Get favorite messages of a user.
  - method: GET
  - header: 
    - token(String): access token
  - param: 
    - sell(boolean): the sell flag to distinguish sell message and buy message, default value is true
  - return:
    - messages(List\<MessageBean>): updated messages

(9)`api/detail/{mid}`
   
  - Get detail information of a message. 
  - method: GET
  - header: 
    - token(String): access token
  - path variable: 
    - mid(String): message id
  - return:
    - message(MessageBean): detail message information. Return null if the message is deleted and it is not belong to the user authenticated by the access token.

(10)`api/picture/{mid}`
   
  - Get pictures of a message. 
  - method: GET
  - header: 
    - token(String): access token
  - path variable: 
    - mid(String): message id
  - return:
    - pictures(List\<PictureBean>): pictures information of this message.

### 6. Favorite