package com.zhiguo.connection;/*
@date 2021/6/13 - 12:53 下午
*/

import org.junit.Test;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
    @Test
    public void testConnection1() throws SQLException{

        Driver driver = new com.mysql.jdbc.Driver();

        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8";
        Properties info = new Properties();
        //用户名和密码封装在Properties内
        info.setProperty("user","root");
        info.setProperty("password","1233211234567");

        Connection conn = driver.connect(url,info);

        System.out.println(conn);

    }

    @Test
    public void ConnectionFinal () throws Exception{

        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

        Properties pros = new Properties();
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");

        Class.forName(driverClass);

        Connection conn = DriverManager.getConnection(url, user, password);

        System.out.println(conn);
    }

}
