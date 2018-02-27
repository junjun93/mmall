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
 补写md5、ftp  keyword.trim() != null恒成立？？""不同于null
 
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
        
OSGI：一种java开发技术，实现项目模块逻辑-->物理意义上的解耦
    内嵌了一个Web服务器的，就是jetty；
    项目启动后，不停止运行，然后停掉其中的一个模块，需要可以再重新加上。
    该模块消失并不是javascript的技术，而是一种服务器技术，我们是通过服务器内部把它动态卸载掉的；

1.初识OSGI：
    http://blog.csdn.net/acmman/article/details/50848595
2.idea中建立一个OSGI项目
    http://blog.csdn.net/love_taylor/article/details/75194394
3.基于idea+maven的OSGI示例
    http://www.itboth.com/d/NNZRzeEVvQna/demo-osgi
4.quick start
    http://enroute.osgi.org/qs/050-start.html
 
 安装、配置vsftpd
 
    sudo passwd root	// 设置root用户密码
    su root 	// 切换到root用户
    1.安装软件 
        yum -y install vsftpd
    2.创建虚拟用户
         （1）选择在根或者用户目录下创建ftp文件夹  mkdir ftpfile
         （2）添加匿名用户  useradd ftpuser -d /ftpfile -s /sbin/nologin
         （3）修改ftpfile权限 chown -R ftpuse
         （4）重设ftpuser密码 passwd ftpuser
         （5）修改SELINUX=disable   vim /etc/selinux/config
         （6）保存退出 :wq
            注：如果一会验证的时候碰到550拒绝访问请执行
              sudo setsebool -P ftp_home_dir 1
            然后重启linux服务器，执行reboot命令
         （7）编辑配置文件 sudo vim /etc/vsftpd/vsftpd.conf
                这20个配置之外的其他配置全部删除
         注：是否使用sudo权限执行请根据具体环境来决定
    3.配置    
        （1）cd /etc/vsftpd
        （2）将用户名写到文件中，后续要引用 sudo vim chroot_list
        （3）保存退出 :wq
    4.防火墙配置
        （1）sudo vim /etc/sysconfig/iptables
        （2）
            -A INPUT  -p TCP --dport 61001:62000 -j ACCEPT
            -A OUTPUT -p TCP --sport 61001:62000 -j ACCEPT
            -A INPUT  -p TCP --dport 20 -j ACCEPT
            -A OUTPUT -p TCP --sport 20 -j ACCEPT
            -A INPUT  -p TCP --dport 21 -j ACCEPT
            -A OUTPUT -p TCP --sport 21 -j ACCEPT
        将以上配置添加到防火墙配置中
       （3）保存退出 :wq
       （4）重启防火墙 sudo service iptables restart
            systemctl restart iptables
    5.vsftpd重用命令
        sudo service vsftpd start
        sudo service vsftpd stop
        sudo service vsftpd restart
        systemctl start vsftpd.service
        
    云服务器 ECS Linux CentOS 7 下重启服务不再通过 service 操作，而是通过 systemctl 操作。
        查看：systemctl status sshd.service
        启动：systemctl start sshd.service
        重启：systemctl restart sshd.service
        自启：systemctl enable sshd.service
        
 web服务器、应用服务器;form enctype
    
    门户：用户登录、产品、购物车、收货地址、购物车、支付、订单管理
    后台：用户管理、品类管理、产品管理、订单管理、统计管理
    
功能性bug:
    category品类名字相同，仍然可以添加；
    parentId不存在，仍然可以添加；
    产品list.do有图片服务器问题；
    富文本接口名问题；
    产品管理图片、富文本上传功能未测



