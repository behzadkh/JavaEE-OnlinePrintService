package ir.org.acm.exam.khosrojerdi.dao.remote;

import ir.org.acm.exam.khosrojerdi.entity.PrinterOPS;
import java.util.List;
import javax.ejb.Remote;


@Remote
public interface PrinterDaoRemoteFacadeInterface {
    public  final String JNDI_REMOTE_EXAMPLE = "ejb:/core//WSConnectionManager!com.art.vesal.factory.WSConnectionManagerRemoteInf";

    public  final String REMOTE_JNDI = "ejb:/OnlinePrintServiceMaven-ejb-1.0-SNAPSHOT//PrinterOPSFacadeImp!ir.org.acm.exam.khosrojerdi.dao.local.PrinterDaoRemoteFacadeInterface";

    public PrinterOPS save(PrinterOPS printerOPS) throws Exception;

    public PrinterOPS update(PrinterOPS printerOPS) throws Exception;

    public void delete(PrinterOPS printerOPS) throws Exception;

    public PrinterOPS find(long entityID);

    public List<PrinterOPS> findAll();
    
    public List<PrinterOPS> findPrinterWithMinimumJob();
    
    public List<PrinterOPS> activePrinterList();

    public long countActivePrinters();
}
