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
 
  5.upload(MultipartFile flie,HttpServletRequest request) FileService
   main实验fileName 06:54 FTPUtil
   二进制文件类型，防止乱码
   richTextImgUpload→simditor
  6.前台商品
   ProductController
 getProductDetail(id) Const ProductStatusEnum
   ON_SALE(1,在售) code value
   list(keyword,categoryId,num,siz)
   oderBy 
 getProductByKeywordCategory
   codeMessage 参数校验 page源码
   Const ProductListOrderBy接口
   set contain的时间复杂度1
   selectByNameAndCategoryIds()
 补写md5、ftp  keyword.trim() != null恒成立？？""不等同于null
 
四.购物车
 CartController
 1.add(count,productId)
    selectCartByUserIdProductId()
    interface Cart() CHECKED
    CartProductVo() CartVo()
    selectCarByUserId() BigDecimal的Str构造器
    BigDecimalTest()丢失精度 加载spring容器
    Java没有专门  处理货币的数据类型
    float、double只能用来科学、工程计算
    BigDecimal商业计算 源码
    BigDecimalUtil sub mul div 
    getAllCheckedStatus(userId)
    selectCartProductCheckedStatusByUserId()
 
 2.update(userId,productId,count) 和前端约定
    deleteProduct(userId,productIds)变成数组遍历然后放入集合
    deleteByUserIdProductIds()
    list(userId)
 
 3.selectAll()
    checkedOrUncheckedProduct(ui,c,pi)+unSelectAll() 
    selectOrUnSelect()
    getCartProductCount()
    selectCartProductCount() sum(quantity)
    IFNULL(,0)
《Thinking In Java》 VS 《Effective Java》 VS 《深入理解Java虚拟机》

五.收货地址
  ShippingController
  1.add(shipping) 对象数据绑定→自生
    add(userId,shipping)
    
  2.del(shippingId)
    del(userId,shippingId)横向越权
    deleteByShippingIdUserId()
    
  3.update(userId)
    updatByShipping()
    
  4.select(userId)
    select(userId, shippingId)
    selectByShippingIdAndUserId()
    
  5.list(num,size)
    list(userId,num,size)
    
六.支付


category品类名字相同，仍然可以添加