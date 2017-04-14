/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.org.acm.exam.khosrojerdi.webbean;

import ir.org.acm.exam.khosrojerdi.dao.local.OrderDaoLocalFacadeInf;
import ir.org.acm.exam.khosrojerdi.entity.FileuploadOPS;
import ir.org.acm.exam.khosrojerdi.entity.OrderOPS;
import ir.org.acm.exam.khosrojerdi.entity.UserOPS;
import ir.org.acm.exam.khosrojerdi.jms.local.BrowserJmsFactoryInf;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author behzad
 */
@Named
@SessionScoped
public class QueueManagement implements Serializable {

    private final ShowMsgUtil errMsg = new ShowMsgUtil();

    private DataModel<MessageDto> msgModel;

    @EJB
    private BrowserJmsFactoryInf browserJmsFactory;

    @EJB
    private OrderDaoLocalFacadeInf orderDao;

    private OrderOPS order;

    public DataModel<MessageDto> getMsgListFromJmsFactory() {
        List<MessageDto> mList = new ArrayList<>();
        try {

            List<Message> list = browserJmsFactory.getListOfMessageInQueue();

            for (Message msg : list) {
                MessageDto msgDto = new MessageDto();
                TextMessage msgTxt = (TextMessage) msg;
                if (msgTxt.propertyExists("orderId")) {
                    long orderId = msgTxt.getLongProperty("orderId");
                    msgDto.setOrderId(orderId + "");
                    order = orderDao.find(orderId);
                }

                msgDto.setJmsMessageId(msgTxt.getJMSMessageID());

                if (order != null) {
                    UserOPS u = order.getUserId();
                    if (u != null)
                        msgDto.setUsername(u.getUsername());

                    FileuploadOPS fileuploadOPS = order.getFileupload();
                    if (fileuploadOPS != null)
                        msgDto.setFilename(fileuploadOPS.getFileName());
                }
                mList.add(msgDto);
            }
            msgModel = new ListDataModel<>(mList);
        } catch (JMSException ex) {
            errMsg.showErrorMessage(ex.getMessage());
        }
        return msgModel;
    }

    public DataModel<MessageDto> getMsgModel() {
        return msgModel;
    }

    public void setMsgModel(DataModel<MessageDto> msgModel) {
        this.msgModel = msgModel;
    }


    public class MessageDto implements Serializable {

        private String orderId = "";
        private String jmsMessageId = "";
        private String username = "Error:No User";
        private String filename = "Error:No Filename";
        private String shortFilename ;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getJmsMessageId() {
            return jmsMessageId;
        }

        public void setJmsMessageId(String jmsMessageId) {
            this.jmsMessageId = jmsMessageId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getShortFilename() {
            this.shortFilename = filename;
            if (filename.length() > 20)
                shortFilename = filename.substring(0, 19);
            return shortFilename;
        }

        public void setShortFilename() {
            this.shortFilename = filename;
        }
    }

}
