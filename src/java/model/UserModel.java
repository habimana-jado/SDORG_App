package model;

import dao.UserDao;
import domain.EStatus;
import domain.User;
import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class UserModel {

    private User user = new User();

    private User userDetails = new User();

    private UserDao userDao = new UserDao();

    private List<User> users;

    private String username = new String();

    private String password = new String();

    private String userdetails = new String();

    private String sid = new String();

    private String sectid = new String();

    private User u = new User();


    public String login() throws IOException, Exception {
        findUser();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        if (user != null && user.getStatus().equals(EStatus.ACTIVE)) {

            switch (user.getUserType()) {
                case SUPERADMIN:
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
                    ec.redirect(ec.getRequestContextPath() + "/pages/superadmin/university.xhtml");
                    return "pages/superadmin/university.xhtml?faces-redirect=true";
                case ADMIN:
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
                    ec.redirect(ec.getRequestContextPath() + "/pages/admin/dashboard.xhtml");
                    return "pages/admin/dashboard.xhtml?faces-redirect=true";
                case SECURITY:
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
                    ec.redirect(ec.getRequestContextPath() + "/pages/security/search.xhtml");
                    return "pages/security/search.xhtml?faces-redirect=true";
                case LECTURER:
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
                    ec.redirect(ec.getRequestContextPath() + "/pages/lecturer/devices.xhtml");
                    return "pages/lecturer/devices.xhtml?faces-redirect=true";
                case STUDENT:
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
                    ec.redirect(ec.getRequestContextPath() + "/pages/student/devices.xhtml");
                    return "pages/student/devices.xhtml?faces-redirect=true";
                case STAFF:
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("session", user);
                    ec.redirect(ec.getRequestContextPath() + "/pages/staff/devices.xhtml");
                    return "pages/staff/devices.xhtml?faces-redirect=true";
                default:
                    user = null;

                    ec.redirect(ec.getRequestContextPath() + "/index.xhtml");

                    return "/SDORG/index.xhtml";
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Wrong Password Or Username"));
            ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
            return "index.xhtml";
        }

    }

    public void findUser() throws Exception {
        List<User> usersLogin = new UserDao().loginencrypt(username, password);

        if (!usersLogin.isEmpty()) {
            for (User u : usersLogin) {
                user = u;
            }
        } else {
            user = null;
        }
    }

    public void logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        user = null;
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(User userDetails) {
        this.userDetails = userDetails;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserdetails() {
        return userdetails;
    }

    public void setUserdetails(String userdetails) {
        this.userdetails = userdetails;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSectid() {
        return sectid;
    }

    public void setSectid(String sectid) {
        this.sectid = sectid;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

}
