package com.mdx.xyphose.usermgmt.repo;

import com.mdx.xyphose.usermgmt.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment,Long> {
    List<Payment> findByUserEmail(String userEmail);
}
