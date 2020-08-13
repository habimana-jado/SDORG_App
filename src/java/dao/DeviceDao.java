
package dao;

import domain.Device;
import domain.Student;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class DeviceDao extends GenericDao<Device>{
    public List<Device> findByStudent(Student x){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query q = s.createQuery("SELECT a FROM Device a WHERE a.student = :x");
        q.setParameter("x", x);
        List<Device> list = q.list();
        s.close();
        return list;
    }
}
