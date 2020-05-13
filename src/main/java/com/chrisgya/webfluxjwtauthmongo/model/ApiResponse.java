package com.chrisgya.webfluxjwtauthmongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiResponse {
    private int status;
    private String message;
    private Object result;
}
