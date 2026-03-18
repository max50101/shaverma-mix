package com.example.shaverma_cloud.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name="users")
public class User {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id; // NOT final

    @Column(unique = true, nullable = false)
    private String username;

    private String fullname="";
    private String street="";
    private String city="";
    private String state="";
    private String zip="";
    private String phoneNumber="";

    public User(Long id,String username){
        this.id=id;
        this.username=username;
    }
    public User(){

    }
    public User(String username, String fullname,
                String street, String city, String state, String zip, String phoneNumber) {
        this.username = username;
        this.fullname = fullname;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
    }


}
