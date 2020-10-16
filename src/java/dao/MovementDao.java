package dao;

import domain.EMovementStatus;
import domain.Movement;
import domain.Student;
import domain.University;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class MovementDao extends GenericDao<Movement> {

    public List<Movement> findByUniversity(University x, EMovementStatus y) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Movement a WHERE a.university = :x AND a.movementStatus = :y");
        q.setParameter("x", x);
        q.setParameter("y", y);
        List<Movement> list = q.list();
        s.close();
        return list;
    }

    public List<Movement> findByUniversityAndMovementStatusAndDate(University x, EMovementStatus y, String from, String to) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date frm = sdf.parse(from);
        Date too = sdf.parse(to);
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Movement a WHERE a.university = :x AND a.movementStatus = :y AND a.entranceTime BETWEEN :from AND :to");
        q.setParameter("x", x);
        q.setParameter("y", y);
        q.setParameter("from", frm);
        q.setParameter("to", too);
        List<Movement> list = q.list();
        s.close();
        return list;
    }

    public List<Movement> findByStudent(Student x) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Movement a WHERE a.device.person = :x");
        q.setParameter("x", x);
        List<Movement> list = q.list();
        s.close();
        return list;
    }

    public Long findTotalByUniversityAndMovementStatus(University x, EMovementStatus y) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT COUNT(a.movementId) FROM Movement a WHERE a.university = :x AND a.movementStatus = :y");
        q.setParameter("x", x);
        q.setParameter("y", y);
        Long list = (Long) q.uniqueResult();
        s.close();
        return list;
    }
}
