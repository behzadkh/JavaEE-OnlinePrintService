package ir.org.acm.exam.khosrojerdi.webbean;

import ir.org.acm.exam.khosrojerdi.dao.local.PrinterDaoLocalFacadeInf;
import ir.org.acm.exam.khosrojerdi.entity.PrinterOPS;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;


@Named
@RequestScoped
public class EditPrinter implements Serializable {

    @EJB
    private PrinterDaoLocalFacadeInf dao;

    private String name;
    private String webServiceContext;
    private long id;

    @Inject
    private ShowMsgUtil showMsgUtil;

    public void cancelAction() {
        NavigationUtil nu = NavigationUtil.getNavigationUtil();
        nu.navigateAction("Admin/print_management");
    }

    public void editPrinterAction() {
        PrinterOPS printer = dao.find(id);
        if (printer != null) {
            printer.setName(name);
            printer.setWebServiceContext(webServiceContext);
            try {
                dao.update(printer);
            } catch (Exception ex) {
                showMsgUtil.showErrorMessage(ex.getMessage());

            }
            cancelAction();

        } else {
            new ShowMsgUtil().showErrorMessage("printer is null...");
        }

    }

    public EditPrinter() {
        PrinterOPS printer = (PrinterOPS) FacesContext.getCurrentInstance().getAttributes()
                .get("printer");
        if (printer != null) {
            this.id = printer.getId();
            this.name = printer.getName();
            this.webServiceContext = printer.getWebServiceContext();

        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebServiceContext() {
        return webServiceContext;
    }

    public void setWebServiceContext(String webServiceContext) {
        this.webServiceContext = webServiceContext;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
