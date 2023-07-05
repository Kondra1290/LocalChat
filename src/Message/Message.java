package Message;

import Client.Connection;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;


public class Message implements Serializable {
    private String from, to, text;
    private String information;
    private LocalDate date;
    private LocalTime time;
    private MessageType type;
    private Set<String> users;
    
    public Message(){
        time = LocalTime.now();
        date = LocalDate.now();
        users = null;
    }
    
    public Message(String name1, String name2, String text){
        this();
        this.type = MessageType.TEXT_MESSAGE;
        from = name1;
        to = name2;
        this.text = text;
        Info();
    }    
    public Message(String newName, MessageType type){
        this.type = type;
        this.from = newName;
    }
    public Message(MessageType type, Set<String> set, String text){
        this.type = type;
        this.users = set;
        this.text = text;
    }   
    public Message(MessageType type){
        this.type = type;
    }
    public Message(MessageType type, String text){
        this.type = type;
        this.text = text;
    }
    
    private void Info(){
        String str1 = String.format("Отправитель: %s\nПолучатель: %s\n",from ,to); 
        String str2 = String.format("Время получения: %s\n", date.toString(), time.toString());
        information = str1 + str2 + text;
    }
    
    public String getSendName(){ return from; }
    public String getText(){ return text; }
    public Set<String> getListOfUsers(){ return users; }
    public MessageType getType(){ return type; }
    public String getInfo(){ return information; }
    public String getName(){ return from; }
    public String getReciver(){ return to; }
}