package Client;

import Controller.dbOperate;
import Database.dbAuth;
import Database.dbUser;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
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
    private JButton refresh;
    private JTextField searchField;

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
        //添加按钮
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Add add = new Add(new dbUser());
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
        //刷新按钮
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                init();
                information.updateUI();
            }
        });
        //修改
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) information.getLastSelectedPathComponent();
                System.out.println("修改的节点：" + information.getSelectionPath());
                if (node.getUserObject() instanceof dbUser) {
                    dbUser obj = (dbUser) node.getUserObject();
                    Add add = new Add(obj);
                    add.setDb(db);
                    JFrame frame = new JFrame("修改");
                    frame.setContentPane(add.AddPanel);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setLocation(500,500);
                    frame.setSize(500,400);
                    frame.pack();
                    frame.setVisible(true);
                }
            }
        });
        //删除
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) information.getLastSelectedPathComponent();
                System.out.println("删除的节点：" + information.getSelectionPath());
                if (node.getUserObject() instanceof dbUser) {
                    dbUser obj = (dbUser) node.getUserObject();
                    if (db.delete(obj)) {
                        JOptionPane.showMessageDialog(null, "删除成功", "成功", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "删除失败", "失败", JOptionPane.PLAIN_MESSAGE);
                    }

                }
            }
        });
        //搜索
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findInTree(searchField.getText());
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

//        information.clearSelection();

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("员工信息");
        rootNode.removeAllChildren();

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


        LinkedList<Object> result = db.Select("user", "Disable", "0");

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

    private void findInTree(String str) {
        Object root = information.getModel().getRoot();
        TreePath treePath = new TreePath(root);
        treePath = findInPath(treePath, str);
        if (treePath != null) {
            information.setSelectionPath(treePath);
            information.scrollPathToVisible(treePath);
        }
    }

    private TreePath findInPath(TreePath treePath, String str) {
        Object object = treePath.getLastPathComponent();
        if (object == null) {
            return null;
        }

        String value = object.toString();
        if (str.equals(value)) {
            return treePath;
        } else {
            TreeModel model = information.getModel();
            int n = model.getChildCount(object);
            for (int i = 0; i < n; i++) {
                Object child = model.getChild(object, i);
                TreePath path = treePath.pathByAddingChild(child);

                path = findInPath(path, str);
                if (path != null) {
                    return path;
                }
            }
            return null;
        }
    }
}
