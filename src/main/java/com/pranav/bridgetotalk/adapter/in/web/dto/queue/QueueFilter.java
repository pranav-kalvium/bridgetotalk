package com.pranav.bridgetotalk.adapter.in.web.dto.queue;

import com.pranav.bridgetotalk.adapter.in.web.dto.QueryOptions;

import java.util.Optional;

public record QueueFilter(
        Optional<String> name,
        QueryOptions queryOptions
) {
}
