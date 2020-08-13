
package dao;

import domain.Faculty;
import domain.Lecturer;
import domain.University;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class LecturerDao extends GenericDao<Lecturer>{
    public List<Lecturer> findByUniversity(University university){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Lecturer a WHERE a.faculty.university = :x");
        q.setParameter("x", university);
        List<Lecturer> u = q.list();
        s.close();
        return u;
    }
}
