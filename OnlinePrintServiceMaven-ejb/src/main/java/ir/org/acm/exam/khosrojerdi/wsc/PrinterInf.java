
package ir.org.acm.exam.khosrojerdi.wsc;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "PrinterInf", targetNamespace = "http://webservice.exam.khosrojerdi.acm.org/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface PrinterInf {


    /**
     * 
     * @param text
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://webservice.exam.khosrojerdi.acm.org/PrinterInf/printRequest", output = "http://webservice.exam.khosrojerdi.acm.org/PrinterInf/printResponse")
    public boolean print(
        @WebParam(name = "text", partName = "text")
        String text);

}
