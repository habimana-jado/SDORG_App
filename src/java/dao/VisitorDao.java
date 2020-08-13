
package dao;

import domain.DeviceImage;
import domain.Lecturer;
import domain.University;
import domain.Visitor;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class VisitorDao extends GenericDao<Visitor>{
     
    public List<Visitor> findByUniversity(University x){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Visitor a WHERE a.university = :x");
        q.setParameter("x", x);
        List<Visitor> list = q.list();
        s.close();
        return list;
    }
}
