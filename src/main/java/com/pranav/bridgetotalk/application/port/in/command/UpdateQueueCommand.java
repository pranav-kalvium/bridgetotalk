package com.pranav.bridgetotalk.application.port.in.command;

import com.pranav.bridgetotalk.domain.attendance.DistributionStrategy;
import com.pranav.bridgetotalk.domain.attendance.QueueSettings;

import java.util.UUID;

public record UpdateQueueCommand (
        UUID id,
        UUID companyId,
        String name,
        DistributionStrategy distributionStrategy,
        QueueSettings settings
) {
}
