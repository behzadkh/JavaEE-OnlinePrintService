package ir.org.acm.exam.khosrojerdi.dao.remote;

import ir.org.acm.exam.khosrojerdi.entity.OrderOPS;
import ir.org.acm.exam.khosrojerdi.entity.UserOPS;
import java.util.List;
import javax.ejb.Remote;


@Remote
public interface OrderDaoRemoteFacadeInterface {

    public OrderOPS save(OrderOPS orderOPS) throws Exception;

    public OrderOPS update(OrderOPS orderOPS) throws Exception;

    public void delete(OrderOPS orderOPS) throws Exception;

    public OrderOPS find(long entityID);

    public List<OrderOPS> findAll();

    public List<OrderOPS> findOrderByUser(UserOPS user);

}
