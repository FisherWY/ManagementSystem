package Database;

/**
 * @Author Fisher
 * @Date 2019/1/18 20:12
 *
 * 员工类
 **/
public class dbUser {
    //用户信息唯一标识码
    private String UUID;
    //用户姓名
    private String User_name;
    //用户部门
    private String Department;
    //是否启用
    private int Disable;

    public dbUser() {

    }

    public dbUser(String UUID, String user_name, String department) {
        this.UUID = UUID;
        User_name = user_name;
        Department = department;
    }

    @Override
    public String toString() {
        return User_name;
    }

    //get和set方法

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public int getDisable() {
        return Disable;
    }

    public void setDisable(int disable) {
        Disable = disable;
    }
}
