package ir.org.acm.exam.khosrojerdi.dao;

import ir.org.acm.exam.khosrojerdi.entity.OrderOPS;
import ir.org.acm.exam.khosrojerdi.entity.UserOPS;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by behzad on 4/3/17.
 */
@Local
public interface OrderDaoInf {
    OrderOPS save(OrderOPS orderOPS) throws Exception;

    OrderOPS update(OrderOPS orderOPS) throws Exception;

    void delete(OrderOPS orderOPS) throws Exception;

    OrderOPS find(long entityID);

    List<OrderOPS> findAll();

    List<OrderOPS> findOrderByUser(UserOPS user);
}
