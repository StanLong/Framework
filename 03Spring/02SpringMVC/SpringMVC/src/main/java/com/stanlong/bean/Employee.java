package com.stanlong.bean;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Employee {
    @NonNull
    private Integer id;
    @NonNull
    private String lastName;

    @NonNull
    private String email;
    //1 male, 0 female
    @NonNull
    private Integer gender;
    public Employee() {
    }
}