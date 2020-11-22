package model;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEvent;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import common.FileUpload;
import common.PassCode;
import dao.AccusationDao;
import dao.DepartmentDao;
import dao.DeviceDao;
import dao.FacultyDao;
import dao.LecturerDao;
import dao.MovementDao;
import dao.SecurityDao;
import dao.StaffDao;
import dao.StudentDao;
import dao.UserDao;
import domain.Accusation;
import domain.Department;
import domain.Device;
import domain.EGender;
import domain.EMovementStatus;
import domain.EStatus;
import domain.EUserType;
import domain.Faculty;
import domain.Lecturer;
import domain.Movement;
import domain.Person;
import domain.Security;
import domain.Staff;
import domain.Student;
import domain.University;
import domain.User;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean
@SessionScoped
public class AdminModel {

    private Student student = new Student();
    private Lecturer lecturer = new Lecturer();
    private University university = new University();
    private User user = new User();
    private Staff staff = new Staff();
    private Security security = new Security();
    private User loggedInUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session");
    private List<Student> students = new StudentDao().findByUniversity(loggedInUser.getAdmin().getUniversity());
    private List<Lecturer> lecturers = new LecturerDao().findByUniversity(loggedInUser.getAdmin().getUniversity());
    private List<Staff> staffs = new StaffDao().findByUniversity(loggedInUser.getAdmin().getUniversity());
    private List<Security> securits = new SecurityDao().findByUniversity(loggedInUser.getAdmin().getUniversity());
    private List<String> chosenImage = new ArrayList<>();
    private String password = new String();
    private Device device = new Device();
    private String studentId = new String();
    private String lecturerId = new String();
    private String staffId = new String();
    private List<Device> devices = new DeviceDao().findAll(Device.class);
    private Faculty faculty = new Faculty();
    private Department department = new Department();
    private String facultyId = new String();
    private String departmentId = new String();
    private String universityId = new String();
    private List<Faculty> faculties = new FacultyDao().findByUniversity(loggedInUser.getAdmin().getUniversity());
    private List<Department> departments = new DepartmentDao().findByUniversity(loggedInUser.getAdmin().getUniversity());
    private String gender;
    private List<User> studentUsers = new UserDao().findByAccess(EUserType.STUDENT);
    private List<User> staffUsers = new UserDao().findByAccess(EUserType.SECURITY);
    private List<User> lecturerUsers = new UserDao().findByAccess(EUserType.LECTURER);
    private List<User> adminUsers = new UserDao().findByAccess(EUserType.ADMIN);
    private List<User> securityUsers = new UserDao().findByAccess(EUserType.STAFF);
//    private List<Movement> universityDevices = new ArrayList<>();
    private List<Movement> universityDevices = new MovementDao().findByUniversity(loggedInUser.getAdmin().getUniversity(), EMovementStatus.CHECKED_IN);
    private List<Movement> universityExitedDevices = new MovementDao().findByUniversity(loggedInUser.getAdmin().getUniversity(), EMovementStatus.CHECKED_OUT);
    private UploadedFile uploadedFile;
    private String uploadedFileName = new String();
    private PieChartModel pieModel1;
    private PieChartModel pieModel2;
    private BarChartModel barChartModel1;
    private String from;
    private String to;
    private String searchKey = new String();
    private final List<Movement> movements = new MovementDao().findByUniversityLogged(loggedInUser.getAdmin().getUniversity());
    private final List<Accusation> accusations = new AccusationDao().findByUniversity(loggedInUser.getAdmin().getUniversity());
    private String checkPassword = new String();
    private String year = "2020";
    private String month = "11";
    private int counter1 = 0;
    private int counter2 = 0;
    private int counter3 = 0;
    private int counter4 = 0;
    private int counter5 = 0;
    private int counter6 = 0;
    private int counter7 = 0;
    private int counter8 = 0;
    private int counter9 = 0;
    private int counter10 = 0;
    private int counter11 = 0;
    private int counter12 = 0;
    private int counter13 = 0;
    private int counter14 = 0;
    private int counter15 = 0;
    private int counter16 = 0;
    private int counter17 = 0;
    private int counter18 = 0;
    private int counter19 = 0;
    private int counter20 = 0;
    private int counter21 = 0;
    private int counter22 = 0;
    private int counter23 = 0;
    private int counter24 = 0;
    private int counter25 = 0;
    private int counter26 = 0;
    private int counter27 = 0;
    private int counter28 = 0;
    private int counter29 = 0;
    private int counter30 = 0;
    private int counter31 = 0;

    @PostConstruct
    public void init() {
//        setCounterValues(year, month);
        createPieModel1();
        createBarModel31();
    }

    private void createPieModel1() {
        pieModel1 = new PieChartModel();

        pieModel2 = new PieChartModel();

        pieModel1.set("Checked-In", new MovementDao().findTotalByUniversityAndMovementStatus(loggedInUser.getAdmin().getUniversity(), EMovementStatus.CHECKED_IN));
        pieModel1.set("Checked-Out", new MovementDao().findTotalByUniversityAndMovementStatus(loggedInUser.getAdmin().getUniversity(), EMovementStatus.CHECKED_OUT));

        pieModel2.set("Raised", new AccusationDao().findTotalByUniversityAndMovementStatus(loggedInUser.getAdmin().getUniversity(), "Raised"));
        pieModel2.set("Resolved", new AccusationDao().findTotalByUniversityAndMovementStatus(loggedInUser.getAdmin().getUniversity(), "Resolved"));

        pieModel1.setTitle("Movements");
        pieModel1.setLegendPosition("w");
        pieModel1.setShadow(false);

        pieModel2.setTitle("Complaints");
        pieModel2.setLegendPosition("e");
        pieModel2.setShadow(false);
    }

    public void createBarModel() {
        barChartModel1 = new BarChartModel();

        ChartSeries model = new ChartSeries();
        model.set("January", 1);
        model.set("February", 3);
        model.set("March", 4);
        model.set("April", 2);
        model.set("May", 8);
        model.set("June", 3);
        model.set("July", 6);
        model.set("August", 1);
        model.set("September", 4);
        model.set("October", 7);
        model.set("November", 9);
        model.set("December", 2);

        barChartModel1.addSeries(model);
        barChartModel1.setTitle("Check-In");
        barChartModel1.setLegendPosition("ne");

        Axis xAxis = barChartModel1.getAxis(AxisType.X);
        xAxis.setLabel("Months");

        Axis yAxis = barChartModel1.getAxis(AxisType.Y);
        yAxis.setLabel("Occurences");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }

    public void setCounterValues(String year, String month) {
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfDay = new SimpleDateFormat("dd");

        for (Movement m : new MovementDao().findByUniversity(loggedInUser.getAdmin().getUniversity(), EMovementStatus.CHECKED_IN)) {
            if (sdfYear.format(m.getEntranceTime()).matches(year) && sdfMonth.format(m.getEntranceTime()).matches(month)) {
                if (sdfDay.format(m.getEntranceTime()).matches("01")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter1++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("02")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter2++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("03")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter3++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("04")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter4++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("05")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter5++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("06")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter6++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("07")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter7++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("08")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter8++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("09")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter9++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("10")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter10++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("11")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter11++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("12")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter12++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("13")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter13++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("14")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter14++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("15")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter15++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("16")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter16++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("17")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter17++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("18")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter18++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("19")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter19++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("20")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter20++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("21")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter21++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("22")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter22++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("23")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter23++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("24")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter24++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("25")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter25++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("26")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter26++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("27")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter27++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("28")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter28++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("29")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter29++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("30")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter30++;
                } else if (sdfDay.format(m.getEntranceTime()).matches("31")) {
                    System.out.println(sdfDay.format(m.getEntranceTime()));
                    counter31++;
                }
            }
        }
    }

