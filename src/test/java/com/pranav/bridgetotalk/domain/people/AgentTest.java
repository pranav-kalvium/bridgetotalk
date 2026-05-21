package com.pranav.bridgetotalk.domain.people;

import com.pranav.bridgetotalk.domain.shared.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AgentTest {

    private final UUID companyId = UUID.randomUUID();

    @Test
    @DisplayName("Should create new agent with default offline status and zero active conversations")
    void shouldCreateNewAgentWithDefaults() {
        Agent agent = Agent.createNew(
                companyId,
                "Jane Doe",
                "jane@example.com",
                "hashedpassword",
                AgentRole.AGENT
        );

        assertThat(agent.getId()).isNotNull();
        assertThat(agent.getCompanyId()).isEqualTo(companyId);
        assertThat(agent.getName()).isEqualTo("Jane Doe");
        assertThat(agent.getEmail()).isEqualTo("jane@example.com");
        assertThat(agent.getPasswordHash()).isEqualTo("hashedpassword");
        assertThat(agent.getRole()).isEqualTo(AgentRole.AGENT);
        assertThat(agent.getStatus()).isEqualTo(AgentStatus.OFFLINE);
        assertThat(agent.getActiveConversations()).isZero();
        assertThat(agent.getCreatedAt()).isNotNull();
        assertThat(agent.getUpdatedAt()).isNotNull();
        assertThat(agent.getDeletedAt()).isNull();
    }

    @Test
    @DisplayName("Should change agent status and update timestamp")
    void shouldChangeAgentStatus() {
        Agent agent = Agent.createNew(
                companyId,
                "Jane Doe",
                "jane@example.com",
                "hashedpassword",
                AgentRole.AGENT
        );

        var originalUpdatedAt = agent.getUpdatedAt();

        // Change status to AVAILABLE
        agent.changeStatus(AgentStatus.AVAILABLE);

        assertThat(agent.getStatus()).isEqualTo(AgentStatus.AVAILABLE);
        assertThat(agent.getUpdatedAt()).isAfterOrEqualTo(originalUpdatedAt);
    }

    @Test
    @DisplayName("Should throw BusinessException when changing to the same status")
    void shouldThrowExceptionWhenChangingToSameStatus() {
        Agent agent = Agent.createNew(
                companyId,
                "Jane Doe",
                "jane@example.com",
                "hashedpassword",
                AgentRole.AGENT
        );

        // Status is already OFFLINE by default
        assertThatThrownBy(() -> agent.changeStatus(AgentStatus.OFFLINE))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Agent already in status: OFFLINE");
    }

    @Test
    @DisplayName("Should manage active conversations count correctly")
    void shouldManageActiveConversationsCount() {
        Agent agent = Agent.createNew(
                companyId,
                "Jane Doe",
                "jane@example.com",
                "hashedpassword",
                AgentRole.AGENT
        );

        agent.incrementActiveConversations();
        assertThat(agent.getActiveConversations()).isEqualTo(1);

        agent.decrementActiveConversations();
        assertThat(agent.getActiveConversations()).isZero();

        // Should not allow decrementing below zero
        assertThatThrownBy(agent::decrementActiveConversations)
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Active conversations cannot be negative");
    }

    @Test
    @DisplayName("Should determine if agent can receive new conversations based on status and limits")
    void shouldCheckIfAgentCanReceiveConversations() {
        Agent agent = Agent.createNew(
                companyId,
                "Jane Doe",
                "jane@example.com",
                "hashedpassword",
                AgentRole.AGENT
        );

        // 1. Offline agents cannot receive conversations
        assertThat(agent.canReceiveNewConversation(3)).isFalse();

        // 2. Make agent available
        agent.changeStatus(AgentStatus.AVAILABLE);
        assertThat(agent.canReceiveNewConversation(3)).isTrue();

        // 3. Increment conversations to reach limit
        agent.incrementActiveConversations();
        agent.incrementActiveConversations();
        agent.incrementActiveConversations(); // count = 3
        assertThat(agent.canReceiveNewConversation(3)).isFalse();

        // 4. Paused agents cannot receive conversations even if below limit
        agent.decrementActiveConversations(); // count = 2
        agent.changeStatus(AgentStatus.PAUSED);
        assertThat(agent.canReceiveNewConversation(3)).isFalse();
    }
}
