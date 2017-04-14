/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.org.acm.exam.khosrojerdi.jms;

import ir.org.acm.exam.khosrojerdi.dao.local.OrderDaoLocalFacadeInf;
import ir.org.acm.exam.khosrojerdi.entity.OrderOPS;
import ir.org.acm.exam.khosrojerdi.entity.StatusEnum;
import ir.org.acm.exam.khosrojerdi.jms.local.JobManagementInf;
import ir.org.acm.exam.khosrojerdi.jms.local.QueueSenderInf;

import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.*;

/**
 * @author behzad
 */
@Stateless
@Local(QueueSenderInf.class)
public class QueueSender implements QueueSenderInf {

    private static final Logger LOGGER = Logger.getLogger(QueueSender.class.getName());

    @Resource(lookup = "java:/jms/QueueConnectionFactoryOnliePrint")
    private QueueConnectionFactory jmsFactory;

    @Resource(lookup = "java:/jms/OnlinePrintQueue")
    private Queue queue;

    @EJB
    private OrderDaoLocalFacadeInf daoOrder;

    @EJB
    private JobManagementInf jobManagement;


    /**

     * @param order
     * @throws JMSException
     */
    @Override
    public boolean sendMessageToQueue(OrderOPS order) {
        boolean result = false;
        LOGGER.log(Level.INFO, "sendMessageToQueue is started");
        try (JMSContext jmsContext = jmsFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            TextMessage message = jmsContext.createTextMessage();
            String msg = Base64.getEncoder().encodeToString(order.getFileupload().getFile());
            message.setText(msg);
//            message.setLongProperty("readTime", System.currentTimeMillis());
            message.setLongProperty("orderId", order.getId());
            jmsContext.createProducer().send(queue, message);
            order.setStatus(StatusEnum.IN_QUEUE);
            daoOrder.update(order);
            System.out.println("sendMessageToQueue is finished and send message.");
            result = true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            order.setStatus(StatusEnum.ERROR);
            try {
                daoOrder.update(order);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
        }
        return result;
    }

    @Override
    public void reQueue(TextMessage textMessage)  {
        LOGGER.log(Level.INFO, "reQueue is called..");
        long orderId = 0;
        try {
            orderId = textMessage.getLongProperty("orderId");
//            long readTime = textMessage.getLongProperty("readTime");
            String text = textMessage.getText();
            textMessage.clearBody();
            textMessage.clearProperties();
            textMessage.acknowledge();
            textMessage.setLongProperty("orderId", orderId);
//            textMessage.setLongProperty("readTime", (readTime + 10_000));
            textMessage.setText(text);

            OrderOPS order = null;
            try (JMSContext jmsContext = jmsFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
                order = daoOrder.find(orderId);
                jmsContext.createProducer().send(queue, textMessage);
                if (order != null) {
                    order.setStatus(StatusEnum.IN_QUEUE);
                    daoOrder.update(order);
                }
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, null, ex);
                try {
                    if (order != null) {
                        order.setStatus(StatusEnum.ERROR);
                        daoOrder.update(order);
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, null, e);
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }


    }

}
