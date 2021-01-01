
package dao;

import domain.Accusation;
import domain.Device;
import domain.Lecturer;
import domain.Security;
import domain.Staff;
import domain.Student;
import domain.University;
import java.util.Date;
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
    public List<Accusation> findByLecturer(Lecturer st){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Accusation a WHERE a.movement.device.person = :x");
        q.setParameter("x", st);
        List<Accusation> u = q.list();
        s.close();
        return u;
    }
    
    public List<Accusation> findByStaff(Staff st){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Accusation a WHERE a.movement.device.person = :x");
        q.setParameter("x", st);
        List<Accusation> u = q.list();
        s.close();
        return u;
    }
    
    
    public List<Accusation> findBySecurity(Security st){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Accusation a WHERE a.movement.device.person = :x");
        q.setParameter("x", st);
        List<Accusation> u = q.list();
        s.close();
        return u;
    }
    
    public Accusation findByDeviceAndStatus(Device de){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Accusation a WHERE a.movement.device = :x AND a.status = :st");
        q.setParameter("x", de);
        q.setParameter("st", "Raised");
        Accusation u = (Accusation) q.uniqueResult();
        s.close();
        return u;
    }
    
    public Long findTotalByUniversityAndMovementStatusAndDateAndDeviceType(University x, String status, Date from, Date to, String type){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery(
                "SELECT COUNT(a.accusationId) FROM Accusation a WHERE a.movement.university = :x AND a.movement.device.deviceType = :type AND"
                        + " a.status = :y AND a.reportingPeriod BETWEEN :from AND :to");
        q.setParameter("x", x);
        q.setParameter("y", status);
        q.setParameter("from", from);
        q.setParameter("to", to);
        q.setParameter("type", type);
        Long list = (Long) q.uniqueResult();
        s.close();
        return list;
    }
    
    public Long findTotalByUniversityAndMovementStatusAndDate(University x, String status, Date from, Date to){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery(
                "SELECT COUNT(a.accusationId) FROM Accusation a WHERE a.movement.university = :x"
                        + " AND a.status = :y AND a.reportingPeriod BETWEEN :from AND :to");
        q.setParameter("x", x);
        q.setParameter("y", status);
        q.setParameter("from", from);
        q.setParameter("to", to);
        Long list = (Long) q.uniqueResult();
        s.close();
        return list;
    }
    
    public Long findTotalByUniversityAndMovementStatus(University x, String status){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery(
                "SELECT COUNT(a.accusationId) FROM Accusation a WHERE a.movement.university = :x"
                        + " AND a.status = :y ");
        q.setParameter("x", x);
        q.setParameter("y", status);
        Long list = (Long) q.uniqueResult();
        s.close();
        return list;
    }
    
    public List<Accusation> findByUniversity(University st){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Accusation a WHERE a.movement.university = :x");
        q.setParameter("x", st);
        List<Accusation> u = q.list();
        s.close();
        return u;
    }
    
    public Accusation findByDeviceAndStatus(Device d, String status){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Accusation a WHERE a.movement.device = :d AND a.status = :s");
        q.setParameter("d", d);
        q.setParameter("s", status);
        Accusation a = (Accusation) q.uniqueResult();
        s.close();
        return a;
    }
    
}
