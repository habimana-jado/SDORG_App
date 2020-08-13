
package domain;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Staff extends Person implements Serializable{
    
    @OneToOne(mappedBy = "staff", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;
    
    @ManyToOne
    private University university;
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }
    
    
}
