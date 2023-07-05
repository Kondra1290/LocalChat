package Server;

import Client.Connection;
import Message.Message;
import Message.MessageType;
import static Server.Server.gui;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Thread{
    protected Socket socket;
    protected Connection connection;
    protected Server server;
    protected boolean interrupt;
    
    public ServerThread(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        interrupt = true;
    }
    
    public void sendMessageToUser(Message message)throws IOException{
        connection.sendMessage(message);
    }
    
    protected void recieveMessage() throws IOException, ClassNotFoundException
    {
        while(true){
            Message message = connection.recieveMessage();
            if(message == null) continue;
            
            if(message.getType() == MessageType.TEXT_MESSAGE){
                sendMesageToAnoutherUser(message);
            }
            else if(message.getType() == MessageType.USER_REGISTRATION){
                if(!server.allUsers.getUser(message.getName()))
                {
                    gui.refreshDialogWindowServer("Добавлен новый пользователь " + message.getName());
                    server.allUsers.addUser(message.getName(), connection);
                    server.threads.put(message.getName(), this);
                    sendMessageToUser(message);
                    server.sendListOfUsers(" Добавлен новый пользователь " + message.getName());
                }
                else sendMessageBack(new Message(MessageType.NAME_IS_USED));
            }
            else if(message.getType() == MessageType.DISCONNECT_USSER){
                gui.refreshDialogWindowServer("Пользователь отключился " + message.getName());
                server.allUsers.deleteUser(message.getName());
                //server.allUsers.logInUsers.remove(message.getName());
                server.threads.remove(message.getName());
                server.sendListOfUsers(" Пользователь отключился " + message.getName());
                break;
            }   
        }
     }
        
    
    private void sendMessageBack(Message message) throws IOException {
        sendMessageToUser(message);
    }
    
    private void sendMesageToAnoutherUser(Message message) throws IOException {
        gui.refreshDialogWindowServer("Сообщение от " + message.getName()+ " к " + message.getReciver());
        if(!message.getReciver().isEmpty())
                server.threads.get(message.getReciver()).sendMessageToUser(message);
    }
    
    public void run(){      
        try {
            server.gui.refreshDialogWindowServer("Новый поток запущен");
            connection = new Connection(socket);
            recieveMessage(); 
            Stop();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    public void Stop(){
        try{
            connection.close();
            interrupt = false;
            if(!Thread.currentThread().isAlive()) server.gui.refreshDialogWindowServer("Поток остановлен");            
        }
        catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
        }
    }
}
