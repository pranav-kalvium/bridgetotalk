package com.pranav.bridgetotalk.domain.attendance;

public enum DistributionStrategy {
    LEAST_BUSY("Least Busy"),        // Direciona para o agente com o menor nÃºmero de conversas ativas.
    ROUND_ROBIN("Round Robin"),      // Roda por todos os agentes elegÃ­veis, sequencialmente.
    LONGEST_AVAILABLE("Longest Available"), // Direciona para o agente que estÃ¡ disponÃ­vel hÃ¡ mais tempo.
    PRIORITY("Priority Based");       // Direciona com base em uma pontuaÃ§Ã£o de prioridade do agente.

    private final String description;

    DistributionStrategy(String description) {
        this.description = description;
    }
}