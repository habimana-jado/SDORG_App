package model;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import common.FileUpload;
import common.PassCode;
import dao.AccusationDao;
import dao.DeviceDao;
import dao.DeviceImageDao;
import dao.MovementDao;
import dao.PersonDao;
import dao.UserDao;
import dao.VisitorDao;
import domain.Accusation;
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
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
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
    private List<Movement> alertedDevices = new ArrayList<>();
    private String alertMessage = "This device is free to enter";
    private Accusation chosenAccusation = new Accusation();
    private boolean alerted = Boolean.FALSE;

    @PostConstruct
    public void init() {
        loggedInUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session");
        universityDevices = new MovementDao().findByUniversity(loggedInUser.getStaff().getUniversity(), EMovementStatus.CHECKED_IN);
        check24HAlert();
    }

    public void registerVisitor() throws Exception {

        if (gender.matches("Male")) {
            visitor.setGender(EGender.MALE);
        } else {
            visitor.setGender(EGender.FEMALE);
        }
        if (new UserDao().usernameExist(user.getUsername())) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Username already exists"));
        } else {
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
    }

    public void check24HAlert() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = null;
        long diff;
        long diffSeconds;
        try {
            String strDate = sdf.format(new Date());
            Calendar cal = new GregorianCalendar();
            cal.setTime(sdf.parse(strDate));
//            cal.add(Calendar.HOUR_OF_DAY, 24);
            String t1 = sdf.format(cal.getTime());
            Date d2 = sdf.parse(t1);
            alertedDevices.clear();
            for (Movement m : universityDevices) {
//                d1 = sdf.parse(m.getEntranceTime().toString());
                d1 = m.getEntranceTime();
                diff = d2.getTime() - d1.getTime();
                diffSeconds = diff / 1000;
                if (diffSeconds > 86400) {
                    alertedDevices.add(m);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String navigateToRegisterVisitorDevice(Person person) {
        chosenPerson = person;
        visitorDevices = new DeviceImageDao().findByPerson(person);
        return "devices.xhtml?faces-redirect=true";
    }

    public String navigateToDevice(DeviceImage device) {
        chosenDeviceImage = device;
        chosenAccusation = new AccusationDao().findByDeviceAndStatus(device.getDevice());
        if (chosenAccusation == null) {
            alertMessage = "This device is free to enter";
            alerted = Boolean.FALSE;
        } else {
            alertMessage = "This device is alerted in Complaints";
            alerted = Boolean.TRUE;
        }
        System.out.println(alertMessage);
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

    public void checkOutDevice(Movement movement) {
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

    public void generateaccusationreport() throws FileNotFoundException, DocumentException, BadElementException, IOException, Exception {

        FacesContext context = FacesContext.getCurrentInstance();
        Document document = new Document();
        Rectangle rect = new Rectangle(20, 20, 580, 500);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance((com.lowagie.text.Document) document, baos);
        writer.setBoxSize("art", rect);
        document.setPageSize(rect);
        if (!document.isOpen()) {
            document.open();
        }
//        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\Uploads");
//        path = path.substring(0, path.indexOf("\\build"));
//        path = path + "\\web\\Uploads\\"+h.getImage();
//        Image image = Image.getInstance(path);
//        image.scaleAbsolute(50, 50);
//        image.setAlignment(Element.ALIGN_LEFT);
        Paragraph title = new Paragraph();
        //BEGIN page
//        title.add(image);
        document.add(title);
        Font font0 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        Font font1 = new Font(Font.TIMES_ROMAN, 14, Font.ITALIC, new Color(37, 46, 158));
        Font font2 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL, new Color(0, 0, 0));
        Font font5 = new Font(Font.TIMES_ROMAN, 10, Font.ITALIC, new Color(0, 0, 0));
        Font colorFont = new Font(Font.TIMES_ROMAN, 10, Font.BOLD, new Color(0, 0, 0));
        Font font6 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        document.add(new Paragraph("SDORG Application\n"));
        document.add(new Paragraph("KG 625 ST 4\n", font0));
        document.add(new Paragraph("P.O.BOX 131 \n", font0));
        document.add(new Paragraph("KIGALI-RWANDA\n\n", font0));
        Paragraph p = new Paragraph("University Alerts Report ", colorFont);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\n"));
        PdfPTable tables = new PdfPTable(6);
        tables.setWidthPercentage(100);

        PdfPCell cell1 = new PdfPCell(new Phrase("#", font2));
        cell1.setBorder(Rectangle.BOX);
        tables.addCell(cell1);

        PdfPCell c2 = new PdfPCell(new Phrase("Device Name", font2));
        c2.setBorder(Rectangle.BOX);
        tables.addCell(c2);

        PdfPCell c3 = new PdfPCell(new Phrase("Device Type", font2));
        c3.setBorder(Rectangle.BOX);
        tables.addCell(c3);

        PdfPCell c4 = new PdfPCell(new Phrase("Entrance Time", font2));
        c4.setBorder(Rectangle.BOX);
        tables.addCell(c4);

        PdfPCell c5 = new PdfPCell(new Phrase("Owner Names", font2));
        c5.setBorder(Rectangle.BOX);
        tables.addCell(c5);

        PdfPCell c6 = new PdfPCell(new Phrase("Owner Phone", font2));
        c6.setBorder(Rectangle.BOX);
        tables.addCell(c6);

        tables.setHeaderRows(1);
        PdfPCell pdfc5;
        PdfPCell pdfc1;
        PdfPCell pdfc3;
        PdfPCell pdfc2;
        PdfPCell pdfc4;
        PdfPCell pdfc6;
        PdfPCell pdfc7;
        PdfPCell pdfc8;
        int i = 1;
        DecimalFormat dcf = new DecimalFormat("###,###,###");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        for (Movement x : alertedDevices) {
            pdfc5 = new PdfPCell(new Phrase(i + ""));
            pdfc5.setBorder(Rectangle.BOX);
            tables.addCell(pdfc5);

            pdfc4 = new PdfPCell(new Phrase(x.getDevice().getDeviceName() + "", font6));
            pdfc4.setBorder(Rectangle.BOX);
            tables.addCell(pdfc4);

            pdfc3 = new PdfPCell(new Phrase(x.getDevice().getDeviceType() + "", font6));
            pdfc3.setBorder(Rectangle.BOX);
            tables.addCell(pdfc3);

            pdfc2 = new PdfPCell(new Phrase(x.getEntranceTime() + "", font6));
            pdfc2.setBorder(Rectangle.BOX);
            tables.addCell(pdfc2);

            pdfc1 = new PdfPCell(new Phrase(x.getDevice().getPerson().getFirstName() + " " + x.getDevice().getPerson().getLastName() + "", font6));
            pdfc1.setBorder(Rectangle.BOX);
            tables.addCell(pdfc1);

            pdfc6 = new PdfPCell(new Phrase(x.getDevice().getPerson().getPhone() + "", font6));
            pdfc6.setBorder(Rectangle.BOX);
            tables.addCell(pdfc6);

            i++;
        }
        document.add(tables);
        Paragraph par = new Paragraph("\n\nPrinted On: " + sdf.format(new Date()) + ". By: " + loggedInUser.getStaff().getFirstName() + " ", font1);
        par.setAlignment(Element.ALIGN_RIGHT);
        document.add(par);
        document.close();
        String fileName = "Report_" + new Date().getTime() / (1000 * 3600 * 24);
        writePDFToResponse(context.getExternalContext(), baos, fileName);
        context.responseComplete();
    }

    private void writePDFToResponse(ExternalContext externalContext, ByteArrayOutputStream baos, String fileName) throws IOException {
        externalContext.responseReset();
        externalContext.setResponseContentType("application/pdf");
        externalContext.setResponseHeader("Expires", "0");
        externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        externalContext.setResponseHeader("Pragma", "public");
        externalContext.setResponseHeader("Content-disposition", "attachment;filename=" + fileName + ".pdf");
        externalContext.setResponseContentLength(baos.size());
        OutputStream out = externalContext.getResponseOutputStream();
        baos.writeTo(out);
        externalContext.responseFlushBuffer();
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

    public List<Movement> getAlertedDevices() {
        return alertedDevices;
    }

    public void setAlertedDevices(List<Movement> alertedDevices) {
        this.alertedDevices = alertedDevices;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public Accusation getChosenAccusation() {
        return chosenAccusation;
    }

    public void setChosenAccusation(Accusation chosenAccusation) {
        this.chosenAccusation = chosenAccusation;
    }

    public boolean isAlerted() {
        return alerted;
    }

    public void setAlerted(boolean alerted) {
        this.alerted = alerted;
    }

}
