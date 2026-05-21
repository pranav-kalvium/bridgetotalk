package com.pranav.bridgetotalk.adapter.out.jpa.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class CompanySettingsJpaEntityMixin {

    @JsonIgnore
    abstract Object getCompany(); // evita serializaÃ§Ã£o recursiva
}
