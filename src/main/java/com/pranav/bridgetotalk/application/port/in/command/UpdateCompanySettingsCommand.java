package com.pranav.bridgetotalk.application.port.in.command;

import com.pranav.bridgetotalk.domain.organization.Plan;

public record UpdateCompanySettingsCommand(
        Integer maxAgents,
        Integer maxQueues,
        String timezone,
        String language,
        Plan plan
) {}
