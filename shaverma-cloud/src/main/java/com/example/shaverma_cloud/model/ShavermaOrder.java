package com.example.shaverma_cloud.model;

import com.example.shaverma_cloud.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
public class ShavermaOrder implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date placedAt;
    @NotBlank(message = "Your name couldn't be empty")
    private String deliveryName;
    @NotBlank(message = "Your street couldn't be empty")
    private String deliveryStreet;
    @NotBlank(message = "Your city couldnt be empty")
    private String deliveryCity;
    private String deliveryState;
    @Digits(integer = 6, fraction = 0,message = "Your zip code must be 6 digits ")
    private String deliveryZip;

    private String ccNumber;

    private String ccExpiration;
    @Digits(integer = 3, fraction = 0,message = "Your cvv code must be 3 digits ")
    private String ccCVV;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Shaverma> shavermas = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryStreet() {
        return deliveryStreet;
    }

    public void setDeliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }




    public ShavermaOrder(String deliveryName, String deliveryStreet,
                         String deliveryCity, String deliveryState, String deliveryZip,
                         String ccNumber, String ccExpiration,
                         String ccCVV, List<Shaverma> shavermas) {
        this.deliveryName = deliveryName;
        this.deliveryStreet = deliveryStreet;
        this.deliveryCity = deliveryCity;
        this.deliveryState = deliveryState;
        this.deliveryZip = deliveryZip;
        this.ccNumber = ccNumber;
        this.ccExpiration = ccExpiration;
        this.ccCVV = ccCVV;
        this.shavermas = shavermas;
    }
    public ShavermaOrder(){

    }
    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public String getDeliveryZip() {
        return deliveryZip;
    }

    public void setDeliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCcExpiration() {
        return ccExpiration;
    }

    public void setCcExpiration(String ccExpiration) {
        this.ccExpiration = ccExpiration;
    }

    public String getCcCVV() {
        return ccCVV;
    }

    public void setCcCVV(String ccCVV) {
        this.ccCVV = ccCVV;
    }

    public List<Shaverma> getShavermas() {
        return shavermas;
    }

    public void setShavermas(List<Shaverma> shavermas) {
        this.shavermas = shavermas;
    }




    public void addShaverma(Shaverma shaverma) {
        this.shavermas.add(shaverma);
    }


}
