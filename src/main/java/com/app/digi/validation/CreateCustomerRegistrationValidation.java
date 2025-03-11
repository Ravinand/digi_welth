package com.app.digi.validation;

import com.app.digi.request.DigiCustomerRegistrationRequest;

public class CreateCustomerRegistrationValidation {

    public static boolean customerRegistrationDataValidate(DigiCustomerRegistrationRequest request) {
        boolean flag = false;

        if (request == null) {
            flag = true;
        } else if (request.getName() == null || request.getName().isEmpty()) {
            flag = true;
        } else if (request.getMobileNumber() == null || request.getMobileNumber().isEmpty()) {
            flag = true;
        } else if (request.getEmailId() == null || request.getEmailId().isEmpty()) {
            flag = true;
        } else if (request.getGender() == null || request.getGender().isEmpty()) {
            flag = true;
        } else if (request.getCustomerLocation() == null || request.getCustomerLocation().isEmpty()) {
            flag = true;
        }
        return flag;
    }
}
