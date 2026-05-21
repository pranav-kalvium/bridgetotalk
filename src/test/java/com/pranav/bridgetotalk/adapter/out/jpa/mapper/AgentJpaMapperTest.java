package com.pranav.bridgetotalk.adapter.out.jpa.mapper;

import com.pranav.bridgetotalk.adapter.out.jpa.entity.AgentJpaEntity;
import com.pranav.bridgetotalk.domain.people.Agent;
import com.pranav.bridgetotalk.domain.people.AgentRole;
import com.pranav.bridgetotalk.domain.people.AgentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AgentJpaMapperTest {

    private final AgentJpaMapper mapper = new AgentJpaMapperImpl();

    @Test
    @DisplayName("Should map Agent domain object to AgentJpaEntity correctly")
    void shouldMapDomainToEntity() {
        Agent agent = Agent.rehydrate(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "John Doe",
                "john@example.com",
                "hashedpwd",
                AgentRole.ADMIN,
                AgentStatus.AVAILABLE,
                Instant.now(),
                Instant.now(),
                null,
                2
        );

        AgentJpaEntity entity = mapper.toEntity(agent);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(agent.getId());
        assertThat(entity.getCompanyId()).isEqualTo(agent.getCompanyId());
        assertThat(entity.getName()).isEqualTo(agent.getName());
        assertThat(entity.getEmail()).isEqualTo(agent.getEmail());
        assertThat(entity.getPasswordHash()).isEqualTo(agent.getPasswordHash());
        assertThat(entity.getRole()).isEqualTo(agent.getRole());
        assertThat(entity.getStatus()).isEqualTo(agent.getStatus());
        assertThat(entity.getCreatedAt()).isEqualTo(agent.getCreatedAt());
        assertThat(entity.getUpdatedAt()).isEqualTo(agent.getUpdatedAt());
        assertThat(entity.getDeletedAt()).isNull();
        assertThat(entity.getActiveConversations()).isEqualTo(agent.getActiveConversations());

        // Verify Lombok Builder Default fix works and queues is not null
        assertThat(entity.getQueues()).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("Should map AgentJpaEntity back to Agent domain object correctly")
    void shouldMapEntityToDomain() {
        AgentJpaEntity entity = AgentJpaEntity.builder()
                .id(UUID.randomUUID())
                .companyId(UUID.randomUUID())
                .name("Alice Smith")
                .email("alice@example.com")
                .passwordHash("pwdhash")
                .role(AgentRole.AGENT)
                .status(AgentStatus.PAUSED)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .deletedAt(null)
                .activeConversations(1)
                .build();

        Agent agent = mapper.toDomain(entity);

        assertThat(agent).isNotNull();
        assertThat(agent.getId()).isEqualTo(entity.getId());
        assertThat(agent.getCompanyId()).isEqualTo(entity.getCompanyId());
        assertThat(agent.getName()).isEqualTo(entity.getName());
        assertThat(agent.getEmail()).isEqualTo(entity.getEmail());
        assertThat(agent.getPasswordHash()).isEqualTo(entity.getPasswordHash());
        assertThat(agent.getRole()).isEqualTo(entity.getRole());
        assertThat(agent.getStatus()).isEqualTo(entity.getStatus());
        assertThat(agent.getCreatedAt()).isEqualTo(entity.getCreatedAt());
        assertThat(agent.getUpdatedAt()).isEqualTo(entity.getUpdatedAt());
        assertThat(agent.getDeletedAt()).isNull();
        assertThat(agent.getActiveConversations()).isEqualTo(entity.getActiveConversations());
    }

    @Test
    @DisplayName("Should handle null mappings gracefully")
    void shouldHandleNulls() {
        assertThat(mapper.toEntity(null)).isNull();
        assertThat(mapper.toDomain(null)).isNull();
    }
}
