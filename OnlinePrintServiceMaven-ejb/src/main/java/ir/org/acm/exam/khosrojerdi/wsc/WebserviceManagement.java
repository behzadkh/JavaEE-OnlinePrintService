/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.org.acm.exam.khosrojerdi.wsc;

import ir.org.acm.exam.khosrojerdi.dao.local.OrderDaoLocalFacadeInf;
import ir.org.acm.exam.khosrojerdi.dao.local.PrinterDaoLocalFacadeInf;
import ir.org.acm.exam.khosrojerdi.entity.OrderOPS;
import ir.org.acm.exam.khosrojerdi.entity.PrinterOPS;
import ir.org.acm.exam.khosrojerdi.entity.StatusEnum;
import ir.org.acm.exam.khosrojerdi.wsc.local.WebserviceManagementInf;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


//address = "http://localhost:8084/WebserviceApp2/PrinterIranWebservice?wsdl";

//address = "http://localhost:8084/WebServiceApp/PrinterIranWebservice?wsdl";

/**
 * @author behzad
 */
@Stateless
@Local(WebserviceManagementInf.class)
public class WebserviceManagement implements WebserviceManagementInf {
    private static final Logger LOGGER = Logger.getLogger(WebserviceManagement.class.getName());

    @EJB
    private OrderDaoLocalFacadeInf daoOrder;

    @EJB
    private PrinterDaoLocalFacadeInf printerDao;


    private OrderOPS order = null;

    private PrinterOPS printer = null;


    @Override
    public void print(String msg, long orderId) {
        LOGGER.log(Level.INFO, "orderId:" + orderId);
        boolean isLoop = true;
        while (isLoop) {
            int test = 0;
            order = daoOrder.find(orderId);
            if (order != null) {
                isLoop = false;
            } else {
                try {
                    test += 500;
                    LOGGER.log(Level.INFO, "wainting for findOrder: " + test + " ms");
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (order.isDelete())
            return;
        LOGGER.log(Level.INFO, "WebserviceManagement is running");
        List<PrinterOPS> printerList = printerDao.findPrinterWithMinimumJob();
        if (printerList != null) {
            if (printerList.size() > 0) {
                try {

                    if (order != null) {
                        order.setStatus(StatusEnum.PRINTER_PROGRESS);
                        order = daoOrder.update(order);
                    } else {
                        LOGGER.log(Level.INFO, "order is null");
                        return;
                    }


                    printer = printerList.get(0);
                    printer.setJobCount(printer.getJobCount() + 1);
                    printer = printerDao.update(printer);

                    boolean result = print(msg, printer.getWebServiceContext());
                    if (result) {
                        order.setStatus(StatusEnum.SUCCESS);
                        order.setDeliverDate(new Date());
                        order.setPrinter(printer);
                    } else {
                        order.setStatus(StatusEnum.ERROR);
                    }
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "WebserviceManagement " + ex.getMessage());
                    if (order != null) {
                        try {
                            order.setStatus(StatusEnum.ERROR);

                        } catch (Exception ex1) {
                            LOGGER.log(Level.SEVERE, null, ex1);
                        }
                    }
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            } else {
                order.setStatus(StatusEnum.NOT_AVAILABLE_PRINTER);
            }
        } else {
            order.setStatus(StatusEnum.NOT_AVAILABLE_PRINTER);
        }

        try {
            daoOrder.update(order);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }


    private boolean print(String msg, String address) {

        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL(address);//"http://localhost:8084/WebServiceApp/PrinterIranWebservice?wsdl"
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        LOGGER.log(Level.INFO, "web service caller is running.");
        try {
            PrinterIranWebserviceService pr = new PrinterIranWebserviceService(url);
            PrinterInf p = pr.getPrinterIranWebservicePort();

            BindingProvider bindingProvider = (BindingProvider) p;

            LOGGER.log(Level.INFO, "address:" + address);
            bindingProvider.getRequestContext().replace(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
            p.print(msg);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.log(Level.SEVERE, ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean isAvailablePrinter() {
        long count = printerDao.countActivePrinters();
        if (count > 0)
            return true;
        else
            return false;
    }

    private void prepareSth() {

    }
}
