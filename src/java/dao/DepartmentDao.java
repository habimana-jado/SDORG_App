
package dao;

import domain.Department;
import domain.University;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class DepartmentDao extends GenericDao<Department>{
    public List<Department> findByUniversity(University university){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Department a WHERE a.faculty.university = :x");
        q.setParameter("x", university);
        List<Department> u = q.list();
        s.close();
        return u;
    }
}
