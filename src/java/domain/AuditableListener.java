
package domain;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AuditableListener {
    @PrePersist
    void preCreate(Auditable auditable) {
        Date now = new Date();
        auditable.setDateCreated(now);
        auditable.setDateUpdated(now);
    }

    @PreUpdate
    void preUpdate(Auditable auditable) {
        Date now = new Date();
        auditable.setDateUpdated(now);
    }
}
