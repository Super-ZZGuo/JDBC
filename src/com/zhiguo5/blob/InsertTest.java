package com.zhiguo5.blob;/*
@date 2021/6/13 - 7:28 下午
*/

import com.zhiguo2.util.JDBCutils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 使用prepareStament实现批量数据操作
 * 主要指的是批量插入
 */
public class InsertTest {
    //直接写终极方案

    //设置连接不允许自动提交数据
    @Test
    public void testInsertFinal() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {

            long start = System.currentTimeMillis();

            conn = JDBCutils.getConnection();

            //设置不允许自动提交数据
            conn.setAutoCommit(false);

            String sql = "insert into goods(name)values(?)";
            ps = conn.prepareStatement(sql);
            for(int i = 1;i <= 1000000;i++){
                ps.setObject(1, "name_" + i);

                //1."攒"sql
                ps.addBatch();

                if(i % 500 == 0){
                    //2.执行batch
                    ps.executeBatch();

                    //3.清空batch
                    ps.clearBatch();
                }

            }

            //提交数据
            conn.commit();

            long end = System.currentTimeMillis();

            System.out.println("花费的时间为：" + (end - start));//20000:83065 -- 565
        } catch (Exception e) {								//1000000:16086 -- 5114
            e.printStackTrace();
        }finally{
            JDBCutils.closeResource(conn, ps);

        }

    }
}
