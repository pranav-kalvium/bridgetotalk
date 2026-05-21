package com.pranav.bridgetotalk.domain.people;

public enum AgentStatus {
    AVAILABLE("Available"),       // Pode receber novas conversas (rotaÃ§Ã£o).
    BUSY("Busy"),                 // EstÃ¡ em uma conversa, mas ainda pode ser elegÃ­vel dependendo da estratÃ©gia.
    PAUSED("Paused"),             // IndisponÃ­vel (ex: almoÃ§o, reuniÃ£o). NÃ£o recebe novas conversas.
    OFFLINE("Offline");           // Deslogado do sistema.

    private final String description;

    AgentStatus(String description) {
        this.description = description;
    }

    public static AgentStatus from(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            return AgentStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Valor invÃ¡lido apÃ³s validaÃ§Ã£o: " + value);
        }
    }
}