package Client;

import Controller.dbOperate;
import Database.dbAuth;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Author Fisher
 * @Date 2019/1/19 16:48
 *
 * 用户登录窗口，程序运行的初始界面
 **/
public class Auth extends JFrame{
    private JButton regist;
    private JButton login;
    private JButton exit;
    private JLabel AccountLabel;
    private JLabel PswLabel;
    private JTextField account;
    private JPasswordField password;
    private JPanel AuthPanel;
    private JFrame frame;

    private dbOperate db = null;

    public void setDb(dbOperate db) {
        this.db = db;
    }

    public Auth() {
        //注册按钮
        regist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Regist regist = new Regist();
                regist.setDb(db);
                regist.run();
                frame.dispose();
            }
        });
        //退出按钮
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        //登录按钮
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (account.getText().length()!=0 && password.getText().length()!=0) {
                    //创建一个登录对象
                    dbAuth user = new dbAuth();
                    user.setUser(account.getText());
                    user.setPassword(password.getText());
                    user.setDisable(0);
                    user.encryptPsw();

                    //清空输入框
                    password.setText("");

                    //登录
                    if (db.login(user)) {
                        //登陆成功，拉起主界面窗口
                        mSystem mSystem = new mSystem(db);
                        mSystem.run();
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "用户名或密码错误", "登陆失败", JOptionPane.PLAIN_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "用户名或密码为空", "输入错误", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
    }

    public void run() {
        frame = new JFrame("登录");
        frame.setContentPane(this.AuthPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(500,500);
        frame.pack();
        frame.setVisible(true);
    }
}
