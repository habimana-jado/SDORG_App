
package domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Lecturer extends Person implements Serializable{
    
    @ManyToOne
    private Faculty faculty;
    
    @OneToOne(mappedBy = "lecturer", fetch = FetchType.EAGER)
    private User user;

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

   
    
    
}
