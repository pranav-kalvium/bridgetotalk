package com.pranav.bridgetotalk.adapter.out.jpa.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class CompanyJpaEntityMixin {

    @JsonIgnore
    abstract Object getSettings(); // ignorar relaÃ§Ã£o 1-1 reversa (prevent loop)

}
