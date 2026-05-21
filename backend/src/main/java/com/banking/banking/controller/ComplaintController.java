package com.banking.banking.controller;

import com.banking.banking.dto.ApiResponse;
import com.banking.banking.dto.ComplaintRequest;
import com.banking.banking.dto.ComplaintResponse;
import com.banking.banking.model.Complaint;
import com.banking.banking.service.ComplaintService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(
            ComplaintService complaintService
    ) {
        this.complaintService = complaintService;
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<ComplaintResponse>> createComplaint(
            @RequestBody ComplaintRequest complaintRequest
    ) {

        ComplaintResponse complaintResponse =
                complaintService.createComplaint(complaintRequest);

        ApiResponse<ComplaintResponse> response =
                new ApiResponse<>(
                        true,
                        "Complaint created successfully",
                        complaintResponse
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN') ")
    public ResponseEntity<ApiResponse<List<ComplaintResponse>>> getUserComplaints(
            @PathVariable Long userId
    ) {

        List<ComplaintResponse> complaints =
                complaintService.getUserComplaints(userId);

        ApiResponse<List<ComplaintResponse>> response =
                new ApiResponse<>(
                        true,
                        "User complaints fetched successfully",
                        complaints
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') and isAuthenticated()")
    public ResponseEntity<ApiResponse<List<ComplaintResponse>>> getAllComplaints() {

        List<ComplaintResponse> complaints =
                complaintService.getAllComplaints();

        ApiResponse<List<ComplaintResponse>> response =
                new ApiResponse<>(
                        true,
                        "All complaints fetched successfully",
                        complaints
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ComplaintResponse>> getComplaintById(
            @PathVariable Long id
    ) {

        ComplaintResponse complaint =
                complaintService.getComplaintById(id);

        ApiResponse<ComplaintResponse> response =
                new ApiResponse<>(
                        true,
                        "Complaint fetched successfully",
                        complaint
                );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/resolve/{id}")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> updateComplaintById(
            @PathVariable Long id
    ){
        ApiResponse<String> response =
                new ApiResponse<String>(
                    true,
                    "Complaint fetched successfully",
                    complaintService.resolveComplaints(id)
                );
        return ResponseEntity.ok(response);
    }
}