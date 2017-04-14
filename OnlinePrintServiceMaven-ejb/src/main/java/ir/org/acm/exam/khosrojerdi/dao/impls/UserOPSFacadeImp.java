
package ir.org.acm.exam.khosrojerdi.dao.impls;

import ir.org.acm.exam.khosrojerdi.dao.UserDAO;
import ir.org.acm.exam.khosrojerdi.dao.local.UserDaoLocalFacadeInf;
import ir.org.acm.exam.khosrojerdi.dao.remote.UserDaoRemoteFacadeInterface;
import ir.org.acm.exam.khosrojerdi.entity.UserOPS;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;


@Stateless
@Local(UserDaoLocalFacadeInf.class)
@Remote(UserDaoRemoteFacadeInterface.class)
public class UserOPSFacadeImp implements UserDaoRemoteFacadeInterface,UserDaoLocalFacadeInf{

    @EJB
    private UserDAO userDAO;
   
    

    @Override
    public UserOPS save(UserOPS userOPS) throws Exception{
        userDAO.save(userOPS);
        return userOPS;
    }

    @Override
    public UserOPS update(UserOPS userOPS) throws Exception{
       return userDAO.update(userOPS);
    }

    @Override
    public void delete(UserOPS userOPS) throws Exception{
        userDAO.delete(userOPS);
    }

    @Override
    public UserOPS find(long entityID) {
        return userDAO.find(entityID);
    }

    @Override
    public List<UserOPS> findAll() {
        return userDAO.findAll();
    }

    @Override
    public List<UserOPS> findByUsernameAndPassword(String username, String password) {
        return userDAO.findUserByUsernameAndPass(username, password);
         
    }
    
}
