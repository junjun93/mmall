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
 
 
    
    门户：用户登录、产品、购物车、收货地址、订单、支付
    后台：用户管理、品类管理、产品管理、订单管理、统计管理
    
功能性bug
    防止横向越权、纵向越权。调用接口字段顺序问题
    category品类名字相同，仍然可以添加；parentId不存在，仍然可以添加；产品save.do可以重复添加同一条信息；
    产品list.do、detail.do有图片服务器问题；产品管理图片、富文本上传功能未测；
    shipping的收件人名字重复报500，前端可以避免名字相同；update.do接口测试字段不全问题
    
    vsftpd 08:30-次日晚上8:00左右，头天搞到00:30定位问题
    jdk安装3个半小时
    
    d m登录名产生主目录
    /root :noh 取消高亮 :wq! 强制保存
    exit
    mv 文件名     重命名
    
    ssh root@47.97.215.177
    ssh junjun@47.97.215.177
    useradd -d /usr/junjun -m junjun
    cd /usr/junjun
    passwd junjun
    vim /etc/sudoers
    exit
    
    首次要设置root用户密码
    passwd root	
    切换到root用户
    su root 	
    
    1.rpm -qa|grep jdk
    rpm -e  --nodeps  jdk版本
    
    cd /
    mkdir developer
    cd developer/
    mkdir setup
    cd setup/
    wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u161-b12/2f38c3b165be4555a1fa6e98c45e0808/jdk-8u161-linux-x64.tar.gz"
    tar -zxvf jdk-8u161-linux-x64.tar.gz
    
    getconf LONG_BIT
    
    vim /etc/profile
    
    2.tomcat的conf/server.xml   URIEncoding="UTF-8" 
    bin文件夹下                启动tomcat ./startup.sh
    
    3.mvn -version
    
    4.vsftpd
    安装、配置vsftpd
     
        1.安装软件 
            yum -y install vsftpd(自动确定)
        2.创建虚拟用户
             （1）选择在根或者用户目录下创建ftp文件夹  mkdir ftpfile
             （2）添加匿名用户  useradd ftpuser -d /ftpfile -s /sbin/nologin
                             userdel -r ftpuser
             （3）修改ftpfile权限 chown -R ftpuser.ftpuser ./ftpfile/(写法问题)
             （4）重设ftpuser密码 passwd ftpuser
             （5）将用户名写到文件中，后续要引用
                 cd /etc/vsftpd 
                 vim chroot_list
        3.配置    
            （1）修改SELINUX=disabled   vim /etc/selinux/config
            （2）保存退出 :wq
            注：如果一会验证的时候碰到550拒绝访问请执行
              setsebool -P ftp_home_dir 1(等价于5)
            然后重启linux服务器，执行reboot命令
            （3）编辑配置文件 vim /etc/vsftpd/vsftpd.conf
                这20个配置之外的其他配置全部删除
            注：是否使用sudo权限执行请根据具体环境来决定
        4.防火墙配置
        坑：今天自己遇到的CentOS7安装vsftpd重启iptables防火墙失败问题已定位：
           在CentOS7中，有很多CentOS6中的常用服务发生了变化。其中iptables是其中比较大的一个，防火墙iptables被
           firewalld取代。因此必须关闭并禁止启动filewalld，才能让iptables防火墙重启
            systemctl stop firewalld
            systemctl disable firewalld
           
            
            (1) yum install iptables-services
                vim /etc/sysconfig/iptables
            (2)
                -A INPUT  -p TCP --dport 61001:62000 -j ACCEPT
                -A OUTPUT -p TCP --sport 61001:62000 -j ACCEPT
                -A INPUT  -p TCP --dport 20 -j ACCEPT
                -A OUTPUT -p TCP --sport 20 -j ACCEPT
                -A INPUT  -p TCP --dport 21 -j ACCEPT
                -A OUTPUT -p TCP --sport 21 -j ACCEPT
                
                -A INPUT -j REJECT --reject-with icmp-host-prohibited
                -A FORWARD -j REJECT --reject-with icmp-host-prohibited
            将以上配置添加到防火墙配置中
           （3）保存退出 :wq
           （4）重启防火墙 systemctl restart iptables
                        systemctl enable  iptables
           
        5.vsftpd重用命令
            systemctl enable vsftpd
            systemctl restart vsftpd
            systemctl start vsftpd
            systemctl status vsftpd
            
    1.500 OOPS: cannot change directory:/product/ftpfile
    
    2.vsftpd：500 OOPS: vsftpd: refusing to run with writable root inside chroot ()
    从2.3.5之后，vsftpd增强了安全检查，如果用户被限定在了其主目录下，则该用户的主目录不能再具有写权限了！如果检查发现还有写权限，就会报该错误。
     要修复这个错误，可以用命令chmod a-w /home/user去除用户主目录的写权限，注意把目录替换成你自己的。或者你可以在vsftpd的配置文件中增加下列两项中的一项：
    allow_writeable_chroot=YES
     web服务器、应用服务器;form enctype
    
    5.nginx依赖安装
        （1）wget 下载地址
            yum -y install gcc zlib zlib-devel pcre-devel openssl openssl-devel 
            ./configure
        
        （2）处理一些c的文件 
             make
             make install
                     
        （3）添加配置文件
            whereis nginx 
            cd conf
            vim nginx.conf  添加include vhost/*.conf;
            vhost目录下，下载4个配置文件

        （4）启动
            cd /usr/local/nginx/sbin/
            ./nginx
            端口占用 fuser -k 80/tcp

    6.mysql
        wget http://repo.mysql.com/mysql-community-release-el7-5.noarch.rpm  
        rpm -ivh mysql-community-release-el7-5.noarch.rpm  
        ls -1 /etc/yum.repos.d/mysql-community*
        yum -y  install mysql-server
        
        vim /etc/my.cnf
        加上 character-set-server=utf8
            default-character-set=utf-8
        

        自启动     systemctl enable mysqld
        
        新安装的mysql服务后，一般需要执行数据库初始化操作 ，从而生成与权限相关的表，执行命令如下：
         /usr/bin/mysql_install_db --user=mysql
         
         http://blog.csdn.net/a2011480169/article/details/51912771
         
        重启      systemctl restart mysqld
        登录     mysql -u root
        查看用户  select user,host,password from mysql.user;
        设置密码  set password for root@localhost = password('shajia');
        退出      exit
        insert into mysql.user(user,host,password) values("mmall","localhost",password("passshajia"));
        insert into mysql.user(host,user,password) values("localhost","mmall",password("passshajia"));
        
        ERROR 2002 (HY000): Can't connect to local MySQL server through socket '/var
        Plugin 'FEDERATED' is disabled. /usr/sbin/mysqld: Table 'mysql.plugin' doesn
        ERROR 1364 (HY000): Field 'ssl_cipher' doesn't have a default value
        sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
        sql_mode=NO_ENGINE_SUBSTITUTION
        
        错语原因：
        mysql用户表的中某些字段不能为空，没有默认值，其实是操作错误，mysql添加用户是不能这样直接insert user表的
        grant usage on *.* to 'mmall'@'localhost' identified by 'passshajia' with grant option;
        
                        
        flush privileges;
        create database `mmall` default character set utf8 COLLATE utf8_general_ci;
        grant all privileges on mmall.* to mmall@localhost identified by 'passshajia';
        
        source /developer/mmall.sql;
        select * from mmall_user\G;
        select * from mmall_product\G;
    
    7.git
       1.rpm -qa|grep git
        rpm -e  --nodeps  git版本
        wget http://learning.happymmall.com/git/git-v2.8.0.tar.gz
        安装依赖
        yum -y install zlib-devel openssl-devel cpio expat-devel gettext-devel curl-devel perl-ExtUtils-CBuilder perl-ExtUtils- MakeMaker 
        tar -zxvf git-v2.8.0.tar.gz
        cd git 2.8.0
        make prefix=/usr/local/git all
        make prefix=/usr/local/git install
        vim /etc/profile
        source /etc/profile
        配置文件少个/
        git config --global user.name "junjun"
        git config --global user.email "ycfangjun@126.com"
    不管linux、windows换行符转换    
        git config --global core.autocrlf false
    避免中文乱码  
        git config --global core.quotepath off
        
        ssh-keygen -t rsa -C "ycfangjun@126.com"
        eval `ssh-agent`
        ssh-add ~/.ssh/id_rsa
        cat ~/.ssh/id_rsa.pub
        
    
    二.上线
    ctrl+c：截断正在运行的脚本
    vsftpd没权限，添加remotePath无用
    设置执行权限 chmod u+x *.sh
    dhcp static
    
    三.CentOS设置静态ip
        nmcli dev status
        vim /etc/sysconfig/network-scripts/ifcfg-ens33
        
        BOOTPROTO="dhcp" 变 BOOTPROTO="static"
        加上三行
        IPADDR=192.168.1.108
        NATMASK=255.255.255.0
        MM_CONTROLLED=no

        systemctl restart networkF
[WARNING] Some problems were encountered while building the effective model for com:mmall:war:1.0-SNAPSHOT
[WARNING] 'build.plugins.plugin.version' for org.apache.maven.plugins:maven-compiler-plugin is missing. @ line 273, column 21
[WARNINIt is highly recommended to fix these problems because they threaten the stability of your build.
[WARNINFor this reason, future Maven versions might no longer support building such malformed projects.
    
    解压文件 tar -zxvf a.gz
    移动文件 mv a.gz /setup
    cp -v a.txt  /developer/setup
    rm -v -i a.txt
    /只有根目录要加
    
    阿里云ssh总是自动断开
    vim /etc/ssh/sshd_config

    找到下面两行
    #ClientAliveInterval 0
    #ClientAliveCountMax 3
    去掉注释，改成
    ClientAliveInterval 30
    ClientAliveCountMax 3600
    
    这两行的意思分别是
    1、客户端每隔多少秒向服务发送一个心跳数据
    2、客户端多少秒没有相应，服务器自动断掉连接
    
    systemctl restart sshd
    systemctl status -l mysqld
    
2018-03-07 12:41:50 0 [Note] /usr/sbin/mysqld (mysqld 5.6.39) starting as process 14224 ...
/usr/sbin/mysqld: Character set 'utf-8' is not a compiled character set and is not specified in the '/usr/share/mysql/charsets/Index.xml' file

  1168  180307 14:03:05 mysqld_safe Starting mysqld daemon with databases from /var/lib/mysql
  1169  2018-03-07 14:03:05 0 [Warning] TIMESTAMP with implicit DEFAULT value is deprecated. Please use --explicit_defaults_for_timestamp server option (see documentation for more details).
  1170  2018-03-07 14:03:05 0 [Note] /usr/sbin/mysqld (mysqld 5.6.39) starting as process 30793 ...
  1171  2018-03-07 14:03:05 30793 [Warning] Buffered warning: Changed limits: max_open_files: 1024 (requested 5000)
  1172  
  1173  2018-03-07 14:03:05 30793 [Warning] Buffered warning: Changed limits: table_open_cache: 431 (requested 2000)
  1174  
  1175  2018-03-07 14:03:05 30793 [Note] Plugin 'FEDERATED' is disabled.
  1176  /usr/sbin/mysqld: Table 'mysql.plugin' doesn't exist
  1177  2018-03-07 14:03:05 30793 [ERROR] Can't open the mysql.plugin table. Please run mysql_upgrade to create it.
  1178  2018-03-07 14:03:05 30793 [Note] InnoDB: Using atomics to ref count buffer pool pages
  1179  2018-03-07 14:03:05 30793 [Note] InnoDB: The InnoDB memory heap is disabled
  1180  2018-03-07 14:03:05 30793 [Note] InnoDB: Mutexes and rw_locks use GCC atomic builtins
  1181  2018-03-07 14:03:05 30793 [Note] InnoDB: Memory barrier is not used
  1182  2018-03-07 14:03:05 30793 [Note] InnoDB: Compressed tables use zlib 1.2.3
  1183  2018-03-07 14:03:05 30793 [Note] InnoDB: Using Linux native AIO
  1184  2018-03-07 14:03:05 30793 [Note] InnoDB: Using CPU crc32 instructions
  1185  2018-03-07 14:03:05 30793 [Note] InnoDB: Initializing buffer pool, size = 128.0M
  1186  2018-03-07 14:03:05 30793 [Note] InnoDB: Completed initialization of buffer pool
  1187  2018-03-07 14:03:05 30793 [Note] InnoDB: The first specified data file ./ibdata1 did not exist: a new database to be created!
  1188  2018-03-07 14:03:05 30793 [Note] InnoDB: Setting file ./ibdata1 size to 12 MB
  1189  2018-03-07 14:03:05 30793 [Note] InnoDB: Database physically writes the file full: wait...
  1190  2018-03-07 14:03:05 30793 [Note] InnoDB: Setting log file ./ib_logfile101 size to 48 MB
  1191  2018-03-07 14:03:06 30793 [Note] InnoDB: Setting log file ./ib_logfile1 size to 48 MB
  1192  2018-03-07 14:03:07 30793 [Note] InnoDB: Renaming log file ./ib_logfile101 to ./ib_logfile0
  1193  2018-03-07 14:03:07 30793 [Warning] InnoDB: New log files created, LSN=45781
  1194  2018-03-07 14:03:07 30793 [Note] InnoDB: Doublewrite buffer not found: creating new
  1195  2018-03-07 14:03:07 30793 [Note] InnoDB: Doublewrite buffer created
  1196  2018-03-07 14:03:07 30793 [Note] InnoDB: 128 rollback segment(s) are active.
  1197  2018-03-07 14:03:07 30793 [Warning] InnoDB: Creating foreign key constraint system tables.
  1198  2018-03-07 14:03:07 30793 [Note] InnoDB: Foreign key constraint system tables created
  1199  2018-03-07 14:03:07 30793 [Note] InnoDB: Creating tablespace and datafile system tables.
  1200  2018-03-07 14:03:07 30793 [Note] InnoDB: Tablespace and datafile system tables created.
  1201  2018-03-07 14:03:07 30793 [Note] InnoDB: Waiting for purge to start
  1202  2018-03-07 14:03:07 30793 [Note] InnoDB: 5.6.39 started; log sequence number 0
  1203  2018-03-07 14:03:07 30793 [ERROR] /usr/sbin/mysqld: unknown variable 'default-character-set=utf8'
  1204  2018-03-07 14:03:07 30793 [ERROR] Aborting

   1254  180307 14:13:05 mysqld_safe Logging to '/var/log/mysqld.log'.
    1255  180307 14:13:05 mysqld_safe Starting mysqld daemon with databases from /var/lib/mysql
    1256  2018-03-07 14:13:05 0 [Warning] TIMESTAMP with implicit DEFAULT value is deprecated. Please use --explicit_defaults_for_timestamp server option (see documentation for more details).
    1257  2018-03-07 14:13:05 0 [Note] /usr/sbin/mysqld (mysqld 5.6.39) starting as process 366 ...
    1258  2018-03-07 14:13:05 366 [Warning] Buffered warning: Changed limits: max_open_files: 1024 (requested 5000)
    1259  
    1260  2018-03-07 14:13:05 366 [Warning] Buffered warning: Changed limits: table_open_cache: 431 (requested 2000)
    1261  
    1262  2018-03-07 14:13:05 366 [Note] Plugin 'FEDERATED' is disabled.
    1263  /usr/sbin/mysqld: Table 'mysql.plugin' doesn't exist
    1264  2018-03-07 14:13:05 366 [ERROR] Can't open the mysql.plugin table. Please run mysql_upgrade to create it.
    1265  2018-03-07 14:13:05 366 [Note] InnoDB: Using atomics to ref count buffer pool pages
    1266  2018-03-07 14:13:05 366 [Note] InnoDB: The InnoDB memory heap is disabled
    1267  2018-03-07 14:13:05 366 [Note] InnoDB: Mutexes and rw_locks use GCC atomic builtins
    1268  2018-03-07 14:13:05 366 [Note] InnoDB: Memory barrier is not used
    1269  2018-03-07 14:13:05 366 [Note] InnoDB: Compressed tables use zlib 1.2.3
    1270  2018-03-07 14:13:05 366 [Note] InnoDB: Using Linux native AIO
    1271  2018-03-07 14:13:05 366 [Note] InnoDB: Using CPU crc32 instructions
    1272  2018-03-07 14:13:05 366 [Note] InnoDB: Initializing buffer pool, size = 128.0M
    1273  2018-03-07 14:13:05 366 [Note] InnoDB: Completed initialization of buffer pool
    1274  2018-03-07 14:13:05 366 [Note] InnoDB: Highest supported file format is Barracuda.
    1275  2018-03-07 14:13:05 366 [Note] InnoDB: 128 rollback segment(s) are active.
    1276  2018-03-07 14:13:05 366 [Note] InnoDB: Waiting for purge to start
    1277  2018-03-07 14:13:05 366 [Note] InnoDB: 5.6.39 started; log sequence number 1600607
    1278  2018-03-07 14:13:05 366 [Warning] No existing UUID has been found, so we assume that this is the first time that this server has been started. Generating a new UUID: 99a9104c-21ce-11e8-b9f9-00163e0f45f3.
    1279  2018-03-07 14:13:05 366 [Note] Server hostname (bind-address): '*'; port: 3306
    1280  2018-03-07 14:13:05 366 [Note] IPv6 is available.
    1281  2018-03-07 14:13:05 366 [Note]   - '::' resolves to '::';
    1282  2018-03-07 14:13:05 366 [Note] Server socket created on IP: '::'.
    1283  2018-03-07 14:13:05 366 [ERROR] Fatal error: Can't open and lock privilege tables: Table 'mysql.user' doesn't exist
    1284  180307 14:13:06 mysqld_safe mysqld from pid file /var/run/mysqld/mysqld.pid ended
