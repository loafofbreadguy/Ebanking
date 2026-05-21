package com.banking.banking.repository;

import com.banking.banking.model.Complaint;
import com.banking.banking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByUserId(Long UserId);
    void deleteByUser(User user);
    List<Complaint> findByResolved(Boolean resolved);
    List<Complaint> findByResolvedAndUserId(Boolean resolved, Long userId);
}
