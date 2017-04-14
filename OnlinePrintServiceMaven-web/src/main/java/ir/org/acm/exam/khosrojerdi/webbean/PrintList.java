
package ir.org.acm.exam.khosrojerdi.webbean;

import ir.org.acm.exam.khosrojerdi.dao.local.PrinterDaoLocalFacadeInf;
import ir.org.acm.exam.khosrojerdi.entity.PrinterOPS;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;


@Named
@RequestScoped
public class PrintList implements Serializable {


    @EJB
    private PrinterDaoLocalFacadeInf dao;
    private String message = "";
    private DataModel<PrinterOPS> model;


    public String removePrinterAction(PrinterOPS printer) {
        try {
            dao.delete(printer);
        } catch (Exception ex) {
            new ShowMsgUtil().showErrorMessage(ex.getMessage());
        }
        new ShowMsgUtil().showInfoMessage("user removed successfuly...");
        return null;
    }

    public void showEditAction(PrinterOPS printer) {
        FacesContext.getCurrentInstance().getAttributes().put("printer", printer);
        NavigationUtil nu = NavigationUtil.getNavigationUtil();
        nu.navigateAction("Admin/edit_printer");
    }

    public void showAddAction() {
        NavigationUtil nu = NavigationUtil.getNavigationUtil();
        nu.navigateAction("Admin/add_printer");
    }

    public DataModel<PrinterOPS> getModel() {
        return model;
    }

    public void setModel(DataModel<PrinterOPS> model) {
        this.model = model;
    }

    public DataModel<PrinterOPS> getListOfPrinters() {
        List<PrinterOPS> list = dao.findAll();
        model = new ListDataModel<PrinterOPS>(list);
        model.setRowIndex(1);
        message = "size = " + list.size();
        return model;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
