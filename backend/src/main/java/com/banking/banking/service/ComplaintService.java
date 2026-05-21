package com.banking.banking.service;

import com.banking.banking.dto.ComplaintRequest;
import com.banking.banking.dto.ComplaintResponse;
import com.banking.banking.model.Complaint;

import java.util.List;

public interface ComplaintService {

    ComplaintResponse createComplaint(ComplaintRequest complaintRequest);

    List<ComplaintResponse> getUserComplaints(Long userId);

    List<ComplaintResponse>  getAllComplaints();

    ComplaintResponse getComplaintById(Long id);

    String resolveComplaints(Long id);
}