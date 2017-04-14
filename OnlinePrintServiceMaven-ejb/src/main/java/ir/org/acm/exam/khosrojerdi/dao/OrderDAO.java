package ir.org.acm.exam.khosrojerdi.dao;

import ir.org.acm.exam.khosrojerdi.entity.OrderOPS;
import ir.org.acm.exam.khosrojerdi.entity.UserOPS;
import ir.org.acm.exam.khosrojerdi.jms.local.QueueSenderInf;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Stateless
@Local(OrderDaoInf.class)
public class OrderDAO implements Serializable, OrderDaoInf {

    private final static String UNIT_NAME = "PersistenceUnit";

    @PersistenceContext(unitName = UNIT_NAME)
    private EntityManager em;

    @EJB
    private QueueSenderInf queueSender;


//    public OrderDAO() {
//        super(OrderOPS.class);
//    }

    public List<OrderOPS> findOrderByUser(UserOPS user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user", user);

        String queryStr = "select o from OrderOPS o where o.userId=:user";
        return find(queryStr, parameters);

    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public OrderOPS save(OrderOPS order) throws Exception {
        em.persist(order);
        queueSender.sendMessageToQueue(order);
        em.flush();
        return order;
    }

    @Override
    public OrderOPS update(OrderOPS orderOPS) throws Exception {
        return em.merge(orderOPS);
    }

    @Override
    public void delete(OrderOPS orderOPS) throws Exception {
        orderOPS.setDelete(true);
        em.merge(orderOPS);
//        OrderOPS entityToBeRemoved = em.merge(orderOPS);
//        em.remove(entityToBeRemoved);
    }

    @Override
    public OrderOPS find(long entityID) {
        em.flush();
        OrderOPS order = em.find(OrderOPS.class, entityID);

        return order;
    }

    @Override
    public List<OrderOPS> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(OrderOPS.class));
        return em.createQuery(cq).getResultList();
    }

    public List<OrderOPS> find(String queryStr, Map<String, Object> parameters) {
        List<OrderOPS> result = null;
        try {
            Query query = em.createQuery(queryStr);
            // Method that will populate parameters if they are passed not null and empty
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(query, parameters);
            }
            result = (List<OrderOPS>) query.getResultList();
        } catch (Exception e) {
            System.out.println("Error while running query: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    private void populateQueryParameters(Query query, Map<String, Object> parameters) {
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
}
