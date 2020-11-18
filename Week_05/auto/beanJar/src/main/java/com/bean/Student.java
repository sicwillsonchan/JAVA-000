package com.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Student implements Serializable {
    
    private int id;
    private String name;
    
    public void init(){
        System.out.println("Begin");
    }
    
    public Student create(){
        return new Student(101,"wilson101");
    }
}
