package com.pranav.bridgetotalk.application.port.in.command;

import java.util.UUID;

public record LinkQueueAgentCommand(
        UUID queueId,
        UUID agentId,
        Integer priority
) {
}
