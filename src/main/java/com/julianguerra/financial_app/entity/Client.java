package com.julianguerra.financial_app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identificationType;

    @Column(unique = true, nullable = false)
    private String identificationNumber;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private LocalDate birthDate;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDate.now();
    }
}