    public void setYear() {
        System.out.println("Year =" + year);
    }

    public void createBarModel31() {
        counter1 = 0;
        counter2 = 0;
        counter3 = 0;
        counter4 = 0;
        counter5 = 0;
        counter6 = 0;
        counter7 = 0;
        counter8 = 0;
        counter9 = 0;
        counter10 = 0;
        counter11 = 0;
        counter12 = 0;
        counter13 = 0;
        counter14 = 0;
        counter15 = 0;
        counter16 = 0;
        counter17 = 0;
        counter18 = 0;
        counter19 = 0;
        counter20 = 0;
        counter21 = 0;
        counter22 = 0;
        counter23 = 0;
        counter24 = 0;
        counter25 = 0;
        counter26 = 0;
        counter27 = 0;
        counter28 = 0;
        counter29 = 0;
        counter30 = 0;
        counter31 = 0;
        setCounterValues(year, month);
        barChartModel1 = new BarChartModel();

        ChartSeries model = new ChartSeries();

        model.setLabel("Count");
        model.set("1st", counter1);
        model.set("2nd", counter2);
        model.set("3rd", counter3);
        model.set("4th", counter4);
        model.set("5th", counter5);
        model.set("6th", counter6);
        model.set("7th", counter7);
        model.set("8th", counter8);
        model.set("9th", counter9);
        model.set("10th", counter10);
        model.set("11th", counter11);
        model.set("12th", counter12);
        model.set("13th", counter13);
        model.set("14th", counter14);
        model.set("15th", counter15);
        model.set("16th", counter16);
        model.set("17th", counter17);
        model.set("18th", counter18);
        model.set("19th", counter19);
        model.set("20th", counter20);
        model.set("21st", counter21);
        model.set("22nd", counter22);
        model.set("23rd", counter23);
        model.set("24th", counter24);
        model.set("25th", counter25);
        model.set("26th", counter26);
        model.set("27th", counter27);
        model.set("28th", counter28);
        model.set("29th", counter29);
        model.set("30th", counter30);
        model.set("31st", counter31);

        barChartModel1.addSeries(model);
        barChartModel1.setTitle("Check-In");
        barChartModel1.setLegendPosition("ne");

        Axis xAxis = barChartModel1.getAxis(AxisType.X);
        xAxis.setLabel("Days");

        Axis yAxis = barChartModel1.getAxis(AxisType.Y);
        yAxis.setLabel("Occurences");
        yAxis.setMin(0);
        yAxis.setMax(20);
    }

    public void createBarModel30() {
        barChartModel1 = new BarChartModel();

        ChartSeries model = new ChartSeries();
        model.set("January", 1);
        model.set("February", 3);
        model.set("March", 4);
        model.set("April", 2);
        model.set("May", 8);
        model.set("June", 3);
        model.set("July", 6);
        model.set("August", 1);
        model.set("September", 4);
        model.set("October", 7);
        model.set("November", 9);
        model.set("December", 2);

        barChartModel1.addSeries(model);
        barChartModel1.setTitle("Check-In");
        barChartModel1.setLegendPosition("ne");

        Axis xAxis = barChartModel1.getAxis(AxisType.X);
        xAxis.setLabel("Months");

        Axis yAxis = barChartModel1.getAxis(AxisType.Y);
        yAxis.setLabel("Occurences");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }

    public void createBarModel29() {
        barChartModel1 = new BarChartModel();

        ChartSeries model = new ChartSeries();
        model.set("January", 1);
        model.set("February", 3);
        model.set("March", 4);
        model.set("April", 2);
        model.set("May", 8);
        model.set("June", 3);
        model.set("July", 6);
        model.set("August", 1);
        model.set("September", 4);
        model.set("October", 7);
        model.set("November", 9);
        model.set("December", 2);

        barChartModel1.addSeries(model);
        barChartModel1.setTitle("Check-In");
        barChartModel1.setLegendPosition("ne");

        Axis xAxis = barChartModel1.getAxis(AxisType.X);
        xAxis.setLabel("Months");

        Axis yAxis = barChartModel1.getAxis(AxisType.Y);
        yAxis.setLabel("Occurences");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }

    public void searchMovementOut() throws ParseException {
        universityExitedDevices = new MovementDao().findByUniversityAndMovementStatusAndDate(loggedInUser.getAdmin().getUniversity(), EMovementStatus.CHECKED_OUT, from, to);
    }

    public void refresh() {
        new MovementDao().findByUniversity(loggedInUser.getAdmin().getUniversity(), EMovementStatus.CHECKED_IN);
        universityExitedDevices = new MovementDao().findByUniversity(loggedInUser.getAdmin().getUniversity(), EMovementStatus.CHECKED_OUT);
        to = new String();
        from = new String();
    }

    public void registerFaculty() {
        Boolean flag = false;
        for (Faculty f : faculties) {
            if (faculty.getName().trim().equalsIgnoreCase(f.getName().trim())) {
                flag = true;
            }
        }
        if (Objects.equals(flag, Boolean.FALSE)) {
            faculty.setUniversity(loggedInUser.getAdmin().getUniversity());
            new FacultyDao().register(faculty);
            faculty = new Faculty();
            faculties = new FacultyDao().findByUniversity(loggedInUser.getAdmin().getUniversity());

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Faculty Registered"));
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Faculty Name Already Registered"));
        }
    }

    public void registerDepartment() {
        Boolean flag = false;
        for (Department f : departments) {
            if (department.getName().trim().equalsIgnoreCase(f.getName().trim())) {
                flag = true;
            }
        }
        if (Objects.equals(flag, Boolean.FALSE)) {
            Faculty f = new FacultyDao().findOne(Faculty.class, facultyId);
            department.setFaculty(f);
            new DepartmentDao().register(department);
            department = new Department();
            departments = new DepartmentDao().findByUniversity(loggedInUser.getAdmin().getUniversity());

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Department Registered"));
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Department Name Already Registered"));
        }

    }

