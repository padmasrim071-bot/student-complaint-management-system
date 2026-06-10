package com.example.project.controller;

import com.example.project.dto.DashboardResponse;
import com.example.project.entity.Complaint;
import com.example.project.service.ComplaintService;
import org.springframework.web.bind.annotation.*;
import com.example.project.dto.ComplaintRequest;
import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    private final ComplaintService service;

    public ComplaintController(ComplaintService service) {
        this.service = service;
    }

    @PostMapping
    public Complaint createComplaint(
            @RequestBody ComplaintRequest request) {

        return service.createComplaint(request);
    }

    @GetMapping
    public List<Complaint> getAllComplaints() {

        return service.getAllComplaints();
    }

    @GetMapping("/{id}")
    public Complaint getComplaintById(@PathVariable Long id) {
        return service.getComplaintById(id);
    }

    @PutMapping("/{id}/status")
    public Complaint updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return service.updateStatus(id, status);
    }
    @GetMapping("/dashboard")
    public DashboardResponse getDashboardStats() {

        return service.getDashboardStats();
    }
    @DeleteMapping("/{id}")
    public String deleteComplaint(@PathVariable Long id) {
        service.deleteComplaint(id);
        return "Deleted successfully";
    }
    @GetMapping("/user/{userId}")
    public List<Complaint> getComplaintsByUserId(
            @PathVariable Long userId) {

        return service.getComplaintsByUserId(userId);
    }
}