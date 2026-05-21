package com.pranav.bridgetotalk.domain.people;

public enum AgentRole {
    ADMIN("Admin"),       // Acesso total, pode gerenciar empresas, planos, e configuraÃ§Ãµes globais.
    MANAGER("Manager"),   // Gerente de equipe. Pode gerenciar agentes, filas e relatÃ³rios da sua empresa.
    AGENT("Agent");       // Atendente padrÃ£o. Pode gerenciar suas prÃ³prias conversas.

    private final String description;

    AgentRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
