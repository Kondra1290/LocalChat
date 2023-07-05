package Message;

public enum MessageType {
    USER_REGISTRATION("�� ��������� � ������� ��� ����� "),
    REGISTRATION_ERROR("������������ � ������ ����� ��� ����������."),
    NAME_IS_USED("��� ������ ����� ��� ����� � �������."),
    LIST_OF_USERS("������ ������������ ������������� ��������."),
    TEXT_MESSAGE("��� ������ ��������� �� "),
    DISCONNECT_USSER("�� ����� �� �������.");
    
    private String text;
    
    MessageType(String text){this.text = text; }
    public String getMessage(){ return text; }
}
