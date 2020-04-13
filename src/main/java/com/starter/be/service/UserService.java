package com.starter.be.service;

import com.starter.be.exception.ServiceException;
import com.starter.be.model.User;
import com.starter.be.payload.ChangePasswordRequest;
import com.starter.be.payload.RegistrationRequest;
import com.starter.be.repository.UserRepository;
import java.util.Optional;
import lombok.extern.java.Log;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log
public class UserService {

  private UserRepository userRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private TokenService tokenService;

  public UserService(
      UserRepository userRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      TokenService tokenService) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.tokenService = tokenService;
  }

  public void changePassword(String token, ChangePasswordRequest request) {
    long userId = tokenService.getUserId(token);

    Optional<User> optionalUser = userRepository.findById(userId);
    User user = optionalUser.orElseThrow(() -> new ServiceException("Unauthorized."));

    if (!bCryptPasswordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
      throw new ServiceException("Current password does not match.");
    }

    user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
    userRepository.save(user);
    log.info(String.format("Password changed: %s", user.toString()));
  }

  public void registerUser(RegistrationRequest registrationRequest) {
    User byUsername = userRepository.findByUsername(registrationRequest.getUsername());
    User byEmail = userRepository.findByEmail(registrationRequest.getEmail());

    if (byUsername != null || byEmail != null) {
      throw new ServiceException("User already exist.");
    }

    create(
        User.builder()
            .username(registrationRequest.getUsername())
            .email(registrationRequest.getEmail())
            .password(registrationRequest.getPassword())
            .build());
  }

  public User create(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setEnabled(true);
    return userRepository.save(user);
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
