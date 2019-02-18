package Database;

import Utils.MD5byCHF;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Fisher
 * @Date 2019/1/18 20:11
 *
 * 管理员类
 **/
public class dbAuth {
    //账户名
    private String User;
    //密码，形式为加密的md5
    private String Password;
    //最后一次登录的时间
    private String Last_login;
    //账户的权限等级
    private int Level;
    //账户是否启用
    private int Disable;

    public dbAuth() {

    }

    public dbAuth(String user, String password, String last_login, int level, int disable) {
        User = user;
        Password = password;
        Last_login = last_login;
        Level = level;
        Disable = disable;
    }

    //更新最后登录时间
    public boolean updateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Last_login = simpleDateFormat.format(new Date());
            return true;
        } catch (Exception e) {
            System.out.println("更新最后的登录时间时出现了错误");
            e.printStackTrace();
            return false;
        }
    }

    //将密码以md5方法加密
    public boolean encryptPsw() {
        MD5byCHF md5byCHF = new MD5byCHF();
        try {
            Password = md5byCHF.encryptMD5(Password);
            return true;
        } catch (Exception e) {
            System.out.println("加密密码时出现了错误");
            e.printStackTrace();
            return false;
        }
    }

    //重写toString方法
    @Override
    public String toString() {
        return User + " Level:" + Level + " Last_login:" + Last_login;
    }

    //以上属性的get和set方法

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getLast_login() {
        return Last_login;
    }

    public void setLast_login(String last_login) {
        Last_login = last_login;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public int getDisable() {
        return Disable;
    }

    public void setDisable(int disable) {
        Disable = disable;
    }
}
