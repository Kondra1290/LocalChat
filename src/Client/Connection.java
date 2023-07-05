/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

/**
 *
 * @author shura
 */
import Message.Message;
import java.io.Closeable;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Connection implements Closeable {
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    
    public Connection(Socket socket) throws IOException
    {
        this.socket = socket;
        InputStream i = socket.getInputStream();
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(i);
    }
    
    public void sendMessage(Message message) throws IOException
    {
        synchronized (this.out){
            out.writeObject(message);
        }
    }
    
    public Message recieveMessage() throws IOException, ClassNotFoundException
    {
        synchronized (this.in){
            Message message = (Message) in.readObject();
            return message;
        }       
    }
    
    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
