package com.pranav.bridgetotalk.adapter.out.jpa.dto;

import java.time.LocalTime;

public record TimeRangeJson(
        LocalTime start,
        LocalTime end
) {}
