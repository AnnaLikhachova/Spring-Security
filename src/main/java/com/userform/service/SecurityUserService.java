package com.userform.service;

public interface SecurityUserService {

    String validatePasswordResetToken(String token);

}
