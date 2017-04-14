package ir.org.acm.exam.khosrojerdi.webbean;

import ir.org.acm.exam.khosrojerdi.dao.local.OrderDaoLocalFacadeInf;
import ir.org.acm.exam.khosrojerdi.entity.FileuploadOPS;
import ir.org.acm.exam.khosrojerdi.entity.OrderOPS;
import ir.org.acm.exam.khosrojerdi.entity.StatusEnum;
import ir.org.acm.exam.khosrojerdi.entity.UserOPS;
import ir.org.acm.exam.khosrojerdi.jms.local.QueueSenderInf;

import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.jms.JMSException;

import org.apache.commons.io.IOUtils;

@Named
@RequestScoped
public class FileUploadBean implements Serializable {

    private static final long serialVersionUID = 9040359120893077422L;

    @EJB
    private QueueSenderInf sender;

    @EJB
    private OrderDaoLocalFacadeInf daoOrder;

    private Part uploadedFile;

    private String statusMessage = "";

    @Inject
    private Login login;
    
    @Inject
    private ShowMsgUtil showMsgUtil;

    private String result = "result";

    private String fileName = "";

    private OrderOPS order;

    public FileUploadBean() {

    }

    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("***** partHeader: " + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public String uploadFileAction(ActionEvent event) {
        statusMessage = "";
        if (uploadedFile != null) {
            System.out.println("\nuploadfile: " + uploadedFile.getContentType()
                    + "\n" + uploadedFile.getName()
                    + "\n");
            fileName = getFileName(uploadedFile);
            System.out.println("fileName: " + fileName);
            try {
                statusMessage = "file upload successful !!";
                UserOPS user = login.getUserList().get(0);//get current user
                order = new OrderOPS(user, StatusEnum.STARTED);
                FileuploadOPS fileuploudOPS = new FileuploadOPS();
                fileuploudOPS.setFileName(fileName);
                fileuploudOPS.setContentType(uploadedFile.getContentType());
                byte[] fileuploadBytes = IOUtils.toByteArray(uploadedFile.getInputStream());
                fileuploudOPS.setFile(fileuploadBytes);
                order.setFileupload(fileuploudOPS);

                createOrder();
//                doJms();

                NavigationUtil nu = NavigationUtil.getNavigationUtil();

                nu.navigateAction("/users/user_report_view");
//                uploadedFile = null;

            } catch (Exception e) {
                statusMessage = "file upload failed !Please try again";
                showMsgUtil.showErrorMessage(e.getMessage());
            }
        } else {
            showMsgUtil.showErrorMessage("Please choose a file...");
        }

        return "/pages/users/user.xhtml";
    }

//    private byte[] doJms() throws IOException, JMSException {
//        System.out.println("Do jms.");
//
//        return fileuploadBytes;
//    }

    private void createOrder() throws Exception {
        System.out.println("create order is started");
        order = daoOrder.save(order);
        System.out.println("info:" + "order is saved successfully.");
    }

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setStatusMessage(String text) {
        this.statusMessage = text;
    }

    public String getStatusMessage() {
//        System.out.println("fileText is invoked...");
//        statusMessage = "";
//
//        if (null != uploadedFile) {
//            try {
//                try {
//                    InputStream is = uploadedFile.getInputStream();
//                    statusMessage = new Scanner(is).useDelimiter("\\A").next();
//                } catch (IOException ex) {
//                    statusMessage = ex.getMessage();
//                    System.out.println(statusMessage);
//                }
//
//                statusMessage = uploadedFile.getContentType();
//                byte[] bytes = IOUtils.toByteArray(uploadedFile.getInputStream());
//
////                Details details = new Details();
////                details.setFileUpload(bytes);
////                DaoFacade dao = new DaoFacade();
////                dao.createFile(details);
////                UserOPS userObj = loginBean.getUser();
////                dao.updateUserDetails(userObj, details);
//                statusMessage += "\n" + " (file is uploaded) ";
//                System.out.println(statusMessage);
//            } catch (IOException ex) {
//                statusMessage = "Error: " + ex.getMessage();
//                System.out.println(statusMessage);
//
//            }
//
//        }
        return statusMessage;
    }

}