    public void registerStudent() throws Exception {
        if (!password.matches(checkPassword)) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Passwords do not match"));
        } else {
            if (new StudentDao().findOne(Student.class, student.getNationalId()) != null) {
                for (String x : chosenImage) {
                    student.setProfilePicture(x);
                }
                chosenImage.clear();
                Department department = new DepartmentDao().findOne(Department.class, departmentId);
                student.setDepartment(department);
                if (gender.matches("Male")) {
                    student.setGender(EGender.MALE);
                } else {
                    student.setGender(EGender.FEMALE);
                }
                student.setPersonType("Student");
                new StudentDao().update(student);

                user.setStudent(student);
                user.setStatus(EStatus.ACTIVE);
                user.setUserType(EUserType.STUDENT);
                user.setPassword(new PassCode().encrypt(password));
                new UserDao().update(user);
                user = new User();
                student = new Student();
                students = new StudentDao().findByUniversity(loggedInUser.getAdmin().getUniversity());

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage("Student Registered"));
            } else if (new UserDao().usernameExist(user.getUsername())) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, new FacesMessage("Username already exists"));
            } else {
                if (chosenImage.isEmpty()) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Upload Profile Image"));
                } else {
                    for (String x : chosenImage) {
                        student.setProfilePicture(x);
                    }
                    chosenImage.clear();
                    Department department = new DepartmentDao().findOne(Department.class, departmentId);
                    student.setDepartment(department);
                    if (gender.matches("Male")) {
                        student.setGender(EGender.MALE);
                    } else {
                        student.setGender(EGender.FEMALE);
                    }
                    student.setPersonType("Student");
                    new StudentDao().register(student);

                    user.setStudent(student);
                    user.setStatus(EStatus.ACTIVE);
                    user.setUserType(EUserType.STUDENT);
                    user.setPassword(new PassCode().encrypt(password));
                    new UserDao().register(user);
                    user = new User();
                    student = new Student();
                    students = new StudentDao().findByUniversity(loggedInUser.getAdmin().getUniversity());

                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Student Registered"));
                }
            }
        }
    }

    public void registerLecturer() throws Exception {
        if (!password.matches(checkPassword)) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Passwords do not match"));
        } else {
            if (new LecturerDao().findOne(Lecturer.class, lecturer.getNationalId()) != null) {

                if (chosenImage.isEmpty()) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Upload Profile Image"));
                } else {
                    for (String x : chosenImage) {
                        lecturer.setProfilePicture(x);
                    }
                    chosenImage.clear();
                    Faculty fac = new FacultyDao().findOne(Faculty.class, facultyId);
                    lecturer.setFaculty(fac);
                    if (gender.matches("Male")) {
                        lecturer.setGender(EGender.MALE);
                    } else {
                        lecturer.setGender(EGender.FEMALE);
                    }
                    lecturer.setPersonType("Lecturer");
                    new LecturerDao().update(lecturer);

                    user.setLecturer(lecturer);
                    user.setStatus(EStatus.ACTIVE);
                    user.setUserType(EUserType.LECTURER);
                    user.setPassword(new PassCode().encrypt(password));
                    new UserDao().update(user);
                    user = new User();
                    lecturer = new Lecturer();
                    lecturers = new LecturerDao().findByUniversity(loggedInUser.getAdmin().getUniversity());

                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Lecturer Updated"));
                }
            } else {

                if (chosenImage.isEmpty()) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Upload Profile Image"));
                } else if (new UserDao().usernameExist(user.getUsername())) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Username already exists"));
                } else {
                    for (String x : chosenImage) {
                        lecturer.setProfilePicture(x);
                    }
                    chosenImage.clear();
                    Faculty fac = new FacultyDao().findOne(Faculty.class, facultyId);
                    lecturer.setFaculty(fac);
                    if (gender.matches("Male")) {
                        lecturer.setGender(EGender.MALE);
                    } else {
                        lecturer.setGender(EGender.FEMALE);
                    }
                    lecturer.setPersonType("Lecturer");
                    new LecturerDao().register(lecturer);

                    user.setLecturer(lecturer);
                    user.setStatus(EStatus.ACTIVE);
                    user.setUserType(EUserType.LECTURER);
                    user.setPassword(new PassCode().encrypt(password));
                    new UserDao().register(user);
                    user = new User();
                    lecturer = new Lecturer();
                    lecturers = new LecturerDao().findByUniversity(loggedInUser.getAdmin().getUniversity());

                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Lecturer Registered"));
                }
            }
        }
    }

    public void registerSecurityGuard() throws Exception {
        if (!password.matches(checkPassword)) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Passwords do not match"));
        } else {
            if (new StaffDao().findOne(Staff.class, staff.getNationalId()) != null) {

                if (chosenImage.isEmpty()) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Upload Profile Image"));
                } else {
                    for (String x : chosenImage) {
                        staff.setProfilePicture(x);
                    }
                    chosenImage.clear();
                    staff.setUniversity(loggedInUser.getAdmin().getUniversity());
                    if (gender.matches("Male")) {
                        staff.setGender(EGender.MALE);
                    } else {
                        staff.setGender(EGender.FEMALE);
                    }
                    staff.setPersonType("Security Guard");
                    new StaffDao().update(staff);

                    user.setStaff(staff);
                    user.setStatus(EStatus.ACTIVE);
                    user.setUserType(EUserType.SECURITY);
                    user.setPassword(new PassCode().encrypt(password));
                    new UserDao().update(user);
                    user = new User();
                    staff = new Staff();
                    staffs = new StaffDao().findByUniversity(loggedInUser.getAdmin().getUniversity());

                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Security Guard Updated"));
                }
            } else {

                if (chosenImage.isEmpty()) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Upload Profile Image"));
                } else if (new UserDao().usernameExist(user.getUsername())) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Username already exists"));
                } else {
                    for (String x : chosenImage) {
                        staff.setProfilePicture(x);
                    }
                    chosenImage.clear();
                    staff.setUniversity(loggedInUser.getAdmin().getUniversity());
                    if (gender.matches("Male")) {
                        staff.setGender(EGender.MALE);
                    } else {
                        staff.setGender(EGender.FEMALE);
                    }
                    staff.setPersonType("Security Guard");
                    new StaffDao().register(staff);

                    user.setStaff(staff);
                    user.setStatus(EStatus.ACTIVE);
                    user.setUserType(EUserType.SECURITY);
                    user.setPassword(new PassCode().encrypt(password));
                    new UserDao().register(user);
                    user = new User();
                    staff = new Staff();
                    staffs = new StaffDao().findByUniversity(loggedInUser.getAdmin().getUniversity());

                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Security Guard Registered"));
                }
            }
        }
    }

    public void registerStaff() throws Exception {
        if (!password.matches(checkPassword)) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("Passwords do not match"));
        } else {
            if (new SecurityDao().findOne(Security.class, security.getNationalId()) != null) {

                if (chosenImage.isEmpty()) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Upload Profile Image"));
                } else {
                    for (String x : chosenImage) {
                        security.setProfilePicture(x);
                    }
                    chosenImage.clear();
                    security.setUniversity(loggedInUser.getAdmin().getUniversity());
                    if (gender.matches("Male")) {
                        security.setGender(EGender.MALE);
                    } else {
                        security.setGender(EGender.FEMALE);
                    }
                    security.setPersonType("Staff");
                    new SecurityDao().update(security);

                    user.setSecurity(security);
                    user.setStatus(EStatus.ACTIVE);
                    user.setUserType(EUserType.STAFF);
                    user.setPassword(new PassCode().encrypt(password));
                    new UserDao().update(user);
                    user = new User();
                    security = new Security();
                    securits = new SecurityDao().findByUniversity(loggedInUser.getAdmin().getUniversity());

                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Staff Registered"));
                }

            } else {

                if (chosenImage.isEmpty()) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Upload Profile Image"));
                } else if (new UserDao().usernameExist(user.getUsername())) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Username already exists"));
                } else {
                    for (String x : chosenImage) {
                        security.setProfilePicture(x);
                    }
                    chosenImage.clear();
                    security.setUniversity(loggedInUser.getAdmin().getUniversity());
                    if (gender.matches("Male")) {
                        security.setGender(EGender.MALE);
                    } else {
                        security.setGender(EGender.FEMALE);
                    }
                    security.setPersonType("Staff");
                    new SecurityDao().register(security);

                    user.setSecurity(security);
                    user.setStatus(EStatus.ACTIVE);
                    user.setUserType(EUserType.STAFF);
                    user.setPassword(new PassCode().encrypt(password));
                    new UserDao().register(user);
                    user = new User();
                    security = new Security();
                    securits = new SecurityDao().findByUniversity(loggedInUser.getAdmin().getUniversity());

                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, new FacesMessage("Staff Registered"));
                }
            }
        }
    }

    public void disable(User user) {
        user.setStatus(EStatus.INACTIVE);
        new UserDao().update(user);

        studentUsers = new UserDao().findByAccess(EUserType.STUDENT);
        staffUsers = new UserDao().findByAccess(EUserType.SECURITY);
        lecturerUsers = new UserDao().findByAccess(EUserType.LECTURER);
        adminUsers = new UserDao().findByAccess(EUserType.ADMIN);
        securityUsers = new UserDao().findByAccess(EUserType.STAFF);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Account Disabled"));
    }

    public void enable(User user) {
        user.setStatus(EStatus.ACTIVE);
        new UserDao().update(user);

        studentUsers = new UserDao().findByAccess(EUserType.STUDENT);
        staffUsers = new UserDao().findByAccess(EUserType.SECURITY);
        lecturerUsers = new UserDao().findByAccess(EUserType.LECTURER);
        adminUsers = new UserDao().findByAccess(EUserType.ADMIN);
        securityUsers = new UserDao().findByAccess(EUserType.STAFF);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Account Enabled"));
    }

    public String navigateEditStudent(Student p) {
        student = p;
        user = new UserDao().findByStudent(p);
        return "addstudent.xhtml?faces-redirect=true";
    }

    public String navigateEditLecturer(Lecturer p) {
        lecturer = p;
        user = new UserDao().findByLecturer(p);
        return "addlecturer.xhtml?faces-redirect=true";
    }

    public String navigateEditStaff(Security p) {
        security = p;
        user = new UserDao().findBySecurity(p);
        return "addstaff.xhtml?faces-redirect=true";
    }

    public String navigateEditSecurity(Staff p) {
        staff = p;
        user = new UserDao().findByStaff(p);
        return "addsecurity.xhtml?faces-redirect=true";
    }

    public void searchLecturer() {
        if (searchKey.isEmpty() || searchKey == null) {
            lecturers = new LecturerDao().findByUniversity(loggedInUser.getAdmin().getUniversity());
        } else {
            lecturers = new LecturerDao().findByUniversityAndNames(loggedInUser.getAdmin().getUniversity(), searchKey.toUpperCase());
        }
    }

    public void searchStudent() {
        if (searchKey.isEmpty() || searchKey == null) {
            students = new StudentDao().findByUniversity(loggedInUser.getAdmin().getUniversity());
        } else {
            students = new StudentDao().findByUniversity(loggedInUser.getAdmin().getUniversity());
        }
    }

    public String UploadStudent(FileUploadEvent event) {
        try {
            uploadedFileName = UUID.randomUUID().toString().substring(1, 5) + event.getFile().getFileName();
            readFile(event.getFile().getInputstream());
            return uploadedFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException ex) {
            return null;
        }
    }

    public String UploadLecturer(FileUploadEvent event) {
        try {
            uploadedFileName = UUID.randomUUID().toString().substring(1, 5) + event.getFile().getFileName();
            readFileLecturer(event.getFile().getInputstream());
            return uploadedFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private void copyFile(String fileName, InputStream in, String concatinationPath) {
        try {
            OutputStream out = new FileOutputStream(new File(concatinationPath + fileName));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void readFile(InputStream input) throws SQLException {
        try {
            List<Student> students = new ArrayList<>();
            Workbook work = new XSSFWorkbook(input);
            Sheet sheet = work.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();
            int rowNum = 0;
            for (Row row : sheet) {

                Student emp = new Student();
                if (row.getRowNum() > 0) {
                    int counter = 0;
                    for (Cell cell : row) {
                        String cellValue = dataFormatter.formatCellValue(cell);
                        switch (counter) {
                            case 0:
                                emp.setNationalId(cellValue);
                                counter++;
                                break;
                            case 1:
                                System.out.println(cellValue);
                                emp.setFirstName(cellValue);
                                counter++;
                                break;
                            case 2:
                                System.out.println(cellValue);
                                emp.setLastName(cellValue);
                                counter++;
                                break;
                            case 3:
                                System.out.println(cellValue);
                                if (cellValue.matches("Male")) {
                                    emp.setGender(EGender.MALE);
                                } else {
                                    emp.setGender(EGender.FEMALE);
                                }
                                emp.setPersonType("Student");
                                counter++;
                                break;
                            case 4:
                                emp.setPhone(cellValue);
                                counter++;
                                break;
                            case 5:
                                emp.setEmail(cellValue);
                                counter++;
                                break;
                            case 6:
                                System.out.println(cellValue);
                                emp.setProgram(cellValue);
                                counter++;
                                break;

                        }
                        Department chosenDept = new DepartmentDao().findOne(Department.class, departmentId);
                        emp.setDepartment(chosenDept);
                        System.out.println(emp.getNationalId() + "--" + emp.getFirstName() + "--" + emp.getLastName() + "--" + emp.getPhone());
                        new StudentDao().register(emp);
                    }
                }
                students = new StudentDao().findByUniversity(loggedInUser.getAdmin().getUniversity());
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void readFileLecturer(InputStream input) throws SQLException {
        try {
            Workbook work = new XSSFWorkbook(input);
            Sheet sheet = work.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();
            int rowNum = 0;
            for (Row row : sheet) {

                Lecturer emp = new Lecturer();
                if (row.getRowNum() > 0) {
                    int counter = 0;
                    for (Cell cell : row) {
                        String cellValue = dataFormatter.formatCellValue(cell);
                        switch (counter) {
                            case 0:
                                emp.setNationalId(cellValue);
                                counter++;
                                break;
                            case 1:
                                emp.setFirstName(cellValue);
                                counter++;
                                break;
                            case 2:
                                emp.setLastName(cellValue);
                                counter++;
                                break;
                            case 3:
                                if (cellValue.matches("Male")) {
                                    emp.setGender(EGender.MALE);
                                } else {
                                    emp.setGender(EGender.FEMALE);
                                }
                                emp.setPersonType("Lecturer");
                                counter++;
                                break;
                            case 4:
                                emp.setPhone(cellValue);
                                counter++;
                                break;
                            case 5:
                                emp.setEmail(cellValue);
                                counter++;
                                break;
                        }
                        Faculty fac = new FacultyDao().findOne(Faculty.class, facultyId);
                        emp.setFaculty(fac);
                        new LecturerDao().register(lecturer);
                        lecturers = new LecturerDao().findByUniversity(loggedInUser.getAdmin().getUniversity());
                    }
                }
                students = new StudentDao().findByUniversity(loggedInUser.getAdmin().getUniversity());
            }
        } catch (IOException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Upload(FileUploadEvent event) {
        chosenImage.add(new FileUpload().Upload(event, "C:\\Users\\nizey\\OneDrive\\Documents\\NetBeansProjects\\Thesis\\SDORG\\web\\uploads\\profile\\"));
    }

    public void generateMonthlyMovementReport() throws FileNotFoundException, DocumentException, BadElementException, IOException, Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        FacesContext context = FacesContext.getCurrentInstance();
        Document document = new Document();
        Rectangle rect = new Rectangle(0, 0, 600, 700);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance((com.lowagie.text.Document) document, baos);
        writer.setBoxSize("art", rect);
        writer.setPageEvent(new MyPdfPageEventHelper());
        document.setPageSize(rect);
        if (!document.isOpen()) {
            document.open();
        }
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
//        path = path.substring(0, path.indexOf("\\build"));
//        path = path + "\\web\\reportDesign\\header.jpeg";
//        Image image = Image.getInstance(path);
//        image.scaleAbsolute(600, 140);
//        image.setAlignment(Element.ALIGN_CENTER);
//        Paragraph title = new Paragraph();
//        //BEGIN page
//        title.add(image);
//        document.add(title);
        Font font0 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        Font font1 = new Font(Font.TIMES_ROMAN, 14, Font.ITALIC, new Color(37, 46, 158));
        Font font2 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL, new Color(0, 0, 0));
        Font font5 = new Font(Font.TIMES_ROMAN, 10, Font.ITALIC, new Color(0, 0, 0));
        Font colorFont = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0));
        Font font6 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
//        document.add(new Paragraph("SDORG Application\n"));
//        document.add(new Paragraph("KG 625 ST 4\n", font0));
//        document.add(new Paragraph("P.O.BOX 131 \n", font0));
//        document.add(new Paragraph("KIGALI-RWANDA\n\n", font0));
        Paragraph p = new Paragraph("UNIVERSITY MOVEMENTS REPORT ", colorFont);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Date: " + sdf.format(new Date())));
        document.add(new Paragraph("\n"));
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\divider.jpeg";
        Image image1 = Image.getInstance(path);
        image1.scaleAbsolute(600, 20);
        image1.setAlignment(Element.ALIGN_CENTER);
        Paragraph title1 = new Paragraph();
        //BEGIN page
        title1.add(image1);
        document.add(title1);
        document.add(new Paragraph("\n"));
        PdfPTable tables = new PdfPTable(4);
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
//
//        PdfPCell c5 = new PdfPCell(new Phrase("Exit Time", font2));
//        c5.setBorder(Rectangle.BOX);
//        tables.addCell(c5);
//
//        PdfPCell c6 = new PdfPCell(new Phrase("Status", font2));
//        c6.setBorder(Rectangle.BOX);
//        tables.addCell(c6);

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

        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfDay = new SimpleDateFormat("dd");

        for (Movement x : new MovementDao().findByUniversity(loggedInUser.getAdmin().getUniversity(), EMovementStatus.CHECKED_IN)) {
            if (sdfYear.format(x.getEntranceTime()).matches(year) && sdfMonth.format(x.getEntranceTime()).matches(month)) {
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

//                pdfc1 = new PdfPCell(new Phrase(x.getExitTime() + "", font6));
//                pdfc1.setBorder(Rectangle.BOX);
//                tables.addCell(pdfc1);
//
//                pdfc6 = new PdfPCell(new Phrase(x.getMovementStatus() + "", font6));
//                pdfc6.setBorder(Rectangle.BOX);
//                tables.addCell(pdfc6);
                i++;
            }
        document.add(tables);
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\sign.jpeg";
        Image image2 = Image.getInstance(path);
        image2.scaleAbsolute(120, 120);
        image2.setAlignment(Element.ALIGN_RIGHT);
        Paragraph title2 = new Paragraph();
        //BEGIN page
        title2.add(image2);
        document.add(title2);
        document.add(new Paragraph("\n"));
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\footer.jpeg";
        Image image3 = Image.getInstance(path);
        image3.scaleAbsolute(600, 40);
        image3.setAlignment(Element.ALIGN_BASELINE);
        Paragraph title3 = new Paragraph();
        //BEGIN page
        title3.add(image3);
        document.add(title3);
        document.add(new Paragraph("\n"));
//        Paragraph par = new Paragraph("\n\nPrinted On: " + sdf.format(new Date()) + ". By: " + loggedInUser.getAdmin().getFirstName() + " ", font1);
//        par.setAlignment(Element.ALIGN_RIGHT);
//        document.add(par);
        document.close();
        String fileName = "Report_" + new Date().getTime() / (1000 * 3600 * 24);
        writePDFToResponse(context.getExternalContext(), baos, fileName);
        context.responseComplete();
        }

    }

    public void generatemovementreport() throws FileNotFoundException, DocumentException, BadElementException, IOException, Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        FacesContext context = FacesContext.getCurrentInstance();
        Document document = new Document();
        Rectangle rect = new Rectangle(0, 0, 600, 700);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance((com.lowagie.text.Document) document, baos);
        writer.setBoxSize("art", rect);
        writer.setPageEvent(new MyPdfPageEventHelper());
        document.setPageSize(rect);
        if (!document.isOpen()) {
            document.open();
        }
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
//        path = path.substring(0, path.indexOf("\\build"));
//        path = path + "\\web\\reportDesign\\header.jpeg";
//        Image image = Image.getInstance(path);
//        image.scaleAbsolute(600, 140);
//        image.setAlignment(Element.ALIGN_CENTER);
//        Paragraph title = new Paragraph();
//        //BEGIN page
//        title.add(image);
//        document.add(title);
        Font font0 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        Font font1 = new Font(Font.TIMES_ROMAN, 14, Font.ITALIC, new Color(37, 46, 158));
        Font font2 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL, new Color(0, 0, 0));
        Font font5 = new Font(Font.TIMES_ROMAN, 10, Font.ITALIC, new Color(0, 0, 0));
        Font colorFont = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0));
        Font font6 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
//        document.add(new Paragraph("SDORG Application\n"));
//        document.add(new Paragraph("KG 625 ST 4\n", font0));
//        document.add(new Paragraph("P.O.BOX 131 \n", font0));
//        document.add(new Paragraph("KIGALI-RWANDA\n\n", font0));
        Paragraph p = new Paragraph("UNIVERSITY MOVEMENTS REPORT ", colorFont);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Date: " + sdf.format(new Date())));
        document.add(new Paragraph("\n"));
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\divider.jpeg";
        Image image1 = Image.getInstance(path);
        image1.scaleAbsolute(600, 20);
        image1.setAlignment(Element.ALIGN_CENTER);
        Paragraph title1 = new Paragraph();
        //BEGIN page
        title1.add(image1);
        document.add(title1);
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

        PdfPCell c5 = new PdfPCell(new Phrase("Exit Time", font2));
        c5.setBorder(Rectangle.BOX);
        tables.addCell(c5);

        PdfPCell c6 = new PdfPCell(new Phrase("Status", font2));
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
        for (Movement x : movements) {
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

            pdfc1 = new PdfPCell(new Phrase(x.getExitTime() + "", font6));
            pdfc1.setBorder(Rectangle.BOX);
            tables.addCell(pdfc1);

            pdfc6 = new PdfPCell(new Phrase(x.getMovementStatus() + "", font6));
            pdfc6.setBorder(Rectangle.BOX);
            tables.addCell(pdfc6);

            i++;
        }
        document.add(tables);
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\sign.jpeg";
        Image image2 = Image.getInstance(path);
        image2.scaleAbsolute(120, 120);
        image2.setAlignment(Element.ALIGN_RIGHT);
        Paragraph title2 = new Paragraph();
        //BEGIN page
        title2.add(image2);
        document.add(title2);
        document.add(new Paragraph("\n"));
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\footer.jpeg";
        Image image3 = Image.getInstance(path);
        image3.scaleAbsolute(600, 40);
        image3.setAlignment(Element.ALIGN_BASELINE);
        Paragraph title3 = new Paragraph();
        //BEGIN page
        title3.add(image3);
        document.add(title3);
        document.add(new Paragraph("\n"));
//        Paragraph par = new Paragraph("\n\nPrinted On: " + sdf.format(new Date()) + ". By: " + loggedInUser.getAdmin().getFirstName() + " ", font1);
//        par.setAlignment(Element.ALIGN_RIGHT);
//        document.add(par);
        document.close();
        String fileName = "Report_" + new Date().getTime() / (1000 * 3600 * 24);
        writePDFToResponse(context.getExternalContext(), baos, fileName);
        context.responseComplete();
    }

    public void generateaccusationreport() throws FileNotFoundException, DocumentException, BadElementException, IOException, Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        FacesContext context = FacesContext.getCurrentInstance();
        Document document = new Document();
        Rectangle rect = new Rectangle(0, 0, 600, 700);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance((com.lowagie.text.Document) document, baos);
        writer.setBoxSize("art", rect);
        writer.setPageEvent(new MyPdfPageEventHelper());
        document.setPageSize(rect);
        if (!document.isOpen()) {
            document.open();
        }
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
//        path = path.substring(0, path.indexOf("\\build"));
//        path = path + "\\web\\reportDesign\\header.jpeg";
//        Image image = Image.getInstance(path);
//        image.scaleAbsolute(600, 140);
//        image.setAlignment(Element.ALIGN_CENTER);
//        Paragraph title = new Paragraph();
//        //BEGIN page
//        title.add(image);
//        document.add(title);
        Font font0 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        Font font1 = new Font(Font.TIMES_ROMAN, 14, Font.ITALIC, new Color(37, 46, 158));
        Font font2 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL, new Color(0, 0, 0));
        Font font5 = new Font(Font.TIMES_ROMAN, 10, Font.ITALIC, new Color(0, 0, 0));
        Font colorFont = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0));
        Font font6 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
