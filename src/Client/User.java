package Client;

import Message.Message;
import Message.MessageType;
import Server.Server;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    protected String name;
    protected Connection connect;
    protected boolean isConnected, logIn;
    protected static ClientGUI gui;
    //private Socket socket;
    protected static ListOfUsers connectedUsers;
    
    public User(){
        isConnected = false;
        name = "";
        logIn = false;
    }
    
    public static void main(String args[]) throws IOException {
        User user = new User();
        gui = new ClientGUI(user);
        gui.setVisible(true);
        connectedUsers = new ListOfUsers();
        
        user.connectWithServer();
        
        while(true){
            if(user.isConnected){
                user.recieveMessage();
            }
        }
    }
    
    protected void Registrarion(String name) throws IOException{
        this.sendMessage(new Message(name, MessageType.USER_REGISTRATION));
    }
    
//    protected void logIN(String name) throws IOException{
//        if(!logIn) this.sendMessage(new Message(name, MessageType.LOG_IN));
//    }
    
    protected void connectWithServer(){
        try{     
            //Socket socket = new Socket("localhost", Server.PORT);
            Socket socket = new Socket("localhost", Server.PORT);
            isConnected = true;
            connect = new Connection(socket);
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public void sendMessage(Message message) throws IOException{
        if(isConnected){
            connect.sendMessage(message);
        }
    }
    
    public void recieveMessage(){
        Message message;
        
        while(getConnect()){
            try {
                message = connect.recieveMessage();
                
                if(message != null)
                    gui.readMessage(message);
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }catch (IOException ex) {
                System.out.println("Вы отключены от сервера"); // переименовать
            }
        }
    }
    
    public boolean getConnect(){
        return isConnected;
    }

    public boolean Disconnect() throws IOException{
        try {
            if(getConnect()){
                sendMessage(new Message(name, MessageType.DISCONNECT_USSER));
                connectedUsers.users.clear();
                gui.refreashList(connectedUsers);
                isConnected = false;
                //connect.close();
                return true;
            }else return false;
        } catch (Exception e) {
            System.out.println("Сервисное сообщение: произошла ошибка при отключении.");
        }
        return false;
    }
}