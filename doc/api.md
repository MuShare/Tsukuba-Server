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
   
  - Get the user information by user ID.
  - method: GET
  - path variable: 
    - uid(String): user ID
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

### 6. Favorite