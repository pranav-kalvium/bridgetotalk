package com.pranav.bridgetotalk.adapter.out.jpa;

import com.pranav.bridgetotalk.adapter.out.jpa.entity.CompanySettingsJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataCompanySettingsRepository extends JpaRepository<CompanySettingsJpaEntity, UUID> {
}
