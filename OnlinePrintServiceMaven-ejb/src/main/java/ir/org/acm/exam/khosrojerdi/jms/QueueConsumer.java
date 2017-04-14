/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.org.acm.exam.khosrojerdi.jms;

import ir.org.acm.exam.khosrojerdi.entity.OrderOPS;
import ir.org.acm.exam.khosrojerdi.wsc.local.WebserviceManagementInf;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author behzad
 */
public class QueueConsumer implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(QueueConsumer.class.getName());


    private WebserviceManagementInf webservice;

    //    @Resource(lookup = "java:/jms/QueueConnectionFactoryOnliePrint")
    private QueueConnectionFactory jmsFactory;

    //    @Resource(lookup = "java:/jms/OnlinePrintQueue")
    private Queue queue;

    private Context ctx = null;

    @Override
    public void run() {
        JMSContext jmsContext = null;
        try {
            ctx = new InitialContext();
            jmsFactory = (QueueConnectionFactory) ctx.lookup("java:/jms/QueueConnectionFactoryOnliePrint");
            queue = (Queue) ctx.lookup("java:/jms/OnlinePrintQueue");
            webservice = (WebserviceManagementInf) ctx.lookup(WebserviceManagementInf.JNDI);
            jmsContext = jmsFactory.createContext(Session.AUTO_ACKNOWLEDGE);
            jmsContext.start();
            JMSConsumer consumer = jmsContext.createConsumer(queue);
            if (webservice.isAvailablePrinter()) {
                TextMessage message = (TextMessage)consumer.receive();
                if (message != null) {
                    LOGGER.log(Level.INFO, "message is received.");
                    String msg = message.getText();
                    long orderId = message.getLongProperty("orderId");
                    webservice.print(msg,orderId);
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);

        } finally {
            jmsContext.close();
            closeContext();
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

/*

 private static final Logger LOGGER = Logger.getLogger(QueueMessageListener.class.getName());

    private WebserviceManagementInf webserviceManagement;

    private OrderDaoLocalFacadeInf daoOrder;

    private Context ctx = null;

    private OrderOPS order = null;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        Long orderId = null;
        try {
            ctx = new InitialContext();
            webserviceManagement = (WebserviceManagementInf) ctx.lookup(WebserviceManagementInf.JNDI);
            daoOrder = (OrderDaoLocalFacadeInf) ctx.lookup(OrderDaoLocalFacadeInf.JNDI);
            orderId = textMessage.getLongProperty("orderId");
            if (webserviceManagement != null) {
                LOGGER.log(Level.INFO, "sendToWebService is called");
                webserviceManagement.print(textMessage.getText(), orderId);
            }
        } catch (Exception e) {
            if(daoOrder != null){
                if(orderId != null){
                    order = daoOrder.find(orderId);
                    order.setStatus(StatusEnum.ERROR);
                    try {
                        daoOrder.update(order);
                    } catch (Exception e1) {
                        LOGGER.log(Level.SEVERE, e1.getMessage());
                    }
                }
            }
            LOGGER.log(Level.SEVERE, e.getMessage());
        } finally {
            closeContext();
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

 */


//    private void sendToWebService(String msg, long orderId) {
//        LOGGER.log(Level.INFO, "sendToWebService is called");
//        webservice.print(msg, orderId);
//    }

//            String selector = "readTime <= " + System.currentTimeMillis();
//            TextMessage message = session.createTextMessage();
//            String msg = Base64.getEncoder().encodeToString(file);
//            byte[] file = Base64.getDecoder().decode(msg);
