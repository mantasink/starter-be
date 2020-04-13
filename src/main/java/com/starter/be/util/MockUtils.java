package com.starter.be.util;

import com.starter.be.model.Role;
import com.starter.be.model.User;
import com.starter.be.repository.RoleRepository;
import com.starter.be.service.UserService;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MockUtils {
  private static final Random RANDOM = new Random();

  @Autowired private RoleRepository roleRepository;
  @Autowired private UserService userService;

  private User[] users = new User[8];

  private static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
    int x = RANDOM.nextInt(clazz.getEnumConstants().length);
    return clazz.getEnumConstants()[x];
  }

  public void mockData() {
    mockUsers();
  }

  private void mockUsers() {
    Role role = new Role();
    role.setRole("USER");
    roleRepository.save(role);

    Role role2 = new Role();
    role2.setRole("ADMIN");
    roleRepository.save(role2);

    mockUser(0, role, null, "admin");
    mockUser(1, role, role2, "asd");
    mockUser(2, null, null, "Tony");
    mockUser(3, null, null, "John");
    mockUser(4, null, null, "nvent");
    mockUser(5, null, null, "Atavist");
    mockUser(6, null, role2, "w33man");
    mockUser(7, role, role2, "developer");
  }

  private void mockUser(int index, Role role1, Role role2, String username) {
    users[index] = new User();

    if (role1 != null) {
      users[index].getRoles().add(role1);
    }
    if (role2 != null) {
      users[index].getRoles().add(role2);
    }

    users[index].setUsername(username);
    users[index].setPassword(username + "123456");
    users[index].setEmail(username + "@mockmail.com");

    userService.create(users[index]);
  }
}
