package com.pranav.bridgetotalk.adapter.in.web.dto.queue;

import jakarta.validation.constraints.NotNull;

public record QueueAgentLinkDto (

        @NotNull(message = "Priority can not be nuyll")
        Integer priority
) {
}
