package Controller;

import Database.*;

import Utils.ConnectionPool;

import java.sql.*;
import java.util.LinkedList;

/**
 * @Author Fisher
 * @Date 2019/1/18 20:25
 **/
public class dbOperate {
    //数据库用户名
    private String user;
    //数据库密码
    private String psw;
    //数据库驱动
    private String driver;
    //数据库url
    private String url;
    //连接池
    private ConnectionPool db = null;
    //数据库连接
    private Connection connection;
    //数据库SQL语句
    private Statement sql;
    //结果集
    private ResultSet resultSet;
    private LinkedList<Object> result = new LinkedList<Object>();
    private dbAuth admin;

    public dbOperate() {
        openConnection();
    }

    public dbOperate(String user, String psw, String driver, String ip, String port, String db) {
        this.user = user;
        this.psw = psw;
        this.driver = driver;
        this.url = "jdbc:mysql://" + ip + ":" + port + "/" + db + "?serverTimezone=GMT%2b8";
        openConnection();
    }

    //打开连接池，返回true表示连接池打开成功，false表示连接池打开失败
    public boolean openConnection() {
        try {
            db = new ConnectionPool(driver, url, user, psw);
            db.createPool();
            db.setTestTable("auth");
            return true;
        } catch (Exception e) {
            System.out.println("创建数据库连接的时候出现了错误");
            e.printStackTrace();
            return false;
        }
    }

    //登录模块
    public boolean login(dbAuth dbobj) {
        try {
            conditionSelect("auth","User", dbobj.getUser());

            //如果用户存在且没有被禁用
            while (result.size()>0) {
                Object obj = result.getFirst();
                if (obj instanceof dbAuth) {
                    String psw = ((dbAuth) obj).getPassword();
                    int disable = ((dbAuth) obj).getDisable();
                    if (dbobj.getPassword().equals(psw) && disable==0) {
                        admin = (dbAuth) obj;
                        ((dbAuth) obj).updateTime();
                        Update("auth", "Last_login", ((dbAuth) obj).getLast_login(), "User", ((dbAuth) obj).getUser());
                        return true;
                    }
                }
                result.removeFirst();
            }
        } catch (Exception e) {
            System.out.println("登陆失败");
            e.printStackTrace();
        }
        return false;
    }

    //注册模块
    public boolean regist(Object dbobj) {
        if (Insert(dbobj)) {
            return true;
        } else {
            return false;
        }
    }

    //更新模块
    public boolean update(Object dbobj, Object newdbobj) {
        if (dbobj instanceof dbAuth && newdbobj instanceof dbAuth) {
            if (!((dbAuth) dbobj).getPassword().equals(((dbAuth) newdbobj).getPassword())) {
                Update("auth","Password", ((dbAuth) newdbobj).getPassword(),"User", ((dbAuth) dbobj).getUser());
            }
            if (((dbAuth) dbobj).getLevel() != ((dbAuth) newdbobj).getLevel()) {
                Update("auth", "Level", String.valueOf(((dbAuth) newdbobj).getLevel()), "User", ((dbAuth) dbobj).getUser());
            }
            if (((dbAuth) dbobj).getDisable() != ((dbAuth) newdbobj).getDisable()) {
                Update("auth", "Disable", String.valueOf(((dbAuth) newdbobj).getDisable()), "User", ((dbAuth) newdbobj).getUser());
            }
            return true;
        } else if (dbobj instanceof dbUser && newdbobj instanceof dbUser) {
            if (!((dbUser) dbobj).getUser_name().equals(((dbUser) newdbobj).getUser_name())) {
                Update("user", "User_name", ((dbUser) newdbobj).getUser_name(), "UUID", ((dbUser) dbobj).getUUID());
            }
            if (!((dbUser) dbobj).getDepartment().equals(((dbUser) newdbobj).getDepartment())) {
                Update("user", "Department", ((dbUser) newdbobj).getDepartment(), "UUID", ((dbUser) dbobj).getUUID());
            }
            return true;
        } else {
            return false;
        }
    }

    //删除模块
    public boolean delete(Object obj) {
        if (obj instanceof dbAuth) {
            return Update("auth", "Disable", "1", "User", ((dbAuth) obj).getUser());
        } else if (obj instanceof dbUser) {
            return Update("user", "Disable", "1", "UUID", ((dbUser) obj).getUUID());
        } else {
            return false;
        }
    }

    //条件查询
    public LinkedList<Object> Select(String table, String column, String condition) {
        if (conditionSelect(table,column,condition)) {
            return result;
        } else {
            return null;
        }
    }

    //查询全表模块
    public LinkedList<Object> SelectAll(String table) {
        if (allSelect(table)) {
            return result;
        } else {
            return null;
        }
    }

    /*
    增删改查的4个模板
     */

