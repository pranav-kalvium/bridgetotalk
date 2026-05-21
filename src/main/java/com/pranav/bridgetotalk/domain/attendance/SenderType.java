package com.pranav.bridgetotalk.domain.attendance;

public enum SenderType {
    CUSTOMER("Customer"),
    AGENT("Agent"),
    SYSTEM("System"); // For automated messages, like closure notifications.


    SenderType(String customer) {

    }
}