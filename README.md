一.工具的使用
1.Tomcat集群
（1）编辑/etc/profile文件
    export CATALINA_BASE=/Users/junjun/Applications/Tomcat1;
    export CATALINA_HOME=/Users/junjun/Applications/Tomcat1;
    export TOMCAT_HOME=/Users/junjun/Applications/Tomcat1;
    export CATALINA_2_BASE=/Users/junjun/Applications/Tomcat2;
    export CATALINA_2_HOME=/Users/junjun/Applications/Tomcat2;
    export TOMCAT_2_HOME=/Users/junjun/Applications/Tomcat2;
    
（2）编辑bin/catalina.sh文件，添加两个export

（3）编辑conf/server.xml文件，改三个端口

2.Mac下IntelliJ IDEA使用Tomcat报error=13, permission denied
    cd /Users/junjun/Applications/Tomcat1/bin
    chmod a+x catalina.sh

3.安装redis-2.8.0
    wget http://download.redis.io/releases/redis-2.8.0.tar.gz
    cd /usr/local/Cellar/redis-2.8.0
    make
    make test
    cd src          cd /usr/local/Cellar/redis-2.8.0/src
    ./redis-server  ./redis-cli
    
二.基础知识
1.Redis基础命令
   (1) info --> database(keyspace)      select 1
   (2) dbsize    save   quit
   (3) flushdb  flushall-->清空
   
2.Redis键命令
    (1) set a a     del xxx     exists a
    (2) ttl a(time to live)     -1-->永久
    (3) expire a 10             -2-->挂了
    (4) type b      hset hash2 name tom
    (5) randomkey
    (6) rename a d      renamenx a d(所有nx结尾的都需要判断是否存在键值对)
  
3.Redis数据结构-string
    (1) setex c 100 c   psetex d 10000 d(ms expire)
    (2) getrange word 0 2   [0,2]
        getset a aa (更新值并返回更新前的旧职)
    (3) mset a1 1 a2 2 a3 3     mget a1 a2 a3
    (4) setnx a a            msetnx a1 1 a2 2 a3 3
    (5) strlen word
    (6) set 1 1     get 1   
        incr 1  incrby 1 100
    (7) decr 1  decrby 1 100   
    (8) append 1 app
    
4.Redis数据结构-hash
    (1) hset map1 name jim      hget map1 name      type    exists
    (2) hgetall map1 name age
    (3) hdel map1 name
    (4) hsetnx map1 name

5.Redis数据结构-list
    (1) lpush

1.Decompiler反编译
2.环境隔离打包、发布测试
