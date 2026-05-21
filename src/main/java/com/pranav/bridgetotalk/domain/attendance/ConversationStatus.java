package com.pranav.bridgetotalk.domain.attendance;

public enum ConversationStatus {
    WAITING_IN_QUEUE("Waiting in Queue"), // The conversation has arrived and is waiting for an agent.
    IN_PROGRESS("In Progress"),           // An agent is active in the conversation.
    WAITING_FOR_CUSTOMER("Waiting for Customer"), // Agent responded, waiting for the customer's next message.
    PENDING("Pending"),                   // Paused or on hold for a third party.
    CLOSED("Closed");                     // Conversation finalized and archived.

    private final String description;

    ConversationStatus(String description) {
        this.description = description;
    }
}