package model;

import common.FileUpload;
import common.PassCode;
import dao.DepartmentDao;
import dao.DeviceDao;
import dao.FacultyDao;
import dao.LecturerDao;
import dao.MovementDao;
import dao.PersonDao;
import dao.StaffDao;
import dao.StudentDao;
import dao.UserDao;
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
import domain.Staff;
import domain.Student;
import domain.University;
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
public class AdminModel {

    private Student student = new Student();
    private Lecturer lecturer = new Lecturer();
    private University university = new University();
    private User user = new User();
    private Staff staff = new Staff();
    private User loggedInUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("session");
    private List<Student> students = new StudentDao().findByUniversity(loggedInUser.getAdmin().getUniversity());
    private List<Lecturer> lecturers = new LecturerDao().findByUniversity(loggedInUser.getAdmin().getUniversity());
    private List<Staff> staffs = new StaffDao().findByUniversity(loggedInUser.getAdmin().getUniversity());
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
//    private List<Movement> universityDevices = new ArrayList<>();
    private List<Movement> universityDevices = new MovementDao().findByUniversity(loggedInUser.getAdmin().getUniversity(), EMovementStatus.CHECKED_IN);
    
    public void registerFaculty() {
        faculty.setUniversity(loggedInUser.getAdmin().getUniversity());
        new FacultyDao().register(faculty);
        faculty = new Faculty();
        faculties = new FacultyDao().findByUniversity(loggedInUser.getAdmin().getUniversity());

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Faculty Registered"));
    }

    public void registerDepartment() {
        Faculty f = new FacultyDao().findOne(Faculty.class, facultyId);
        department.setFaculty(f);
        new DepartmentDao().register(department);
        department = new Department();
        departments = new DepartmentDao().findByUniversity(loggedInUser.getAdmin().getUniversity());

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Department Registered"));

    }

    public void registerStudent() throws Exception {
        if (new StudentDao().findOne(Student.class, student.getNationalId()) != null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage("National ID already used"));
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

    public void disable(User user) {
        user.setStatus(EStatus.INACTIVE);
        new UserDao().update(user);

        studentUsers = new UserDao().findByAccess(EUserType.STUDENT);
        staffUsers = new UserDao().findByAccess(EUserType.SECURITY);
        lecturerUsers = new UserDao().findByAccess(EUserType.LECTURER);
        adminUsers = new UserDao().findByAccess(EUserType.ADMIN);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage("Account Disabled"));
    }

    public void Upload(FileUploadEvent event) {
        chosenImage.add(new FileUpload().Upload(event, "C:\\Users\\nizey\\OneDrive\\Documents\\NetBeansProjects\\Thesis\\SDORG\\web\\uploads\\profile\\"));
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

}
