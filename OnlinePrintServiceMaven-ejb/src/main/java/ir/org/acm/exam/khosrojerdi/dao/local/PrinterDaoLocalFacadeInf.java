package ir.org.acm.exam.khosrojerdi.dao.local;

import ir.org.acm.exam.khosrojerdi.dao.remote.PrinterDaoRemoteFacadeInterface;
import javax.ejb.Local;


@Local
public interface PrinterDaoLocalFacadeInf extends PrinterDaoRemoteFacadeInterface{
    public  final String JNDI = "java:global/OnlinePrintServiceMaven-ear-1.0-SNAPSHOT/OnlinePrintServiceMaven-ejb-1.0-SNAPSHOT/PrinterOPSFacadeImp!ir.org.acm.exam.khosrojerdi.dao.local.PrinterDaoLocalFacadeInf";
}
