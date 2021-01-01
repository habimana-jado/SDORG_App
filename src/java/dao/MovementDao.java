package dao;

import domain.Device;
import domain.EMovementStatus;
import domain.Lecturer;
import domain.Movement;
import domain.Security;
import domain.Staff;
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

    public List<Movement> findByUniversityLogged(University x) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Movement a WHERE a.university = :x ");
        q.setParameter("x", x);
        List<Movement> list = q.list();
        s.close();
        return list;
    }

    public List<Movement> findByUniversityAndMovementStatusAndDate(University x, EMovementStatus y, String from, String to) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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
    
    public List<Movement> findBySecurity(Security x) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Movement a WHERE a.device.person = :x");
        q.setParameter("x", x);
        List<Movement> list = q.list();
        s.close();
        return list;
    }
    public List<Movement> findByLecturer(Lecturer x) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Movement a WHERE a.device.person = :x");
        q.setParameter("x", x);
        List<Movement> list = q.list();
        s.close();
        return list;
    }

    public List<Movement> findByStaff(Staff x) {
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
    
    public Long findTotalByUniversityAndMovementStatusAndDateAndDeviceType(University x, EMovementStatus y, Date from, Date to, String type) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery
        ("SELECT COUNT(a.movementId) FROM Movement a WHERE a.university = :x AND a.device.deviceType = :type AND a.movementStatus = :y "
                + "AND a.entranceTime BETWEEN :from AND :to ");
        q.setParameter("x", x);
        q.setParameter("y", y);
        q.setParameter("from", from);
        q.setParameter("to", to);
        q.setParameter("type", type);
        Long list = (Long) q.uniqueResult();
        s.close();
        return list;
    }
    
    public Long findTotalByUniversityAndMovementStatusAndDate(University x, EMovementStatus y, Date from, Date to) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery
        ("SELECT COUNT(a.movementId) FROM Movement a WHERE a.university = :x AND a.movementStatus = :y "
                + "AND a.entranceTime BETWEEN :from AND :to ");
        q.setParameter("x", x);
        q.setParameter("y", y);
        q.setParameter("from", from);
        q.setParameter("to", to);
        Long list = (Long) q.uniqueResult();
        s.close();
        return list;
    }
    
    public Movement findByDeviceAndStatus(Device d,EMovementStatus x) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Movement a WHERE a.device = :d AND a.movementStatus = :stat");
        q.setParameter("d", d);
        q.setParameter("stat", x);
        Movement list = (Movement) q.uniqueResult();
        s.close();
        return list;
    }

}
