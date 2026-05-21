package com.banking.banking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ComplaintRequest {

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "description is required")
    private String description;
}
