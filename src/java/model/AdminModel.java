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
    private String from;
    private String to;
    private final List<Movement> movements = new MovementDao().findByUniversityLogged(loggedInUser.getAdmin().getUniversity());
    private final List<Accusation> accusations = new AccusationDao().findByUniversity(loggedInUser.getAdmin().getUniversity());

    @PostConstruct
    public void init() {
        createPieModel1();
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
        if (new StudentDao().findOne(Student.class, student.getNationalId()) != null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("National ID already used"));
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

    public void registerLecturer() throws Exception {
        if (new LecturerDao().findOne(Lecturer.class, lecturer.getNationalId()) != null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("National ID already used"));
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

    public void registerSecurityGuard() throws Exception {
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
                student.setGender(EGender.MALE);
            } else {
                student.setGender(EGender.FEMALE);
            }
            staff.setPersonType("Staff");
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

    public void registerStaff() throws Exception {
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
            security.setPersonType("Security");
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

    public void generatemovementreport() throws FileNotFoundException, DocumentException, BadElementException, IOException, Exception {

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
        Paragraph p = new Paragraph("University Movements Report ", colorFont);
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
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
        Paragraph par = new Paragraph("\n\nPrinted On: " + sdf.format(new Date()) + ". By: " + loggedInUser.getAdmin().getFirstName() + " ", font1);
        par.setAlignment(Element.ALIGN_RIGHT);
        document.add(par);
        document.close();
        String fileName = "Report_" + new Date().getTime() / (1000 * 3600 * 24);
        writePDFToResponse(context.getExternalContext(), baos, fileName);
        context.responseComplete();
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
        Paragraph p = new Paragraph("University Complaints Report ", colorFont);
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
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
        Paragraph par = new Paragraph("\n\nPrinted On: " + sdf.format(new Date()) + ". By: " + loggedInUser.getAdmin().getFirstName() + " ", font1);
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

}
