package ir.org.acm.exam.khosrojerdi.webbean;

import ir.org.acm.exam.khosrojerdi.dao.remote.PrinterDaoRemoteFacadeInterface;
import ir.org.acm.exam.khosrojerdi.entity.PrinterOPS;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;



@Named
@RequestScoped
public class AddPrinter implements Serializable {

    @EJB
    private PrinterDaoRemoteFacadeInterface dao;

    private String name;
    private String webServiceContext;

    public void cancelAction() {
//        CallPrinter1 cp1 = new CallPrinter1();
//        Random r = new Random(3);
//        cp1.print_1(r.nextInt(3)+1);
        NavigationUtil nu = NavigationUtil.getNavigationUtil();
        nu.navigateAction("Admin/print_management");
    }

    public void addPrinterAction() {

        PrinterOPS printer = new PrinterOPS();
        printer.setName(name);
        printer.setWebServiceContext(webServiceContext);
        try {
            dao.save(printer);
        } catch (Exception ex) {
            new ShowMsgUtil().showErrorMessage(ex.getMessage());
        }
        cancelAction();
    }

  

    public AddPrinter() {
      
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

}
