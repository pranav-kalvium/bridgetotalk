package com.pranav.bridgetotalk.application.port.in;

import com.pranav.bridgetotalk.adapter.in.web.dto.agent.AgentFilter;
import com.pranav.bridgetotalk.application.port.in.command.CreateAgentCommand;
import com.pranav.bridgetotalk.domain.attendance.Queue;
import com.pranav.bridgetotalk.domain.people.Agent;
import com.pranav.bridgetotalk.domain.people.AgentStatus;

import java.util.List;
import java.util.UUID;

public interface ManageAgentUseCase {

    Agent create(CreateAgentCommand command);

    Agent getActiveAgent(UUID id, UUID companyId);

    List<Agent> filterAgentsByCompanyId(AgentFilter agentFilter, UUID companyId);

    void updateAgentStatus(UUID id, UUID companyId, AgentStatus status);

    void deleteAgent(UUID id, UUID companyId);

    List<Queue> findQueuesByAgentId(UUID agentId);

}