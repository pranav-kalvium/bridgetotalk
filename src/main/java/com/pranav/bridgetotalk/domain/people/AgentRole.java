package com.pranav.bridgetotalk.domain.people;

public enum AgentRole {
    ADMIN("Admin"),       // Full access, can manage companies, plans, and global configurations.
    MANAGER("Manager"),   // Team manager. Can manage agents, queues, and reports of their company.
    AGENT("Agent");       // Standard agent. Can manage their own conversations.

    private final String description;

    AgentRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
