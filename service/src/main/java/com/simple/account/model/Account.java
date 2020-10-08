package com.simple.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Account {
    @Id
    @Column(columnDefinition = "varchar(250)")
    private String id;
    private String name;
    private String password;
    private String passwordHash;
    private String email;
    private boolean confirmedAndActive;
    private String phoneNumber;
    private String photoUrl;
}
