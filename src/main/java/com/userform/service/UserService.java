package com.userform.service;

import com.userform.model.User;
import com.userform.model.VerificationToken;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

    VerificationToken getVerificationToken(String token);

    void updateUser(User user);

    VerificationToken generateNewVerificationToken(String existingToken);

    User getUser(String token);

    User findUserByEmail(String userEmail);

    void createPasswordResetTokenForUser(User user, String token);

    User getUserByPasswordResetToken(String token);

    void changeUserPassword(User user, String newPassword);

    boolean checkIfValidOldPassword(User user, String oldPassword);

    void createVerificationTokenForUser(User user, String token);

    String validateVerificationToken(String token);
}
