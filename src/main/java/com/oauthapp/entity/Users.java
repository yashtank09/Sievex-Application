package com.oauthapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "m_users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_first_name")
    private String firstName;
    @Column(name = "user_last_name")
    private String lastName;
    @Column(name = "user_phone")
    private String phone;

    @JsonIgnore
    @Column(name = "user_profile_completed")
    private boolean profileCompleted;

    @Column(name = "user_password")
    private String password;

    @JsonIgnore
    @Column(name = "user_role")
    private String role;

    @JsonIgnore
    @Column(name = "user_type")
    private String type;

    @JsonIgnore
    @Column(name = "user_status")
    private String status;

    @JsonIgnore
    @Column(name = "created_at")
    private String createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private String updatedAt;

}
