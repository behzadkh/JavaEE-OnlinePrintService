/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.org.acm.exam.khosrojerdi.jms.local;

import ir.org.acm.exam.khosrojerdi.entity.OrderOPS;
import javax.ejb.Local;
import javax.jms.JMSException;
import javax.jms.TextMessage;


/**
 *
 * @author behzad
 */
@Local
public interface QueueSenderInf {

      final String JNDI = "java:global/OnlinePrintServiceMaven-ear-1.0-SNAPSHOT/OnlinePrintServiceMaven-ejb-1.0-SNAPSHOT/QueueSender!ir.org.acm.exam.khosrojerdi.jms.local.QueueSenderInf";

     boolean sendMessageToQueue(OrderOPS order) ;
    
     void reQueue(TextMessage textMessage) ;
}
