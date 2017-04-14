package ir.org.acm.exam.khosrojerdi.dao;

import java.io.Serializable;

/**
 * Created by behzad on 4/3/17.
 */
public interface GenericDAOInf <T, PK extends Serializable> {
    T create(T t);
    T read(PK id);
    T update(T t);
    void delete(T t);
}
