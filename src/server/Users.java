package server;

import java.util.ArrayList;
import java.util.List;
 
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "userlist")
public class Users
{
    private List<User> userList;
 
    public Users() 
    {
    	userList = new ArrayList<User>();
	}

    @XmlElementWrapper
    @XmlElement(name="user")
	public List<User> getUsers() 
    {
        return userList;
    }
 
    public void setUsers(List<User> users) 
    {
        userList = users;
    }
}
