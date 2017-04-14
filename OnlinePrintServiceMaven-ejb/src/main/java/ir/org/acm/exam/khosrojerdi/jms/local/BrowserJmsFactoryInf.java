package ir.org.acm.exam.khosrojerdi.jms.local;

import javax.ejb.Local;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.List;

/**
 * Created by behzad on 3/31/17.
 */
@Local
public interface BrowserJmsFactoryInf {

    public List<Message> getListOfMessageInQueue() throws JMSException;
}
