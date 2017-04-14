package ir.org.acm.exam.khosrojerdi.dao;

import ir.org.acm.exam.khosrojerdi.entity.UserOPS;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@Local
public class UserDAO extends GenericDAO<UserOPS> {


    public UserDAO() {
        super(UserOPS.class);
    }

    public List<UserOPS> findUserByUsernameAndPass(String username, String password) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("username", username);
        parameters.put("password", password);
        String queryStr = "select u from UserOPS u where u.username=:username and u.password=:password";
        return super.find(queryStr, parameters);

    }

}
