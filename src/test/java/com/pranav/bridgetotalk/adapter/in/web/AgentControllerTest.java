package com.pranav.bridgetotalk.adapter.in.web;

import com.pranav.bridgetotalk.adapter.in.web.dto.agent.AgentDto;
import com.pranav.bridgetotalk.adapter.in.web.mapper.AgentDtoMapper;
import com.pranav.bridgetotalk.adapter.in.web.mapper.CompanyDtoMapper;
import com.pranav.bridgetotalk.adapter.in.web.mapper.QueueDtoMapper;
import com.pranav.bridgetotalk.application.service.ManagmentAgentService;
import com.pranav.bridgetotalk.domain.people.Agent;
import com.pranav.bridgetotalk.domain.people.AgentRole;
import com.pranav.bridgetotalk.domain.people.AgentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentControllerTest {

    @Mock
    private ManagmentAgentService service;

    @Mock
    private AgentDtoMapper mapper;

    @Mock
    private QueueDtoMapper queueDtoMapper;

    @Mock
    private CompanyDtoMapper companyDtoMapper;

    @InjectMocks
    private AgentController controller;

    @Test
    @DisplayName("Should return active agent when found by ID and company ID")
    void shouldReturnActiveAgent() {
        UUID agentId = UUID.randomUUID();
        UUID companyId = UUID.randomUUID();

        Agent mockAgent = Agent.rehydrate(
                agentId,
                companyId,
                "John Doe",
                "john@example.com",
                "hashedpassword",
                AgentRole.AGENT,
                AgentStatus.AVAILABLE,
                Instant.now(),
                Instant.now(),
                null,
                0
        );

        AgentDto mockAgentDto = new AgentDto(
                agentId,
                companyId,
                "John Doe",
                "john@example.com",
                null,
                AgentRole.AGENT,
                AgentStatus.AVAILABLE,
                Instant.now(),
                null
        );

        when(service.getActiveAgent(agentId, companyId)).thenReturn(mockAgent);
        when(mapper.toDto(mockAgent)).thenReturn(mockAgentDto);

        ResponseEntity<AgentDto> response = controller.getActiveAgentByEmailAndCompany(agentId, companyId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().id()).isEqualTo(agentId);
        assertThat(response.getBody().companyId()).isEqualTo(companyId);
        assertThat(response.getBody().name()).isEqualTo("John Doe");
        assertThat(response.getBody().email()).isEqualTo("john@example.com");
    }
}
