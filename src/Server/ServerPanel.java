package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerPanel extends javax.swing.JFrame {
    
    private JFrame frame = new JFrame("������ �������");
    private JTextArea dialogWindow = new JTextArea(10, 40);
    private JButton buttonStartServer = new JButton("��������� ������");
    private JButton buttonStopServer = new JButton("���������� ������");
    private JPanel panelButtons = new JPanel();
    private final Server server;
    
    ServerPanel(Server server){
        this.server = server;
    }
    
    protected void initFrameServer() {
        dialogWindow.setEditable(false);
        dialogWindow.setLineWrap(true);  //�������������� ������� ������ � JTextArea
        frame.add(new JScrollPane(dialogWindow), BorderLayout.CENTER);
        panelButtons.add(buttonStartServer);
        panelButtons.add(buttonStopServer);
        frame.add(panelButtons, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null); // ��� ������� ���������� ���� �� ������ ������
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //����� ��������� ������� ��� �������� ���� ���������� �������
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                server.stopServer();
                System.exit(0);
            }
        });
        frame.setVisible(true);

        buttonStartServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                int port = getPortFromOptionPane();
//                server.runServer(port);
                server.runServer(0);
            }
        });
        
        buttonStopServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.stopServer();
            }
        });
    }
    
    protected int getPortFromOptionPane() {
        while (true) {
            String port = JOptionPane.showInputDialog(
                    frame, "������� ���� �������:",
                    "���� ����� �������",
                    JOptionPane.QUESTION_MESSAGE
            );
            try {
                return Integer.parseInt(port.trim());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        frame, "������ ������������ ���� �������. ���������� ��� ���.",
                        "������ ����� ����� �������", JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    public void refreshDialogWindowServer(String serviceMessage) {
        dialogWindow.append(serviceMessage + "\n");
    }
}
