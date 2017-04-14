package ir.org.acm.exam.khosrojerdi.dao.local;

import ir.org.acm.exam.khosrojerdi.dao.remote.UserDaoRemoteFacadeInterface;
import javax.ejb.Local;


@Local
public interface UserDaoLocalFacadeInf extends UserDaoRemoteFacadeInterface{

}