//        document.add(new Paragraph("SDORG Application\n"));
//        document.add(new Paragraph("KG 625 ST 4\n", font0));
//        document.add(new Paragraph("P.O.BOX 131 \n", font0));
//        document.add(new Paragraph("KIGALI-RWANDA\n\n", font0));
        Paragraph p = new Paragraph("UNIVERSITY COMPLAINTS REPORT ", colorFont);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Date: " + sdf.format(new Date())));
        document.add(new Paragraph("\n"));
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\divider.jpeg";
        Image image1 = Image.getInstance(path);
        image1.scaleAbsolute(600, 20);
        image1.setAlignment(Element.ALIGN_CENTER);
        Paragraph title1 = new Paragraph();
        //BEGIN page
        title1.add(image1);
        document.add(title1);
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

        PdfPCell c4 = new PdfPCell(new Phrase("Complaint Date", font2));
        c4.setBorder(Rectangle.BOX);
        tables.addCell(c4);

        PdfPCell c5 = new PdfPCell(new Phrase("Owner Comment", font2));
        c5.setBorder(Rectangle.BOX);
        tables.addCell(c5);

        PdfPCell c6 = new PdfPCell(new Phrase("Status", font2));
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
        for (Accusation x : accusations) {
            pdfc5 = new PdfPCell(new Phrase(i + ""));
            pdfc5.setBorder(Rectangle.BOX);
            tables.addCell(pdfc5);

            pdfc4 = new PdfPCell(new Phrase(x.getMovement().getDevice().getDeviceName() + "", font6));
            pdfc4.setBorder(Rectangle.BOX);
            tables.addCell(pdfc4);

            pdfc3 = new PdfPCell(new Phrase(x.getMovement().getDevice().getDeviceType() + "", font6));
            pdfc3.setBorder(Rectangle.BOX);
            tables.addCell(pdfc3);

            pdfc2 = new PdfPCell(new Phrase(x.getReportingPeriod() + "", font6));
            pdfc2.setBorder(Rectangle.BOX);
            tables.addCell(pdfc2);

            pdfc1 = new PdfPCell(new Phrase(x.getComment() + "", font6));
            pdfc1.setBorder(Rectangle.BOX);
            tables.addCell(pdfc1);

            pdfc6 = new PdfPCell(new Phrase(x.getStatus() + "", font6));
            pdfc6.setBorder(Rectangle.BOX);
            tables.addCell(pdfc6);

            i++;
        }
        document.add(tables);
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\sign.jpeg";
        Image image2 = Image.getInstance(path);
        image2.scaleAbsolute(120, 120);
        image2.setAlignment(Element.ALIGN_RIGHT);
        Paragraph title2 = new Paragraph();
        //BEGIN page
        title2.add(image2);
        document.add(title2);
        document.add(new Paragraph("\n"));
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\footer.jpeg";
        Image image3 = Image.getInstance(path);
        image3.scaleAbsolute(600, 40);
        image3.setAlignment(Element.ALIGN_BASELINE);
        Paragraph title3 = new Paragraph();
        //BEGIN page
        title3.add(image3);
        document.add(title3);
        document.add(new Paragraph("\n"));
