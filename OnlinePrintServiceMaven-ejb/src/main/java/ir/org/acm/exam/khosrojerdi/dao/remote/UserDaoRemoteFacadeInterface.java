package ir.org.acm.exam.khosrojerdi.dao.remote;

import ir.org.acm.exam.khosrojerdi.entity.UserOPS;
import java.util.List;
import javax.ejb.Remote;


@Remote
public interface UserDaoRemoteFacadeInterface {

    public List<UserOPS> findByUsernameAndPassword(String username, String password);

    public UserOPS save(UserOPS userOPS) throws Exception;

    public UserOPS update(UserOPS userOPS) throws Exception;

    public void delete(UserOPS userOPS) throws Exception;

    public UserOPS find(long entityID);

    public List<UserOPS> findAll();

}
