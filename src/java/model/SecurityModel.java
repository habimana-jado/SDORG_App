package model;

import common.FileUpload;
import common.PassCode;
import dao.DeviceDao;
import dao.DeviceImageDao;
import dao.MovementDao;
import dao.PersonDao;
import dao.UserDao;
import dao.VisitorDao;
import domain.Device;
import domain.DeviceImage;
import domain.EGender;
import domain.EMovementStatus;
import domain.EStatus;
import domain.EUserType;
import domain.Movement;
import domain.Person;
import domain.User;
import domain.Visitor;
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
public class SecurityModel {

    private User loggedInUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session");
    private Visitor visitor = new Visitor();
    private List<String> chosenImage = new ArrayList<>();
    private List<DeviceImage> visitorDevices = new ArrayList<>();
    private String password = new String();
    private String gender = new String();
    private User user = new User();
    private Device device = new Device();
    private Person chosenPerson = new Person();
    private List<Person> persons = new PersonDao().findAll(Person.class);
    private DeviceImage chosenDeviceImage = new DeviceImage();
    private List<Movement> universityDevices = new MovementDao().findByUniversity(loggedInUser.getStaff().getUniversity(), EMovementStatus.CHECKED_IN);
    
    public void registerVisitor() throws Exception {

        if (gender.matches("Male")) {
            visitor.setGender(EGender.MALE);
        } else {
            visitor.setGender(EGender.FEMALE);
        }

        visitor.setPersonType("Visitor");
        new VisitorDao().register(visitor);
        persons = new PersonDao().findAll(Person.class);

        user.setVisitor(visitor);
        user.setStatus(EStatus.ACTIVE);
        user.setUserType(EUserType.VISITOR);
        user.setPassword(new PassCode().encrypt(password));
        new UserDao().register(user);
        user = new User();
        visitor = new Visitor();

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Visitor Registered"));
    }

    public String navigateToRegisterVisitorDevice(Person person) {
        chosenPerson = person;
        visitorDevices = new DeviceImageDao().findByPerson(person);
        return "devices.xhtml?faces-redirect=true";
    }

    public String navigateToDevice(DeviceImage device) {
        chosenDeviceImage = device;
        return "check-in.xhtml?faces-redirect=true";
    }

    public void registerVisitorDevice() {
        try {
            if (chosenImage.isEmpty()) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage("Upload Device Image"));
            } else {
                device.setMovementStatus(EMovementStatus.CHECKED_OUT);
                device.setPerson(chosenPerson);
                new DeviceDao().register(device);

                DeviceImage deviceImage = new DeviceImage();
                for (String x : chosenImage) {
                    deviceImage.setPath(x);
                    deviceImage.setDevice(device);
                    new DeviceImageDao().register(deviceImage);
                }
                chosenImage.clear();
                visitorDevices = new DeviceImageDao().findByPerson(chosenPerson);
                device = new Device();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage("Device Registered"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkInDevice() {
        Movement movement = new Movement();
        movement.setDevice(chosenDeviceImage.getDevice());
        movement.setEntranceTime(new Date());
        movement.setMovementStatus(EMovementStatus.CHECKED_IN);
        movement.setUniversity(loggedInUser.getStaff().getUniversity());
        new MovementDao().register(movement);
        
        Device device = chosenDeviceImage.getDevice();
        device.setMovementStatus(EMovementStatus.CHECKED_IN);
        new DeviceDao().update(device);
        
        visitorDevices = new DeviceImageDao().findByPerson(chosenPerson);
        universityDevices = new MovementDao().findByUniversity(loggedInUser.getStaff().getUniversity(), EMovementStatus.CHECKED_IN);
        
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Device Checked-In"));
    }
    
    public void checkOutDevice(Movement movement){
        movement.setExitTime(new Date());
        movement.setMovementStatus(EMovementStatus.CHECKED_OUT);
        new MovementDao().update(movement);
        
        Device device = movement.getDevice();
        device.setMovementStatus(EMovementStatus.CHECKED_OUT);
        new DeviceDao().update(device);
        universityDevices = new MovementDao().findByUniversity(loggedInUser.getStaff().getUniversity(), EMovementStatus.CHECKED_IN);
        
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Device Checked-Out"));
    }

    public void upload(FileUploadEvent event) {
        chosenImage.add(new FileUpload().Upload(event, "C:\\Users\\nizey\\OneDrive\\Documents\\NetBeansProjects\\Thesis\\SDORG\\web\\uploads\\device\\"));
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public List<String> getChosenImage() {
        return chosenImage;
    }

    public void setChosenImage(List<String> chosenImage) {
        this.chosenImage = chosenImage;
    }

    public List<DeviceImage> getVisitorDevices() {
        return visitorDevices;
    }

    public void setVisitorDevices(List<DeviceImage> visitorDevices) {
        this.visitorDevices = visitorDevices;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Person getChosenPerson() {
        return chosenPerson;
    }

    public void setChosenPerson(Person chosenPerson) {
        this.chosenPerson = chosenPerson;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public DeviceImage getChosenDeviceImage() {
        return chosenDeviceImage;
    }

    public void setChosenDeviceImage(DeviceImage chosenDeviceImage) {
        this.chosenDeviceImage = chosenDeviceImage;
    }

    public List<Movement> getUniversityDevices() {
        return universityDevices;
    }

    public void setUniversityDevices(List<Movement> universityDevices) {
        this.universityDevices = universityDevices;
    }

}