//        Paragraph par = new Paragraph("\n\nPrinted On: " + sdf.format(new Date()) + ". By: " + loggedInUser.getAdmin().getFirstName() + " ", font1);
//        par.setAlignment(Element.ALIGN_RIGHT);
//        document.add(par);
        document.close();
        String fileName = "Report_" + new Date().getTime() / (1000 * 3600 * 24);
        writePDFToResponse(context.getExternalContext(), baos, fileName);
        context.responseComplete();
    }

    public void generateLecturerReport() throws FileNotFoundException, DocumentException, BadElementException, IOException, Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        FacesContext context = FacesContext.getCurrentInstance();
        Document document = new Document();
        Rectangle rect = new Rectangle(0, 0, 600, 700);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance((com.lowagie.text.Document) document, baos);
        writer.setBoxSize("art", rect);
        writer.setPageEvent(new MyPdfPageEventHelper());
        document.setPageSize(rect);
        if (!document.isOpen()) {
            document.open();
        }
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
//        path = path.substring(0, path.indexOf("\\build"));
//        path = path + "\\web\\reportDesign\\header.jpeg";
//        Image image = Image.getInstance(path);
//        image.scaleAbsolute(600, 140);
//        image.setAlignment(Element.ALIGN_CENTER);
//        Paragraph title = new Paragraph();
//        //BEGIN page
//        title.add(image);
//        document.add(title);
        Font font0 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        Font font1 = new Font(Font.TIMES_ROMAN, 14, Font.ITALIC, new Color(37, 46, 158));
        Font font2 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL, new Color(0, 0, 0));
        Font font5 = new Font(Font.TIMES_ROMAN, 10, Font.ITALIC, new Color(0, 0, 0));
        Font colorFont = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0));
        Font font6 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
