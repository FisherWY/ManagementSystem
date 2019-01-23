package Client;

import Controller.dbOperate;
import Database.dbAuth;
import Database.dbUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * @Author Fisher
 * @Date 2019/1/19 17:17
 **/
public class mSystem extends JFrame{
    public JPanel SystemPanel;
    private JButton exit;
    private JButton edit;
    private JButton delete;
    private JButton add;
    private JButton search;
    private JTree information;

    private dbOperate db = null;
    private dbAuth user = null;

    public mSystem(dbOperate db) {
        this.db = db;

        //退出按钮
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Add add = new Add();
                add.setDb(db);
                JFrame frame = new JFrame("添加");
                frame.setContentPane(add.AddPanel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLocation(500,500);
                frame.setSize(500,400);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public void setDb(dbOperate db) {
        this.db = db;
    }

    public void setUser(dbAuth user) {
        this.user = user;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        //创建树形结构
        init();
    }

    public void init() {
//        String[] department = {"部门A","部门B","部门C","部门D","部门E"};

        information.clearSelection();

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("员工信息");

        DefaultMutableTreeNode departmentNode0 = new DefaultMutableTreeNode("部门A");
        DefaultMutableTreeNode departmentNode1 = new DefaultMutableTreeNode("部门B");
        DefaultMutableTreeNode departmentNode2 = new DefaultMutableTreeNode("部门C");
        DefaultMutableTreeNode departmentNode3 = new DefaultMutableTreeNode("部门D");
        DefaultMutableTreeNode departmentNode4 = new DefaultMutableTreeNode("部门E");

        rootNode.add(departmentNode0);
        rootNode.add(departmentNode1);
        rootNode.add(departmentNode2);
        rootNode.add(departmentNode3);
        rootNode.add(departmentNode4);


        LinkedList<Object> result = db.SelectAll("user");

        while (result.size() != 0) {
            Object obj = result.getFirst();
            if (obj instanceof dbUser) {
                DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(obj);
                switch (((dbUser) obj).getDepartment()) {
                    case "部门A":
                        departmentNode0.add(userNode);
                        break;
                    case "部门B":
                        departmentNode1.add(userNode);
                        break;
                    case "部门C":
                        departmentNode2.add(userNode);
                        break;
                    case "部门D":
                        departmentNode3.add(userNode);
                        break;
                    case "部门E":
                        departmentNode4.add(userNode);
                        break;
                    default:
                        break;
                }
            }
            result.removeFirst();
        }

        information = new JTree(rootNode);
        information.setShowsRootHandles(true);
        information.setEditable(false);
    }
}
