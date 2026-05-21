package com.pranav.bridgetotalk.adapter.in.web.dto.company;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pranav.bridgetotalk.domain.organization.Plan;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record CreateCompanyDto(
        String name,
        String slug,
        String email,
        String phone,
        String document,
        Plan plan
) {}
