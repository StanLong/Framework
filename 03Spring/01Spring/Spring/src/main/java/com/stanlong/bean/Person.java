package com.stanlong.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Person {
    private String pname;
    private Integer age;

    private Address homeAddr;
    private Address companyAddr;
}
