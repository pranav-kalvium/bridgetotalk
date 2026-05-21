package com.pranav.bridgetotalk.domain.people;

public enum AgentStatus {
    AVAILABLE("Available"),       // Can receive new conversations (rotation).
    BUSY("Busy"),                 // Is in a conversation, but may still be eligible depending on the strategy.
    PAUSED("Paused"),             // Unavailable (e.g. lunch, meeting). Does not receive new conversations.
    OFFLINE("Offline");           // Logged out of the system.

    private final String description;

    AgentStatus(String description) {
        this.description = description;
    }

    public static AgentStatus from(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            return AgentStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid value after validation: " + value);
        }
    }
}