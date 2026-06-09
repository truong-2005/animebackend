package com.qtanime.animebackend.entity;

import java.time.LocalDate;
import java.util.List;

import com.qtanime.animebackend.enums.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Column(
            unique = true,
            nullable = false
    )
    private String username;

    @Column(
            unique = true,
            nullable = false
    )
    private String email;

    @Column(unique = true)
    private String phone;

    private String password;

    private String fullName;

    // FILE IMAGE
    private String avatar;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Boolean enabled;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}