package com.pranav.bridgetotalk.application.port.out;

import com.pranav.bridgetotalk.domain.attendance.Conversation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversationRepositoryPort {

    Conversation save(Conversation conversation);

    Optional<Conversation> findById(UUID id);

    // Method to search for conversations ready for routing
    List<Conversation> findWaitingInQueue(UUID companyId);

    // Method to search for active conversations of a specific agent
    List<Conversation> findActiveByAgentId(UUID agentId);
}