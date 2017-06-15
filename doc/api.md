# REST API Document

### 1. Category

(1)`api/category/list`
   
  - Get updated categories by revision.
  - method: GET
  - param: 
    - rev(int): revision, default value is 0
  - return:
    - update(boolean): categories is updated or not by this revision
    - rev(int): new revision
    - categories(List\<CategoryBean>): updated category list

### 2. Selection

(1)`api/selection/list`
   
  - Get updated selections by revision.
  - method: GET
  - param: 
    - rev(int): revision, default value is 0
  - return:
    - update(boolean): selections is updated or not by this revision
    - rev(int): new revision
    - selections(List\<SelectionBean>): updated selection list

### 3. Option

(1)`api/option/list`
   
  - Get updated options by revision.
  - method: GET
  - param: 
    - rev(int): revision, default value is 0
  - return:
    - update(boolean): options is updated or not by this revision
    - rev(int): new revision
    - options(List\<Option Bean>): updated option list

### 4. User

### 5. Message

### 6. Favorite