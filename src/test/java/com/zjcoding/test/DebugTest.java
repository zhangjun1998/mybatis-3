package com.zjcoding.test;

import com.zjcoding.entity.AuthorEntity;
import com.zjcoding.mapper.AuthorMapper;
import org.apache.ibatis.BaseDataTest;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.List;

/**
 * debug入口
 *
 * @author ZhangJun
 * @date 2023/1/4 09:47
 */
public class DebugTest {

    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void setup() throws Exception {
        // 初始化内置数据源
        DataSource dataSource = BaseDataTest.createBlogDataSource();
        // 初始化配置
        // 这里直接创建Configuration对象，省去了解析器将xml配置解析为Configuration的过程
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("Production", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.setLazyLoadingEnabled(true);
        configuration.setUseActualParamName(false); // to test legacy style reference (#{0} #{1})
        configuration.addMapper(AuthorMapper.class);
        // 构造SqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    /**
     * 1. 根据SqlSessionFactory获取SqlSession
     * 2. 使用SqlSession获取Mapper
     * 3. 执行方法调用
     * 4. 提交事务
     */
    @Test
    public void justGo() {
        // 1. 根据SqlSessionFactory获取SqlSession
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {

            // 2. 使用SqlSession获取Mapper
            // 这一步返回的是Mapper接口的代理对象MapperProxy，通过动态代理来实现对方法的增强
            AuthorMapper mapper = sqlSession.getMapper(AuthorMapper.class);

            // 3. 执行方法调用
            // 方法调用会进入MapperProxy对象的invoke()方法
            List<AuthorEntity> authorList = mapper.listAll();
            AuthorEntity author = mapper.findById(101L);
            System.out.println(author);
            System.out.println(authorList);

            // 4. 提交事务
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}