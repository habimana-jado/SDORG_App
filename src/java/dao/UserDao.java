
package dao;

import common.PassCode;
import domain.EUserType;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class UserDao extends GenericDao<User>{
    
    public Boolean usernameExist(String username){
        Session s=HibernateUtil.getSessionFactory().openSession();
        Query q=s.createQuery("select CASE WHEN (COUNT(a) > 0) THEN true else false end from User a where a.username=:v ");
        q.setParameter("v", username);
        Boolean l=(Boolean) q.uniqueResult();
        return l;
    }
    public List<User> login(String u,String password){
        Session s=HibernateUtil.getSessionFactory().openSession();
        Query q=s.createQuery("select a from User a where a.username=:v and a.password=:p");
        q.setParameter("v", u);
        q.setParameter("p", password);
        List<User> l=q.list();
        return l;
    } 
    
    public List<User> loginencrypt(String u, String pass) throws Exception {

        Session s = HibernateUtil.getSessionFactory().openSession();
        List<User> list = new ArrayList<>();

        List<User> users = new UserDao().findAll(User.class);
        String z = "";
        for (User us : users) {
            if (us.getUsername().matches(u)) {
                if ((new PassCode().decrypt(us.getPassword())).matches(pass)) {
                    list.add(us);
                }
            }

        }

        s.close();
        return list;

    }
    
    public User findByUsername(String username){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM User a WHERE a.username = :x");
        q.setParameter("x", username);
        User u = (User) q.uniqueResult();
        s.close();
        return u;
    }
    
    public List<User> findByAccess(EUserType access){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM User a WHERE a.userType = :x");
        q.setParameter("x", access);
        List<User> u = q.list();
        s.close();
        return u;
    }
}