//        document.add(new Paragraph("SDORG Application\n"));
//        document.add(new Paragraph("KG 625 ST 4\n", font0));
//        document.add(new Paragraph("P.O.BOX 131 \n", font0));
//        document.add(new Paragraph("KIGALI-RWANDA\n\n", font0));
        Paragraph p = new Paragraph("UNIVERSITY LECTURERS REPORT ", colorFont);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Date: " + sdf.format(new Date())));
        document.add(new Paragraph("\n"));
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\divider.jpeg";
        Image image1 = Image.getInstance(path);
        image1.scaleAbsolute(600, 20);
        image1.setAlignment(Element.ALIGN_CENTER);
        Paragraph title1 = new Paragraph();
        //BEGIN page
        title1.add(image1);
        document.add(title1);
        document.add(new Paragraph("\n"));
        PdfPTable tables = new PdfPTable(5);
        tables.setWidthPercentage(100);

        PdfPCell cell1 = new PdfPCell(new Phrase("#", font2));
        cell1.setBorder(Rectangle.BOX);
        tables.addCell(cell1);

        PdfPCell c2 = new PdfPCell(new Phrase("Lecturer Name", font2));
        c2.setBorder(Rectangle.BOX);
        tables.addCell(c2);

        PdfPCell c3 = new PdfPCell(new Phrase("Phone Number", font2));
        c3.setBorder(Rectangle.BOX);
        tables.addCell(c3);

        PdfPCell c4 = new PdfPCell(new Phrase("Email", font2));
        c4.setBorder(Rectangle.BOX);
        tables.addCell(c4);

        PdfPCell c5 = new PdfPCell(new Phrase("Faculty", font2));
        c5.setBorder(Rectangle.BOX);
        tables.addCell(c5);

