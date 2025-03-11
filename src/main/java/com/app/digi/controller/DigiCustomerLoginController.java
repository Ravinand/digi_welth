package com.app.digi.controller;

import com.app.digi.entity.DigiCustomerRegistrationEntity;
import com.app.digi.repository.DigiCustomerRegistrationRepository;
import com.app.digi.request.CustomerLoginPasswordChangeRequest;
import com.app.digi.request.CustomerLoginRequest;
import com.app.digi.request.DigiCustomerRegistrationRequest;
import com.app.digi.response.CustomerLoginResponse;
import com.app.digi.response.DigiCustomerRegistrationResponse;
import com.app.digi.response.ResponseBase;
import com.app.digi.scope.AuthScope;
import com.app.digi.security.JwtTokenProvider;
import com.app.digi.util.ControllerMapping;
import com.app.digi.util.ResponseConstants;
import com.app.digi.validation.CreateCustomerRegistrationValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@CrossOrigin(origins = "*")
public class DigiCustomerLoginController {

    @Autowired
    private DigiCustomerRegistrationRepository digiCustomerRegistrationRepository;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping(ControllerMapping.CREATE_CUSTOMER_REGISTRATION)
    public ResponseEntity<DigiCustomerRegistrationResponse> createCustomerRegistration(@RequestBody DigiCustomerRegistrationRequest request) {
        DigiCustomerRegistrationResponse response = new DigiCustomerRegistrationResponse();
        try {
            if (request == null || CreateCustomerRegistrationValidation.customerRegistrationDataValidate(request)) {
                response.statusCode = ResponseConstants.failedCode;
                response.statusMessage = ResponseConstants.requestParamEmpty;
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                DigiCustomerRegistrationEntity entity = new DigiCustomerRegistrationEntity();
                entity.setCustomerId(UUID.randomUUID());
                entity.setName(request.getName());
                entity.setMobileNumber(request.getMobileNumber());
                entity.setEmailId(request.getEmailId());
                entity.setGender(request.getGender());
                entity.setPassword(this.autoPasswordGenerated());
                entity.setCustomerLocation(request.getCustomerLocation());
                entity.setMessage(request.getMessage());
                entity.setActive(true);
                entity.setCreateAt(System.currentTimeMillis());
                entity.setUpdateAt(0l);
                DigiCustomerRegistrationEntity respDigiCustomerRegiData = digiCustomerRegistrationRepository.save(entity);
                response.setUserName(respDigiCustomerRegiData.getMobileNumber());
                response.setPassword(respDigiCustomerRegiData.getPassword());
                response.statusCode = ResponseConstants.successCode;
                response.statusMessage = ResponseConstants.successMessage;
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.statusMessage = ResponseConstants.failedMessage;
            response.statusCode = ResponseConstants.WRONG_INPUT;
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    private String autoPasswordGenerated() {
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new SecureRandom();
        return IntStream.range(0, 8).mapToObj(i -> String.valueOf(alphanumeric.charAt(random.nextInt(alphanumeric.length())))).collect(Collectors.joining());
    }

    @PostMapping(ControllerMapping.CUSTOMER_LOGIN)
    public ResponseEntity<CustomerLoginResponse> customerLogin(@RequestBody CustomerLoginRequest request) {
        CustomerLoginResponse response = new CustomerLoginResponse();
        try {
            if (request == null || request.getUsername() == null || request.getUsername().isEmpty() || request.getPassword() == null || request.getPassword().isEmpty()) {
                response.statusCode = ResponseConstants.failedCode;
                response.statusMessage = ResponseConstants.requestParamEmpty;
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                DigiCustomerRegistrationEntity respUserValidateData = digiCustomerRegistrationRepository.findByMobileNumberAndPasswordAndIsActive(request.getUsername().trim(), request.getPassword().trim(), true);
                if (respUserValidateData != null) {
                    String createToken = tokenProvider.createToken(String.valueOf(respUserValidateData.getCustomerId()), AuthScope.DIGI_CUSTOMER);
                    response.setName(respUserValidateData.getName());
                    response.setMessage(respUserValidateData.getMessage());
                    response.setCustomerLocation(respUserValidateData.getCustomerLocation());
                    response.setGender(respUserValidateData.getGender());
                    response.setMobileNumber(respUserValidateData.getMobileNumber());
                    response.setEmailId(respUserValidateData.getEmailId());
                    response.setToken(createToken);
                    System.out.println("============" + request);
                } else {
                    response.statusCode = ResponseConstants.failedCode;
                    response.statusMessage = ResponseConstants.INVALID_USER_NAME_AND_PASSWORD;
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
                response.statusCode = ResponseConstants.successCode;
                response.statusMessage = ResponseConstants.successMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.statusMessage = ResponseConstants.failedMessage;
            response.statusCode = ResponseConstants.WRONG_INPUT;
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(ControllerMapping.CHANGE_CUSTOMER_LOGIN_PASSWORD)
    public ResponseEntity<ResponseBase> changeCustomerLoginPassword(@RequestBody CustomerLoginPasswordChangeRequest request, @RequestHeader("Authorization") String token) {
        ResponseBase response = new ResponseBase();
        try {
            if (request == null || token == null || token.isEmpty() ||
                    request.getNewPassword() == null || request.getNewPassword().isEmpty() ||
                    request.getReEnterPassword() == null || request.getReEnterPassword().isEmpty()) {
                response.statusCode = ResponseConstants.failedCode;
                response.statusMessage = ResponseConstants.requestParamEmpty;
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                String bearer = tokenProvider.resolveToken(token);
                if (tokenProvider.validateToken(bearer, AuthScope.DIGI_CUSTOMER)) {
                    String getCustomerId = tokenProvider.getUsername(bearer);
                    DigiCustomerRegistrationEntity respValidateUser = digiCustomerRegistrationRepository.findByCustomerIdAndIsActive(UUID.fromString(getCustomerId), true);
                    if (respValidateUser != null) {
                        if (request.getNewPassword().trim().equals(request.getReEnterPassword().trim())) {
                            respValidateUser.setPassword(request.getNewPassword());
                            respValidateUser.setUpdateAt(System.currentTimeMillis());
                            digiCustomerRegistrationRepository.save(respValidateUser);
                        } else {
                            response.statusCode = ResponseConstants.failedCode;
                            response.statusMessage = ResponseConstants.BOTH_PASSWORD_NOT_MATCH;
                            return new ResponseEntity<>(response, HttpStatus.OK);
                        }
                    } else {
                        response.statusCode = ResponseConstants.failedCode;
                        response.statusMessage = ResponseConstants.LOGIN_USER_DATA_NOT_FOUND;
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    }
                } else {
                    response.statusCode = ResponseConstants.failedCode;
                    response.statusMessage = ResponseConstants.INVALID_TOKEN;
                    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
                }
                response.statusCode = ResponseConstants.successCode;
                response.statusMessage = ResponseConstants.successMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.statusMessage = ResponseConstants.failedMessage;
            response.statusCode = ResponseConstants.WRONG_INPUT;
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
