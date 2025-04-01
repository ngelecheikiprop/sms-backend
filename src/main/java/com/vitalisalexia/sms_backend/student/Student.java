package com.vitalisalexia.sms_backend.student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="students")
public class Student {
    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String className;
    private Integer score;
    private Integer status;
    private String photoPath;
}
