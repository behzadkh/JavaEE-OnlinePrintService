package ir.org.acm.exam.khosrojerdi.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


@Entity
@NamedQueries({
        @NamedQuery(name = "listAllOrders", query = "select o from OrderOPS o"),
        @NamedQuery(name = "OrderOPS.find", query = "select o from OrderOPS o where o.id=:id ")
})

public class OrderOPS implements Serializable {

    public static final String FIND = "OrderOPS.find";

    private static final long serialVersionUID = 2L;
    @Id
    @GeneratedValue(generator="order_seq")
    @SequenceGenerator(name="order_seq",sequenceName="order_seq")
    private long id;

    @ManyToOne
    private UserOPS userId;
    
    @OneToOne
    @JoinColumn(name = "printer_id" , nullable = true)
    private PrinterOPS printer;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fileupload_id" , nullable = true)
    private FileuploadOPS fileupload;

    
    @Enumerated(EnumType.ORDINAL)
    private StatusEnum status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deliverDate;
    
    private boolean deleteStatus;

    public OrderOPS() {
    }

    public OrderOPS(UserOPS userId, StatusEnum status) {
        this.userId = userId;  
        this.status = status;        
    }

    public FileuploadOPS getFileupload() {
        return fileupload;
    }

    public void setFileupload(FileuploadOPS fileupload) {
        this.fileupload = fileupload;
    }


    public UserOPS getUserId() {
        return userId;
    }

    public void setUserId(UserOPS userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

   
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public PrinterOPS getPrinter() {
        return printer;
    }

    public void setPrinter(PrinterOPS printerOPS) {
        this.printer = printerOPS;
    }

    public boolean isDelete() {
        return deleteStatus;
    }

    public void setDelete(boolean delete) {
        this.deleteStatus = delete;
    }
    
    
    
    @PrePersist
    public void setOrderDate() {
        this.orderDate = new Date();
    }

    @Override
    public String toString() {
        return "org.acm.khosrojerdi.exam.model.entities.OrderOPS[ id=" + id + " ]";
    }

}
