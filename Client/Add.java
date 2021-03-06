package Client;

import Controller.dbOperate;
import Database.dbAuth;
import Database.dbUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

/**
 * @Author Fisher
 * @Date 2019/1/19 22:56
 *
 * 添加、修改员工信息窗口
 **/
public class Add {
    private JPanel AddPanel;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JButton add;
    private JButton reset;
    private JButton back;
    private JFrame frame;

    //管理员和员工的信息
    private dbUser user;
    private dbAuth admin = null;

    //数据库连接
    private dbOperate db = null;

    public Add(dbUser obj) {
        //设置员工信息
        this.user = obj;
        if (obj.getUUID()!=null) {
            textField1.setText(obj.getUser_name());
            comboBox1.setSelectedItem(obj.getDepartment());
        }
        //重置按钮
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
                comboBox1.setSelectedIndex(0);
            }
        });
        //添加按钮
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //如果输入框中存在空值
                if (textField1.getText().equals("") || comboBox1.getSelectedIndex()==0) {
                    JOptionPane.showMessageDialog(null, "用户名或部门不能为空", "失败", JOptionPane.PLAIN_MESSAGE);
                } else { //检查输入成功
                    System.out.println("添加的信息：" + textField1.getText() + "  " + comboBox1.getSelectedItem().toString());
                    //修改员工信息
                    if (user.getUUID()!=null) {
                        dbUser newuser = new dbUser();
                        newuser.setUUID(user.getUUID());
                        newuser.setDisable(user.getDisable());
                        newuser.setUser_name(textField1.getText());
                        newuser.setDepartment(comboBox1.getSelectedItem().toString());
                        if (db.update(user, newuser)) {
                            JOptionPane.showMessageDialog(null, "修改成功", "成功", JOptionPane.PLAIN_MESSAGE);
                            mSystem mSystem = new mSystem(db);
                            mSystem.run();
                            frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "修改失败", "失败", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else { //添加新员工信息
                        user.setUUID(UUID.randomUUID().toString());
                        user.setUser_name(textField1.getText());
                        user.setDepartment(comboBox1.getSelectedItem().toString());
                        user.setDisable(0);
                        if (!db.Insert(user)) {
                            JOptionPane.showMessageDialog(null, "添加成功", "成功", JOptionPane.PLAIN_MESSAGE);
                            mSystem mSystem = new mSystem(db);
                            mSystem.run();
                            frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "添加失败", "失败", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
            }
        });
        //返回到主管理页面
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mSystem mSystem = new mSystem(db);
                mSystem.run();
                frame.dispose();
            }
        });
    }

    public void setDb(dbOperate db) {
        this.db = db;
    }

    public void setAdmin(dbAuth admin) {
        this.admin = admin;
    }

    public void run(String tittle) {
        frame = new JFrame(tittle);
        frame.setContentPane(this.AddPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(500,500);
        frame.setSize(500,400);
        frame.pack();
        frame.setVisible(true);
    }
}
