package com.pranav.bridgetotalk.adapter.out.jpa.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class CompanyJpaEntityMixin {

    @JsonIgnore
    abstract Object getSettings(); // Ignore 1-1 reverse relationship (prevent loop)

}
