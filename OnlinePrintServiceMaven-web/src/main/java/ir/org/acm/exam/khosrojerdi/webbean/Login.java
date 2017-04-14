package ir.org.acm.exam.khosrojerdi.webbean;

import ir.org.acm.exam.khosrojerdi.dao.local.UserDaoLocalFacadeInf;
import ir.org.acm.exam.khosrojerdi.entity.UserOPS;
import ir.org.acm.exam.khosrojerdi.utils.UtilUserSession;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;
import javax.servlet.http.HttpSession;


@Named
@SessionScoped
public class Login implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private UserDaoLocalFacadeInf dao;
    
    private final ShowMsgUtil errMsg = new ShowMsgUtil();

    private String username;
    private String password;
    private boolean loginStatus = false;
    private List<UserOPS> userList = null;
    

    private boolean admin = false;
    
   

    public Login() {
//        this.isWaiting = true;
    }

    public String goToMainPage() {
        String outcome = "";
        if (isAdmin()) {
            outcome = NavigationUtil.getADMIN_PATH();
        } else {
            outcome = NavigationUtil.getUSER_PATH();
        }
        return outcome;
    }

    private void addToSession() {
        //get http session and store username
        HttpSession session = UtilUserSession.getSession();
        session.setAttribute("username", username);
        session.setAttribute("isAdmin",admin);
    }

    public String loginAction() {
        
        String outcome = "";

        userList = dao.findByUsernameAndPassword(username, password);
        if (userList.size() == 1) {            
            if (userList.get(0).getAdminStatus()) {

                outcome = NavigationUtil.getADMIN_PATH();
                admin = true;
                loginStatus = true;
            } else {
                admin = false;
                outcome = NavigationUtil.getUSER_PATH();
                loginStatus = true;
            }
            addToSession();

        } else {
            
            errMsg.showErrorMessage("Invalid Login!<br/>Please Try Again!");            
            outcome = null;
            loginStatus = false;
        }

        if (outcome != null) {
            outcome += ".xhtml?faces-redirect=true";
        }
        System.out.println("path:"+outcome);
        return outcome;

    }

    public String logoutAction() {
        
        String outcome = "";
        if (loginStatus) {
            loginStatus = false;
            HttpSession session = UtilUserSession.getSession();
            session.invalidate();
        }
        // System.out.println("ReqCtxPath: " + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
        //outcome = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        outcome = "/index.xhtml?faces-redirect=true";

        return outcome;
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

    public boolean isLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<UserOPS> getUserList() {
        return userList;
    }

    public void setUserList(List<UserOPS> userList) {
        this.userList = userList;
    }   

}
