package com.pranav.bridgetotalk.adapter.in.web.dto.agent;

import com.pranav.bridgetotalk.domain.people.AgentStatus;

import java.util.UUID;

public record UpdateAgentDto (
        UUID companyId,

        AgentStatus status
) {}
