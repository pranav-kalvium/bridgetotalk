package com.pranav.bridgetotalk.application.port.in;

import com.pranav.bridgetotalk.application.port.in.command.UpdateCompanyCommand;
import com.pranav.bridgetotalk.application.port.in.command.UpdateCompanySettingsCommand;
import com.pranav.bridgetotalk.domain.organization.Company;
import com.pranav.bridgetotalk.domain.organization.CompanySettings;
import com.pranav.bridgetotalk.domain.organization.CompanyStatus;
import com.pranav.bridgetotalk.domain.shared.exception.BusinessException;

import java.util.List;
import java.util.UUID;

public interface ManageCompanyUseCase {

    Company create(Company company);

    CompanySettings updateSettings(UUID companyId, UpdateCompanySettingsCommand updateCompanySettingsCommand) throws BusinessException;

    Company get(UUID id);

    List<Company> list();

    Company update(UpdateCompanyCommand command) throws BusinessException;

    Company changeStatus(UUID companyId, CompanyStatus newStatus) throws BusinessException;
}
