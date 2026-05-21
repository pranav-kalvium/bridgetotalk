package com.pranav.bridgetotalk.adapter.in.web.dto.queue;

import java.util.List;

public record DailyScheduleDto(
        List<TimeRangeDto> ranges
) {}
