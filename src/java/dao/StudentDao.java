
package dao;

import domain.Lecturer;
import domain.Student;
import domain.University;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class StudentDao extends GenericDao<Student>{
    public List<Student> findByUniversity(University university){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Student a WHERE a.department.faculty.university = :x");
        q.setParameter("x", university);
        List<Student> u = q.list();
        s.close();
        return u;
    }
    public List<Student> findByUniversityAndNames(University university, String n){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Student a WHERE a.department.faculty.university = :x AND a.firstName = :y OR a.lastName = :z");
        q.setParameter("x", university);
        q.setParameter("y", n);
        q.setParameter("z", n);
        List<Student> u = q.list();
        s.close();
        return u;
    }
}
