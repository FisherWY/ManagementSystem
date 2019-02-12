import Client.Auth;
import Client.mSystem;
import Controller.dbOperate;

import javax.swing.*;

/**
 * @Author Fisher
 * @Date 2019/1/17 20:32
 **/
public class main {
    public static void main(String args[]){
        dbOperate db = new dbOperate();

        Auth auth = new Auth();
        auth.setDb(db);
        JFrame frame = new JFrame("登录");
        frame.setContentPane(auth.AuthPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(500,500);
        frame.pack();
        frame.setVisible(true);

//        mSystem mSystem = new mSystem(db);
//        JFrame frame = new JFrame("登录");
//        frame.setContentPane(mSystem.SystemPanel);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.setLocation(500,500);
//        frame.setSize(500,400);
//        frame.pack();
//        frame.setVisible(true);


//        if (db.openConnection()) {
//            mSystem.out.println("connected");
//
//            dbAuth user = new dbAuth("ddd","asdasda","null",0,0);
//            dbAuth newuser = new dbAuth("aaa", "53245325", "null", 1, 1);

//            user.encryptPsw();

            //更新信息
//            if (db.update(user,newuser)) {
//                mSystem.out.println("Updated");
//            } else {
//                mSystem.out.println("not Updated");
//            }

            //注册账号
//            if (!db.regist(user)) {
//                mSystem.out.println("registed");
//            } else {
//                mSystem.out.println("not registed");
//            }

            //登录
//            if (db.login(user)) {
//                mSystem.out.println("login");
//            } else {
//                mSystem.out.println("not login");
//            }
//
//            //删除
//            if (db.delete(user)) {
//                mSystem.out.println("deleted");
//            } else {
//                mSystem.out.println("not deleted");
//            }



//        } else {
//            mSystem.out.println("not connected");
//        }
    }
}
