package com.skychat.server.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//sqlSessionFactory -->sqlSession
@Component
public class MybatisUtils {

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private DataSourceConfigUtils dataSourceConfigUtils;

    //既然有了sqlSessionFactory,顾名思义，我们就可以从中获得sqlSession的实例了
    //sqlSession 完全包含了面向数据库执行SQL命令所需的所有方法
    public SqlSession getSqlSession(){
        String resource = "baits/mybaits-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, buildInitProperties());
        return sqlSessionFactory.openSession();
    }

    private Properties buildInitProperties(){
        Properties properties = new Properties();
        properties.put("driver", dataSourceConfigUtils.getDriver());
        properties.put("url", dataSourceConfigUtils.getUrl());
        properties.put("username", dataSourceConfigUtils.getUsername());
        properties.put("password", dataSourceConfigUtils.getPassword());
        return properties;
    }

}