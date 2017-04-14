/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.org.acm.exam.khosrojerdi.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author behzad
 */
@Entity
public class GroupOPS implements Serializable{
    @Id
    private String groupName;

//    @ManyToMany
//    @JoinTable(name="USER_GROUP",
//    joinColumns = @JoinColumn(name = "groupName", 
//        referencedColumnName = "groupName"), 
//    inverseJoinColumns = @JoinColumn(name = "username", 
//        referencedColumnName = "username"))
//    private List<UserOPS> users;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

//    public List<UserOPS> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<UserOPS> users) {
//        this.users = users;
//    }
    
    
}
