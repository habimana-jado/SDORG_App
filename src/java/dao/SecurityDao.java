
package dao;

import domain.Security;
import domain.Staff;
import domain.University;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class SecurityDao extends GenericDao<Security>{
    public List<Security> findByUniversity(University university){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Security a WHERE a.university = :x");
        q.setParameter("x", university);
        List<Security> u = q.list();
        s.close();
        return u;
    }
}
