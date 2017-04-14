package ir.org.acm.exam.khosrojerdi.webbean;

import ir.org.acm.exam.khosrojerdi.dao.local.UserDaoLocalFacadeInf;
import ir.org.acm.exam.khosrojerdi.entity.UserOPS;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@RequestScoped
public class EditUser {

    @EJB
    private UserDaoLocalFacadeInf dao;
    private String message = "";
    private String username = "";
    private String pass = "";
    private String confirmPass = "";
    private boolean adminStatus;
    private UserOPS oldUser;
    private long id;
    private String role;
    private String currentPassword;
    private String newPassword;
    private String retypeNewPassword;
    @Inject
    private Login login;

    public void cancelAction() {
        NavigationUtil nu = NavigationUtil.getNavigationUtil();
        nu.navigateAction("Admin/user_management");
    }

    public void changePasswordAction() {
        String msgType = "";
        try {
            if (login.getPassword().equals(this.currentPassword)) {
                if (this.newPassword.equals(this.retypeNewPassword)) {
                    UserOPS user = dao.find(login.getUserList().get(0).getId());
                    user.setPassword(newPassword);
                    dao.update(user);
                    message = "Password changed successfully...";
                    msgType = "Info";
                } else {
                    message = "new password is not the same as retype password...";
                    msgType = "Error";
                }
            } else {
                message = "Current Password is wrong!";
                msgType = "Error";
            }
        } catch (Exception ex) {
            message = ex.getMessage();
            msgType = "Error";
        } finally {
            if (msgType.equals("Error")) {
                new ShowMsgUtil().showErrorMessage(message);
            }
            if (msgType.equals("Info")) {
                new ShowMsgUtil().showInfoMessage(message);
            }
        }
    }

    public void editUserAction() {
        //oldUser = (UserOPS) FacesContext.getCurrentInstance().getAttributes().get("user");
        oldUser = dao.find(id);
        if (oldUser != null) {
            if (getPass().equals(getConfirmPass())) {
                oldUser.setUsername(username);
                oldUser.setPassword(pass);
                oldUser.setAdminStatus(adminStatus); 
                oldUser.setRole(role);
                try {
                    dao.update(oldUser);
                } catch (Exception ex) {
                    new ShowMsgUtil().showErrorMessage(ex.getMessage());
                    return;
                }
                cancelAction();
            } else {
                new ShowMsgUtil().showErrorMessage("Password is not equal with Retype Password!!!");
            }

        } else {
            String err = "user is null...";
            new ShowMsgUtil().showErrorMessage(err);
        }

    }

    public EditUser() {
        oldUser = (UserOPS) FacesContext.getCurrentInstance().getAttributes().get("user");
        if (oldUser != null) {
            this.username = oldUser.getUsername();
            this.adminStatus = oldUser.getAdminStatus();
            
            this.id = oldUser.getId();
        }

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(boolean adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    public UserOPS getOldUser() {
        return oldUser;
    }

    public void setOldUser(UserOPS oldUser) {
        this.oldUser = oldUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRetypeNewPassword() {
        return retypeNewPassword;
    }

    public void setRetypeNewPassword(String retypeNewPassword) {
        this.retypeNewPassword = retypeNewPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    

}
