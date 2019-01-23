package Controller;

import Utils.ConnectionPool;

/**
 * @Author Fisher
 * @Date 2019/1/17 20:42
 **/
public class dbConnection {
    private String user = "fisher";
    private String psw = "12345678";
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=GMT%2b8";
    //连接池
    private ConnectionPool pool = null;
    private boolean status = false;

    dbConnection(String user, String psw, String ip, String port, String db) {
        this.user = user;
        this.psw = psw;
        this.url = "jdbc:mysql://" + ip + ":" + port + "/" + db + "?serverTimezone=GMT%2b8";
        pool = new ConnectionPool(driver,url,user,psw);

        try {
            pool.createPool();
            pool.setTestTable("auth");
        } catch (Exception e) {
            System.out.println("创建连接池的时候出现了错误:" + e.getMessage());
        }
    }

    dbConnection() {
        pool = new ConnectionPool(driver,url,user,psw);

        try {
            pool.createPool();;
            pool.setTestTable("auth");
        } catch (Exception e) {
            System.out.println("创建连接池的时候出现了错误:" + e.getMessage());
        }
    }



}
