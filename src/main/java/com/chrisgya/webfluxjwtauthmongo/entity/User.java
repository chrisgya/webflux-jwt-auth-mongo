package com.chrisgya.webfluxjwtauthmongo.entity;

import com.chrisgya.webfluxjwtauthmongo.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String name;
    private int age;
    private double salary;
    @Indexed
    private String username;
    private String password;
    private List<Role> roles;

    public User(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
}
