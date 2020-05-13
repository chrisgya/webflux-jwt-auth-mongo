package com.chrisgya.webfluxjwtauthmongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
    private String name;
    private String description;
}