    //条件查询模板
    private boolean conditionSelect(String table, String column, String condition) {
        try {
            connection = db.getConnection();
            sql = connection.createStatement();

//            sql = connection.prepareStatement("select * from "+table+" where "+"? = ?");
//            由于防止sql注入式攻击，表名不可以做动态绑定
//            ((PreparedStatement) sql).setString(1, table);
//            ((PreparedStatement) sql).setString(1, column);
//            ((PreparedStatement) sql).setString(2, condition);
//            if (((PreparedStatement) sql).execute()) {
//                resultSet = sql.getResultSet();
//            }

            String query = "select * from "+table+" where "+ column + " = '" + condition + "'";
            resultSet = sql.executeQuery(query);

            result.clear();
            if (table.equals("auth")) {
                while (resultSet.next()) {
                    dbAuth obj = new dbAuth();
                    obj.setUser(resultSet.getString(1));
                    obj.setPassword(resultSet.getString(2));
                    obj.setLast_login(resultSet.getString(3));
                    obj.setLevel(resultSet.getInt(4));
                    obj.setDisable(resultSet.getInt(5));
                    result.addLast(obj);
                }
            } else if (table.equals("user")) {
                while (resultSet.next()) {
                    dbUser obj = new dbUser();
                    obj.setUUID(resultSet.getString(1));
                    obj.setUser_name(resultSet.getString(2));
                    obj.setDepartment(resultSet.getString(3));
                    result.addLast(obj);
                }
            } else {
                throw new RuntimeException("无该表");
            }

            connection.close();
            return true;
        } catch (Exception e) {
            System.out.println("查询失败");
            e.printStackTrace();
            return false;
        }
    }

    //全表查询模板
    private boolean allSelect(String table) {
        try {
            connection = db.getConnection();
            sql = connection.createStatement();

            String query = "select * from " + table;
            resultSet = sql.executeQuery(query);

            result.clear();
            if (table.equals("auth")) {
                while (resultSet.next()) {
                    dbAuth obj = new dbAuth();
                    obj.setUser(resultSet.getString(1));
                    obj.setPassword(resultSet.getString(2));
                    obj.setLast_login(resultSet.getString(3));
                    obj.setLevel(resultSet.getInt(4));
                    obj.setDisable(resultSet.getInt(5));
                    result.addLast(obj);
                }
            } else if (table.equals("user")) {
                while (resultSet.next()) {
                    dbUser obj = new dbUser();
                    obj.setUUID(resultSet.getString(1));
                    obj.setUser_name(resultSet.getString(2));
                    obj.setDepartment(resultSet.getString(3));
                    result.addLast(obj);
                }
            } else {
                throw new RuntimeException("无该表");
            }

            connection.close();
            return true;
        } catch (Exception e) {
            System.out.println("查询失败");
            e.printStackTrace();
            return false;
        }
    }

    //向表中插入值,type为0是账号密码表，type为1是用户信息表
    public boolean Insert(Object obj) {
        try {
            connection = db.getConnection();
            sql = connection.createStatement();
            if (obj instanceof dbAuth) {
                sql = connection.prepareStatement("insert into auth values (?,?,?,?,?)");
                ((PreparedStatement) sql).setString(1, ((dbAuth) obj).getUser());
                ((PreparedStatement) sql).setString(2, ((dbAuth) obj).getPassword());
                ((PreparedStatement) sql).setString(3, ((dbAuth) obj).getLast_login());
                ((PreparedStatement) sql).setInt(4, ((dbAuth) obj).getLevel());
                ((PreparedStatement) sql).setInt(5, ((dbAuth) obj).getDisable());
                return ((PreparedStatement) sql).execute();
            } else if (obj instanceof dbUser) {
                sql = connection.prepareStatement("insert into user values (?,?,?,?)");
                ((PreparedStatement) sql).setString(1, ((dbUser) obj).getUUID());
                ((PreparedStatement) sql).setString(2, ((dbUser) obj).getUser_name());
                ((PreparedStatement) sql).setString(3, ((dbUser) obj).getDepartment());
                ((PreparedStatement) sql).setInt(4, ((dbUser) obj).getDisable());
                return ((PreparedStatement) sql).execute();
            } else {
                throw new RuntimeException("数据库中没有这个表");
            }
        } catch (Exception e) {
            System.out.println("向数据库中插入数据时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    //更新表模板
    private boolean Update(String table, String column, String newvalue, String key_column, String key) {
        try {
            connection = db.getConnection();
            sql = connection.createStatement();

            sql = connection.prepareStatement("update "+table+" set "+column+" = ? where "+key_column+" = ?");
            ((PreparedStatement) sql).setString(1, newvalue);
            ((PreparedStatement) sql).setString(2, key);

            ((PreparedStatement) sql).executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("更新失败");
            e.printStackTrace();
        }
        return false;
    }

    //删除数据模板
    private boolean Delete(String table, String key, String value) {
        try {
            connection = db.getConnection();
            sql = connection.createStatement();

            String query = "delete from " + table + " where '" + key + "' = '" + value +"'";
            sql.execute(query);

            return true;
        } catch (Exception e) {
            System.out.println("删除数据失败");
            e.printStackTrace();
        }
        return false;
    }

    //获取查询结果
    public LinkedList<Object> getResult() {
        return result;
    }

    //获取当前管理员身份
    public dbAuth getAdmin() {
        return admin;
    }
}
