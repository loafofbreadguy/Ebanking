package com.banking.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComplaintResponse {

    private Long id;

    private String subject;

    private String description;

    private Long userId;
}