package ir.org.acm.exam.khosrojerdi.webbean;

import ir.org.acm.exam.khosrojerdi.dao.local.UserDaoLocalFacadeInf;
import ir.org.acm.exam.khosrojerdi.entity.UserOPS;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;


@Named
@RequestScoped
public class UserList implements Serializable {


    @EJB
    private UserDaoLocalFacadeInf dao;
    private String message = "";
    private DataModel<UserOPS> model;

    public String removeUserAction(UserOPS user) {
        try {
            dao.delete(user);
        } catch (Exception ex) {
            new ShowMsgUtil().showErrorMessage(ex.getMessage());
        }
        new ShowMsgUtil().showInfoMessage("user removed successfuly...");
        return null;
    }

    public void showEditAction(UserOPS user) {
        FacesContext.getCurrentInstance().getAttributes().put("user", user);
        NavigationUtil nu = NavigationUtil.getNavigationUtil();
        nu.navigateAction("Admin/edit_user");
    }

    public void showOrderDetail(UserOPS user) {
        if (FacesContext.getCurrentInstance().getAttributes().containsKey("user"))
            FacesContext.getCurrentInstance().getAttributes().replace("user", user);
        else
            FacesContext.getCurrentInstance().getAttributes().put("user", user);

        NavigationUtil nu = NavigationUtil.getNavigationUtil();
        nu.navigateAction("Admin/order_report_order");
    }

    public DataModel<UserOPS> getModel() {
        return model;
    }

    public void setModel(DataModel<UserOPS> model) {
        this.model = model;
    }

    public DataModel<UserOPS> getListOfUsers() {
        model = new ListDataModel<>(dao.findAll());
        model.setRowIndex(1);
        message = "total: " + model.getRowCount();
        return model;
    }

    public DataModel<UserOPS> getListOfUsersOrder() {
        List<UserOPS> users =  dao.findAll().stream()
                .filter(u -> u.getAdminStatus() == false)
                .collect(Collectors.toList());
        model = new ListDataModel<>(users);
        model.setRowIndex(1);
        message = "total: " + model.getRowCount();
        return model;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
