package ir.org.acm.exam.khosrojerdi.dao.local;

import ir.org.acm.exam.khosrojerdi.dao.remote.OrderDaoRemoteFacadeInterface;

import javax.ejb.Local;


@Local
public interface OrderDaoLocalFacadeInf extends OrderDaoRemoteFacadeInterface {

    String JNDI = "java:global/OnlinePrintServiceMaven-ear-1.0-SNAPSHOT/OnlinePrintServiceMaven-ejb-1.0-SNAPSHOT/OrderOPSFacadeImp!ir.org.acm.exam.khosrojerdi.dao.local.OrderDaoLocalFacadeInf";

}
