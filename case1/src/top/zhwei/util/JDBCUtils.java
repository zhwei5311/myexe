package top.zhwei.util;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC工具类，使用Druid连接池
 */
public class JDBCUtils {
    private static DataSource dataSource;

    static {
        //加载配置文件
        Properties properties = new Properties();
        //使用ClassLoader加载配置文件，获取字节输入流
        InputStream inputStream = JDBCUtils.class.getClassLoader().getResourceAsStream("duruid.properties");
        try {
            properties.load(inputStream);
            //初始化连接池对象
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接池对象
     */
    public static DataSource getDataSource() {
        return dataSource;
    }

    /**
     * 获取连接Connection对象
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
