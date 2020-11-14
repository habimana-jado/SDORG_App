
package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@EntityListeners(AuditableListener.class)
public class Movement implements Serializable, Auditable {
    @Id
    private String movementId = UUID.randomUUID().toString();
    @Temporal(TemporalType.TIMESTAMP)
    private Date entranceTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date exitTime;
    @Enumerated(EnumType.STRING)
    private EMovementStatus movementStatus;
    private Date dateCreated;
    private Date dateupdated;
    @ManyToOne
    private Person createdBy;
    @ManyToOne
    private Person updatedBy;
   
    
    @ManyToOne
    private Device device;
    
    @ManyToOne
    private University university;

    @OneToMany(mappedBy = "movement", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Accusation> accusation;

    public String getMovementId() {
        return movementId;
    }

    public void setMovementId(String movementId) {
        this.movementId = movementId;
    }
    

    public Date getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(Date entranceTime) {
        this.entranceTime = entranceTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public EMovementStatus getMovementStatus() {
        return movementStatus;
    }

    public void setMovementStatus(EMovementStatus movementStatus) {
        this.movementStatus = movementStatus;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public List<Accusation> getAccusation() {
        return accusation;
    }

    public void setAccusation(List<Accusation> accusation) {
        this.accusation = accusation;
    }

    @Override
    public Date getDateCreated() {
        return dateCreated;
    }

    @Override
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Person getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Person createdBy) {
        this.createdBy = createdBy;
    }

    public Person getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Person updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public Date getDateUpdated() {
        return dateupdated;
    }

    @Override
    public void setDateUpdated(Date lastUpdated) {
        this.dateupdated = lastUpdated;
    }
    
}
