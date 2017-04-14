
package ir.org.acm.exam.khosrojerdi.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;


@Entity
@NamedQuery(name = "listAllUsers" , query = "select u from UserOPS u")
public class UserOPS implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator="user_seq")
    @SequenceGenerator(name="user_seq",sequenceName="user_seq")
    private Long id;
    
//    @ManyToMany(mappedBy = "users")
//    private List<GroupOPS> groups;

    @Column(nullable = false ,unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean adminStatus;

    private String role;
    
    @OneToMany(mappedBy = "userId")
    private List<OrderOPS> orderList = new ArrayList<OrderOPS>();

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<OrderOPS> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderOPS> orderList) {
        this.orderList = orderList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(boolean adminStatus) {
        this.adminStatus = adminStatus;
    }

//    public List<GroupOPS> getGroups() {
//        return groups;
//    }
//
//    public void setGroups(List<GroupOPS> groups) {
//        this.groups = groups;
//    }
   
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserOPS)) {
            return false;
        }
        UserOPS other = (UserOPS) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.acm.khosrojerdi.exam.model.entities.UserOPS[ id=" + id + " ]";
    }

}
