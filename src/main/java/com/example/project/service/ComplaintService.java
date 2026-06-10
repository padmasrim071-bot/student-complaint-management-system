package com.example.project.service;

import com.example.project.dto.ComplaintRequest;
import com.example.project.dto.DashboardResponse;
import com.example.project.entity.Complaint;
import com.example.project.entity.User;
import com.example.project.repository.ComplaintRepository;
import com.example.project.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintRepository repository;
    private final UserRepository userRepository;

    public ComplaintService(
            ComplaintRepository repository,
            UserRepository userRepository) {

        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Complaint createComplaint(
            ComplaintRequest request) {

        User user = userRepository.findById(
                        request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        Complaint complaint = new Complaint();

        complaint.setTitle(request.getTitle());
        complaint.setDescription(
                request.getDescription());
        complaint.setCategory(
                request.getCategory());

        complaint.setStatus("PENDING");
        complaint.setCreatedAt(
                LocalDateTime.now());

        complaint.setUser(user);

        return repository.save(complaint);
    }

    public List<Complaint> getAllComplaints() {
        return repository.findAll();
    }

    public Complaint getComplaintById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Complaint not found with id: " + id));
    }

    public Complaint updateStatus(
            Long id,
            String status) {

        Complaint complaint =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Complaint not found with id: " + id));

        complaint.setStatus(status);

        return repository.save(complaint);
    }

    public DashboardResponse getDashboardStats() {

        long total = repository.count();
        long pending =
                repository.countByStatus("PENDING");
        long resolved =
                repository.countByStatus("RESOLVED");
        long inProgress =
                repository.countByStatus("IN_PROGRESS");
        long rejected =
                repository.countByStatus("REJECTED");

        return new DashboardResponse(
                total,
                pending,
                resolved,
                inProgress,
                rejected
        );
    }

    public void deleteComplaint(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException(
                    "Complaint not found with id: " + id);
        }

        repository.deleteById(id);
    }

    public List<Complaint> getComplaintsByUserId(
            Long userId) {

        return repository.findByUserId(userId);
    }
}