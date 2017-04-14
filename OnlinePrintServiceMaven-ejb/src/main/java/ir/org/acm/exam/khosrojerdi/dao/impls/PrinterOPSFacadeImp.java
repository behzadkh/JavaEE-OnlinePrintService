
package ir.org.acm.exam.khosrojerdi.dao.impls;

import ir.org.acm.exam.khosrojerdi.dao.PrinterDAO;
import ir.org.acm.exam.khosrojerdi.dao.local.PrinterDaoLocalFacadeInf;
import ir.org.acm.exam.khosrojerdi.dao.remote.PrinterDaoRemoteFacadeInterface;
import ir.org.acm.exam.khosrojerdi.entity.PrinterOPS;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;


@Stateless
@Local(PrinterDaoLocalFacadeInf.class)
@Remote(PrinterDaoRemoteFacadeInterface.class)
public class PrinterOPSFacadeImp implements PrinterDaoRemoteFacadeInterface,PrinterDaoLocalFacadeInf{

    @EJB
    private PrinterDAO dao;
    
    @Override
    public PrinterOPS save(PrinterOPS printerOPS) throws Exception{
        dao.save(printerOPS);
        return printerOPS;
    }

    @Override
    public PrinterOPS update(PrinterOPS printerOPS) throws Exception{
        dao.update(printerOPS);
        return printerOPS;
    }

    @Override
    public void delete(PrinterOPS printerOPS) throws Exception{
        dao.delete(printerOPS);
    }

    @Override
    public PrinterOPS find(long entityID) {
       return dao.find(entityID);
    }

    @Override
    public List<PrinterOPS> findAll() {
        return dao.findAll();
    }
    
    @Override 
    public List<PrinterOPS> findPrinterWithMinimumJob(){
        return dao.findPrinterWithMinimumJob();
    }

    @Override
    public List<PrinterOPS> activePrinterList() {
        return dao.activePrinterList();
    }

    @Override
    public long countActivePrinters(){
        return dao.countActivePrinters();
    }
    
}
