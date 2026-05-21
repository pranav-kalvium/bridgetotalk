package com.pranav.bridgetotalk.adapter.in.web.mapper;

import com.pranav.bridgetotalk.adapter.in.web.dto.company.CompanySettingsUpdateDto;
import com.pranav.bridgetotalk.adapter.in.web.dto.company.CreateCompanyDto;
import com.pranav.bridgetotalk.adapter.in.web.dto.company.ResponseCompanyDto;
import com.pranav.bridgetotalk.application.port.in.command.UpdateCompanyCommand;
import com.pranav.bridgetotalk.domain.organization.Company;
import com.pranav.bridgetotalk.domain.organization.CompanySettings;
import com.pranav.bridgetotalk.domain.shared.exception.BusinessException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CompanyDtoMapper {

    @Mapping(target = "id", ignore = true) // sempre gerado pelo Company()
    default Company toDomain(CreateCompanyDto dto){

        return Company.createNew(
                dto.name(),
                dto.slug(),
                dto.email(),
                dto.phone(),
                dto.document(),
                dto.plan()
        );
    }

    CreateCompanyDto toCreateCompanyDto(Company company);

    @Mapping(target = "id", source = "id")
    UpdateCompanyCommand toCommand(UUID id, CreateCompanyDto dto);

    ResponseCompanyDto toResponseDto(Company company);

    default CompanySettings companySettingsUpdateDtoToCompanySettings(CompanySettingsUpdateDto companySettingsDto) throws BusinessException {
        if ( companySettingsDto == null ) {
            return null;
        }

        return CompanySettings.createFromPlan(companySettingsDto.plan());
    }
}
