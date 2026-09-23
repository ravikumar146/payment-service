package com.nextgen.paymentservice.repository;

import com.nextgen.paymentservice.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentDetails, Long> {
}
