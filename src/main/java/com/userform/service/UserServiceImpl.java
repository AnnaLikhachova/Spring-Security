package com.userform.service;

import com.userform.dao.PasswordResetTokenRepository;
import com.userform.dao.RoleRepository;
import com.userform.dao.UserRepository;
import com.userform.dao.VerificationTokenRepository;
import com.userform.model.PasswordResetToken;
import com.userform.model.User;
import com.userform.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private VerificationTokenRepository tokenDao;
    @Autowired
    private PasswordResetTokenRepository passwordTokenDao;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return tokenDao.findByToken(token);
    }

    @Override
    public void updateUser(User user) {
        User entity = userRepository.findOne(user.getId());
        if(entity!=null){
            entity.setUsername(user.getUsername());
            entity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            entity.setRoles(user.getRoles());
            entity.setEmail(user.getEmail());
            entity.setGreen(user.isGreen());
            userRepository.save(entity);
        }

    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingToken) {
        VerificationToken vToken = tokenDao.findByToken(existingToken);
        vToken.updateToken(UUID.randomUUID()
                .toString());
        vToken = tokenDao.save(vToken);
        return vToken;
    }

    @Override
    public User getUser(String verificationToken) {
        VerificationToken token = tokenDao.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public User findUserByEmail(String userEmail) {
        return userRepository.findUserByEmail(userEmail);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenDao.save(myToken);
    }

    @Override
    public User getUserByPasswordResetToken(String token) {
        return passwordTokenDao.findByToken(token).getUser();
    }

    @Override
    public void changeUserPassword(User user, String newPassword) {
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return bCryptPasswordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public void createVerificationTokenForUser(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenDao.save(myToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = tokenDao.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            // tokenDao.deleteByToken(token);
            return TOKEN_EXPIRED;
        }

        user.setGreen(true);
        // tokenDao.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }
}
