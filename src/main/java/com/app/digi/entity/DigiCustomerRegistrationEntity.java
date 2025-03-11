package com.app.digi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "digi_customer_registration")
public class DigiCustomerRegistrationEntity {
    @Id
    @Column(name = "customer_id")
    private UUID customerId;
    @Column(name = "name")
    private String name;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "email_id")
    private String emailId;
    @Column(name = "gender")
    private String gender;
    @Column(name = "password")
    private String password;
    @Column(name = "customer_location")
    private String customerLocation;
    @Column(name = "message")
    private String message;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "create_at")
    private Long createAt;
    @Column(name = "update_at")
    private Long updateAt;

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustomerLocation() {
        return customerLocation;
    }

    public void setCustomerLocation(String customerLocation) {
        this.customerLocation = customerLocation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Long updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "DigiCustomerRegistrationEntity{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                ", customerLocation='" + customerLocation + '\'' +
                ", message='" + message + '\'' +
                ", isActive=" + isActive +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
