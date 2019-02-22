import Client.Auth;
import Controller.dbOperate;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @Author Fisher
 * @Date 2019/1/17 20:32
 **/

public class Launcher {
    public static void main(String args[]){
        try {
            //加载配置文件
            URL configPath = ClassLoader.getSystemResource("config.properties");
            System.out.println(configPath.getFile());
            Properties config = new Properties();
            InputStream input = new BufferedInputStream(new FileInputStream(configPath.getPath()));
            config.load(input);

            //读取配置文件
            String user = config.getProperty("user");
            String psw = config.getProperty("psw");
            String driver = config.getProperty("driver");
            String ip = config.getProperty("ip");
            String port = config.getProperty("port");
            String dbname = config.getProperty("dbname");

//            建立数据库连接池，本地运行使用
            dbOperate db = new dbOperate(user, psw, driver, ip, port, dbname);
//            连接云服务器时使用以下连接
//            dbOperate db = new dbOperate("fisher", "12345678", "com.mysql.cj.jdbc.Driver", "47.106.14.214", "3306", "mSystem");

            //装载程序
            Auth auth = new Auth();
            auth.setDb(db);
            auth.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
