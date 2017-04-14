package ir.org.acm.exam.khosrojerdi.webbean;

import ir.org.acm.exam.khosrojerdi.dao.local.OrderDaoLocalFacadeInf;
import ir.org.acm.exam.khosrojerdi.entity.OrderOPS;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.CollectionDataModel;
import javax.faces.model.DataModel;
import javax.inject.Named;


@Named
@RequestScoped
public class OrderListManagement {

    private String message = "";
    @EJB
    private OrderDaoLocalFacadeInf dao;
    private DataModel<OrderOPS> model;

    public DataModel<OrderOPS> getModel() {
        return model;
    }

    public void setModel(DataModel<OrderOPS> model) {
        this.model = model;
    }
    
    public DataModel<OrderOPS> getListAllOrders() {
        List<OrderOPS> list = dao.findAll();
        model = new CollectionDataModel<OrderOPS>(list);
        model.setRowIndex(1);
        message = "size of order = " + list.size();
        return model;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
