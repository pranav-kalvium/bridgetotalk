package com.pranav.bridgetotalk.application.service;

import com.pranav.bridgetotalk.adapter.in.web.dto.agent.AgentFilter;
import com.pranav.bridgetotalk.application.mapper.AgentCommandMapper;
import com.pranav.bridgetotalk.application.port.in.ManageAgentUseCase;
import com.pranav.bridgetotalk.application.port.in.command.CreateAgentCommand;
import com.pranav.bridgetotalk.application.port.out.AgentQueueRepositoryPort;
import com.pranav.bridgetotalk.application.port.out.AgentRepositoryPort;
import com.pranav.bridgetotalk.application.port.out.CompanyRepositoryPort;
import com.pranav.bridgetotalk.domain.attendance.Queue;
import com.pranav.bridgetotalk.domain.organization.CompanyNotFoundException;
import com.pranav.bridgetotalk.domain.people.Agent;
import com.pranav.bridgetotalk.domain.people.AgentNotFoundException;
import com.pranav.bridgetotalk.domain.people.AgentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManagmentAgentService implements ManageAgentUseCase {

    private final AgentRepositoryPort repository;
    private final CompanyRepositoryPort companyRepository;
    private final AgentQueueRepositoryPort agentQueueRepository;
    private final AgentCommandMapper mapper;

    @Override
    public Agent create(CreateAgentCommand command) {

        companyRepository.findById(command.companyId())
                .orElseThrow(() -> new CompanyNotFoundException(command.companyId()));

        var newAgentDomain = mapper.toDomain(command);
        return repository.save(newAgentDomain);
    }

    @Override
    public Agent getActiveAgent(UUID id, UUID companyId) {
        return repository.findActiveAgentByIdAndCompanyId(id, companyId).
                orElseThrow(() -> new AgentNotFoundException(id));
    }

    @Override
    public List<Agent> filterAgentsByCompanyId(AgentFilter agentFilter, UUID companyId) {

        return repository.findAllActiveAgentsByCompanyId(agentFilter, companyId);
    }

    public Agent findActiveAgentByCompanyIdAndEmail(UUID companyId, String email){
        return repository.findActiveAgentByCompanyIdAndEmail(companyId, email)
                .orElseThrow(() -> new AgentNotFoundException(email));
    }

    @Override
    public void updateAgentStatus(UUID id, UUID companyId, AgentStatus status) {

        var existingAgent = getActiveAgent(id, companyId);
        existingAgent.changeStatus(status);

        repository.updateStatus(existingAgent);
    }

    @Override
    public void deleteAgent(UUID id, UUID companyId) {
        repository.deleteAgent(id, companyId);
    }

    @Override
    public List<Queue> findQueuesByAgentId(UUID agentId) {
        return agentQueueRepository.findQueuesByAgentId(agentId);
    }
}
