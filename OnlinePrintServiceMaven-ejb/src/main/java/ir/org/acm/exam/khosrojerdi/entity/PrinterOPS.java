
package ir.org.acm.exam.khosrojerdi.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "listAllPrinters", query = "select p from PrinterOPS p"),
        @NamedQuery(name = "countActivePrinters", query = "select count(p) from PrinterOPS p where p.active = true ")
})
public class PrinterOPS implements Serializable {
    public static final String LIST_ALL_PRINTERS = "listAllPrinters";
    public static final String COUNT_ACTIVE_PRINTERS = "countActivePrinters";

    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(generator="printer_seq")
    @SequenceGenerator(name="printer_seq",sequenceName="printer_seq")
    private Long id;
    
    private String webServiceContext;

    @Transient
    private String shortWebserviceCtx;
    
    private String name;
    
    @OneToOne(mappedBy = "printer")
    private OrderOPS order;
    
    
    private int jobCount;
    
  
    private boolean active;
    
    public PrinterOPS()
    {
        name = null;
    }
    
    public PrinterOPS(String name)
    {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebServiceContext() {
        return webServiceContext;
    }

    public void setWebServiceContext(String webServiceContext) {
        this.webServiceContext = webServiceContext;
    }

    public OrderOPS getOrder() {
        return order;
    }

    public void setOrder(OrderOPS order) {
        this.order = order;
    }

    public int getJobCount() {
        return jobCount;
    }

    public void setJobCount(int jobCount) {
        this.jobCount = jobCount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public String getShortWebserviceCtx() {
        if (webServiceContext.length() > 20) {
            this.shortWebserviceCtx = webServiceContext.substring(0, 19);
        } else {
            this.shortWebserviceCtx = webServiceContext;
        }
        return shortWebserviceCtx;
    }

    public void setShortWebserviceCtx(String shortWebserviceCtx) {
        this.shortWebserviceCtx = webServiceContext;
    }

    @Override
    public String toString() {
        return "org.acm.khosrojerdi.exam.ejb.entities.Printers[ id=" + id + " ]";
    }
    
}
