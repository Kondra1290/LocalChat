package Server;

import Client.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllUsers {
    protected Map<String, Connection> users;
    protected ArrayList<String> logInUsers;
    
    protected AllUsers(){
        users = new HashMap<>();
        logInUsers = new ArrayList<>();
    }
    
    protected boolean addUser(String name, Connection connection){
        if(!users.containsKey(name)){
            users.put(name, connection);
            return true;
        }
        else return false;
    }
    protected boolean getUser(String name){
        return users.containsKey(name);
    }
    //в разработке
    protected Map<String, Connection> getUsers(){
        return users;
    }
    
    protected void deleteUser(String name){
        
        users.remove(name);
    }
}
