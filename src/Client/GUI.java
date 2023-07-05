/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import Message.Message;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class GUI {
    private JFrame frame = new JFrame("Переписка");
    private JTextArea dialogWindow = new JTextArea(10, 40);
    private JTextArea messageWindow = new JTextArea(10, 40);
    private JButton send = new JButton("Отправить");
    private JPanel panelButtons = new JPanel();
    private String yourName, anotherName;
    private User user;
    
    
    public GUI(User user, String anotherName){
        this.user = user;
        this.anotherName = anotherName;
        ininComponents();
    }
    
    private void ininComponents(){
        dialogWindow.setColumns(20);
        dialogWindow.setRows(5);
        messageWindow.setColumns(10);
        messageWindow.setRows(3);

        frame.add(new JScrollPane(dialogWindow), BorderLayout.CENTER);
        frame.add(new JScrollPane(messageWindow), BorderLayout.CENTER);
        panelButtons.add(send);
        frame.add(panelButtons, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null); // при запуске отображает окно по центру экрана
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //класс обработки события при закрытии окна приложения Сервера
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
        
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    String text = messageWindow.getText().trim();
                    if(text != null && !text.isEmpty()){
                        user.sendMessage(new Message(user.name, anotherName, text));
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }
    
    
}
