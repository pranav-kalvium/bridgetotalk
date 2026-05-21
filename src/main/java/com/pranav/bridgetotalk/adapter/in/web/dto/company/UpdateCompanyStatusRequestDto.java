package com.pranav.bridgetotalk.adapter.in.web.dto.company;

import com.pranav.bridgetotalk.domain.organization.CompanyStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateCompanyStatusRequestDto(
        @NotNull(message = "Status is required")
        CompanyStatus status
){}

