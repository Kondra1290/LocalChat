package Client;

import java.util.HashSet;
import java.util.Set;

public class ListOfUsers {
    Set<String> users;
    
    ListOfUsers(){
        users = new HashSet();
    }
    
    public void addUser(String name){ users.add(name); }
    public Set<String> getUsers(){ return users; }
    public void removeUser(String name){ users.remove(name); }
    void setUsers(Set<String> users){ this.users = users; }
}