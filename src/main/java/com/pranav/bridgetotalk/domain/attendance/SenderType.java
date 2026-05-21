package com.pranav.bridgetotalk.domain.attendance;

public enum SenderType {
    CUSTOMER("Customer"),
    AGENT("Agent"),
    SYSTEM("System"); // Para mensagens automÃ¡ticas, como notificaÃ§Ã£o de fechamento.


    SenderType(String customer) {

    }
}