//        PdfPCell c6 = new PdfPCell(new Phrase("Status", font2));
//        c6.setBorder(Rectangle.BOX);
//        tables.addCell(c6);
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
        for (Lecturer x : lecturers) {
            pdfc5 = new PdfPCell(new Phrase(i + ""));
            pdfc5.setBorder(Rectangle.BOX);
            tables.addCell(pdfc5);

            pdfc4 = new PdfPCell(new Phrase(x.getFirstName() + " " + x.getLastName() + "", font6));
            pdfc4.setBorder(Rectangle.BOX);
            tables.addCell(pdfc4);

            pdfc3 = new PdfPCell(new Phrase(x.getPhone() + "", font6));
            pdfc3.setBorder(Rectangle.BOX);
            tables.addCell(pdfc3);

            pdfc2 = new PdfPCell(new Phrase(x.getEmail() + "", font6));
            pdfc2.setBorder(Rectangle.BOX);
            tables.addCell(pdfc2);

            pdfc1 = new PdfPCell(new Phrase(x.getFaculty().getName() + "", font6));
            pdfc1.setBorder(Rectangle.BOX);
            tables.addCell(pdfc1);

//            pdfc6 = new PdfPCell(new Phrase(x.getMovementStatus() + "", font6));
//            pdfc6.setBorder(Rectangle.BOX);
//            tables.addCell(pdfc6);
            i++;
        }
        document.add(tables);
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\sign.jpeg";
        Image image2 = Image.getInstance(path);
        image2.scaleAbsolute(120, 120);
        image2.setAlignment(Element.ALIGN_RIGHT);
        Paragraph title2 = new Paragraph();
        //BEGIN page
        title2.add(image2);
        document.add(title2);
        document.add(new Paragraph("\n"));
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\footer.jpeg";
        Image image3 = Image.getInstance(path);
        image3.scaleAbsolute(600, 40);
        image3.setAlignment(Element.ALIGN_BASELINE);
        Paragraph title3 = new Paragraph();
        //BEGIN page
        title3.add(image3);
        document.add(title3);
        document.add(new Paragraph("\n"));
//        Paragraph par = new Paragraph("\n\nPrinted On: " + sdf.format(new Date()) + ". By: " + loggedInUser.getAdmin().getFirstName() + " ", font1);
//        par.setAlignment(Element.ALIGN_RIGHT);
//        document.add(par);
        document.close();
        String fileName = "Report_" + new Date().getTime() / (1000 * 3600 * 24);
        writePDFToResponse(context.getExternalContext(), baos, fileName);
        context.responseComplete();
    }

    public void generateStudentReport() throws FileNotFoundException, DocumentException, BadElementException, IOException, Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        FacesContext context = FacesContext.getCurrentInstance();
        Document document = new Document();
        Rectangle rect = new Rectangle(0, 0, 600, 700);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance((com.lowagie.text.Document) document, baos);
        writer.setBoxSize("art", rect);
        writer.setPageEvent(new MyPdfPageEventHelper());
        document.setPageSize(rect);
        if (!document.isOpen()) {
            document.open();
        }
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
//        path = path.substring(0, path.indexOf("\\build"));
//        path = path + "\\web\\reportDesign\\header.jpeg";
//        Image image = Image.getInstance(path);
//        image.scaleAbsolute(600, 140);
//        image.setAlignment(Element.ALIGN_CENTER);
//        Paragraph title = new Paragraph();
//        //BEGIN page
//        title.add(image);
//        document.add(title);
        Font font0 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        Font font1 = new Font(Font.TIMES_ROMAN, 14, Font.ITALIC, new Color(37, 46, 158));
        Font font2 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL, new Color(0, 0, 0));
        Font font5 = new Font(Font.TIMES_ROMAN, 10, Font.ITALIC, new Color(0, 0, 0));
        Font colorFont = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0));
        Font font6 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
//        document.add(new Paragraph("SDORG Application\n"));
//        document.add(new Paragraph("KG 625 ST 4\n", font0));
//        document.add(new Paragraph("P.O.BOX 131 \n", font0));
//        document.add(new Paragraph("KIGALI-RWANDA\n\n", font0));
        Paragraph p = new Paragraph("UNIVERSITY STUDENT REPORT ", colorFont);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Date: " + sdf.format(new Date())));
        document.add(new Paragraph("\n"));
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\divider.jpeg";
        Image image1 = Image.getInstance(path);
        image1.scaleAbsolute(600, 20);
        image1.setAlignment(Element.ALIGN_CENTER);
        Paragraph title1 = new Paragraph();
        //BEGIN page
        title1.add(image1);
        document.add(title1);
        document.add(new Paragraph("\n"));
        PdfPTable tables = new PdfPTable(6);
        tables.setWidthPercentage(100);

        PdfPCell cell1 = new PdfPCell(new Phrase("#", font2));
        cell1.setBorder(Rectangle.BOX);
        tables.addCell(cell1);

        PdfPCell c2 = new PdfPCell(new Phrase("Student Name", font2));
        c2.setBorder(Rectangle.BOX);
        tables.addCell(c2);

        PdfPCell c3 = new PdfPCell(new Phrase("Phone Number", font2));
        c3.setBorder(Rectangle.BOX);
        tables.addCell(c3);

        PdfPCell c4 = new PdfPCell(new Phrase("Email", font2));
        c4.setBorder(Rectangle.BOX);
        tables.addCell(c4);

        PdfPCell c5 = new PdfPCell(new Phrase("Department", font2));
        c5.setBorder(Rectangle.BOX);
        tables.addCell(c5);

        PdfPCell c6 = new PdfPCell(new Phrase("Faculty", font2));
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
        for (Student x : students) {
            pdfc5 = new PdfPCell(new Phrase(i + ""));
            pdfc5.setBorder(Rectangle.BOX);
            tables.addCell(pdfc5);

            pdfc4 = new PdfPCell(new Phrase(x.getFirstName() + " " + x.getLastName() + "", font6));
            pdfc4.setBorder(Rectangle.BOX);
            tables.addCell(pdfc4);

            pdfc3 = new PdfPCell(new Phrase(x.getPhone() + "", font6));
            pdfc3.setBorder(Rectangle.BOX);
            tables.addCell(pdfc3);

            pdfc2 = new PdfPCell(new Phrase(x.getEmail() + "", font6));
            pdfc2.setBorder(Rectangle.BOX);
            tables.addCell(pdfc2);

            pdfc1 = new PdfPCell(new Phrase(x.getDepartment().getName() + "", font6));
            pdfc1.setBorder(Rectangle.BOX);
            tables.addCell(pdfc1);

            pdfc6 = new PdfPCell(new Phrase(x.getDepartment().getFaculty().getName() + "", font6));
            pdfc6.setBorder(Rectangle.BOX);
            tables.addCell(pdfc6);

            i++;
        }
        document.add(tables);
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\sign.jpeg";
        Image image2 = Image.getInstance(path);
        image2.scaleAbsolute(120, 120);
        image2.setAlignment(Element.ALIGN_RIGHT);
        Paragraph title2 = new Paragraph();
        //BEGIN page
        title2.add(image2);
        document.add(title2);
        document.add(new Paragraph("\n"));
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\footer.jpeg";
        Image image3 = Image.getInstance(path);
        image3.scaleAbsolute(600, 40);
        image3.setAlignment(Element.ALIGN_BASELINE);
        Paragraph title3 = new Paragraph();
        //BEGIN page
        title3.add(image3);
        document.add(title3);
        document.add(new Paragraph("\n"));
