/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.org.acm.exam.khosrojerdi.wsc.local;

import ir.org.acm.exam.khosrojerdi.entity.OrderOPS;
import ir.org.acm.exam.khosrojerdi.entity.PrinterOPS;

import javax.ejb.Local;

/**
 * @author behzad
 */
@Local
public interface WebserviceManagementInf {
     String JNDI = "java:global/OnlinePrintServiceMaven-ear-1.0-SNAPSHOT/OnlinePrintServiceMaven-ejb-1.0-SNAPSHOT/WebserviceManagement!ir.org.acm.exam.khosrojerdi.wsc.local.WebserviceManagementInf";

     void print(String msg, long orderId);

     boolean isAvailablePrinter();

}
