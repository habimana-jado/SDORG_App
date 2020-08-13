
package dao;

import domain.EMovementStatus;
import domain.Movement;
import domain.University;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class MovementDao extends GenericDao<Movement>{
    public List<Movement> findByUniversity(University x, EMovementStatus y){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Movement a WHERE a.university = :x AND a.movementStatus = :y");
        q.setParameter("x", x);
        q.setParameter("y", y);
        List<Movement> list = q.list();
        s.close();
        return list;
    }
}
