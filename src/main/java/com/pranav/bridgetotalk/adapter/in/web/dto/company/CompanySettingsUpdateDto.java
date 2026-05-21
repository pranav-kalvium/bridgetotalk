package com.pranav.bridgetotalk.adapter.in.web.dto.company;

import com.pranav.bridgetotalk.domain.organization.Plan;

public record CompanySettingsUpdateDto(
        Integer maxAgents,
        Integer maxQueues,
        String timezone,
        String language,
        Plan plan,
        int maxConcurrentConversationsPerAgent
) {}