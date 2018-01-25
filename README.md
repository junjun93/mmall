注：适应ServerResponse可复用的服务响应类、Const常量类的代码富用方式


3.登出logout(session)
    注册register 调用接口checkEmail  ctrl+t
    枚举分组、内部接口分组 Role() ROLE_ADMIN ROLE_CUSTOMER
    校验 checkValid(str,type) EMAIL USERNAME register复用代码

4.getUserInfo(session)
    forgetGetQuesion selectQuestion selectQuestionByUsername-d
    forgetCheckAnswer(username, question, answer)
    checkAnswer() forgetOKEN  TokenCatch
    
5.forgetResetPassword()
     isBlank校验forgetToken  username是否存在
    _token TOKEN_PREFIX token过期校验、token equal
     updatPasswordByName()
 
6.resetPassword() checkPassword() userId横向
    updateByPrimaryKey选择性更新

7.update_information(session,user) current id
    checkEmailByUserId(email,id)

8.用户详细信息get_information(session)强制登录
     getInformation(id) selectByPrimaryKey
  忘记密码修改时要判断和原来密码一样吗？
  更新个人信息时要判断email和原来一样吗？
  
  二.分类模块
   1.CategoryManageController
    getCategory(session,id)
    addCategory(session,name,parentId)
    setCategoryName(session,id,name)
    getDeepCategory(session,id)→递归算法
    hashCode相同，equals可能不同；equals不同，hashCode不同
  mybatis处理集合不返回none
    Sets、Lists→gavaa
    @RequestParam设置参数默认值
     
 三.商品管理
  1.ProductManageController
   productSave(session,product)
   saveOrUpdateProduct(product)
   setSaleStatus(session,id,status)
  2.getDetail()
   manageProduct(id) VO
   ProjectDtailVo
  private assemble--Vo url与项目分离
   PropertiesUtil static>普通>构造，类加载
   DateTimeUtil strToDate(dataTimeStr,formatStr)
                DateToStr(date,formatStr)
  3.getList(session,pageNum,pageSize)
   getProductList()  selectList
   ProjectListVo assemble b
  4.searchProject(name,id,pageNum,pageSize) selectByNameAndBYProductId()
   4版本SQL语句:直接写、1=1、where语句
 
  5.upload(MultiparFile flie,HttpServletRequest request) FileService
   main实验fileName 06:54 FTPUtil
   二进制文件类型，防止乱码
   richTextImgUpload→simditor
 补写md5、ftp