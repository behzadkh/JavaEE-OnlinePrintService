/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.org.acm.exam.khosrojerdi.jms;

import ir.org.acm.exam.khosrojerdi.dao.local.PrinterDaoLocalFacadeInf;
import ir.org.acm.exam.khosrojerdi.entity.PrinterOPS;
import ir.org.acm.exam.khosrojerdi.wsc.local.WebserviceManagementInf;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author behzad
 */

public class WebserviceCheckerJob implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(WebserviceCheckerJob.class.getName());

    //    @EJB(mappedName = "java:app/OnlinePrintServiceMaven-ejb-1.0-SNAPSHOT/PrinterOPSFacadeImp!ir.org.acm.exam.khosrojerdi.dao.local.PrinterDaoLocalFacadeInf")
    private PrinterDaoLocalFacadeInf printerDao;

    private List<PrinterOPS> printerList;

    private Context ctx = null;

    private WebserviceManagementInf webserviceManagement;

    public WebserviceCheckerJob() {
        try {
            ctx = new InitialContext();
            printerDao = (PrinterDaoLocalFacadeInf) ctx.lookup(PrinterDaoLocalFacadeInf.JNDI);
            for (PrinterOPS printer : printerDao.activePrinterList()) {
                printer.setActive(false);
                printerDao.update(printer);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);

        } finally {
            closeContext();
        }
    }


    @Override
    public void run() {
//        LOGGER.log(Level.INFO, "WEBservice checker is running - " + new Date());
        try {
            ctx = new InitialContext();
            printerDao = (PrinterDaoLocalFacadeInf) ctx.lookup(PrinterDaoLocalFacadeInf.JNDI);
            if (printerDao != null) {
                printerList = printerDao.findAll();
                for (PrinterOPS printer : printerList) {
                    String address = printer.getWebServiceContext();
                    boolean result = isWSDLAvailable(address);

                    if (result != printer.isActive()) {
                        printer.setActive(result);
                        printerDao.update(printer);
                    }
                }

            } else {
                LOGGER.log(Level.SEVERE, null, "JNDI lookup does not work correctly - run()");
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);

        } finally {
            closeContext();
        }
    }

//    private void removeMessageListener(PrinterOPS printer) {
//        try {
//            ctx = new InitialContext();
//            webserviceManagement = (WebserviceManagementInf) ctx.lookup(WebserviceManagementInf.JNDI);
//            if (webserviceManagement != null)
//                webserviceManagement.removeActivePrinter(printer);
//            else
//                LOGGER.log(Level.SEVERE, null, "JNDI lookup does not work correctly - removeMessageListener()");
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//        } finally {
//            closeContext();
//        }
//    }

//    private void addMessageListener(PrinterOPS printer) {
//        try {
//            ctx = new InitialContext();
//            webserviceManagement = (WebserviceManagementInf) ctx.lookup(WebserviceManagementInf.JNDI);
//            if (webserviceManagement != null)
//                webserviceManagement.addActivePrinter(printer);
//            else
//                LOGGER.log(Level.SEVERE, null, "JNDI lookup does not work correctly - addMessageListener()");
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//        } finally {
//            closeContext();
//        }
//    }

    private boolean isWSDLAvailable(String wsdlAddr) {
        HttpURLConnection c = null;
        try {
            URL u = new URL(wsdlAddr);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("HEAD");
            c.getInputStream();
            return c.getResponseCode() == 200;
        } catch (Exception e) {
            return false;
        } finally {
            if (c != null) {
                c.disconnect();
            }
        }
    }

    private void closeContext() {
        if (ctx != null) {
            try {
                this.ctx.close();
            } catch (NamingException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        }
    }

}
