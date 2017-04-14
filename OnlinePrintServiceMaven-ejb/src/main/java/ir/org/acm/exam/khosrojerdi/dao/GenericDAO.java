package ir.org.acm.exam.khosrojerdi.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;


public abstract class GenericDAO<T> {

    private final static String UNIT_NAME = "PersistenceUnit";
    
    @PersistenceContext(unitName = UNIT_NAME)
    private EntityManager em;

    private Class<T> entityClass;

    public GenericDAO(){}

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T save(T entity) throws Exception{
        em.persist(entity);
        return entity;
    }

    public void delete(T entity) throws Exception{
        T entityToBeRemoved = em.merge(entity);
        em.remove(entityToBeRemoved);

    }

    public T update(T entity) throws Exception{
        return em.merge(entity);
    }

    public T find(long entityID) {
        return em.find(entityClass, entityID);
    }

    // Using the unchecked because JPA does not have a
    // em.getCriteriaBuilder().createQuery()<T> method
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

 // Using the unchecked because JPA does not have a
 // ery.getSingleResult()<T> method
    @SuppressWarnings("unchecked")
    public List<T> find(String queryStr, Map<String, Object> parameters) {
        List<T> result = null;
        try {
            Query query = em.createQuery(queryStr);
            // Method that will populate parameters if they are passed not null and empty
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(query, parameters);
            }
            result = (List<T>) query.getResultList();
        } catch (Exception e) {
            System.out.println("Error while running query: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    private void populateQueryParameters(Query query, Map<String, Object> parameters) {
        for (Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }

    public EntityManager getEm(){
        return this.em;
    }

}
