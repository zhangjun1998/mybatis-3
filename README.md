简介
============================
这里是 [『香蕉大魔王』](https://github.com/zhangjun1998) 的 Caffeine 源码解析，[原始项目地址](https://github.com/zhangjun1998/mybatis-3) 在这。
技术暂时还比较拙劣，可能有很多理解不到位或有出入的地方，所以源码注释仅供参考哈，如有错误还请指正，错误很多的话就别说了，请把一些优秀的博客地址贴给我，我爱学习。

debug入口
============================
debug入口在```com.zjcoding.test.DebugTest```中

一些说明放在了```com.zjcoding.package-info```中

方法执行主要流程
============================
1. 配置扫描创建Configuration，创建SqlSessionFactory
2. 注册Mapper到MapperRegistry
3. SqlSessionFactory#openSession()，创建SqlSession执行方法
4. SqlSession#getMapper()获取Mapper的动态代理对象MapperProxy
5. MapperProxy#invoke()方法拦截，Mapper所有方法都会进入这里统一处理
6. MapperMethod#execute()，对Mapper方法的封装调用
7. SqlSession#selectOne()，最终还是交还给SqlSession执行，SqlSession又会调用Executor执行
8. CachingExecutor(二级缓存)，开启了二级缓存时会使用CachingExecutor对实际的Executor做一层包装(装饰器模式)，方便实现二级缓存
9. delegateExecutor#query()，由CachingExecutor调用实际的代理Executor执行
10. 先走一级缓存localCache查询，没有命中时走Executor#queryFromDatabase()查询DB
11. Executor#doQuery()，获取StatementHandler，创建Statement，调用StatementHandler#query()执行
12. StatementHandler#query()，调用PreparedStatement#execute()执行语句，使用ResultSetHandler处理返回的结果集
13. PreparedStatement#execute()
14. ResultHandler#handleResultSets()
15. 结束 

一级缓存和二级缓存
============================

二级缓存
----------------------------
+ 二级缓存，namespace(Mapper)级别的缓存，多个SqlSession之间共享
+ 采用TransactionalCacheManager来对事务缓存进行管理，保证不同SqlSession在执行事务时不会因为缓存影响事务的隔离级别
+ MyBatis的二级缓存相对于一级缓存来说，实现了SqlSession之间缓存数据的共享，同时粒度更加的细，能够到namespace级别，通过Cache接口实现类不同的组合，对Cache的可控性也更强。
+ 由于MyBatis是基于Mapper的DML操作来清理缓存的，因此MyBatis在多表查询时极大可能会出现脏数据，有设计上的缺陷，安全使用二级缓存的条件比较苛刻。
+ 在分布式环境下，由于默认的MyBatis Cache实现都是基于本地的，分布式环境下必然会出现读取到脏数据，需要使用集中式缓存将MyBatis的Cache接口实现，有一定的开发成本，直接使用Redis、Memcached等分布式缓存可能成本更低，安全性也更高。
+ 综上，二级缓存的使用环境太苛刻，没有特殊需要可以直接禁用二级缓存

一级缓存
----------------------------
+ 一级缓存，默认是Session级别的缓存(也可配置为Statement级别)，作用域在单个SqlSession中，每个SqlSession会持有一个Executor作为实际的执行器
+ 一级缓存的目的是在单个SqlSession中执行有重复的查询语句时直接从缓存取出，不走DB，提高查询速度

其它
============================
**联系方式：**

+ 邮箱：zhangjun_java@163.com
+ 微信：rzy-zj

如要联系我请备注来意，好，祝大家技术进步，生活快乐，希望每个人都能实现 work-life balance。



---------------------------分割线-----------------------
------------------------------------



MyBatis SQL Mapper Framework for Java
=====================================

[![Build Status](https://travis-ci.org/mybatis/mybatis-3.svg?branch=master)](https://travis-ci.org/mybatis/mybatis-3)
[![Coverage Status](https://coveralls.io/repos/mybatis/mybatis-3/badge.svg?branch=master&service=github)](https://coveralls.io/github/mybatis/mybatis-3?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/56199c04a193340f320005d3/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56199c04a193340f320005d3)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Stack Overflow](http://img.shields.io/:stack%20overflow-mybatis-brightgreen.svg)](http://stackoverflow.com/questions/tagged/mybatis)
[![Project Stats](https://www.openhub.net/p/mybatis/widgets/project_thin_badge.gif)](https://www.openhub.net/p/mybatis)

![mybatis](http://mybatis.github.io/images/mybatis-logo.png)

The MyBatis SQL mapper framework makes it easier to use a relational database with object-oriented applications.
MyBatis couples objects with stored procedures or SQL statements using a XML descriptor or annotations.
Simplicity is the biggest advantage of the MyBatis data mapper over object relational mapping tools.

Essentials
----------

* [See the docs](http://mybatis.github.io/mybatis-3)
* [Download Latest](https://github.com/mybatis/mybatis-3/releases)
* [Download Snapshot](https://oss.sonatype.org/content/repositories/snapshots/org/mybatis/mybatis/)
