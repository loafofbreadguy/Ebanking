package com.banking.banking.service.impl;

import com.banking.banking.dto.ComplaintRequest;
import com.banking.banking.dto.ComplaintResponse;

import com.banking.banking.model.Complaint;
import com.banking.banking.model.User;
import com.banking.banking.repository.ComplaintRepository;
import com.banking.banking.repository.UserRepository;
import com.banking.banking.service.ComplaintService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public ComplaintServiceImpl(
            ComplaintRepository complaintRepository,
            UserRepository userRepository
    ) {
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ComplaintResponse createComplaint(ComplaintRequest complaintRequest) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Complaint complaint = new Complaint();
        complaint.setUser(user);
        complaint.setDescription(complaintRequest.getDescription());
        complaint.setSubject(complaintRequest.getSubject());
        complaint.setResolved(false);

        Complaint savedComplaint = complaintRepository.save(complaint);

        return new ComplaintResponse(
                complaint.getId(),
                complaint.getSubject(),
                complaint.getDescription(),
                complaint.getUser().getId()
        );
    }

    @Override
    public List<ComplaintResponse> getUserComplaints(Long userId) {
        List<Complaint> complaints = complaintRepository.findByUserId(userId);

        return complaints.stream().map(complaint -> new ComplaintResponse(
                complaint.getId(),
                complaint.getSubject(),
                complaint.getDescription(),
                complaint.getUser().getId()
        )).toList();
    }

    @Override
    public List<ComplaintResponse> getAllComplaints() {

        List<Complaint> complaints =
                complaintRepository.findByResolved(false);

        return complaints.stream()
                .map(complaint -> new ComplaintResponse(
                        complaint.getId(),
                        complaint.getSubject(),
                        complaint.getDescription(),
                        complaint.getUser().getId()
                ))
                .toList();
    }

    @Override
    public ComplaintResponse getComplaintById(Long id) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Complaint not found"));

        return new ComplaintResponse(
                complaint.getId(),
                complaint.getSubject(),
                complaint.getDescription(),
                complaint.getUser().getId()
        );
    }

    @Override
    public String resolveComplaints(Long id) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Complaint not found"));

        complaint.setResolved(true);

        complaintRepository.save(complaint);

        return "Complaint resolved successfully";
    }

}