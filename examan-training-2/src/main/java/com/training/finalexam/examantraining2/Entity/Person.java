package com.training.finalexam.examantraining2.Entity;

import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Person {

    @Column(name = "name", nullable = false)
    private String Name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "academic_status")
    private AcademicStatus academicStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column
    private String columnaDePrueba;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Lpets> Lpets;

    @Column(name = "uuid", nullable = false, updatable = false)
    private String uniqueId;

    public Student(String firstName, String lastName, String email, Integer age, String uuid, AcademicStatus academicStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.uniqueId = uuid;
        this.academicStatus = academicStatus;
    }
}
