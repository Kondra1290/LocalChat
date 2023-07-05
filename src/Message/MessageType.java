package Message;

public enum MessageType {
    USER_REGISTRATION("Вы добавлены в систему под ником "),
    REGISTRATION_ERROR("Пользователь с данным ником уже существует."),
    NAME_IS_USED("Под данным ником уже вощли в систему."),
    LIST_OF_USERS("Список подключенных пользователей обновлен."),
    TEXT_MESSAGE("Вам пришло сообщение от "),
    DISCONNECT_USSER("Вы вышли из системы.");
    
    private String text;
    
    MessageType(String text){this.text = text; }
    public String getMessage(){ return text; }
}
