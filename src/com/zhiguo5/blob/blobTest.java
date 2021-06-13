package com.zhiguo5.blob;/*
@date 2021/6/13 - 6:13 下午
*/

import com.zhiguo.bean.Customer;
import com.zhiguo2.util.JDBCutils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;

/**
 * 通过PrepareStament操作Blob类型的数据
 */
public class blobTest {


    //向数据表里插入Blob类型数据
    @Test
    public void testInsert () throws Exception {
        Connection conn = JDBCutils.getConnection();
        String sql = "insert into customers(name,email,birth,photo)values(?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1,"禧羊羊");
        ps.setString(2,"xiyyshapi@qq.com");
        ps.setString(3,"2000-11-07");
        FileInputStream is = new FileInputStream(new File("src/xiyy.jpeg"));
        ps.setBlob(4,is);

        ps.execute();

        JDBCutils.closeResource(conn,ps);

    }

    //读取数据表里Blob类型数据
    @Test
    public void testQuery () throws Exception {


        InputStream is = null;
        FileOutputStream fos = null;
        Connection conn = null;
        PreparedStatement ps = null;

        ResultSet rs = null;
        try {
            conn = JDBCutils.getConnection();
            String sql = "select id,name,email,birth,photo from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 20);
            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Date birth = rs.getDate("birth");

                Customer cust = new Customer(id, name, email, birth);

                System.out.println(cust);

                //将blob字段下载下来，以文件的方式保存在本地
                Blob photo = rs.getBlob("photo");

                is = photo.getBinaryStream();

                fos = new FileOutputStream("xiyy2.jpeg");

                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {

            try{
                if(is != null)
                    is.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            try{
                if(fos != null)
                    fos.close();
            }catch(Exception e){
                e.printStackTrace();
            }

            JDBCutils.closeResource(conn,ps,rs);

        }
    }
}
