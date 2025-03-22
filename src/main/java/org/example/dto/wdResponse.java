package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class wdResponse {
    private String responseCode;
    private String responseMessage;
    private String userId;
    private double balance;
}
