package Server;

import Client.Connection;
import Message.Message;
import Message.MessageType;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Server {
    public static final int PORT = 8010;
//    protected int PORT;
    protected Map<String, ServerThread> threads;
    protected AllUsers allUsers;
    
    protected static ServerPanel gui;
    protected boolean isStarted;
    protected Socket socket;
    private static ServerSocket serverSocket;
    
    Server(){
        isStarted = false;
        serverSocket = null;
        socket = null;
        threads = new HashMap<>();
        allUsers = new AllUsers();
    }
    
    public static void main(String[] args) {
        Server server = new Server();
        gui = new ServerPanel(server);
        gui.initFrameServer();
        server.runServer(0);
        
        while(true){
            if(server.isStarted){
                gui.refreshDialogWindowServer("Сервер запущен.");
                server.acceptUsers();
                server.isStarted = false;
            }
        }
    }
    
    protected void runServer(int port)
    {
        //PORT = port;
        try{
            isStarted = true;
            serverSocket = new ServerSocket(PORT);  
        }catch (IOException ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    protected void acceptUsers()
    {
        try{
            while(true){
                socket = serverSocket.accept();
                createNewServerThread();
            }
        }catch (IOException ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }finally {
            try {
                socket.close();
                gui.refreshDialogWindowServer("Сервер остановлен.");
                serverSocket.close();
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    protected void createNewServerThread(){
        ServerThread serverThread = new ServerThread(socket, this);
        gui.refreshDialogWindowServer("Добавлен новый поток");
        serverThread.start();
    }
    
    public void sendMesageToAllUsers(Message message) throws IOException
    {
        for(ServerThread thread: threads.values())
            thread.sendMessageToUser(message);
    }
    
    protected void sendListOfUsers(String text) throws IOException
    {
        if(!allUsers.users.keySet().isEmpty()){
            Message listOfUsers;
            Set<String> listUsers = new HashSet<>();
            
            for(Map.Entry<String, Connection> user : allUsers.getUsers().entrySet())
                listUsers.add(user.getKey());
            
            listOfUsers = new Message(MessageType.LIST_OF_USERS, listUsers, text);
            
            for(String name: threads.keySet())
                threads.get(name).sendMessageToUser(listOfUsers);
        }    
    }
    
    protected void stopServer(){
        try {
            //если серверныйСокет не имеет ссылки или не запущен
            if (serverSocket != null && !serverSocket.isClosed()) {
                for (Map.Entry<String, Connection> user : allUsers.getUsers().entrySet()) {
                    user.getValue().close();
                }
                serverSocket.close();
                allUsers.getUsers().clear();
                gui.refreshDialogWindowServer("Сервер остановлен");
            } else gui.refreshDialogWindowServer("Сервер не запущен - останавливать нечего!");
        } catch (Exception e) {
            gui.refreshDialogWindowServer("Остановить сервер не удалось.");
        }
    }

    
//    public void sendMessageToServer(ServerThread serverThread, String message){
//        for(ServerThread serverThread: serverThreadsList){
//            serverThread.sendMessageToClient(message);
//        }
//          serverThread.sendMessageToClient(message);
//    }
//    
//     public static synchronized UsersList getUsersList(){
//         if(list != null)return list;
//         else return new UsersList();
//    }

}
