
package dao;

import domain.Lecturer;
import domain.Staff;
import domain.University;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class StaffDao extends GenericDao<Staff>{
    public List<Staff> findByUniversity(University university){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Staff a WHERE a.university = :x");
        q.setParameter("x", university);
        List<Staff> u = q.list();
        s.close();
        return u;
    }
}
