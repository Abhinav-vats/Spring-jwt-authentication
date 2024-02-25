package com.jwt.demo.Springjwtauthentication;

import com.jwt.demo.Springjwtauthentication.user.Role;
import com.jwt.demo.Springjwtauthentication.user.User;
import com.jwt.demo.Springjwtauthentication.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    UserRepository repository;

    @Test
    public void testCreateUser(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPass = "nam2020";
        final String encodedPass = encoder.encode(rawPass);

        User user = new User("abhi@java.net", encodedPass);
        User savedUser = repository.save(user);

        System.out.println(savedUser.getEmail().equalsIgnoreCase(user.getEmail()));
    }
    @Test
    public void testAnotherCreateUser(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPass = "Abhinav@123";
        final String encodedPass = encoder.encode(rawPass);

        User user = new User("abhi@gmail.com", encodedPass);
        User savedUser = repository.save(user);

        System.out.println(savedUser.getEmail().equalsIgnoreCase(user.getEmail()));
    }

    @Test
    public void testAnitaCreateUser(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPass = "Anita@123";
        final String encodedPass = encoder.encode(rawPass);

        User user = new User("anita@gmail.com", encodedPass);
        User savedUser = repository.save(user);

        System.out.println(savedUser.getEmail().equalsIgnoreCase(user.getEmail()));
    }

    @Test
    public void testJaiCreateUser(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPass = "Jai@123";
        final String encodedPass = encoder.encode(rawPass);

        User user = new User("Jai@gmail.com", encodedPass);
        User savedUser = repository.save(user);

        System.out.println(savedUser.getEmail().equalsIgnoreCase(user.getEmail()));
    }

    @Test
    public void testAssignRolesToUser(){
        int userId = 4;
        User user = repository.findById(userId).get();

        user.addRole(new Role(2));
        user.addRole(new Role(3));

        User updatedUser = repository.save(user);

        System.out.println(updatedUser.getRoles().size());

    }
}
