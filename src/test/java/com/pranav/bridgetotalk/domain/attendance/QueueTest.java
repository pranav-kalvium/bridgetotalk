package com.pranav.bridgetotalk.domain.attendance;

import com.pranav.bridgetotalk.domain.shared.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QueueTest {

    private final UUID companyId = UUID.randomUUID();

    @Test
    @DisplayName("Should create queue with valid name and initialize with default settings")
    void shouldCreateNewQueueWithDefaults() {
        Queue queue = Queue.createNew(
                companyId,
                "Support Queue",
                DistributionStrategy.ROUND_ROBIN,
                null
        );

        assertThat(queue.getId()).isNotNull();
        assertThat(queue.getCompanyId()).isEqualTo(companyId);
        assertThat(queue.getName()).isEqualTo("Support Queue");
        assertThat(queue.getDistributionStrategy()).isEqualTo(DistributionStrategy.ROUND_ROBIN);
        assertThat(queue.getCreatedAt()).isNotNull();
        assertThat(queue.getUpdatedAt()).isNotNull();
        assertThat(queue.getDeletedAt()).isNull();
        assertThat(queue.getSettings()).isNotNull();
        
        // Assert defaults initialized in QueueSettings
        assertThat(queue.getSettings().getWelcomeMessage().enabled()).isFalse();
        assertThat(queue.getSettings().getOffHoursMessage().enabled()).isFalse();
        assertThat(queue.getSettings().getWaitingMessage().enabled()).isFalse();
        assertThat(queue.getSettings().getWeeklySchedule()).isNotNull();
    }

    @Test
    @DisplayName("Should default distribution strategy to LEAST_BUSY when creating queue if null")
    void shouldDefaultDistributionStrategyToLeastBusy() {
        Queue queue = Queue.createNew(
                companyId,
                "Support Queue",
                null,
                null
        );

        assertThat(queue.getDistributionStrategy()).isEqualTo(DistributionStrategy.LEAST_BUSY);
    }

    @Test
    @DisplayName("Should throw BusinessException when creating queue with blank name")
    void shouldThrowExceptionWhenQueueNameIsBlank() {
        assertThatThrownBy(() -> Queue.createNew(companyId, "  ", null, null))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Queue name can not be blank");
    }

    @Test
    @DisplayName("Should update queue details and modify updatedAt timestamp")
    void shouldUpdateQueueDetails() {
        Queue queue = Queue.createNew(
                companyId,
                "Support Queue",
                DistributionStrategy.LEAST_BUSY,
                null
        );

        var originalUpdatedAt = queue.getUpdatedAt();

        queue.update("Billing Queue", DistributionStrategy.ROUND_ROBIN);

        assertThat(queue.getName()).isEqualTo("Billing Queue");
        assertThat(queue.getDistributionStrategy()).isEqualTo(DistributionStrategy.ROUND_ROBIN);
        assertThat(queue.getUpdatedAt()).isAfterOrEqualTo(originalUpdatedAt);
    }

    @Test
    @DisplayName("Should not update queue name if update name is blank or null")
    void shouldNotUpdateQueueNameIfEmpty() {
        Queue queue = Queue.createNew(
                companyId,
                "Support Queue",
                DistributionStrategy.LEAST_BUSY,
                null
        );

        queue.update("", null);
        assertThat(queue.getName()).isEqualTo("Support Queue");

        queue.update(null, null);
        assertThat(queue.getName()).isEqualTo("Support Queue");
    }

    @Test
    @DisplayName("Should correctly determine business hours status")
    void shouldCorrectlyVerifyBusinessHours() {
        // Queue default settings has standard business hours: Mon-Fri 9:00 - 18:00
        Queue queue = Queue.createNew(
                companyId,
                "Support Queue",
                DistributionStrategy.LEAST_BUSY,
                null
        );

        // Monday at 10:00 AM -> Open
        LocalDateTime mondayMorning = LocalDateTime.of(2026, 5, 18, 10, 0); // May 18, 2026 is Monday
        assertThat(queue.isOpenForAttendance(mondayMorning)).isTrue();

        // Monday at 8:59 AM -> Closed (inclusive/exclusive boundary: opens at 9:00)
        LocalDateTime mondayEarly = LocalDateTime.of(2026, 5, 18, 8, 59);
        assertThat(queue.isOpenForAttendance(mondayEarly)).isFalse();

        // Monday at 6:00 PM -> Closed (exclusive boundary: closes at 18:00)
        LocalDateTime mondayClosed = LocalDateTime.of(2026, 5, 18, 18, 0);
        assertThat(queue.isOpenForAttendance(mondayClosed)).isFalse();

        // Saturday at 12:00 PM -> Closed (weekend)
        LocalDateTime saturdayNoon = LocalDateTime.of(2026, 5, 23, 12, 0); // May 23, 2026 is Saturday
        assertThat(queue.isOpenForAttendance(saturdayNoon)).isFalse();
    }

    @Test
    @DisplayName("Should validate time range start and end parameters")
    void shouldValidateTimeRangeParameters() {
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);

        TimeRange range = new TimeRange(start, end);
        assertThat(range.start()).isEqualTo(start);
        assertThat(range.end()).isEqualTo(end);

        // start equals end should throw exception
        assertThatThrownBy(() -> new TimeRange(start, start))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Start time must be before end time");

        // start after end should throw exception
        assertThatThrownBy(() -> new TimeRange(end, start))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Start time must be before end time");
    }

    @Test
    @DisplayName("Should validate overlapping time ranges in daily schedule")
    void shouldValidateOverlappingTimeRanges() {
        TimeRange range1 = new TimeRange(LocalTime.of(9, 0), LocalTime.of(12, 0));
        TimeRange range2 = new TimeRange(LocalTime.of(11, 0), LocalTime.of(15, 0));

        assertThatThrownBy(() -> new DailySchedule(List.of(range1, range2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Time ranges must not overlap");

        // Null or empty range list should throw exception
        assertThatThrownBy(() -> new DailySchedule(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("DailySchedule must have at least one time range");

        assertThatThrownBy(() -> new DailySchedule(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("DailySchedule must have at least one time range");
    }

    @Test
    @DisplayName("Should allow multiple non-overlapping time ranges in daily schedule")
    void shouldAllowMultipleNonOverlappingRanges() {
        TimeRange morning = new TimeRange(LocalTime.of(9, 0), LocalTime.of(12, 0));
        TimeRange afternoon = new TimeRange(LocalTime.of(13, 0), LocalTime.of(18, 0));

        DailySchedule schedule = new DailySchedule(List.of(afternoon, morning)); // Sorted implicitly during validation

        assertThat(schedule.getRanges()).containsExactlyInAnyOrder(morning, afternoon);
        assertThat(schedule.isWithin(LocalTime.of(10, 0))).isTrue();
        assertThat(schedule.isWithin(LocalTime.of(12, 30))).isFalse();
        assertThat(schedule.isWithin(LocalTime.of(15, 0))).isTrue();
    }
}
