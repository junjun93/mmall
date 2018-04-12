门户：用户登录、产品、购物车、收货地址、订单、支付
后台：用户管理、品类管理、产品管理、订单管理、统计管理

已解决的功能性bug
    防止横向越权、纵向越权。调用接口字段顺序问题
    category品类名字相同，仍然可以添加；
    parentId不存在，仍然可以添加；
    产品save.do可以重复添加同一条信息；
    shipping的收件人名字重复报500，前端可以避免名字相同；
    update.do接口测试字段不全问题
    cancel.do取消订单有bug
    
user.getRole() == Const.Role.ROLE_ADMIN
FTPUtil第32行的文件夹不存在

图片上传bug
1.PropertiesUtil类漏了一行props = new Properties();
2.index.jsp表单action属性漏了项目名mmall
3.现在上传vsftpd文件服务器成功，返回的json里的url点开报404，应该是hosts文件出问题了

状态信息、各种校验、高复用响应对象、VO对象处理数据
select ifnull(sum(quantity),0) as count from mmall_cart where user_id = #{userId}
代码比对工具，Java获取系统时间的四种方法

nginx目录转发直接读取到了二维码,哈哈2.0了