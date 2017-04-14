
package ir.org.acm.exam.khosrojerdi.entity;

import java.io.Serializable;
import javax.persistence.*;


@Entity
public class FileuploadOPS implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "fileupload_seq")
    @SequenceGenerator(name = "fileupload_seq", sequenceName = "fileupload_seq")
    private Long id;

    private String fileName;

    private String contentType;

    @Transient
    private String shortFileName;

//    private String pathFile;

    @Lob
    private byte[] file;

    @OneToOne(mappedBy = "fileupload")
    private OrderOPS order;

    public OrderOPS getOrder() {
        return order;
    }

    public void setOrder(OrderOPS order) {
        this.order = order;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortFileName() {
        if (fileName.length() > 10) {
            this.shortFileName = fileName.substring(0, 9);
        } else {
            this.shortFileName = fileName;
        }
        return shortFileName;
    }

    public void setShortFileName(String shortFileName) {
        this.shortFileName = fileName;
    }

    @Override
    public String toString() {
        return "org.acm.khosrojerdi.exam.ejb.entities.FileuploadOPS[ id=" + id + " ]";
    }

}
