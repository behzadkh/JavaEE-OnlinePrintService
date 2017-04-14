
package ir.org.acm.exam.khosrojerdi.dao;

import ir.org.acm.exam.khosrojerdi.entity.PrinterOPS;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;


@Stateless
@Local
public class PrinterDAO extends GenericDAO<PrinterOPS> {

    public PrinterDAO() {
        super(PrinterOPS.class);
    }

    public List<PrinterOPS> activePrinterList() {
        String query = "select p from PrinterOPS p where p.active = true";
        return this.find(query, null);
    }

    public long countActivePrinters() {
        long count = (long) getEm().createNamedQuery(PrinterOPS.COUNT_ACTIVE_PRINTERS).getSingleResult();
        return count;
    }

    public List<PrinterOPS> findPrinterWithMinimumJob() {
        String query = "select p1 from PrinterOPS p1 where p1.jobCount = (select min(p.jobCount) from PrinterOPS p where p.active = true)";
        List<PrinterOPS> list = find(query, null);
        System.out.println("list find min:" + list.toString());
        return list;
    }
}
