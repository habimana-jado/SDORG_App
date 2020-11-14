
package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@EntityListeners(AuditableListener.class)
public class Accusation implements Serializable, Auditable{
    @Id
    private String accusationId = UUID.randomUUID().toString();
    private String comment;
    private String status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportingPeriod;
    @Temporal(TemporalType.TIMESTAMP)
    private Date resolvedPeriod;
    private Date dateCreated;
    private Date dateupdated;
    @ManyToOne
    private Person createdBy;
    @ManyToOne
    private Person updatedBy;
   
    @ManyToOne
    private Movement movement;

    public String getAccusationId() {
        return accusationId;
    }

    public void setAccusationId(String accusationId) {
        this.accusationId = accusationId;
    }
   
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public Date getReportingPeriod() {
        return reportingPeriod;
    }

    public void setReportingPeriod(Date reportingPeriod) {
        this.reportingPeriod = reportingPeriod;
    }

    public Date getResolvedPeriod() {
        return resolvedPeriod;
    }

    public void setResolvedPeriod(Date resolvedPeriod) {
        this.resolvedPeriod = resolvedPeriod;
    }

    @Override
    public Date getDateCreated() {
        return dateCreated;
    }

    @Override
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public Date getDateUpdated() {
        return dateupdated;
    }

    @Override
    public void setDateUpdated(Date lastUpdated) {
        this.dateupdated = lastUpdated;
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

    
}
