package com.berkay22demirel.couriertracking.model;

import com.berkay22demirel.couriertracking.aop.annotation.ID;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Courier {

    @ID
    private Long id;
    @NotEmpty(message = "name cannot be empty")
    private String name;
    @NotNull(message = "age cannot be null")
    @Min(value = 18, message = "age must be min 18")
    @Max(value = 65, message = "age must be max 65")
    private Integer age;
    @Min(value = 2200, message = "salary must be min 2200")
    @Max(value = 100000, message = "salary must be max 100000")
    @NotNull(message = "salary cannot be null")
    private Double salary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
