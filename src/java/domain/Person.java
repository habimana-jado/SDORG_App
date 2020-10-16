
package domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Person implements Serializable {
    @Id
    private String nationalId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String profilePicture;
    @Enumerated(EnumType.STRING)
    private EGender gender;
    private String personType;
    
    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Device> device;
    
    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public List<Device> getDevice() {
        return device;
    }

    public void setDevice(List<Device> device) {
        this.device = device;
    }  

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }
    
}
