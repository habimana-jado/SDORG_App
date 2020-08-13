
package model;

import common.FileUpload;
import dao.DeviceDao;
import dao.DeviceImageDao;
import domain.Device;
import domain.DeviceImage;
import domain.EMovementStatus;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;

@ManagedBean
@SessionScoped
public class LecturerModel {
    private User loggedInUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session");
    private List<DeviceImage> myDevices = new DeviceImageDao().findByPerson(loggedInUser.getLecturer());
    private Device device = new Device();
    private List<String> chosenImage = new ArrayList<>();
    
    public void registerLecturerDevice() {
        try {
            if (chosenImage.isEmpty()) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage("Upload Profile Image"));
            } else {
                device.setMovementStatus(EMovementStatus.CHECKED_OUT);
                device.setPerson(loggedInUser.getLecturer());
                new DeviceDao().register(device);

                myDevices = new DeviceImageDao().findByLecturer(loggedInUser.getLecturer());

                DeviceImage deviceImage = new DeviceImage();
                for (String x : chosenImage) {
                    deviceImage.setPath(x);
                    deviceImage.setDevice(device);
                    new DeviceImageDao().register(deviceImage);
                }
                chosenImage.clear();
                
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage("Device Registered"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void Upload(FileUploadEvent event) {
        chosenImage.add(new FileUpload().Upload(event, "C:\\Users\\nizey\\OneDrive\\Documents\\NetBeansProjects\\Thesis\\SDORG\\web\\uploads\\device\\"));
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public List<DeviceImage> getMyDevices() {
        return myDevices;
    }

    public void setMyDevices(List<DeviceImage> myDevices) {
        this.myDevices = myDevices;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public List<String> getChosenImage() {
        return chosenImage;
    }

    public void setChosenImage(List<String> chosenImage) {
        this.chosenImage = chosenImage;
    }

    
}
