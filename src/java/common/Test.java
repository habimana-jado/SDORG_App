
package common;

import dao.UserDao;
import domain.EStatus;
import domain.EUserType;
import domain.User;

public class Test {
    public static void main(String[] args) throws Exception {
//        User u = new User();
//        u.setPassword(new PassCode().encrypt("hec"));
//        u.setStatus(EStatus.ACTIVE);
//        u.setUserType(EUserType.SUPERADMIN);
//        u.setUsername("hec");
//        new UserDao().register(u);

        User u = new UserDao().findByUsername("karenzi");
        System.out.println(new PassCode().decrypt(u.getPassword()));
    }
}
