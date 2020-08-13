
package domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DeviceImage implements Serializable {
    @Id
    private String deviceImageId = UUID.randomUUID().toString();
    private String path;
    
    @ManyToOne
    private Device device;

    public String getDeviceImageId() {
        return deviceImageId;
    }

    public void setDeviceImageId(String deviceImageId) {
        this.deviceImageId = deviceImageId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
    
    
}
