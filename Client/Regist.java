package Client;

import Controller.dbOperate;
import Database.dbAuth;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Author Fisher
 * @Date 2019/1/19 20:55
 **/
public class Regist extends JFrame{
    private JButton exit1;
    private JButton regist;
    private JTextField account;
    private JPasswordField password;
    private JSlider slider1;
    private JPanel RegistPanel;
    private JFrame frame;

    private dbOperate db = null;
    private dbAuth admin = null;

    public void setDb(dbOperate db) {
        this.db = db;
    }

    public void setAdmin(dbAuth admin) {
        this.admin = admin;
    }

    public Regist() {
        //注册按钮
        regist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.lang.System.out.println("注册按钮");
                if (account.getText().length()!=0 && password.getText().length()!=0) {

                    //创建一个新的账号对象
                    dbAuth user = new dbAuth();
                    user.setUser(account.getText());
                    user.setPassword(password.getText());
                    user.setDisable(0);
                    user.setLevel(slider1.getValue());
                    user.encryptPsw();

                    //清空输入框
                    account.setText("");
                    password.setText("");
                    slider1.setValue(1);

                    if (!db.regist(user)) {
                        JOptionPane.showMessageDialog(null, "注册成功", "成功", JOptionPane.PLAIN_MESSAGE);
                        Auth auth = new Auth();
                        auth.setDb(db);
                        auth.run();
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "注册失败", "错误", JOptionPane.PLAIN_MESSAGE);
//                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "用户名或密码为空", "输入错误", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        //退出按钮
        exit1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Auth auth = new Auth();
                auth.setDb(db);
                auth.run();
                frame.dispose();
            }
        });
    }

    public void run() {
        frame = new JFrame("注册");
        frame.setContentPane(this.RegistPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(500,500);
        frame.pack();
        frame.setVisible(true);
    }
}
