
package ir.org.acm.exam.khosrojerdi.dao.impls;

import ir.org.acm.exam.khosrojerdi.dao.FileuploadDAO;
import ir.org.acm.exam.khosrojerdi.dao.OrderDAO;
import ir.org.acm.exam.khosrojerdi.dao.local.OrderDaoLocalFacadeInf;
import ir.org.acm.exam.khosrojerdi.dao.remote.OrderDaoRemoteFacadeInterface;
import ir.org.acm.exam.khosrojerdi.entity.OrderOPS;
import ir.org.acm.exam.khosrojerdi.entity.UserOPS;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.List;


@Stateless
@Local(OrderDaoLocalFacadeInf.class)
@Remote(OrderDaoRemoteFacadeInterface.class)
public class OrderOPSFacadeImp implements OrderDaoLocalFacadeInf, OrderDaoRemoteFacadeInterface {

    @EJB
    private OrderDAO orderDAO;

    @EJB
    private FileuploadDAO fileuploadDAO;

    @Override
    public OrderOPS save(OrderOPS orderOPS) throws Exception {
        return orderDAO.save(orderOPS);
    }

    @Override
    public OrderOPS update(OrderOPS orderOPS) throws Exception {
        return orderDAO.update(orderOPS);
    }

    @Override
    public void delete(OrderOPS orderOPS) throws Exception {
        orderDAO.delete(orderOPS);
    }

    @Override
    public OrderOPS find(long entityID) {
        return orderDAO.find(entityID);
    }

    @Override
    public List<OrderOPS> findAll() {
        return orderDAO.findAll();
    }

    @Override
    public List<OrderOPS> findOrderByUser(UserOPS user) {
        return orderDAO.findOrderByUser(user);
    }

}
