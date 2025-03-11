package com.app.digi.repository;

import com.app.digi.entity.DigiCustomerRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DigiCustomerRegistrationRepository extends JpaRepository<DigiCustomerRegistrationEntity, UUID> {

    public DigiCustomerRegistrationEntity findByMobileNumberAndPasswordAndIsActive(String mobileNumber, String password, boolean isActive);

    public DigiCustomerRegistrationEntity findByCustomerIdAndIsActive(UUID customerId, boolean isActive);
}
