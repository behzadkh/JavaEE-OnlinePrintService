package ir.org.acm.exam.khosrojerdi.webbean;

import ir.org.acm.exam.khosrojerdi.dao.local.UserDaoLocalFacadeInf;
import ir.org.acm.exam.khosrojerdi.entity.UserOPS;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;


@Named
@RequestScoped
public class Register implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private UserDaoLocalFacadeInf dao;

    private String username;
    private String password;
    private String err = "";

    public Register() {

    }

    public String registerAction() {
        
        String outcome = "";

        UserOPS user = new UserOPS();
        user.setAdminStatus(false);
        user.setUsername(username);
        user.setPassword(password);
        boolean checkUser ;
        try {
//            Thread.sleep(10_000);
            dao.save(user);
            checkUser = true;
        } catch (Exception ex) {
            checkUser = false;
        }

        if (!checkUser) {
            err = "user has already existed.";
            new ShowMsgUtil().showErrorMessage(err);
            System.out.println(err);
        } else {
            err = "user registered successfully...";
            new ShowMsgUtil().showInfoMessage(err);
            System.out.println(err);
        }

        return outcome;
    }

    public void clearBoxes() {
        username = null;
        password = null;
        err = null;
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

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

}
