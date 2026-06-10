package com.example.project.repository;

import com.example.project.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository
        extends JpaRepository<Complaint, Long> {

    long countByStatus(String status);

    List<Complaint> findByUserId(Long userId);
}