
package dao;

import domain.Faculty;
import domain.University;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class FacultyDao extends GenericDao<Faculty>{
    public List<Faculty> findByUniversity(University university){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Faculty a WHERE a.university = :x");
        q.setParameter("x", university);
        List<Faculty> u = q.list();
        s.close();
        return u;
    }
}
