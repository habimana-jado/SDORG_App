
package dao;

import domain.Accusation;
import domain.Department;
import domain.EMovementStatus;
import domain.Student;
import domain.University;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class AccusationDao extends GenericDao<Accusation>{
    public List<Accusation> findByStudent(Student st){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Accusation a WHERE a.movement.device.person = :x");
        q.setParameter("x", st);
        List<Accusation> u = q.list();
        s.close();
        return u;
    }
    
    public Long findTotalByUniversityAndMovementStatus(University x, String status){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT COUNT(a.accusationId) FROM Accusation a WHERE a.movement.university = :x AND a.status = :y");
        q.setParameter("x", x);
        q.setParameter("y", status);
        Long list = (Long) q.uniqueResult();
        s.close();
        return list;
    }
}
