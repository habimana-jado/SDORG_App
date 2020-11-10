package model;

import common.FileUpload;
import dao.AccusationDao;
import dao.DeviceDao;
import dao.DeviceImageDao;
import dao.MovementDao;
import domain.Accusation;
import domain.Device;
import domain.DeviceImage;
import domain.EMovementStatus;
import domain.Movement;
import domain.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;

@ManagedBean
@SessionScoped
public class StaffModel {

    private User loggedInUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session");
    private List<DeviceImage> myDevices = new DeviceImageDao().findByPerson(loggedInUser.getStaff());
    private Device device = new Device();
    private List<String> chosenImage = new ArrayList<>();
    private List<Accusation> accusations = new AccusationDao().findByStaff(loggedInUser.getStaff());
    private Accusation accusation = new Accusation();
    private Movement movement = new Movement();
    private List<Movement> movements = new MovementDao().findByStaff(loggedInUser.getStaff());
    private String movementId = new String();
    private Movement chosenMovement = new Movement();
    private String newDate = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());

    public void registerAccusation() {
        try {
            accusation.setStatus("Raised");
            accusation.setMovement(chosenMovement);
            accusation.setReportingPeriod(new Date());
            new AccusationDao().register(accusation);
            accusations = new AccusationDao().findByStaff(loggedInUser.getStaff());
            accusation = new Accusation();

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Complaint Raised"));
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void resolveAccusation(Accusation accusation){
        try {
            accusation.setStatus("Resolved");
            accusation.setResolvedPeriod(new Date());
            new AccusationDao().update(accusation);
            accusations = new AccusationDao().findByStaff(loggedInUser.getStaff());
            
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Complaint Resolved"));
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void chooseMovement(Movement movement){
        chosenMovement = movement;
    }
    
    public void registerStaffDevice() {
        try {
            if (chosenImage.isEmpty()) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage("Upload Profile Image"));
            } else {
                device.setMovementStatus(EMovementStatus.CHECKED_OUT);
                device.setPerson(loggedInUser.getStaff());
                new DeviceDao().register(device);

                myDevices = new DeviceImageDao().findByStaff(loggedInUser.getStaff());

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

    public List<DeviceImage> getMyDevices() {
        return myDevices;
    }

    public void setMyDevices(List<DeviceImage> myDevices) {
        this.myDevices = myDevices;
    }

    public List<Accusation> getAccusations() {
        return accusations;
    }

    public void setAccusations(List<Accusation> accusations) {
        this.accusations = accusations;
    }

    public Accusation getAccusation() {
        return accusation;
    }

    public void setAccusation(Accusation accusation) {
        this.accusation = accusation;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }

    public String getMovementId() {
        return movementId;
    }

    public void setMovementId(String movementId) {
        this.movementId = movementId;
    }

    public Movement getChosenMovement() {
        return chosenMovement;
    }

    public void setChosenMovement(Movement chosenMovement) {
        this.chosenMovement = chosenMovement;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }
    

}
