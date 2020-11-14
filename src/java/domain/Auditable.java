
package domain;

import java.util.Date;

public interface Auditable {
    
    Date getDateCreated();

    void setDateCreated(Date dateCreated);

    Date getDateUpdated();

    void setDateUpdated(Date lastUpdated);
    
}