//        Paragraph par = new Paragraph("\n\nPrinted On: " + sdf.format(new Date()) + ". By: " + loggedInUser.getAdmin().getFirstName() + " ", font1);
//        par.setAlignment(Element.ALIGN_RIGHT);
//        document.add(par);
        document.close();
        String fileName = "Report_" + new Date().getTime() / (1000 * 3600 * 24);
        writePDFToResponse(context.getExternalContext(), baos, fileName);
        context.responseComplete();
    }

    class MyPdfPageEventHelper extends PdfPageEventHelper {

        @Override
        public void onEndPage(PdfWriter pdfWriter, Document document) {

//            System.out.println("Creating Waterwark Image in PDF");
            try {
                String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
                path = path.substring(0, path.indexOf("\\build"));
                path = path + "\\web\\reportDesign\\logoedited.png";
                Image waterMarkImage = Image.getInstance(path);

                //Get width and height of whole page
                float pdfPageWidth = document.getPageSize().getWidth();
                float pdfPageHeight = document.getPageSize().getHeight();

                //Set waterMarkImage on whole page
                pdfWriter.getDirectContentUnder().addImage(waterMarkImage,
                        pdfPageWidth, 0, 0, pdfPageHeight, 0, 0);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void generateStaffReport() throws FileNotFoundException, DocumentException, BadElementException, IOException, Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        FacesContext context = FacesContext.getCurrentInstance();
        Document document = new Document();
        Rectangle rect = new Rectangle(0, 0, 600, 700);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance((com.lowagie.text.Document) document, baos);
        writer.setBoxSize("art", rect);
        writer.setPageEvent(new MyPdfPageEventHelper());
        document.setPageSize(rect);
        if (!document.isOpen()) {
            document.open();
        }
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
//        path = path.substring(0, path.indexOf("\\build"));
//        path = path + "\\web\\reportDesign\\header.jpeg";
//        Image image = Image.getInstance(path);
//        image.scaleAbsolute(600, 140);
//        image.setAlignment(Element.ALIGN_CENTER);
//        Paragraph title = new Paragraph();
//        //BEGIN page
//        title.add(image);
//        document.add(title);
        Font font0 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
        Font font1 = new Font(Font.TIMES_ROMAN, 14, Font.ITALIC, new Color(37, 46, 158));
        Font font2 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL, new Color(0, 0, 0));
        Font font5 = new Font(Font.TIMES_ROMAN, 10, Font.ITALIC, new Color(0, 0, 0));
        Font colorFont = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, new Color(0, 0, 0));
        Font font6 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL);
//        document.add(new Paragraph("SDORG Application\n"));
//        document.add(new Paragraph("KG 625 ST 4\n", font0));
//        document.add(new Paragraph("P.O.BOX 131 \n", font0));
//        document.add(new Paragraph("KIGALI-RWANDA\n\n", font0));
        Paragraph p = new Paragraph("UNIVERSITY STAFF REPORT ", colorFont);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Date: " + sdf.format(new Date())));
        document.add(new Paragraph("\n"));
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\divider.jpeg";
        Image image1 = Image.getInstance(path);
        image1.scaleAbsolute(600, 20);
        image1.setAlignment(Element.ALIGN_CENTER);
        Paragraph title1 = new Paragraph();
        //BEGIN page
        title1.add(image1);
        document.add(title1);
        document.add(new Paragraph("\n"));
        PdfPTable tables = new PdfPTable(4);
        tables.setWidthPercentage(100);

        PdfPCell cell1 = new PdfPCell(new Phrase("#", font2));
        cell1.setBorder(Rectangle.BOX);
        tables.addCell(cell1);

        PdfPCell c2 = new PdfPCell(new Phrase("Staff Name", font2));
        c2.setBorder(Rectangle.BOX);
        tables.addCell(c2);

        PdfPCell c3 = new PdfPCell(new Phrase("Phone Number", font2));
        c3.setBorder(Rectangle.BOX);
        tables.addCell(c3);

        PdfPCell c4 = new PdfPCell(new Phrase("Email", font2));
        c4.setBorder(Rectangle.BOX);
        tables.addCell(c4);

//        PdfPCell c5 = new PdfPCell(new Phrase("Faculty", font2));
//        c5.setBorder(Rectangle.BOX);
//        tables.addCell(c5);
//        PdfPCell c6 = new PdfPCell(new Phrase("Status", font2));
//        c6.setBorder(Rectangle.BOX);
//        tables.addCell(c6);
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
        for (Security x : securits) {
            pdfc5 = new PdfPCell(new Phrase(i + ""));
            pdfc5.setBorder(Rectangle.BOX);
            tables.addCell(pdfc5);

            pdfc4 = new PdfPCell(new Phrase(x.getFirstName() + " " + x.getLastName() + "", font6));
            pdfc4.setBorder(Rectangle.BOX);
            tables.addCell(pdfc4);

            pdfc3 = new PdfPCell(new Phrase(x.getPhone() + "", font6));
            pdfc3.setBorder(Rectangle.BOX);
            tables.addCell(pdfc3);

            pdfc2 = new PdfPCell(new Phrase(x.getEmail() + "", font6));
            pdfc2.setBorder(Rectangle.BOX);
            tables.addCell(pdfc2);

//            pdfc1 = new PdfPCell(new Phrase(x.getFaculty().getName() + "", font6));
//            pdfc1.setBorder(Rectangle.BOX);
//            tables.addCell(pdfc1);
//            pdfc6 = new PdfPCell(new Phrase(x.getMovementStatus() + "", font6));
//            pdfc6.setBorder(Rectangle.BOX);
//            tables.addCell(pdfc6);
            i++;
        }
        document.add(tables);
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\sign.jpeg";
        Image image2 = Image.getInstance(path);
        image2.scaleAbsolute(120, 120);
        image2.setAlignment(Element.ALIGN_RIGHT);
        Paragraph title2 = new Paragraph();
        //BEGIN page
        title2.add(image2);
        document.add(title2);
        document.add(new Paragraph("\n"));
        path = new String();
        path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\reportDesign");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\reportDesign\\footer.jpeg";
        Image image3 = Image.getInstance(path);
        image3.scaleAbsolute(600, 40);
        image3.setAlignment(Element.ALIGN_BASELINE);
        Paragraph title3 = new Paragraph();
        //BEGIN page
        title3.add(image3);
        document.add(title3);
        document.add(new Paragraph("\n"));
//        Paragraph par = new Paragraph("\n\nPrinted On: " + sdf.format(new Date()) + ". By: " + loggedInUser.getAdmin().getFirstName() + " ", font1);
//        par.setAlignment(Element.ALIGN_RIGHT);
//        document.add(par);
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public List<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }

    public List<String> getChosenImage() {
        return chosenImage;
    }

    public void setChosenImage(List<String> chosenImage) {
        this.chosenImage = chosenImage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<User> getStudentUsers() {
        return studentUsers;
    }

    public void setStudentUsers(List<User> studentUsers) {
        this.studentUsers = studentUsers;
    }

    public List<User> getStaffUsers() {
        return staffUsers;
    }

    public void setStaffUsers(List<User> staffUsers) {
        this.staffUsers = staffUsers;
    }

    public List<User> getLecturerUsers() {
        return lecturerUsers;
    }

    public void setLecturerUsers(List<User> lecturerUsers) {
        this.lecturerUsers = lecturerUsers;
    }

    public List<User> getAdminUsers() {
        return adminUsers;
    }

    public void setAdminUsers(List<User> adminUsers) {
        this.adminUsers = adminUsers;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public List<Movement> getUniversityDevices() {
        return universityDevices;
    }

    public void setUniversityDevices(List<Movement> universityDevices) {
        this.universityDevices = universityDevices;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getUploadedFileName() {
        return uploadedFileName;
    }

    public void setUploadedFileName(String uploadedFileName) {
        this.uploadedFileName = uploadedFileName;
    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public void setPieModel1(PieChartModel pieModel1) {
        this.pieModel1 = pieModel1;
    }

    public PieChartModel getPieModel2() {
        return pieModel2;
    }

    public void setPieModel2(PieChartModel pieModel2) {
        this.pieModel2 = pieModel2;
    }

    public List<Movement> getUniversityExitedDevices() {
        return universityExitedDevices;
    }

    public void setUniversityExitedDevices(List<Movement> universityExitedDevices) {
        this.universityExitedDevices = universityExitedDevices;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public List<Security> getSecurits() {
        return securits;
    }

    public void setSecurits(List<Security> securits) {
        this.securits = securits;
    }

    public List<User> getSecurityUsers() {
        return securityUsers;
    }

    public void setSecurityUsers(List<User> securityUsers) {
        this.securityUsers = securityUsers;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }

    public BarChartModel getBarChartModel1() {
        return barChartModel1;
    }

    public void setBarChartModel1(BarChartModel barChartModel1) {
        this.barChartModel1 = barChartModel1;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

}
