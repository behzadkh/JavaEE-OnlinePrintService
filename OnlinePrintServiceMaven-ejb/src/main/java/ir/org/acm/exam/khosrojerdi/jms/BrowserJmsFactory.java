/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.org.acm.exam.khosrojerdi.jms;

import ir.org.acm.exam.khosrojerdi.jms.local.BrowserJmsFactoryInf;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;

/**
 *
 * @author behzad
 */
@Stateless
@Local(BrowserJmsFactoryInf.class)
public class BrowserJmsFactory implements BrowserJmsFactoryInf {

    @Resource(mappedName = "java:/jms/QueueConnectionFactoryOnliePrint")
    private QueueConnectionFactory jmsFactory;

    @Resource(mappedName = "java:/jms/OnlinePrintQueue")
    private Queue queue;

    @Override
    public List<Message> getListOfMessageInQueue() throws JMSException {

        List<Message> messageList = new ArrayList<>();
        // create a queue session
        try (QueueConnection queueConn = jmsFactory.createQueueConnection();
                // create a queue session
                QueueSession queueSession = queueConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                // create a queue browser
                QueueBrowser queueBrowser = queueSession.createBrowser(queue);) {

            // start the connection
            queueConn.start();
            // browse the messages
            Enumeration e = queueBrowser.getEnumeration();

            // count number of messages
            while (e.hasMoreElements()) {
                Message message = (Message) e.nextElement();

                messageList.add(message);
            }
            // close the queue connection
        }
        return messageList;
    }


}
