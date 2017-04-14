package ir.org.acm.exam.khosrojerdi.webbean;

import ir.org.acm.exam.khosrojerdi.dao.local.OrderDaoLocalFacadeInf;
import ir.org.acm.exam.khosrojerdi.entity.OrderOPS;
import ir.org.acm.exam.khosrojerdi.entity.UserOPS;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class OrderList implements Serializable {

    private static final long serialVersionUID = 1L;
    private DataModel<OrderOPS> model;
    private String username = "";

    private final ShowMsgUtil showMsgUtil = new ShowMsgUtil();

    private String message = "";
    @EJB
    private OrderDaoLocalFacadeInf dao;

    @Inject
    private Login login;

    private UserOPS user;

    public OrderList() {
        user = (UserOPS) FacesContext.getCurrentInstance().getAttributes().get("user");
    }

    public void removeOrder(OrderOPS order) {
        if (order != null) {
            try {
                dao.delete(order);
                showMsgUtil.showInfoMessage("order is removed successfully.");
            } catch (Exception e) {
                showMsgUtil.showErrorMessage(e.getMessage());
            }
        }
    }

    public DataModel<OrderOPS> getListOfUserOrders() {
        if (user == null) {
            user = login.getUserList().get(0);
            if (user != null)
                model = new ListDataModel<>(dao.findOrderByUser(user));

        } else {
            model = new ListDataModel<>(dao.findOrderByUser(user));
        }
        model.setRowIndex(1);
        message = "total: " + model.getRowCount();
        return model;
    }

    public DataModel<OrderOPS> getModel() {
        return model;
    }

    public void setModel(DataModel<OrderOPS> model) {
        this.model = model;
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

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

}
