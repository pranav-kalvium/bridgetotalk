package com.pranav.bridgetotalk.domain.attendance;

public enum DistributionStrategy {
    LEAST_BUSY("Least Busy"),        // Routes to the agent with the lowest number of active conversations.
    ROUND_ROBIN("Round Robin"),      // Rotates through all eligible agents, sequentially.
    LONGEST_AVAILABLE("Longest Available"), // Routes to the agent who has been available for the longest time.
    PRIORITY("Priority Based");       // Routes based on an agent priority score.

    private final String description;

    DistributionStrategy(String description) {
        this.description = description;
    }
}