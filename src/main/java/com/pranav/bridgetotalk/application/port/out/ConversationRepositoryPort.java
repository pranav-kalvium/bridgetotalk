package com.pranav.bridgetotalk.application.port.out;

import com.pranav.bridgetotalk.domain.attendance.Conversation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversationRepositoryPort {

    Conversation save(Conversation conversation);

    Optional<Conversation> findById(UUID id);

    // MÃ©todo para buscar conversas prontas para roteamento
    List<Conversation> findWaitingInQueue(UUID companyId);

    // MÃ©todo para buscar conversas ativas de um agente especÃ­fico
    List<Conversation> findActiveByAgentId(UUID agentId);
}