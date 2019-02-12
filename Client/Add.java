package Client;

import Controller.dbOperate;
import Database.dbUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

/**
 * @Author Fisher
 * @Date 2019/1/19 22:56
 **/
public class Add {
    public JPanel AddPanel;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JButton add;
    private JButton reset;
    private dbUser user;

    private dbOperate db = null;

    public Add(dbUser obj) {
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
                if (textField1.getText().equals("") || comboBox1.getSelectedIndex()==0) {
                    JOptionPane.showMessageDialog(null, "用户名或部门不能为空", "失败", JOptionPane.PLAIN_MESSAGE);
                } else {
                    if (user.getUUID()!=null) {
                        dbUser newuser = new dbUser();
                        newuser.setUUID(user.getUUID());
                        newuser.setDisable(user.getDisable());
                        newuser.setUser_name(textField1.getText());
                        newuser.setDepartment(comboBox1.getSelectedItem().toString());
                        if (db.update(user, newuser)) {
                            JOptionPane.showMessageDialog(null, "修改成功", "成功", JOptionPane.PLAIN_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "修改失败", "失败", JOptionPane.PLAIN_MESSAGE);
                        }

                    } else {
                        user.setUUID(UUID.randomUUID().toString());
                        user.setUser_name(textField1.getText());
                        user.setDepartment(comboBox1.getSelectedItem().toString());
                        user.setDisable(0);
                        if (!db.Insert(user)) {
                            JOptionPane.showMessageDialog(null, "添加成功", "成功", JOptionPane.PLAIN_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "添加失败", "失败", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }

            }
        });
    }

    public void setDb(dbOperate db) {
        this.db = db;
    }
}
