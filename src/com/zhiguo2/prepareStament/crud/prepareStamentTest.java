package com.zhiguo2.prepareStament.crud;/*
@date 2021/6/13 - 2:35 下午
*/

import com.zhiguo.connection.ConnectionTest;
import com.zhiguo2.util.JDBCutils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class prepareStamentTest {

    //修改记录
    @Test
    public void testUpdate(String sql,Object ...args){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //获取连接
            conn = JDBCutils.getConnection();
            //预编译sql语句，返回PreparedStatement实例
            ps = conn.prepareStatement(sql);
            //填充占位符？
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //执行
            ps.execute();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            //资源关闭
            JDBCutils.closeResource(conn,ps);
        }
    }
    //向Customer中添加一条数据
    @Test
    public void testInsert() throws Exception {
        //从配置文件里读取信息
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

        Properties pros = new Properties();
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");

        Class.forName(driverClass);

        //获取连接
        Connection conn = DriverManager.getConnection(url, user, password);

        //预编译sql语句，返回PreparedStatement实例
        String sql = "insert into customers(name,email,birth)values(?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        //填充占位符？
        ps.setString(1,"黄志国");
        ps.setString(2,"1378412051@qq.com");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf.parse("2000-11-25");
        ps.setDate(3,new java.sql.Date(date.getTime()));

        //执行
        ps.execute();

        //资源关闭
        ps.close();
        conn.close();

    }
}
