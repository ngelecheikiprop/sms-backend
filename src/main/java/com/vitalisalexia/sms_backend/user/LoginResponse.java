package com.vitalisalexia.sms_backend.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    Integer status;
    String message;
    String token;
}
