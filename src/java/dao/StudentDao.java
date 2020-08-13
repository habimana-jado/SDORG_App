
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
}
