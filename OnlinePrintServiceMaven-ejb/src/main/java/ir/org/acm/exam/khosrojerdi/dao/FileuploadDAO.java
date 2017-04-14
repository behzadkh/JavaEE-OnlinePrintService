package ir.org.acm.exam.khosrojerdi.dao;

import ir.org.acm.exam.khosrojerdi.entity.FileuploadOPS;

import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Created by behzad on 3/31/17.
 */
@Stateless
@Local
public class FileuploadDAO extends GenericDAO<FileuploadOPS> {

    public FileuploadDAO() {
        super(FileuploadOPS.class);
    }
}
