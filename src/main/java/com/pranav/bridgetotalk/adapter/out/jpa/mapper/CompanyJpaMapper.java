package com.pranav.bridgetotalk.adapter.out.jpa.mapper;

import com.pranav.bridgetotalk.adapter.out.jpa.entity.CompanyJpaEntity;
import com.pranav.bridgetotalk.adapter.out.jpa.entity.CompanySettingsJpaEntity;
import com.pranav.bridgetotalk.domain.organization.Company;
import com.pranav.bridgetotalk.domain.organization.CompanySettings;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CompanyJpaMapper {

    default CompanyJpaEntity toEntity(Company company) {

        CompanySettingsJpaEntity settings = null;

        if (company.getSettings() != null) {
            settings = toSettingsEntityFromDomain(company.getSettings());
        }

        var entity = new CompanyJpaEntity(
                company.getId(),
                company.getName(),
                company.getSlug(),
                company.getEmail(),
                company.getDocument(),
                company.getPhone(),
                company.getStatus(),
                company.getCreatedAt(),
                company.getUpdatedAt(),
                company.getDeletedAt(),
                settings
        );

        if (settings != null) {
            settings.setCompany(entity);
        }

        return entity;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDomain(
            Company domain,
            @MappingTarget CompanyJpaEntity entity
    );

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @org.mapstruct.Mapping(target = "companyId", ignore = true)
    @org.mapstruct.Mapping(target = "company", ignore = true)
    void updateSettingsEntityFromDomain(
            CompanySettings domain,
            @MappingTarget CompanySettingsJpaEntity entity
    );

    default Company toDomain(CompanyJpaEntity entity) {

        var settings = entity.getSettings() != null
                ? companySettingsJpaEntityToCompanySettings(entity.getSettings())
                : null;

        return Company.rehydrate(
                entity.getId(),
                entity.getName(),
                entity.getSlug(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getDocument(),
                entity.getStatus(),
                settings,
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }

    @org.mapstruct.Mapping(target = "companyId", ignore = true)
    @org.mapstruct.Mapping(target = "company", ignore = true)
    CompanySettingsJpaEntity toSettingsEntityFromDomain(CompanySettings domain);

    CompanySettings companySettingsJpaEntityToCompanySettings(
            CompanySettingsJpaEntity entity
    );
}
